package com.jupjup.www.jupjup.service.oauth;

import com.jupjup.www.jupjup.config.JWTUtil;
import com.jupjup.www.jupjup.domain.entity.RefreshToken;
import com.jupjup.www.jupjup.domain.enums.BaseUrl;
import com.jupjup.www.jupjup.domain.repository.RefreshTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/*로그인 성공 시 호출 페이지*/
@Component
@RequiredArgsConstructor
@Slf4j
public class CustomOAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final RefreshTokenRepository refreshTokenRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        //OAuth2User
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
            log.info("oauthToken: {}", oauthToken.getPrincipal());
            log.info("oauthToken.getAuthorizedClientRegistrationId() = {}", oauthToken.getAuthorizedClientRegistrationId());
        }
        String userName = customUserDetails.getName();
        String userEmail = customUserDetails.getUserEmail();
        String providerId = customUserDetails.getProviderId();
        log.info("providerId: {}", providerId);

        // 권한 (USER,ADMIN)
        Iterator<? extends GrantedAuthority> iterator =  authentication.getAuthorities().iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        // jwt 발급
        String accessToken = JWTUtil.generateAccessToken(userName, userEmail, role);
        String refreshToken = JWTUtil.generateRefreshToken(userName, userEmail, role);

        // 리프레시 저장
        refreshTokenRepository.save(RefreshToken.builder()
                .providerId(providerId)
                .refreshToken(refreshToken)
                .userEmail(userEmail)
                .expiration(JWTUtil.RefreshTokenExTimeCul(refreshToken))
                .build());


        // 액세스,리프레시 로그인 1회성 쿠키에 넣어 발송
        response.addCookie(JWTUtil.getCookieFromRefreshToken(refreshToken));
        response.addCookie(JWTUtil.getCookieFromAccessToken(accessToken));

        // 리다이렉트할 URL 설정
        String redirectURL = BaseUrl.REACT.getUrl();

        log.info("redirect url is {}", redirectURL);
        log.info("accessToken: {}", accessToken);

        // 리다이렉트
        response.sendRedirect(redirectURL);

    }

}
