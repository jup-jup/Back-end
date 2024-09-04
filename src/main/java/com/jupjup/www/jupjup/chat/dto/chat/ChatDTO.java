package com.jupjup.www.jupjup.chat.dto.chat;

import com.jupjup.www.jupjup.chat.entity.Chat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class ChatDTO {

    private Long id;
    private Long roomId;
    private Long userId;
    private String content;
    private LocalDateTime createdAt;

    public static ChatDTO of(Chat chat) {
        return ChatDTO.builder()
                .id(chat.getId())
                .roomId(chat.getRoomId())
                .userId(chat.getUserId())
                .content(chat.getContent())
                .createdAt(chat.getCreatedAt())
                .build();
    }

}
