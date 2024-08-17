package com.jupjup.www.jupjup.controller;

import com.jupjup.www.jupjup.service.refreshService.RefreshReissueService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



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

    @Operation(summary = "리프레시 토큰 재발급 시 요청 api")
    @PostMapping("/reissue/{userEmail}")
    public ResponseEntity<String> reissue(@CookieValue("refreshToken") String refreshToken, @PathVariable String userEmail ,HttpServletResponse resp) {

        try {
            if (refreshReissueService.refreshTokenReissue(refreshToken, userEmail,resp)) {
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