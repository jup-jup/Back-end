package com.jupjup.www.jupjup.user.service.refreshService;


import com.jupjup.www.jupjup.config.JWTUtil;
import com.jupjup.www.jupjup.user.entity.RefreshToken;
import com.jupjup.www.jupjup.user.repository.RefreshTokenRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RefreshReissueService {

    private final RefreshTokenRepository refreshRepository;


    public boolean refreshTokenReissue(String refreshToken, String userEmail, HttpServletResponse resp) throws IOException {

        List<RefreshToken> entity = refreshRepository.findByUserEmailAndRefreshToken(userEmail, refreshToken);
        try {
            if(entity.isEmpty()) return false;
            if (JWTUtil.validateRefreshToken(refreshToken)) {

            }
        } catch (ExpiredJwtException e) {
            resp.sendError(HttpStatus.FORBIDDEN.value());
        }

        Long userId = JWTUtil.getUserIdFromRefreshToken(refreshToken);
        String userName = JWTUtil.getUserNameFromRefreshToken(refreshToken);

        // 유저 권한 확인
//        String userRole = JWTUtil.getRoleFromRefreshToken(refreshToken);
        String providerId = entity.get(0).getProviderId();

        // 액세스 토큰 재발급 및 리프레시 토큰 rotate
        String newRefreshToken = JWTUtil.generateRefreshToken(userId, userName, userEmail);
        String newAccessToken = JWTUtil.generateAccessToken(userId, userName, userEmail);

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
        resp.addCookie(JWTUtil.getCookieFromRefreshToken(newRefreshToken));

        return true;
    }

}