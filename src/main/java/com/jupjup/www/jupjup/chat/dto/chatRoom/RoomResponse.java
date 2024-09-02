package com.jupjup.www.jupjup.chat.dto.chatRoom;

import com.jupjup.www.jupjup.giveaway.enums.GiveawayStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoomResponse {

    private Long giveawayId; // 관련 나눔 항목 id

    private GiveawayStatus giveawayStatus; // 나눔 상태

    private List<String> messages; // 채팅 목록

    private List<RoomUser> users; // 채팅방 유저 정보

}
