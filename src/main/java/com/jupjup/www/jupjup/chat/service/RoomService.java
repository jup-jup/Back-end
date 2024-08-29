package com.jupjup.www.jupjup.chat.service;

import com.jupjup.www.jupjup.chat.entity.Room;
import com.jupjup.www.jupjup.chat.entity.UserChatRoom;
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
    public CreateRoomResponse create(CreateRoomRequest request, String userEmail) {
        // TODO: 유저 확인 중복 코드 제거 확인
        // 유저 정보 호출
        Long userId = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 유저입니다."))
                .getId();

        // TODO: 두 유저간에는 나눔당 하나만의 채팅방이 생겨야 하므로 이 부분 체크하는 로직 추가 필요

        Room room = Room.builder()
                .giveawayId(request.getGiveawayId())
                .build();

        roomRepository.save(room);

        List<UserChatRoom> userChatRoom = Arrays.asList(
                UserChatRoom.builder()
                        .roomId(room.getId())
                        .userId(userId)
                        .build(),
                UserChatRoom.builder()
                        .roomId(room.getId())
                        .userId(request.getGiverId())
                        .build()
        );
        userChatRoomRepository.saveAll(userChatRoom);

        return CreateRoomResponse.toDTO(room, Arrays.asList(userId, request.getGiverId()));
    }

    @Transactional
    public List<RoomListResponse> list(String userEmail) {
        // TODO: 유저 확인 중복 코드 제거 확인
        // 유저 정보 호출
        Long userId = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 유저입니다."))
                .getId();

        // user_chat_room 테이블에서 참여중인 room_id 가져오기
        List<UserChatRoom> roomUsers = userChatRoomRepository.findJoinedRoomByUserId(userId);
        if (roomUsers.isEmpty()) {
            return List.of(RoomListResponse.builder().build());
        }


        // TODO: room 별로 마지막 메시지 정보도 가져와야 함.

        // room_id 별로 참여 중인 user 목록 필터링
        Map<Long, List<Long>> roomUserMap = roomUsers.stream()
                .collect(Collectors.groupingBy(
                        UserChatRoom::getRoomId,
                        Collectors.mapping(UserChatRoom::getUserId, Collectors.toList())
                ));

        // room_id 들로 해당 채팅 리스트 조회
        List<Long> roomIds = roomUsers.stream()
                .map(UserChatRoom::getRoomId)
                .distinct()
                .toList();
        List<Room> rooms = roomRepository.findAllById(roomIds);


        return rooms.stream()
                .map(o -> RoomListResponse.toDTO(o, roomUserMap.get(o.getId())))
                .toList();
    }

}
