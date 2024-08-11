package com.jupjup.www.jupjup.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupjup.www.jupjup.controller.UserController;
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
import java.net.URLEncoder;
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


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        //OAuth2User
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String userName = customUserDetails.getName();
        String userEmail = customUserDetails.getUserEmail();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();

        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        // jwt 발급
        String accessToken = JWTUtil.generateAccessToken(userName, role);
        String refreshToken = JWTUtil.generateRefreshToken(userName, role);

        // 리프레시 저장
        refreshTokenRepository.save(RefreshEntity.builder()
                .refresh(refreshToken)
                .userEmail(userEmail)
                .expiration(JWTUtil.RefreshTokenExTimeCul(refreshToken))
                .build());


        // HttpHeaders 객체 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        headers.add(HttpHeaders.SET_COOKIE, JWTUtil.createCookie(refreshToken).toString());

        // JSON 응답 생성
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("username", userName);
        responseBody.put("userEmail", userEmail);
        String jsonResponse = objectMapper.writeValueAsString(responseBody);

        // 응답 작성
        response.setContentType("application/json;charset=UTF-8"); // 한글 인코딩 꼭 작성해줘야 함
        response.getWriter().write(jsonResponse);

        // 쿼리 스트링에 포함할 값을 URL 인코딩합니다.
        String encodedUsername = URLEncoder.encode(userName, StandardCharsets.UTF_8);
        String encodedUserEmail = URLEncoder.encode(userEmail, StandardCharsets.UTF_8);
        String encodedAccessToken = URLEncoder.encode(accessToken, StandardCharsets.UTF_8);

        String redirectURL = UriComponentsBuilder.fromUriString(UserController.BASE_URL)
                .queryParam("access_token", encodedAccessToken)
                .queryParam("userEmail", encodedUserEmail)
                .queryParam("userName", encodedUsername)
                .build().toUriString();

        response.sendRedirect(redirectURL);

    }

}
