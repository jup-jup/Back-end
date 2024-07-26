package com.jupjup.www.jupjup.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String userEmail;
    private String role;

    @Builder
    public UserEntity( String username, String password, String userEmail, String role) {
        this.username = username;
        this.password = password;
        this.userEmail = userEmail;
        this.role = role;
    }

}