package com.jupjup.www.jupjup.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupjup.www.jupjup.user.entity.RefreshToken;
import com.jupjup.www.jupjup.user.oauth.CustomUserDetails;
import com.jupjup.www.jupjup.user.repository.RefreshTokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/* UsernamePasswordAuthenticationFilter
사용자의 사용자명(username)과 비밀번호(password)를 통해 인증(authentication) 과정을 처리합니다.
이 필터는 일반적으로 사용자가 로그인 요청을 보낼 때 실행되며, 다음과 같은 역할을 수행합니다:
1.	로그인 요청 처리: 로그인 폼에서 전송된 사용자명과 비밀번호를 수신합니다.
2.	인증 토큰 생성: 사용자의 입력을 기반으로 UsernamePasswordAuthenticationToken을 생성합니다.
3.	인증 관리: 생성된 토큰을 AuthenticationManager에 전달하여 사용자가 유효한지 확인합니다.
4.	인증 성공 처리: 인증이 성공하면, 사용자를 인증된 상태로 설정하고, 성공 핸들러를 호출하여 적절한 페이지로 리다이렉션합니다.
5.	인증 실패 처리: 인증이 실패하면, 실패 핸들러를 호출하여 에러 메시지를 보여주거나 로그인 페이지로 다시 리다이렉션합니다

간략한 예시 흐름
1.	사용자가 로그인 폼을 통해 사용자명과 비밀번호를 제출합니다.
2.	UsernamePasswordAuthenticationFilter가 요청을 가로채서 사용자명과 비밀번호를 추출합니다.
3.	추출된 정보로 UsernamePasswordAuthenticationToken을 생성합니다.
4.	생성된 토큰을 AuthenticationManager에 전달하여 인증을 시도합니다.
5.	인증이 성공하면, 성공 핸들러를 통해 사용자를 환영하는 페이지로 리다이렉션합니다.
6.	인증이 실패하면, 실패 핸들러를 통해 에러 메시지를 표시합니다..*/
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RefreshTokenRepository refreshRepository;


    @Override
    // 실제로 인증을 시도하는 핵심 메서드로, 인증이 성공하면 Authentication 객체를 반환하고, 실패하면 AuthenticationException 을 던집니다.
    public Authentication attemptAuthentication(@RequestBody HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // POST 요청이 아니면 throw Exception
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        if ("application/json".equals(request.getContentType())) {
            try {
                Map requestBody = objectMapper.readValue(request.getInputStream(), Map.class);
                Object userEmail = requestBody.get("userEmail");
                Object password = requestBody.get("password");
                log.info("attemptAuthentication() userEmail: {}", userEmail);
                // 사용자명과 비밀번호로 AuthenticationToken 생성
                // AuthenticationManager 에 토큰을 전달하여 인증 시도
                // 인증이 성공하면 Authentication 객체를 반환, 실패하면 예외를 던짐
                // CustomUserDetailsService 의 loadUserByUsername() 메서드와 상호작용하여 DB 에서 사용자 정보를 조회하고 검증
                // attemptAuthentication() -> authenticationManager.authenticate(authToken) -> CustomUserDetailsService.loadUserByUsername()
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userEmail, password);
                return authenticationManager.authenticate(authToken);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return super.attemptAuthentication(request, response);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        CustomUserDetails userDetails = (CustomUserDetails) authResult.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        Long userId = userDetails.getUserId();
        String userName = userDetails.getName();
        String userEmail = userDetails.getUserEmail();
        log.info("successfulAuthentication() userName: {}, userEmail : {}, ", userName, userEmail);

        GrantedAuthority auth = authorities.stream().findFirst()
                .orElseThrow(() -> new RuntimeException("No authorities found"));

        // 토큰 생성
        String accessToken = JWTUtil.generateAccessToken(userId, userName, userEmail);
        String refreshToken = JWTUtil.generateRefreshToken(userId, userName, userEmail);
        log.info("refreshToken = {}", refreshToken);

        // 리프레시 토큰을 데이터베이스에 저장
        RefreshToken refreshEntity = RefreshToken.builder()
                .userEmail(userEmail)
                .refreshToken(refreshToken)
                .expiration(new Date(System.currentTimeMillis() + JWTUtil.refreshExpirationTime))
                .build();

        refreshRepository.save(refreshEntity);

        response.addCookie(JWTUtil.getCookieFromRefreshToken(refreshToken));
        response.addHeader("Authorization", "Bearer " + accessToken);
        response.setStatus(HttpServletResponse.SC_OK);
    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

}
