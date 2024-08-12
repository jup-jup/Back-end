package com.jupjup.www.jupjup.model.dto;

import lombok.*;
import org.apache.catalina.User;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long sequence;
    private String id;
    private String userEmail;
    private String name;
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
        return "UserDTO{" +
                "id=" + id +
                ", userEmail='" + userEmail + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

}