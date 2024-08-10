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
        log.info("Role : {}", role);

        // jwt 발급
        String accessToken = JWTUtil.generateAccessToken(username, "ROLE_USER");
        String refreshToken = JWTUtil.generateRefreshToken(username, "ROLE_USER");

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
        responseBody.put("username", username);
        responseBody.put("userEmail", userEmail);
        String jsonResponse = objectMapper.writeValueAsString(responseBody);

        // 응답 작성
        response.setContentType("application/json;charset=UTF-8"); // 한글 인코딩 꼭 작성해줘야 함
        response.getWriter().write(jsonResponse);


    }

}
