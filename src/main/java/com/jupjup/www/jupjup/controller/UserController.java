package com.jupjup.www.jupjup.controller;

import com.jupjup.www.jupjup.model.dto.UserResponse;
import com.jupjup.www.jupjup.domain.repository.RefreshTokenRepository;
import com.jupjup.www.jupjup.service.JoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @fileName      : UserController.java
 * @author        : boramkim
 * @since         : 2024. 8. 1.
 * @description    : 유저 로그인 api
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/user")
public class UserController {

    private final JoinService joinService;
    private final RefreshTokenRepository refreshTokenRepository;
    private static final List<String> SUPPORTED_PROVIDERS = Arrays.asList("google", "kakao", "naver");

//    @PostMapping("/join")
//    public ResponseEntity<?> join(@RequestBody UserResponse userDTO) {
//        String result = joinService.joinProcess(userDTO);
//        return ResponseEntity.ok(result);
//    }

    @GetMapping("/login")
    public RedirectView redirectToAuthorization(@RequestParam String provider) {
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
