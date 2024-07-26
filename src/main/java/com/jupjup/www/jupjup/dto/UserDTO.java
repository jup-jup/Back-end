package com.jupjup.www.jupjup.dto;

import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String userEmail;
    private String username;
    private String password;
    private String role;

    public UserDTO(String userEmail, String username, String password, String role) {
        this.userEmail = userEmail;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", userEmail='" + userEmail + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}