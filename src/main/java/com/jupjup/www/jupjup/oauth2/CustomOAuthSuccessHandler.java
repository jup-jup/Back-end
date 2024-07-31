package com.jupjup.www.jupjup.oauth2;

import com.jupjup.www.jupjup.jwt.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

/*로그인 성공 시 호출 페이지*/
@Component
@RequiredArgsConstructor
@Slf4j
public class CustomOAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler  {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        //OAuth2User
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        log.info("Custom User : {}", customUserDetails);

        String username = customUserDetails.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();

        GrantedAuthority auth = iterator.next();

        log.info("유저 ROLE : {}", auth.getAuthority());
        String role = auth.getAuthority();

        // jwt 발급
        String accessToken = JWTUtil.generateAccessToken(username, role);
        String RefreshToken = JWTUtil.generateRefreshToken(username, role);

        ResponseCookie responseCookie = ResponseCookie.from("refresh_token", RefreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("strict")
                .build();

        response.setHeader("Authorization", "Bearer " +accessToken);
        response.addHeader("Set-Cookie", responseCookie.toString());
        response.sendRedirect("http://localhost:8080/user");

    }

}
