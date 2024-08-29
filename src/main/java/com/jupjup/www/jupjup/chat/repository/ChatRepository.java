package com.jupjup.www.jupjup.chat.repository;

import com.jupjup.www.jupjup.chat.entity.Chat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findAllByRoomId(Pageable pageable, Long roomId);
}
