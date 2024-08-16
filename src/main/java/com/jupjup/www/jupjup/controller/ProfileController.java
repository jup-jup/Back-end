package com.jupjup.www.jupjup.controller;


import com.jupjup.www.jupjup.model.dto.profile.ProfileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profile")
public class ProfileController {

    @GetMapping("/modify/{id}")
    public ResponseEntity<?> modify() {
        return ResponseEntity
                .ok()
                .build();
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody ProfileRequest profile) {
        return ResponseEntity
                .ok()
                .build();
    }



}
