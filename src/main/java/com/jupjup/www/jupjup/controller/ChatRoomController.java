package com.jupjup.www.jupjup.controller;

import com.jupjup.www.jupjup.domain.entity.chat.Chat;
import com.jupjup.www.jupjup.domain.entity.chat.Room;
import com.jupjup.www.jupjup.model.dto.chatRoom.CreateRoomRequest;
import com.jupjup.www.jupjup.model.dto.chatRoom.CreateRoomResponse;
import com.jupjup.www.jupjup.model.dto.chatRoom.RoomListResponse;
import com.jupjup.www.jupjup.model.dto.chatRoom.RoomResponse;
import com.jupjup.www.jupjup.service.chat.RoomService;
import com.jupjup.www.jupjup.service.oauth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
// TODO: 전체 서버 관점에서 /api/v1 prefix 를 사용한다면 application.yml 에서 설정하는 것이 좋아보임
@RequestMapping("/api/v1/chat-rooms")
public class ChatRoomController {

    private final RoomService roomService;

    @PostMapping("")
    public ResponseEntity<?> createRoom(@RequestBody CreateRoomRequest request, Authentication authentication) {

        try {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            CreateRoomResponse roomDTO = roomService.create(request, customUserDetails.getUserEmail());

            return ResponseEntity
                    .ok()
                    .body(roomDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<List<RoomListResponse>> getRooms() {

        return ResponseEntity
                .ok()
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getRoom(@PathVariable Long id) {

        return ResponseEntity
                .ok()
                .build();
    }

}
