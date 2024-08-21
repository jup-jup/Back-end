package com.jupjup.www.jupjup.service.chat;

import com.jupjup.www.jupjup.domain.entity.chat.Room;
import com.jupjup.www.jupjup.domain.entity.chat.UserChatRoom;
import com.jupjup.www.jupjup.domain.repository.UserRepository;
import com.jupjup.www.jupjup.domain.repository.chat.RoomRepository;
import com.jupjup.www.jupjup.domain.repository.chat.UserChatRoomRepository;
import com.jupjup.www.jupjup.model.dto.chatRoom.CreateRoomRequest;
import com.jupjup.www.jupjup.model.dto.chatRoom.CreateRoomResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

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

}
