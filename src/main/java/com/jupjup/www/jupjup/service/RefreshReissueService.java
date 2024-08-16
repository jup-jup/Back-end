package com.jupjup.www.jupjup.service;


import com.jupjup.www.jupjup.config.JWTUtil;
import com.jupjup.www.jupjup.domain.entity.RefreshToken;
import com.jupjup.www.jupjup.domain.repository.RefreshTokenRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RefreshReissueService {

    private final RefreshTokenRepository refreshRepository;

    public boolean refreshTokenReissue(String refreshToken, String userEmail, HttpServletResponse resp) {

        List<RefreshToken> entity = refreshRepository.findByUserEmailAndRefreshToken(userEmail, refreshToken);

        log.info("entity={}", entity.stream().toList());

        // refresh 토큰 유효성 체크
        if (entity.isEmpty() ||JWTUtil.validateRefreshToken(refreshToken)) {
            log.error("refreshToken is null or empty");
            return false;
        }

        // 유저 권한 확인
        String userRole = JWTUtil.getRoleFromRefreshToken(refreshToken);
        String providerId = entity.get(0).getProviderId();

        // 액세스 토큰 재발급 및 리프레시 토큰 rotate
        String newRefreshToken = JWTUtil.generateRefreshToken(userEmail, userRole);
        String newAccessToken = JWTUtil.generateAccessToken(userEmail, userRole);

        // 기존 리프레시 토큰 삭제후 새로운 리프레시 토큰을 저장 (동시성 문제 해결을 위해 원자적으로 처리)
        refreshRepository.deleteAllByRefreshToken(refreshToken);
        // 리프레시 저장
        refreshRepository.save(RefreshToken.builder()
                .providerId(providerId)
                .refreshToken(newRefreshToken)
                .userEmail(userEmail)
                .expiration(JWTUtil.RefreshTokenExTimeCul(newRefreshToken))
                .build());
        log.info("New refresh token has been added");

        resp.addHeader("Authorization", "Bearer " + newAccessToken);
        resp.addCookie(JWTUtil.createCookie(newRefreshToken));

        return true;
    }

}