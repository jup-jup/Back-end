package com.jupjup.www.jupjup.service.basicLogin;

import com.jupjup.www.jupjup.service.oauth.CustomUserDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
@Slf4j
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

//    private static final Logger log = LoggerFactory.getLogger(CustomSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            log.info("grantedAuthority.getAuthority() = {}", grantedAuthority.getAuthority());
        }

        // 사용자의 주체 정보
        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomUserDetails customOAuth2User) {
            log.info("name = {}", customOAuth2User.getName());
            log.info("username = {}", customOAuth2User.getUsername());
            log.info("getPassword = {}", customOAuth2User.getPassword());
            log.info("getAuthorities = {}", customOAuth2User.getAuthorities());
        }

        // 자격 증명 (인증 후에는 보통 null로 설정됨)
        Object credentials = authentication.getCredentials();
        log.info("Credentials: {}", credentials);

        // 인증 요청에 대한 추가 세부 정보
        Object details = authentication.getDetails();
        log.info("Details: {}", details);

        // 인증 여부
        boolean isAuthenticated = authentication.isAuthenticated();
        log.info("Authenticated: {}", isAuthenticated);

        // 성공 후 리다이렉트
        response.sendRedirect("/");
    }
}