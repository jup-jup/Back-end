package com.jupjup.www.jupjup.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String providerId;

    @Column(nullable = false)
    private String refreshToken;

    @Column(nullable = false)
    private Date expiration;  // Updated to use LocalDateTime

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;



    @Builder
    public RefreshToken(String providerId ,String userEmail, String refreshToken, Date expiration) {
        this.providerId = providerId;
        this.userEmail = userEmail;
        this.refreshToken = refreshToken;
        this.expiration = expiration;
    }
}