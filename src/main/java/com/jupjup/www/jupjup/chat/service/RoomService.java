package com.jupjup.www.jupjup.chat.service;

import com.jupjup.www.jupjup.chat.dto.room.CreateRoomRequest;
import com.jupjup.www.jupjup.chat.dto.room.CreateRoomResponse;
import com.jupjup.www.jupjup.chat.dto.room.RoomListResponse;
import com.jupjup.www.jupjup.chat.entity.Room;
import com.jupjup.www.jupjup.chat.repository.RoomRepository;
import com.jupjup.www.jupjup.giveaway.entity.Giveaway;
import com.jupjup.www.jupjup.giveaway.repository.GiveawayRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final GiveawayRepository giveawayRepository;

    @Transactional
    public CreateRoomResponse create(CreateRoomRequest request, Long userId) {
        // 유저간 대화방이 있는지 확인
        List<Room> rooms = roomRepository.findByUserIdAndGiveawayId(userId, request.getGiveawayId());
        if (!rooms.isEmpty()) { // 이미 채팅방 존재하면 해당 채팅방 정보로 리턴
            return CreateRoomResponse.toDTO(rooms.get(0));
        }

        Giveaway giveaway = giveawayRepository.findById(request.getGiveawayId())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 나눔 id"));

        if (giveaway.getGiverId().equals(userId)) {
            throw new IllegalArgumentException("본인의 나눔 글입니다.");
        }

        Room room = Room.builder()
                .giveawayId(request.getGiveawayId())
                .build();

        room.addUserChatRoom(userId);
        room.addUserChatRoom(giveaway.getGiverId());

        roomRepository.save(room);

        return CreateRoomResponse.toDTO(room);
    }

    @Transactional
    public List<RoomListResponse> list(Long userId) {
        List<Room> joinedRooms = roomRepository.findJoinedRoomsByUserId(userId);
        if (joinedRooms.isEmpty()) {
            return null;
        }

        return joinedRooms.stream()
                .map(RoomListResponse::toDTO)
                .toList();
    }

}
