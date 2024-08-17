package com.jupjup.www.jupjup.controller;


import com.jupjup.www.jupjup.domain.entity.mypage.MyPageSharingList;
import com.jupjup.www.jupjup.model.dto.mypage.MyPageListResponse;
import com.jupjup.www.jupjup.service.mypageSharingService.MypageSharingService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : boramkim
 * @description : 16 ~ 20 번 나눔 마이페이지 컨트롤러
 * @since : 2024. 8. 12.
 */

@RestController
@RequestMapping("/api/v1/myPage")
@RequiredArgsConstructor
@Slf4j
public class MypageSharingController {

     private final MypageSharingService mypageSharingService;


    @Operation(summary = "나눔 리스트")
    @GetMapping("/sharingHistory/{userNickName}")
    public ResponseEntity<?> sharingHistory(@PathVariable String userNickName) {
        try {
            return ResponseEntity.ok(mypageSharingService.mypageSharingList(userNickName));
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "나눔 상세페이지 리스트")
    @GetMapping("/sharingHistoryDetail/{id}")
    public ResponseEntity<?> getListDetail(@PathVariable long id) {
        try {
            return ResponseEntity.ok(mypageSharingService.mypageSharingDetailList(id));
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "나눔 수정 페이지 리스트")
    @GetMapping("/modify/{id}")
    public ResponseEntity<?> modify( @PathVariable long id) {
        try {
            return ResponseEntity.ok(mypageSharingService.modifyMyPageSharing(id));
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "나눔하기 업데이트")
    @PostMapping("/modify/save")
    public ResponseEntity<?> modify(@RequestBody MyPageListResponse myPageListResponse) {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "받은 내역 리스트")
    @GetMapping("/receivedHistory/{id}")
    public ResponseEntity<?> receivedHistory(@PathVariable long id) {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "받은 내역 상세 리스트")
    @GetMapping("/receivedHistoryDetail/{id}")
    public ResponseEntity<?> receivedHistoryDetail(@PathVariable long id) {
        return ResponseEntity.ok().build();
    }
}