package com.jupjup.www.jupjup.config;


import com.jupjup.www.jupjup.domain.repository.RefreshTokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenCleanupScheduler {

    private final RefreshTokenRepository refreshTokenRepository;

    public TokenCleanupScheduler(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
    public void removeExpiredTokens() {
        refreshTokenRepository.deleteByExpirationBefore(new Date());
    }

}
