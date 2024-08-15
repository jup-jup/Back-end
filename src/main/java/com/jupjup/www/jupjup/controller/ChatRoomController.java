package com.jupjup.www.jupjup.controller;

import com.jupjup.www.jupjup.model.dto.chatRoom.CreateRoomRequest;
import com.jupjup.www.jupjup.model.dto.chatRoom.RoomListResponse;
import com.jupjup.www.jupjup.model.dto.chatRoom.RoomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat-rooms")
public class ChatRoomController {

    @GetMapping("/")
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

    @PostMapping("/")
    public ResponseEntity<?> createRoom(@RequestBody CreateRoomRequest request) {

        return ResponseEntity
                .ok()
                .build();
    }

}
