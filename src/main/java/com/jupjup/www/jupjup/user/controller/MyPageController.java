package com.jupjup.www.jupjup.user.controller;

import com.jupjup.www.jupjup.config.JWTUtil;
import com.jupjup.www.jupjup.giveaway.dto.GiveawayListResponse;
import com.jupjup.www.jupjup.giveaway.entity.Giveaway;
import com.jupjup.www.jupjup.user.enums.MyPageType;
import com.jupjup.www.jupjup.user.service.MyPageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/mypage")
public class MyPageController {

    private final MyPageService myPageService;

    /**
     * @description : type 별도로 받으며 중복 코드 제거 , 추후 유지보수를 위해 서비스단에서 메서드 분리
     */
    @Operation(summary = "마이페이지 리스트 조회 (페이징 지원 & type = giver or Receiver)")
    @GetMapping("/{type}/list")
    public ResponseEntity<?> getList(
            @PageableDefault(size = 30, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable String type, @RequestHeader("Authorization") String accessToken) {

        Long userId = JWTUtil.parseUserIdFromToken(accessToken);

        List<GiveawayListResponse> giverList = myPageService.findAllGiverList(pageable,userId);
        List<GiveawayListResponse> receiverList = myPageService.findAllReceiverList(pageable,userId);

        if (type.equals(MyPageType.GIVER.getType())) {
            return ResponseEntity.ok(giverList);
        } else if (type.equals(MyPageType.RECEIVER.getType())) {
            return ResponseEntity.ok().body("ok");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}


