package com.jupjup.www.jupjup.chat.dto.chatRoom;

import com.jupjup.www.jupjup.chat.entity.Room;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
public class CreateRoomResponse {

    private Long roomId;
    private List<Long> userIds;
    private Long giveawayId;
    private LocalDateTime createdAt;

    public static CreateRoomResponse toDTO(Room room, List<Long> userIds) {
        return CreateRoomResponse.builder()
                .roomId(room.getId())
                .userIds(userIds) // TODO: userIds 는 어떻게 넘겨줄지 확인 필요. ManyToMany 에서 연관 테이블에 저장하는 데이터에 대한 이슈
                .giveawayId(room.getGiveawayId())
                .createdAt(room.getCreatedAt())
                .build();
    }

}
