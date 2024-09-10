package com.jupjup.www.jupjup.chat.repository.impl;

import com.jupjup.www.jupjup.chat.entity.QChat;
import com.jupjup.www.jupjup.chat.entity.QRoom;
import com.jupjup.www.jupjup.chat.entity.QUserChatRoom;
import com.jupjup.www.jupjup.chat.entity.Room;
import com.jupjup.www.jupjup.chat.repository.RoomRepositoryCustom;
import com.jupjup.www.jupjup.user.entity.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


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
        QChat qChat = QChat.chat;
        QUser qUser = QUser.user;

        return queryFactory
                .select(qRoom)
                .from(qRoom)
                .join(qRoom.userChatRooms, qUserChatRoom)
                .join(qUserChatRoom.user, qUser)
                .leftJoin(qRoom.chats, qChat).fetchJoin()
                .where(qUserChatRoom.userId.eq(userId))
                .fetch();
    }

    @Override
    public Optional<Room> findByIdAndUserId(Long roomId, Long userId) {
        QRoom qRoom = QRoom.room;
        QUserChatRoom qUserChatRoom = QUserChatRoom.userChatRoom;

        Room result = queryFactory
                .select(qRoom)
                .from(qRoom)
                .join(qRoom.userChatRooms, qUserChatRoom)
                .where(
                        qRoom.id.eq(roomId)
                                .and(qUserChatRoom.userId.eq(userId))
                )
                .fetchFirst();

        return Optional.ofNullable(result);
    }

}
