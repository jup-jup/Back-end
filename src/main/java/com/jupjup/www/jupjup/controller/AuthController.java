package com.jupjup.www.jupjup.controller;

import com.jupjup.www.jupjup.model.dto.TokenDTO;
import com.jupjup.www.jupjup.domain.entity.RefreshEntity;
import com.jupjup.www.jupjup.config.JWTUtil;
import com.jupjup.www.jupjup.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;

    public ResponseEntity<?> reissue(@CookieValue("refreshToken") String refreshToken , @RequestBody RefreshEntity refreshEntity
            ,HttpServletResponse resp) {

        System.out.println("email " + refreshEntity.getUserEmail());

        // 토큰 유효성 체크
        if(refreshToken == null || refreshToken.isEmpty() || JWTUtil.validateRefreshToken(refreshToken)) {
            log.error("refreshToken is null or empty");
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("토큰 만료 재로그인 요망");
        }

        // 토큰 재발급
        TokenDTO tokenDTO = authService.refreshTokenRotate(refreshToken, refreshEntity.getUserEmail());
        resp.addHeader("Authorization" ,"Bearer "+ tokenDTO.getAccessToken());
        resp.addCookie(JWTUtil.createCookie(tokenDTO.getRefreshToken()));

        return ResponseEntity.ok("액세스 토큰 재발급 완료");

    }

}