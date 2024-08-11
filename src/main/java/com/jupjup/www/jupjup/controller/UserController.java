package com.jupjup.www.jupjup.controller;

import com.jupjup.www.jupjup.dto.UserDTO;
import com.jupjup.www.jupjup.repository.RefreshTokenRepository;
import com.jupjup.www.jupjup.service.JoinService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
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
@RequestMapping("/api/v1/user")
public class UserController {

    private final JoinService joinService;
    private final RefreshTokenRepository refreshTokenRepository;
    private static final List<String> SUPPORTED_PROVIDERS = Arrays.asList("google", "kakao", "naver");
    public static final String BASE_URL = "http://localhost:3000";

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