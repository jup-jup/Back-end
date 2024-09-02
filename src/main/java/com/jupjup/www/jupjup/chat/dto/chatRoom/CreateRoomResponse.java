package com.jupjup.www.jupjup.chat.dto.chatRoom;

import com.jupjup.www.jupjup.chat.entity.Room;
import com.jupjup.www.jupjup.chat.entity.UserChatRoom;
import com.jupjup.www.jupjup.user.entity.User;
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

    public static CreateRoomResponse toDTO(Room room) {
        List<Long> userIds = room.getUserChatRooms().stream()
                .map(UserChatRoom::getUser)
                .map(User::getId)
                .toList();

        return CreateRoomResponse.builder()
                .roomId(room.getId())
                .userIds(userIds)
                .giveawayId(room.getGiveawayId())
                .createdAt(room.getCreatedAt())
                .build();
    }

}
