package com.jupjup.www.jupjup.chat.controller;

import com.jupjup.www.jupjup.chat.dto.chat.ChatDTO;
import com.jupjup.www.jupjup.chat.dto.chat.CreateChatRequest;
import com.jupjup.www.jupjup.chat.entity.Chat;
import com.jupjup.www.jupjup.chat.service.ChatService;
import com.jupjup.www.jupjup.config.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Chat", description = "채팅 API")
@RequiredArgsConstructor
@RestController
// TODO: 전체 서버 관점에서 /api/v1 prefix 를 사용한다면 application.yml 에서 설정하는 것이 좋아보임
@RequestMapping("/api/v1/chat-rooms/{roomId}/chats") // 메인은 채팅인데 채팅 방을 기준으로 가는 URL 이 괜찮을지..
public class ChatController {

    private final ChatService chatService;

    private static final String BEARER_PREFIX = "Bearer ";

    @MessageMapping("/room/{roomId}/chat")
    @SendTo("/sub/room/{roomId}")
    public ChatDTO sendChat(
            @DestinationVariable Long roomId,
            @Header("Authorization") String header,
            CreateChatRequest request
    ) {
        String token = header.substring(BEARER_PREFIX.length());
        Long userId = JWTUtil.getUserIdFromAccessToken(token);

        Chat chat = chatService.add(roomId, request.getContent(), userId);

        return ChatDTO.of(chat);
    }

    @Operation(summary = "get chat list", description = "채팅방의 채팅 리스트 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ChatDTO.class)))
                    }),
            @ApiResponse(responseCode = "401", description = "잘못된 유저입니다. / 해당 채팅방의 유저가 아닙니다.")
    })
    @GetMapping("")
    public ResponseEntity<?> getChatList(
            @PathVariable Long roomId,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @Valid @RequestHeader("Authorization") String header
    ) {
        try {
            // TODO: authorization header 에서 userId 뽑아오는 방법이 이게 최선일까..
            String token = header.substring(BEARER_PREFIX.length());
            Long userId = JWTUtil.getUserIdFromAccessToken(token);

            List<ChatDTO> list = chatService.chatList(pageable, roomId, userId);

            return ResponseEntity
                    .ok()
                    .body(list);

        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

}
