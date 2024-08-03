package com.jupjup.www.jupjup.config;

import com.jupjup.www.jupjup.formLoginHandler.CustomAccessDeniedHandler;
import com.jupjup.www.jupjup.formLoginHandler.CustomSuccessHandler;
import com.jupjup.www.jupjup.jwt.JWTFilter;
import com.jupjup.www.jupjup.jwt.JWTUtil;
import com.jupjup.www.jupjup.jwt.JwtProperties;
import com.jupjup.www.jupjup.jwt.LoginFilter;
import com.jupjup.www.jupjup.oauth2.CustomOAuth2UserService;
import com.jupjup.www.jupjup.oauth2.CustomOAuthSuccessHandler;
import com.jupjup.www.jupjup.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
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
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOAuthSuccessHandler customOAuthSuccessHandler;
    private final CustomSuccessHandler customSuccessHandler;
    private final AuthenticationConfiguration configuration;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final JWTUtil jwtUtil;
    private final JwtProperties jwtProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //csrf disable
        http.csrf(AbstractHttpConfigurer::disable);

        //경로별 인가 작업
        http.authorizeHttpRequests((auth) -> auth
                // 정적 리소스에 대한 접근 허용
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .requestMatchers("/static/**", "/templates/**").permitAll()
                .requestMatchers("/join", "/logout","/auth/refresh").permitAll()
                .requestMatchers("/index","/joinForm", "/loginForm","/loginError","/loginSuccess").permitAll()
                .requestMatchers("/swagger", "/swagger-ui/**", "/api-docs", "/api-docs/**", "/v3/api-docs/**").permitAll()
                .anyRequest().permitAll());

        // 예외 핸들러 작성
        http.exceptionHandling(ex -> ex
                .accessDeniedHandler(customAccessDeniedHandler)
        );

        //From 로그인 방식 disable
//        http.formLogin(AbstractHttpConfigurer::disable);
//        http.logout(AbstractHttpConfigurer::disable);

        //From 로그인 방식
        http.formLogin((form) -> form
                .loginPage("/loginForm")
                .loginProcessingUrl("/login")
                .successHandler(customSuccessHandler)
                .permitAll()
        ).logout(LogoutConfigurer::permitAll);

        //HTTP Basic 인증 방식 disable
        http.httpBasic(AbstractHttpConfigurer::disable);

        //cors 보안 강화
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        //oauth2
        http.oauth2Login((auth) -> auth
                .userInfoEndpoint((userInfoEndpointConfig -> userInfoEndpointConfig
                        .userService(customOAuth2UserService)))
                .successHandler(customOAuthSuccessHandler));


        // JWTFilter 추가 - JWTFilter 는 로그인 필터(LoginFilter) 후에 실행되어야 합니다.
//        http.addFilterBefore(new JWTFilter(jwtProperties, jwtUtil), LoginFilter.class);

        // LoginFilter 는 UsernamePasswordAuthenticationFilter 와 동일한 위치에 배치
        http.addFilterAt(new LoginFilter(customAuthenticationManager(configuration), jwtUtil, refreshTokenRepository), UsernamePasswordAuthenticationFilter.class);

        // 세션 설정 : STATELESS
        http.sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

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
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


/**
 * 이 메서드는 정적 자원에 대해 보안을 적용하지 않도록 설정한다.
 * 정적 자원은 보통 HTML, CSS, JavaScript, 이미지 파일 등을 의미하며, 이들에 대해 보안을 적용하지 않음으로써 성능을 향상시킬 수 있다.
 */
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return web -> web.ignoring()
//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
//
//    }

}