package com.jupjup.www.jupjup.chat.repository.impl;

import com.jupjup.www.jupjup.chat.entity.QRoom;
import com.jupjup.www.jupjup.chat.entity.QUserChatRoom;
import com.jupjup.www.jupjup.chat.entity.Room;
import com.jupjup.www.jupjup.chat.repository.RoomRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@RequiredArgsConstructor
@Repository
public class RoomRepositoryCustomImpl implements RoomRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Room> findByUserIdAndGiveawayId(Long userId, Long giveawayId) {
        QRoom qRoom = QRoom.room;
        QUserChatRoom qUserChatRoom = QUserChatRoom.userChatRoom;

        return queryFactory
                .select(qRoom)
                .from(qRoom)
                .join(qRoom.userChatRooms, qUserChatRoom)
                .where(
                        qUserChatRoom.userId.eq(userId)
                                .and(qRoom.giveawayId.eq(giveawayId))
                )
                .fetch();
    }

    @Override
    public List<Room> findJoinedRoomsByUserId(Long userId) {
        QRoom qRoom = QRoom.room;
        QUserChatRoom qUserChatRoom = QUserChatRoom.userChatRoom;

        return queryFactory
                .select(qRoom)
                .from(qRoom)
                .join(qRoom.userChatRooms, qUserChatRoom)
                .where(qUserChatRoom.userId.eq(userId))
                .fetch();
    }

}
