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
    private String userName;

    public UserResponse(Long userId, String userEmail, String userName) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userName = userName;
    }

}