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
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;
import java.util.List;

/**
 * @fileName      : UserController.java
 * @author        : boramkim
 * @since         : 2024. 8. 1.
 * @description    : 로그인,로그아웃,회원가입 api
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class UserController {

    private final JoinService joinService;
    private final RefreshTokenRepository refreshTokenRepository;
    private static final List<String> SUPPORTED_PROVIDERS = Arrays.asList("google", "kakao", "naver");

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody UserDTO userDTO) {
        String result = joinService.joinProcess(userDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/login")
    public RedirectView redirectToAuthorization(@RequestParam(required = false, defaultValue = "null") String provider) {
        if (!SUPPORTED_PROVIDERS.contains(provider.toLowerCase())) {
            throw new IllegalArgumentException("Unsupported OAuth2 provider: " + provider);
        }
        log.info("provider : {}", provider);
        String authorizationUri = "/oauth2/authorize/" + provider.toLowerCase();
        return new RedirectView(authorizationUri);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam String userEmail) {
        refreshTokenRepository.deleteByUserEmail(userEmail);
        return ResponseEntity.ok("로그아웃 완료");
    }
}