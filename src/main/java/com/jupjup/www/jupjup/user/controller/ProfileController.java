package com.jupjup.www.jupjup.user.controller;


import com.jupjup.www.jupjup.user.dto.profile.ProfileRequest;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profile")
public class ProfileController {

    @Operation(summary = "프로필 수정 페이지")
    @GetMapping("/modify")
    public ResponseEntity<?> modify() {
        return ResponseEntity
                .ok()
                .build();
    }
    @Operation(summary = "프로필 업데이트")
    @PostMapping("/modify/update/{id}")
    public ResponseEntity<?> save(@PathVariable long id , @RequestBody ProfileRequest profile) {
        return ResponseEntity
                .ok()
                .build();
    }



}
