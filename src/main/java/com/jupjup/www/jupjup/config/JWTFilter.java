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

    public List<String> list = List.of("/login", "/api/v1/user", "/swagger", "/api-docs", "/reissue");

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        log.info("request.getRequestURI() = {}", request.getRequestURI());
        // 토큰 유효성 체크 불필요한 요청일 경우
        for (String i : list) {
            if (request.getRequestURI().contains(i)) {
                log.info("JWT 유효성 검사 URL이 아닙니다.");
                filterChain.doFilter(request, response);
                return;
            }
        }

        log.info("JWT 유효성 검사 진행 URL");

        String authorization = request.getHeader("Authorization");
        log.info("authorization = {}", authorization);
        // Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith(BEARER_PREFIX)) {
            log.error("Token is null or does not start with 'Bearer ' | className = {}", getClass());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String accessToken = authorization.substring(BEARER_PREFIX.length());

        // 토큰 유효성 검증
        try {
            JWTUtil.validateAccessToken(accessToken);
            // 액세스 토큰 만료시 리프레시토큰으로 재발급
        } catch (ExpiredJwtException | IllegalArgumentException e) {
            response.sendRedirect("/api/v1/auth/reissue");
            return;
        }

        // TODO : 아래 부분 필요한지 체크
        // 토큰에서 username, role 획득
//        String userName = JWTUtil.getUserNameFromAccessToken(accessToken);
//        String userEmail = JWTUtil.getUserEmailFromAccessToken(accessToken);
//        String role = JWTUtil.getRoleFromAccessToken(accessToken);
//        UserResponse userDTO = UserResponse.builder()
//                .username(userName)
//                .userEmail(userEmail)
//                .role(role)
//                .build();
//
//        // UserDTO 생성 및 값 설정
//
//        // CustomUserDetails 에 회원 정보 객체 담기
//        CustomUserDetails customOAuth2User = new CustomUserDetails(userDTO);
//        // 스프링 시큐리티 인증 토큰 생성
//        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
//        // 세션에 사용자 등록
//        SecurityContextHolder.getContext().setAuthentication(authToken);
//        log.info("SecurityContextHolder 권한 등록 완료");
        filterChain.doFilter(request, response);
    }
}