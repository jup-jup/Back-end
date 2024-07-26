package com.jupjup.www.jupjup.formLoginHandler;

import com.jupjup.www.jupjup.oauth2.CustomUserDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for(GrantedAuthority grantedAuthority : authorities){
            System.out.println("grantedAuthority.getAuthority() = " + grantedAuthority.getAuthority());
        }

            // 사용자의 주체 정보
        Object principal = authentication.getPrincipal();

        if(principal instanceof CustomUserDetails customOAuth2User){
            System.out.println("name = " + customOAuth2User.getName());
            System.out.println("username = " + customOAuth2User.getUsername());
            System.out.println("getPassword = " + customOAuth2User.getPassword());
            System.out.println("getAuthorities = " + customOAuth2User.getAuthorities());
        }

        // 자격 증명 (인증 후에는 보통 null로 설정됨)
        Object credentials = authentication.getCredentials();
        System.out.println("Credentials: " + credentials);

        // 인증 요청에 대한 추가 세부 정보
        Object details = authentication.getDetails();
        System.out.println("Details: " + details);

        // 인증 여부
        boolean isAuthenticated = authentication.isAuthenticated();
        System.out.println("Authenticated: " + isAuthenticated);

        // 성공 후 리다이렉트
        response.sendRedirect("/");

    }
}
