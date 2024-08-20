package com.jupjup.www.jupjup.model.dto.user;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private Long sequence;
    private String providerId;
    private String userEmail;
    private String username;
    private String password;
    private String role;
    private String picture;

    public UserResponse(String userEmail, String username, String password, String role) {
        this.userEmail = userEmail;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "sequence=" + sequence +
                ", providerId='" + providerId + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }

}