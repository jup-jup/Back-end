package com.jupjup.www.jupjup.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Setter @Getter
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String providerKey;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String userEmail;
    @Column(nullable = false)
    private String role;
    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        updatedAt = createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Builder
    public UserEntity( String providerKey,String username, String userEmail, String role) {
        this.providerKey = providerKey;
        this.name = username;
        this.userEmail = userEmail;
        this.role = role;
    }

}