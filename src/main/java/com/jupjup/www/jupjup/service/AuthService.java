package com.jupjup.www.jupjup.service;

import com.jupjup.www.jupjup.model.dto.RefreshTokenResponse;
import com.jupjup.www.jupjup.config.JWTUtil;
import com.jupjup.www.jupjup.domain.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * @fileName      : AuthService.java
 * @author        : boramkim
 * @since         : 2024. 8. 12.
 * @description    :단일 리프레시 토큰 전략과 유효 기간 설정 및 정리
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthService {

    private final RefreshTokenRepository refreshRepository;



    /**
     * @author        : boramkim
     * @since         : 2024. 8. 12.
     * @description   : 리프레시 토큰 업데이트 및 액세스 토큰 재발급
     */
    public RefreshTokenResponse refreshTokenRotate(String refreshToken, String userEmail) {

        // DB에 유저정보 + refreshToken 정보 확인
        Optional<com.jupjup.www.jupjup.domain.entity.RefreshToken> byUserEmail = refreshRepository.findByUserEmail(userEmail);
        if (byUserEmail.isPresent()) {
            com.jupjup.www.jupjup.domain.entity.RefreshToken entity = byUserEmail.get();
            log.info("Refresh token found with expiration: {}" , entity.getExpiration());
            refreshRepository.deleteAllByRefresh(refreshToken);
            log.info("Refresh token has been deleted");
        }

        // 유저 권한 확인
        String userRole = JWTUtil.getRoleFromRefreshToken(refreshToken);

        // 액세스 토큰 재발급 및 리프레시 토큰 rotate
        String newRefreshToken = JWTUtil.generateRefreshToken(userEmail, userRole);
        String newAccessToken = JWTUtil.generateAccessToken(userEmail, userRole);

        // 기존 리프레시 토큰 삭제후 새로운 리프레시 토큰을 저장 (동시성 문제 해결을 위해 원자적으로 처리)
        refreshRepository.deleteAllByRefresh(refreshToken);
        // 리프레시 저장
        refreshRepository.save(com.jupjup.www.jupjup.domain.entity.RefreshToken.builder()
                .refresh(newRefreshToken)
                .userEmail(userEmail)
                .expiration(JWTUtil.RefreshTokenExTimeCul(newRefreshToken))
                .build());
        log.info("New refresh token has been added");

        return RefreshTokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

}
