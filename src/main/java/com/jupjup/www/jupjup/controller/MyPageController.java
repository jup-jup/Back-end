package com.jupjup.www.jupjup.controller;


import com.jupjup.www.jupjup.model.dto.mypage.MyPageListResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : boramkim
 * @fileName : MyPageController.java
 * @description : 16 ~ 20 번 마이페이지 컨트롤러
 * @since : 2024. 8. 12.
 */

@RestController
@RequestMapping("/api/v1/myPage")
public class MyPageController {
    // 나눔 리스트
    @GetMapping("/sharingHistory")
    public ResponseEntity<?> sharingHistory() {

        return ResponseEntity
                .ok()
                .build();
    }
    // 나눔 상세
    @GetMapping("/sharingHistoryDetail/{id}")
    public ResponseEntity<?> getListDetail() {

        return ResponseEntity
                .ok()
                .build();
    }
    // 나눔 수정 페이지
    @GetMapping("/modify")
    public ResponseEntity<?> modify() {

        return ResponseEntity
                .ok()
                .build();
    }

    // 나눔 수정
    @PostMapping("/modify/{id}")
    public ResponseEntity<?> modify(MyPageListResponse myPageListResponse) {

        return ResponseEntity
                .ok()
                .build();
    }
    // 받은 내역 리스트
    @GetMapping("/receivedHistory")
    public ResponseEntity<?> receivedHistory() {

        return ResponseEntity
                .ok()
                .build();
    }
    // 받은 내역 상세
    @GetMapping("/receivedHistoryDetail/{id}")
    public ResponseEntity<?> receivedHistoryDetail() {

        return ResponseEntity
                .ok()
                .build();
    }


}
