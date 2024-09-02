package com.jupjup.www.jupjup.service.oauth;

import com.jupjup.www.jupjup.config.JWTUtil;
import com.jupjup.www.jupjup.domain.entity.RefreshToken;
import com.jupjup.www.jupjup.domain.enums.BaseUrl;
import com.jupjup.www.jupjup.user.repository.RefreshTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

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

        Iterator<? extends GrantedAuthority> iterator =  authentication.getAuthorities().iterator();
        GrantedAuthority auth = iterator.next();

        // JWT 발급
        String accessToken = JWTUtil.generateAccessToken(userId, userName, userEmail);
        log.info("accessToken = {}",accessToken);
        String refreshToken = JWTUtil.generateRefreshToken(userId, userName, userEmail);

        // 리프레시 토큰 저장
        refreshTokenRepository.save(RefreshToken.builder()
                .providerId(providerId)
                .refreshToken(refreshToken)
                .userEmail(userEmail)
                .expiration(JWTUtil.RefreshTokenExTimeCul(refreshToken))
                .build());

//        // JWTUtil 을 통해 쿠키 생성 및 설정
//        Cookie refreshTokenCookie = JWTUtil.getCookieFromRefreshToken(refreshToken);
//        Cookie accessTokenCookie = JWTUtil.getCookieFromAccessToken(accessToken);
//        // 응답에 쿠키 추가
//        response.addCookie(refreshTokenCookie);
//        response.addCookie(accessTokenCookie);
//        response.sendRedirect(redirectURL);

        String redirectURL = BaseUrl.REACT.getUrl();
        String targetUrl = UriComponentsBuilder.fromUriString(redirectURL)
                .queryParam("accessToken", accessToken)
                .queryParam("refreshToken", refreshToken)
                .build().toString();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);

    }
}