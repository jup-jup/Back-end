package com.jupjup.www.jupjup.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@RequiredArgsConstructor
@Entity
@Table(name="refreshToken")
public class RefreshEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userEmail;
    private String refresh;
    private Date expiration;

    @Builder
    public RefreshEntity(String userEmail, String refresh, Date expiration) {
        this.userEmail = userEmail;
        this.refresh = refresh;
        this.expiration = expiration;
    }
}


