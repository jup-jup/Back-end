package com.jupjup.www.jupjup.config;


import com.jupjup.www.jupjup.formLoginHandler.CustomAccessDeniedHandler;
import com.jupjup.www.jupjup.formLoginHandler.CustomSuccessHandler;
import com.jupjup.www.jupjup.jwt.JWTFilter;
import com.jupjup.www.jupjup.jwt.JWTUtil;
import com.jupjup.www.jupjup.jwt.LoginFilter;
import com.jupjup.www.jupjup.oauth2.CustomOAuth2UserService;
import com.jupjup.www.jupjup.oauth2.CustomOAuthSuccessHandler;
import com.jupjup.www.jupjup.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOAuthSuccessHandler customOAuthSuccessHandler;
    private final CustomSuccessHandler customSuccessHandler;
    private final AuthenticationConfiguration configuration;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final JWTUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;


    @Bean
    public SecurityFilterChain FilterChain(HttpSecurity http) throws Exception {

        //csrf disable
        http
                .csrf(AbstractHttpConfigurer::disable);

        //경로별 인가 작업
        http
                .authorizeHttpRequests((auth) -> auth
                        // 정적 리소스에 대한 접근 허용
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/join", "/login","/logout","/auth/refresh","/auth/test").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/user").hasRole("USER")
                        .requestMatchers("/swagger", "/swagger-ui/**", "/api-docs", "/api-docs/**", "/v3/api-docs/**", "/")
                        .permitAll()
                        // authenticated() : 로그인 되어야 접근 가능 함
                        .anyRequest().authenticated());

        // 예외 핸들러 작성
        http
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(customAccessDeniedHandler)
                );


        //From 로그인 방식 disable
        http
                .formLogin(AbstractHttpConfigurer::disable);
        http
                .logout(AbstractHttpConfigurer::disable);
//        http
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .invalidateHttpSession(true)
//                        .deleteCookies("JSESSIONID")
//                        .logoutSuccessUrl("/logoutSuccess")
//                        .permitAll());

        //From 로그인 방식 disable
//        http
//                .formLogin((form) -> form
//                        .loginPage("/loginForm")
//                        .loginProcessingUrl("/login")
//                        .successHandler(customSuccessHandler)
//                        .permitAll()
//                ).logout(LogoutConfigurer::permitAll);


        //HTTP Basic 인증 방식 disable
        http
                .httpBasic(AbstractHttpConfigurer::disable);

        //cors 보안 강화
        http.
                cors(cors -> cors.configurationSource(corsConfigurationSource()));

        //oauth2
        http
                .oauth2Login((auth) -> auth
                        .userInfoEndpoint((userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(customOAuth2UserService)))
                        .successHandler(customOAuthSuccessHandler));


        // JWTFilter 추가 - JWTFilter 는 로그인 필터(LoginFilter) 후에 실행되어야 합니다.
        http.addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);

        // LoginFilter 는 UsernamePasswordAuthenticationFilter 와 동일한 위치에 배치
        http.addFilterAt(new LoginFilter(customAuthenticationManager(configuration), jwtUtil,refreshTokenRepository), UsernamePasswordAuthenticationFilter.class);

//         로깅 설정
//        http
//                .addFilterAfter(new LoggingFilter(), UsernamePasswordAuthenticationFilter.class); // LoggingFilter 를 UsernamePasswordAuthenticationFilter 가 실행된 후에 실행

        // 세션 설정 : STATELESS
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

//    @Bean
//    public FilterRegistrationBean<LoggingFilter> loggingFilter() {
//        FilterRegistrationBean<LoggingFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new LoggingFilter());
//        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE); // 가장 먼저 실행되도록 설정
//        return registrationBean;
//    }

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
        //새로운 CorsConfiguration 객체 생성
        CorsConfiguration configuration = new CorsConfiguration();
        //모든 출처 허용
        configuration.setAllowedOrigins(List.of("*"));
        //허용할 HTTP 메서드 설정
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        //허용할 헤더 설정
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        //쿠키 허용
        configuration.setAllowCredentials(true);
        //캐싱 시간 설정 (초 단위)
        configuration.setMaxAge(3600L);
        //새로운 UrlBasedCorsConfigurationSource 객체 생성
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //모든 경로에 대해 CORS 구성 등록
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}


