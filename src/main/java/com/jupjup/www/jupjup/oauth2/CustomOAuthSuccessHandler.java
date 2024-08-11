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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Ref;
import java.util.*;

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

        // OAuth2User
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String username = customUserDetails.getName();
        String userEmail = customUserDetails.getUserEmail();

        // JWT 발급
        String accessToken = JWTUtil.generateAccessToken(username, "ROLE_USER");
        String refreshToken = JWTUtil.generateRefreshToken(username, "ROLE_USER");

        // Refresh Token 저장
        refreshTokenRepository.save(RefreshEntity.builder()
                .refresh(refreshToken)
                .userEmail(userEmail)
                .expiration(JWTUtil.RefreshTokenExTimeCul(refreshToken))
                .build());

        // Refresh 쿠키 생성
        JWTUtil.createCookie(refreshToken);

        // 클라이언트로 리다이렉트할 URL 생성
        String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:3000/")
                .queryParam("accessToken", accessToken)
                .queryParam("username", username)
                .queryParam("userEmail", userEmail)
                .build().toUriString();

        // 클라이언트로 리다이렉트
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

}
