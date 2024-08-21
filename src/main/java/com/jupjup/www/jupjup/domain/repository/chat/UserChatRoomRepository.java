package com.jupjup.www.jupjup.domain.repository.chat;

import com.jupjup.www.jupjup.domain.entity.chat.UserChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserChatRoomRepository extends JpaRepository<UserChatRoom, Long> {
    Optional<UserChatRoom> findByIdAndUserId(Long id, Long userId);
}
