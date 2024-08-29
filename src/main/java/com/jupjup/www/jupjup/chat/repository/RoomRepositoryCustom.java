package com.jupjup.www.jupjup.chat.repository;

import com.jupjup.www.jupjup.chat.entity.Room;

import java.util.List;
import java.util.Optional;

public interface RoomRepositoryCustom {

    List<Room> findByUserIdAndGiveawayId(Long userId, Long giveawayId);
    List<Room> findJoinedRoomsByUserId(Long userId);

    Optional<Room> findByIdAndUserId(Long roomId, Long userId);

}
