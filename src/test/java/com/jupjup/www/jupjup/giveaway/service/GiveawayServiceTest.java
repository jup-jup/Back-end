//package com.jupjup.www.jupjup.giveaway.service;
//
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
//@SpringBootTest
//public class GiveawayServiceTest {
//
//    @Autowired
//    private final GiveawayRepository giveawayRepository;
//
//    @Autowired
//    private final UserRepository userRepository;
//
//    @Autowired
//    public GiveawayServiceTest(GiveawayRepository giveawayRepository, UserRepository userRepository) {
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
//                .orElseThrow(() -> new IllegalArgumentException("잘못된 나눔 id"));
//        detail.updateViewCnt();
//
//        // then
//        Assertions.assertEquals(detail.getViewCount(), viewCnt + 1);
//
//    }
//
//}
