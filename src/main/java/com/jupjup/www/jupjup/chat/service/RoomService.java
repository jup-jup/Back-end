package com.jupjup.www.jupjup.chat.service;

import com.jupjup.www.jupjup.chat.entity.Room;
import com.jupjup.www.jupjup.chat.entity.UserChatRoom;
import com.jupjup.www.jupjup.domain.entity.User;
import com.jupjup.www.jupjup.domain.repository.UserRepository;
import com.jupjup.www.jupjup.chat.repository.RoomRepository;
import com.jupjup.www.jupjup.chat.repository.UserChatRoomRepository;
import com.jupjup.www.jupjup.chat.dto.chatRoom.CreateRoomRequest;
import com.jupjup.www.jupjup.chat.dto.chatRoom.CreateRoomResponse;
import com.jupjup.www.jupjup.chat.dto.chatRoom.RoomListResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserChatRoomRepository userChatRoomRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateRoomResponse create(CreateRoomRequest request, Long userId) {
        // 유저간 대화방이 있는지 확인
        List<Room> rooms = roomRepository.findByUserIdAndGiveawayId(userId, request.getGiveawayId());
        if (!rooms.isEmpty()) { // 이미 채팅방 존재하면 해당 채팅방 정보로 리턴
            return CreateRoomResponse.toDTO(rooms.get(0));
        }

        Room room = Room.builder()
                .giveawayId(request.getGiveawayId())
                .build();

        room.addUserChatRoom(userId);
        room.addUserChatRoom(request.getGiveawayId());

        roomRepository.save(room);

        return CreateRoomResponse.toDTO(room);
    }

    @Transactional
    public List<RoomListResponse> list(Long userId) {
        // user_chat_room 테이블에서 참여중인 room_id 가져오기
        List<UserChatRoom> roomUsers = userChatRoomRepository.findJoinedRoomByUserId(userId);
        if (roomUsers.isEmpty()) {
            return List.of(RoomListResponse.builder().build());
        }


        // TODO: room 별로 마지막 메시지 정보도 가져와야 함.

        // room_id 별로 참여 중인 user 목록 필터링
//        Map<Long, List<Long>> roomUserMap = roomUsers.stream()
//                .collect(Collectors.groupingBy(
//                        UserChatRoom::getRoomId,
//                        Collectors.mapping(UserChatRoom::getUserId, Collectors.toList())
//                ));
//
//        // room_id 들로 해당 채팅 리스트 조회
//        List<Long> roomIds = roomUsers.stream()
//                .map(UserChatRoom::getRoomId)
//                .distinct()
//                .toList();
//        List<Room> rooms = roomRepository.findAllById(roomIds);


//        return rooms.stream()
//                .map(o -> RoomListResponse.toDTO(o, roomUserMap.get(o.getId())))
//                .toList();
        return null;
    }

}
