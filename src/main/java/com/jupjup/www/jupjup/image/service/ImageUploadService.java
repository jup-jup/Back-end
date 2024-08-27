package com.jupjup.www.jupjup.image.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageUploadService {
    String upload(MultipartFile file, Long userId) throws IOException;
}
