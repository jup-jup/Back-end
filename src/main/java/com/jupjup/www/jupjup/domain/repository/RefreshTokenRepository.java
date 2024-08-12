package com.jupjup.www.jupjup.domain.repository;

import com.jupjup.www.jupjup.domain.entity.RefreshToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

    void deleteAllByRefreshToken(String refreshToken);
    void deleteByExpirationBefore(Date expiration);
    List<RefreshToken> findByUserEmail(String userEmail);
    void deleteByUserEmail(String userEmail);
    List<RefreshToken> findByUserEmailAndRefreshToken(String userEmail,String refreshToken);


}
