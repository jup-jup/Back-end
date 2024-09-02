package com.jupjup.www.jupjup.user.dto.user;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private Long sequence;
    private String providerId;
    private String userEmail;
    private Long userId;
    private String username;
    private String password;
    private String role;
    private String picture;

    public UserResponse(Long userId, String userEmail, String username, String password, String role) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.username = username;
        this.password = password;
        this.role = role;
    }

}