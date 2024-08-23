package com.jupjup.www.jupjup.service.oauth;

import com.jupjup.www.jupjup.config.JWTUtil;
import com.jupjup.www.jupjup.domain.entity.RefreshToken;
import com.jupjup.www.jupjup.domain.enums.BaseUrl;
import com.jupjup.www.jupjup.domain.repository.RefreshTokenRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Iterator;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomOAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // OAuth2User
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        Long userId = customUserDetails.getUserId();
        String userName = customUserDetails.getName();
        String userEmail = customUserDetails.getUserEmail();
        String providerId = customUserDetails.getProviderId();

        log.info("providerId: {}", providerId);

        // 권한 (USER, ADMIN)
        Iterator<? extends GrantedAuthority> iterator =  authentication.getAuthorities().iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        // JWT 발급
        String accessToken = JWTUtil.generateAccessToken(userId, userName, userEmail, role);
        String refreshToken = JWTUtil.generateRefreshToken(userId, userName, userEmail, role);

        // 리프레시 토큰 저장
        refreshTokenRepository.save(RefreshToken.builder()
                .providerId(providerId)
                .refreshToken(refreshToken)
                .userEmail(userEmail)
                .expiration(JWTUtil.RefreshTokenExTimeCul(refreshToken))
                .build());

        // JWTUtil을 통해 쿠키 생성 및 설정
        Cookie refreshTokenCookie = JWTUtil.getCookieFromRefreshToken(refreshToken);
        Cookie accessTokenCookie = JWTUtil.getCookieFromAccessToken(accessToken);

        // 응답에 쿠키 추가
        response.addCookie(refreshTokenCookie);
        response.addCookie(accessTokenCookie);

        // 리다이렉트할 URL 설정
        String redirectURL = BaseUrl.REACT.getUrl();

        // 리다이렉트
        response.sendRedirect(redirectURL);
    }
}