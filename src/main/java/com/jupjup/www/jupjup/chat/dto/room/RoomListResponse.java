package com.jupjup.www.jupjup.chat.dto.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jupjup.www.jupjup.chat.dto.chat.ChatDTO;
import com.jupjup.www.jupjup.chat.entity.Chat;
import com.jupjup.www.jupjup.chat.entity.Room;
import com.jupjup.www.jupjup.chat.entity.UserChatRoom;
import com.jupjup.www.jupjup.user.dto.user.UserDTO;
import com.jupjup.www.jupjup.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Builder
@Getter
@Setter
public class RoomListResponse {

    @JsonProperty("id")
    private Long id; // room_id

    @JsonProperty("joined_users")
    private List<UserDTO> joinedUsers; // 참여중인 유저 정보

    @JsonProperty("giveaway_id")
    private Long giveawayId; // 관련 나눔 품목 id

    @JsonProperty("last_chat")
    private ChatDTO lastChat; // 마지막 메시지

    public static RoomListResponse toDTO(Room room) {
        List<UserDTO> joinedUsers = room.getUserChatRooms().stream()
                .map(UserChatRoom::getUser)
                .map(UserDTO::of)
                .toList();

        Chat lastChat = Chat.builder().build();
        if (!room.getChats().isEmpty()) {
            lastChat = room.getChats().stream()
                    .max(Comparator.comparing(Chat::getCreatedAt))
                    .get();
        }

        return RoomListResponse.builder()
                .id(room.getId())
                .joinedUsers(joinedUsers)
                .giveawayId(room.getGiveawayId())
                .lastChat(ChatDTO.of(lastChat))
                .build();
    }

}
