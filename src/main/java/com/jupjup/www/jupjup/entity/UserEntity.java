package com.jupjup.www.jupjup.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter @Getter
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String userEmail;
    private String role;

    @Builder
    public UserEntity( String username, String userEmail, String role) {
        this.username = username;
        this.userEmail = userEmail;
        this.role = role;
    }

}