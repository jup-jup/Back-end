package com.jupjup.www.jupjup.user.dto.user;

import com.jupjup.www.jupjup.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

// 다른 도메인 (chat, giveaway, ...) 등에서 사용하기 위한 DTO 입니다.
@Builder
@Getter
@Setter
public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserDTO of(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

}
