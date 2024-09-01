package com.jupjup.www.jupjup.config;

import com.jupjup.www.jupjup.common.response.ErrorResponse;
import com.jupjup.www.jupjup.model.dto.user.UserResponse;
import com.jupjup.www.jupjup.service.oauth.CustomUserDetails;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public List<String> list = List.of
            ("/login",
                    "/api/v1/user",
                    "/swagger",
                    "/api-docs",
                    "/reissue",
                    "/api/v1/giveaways/list",
                    "/api/v1/giveaways/detail/",
                    "/health");

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        // 1.토큰 유효성 체크 불필요한 요청일 경우
        for (String i : list) {
            if (request.getRequestURI().contains(i)) {
                log.info("JWT 유효성 검사 불필요 URI =  {}",request.getRequestURI());
                filterChain.doFilter(request, response);
                return;
            }
        }

        // 2.헤더에서 액세스 토큰 가져오기
        String authorization = request.getHeader("Authorization");
        log.info("authorization = {}", authorization);

        // 3.액세스토큰 검증
        if (authorization == null || !authorization.startsWith(BEARER_PREFIX)) {
            log.error("토큰이 비어 있거나, 형식이 올바르지 않습니다. ");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String accessToken = authorization.substring(BEARER_PREFIX.length());

        // 4.토큰 유효성 검증
        try {
            if (!JWTUtil.validateAccessToken(accessToken)) {
                log.error("토큰이 만료되었습니다.");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        } catch (ExpiredJwtException | IllegalArgumentException e) {
            response.sendRedirect("/api/v1/auth/reissue");
            return;
        }
        filterChain.doFilter(request, response);
    }
}