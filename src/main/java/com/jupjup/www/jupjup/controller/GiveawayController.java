package com.jupjup.www.jupjup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class GiveawayController {
    // TODO: API 별 DTO 정의

    // 나눔 리스트
    @GetMapping("/giveaways")
    public ResponseEntity<?> getList() {

        return ResponseEntity
                .ok()
                .build();
    }

    // 나눔 상세 페이지
    @GetMapping("/giveaways/{id}")
    public ResponseEntity<?> getGiveaway(@PathVariable Long id) {

        return ResponseEntity
                .ok()
                .build();
    }

    // 나눔 올리기
    @PostMapping("/giveaways")
    public ResponseEntity<?> addGiveaway() {

        return ResponseEntity
                .ok()
                .build();
    }

    // 나눔 업데이트
    @PutMapping("/giveaways/{id}")
    public ResponseEntity<?> updateGiveaway(@PathVariable Long id) {

        return ResponseEntity
                .ok()
                .build();
    }

    // 나눔 업데이트
    @DeleteMapping("/giveaways/{id}")
    public ResponseEntity<?> deleteGiveaway(@PathVariable Long id) {

        return ResponseEntity
                .ok()
                .build();
    }

    // 나눔 아이템 채팅방 리스트 (판매자만 확인 가능)
    @GetMapping("/giveaways/{giveawayId}/chat-rooms")
    public ResponseEntity<?> getGiveawayChatRooms(@PathVariable Long giveawayId) {
        // TODO: validation - 본인이 올린 아이템인지

        return ResponseEntity
                .ok()
                .build();
    }

    // 나눔 아이템 채팅방 입장. 채팅 리스트 불러오기
    @GetMapping("/giveaways/{giveawayId}/chat-rooms/{chatRoomId}")
    public ResponseEntity<?> getChats(@PathVariable Long giveawayId, @PathVariable Long chatRoomId) {
        // TODO: 채팅방이 없다면 생성도 이쪽에서 해야할까?

        return ResponseEntity
                .ok()
                .build();
    }

}
