//package com.jupjup.www.jupjup.chat.service;
//
//import com.jupjup.www.jupjup.chat.entity.Room;
//import com.jupjup.www.jupjup.chat.repository.RoomRepository;
//import com.jupjup.www.jupjup.giveaway.entity.Giveaway;
//import com.jupjup.www.jupjup.giveaway.repository.GiveawayRepository;
//import com.jupjup.www.jupjup.user.entity.User;
//import com.jupjup.www.jupjup.user.repository.UserRepository;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//@SpringBootTest
//@Transactional
//public class RoomServiceTest {
//
//    @Autowired
//    private final RoomRepository roomRepository;
//
//    @Autowired
//    private final UserRepository userRepository;
//
//    @Autowired
//    private final GiveawayRepository giveawayRepository;
//
//    @Autowired
//    public RoomServiceTest(RoomRepository roomRepository, UserRepository userRepository, GiveawayRepository giveawayRepository) {
//        this.roomRepository = roomRepository;
//        this.userRepository = userRepository;
//        this.giveawayRepository = giveawayRepository;
//    }
//
//    @Test
//    public void create() {
//        // given. 데이터 세팅
//
//        // 유저 저장
//        User user1 = User.builder()
//                .providerKey("providerKey")
//                .name("user1")
//                .email("userEmail")
//                .build();
//
//        User user2 = User.builder()
//                .providerKey("providerKey")
//                .name("user2")
//                .email("userEmail")
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
//        // when. 생성 수행
//        // 유저간 대화방이 있는지 확인
//        List<Room> rooms = roomRepository.findByUserIdAndGiveawayId(user2.getId(), giveaway.getId());
//        Assertions.assertNotNull(rooms);
//
//        Room room = Room.builder()
//                .giveawayId(giveaway.getId())
//                .build();
//
//        room.addUserChatRoom(user2.getId());
//        room.addUserChatRoom(user1.getId());
//
//        roomRepository.save(room);
//
//    }
//
//}
