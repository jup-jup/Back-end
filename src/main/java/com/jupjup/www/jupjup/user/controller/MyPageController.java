package com.jupjup.www.jupjup.user.controller;

import com.jupjup.www.jupjup.config.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/mypage")
public class MyPageController {

    @Operation(summary = "나눔 리스트 조회")
    @GetMapping("/sharings")
    public ResponseEntity<?> getSharingHistory(@RequestHeader("Authorization") String accessToken) {
        String userEmail = JWTUtil.parseUsernameFromToken(accessToken);
        return ResponseEntity.ok(mypageSharingService.getMyPageSharingListByUserName(userEmail));
    }

    @Operation(summary = "나눔 상세 페이지 조회")
    @GetMapping("/sharings/{id}")
    public ResponseEntity<?> getSharingDetail(@PathVariable long id) {
        return ResponseEntity.ok(mypageSharingService.getMyPageSharingById(id));
    }

    // TODO : 이미지 업로드 불러오기 추후 개발
    @Operation(summary = "수정 페이지용 기존 나눔 데이터 조회")
    @GetMapping("/sharings/{id}/edit")
    public ResponseEntity<?> getSharingForEdit(@PathVariable long id) {
        return ResponseEntity.ok(mypageSharingService.getMyPageSharingById(id));
    }

    @Operation(summary = "받은 내역 리스트 조회")
    @GetMapping("/received")
    public ResponseEntity<?> getReceivedHistory(@RequestHeader("Authorization") String accessToken) {
        String userEmail = JWTUtil.parseUsernameFromToken(accessToken);
        return ResponseEntity.ok(mypageSharingService.getMyPageReceivedListByUserName(userEmail));
    }

    @Operation(summary = "받은 내역 상세 조회")
    @GetMapping("/received/{id}")
    public ResponseEntity<?> getReceivedDetail(@PathVariable long id) {
        return ResponseEntity.ok(mypageSharingService.getMyPageReceivedById(id));
    }
}