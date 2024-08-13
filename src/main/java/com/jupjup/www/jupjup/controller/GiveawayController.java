package com.jupjup.www.jupjup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/giveaways")
public class GiveawayController {
    // TODO: API 별 DTO 정의

    // 나눔 리스트
    @GetMapping("/")
    public ResponseEntity<?> getList() {

        return ResponseEntity
                .ok()
                .build();
    }

    // 나눔 상세 페이지
    @GetMapping("/{id}")
    public ResponseEntity<?> getGiveaway(@PathVariable Long id) {

        return ResponseEntity
                .ok()
                .build();
    }

    // 나눔 올리기
    @PostMapping("/")
    public ResponseEntity<?> addGiveaway() {

        return ResponseEntity
                .ok()
                .build();
    }

    // 나눔 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<?> updateGiveaway(@PathVariable Long id) {

        return ResponseEntity
                .ok()
                .build();
    }

    // 나눔 업데이트
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGiveaway(@PathVariable Long id) {

        return ResponseEntity
                .ok()
                .build();
    }

}
