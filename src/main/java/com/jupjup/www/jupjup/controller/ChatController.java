package com.jupjup.www.jupjup.controller;

import com.jupjup.www.jupjup.model.dto.chat.CreateChatRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/chat-rooms/{roomId}/chats") // 메인은 채팅인데 채팅 방을 기준으로 가는 URL 이 괜찮을지..
public class ChatController {

    @GetMapping("/{id}")
    public ResponseEntity<?> getChat(@PathVariable Long roomId, @PathVariable Long id) {

        return ResponseEntity
                .ok()
                .build();
    }

    @PostMapping("/")
    public ResponseEntity<?> createChat(@PathVariable Long roomId,
                                        @RequestBody CreateChatRequest request) {

        return ResponseEntity
                .created(URI.create("/rooms/{roomId}/chats/{chatId}"))
                .build();
    }

}
