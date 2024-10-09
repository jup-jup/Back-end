package com.jupjup.www.jupjup.user.controller;

import com.jupjup.www.jupjup.config.security.JWTUtil;
import com.jupjup.www.jupjup.user.repository.RefreshTokenRepository;
import com.jupjup.www.jupjup.user.service.refreshService.RefreshReissueService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Arrays;
import java.util.List;


/**
 * @fileName      : AuthController.java
 * @author        : boramkim
 * @since         : 2024. 8. 1.
 * @description    : jwt 토큰 api
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {


    private final RefreshReissueService refreshReissueService;
    private final RefreshTokenRepository refreshTokenRepository;
    private static final List<String> SUPPORTED_PROVIDERS = Arrays.asList("google", "kakao", "naver");


    @Operation(summary = "소셜 로그인")
    @GetMapping("/login")
    public ResponseEntity<?> redirectToAuthorization(@RequestParam String provider) {
        if (SUPPORTED_PROVIDERS.contains(provider.toLowerCase())) {
            String authorizationUri = "/oauth2/authorize/" + provider.toLowerCase();
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(authorizationUri));
            return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER); // 303 See Other for redirection
        }
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String accessToken) {
        String userEmail = JWTUtil.parseUsernameFromToken(accessToken);
        refreshTokenRepository.deleteByUserEmail(userEmail);
        return ResponseEntity.ok("로그아웃 완료");
    }


    @Operation(summary = "리프레시 토큰 재발급 시 요청 api")
    @GetMapping("/reissue")
    public ResponseEntity<String> reissue(@CookieValue("refreshToken") String refreshToken,HttpServletResponse resp) {

        log.info("refreshToken = {} ", refreshToken);
        log.info("userEmail = {} ", JWTUtil.getUserEmailFromRefreshToken(refreshToken));

        try {
            if (refreshReissueService.refreshTokenReissue(refreshToken, JWTUtil.getUserEmailFromRefreshToken(refreshToken),resp)) {
                return ResponseEntity.ok("액세스 토큰 재발급 완료");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("토큰 유효기간 만료 재 로그인 하세요.");
            }
        } catch (Exception e) {
            log.error("토큰 재발급 중 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("일시적인 서버 장애");
        }
    }

}