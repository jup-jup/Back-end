package com.jupjup.www.jupjup.controller;

import com.jupjup.www.jupjup.domain.repository.RefreshTokenRepository;
import com.jupjup.www.jupjup.service.JoinService;
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
    public ResponseEntity<?> redirectToAuthorization(@RequestParam String provider) {
        if (SUPPORTED_PROVIDERS.contains(provider.toLowerCase())) {
            String authorizationUri = "/oauth2/authorize/" + provider.toLowerCase();
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(authorizationUri));
            return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER); // 303 See Other for redirection
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue("refreshToken") String refreshToken) {
        log.info("refreshToken: {}", refreshToken);
        refreshTokenRepository.deleteAllByRefreshToken(refreshToken);
        return ResponseEntity.ok("로그아웃 완료");
    }


}
