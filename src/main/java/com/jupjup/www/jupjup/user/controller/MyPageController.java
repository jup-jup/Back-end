package com.jupjup.www.jupjup.user.controller;

import com.jupjup.www.jupjup.config.security.JWTUtil;
import com.jupjup.www.jupjup.giveaway.dto.GiveawayDetailResponse;
import com.jupjup.www.jupjup.giveaway.dto.GiveawayListResponse;
import com.jupjup.www.jupjup.user.enums.MyPageType;
import com.jupjup.www.jupjup.user.service.MyPageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Operation(summary = "마이페이지 리스트 조회 (페이징 지원 & type = giver or receiver)")
    @GetMapping("/{type}")
    public ResponseEntity<?> list(
            @PageableDefault(size = 30, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable String type, @RequestHeader("Authorization") String accessToken) {

        Long userId = JWTUtil.parseUserIdFromToken(accessToken);
        List<GiveawayListResponse> giverList = myPageService.findAllGiverList(pageable, userId);
        List<GiveawayListResponse> receiverList = myPageService.findAllReceiverList(pageable, userId);

        if (type.toLowerCase().equals(MyPageType.GIVER.getType())) {
            return ResponseEntity.ok(giverList);
        } else if (type.toLowerCase().equals(MyPageType.RECEIVER.getType())) {
            return ResponseEntity.ok(receiverList);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

        @Operation(summary = "마이페이지 디테일")
        @GetMapping("/{id}/detail")
        public ResponseEntity<?> detail (@PathVariable Long id){
            GiveawayDetailResponse giverList = myPageService.getDetail(id);
            return ResponseEntity.ok(giverList);
        }

    }


