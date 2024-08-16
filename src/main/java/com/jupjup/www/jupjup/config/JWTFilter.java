package com.jupjup.www.jupjup.config;

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

    public List<String> list = List.of("api/v1/user", "swagger", "api-docs","/" , "api");

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        // 토큰 유효성 체크 불필요한 요청일 경우
        for(String i : list){
            if(request.getRequestURI().contains(i)){
                filterChain.doFilter(request, response);
                return;
            }
         }

        String authorization = request.getHeader("Authorization");

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
        } catch (NullPointerException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
            return;
        } catch (ExpiredJwtException | IllegalArgumentException e) {
            response.sendRedirect("expire");
            return;
        }

        // 토큰에서 username, role 획득
        String username = JWTUtil.getUsernameFromAccessToken(accessToken);
        String role = JWTUtil.getRoleFromAccessToken(accessToken);

        // UserDTO 생성 및 값 설정
        UserResponse userDTO = UserResponse.builder()
                .username(username)
                .role(role)
                .build();

        // CustomUserDetails 에 회원 정보 객체 담기
        CustomUserDetails customOAuth2User = new CustomUserDetails(userDTO);
        // 스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
        // 세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);
        log.info("SecurityContextHolder 권한 등록 완료");
        filterChain.doFilter(request, response);
    }
}