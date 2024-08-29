package com.jupjup.www.jupjup.chat.dto;

import com.jupjup.www.jupjup.chat.entity.Chat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class ChatList {

    private Long id; // chat id
    private String content; // chat message 내용
    private Long userId; // 작성자 id
    private LocalDateTime created_at;

    public static ChatList toDTO(Chat chat) {
        return ChatList.builder()
                .id(chat.getId())
                .content(chat.getContent())
                .userId(chat.getUserId())
                .build();
    }

}
