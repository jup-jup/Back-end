package com.jupjup.www.jupjup.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupjup.www.jupjup.entity.RefreshEntity;
import com.jupjup.www.jupjup.jwt.JWTUtil;
import com.jupjup.www.jupjup.repository.RefreshTokenRepository;
import com.nimbusds.oauth2.sdk.token.RefreshToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Ref;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

/*로그인 성공 시 호출 페이지*/
@Component
@RequiredArgsConstructor
@Slf4j
public class CustomOAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler  {

    private final RefreshTokenRepository refreshTokenRepository;
    RefreshTokenRepository tokenRepository;
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        //OAuth2User
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        log.info("Custom User : {}", customUserDetails);

        String username = customUserDetails.getName();
        String userEmail = customUserDetails.getUserEmail();

        log.info("Username : {}", username);
        log.info("UserEmail: {}", customUserDetails.getUserEmail());

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();

        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        // jwt 발급
        String accessToken = JWTUtil.generateAccessToken(username, "ROLE_USER");
        String RefreshToken = JWTUtil.generateRefreshToken(username, "ROLE_USER");

        // 리프레시 저장
        refreshTokenRepository.save(RefreshEntity.builder()
                .refresh(RefreshToken)
                .userEmail(userEmail)
                .expiration(JWTUtil.RefreshTokenExTimeCul(RefreshToken))
                .build());

        response.setHeader("Authorization", "Bearer " +accessToken);
        response.addHeader("Set-Cookie", JWTUtil.createCookie(RefreshToken).toString());
        response.getWriter().write(username);
        response.getWriter().write(userEmail);
//        response.sendRedirect("http://www.jupjup.store:8080/");

    }

}
