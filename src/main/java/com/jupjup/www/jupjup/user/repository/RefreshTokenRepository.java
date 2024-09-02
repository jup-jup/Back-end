package com.jupjup.www.jupjup.user.repository;

import com.jupjup.www.jupjup.user.entity.RefreshToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

@Transactional
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

    void deleteAllByRefreshToken(String refreshToken);
    void deleteByExpirationBefore(Date expiration);
    void deleteByUserEmail(String userEmail);
    List<RefreshToken> findByUserEmailAndRefreshToken(String userEmail,String refreshToken);
}
