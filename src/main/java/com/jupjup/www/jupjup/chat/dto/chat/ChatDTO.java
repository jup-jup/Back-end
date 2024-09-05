package com.jupjup.www.jupjup.chat.dto.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jupjup.www.jupjup.chat.entity.Chat;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class ChatDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("room_id")
    private Long roomId;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("content")
    private String content;

    @JsonProperty("created_at")
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
