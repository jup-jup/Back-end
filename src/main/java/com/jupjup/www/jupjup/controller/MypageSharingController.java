package com.jupjup.www.jupjup.controller;


import com.jupjup.www.jupjup.model.dto.mypage.MyPageListResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author : boramkim
 * @description : 16 ~ 20 번 나눔 마이페이지 컨트롤러
 * @since : 2024. 8. 12.
 */

@RestController
@RequestMapping("/api")
public class MypageSharingController {

    @Operation(summary = "나눔 리스트")
    @GetMapping("/sharingHistory/{id}")
    public ResponseEntity<?> sharingHistory(@PathVariable long id) {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "나눔 상세페이지 리스트")
    @GetMapping("/sharingHistoryDetail/{id}")
    public ResponseEntity<?> getListDetail(@PathVariable long id) {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "나눔 수정 페이지 리스트")
    @GetMapping("/modify/{id}")
    public ResponseEntity<?> modify( @PathVariable long id) {
        return ResponseEntity.ok().build();
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