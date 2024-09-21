package com.jupjup.www.jupjup.user.oauth;

import com.jupjup.www.jupjup.config.JWTUtil;
import com.jupjup.www.jupjup.user.entity.RefreshToken;
import com.jupjup.www.jupjup.common.enums.BaseUrl;
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

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        Long userId = customUserDetails.getUserId();
        log.info("userId = {}", userId);
        String userName = customUserDetails.getName();
        String userEmail = customUserDetails.getUserEmail();
        String providerId = customUserDetails.getProviderId();

        // JWT 발급
        String accessToken = JWTUtil.generateAccessToken(userId, userName, userEmail);
        log.info("accessToken = {}",accessToken);
        String refreshToken = JWTUtil.generateRefreshToken(userId, userName, userEmail);
        log.info("refreshToken = {}",refreshToken);


        // 리프레시 토큰 저장
        refreshTokenRepository.save(RefreshToken.builder()
                .providerId(providerId)
                .refreshToken(refreshToken)
                .userEmail(userEmail)
                .expiration(JWTUtil.RefreshTokenExTimeCul(refreshToken))
                .build());

        // TODO : 더 보안성을 높여 전달하는 방법은 없을까 ? 프론트가 SSL 설정해도 안되는데
        String redirectURL = BaseUrl.REACT.getUrl();
        String targetUrl = UriComponentsBuilder.fromUriString(redirectURL)
                .queryParam("accessToken", accessToken)
                .queryParam("refreshToken", refreshToken)
                .build().toString();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);

    }
}