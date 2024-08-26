package com.jupjup.www.jupjup.controller;

import com.jupjup.www.jupjup.model.dto.chatRoom.CreateRoomRequest;
import com.jupjup.www.jupjup.model.dto.chatRoom.CreateRoomResponse;
import com.jupjup.www.jupjup.model.dto.chatRoom.RoomListResponse;
import com.jupjup.www.jupjup.model.dto.chatRoom.RoomResponse;
import com.jupjup.www.jupjup.service.chat.RoomService;
import com.jupjup.www.jupjup.service.oauth.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Array;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Chat Room", description = "채팅방 API")
@RequiredArgsConstructor
@RestController
// TODO: 전체 서버 관점에서 /api/v1 prefix 를 사용한다면 application.yml 에서 설정하는 것이 좋아보임
@RequestMapping("/api/v1/chat-rooms")
public class ChatRoomController {

    private final RoomService roomService;

    @Operation(summary = "create room", description = "채팅방 생성 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreateRoomResponse.class))),
            @ApiResponse(responseCode = "401", description = "잘못된 유저입니다.")
    })
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

    @Operation(summary = "get rooms", description = "채팅방 목록 API. 해당 유저의 채팅 목록 전체를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RoomListResponse.class)))),
            @ApiResponse(responseCode = "401", description = "잘못된 유저입니다.")
    })
    @GetMapping("")
    public ResponseEntity<?> getRooms(Authentication authentication) {
        try {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

            List<RoomListResponse> list = roomService.list(customUserDetails.getUserEmail());

            return ResponseEntity
                    .ok()
                    .body(list);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        }
    }

    @Operation(summary = "get rooms", description = "채팅방 정보 API. 참여 중인 채팅방 디테일을 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(schema = @Schema(implementation = RoomResponse.class))),
            @ApiResponse(responseCode = "401", description = "잘못된 유저입니다.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getRoom(@PathVariable Long id) {

        return ResponseEntity
                .ok()
                .build();
    }

}
