package com.jupjup.www.jupjup.controller;

import com.jupjup.www.jupjup.dto.TokenDTO;
import com.jupjup.www.jupjup.entity.RefreshEntity;
import com.jupjup.www.jupjup.jwt.JWTUtil;
import com.jupjup.www.jupjup.servicr.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class JwtController {

    private final AuthService authService;

    @Operation(summary = "access 토큰 재발급")
    @PostMapping("/refresh")
    public ResponseEntity<?> reissue(@CookieValue("refreshToken") String refreshToken , @RequestBody RefreshEntity refreshEntity
    ,HttpServletResponse resp) {

        System.out.println("email " + refreshEntity.getUserEmail());

        // 토큰 유효성 체크
        if(refreshToken == null || refreshToken.isEmpty() || JWTUtil.validateRefreshToken(refreshToken)) {
            log.error("refreshToken is null or empty");
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("토큰 만료 재로그인 요망.");
        }

        // 토큰 재발급
        TokenDTO tokenDTO = authService.refreshTokenRotate(refreshToken, refreshEntity.getUserEmail());
        resp.addHeader("Authorization" ,"Bearer "+ tokenDTO.getAccessToken());
        resp.addCookie(JWTUtil.createCookie(tokenDTO.getRefreshToken()));

        return ResponseEntity.ok("ok");

    }

    @GetMapping("/test")
    public ResponseEntity<?> getUser() {
        throw new NullPointerException();
    }
}