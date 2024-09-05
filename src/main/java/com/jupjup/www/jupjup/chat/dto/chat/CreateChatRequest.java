package com.jupjup.www.jupjup.chat.dto.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateChatRequest {

    private String content; // 채팅 메시지
    // TODO: 파일 이미지 올리는 기능 필요하다면 파일 id 추가

}
