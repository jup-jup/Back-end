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
        QUserChatRoom qUserChatRoom = QUserChatRoom.userChatRoom;
        QRoom qRoom = QRoom.room;

        return queryFactory
                .select(qRoom)
                .from(qRoom)
                .join(qRoom.userChatRooms, qUserChatRoom)
                .where(
                        qUserChatRoom.user.id.eq(userId)
                                .and(qRoom.giveawayId.eq(giveawayId))
                )
                .fetch();
    }

}
