package com.jupjup.www.jupjup.domain.repository.chat;

import com.jupjup.www.jupjup.domain.entity.chat.UserChatRoom;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserChatRoomRepository extends JpaRepository<UserChatRoom, Long> {
    Optional<UserChatRoom> findByIdAndUserId(Long id, Long userId);

    @Query("SELECT ucr FROM UserChatRoom ucr " +
            "JOIN UserChatRoom ucr2 ON ucr.room.id = ucr2.room.id " +
            "WHERE ucr2.user.id = :userId")
    List<UserChatRoom> findJoinedRoomByUserId(@Param("userId") Long userId);
}
