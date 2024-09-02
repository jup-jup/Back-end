//package com.jupjup.www.jupjup.chat.repository;
//
//import com.jupjup.www.jupjup.chat.entity.QRoom;
//import com.jupjup.www.jupjup.chat.entity.QUserChatRoom;
//import com.jupjup.www.jupjup.chat.entity.Room;
//import com.jupjup.www.jupjup.domain.entity.User;
//import com.jupjup.www.jupjup.domain.entity.giveaway.Giveaway;
//import com.jupjup.www.jupjup.domain.repository.GiveawayRepository;
//import com.jupjup.www.jupjup.user.repository.UserRepository;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//@SpringBootTest
//@Transactional
//public class RoomRepositoryCustomImplTest {
//
//    @Autowired
//    private final JPAQueryFactory queryFactory;
//
//    @Autowired
//    private final RoomRepository roomRepository;
//
//    @Autowired
//    private final GiveawayRepository giveawayRepository;
//
//    @Autowired
//    private final UserRepository userRepository;
//
//
//    @Autowired
//    public RoomRepositoryCustomImplTest(JPAQueryFactory queryFactory, RoomRepository roomRepository, GiveawayRepository giveawayRepository, UserRepository userRepository) {
//        this.queryFactory = queryFactory;
//        this.roomRepository = roomRepository;
//        this.giveawayRepository = giveawayRepository;
//        this.userRepository = userRepository;
//    }
//
//    @DisplayName("채팅방이 없는 경우 새로운 채팅방 생성")
//    @Test
//    public void findByUserIdAndGiveawayId() {
//        // given. 데이터 세팅
//
//        // 유저 저장
//        User user1 = User.builder()
//                .providerKey("providerKey")
//                .username("user1")
//                .userEmail("userEmail")
//                .role("role")
//                .build();
//
//        User user2 = User.builder()
//                .providerKey("providerKey")
//                .username("user2")
//                .userEmail("userEmail")
//                .role("role")
//                .build();
//
//        userRepository.saveAll(List.of(user1, user2));
//
//        // 나눔 정보 저장
//        Giveaway giveaway = Giveaway.builder()
//                .title("title")
//                .giverId(user1.getId())
//                .build();
//        giveawayRepository.save(giveaway);
//
//        // 채팅방 생성
//        Room room = Room.builder()
//                .giveawayId(giveaway.getId())
//                .build();
//
//        // user_chat_room 정보 추가
//        room.addUserChatRoom(user1.getId());
//        room.addUserChatRoom(user2.getId());
//
//        roomRepository.save(room);
//
//
//        final QUserChatRoom qUserChatRoom = QUserChatRoom.userChatRoom;
//        final QRoom qRoom = QRoom.room;
//
//        // when. 쿼리
//        List<Room> rooms = queryFactory
//                .select(qRoom)
//                .from(qRoom)
//                .join(qRoom.userChatRooms, qUserChatRoom)
//                .where(
//                        qUserChatRoom.user.id.eq(user1.getId())
//                                .and(qRoom.giveawayId.eq(giveaway.getId()))
//                )
//                .fetch();
//
//        // then. 검증
//        Assertions.assertEquals(rooms.size(), 1); // giveaway_id & user_id 에 채팅방은 1개
//        Assertions.assertEquals(rooms.get(0), room);
//    }
//
//}
