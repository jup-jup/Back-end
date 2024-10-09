//package com.jupjup.www.jupjup.giveaway.service;
//
//import com.jupjup.www.jupjup.giveaway.dto.UpdateGiveawayStatusRequest;
//import com.jupjup.www.jupjup.giveaway.entity.Giveaway;
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
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
//public class GiveawayServiceTest {
//
//    @Autowired
//    private final GiveawayService giveawayService;
//
//    @Autowired
//    private final GiveawayRepository giveawayRepository;
//
//    @Autowired
//    private final UserRepository userRepository;
//
//    @Autowired
//    public GiveawayServiceTest(GiveawayService giveawayService, GiveawayRepository giveawayRepository, UserRepository userRepository) {
//        this.giveawayService = giveawayService;
//        this.giveawayRepository = giveawayRepository;
//        this.userRepository = userRepository;
//    }
//
//    @Transactional
//    @Test
//    public void getDetail() {
//        // given
//        User user = User.builder()
//                .name("name1")
//                .email("email@email.com")
//                .providerKey("provider")
//                .build();
//        userRepository.save(user);
//
//        Giveaway giveaway = Giveaway.builder()
//                .title("title")
//                .location("location")
//                .giverId(user.getId())
//                .build();
//        giveawayRepository.save(giveaway);
//
//        Long id = giveaway.getId();
//        Long viewCnt = giveaway.getViewCount();
//
//        // when
//        Giveaway detail = giveawayRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("잘못된 나눔 id 입니다."));
//        detail.updateViewCnt();
//
//        // then
//        assertEquals(detail.getViewCount(), viewCnt + 1);
//
//    }
//
//    @Test
//    @Transactional
//    public void updateStatus() {
//        // given
//        User giver = User.builder()
//                .name("giver")
//                .email("email@email.com")
//                .providerKey("provider")
//                .build();
//        User receiver = User.builder()
//                .name("receiver")
//                .email("email@email.com")
//                .providerKey("provider")
//                .build();
//        userRepository.saveAll(List.of(giver, receiver));
//
//        Giveaway giveaway = Giveaway.builder()
//                .title("title")
//                .location("location")
//                .giverId(giver.getId())
//                .build();
//        giveawayRepository.save(giveaway);
//
//        // when
//        assertEquals(GiveawayStatus.PENDING, giveaway.getStatus());
//
//        UpdateGiveawayStatusRequest reservedRequest = new UpdateGiveawayStatusRequest();
//        reservedRequest.setStatus(GiveawayStatus.RESERVED);
//        reservedRequest.setReceiverId(receiver.getId());
//
//        // 내가 상태 변경 (예약)
//        giveawayService.updateStatus(giveaway.getId(), reservedRequest, giver.getId());
//        Giveaway reservedGiveaway = giveawayRepository.findById(giveaway.getId()).get();
//        assertEquals(GiveawayStatus.RESERVED, reservedGiveaway.getStatus());
//    }
//
//}
