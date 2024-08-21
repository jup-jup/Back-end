package com.jupjup.www.jupjup.controller;


import com.jupjup.www.jupjup.model.dto.mypage.MyPageSharingListRequest;
import com.jupjup.www.jupjup.service.mypageService.MypageSharingService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author : boramkim
 * @description : 16 ~ 20 번 나눔내역, 받은내역
 * @since : 2024. 8. 12.
 */

@RestController
@RequestMapping("/api/v1/myPage")
@RequiredArgsConstructor
@Slf4j
public class MypageController {

    private final MypageSharingService mypageSharingService;


    @Operation(summary = "나눔 리스트")
    @GetMapping("/sharingHistory/{username}")
    public ResponseEntity<?> sharingHistory(@PathVariable String username) {
        return ResponseEntity.ok(mypageSharingService.getMyPageSharingListByUserName(username));
    }

    @Operation(summary = "나눔 상세페이지 리스트")
    @GetMapping("/sharingHistoryDetail/{id}")
    public ResponseEntity<?> getListDetail(@PathVariable long id) {
        return ResponseEntity.ok(mypageSharingService.getMyPageSharingById(id));
    }

    // TODO : 이미지 업로드 불러오기 추후 개발
    @Operation(summary = "나눔 수정 페이지")
    @GetMapping("/modify/{id}")
    public ResponseEntity<?> modify(@PathVariable long id) {
        return ResponseEntity.ok(mypageSharingService.getMyPageSharingById(id));
    }

    @Operation(summary = "나눔하기 업데이트")
    @PostMapping("/modify/save")
    // TODO : 이미지 업로드 추후 개발
    public ResponseEntity<?> modify(@RequestBody MyPageSharingListRequest myPageSharingListRequest) {
        if (mypageSharingService.updateItem(myPageSharingListRequest)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Operation(summary = "받은 내역 리스트")
    @GetMapping("/receivedHistory/{username}")
    public ResponseEntity<?> receivedHistory(@PathVariable String username) {
        log.info("username {}", username);
        return ResponseEntity.ok(mypageSharingService.getMyPageReceivedListByUserName(username));
    }

    @Operation(summary = "받은 내역 상세 리스트")
    @GetMapping("/receivedHistoryDetail/{id}")
    public ResponseEntity<?> receivedHistoryDetail(@PathVariable long id) {
        return ResponseEntity.ok(mypageSharingService.getMyPageReceivedById(id));
    }
}