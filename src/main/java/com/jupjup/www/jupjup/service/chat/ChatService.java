package com.jupjup.www.jupjup.service.chat;

import com.jupjup.www.jupjup.domain.entity.chat.Chat;
import com.jupjup.www.jupjup.domain.entity.chat.UserChatRoom;
import com.jupjup.www.jupjup.domain.repository.UserRepository;
import com.jupjup.www.jupjup.domain.repository.chat.ChatRepository;
import com.jupjup.www.jupjup.domain.repository.chat.UserChatRoomRepository;
import com.jupjup.www.jupjup.model.dto.chat.ChatList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserChatRoomRepository userChatRoomRepository;
    private final UserRepository userRepository;

    public Chat add(Long roomId, String content, String userEmail) {
        // TODO: 중복 코드 제거 확인
        // 유저 정보 호출
        Long userId = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 유저입니다."))
                .getId();

        // TODO: 소켓으로 채팅 전송

        // TODO: 해당 채팅방 마지막 메시지 업데이트

        Chat chat = Chat.builder()
                .roomId(roomId)
                .userId(userId)
                .content(content)
                .build();

        return chatRepository.save(chat);
    }

    public List<ChatList> chatList(Pageable pageable, Long roomId, String userEmail) {
        // 유저 정보 호출
        Long userId = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 유저입니다."))
                .getId();

        // 해당 room 에 참여중인 user 인지 확인
        UserChatRoom chatRoom = userChatRoomRepository.findByIdAndUserId(roomId, userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 채팅방의 유저가 아닙니다."));

        // 해당 룸의 채팅 호출
        List<Chat> chatList = chatRepository.findAllByRoomId(pageable, roomId);

        return chatList.stream()
                .map(ChatList::toDTO)
                .toList();
    }

}
