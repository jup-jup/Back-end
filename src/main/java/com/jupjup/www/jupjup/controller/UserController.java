package com.jupjup.www.jupjup.controller;

import com.jupjup.www.jupjup.dto.UserDTO;
import com.jupjup.www.jupjup.repository.RefreshTokenRepository;
import com.jupjup.www.jupjup.service.JoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class UserController {

    private final JoinService joinService;
    private final RefreshTokenRepository refreshTokenRepository;

    @PostMapping("/join")
    @Operation(summary = "회원가입", description = "새로운 사용자를 등록합니다.")
    @ApiResponse(responseCode = "200", description = "회원가입 완료", content = @Content(schema = @Schema(implementation = String.class)))
    public ResponseEntity<?> join(@RequestBody UserDTO userDTO) {
        joinService.joinProcess(userDTO);
        return ResponseEntity.ok("회원가입완료");
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "사용자를 로그아웃 처리하고, 리프레시 토큰을 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "로그아웃 완료", content = @Content(schema = @Schema(implementation = String.class)))
    public ResponseEntity<?> logout(@RequestParam String userEmail) {
        refreshTokenRepository.deleteByUserEmail(userEmail);
        return ResponseEntity.ok("로그아웃 완료");
    }
}