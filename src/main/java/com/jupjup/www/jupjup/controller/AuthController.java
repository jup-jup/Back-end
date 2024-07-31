package com.jupjup.www.jupjup.controller;

import com.jupjup.www.jupjup.dto.TokenDTO;
import com.jupjup.www.jupjup.entity.RefreshEntity;
import com.jupjup.www.jupjup.jwt.JWTUtil;
import com.jupjup.www.jupjup.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/refresh")
    @Operation(summary = "액세스 토큰 재발급",description = "만료 된 액세스 토큰을 재발급합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "액세스 토큰 재발급 완료", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenDTO.class))),
            @ApiResponse(responseCode = "401", description = "토큰 만료 재로그인 요망")
    })
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