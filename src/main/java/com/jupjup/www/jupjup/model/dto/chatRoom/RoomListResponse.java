package com.jupjup.www.jupjup.model.dto.chatRoom;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class RoomListResponse {

    private Long id; // room_id

    private List<Long> joinedUserIds; // 참여 중인 user_id

    private Long giveawayId; // 관련 나눔 품목 id

    private String lastChat; // 마지막 메시지

    private LocalDateTime lastChatCreatedAt; // 최신 메시지 생성일

}
