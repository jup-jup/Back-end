//package com.jupjup.www.jupjup.giveaway.entity;
//
//import com.jupjup.www.jupjup.giveaway.enums.GiveawayStatus;
//import com.jupjup.www.jupjup.giveaway.repository.GiveawayRepository;
//import com.jupjup.www.jupjup.user.entity.User;
//import com.jupjup.www.jupjup.user.repository.UserRepository;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//public class GiveawayTest {
//
//    @Autowired
//    private final GiveawayRepository giveawayRepository;
//
//    @Autowired
//    private final UserRepository userRepository;
//
//    @Autowired
//    public GiveawayTest(GiveawayRepository giveawayRepository, UserRepository userRepository) {
//        this.giveawayRepository = giveawayRepository;
//        this.userRepository = userRepository;
//    }
//
//    @Transactional
//    @Test
//    public void validateUpdateStatus() {
//        // given
//        User giver = User.builder()
//                .name("giver")
//                .email("giver@email.com")
//                .providerKey("provider")
//                .build();
//
//        User receiver = User.builder()
//                .name("receiver")
//                .email("receiver@email.com")
//                .providerKey("provider")
//                .build();
//
//        User anotherUser = User.builder()
//                .name("user")
//                .email("user@email.com")
//                .providerKey("provider")
//                .build();
//
//        userRepository.saveAll(List.of(giver, receiver, anotherUser));
//
//        Giveaway giveaway = Giveaway.builder()
//                .title("title")
//                .location("location")
//                .giverId(giver.getId())
//                .images(List.of())
//                .build();
//        giveawayRepository.save(giveaway);
//
//        // when & then
//        assertTrue(giveaway.validateUpdateStatus(GiveawayStatus.RESERVED, giver.getId()));
//        assertFalse(giveaway.validateUpdateStatus(GiveawayStatus.RESERVED, receiver.getId()));
//        assertFalse(giveaway.validateUpdateStatus(GiveawayStatus.COMPLETED, giver.getId()));
//
//        // RESERVED
//        giveaway.updateStatus(GiveawayStatus.RESERVED, receiver.getId());
//
//        assertTrue(giveaway.validateUpdateStatus(GiveawayStatus.PENDING, giver.getId()));
//        assertTrue(giveaway.validateUpdateStatus(GiveawayStatus.COMPLETED, giver.getId()));
//        assertTrue(giveaway.validateUpdateStatus(GiveawayStatus.COMPLETED, receiver.getId()));
//        assertFalse(giveaway.validateUpdateStatus(GiveawayStatus.COMPLETED, anotherUser.getId()));
//        assertNull(giveaway.getReceivedAt());
//
//        // COMPLETED
//        giveaway.updateStatus(GiveawayStatus.COMPLETED, receiver.getId());
//
//        assertEquals(receiver.getId(), giveaway.getReceiverId());
//        assertNotNull(giveaway.getReceivedAt());
//    }
//
//}
