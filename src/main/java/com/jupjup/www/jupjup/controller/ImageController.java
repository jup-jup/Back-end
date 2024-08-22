package com.jupjup.www.jupjup.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/image")
public class ImageController {

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestBody MultipartFile image) {

        return ResponseEntity.ok().build();
    }

    // TODO: 경로로 이미지 찾는 기능
    @GetMapping("")
    public ResponseEntity<?> findImage() {

        return ResponseEntity.ok().build();
    }

}
