package com.jupjup.www.jupjup.service;

import com.jupjup.www.jupjup.dto.TokenDTO;
import com.jupjup.www.jupjup.entity.RefreshEntity;
import com.jupjup.www.jupjup.jwt.JWTUtil;
import com.jupjup.www.jupjup.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
// 단일 리프레시 토큰 전략과 유효 기간 설정 및 정리
public class AuthService {

    private final RefreshTokenRepository refreshRepository;
    public TokenDTO refreshTokenRotate(String refreshToken, String userEmail) {

        // DB에 유저정보 + refreshToken 정보 확인
        Optional<RefreshEntity> byUserEmail = refreshRepository.findByUserEmail(userEmail);
        if (byUserEmail.isPresent()) {
            RefreshEntity entity = byUserEmail.get();
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
        refreshRepository.save(RefreshEntity.builder()
                .refresh(newRefreshToken)
                .userEmail(userEmail)
                .expiration(JWTUtil.RefreshTokenExTimeCul(newRefreshToken))
                .build());
        log.info("New refresh token has been added");

        return TokenDTO.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

}
