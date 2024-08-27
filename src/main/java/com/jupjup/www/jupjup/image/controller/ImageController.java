package com.jupjup.www.jupjup.image.controller;

import com.jupjup.www.jupjup.config.JWTUtil;
import com.jupjup.www.jupjup.image.dto.UploadImageResponse;
import com.jupjup.www.jupjup.image.service.ImageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "Image", description = "이미지 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/image")
public class ImageController {

    private final ImageService imageService;

    private static final String BEARER_PREFIX = "Bearer ";

    @PostMapping("")
    public ResponseEntity<?> upload(@RequestBody List<MultipartFile> files, @RequestHeader("Authorization") String header) {
        // TODO: authorization header 에서 userId 뽑아오는 방법이 이게 최선일까..
        String token = header.substring(BEARER_PREFIX.length());
        Long userId = JWTUtil.getUserIdFromAccessToken(token);

        try {
            List<UploadImageResponse> images = imageService.save(files, userId);
            return ResponseEntity
                    .ok()
                    .body(images);
        } catch (IOException e) {
            return ResponseEntity
                    .internalServerError()
                    .body(e.getMessage());
        }
    }

    // TODO: 경로로 이미지 찾는 기능
    @GetMapping("/{id}")
    public ResponseEntity<?> findImage(@PathVariable Long id) {

        return ResponseEntity.ok().build();
    }

}
