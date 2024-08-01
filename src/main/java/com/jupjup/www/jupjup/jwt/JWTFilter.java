package com.jupjup.www.jupjup.jwt;

import com.jupjup.www.jupjup.dto.UserDTO;
import com.jupjup.www.jupjup.oauth2.CustomUserDetails;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * JWT 검증 클래스
 */

@Slf4j
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private static final String BEARER_PREFIX = "Bearer ";
    private final JwtProperties jwtProperties;
    private final JWTUtil jwtUtil;
    private List<AntPathRequestMatcher> excludeMatchers;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        Pattern pattern = Pattern.compile("/swagger.*|/v3.*");
//        String path = request.getRequestURI();
//        log.info("path = {}", path);
//        boolean b = pattern.matcher(path).matches();
//        log.info("b {} " , b);
//        return pattern.matcher(path).matches();
        return request.getRequestURI().startsWith("/resources/");
    }

    private static final List<AntPathRequestMatcher> excludeUrls = List.of(
            new AntPathRequestMatcher("/templates/**"),
            new AntPathRequestMatcher("/loginForm"),
            new AntPathRequestMatcher("/loginError"),
            new AntPathRequestMatcher("/loginSuccess"),
            new AntPathRequestMatcher("/join"),
            new AntPathRequestMatcher("/auth/refresh"),
            new AntPathRequestMatcher("/auth/test"),
            new AntPathRequestMatcher("/logout"),
            new AntPathRequestMatcher("/swagger/**"),
            new AntPathRequestMatcher("/swagger-ui/**"),
            new AntPathRequestMatcher("/v3/api-docs/**")
    );

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        String requestUri = request.getRequestURI();
        log.info("Request URI: {}", requestUri);

        // 토큰 유효성 체크 불필요한 요청일 경우
        for (AntPathRequestMatcher matcher : excludeUrls) {
            if (matcher.matches(request)) {
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
        UserDTO userDTO = UserDTO.builder()
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