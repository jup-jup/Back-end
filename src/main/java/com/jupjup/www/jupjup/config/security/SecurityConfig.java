package com.jupjup.www.jupjup.config.security;

import com.jupjup.www.jupjup.user.oauth.CustomOAuth2UserService;
import com.jupjup.www.jupjup.user.oauth.CustomOAuthSuccessHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOAuthSuccessHandler customOAuthSuccessHandler;
    private final JWTUtil jwtUtil;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        log.info("filterChain 시작");
        // CSRF 비활성화
        http.csrf(AbstractHttpConfigurer::disable);

        // 폼 로그인과 HTTP 기본 인증 비활성화
        http.formLogin(AbstractHttpConfigurer::disable);
        http.logout(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);

        // CORS 설정
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        // OAuth2 로그인 설정
        http.oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfo -> userInfo
                        .userService(customOAuth2UserService))
                .successHandler(customOAuthSuccessHandler)
                .authorizationEndpoint(authorization -> authorization
                        .baseUri("/oauth2/authorize"))
                .redirectionEndpoint(redirection -> redirection
                        .baseUri("/login/oauth2/code/*"))
        );

        // JWTFilter 추가 - UsernamePasswordAuthenticationFilter 이전에 배치
        http.addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        // JWT ExceptionHandler
        http.addFilterBefore(new ExceptionHandlerFilter(), JWTFilter.class);

        // 세션 관리 - STATELESS 설정
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        log.info("filterChain 종료");

        return http.build();
    }

    @Bean
    public AuthenticationManager customAuthenticationManager(AuthenticationConfiguration configure) throws Exception {
        return configure.getAuthenticationManager();
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000", "https://jupjup.shop"));  // 명시적인 도메인 설정
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Origin", "Accept"));
        configuration.setAllowCredentials(true);  // 크리덴셜 허용 (쿠키, 인증 헤더 등)
        configuration.setMaxAge(3600L);
        configuration.setExposedHeaders(List.of("Content-Length", "X-Custom-Header"));  // 필요한 경우 추가
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}