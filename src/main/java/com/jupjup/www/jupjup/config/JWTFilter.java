package com.jupjup.www.jupjup.config;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * JWT 검증 클래스
 */

@Slf4j
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private static final String BEARER_PREFIX = "Bearer ";
    private final JWTUtil jwtUtil;

    public List<String> list = List.of(
            "/login",
            "/api/v1/user",
            "/swagger",
            "/api-docs",
            "/health",
            "/ws",
            "/reissue",
            "/api/v1/giveaways/list",
            "/api/v1/giveaways/detail",
            "/api/v1/giveaways/search",
            "/api/v1/auth"
    );

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        log.info("request URI = {}", request.getRequestURI());

        // 1.토큰 유효성 체크 불필요한 요청일 경우
        for (String i : list) {
            if (request.getRequestURI().contains(i)) {
                log.info("JWT 유효성 검사 불필요 URI =  {}", request.getRequestURI());
                filterChain.doFilter(request, response);
                return;
            }
        }

        // 2.헤더에서 액세스 토큰 가져오기
        String authorization = request.getHeader("Authorization");

        // 3.액세스 토큰 null 체크
        if (authorization == null || !authorization.startsWith(BEARER_PREFIX)) {
            log.error("토큰이 비어 있거나, 형식이 올바르지 않습니다. ");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 4.토큰 유효성 검증
        try {
            String accessToken = authorization.substring(BEARER_PREFIX.length());
            if (JWTUtil.validateAccessToken(accessToken)) {
                log.info("토큰 유효성 검사 완료!");
                filterChain.doFilter(request, response);
                return;
            }
            log.error("토큰 유효성 검사 실패!");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } catch (ExpiredJwtException | IllegalArgumentException e) {
            log.info("액세스토큰 만료로 리프레시 토큰 발급합니다.");
            response.sendRedirect("/api/v1/auth/reissue");

        }

    }
}