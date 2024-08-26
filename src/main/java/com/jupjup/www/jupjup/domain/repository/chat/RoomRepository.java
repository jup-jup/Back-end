package com.jupjup.www.jupjup.domain.repository.chat;

import com.jupjup.www.jupjup.domain.entity.chat.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}
