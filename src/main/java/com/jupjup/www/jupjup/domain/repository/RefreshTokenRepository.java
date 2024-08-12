package com.jupjup.www.jupjup.domain.repository;

import com.jupjup.www.jupjup.domain.entity.RefreshEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

@Transactional
public interface RefreshTokenRepository extends JpaRepository<RefreshEntity,Long> {

    void deleteAllByRefresh(String refresh);
    void deleteByExpirationBefore(Date expiration);
    Optional<RefreshEntity> findByUserEmail(String userEmail);
    void deleteByUserEmail(String userEmail);


}
