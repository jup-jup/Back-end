package com.jupjup.www.jupjup.chat.service;

import com.jupjup.www.jupjup.chat.dto.chat.ChatDTO;
import com.jupjup.www.jupjup.chat.entity.Chat;
import com.jupjup.www.jupjup.chat.repository.ChatRepository;
import com.jupjup.www.jupjup.chat.repository.RoomRepository;
import com.jupjup.www.jupjup.common.exception.CustomException;
import com.jupjup.www.jupjup.common.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final RoomRepository roomRepository;

    public Chat add(Long roomId, String content, Long userId) {
        Chat chat = Chat.builder()
                .roomId(roomId)
                .userId(userId)
                .content(content)
                .build();

        return chatRepository.save(chat);
    }

    public List<ChatDTO> chatList(Pageable pageable, Long roomId, Long userId) {
        // 해당 room 에 참여중인 user 인지 확인
        roomRepository.findByIdAndUserId(roomId, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_PERMISSION));

        // 해당 룸의 채팅 호출. TODO: 시간 순 정렬 필요
        List<Chat> chatList = chatRepository.findAllByRoomId(pageable, roomId);

        return chatList.stream()
                .map(ChatDTO::of)
                .toList();
    }

}
