package com.jupjup.www.jupjup.image.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.jupjup.www.jupjup.image.service.ImageUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Objects;

@Service("s3")
@RequiredArgsConstructor
@Slf4j
public class S3ImageUploadServiceImpl implements ImageUploadService {

    private final AmazonS3 amazonS3;
    private static final String BUCKET_NAME = "jupjup-shop-bucket";

    @Override
    public String upload(MultipartFile file, Long userId) throws IOException {
        String fileName = generateUniqueFileName(Objects.requireNonNull(file.getOriginalFilename()));
        String filePath = "/" + userId + "/" + LocalDate.now() + "/" + fileName;

        // S3에 파일 업로드
        try (InputStream inputStream = file.getInputStream()) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            // S3에 파일 업로드
            amazonS3.putObject(BUCKET_NAME, filePath, inputStream, metadata);
            log.info("파일 업로드 완료!");
        } catch (IOException e) {
            throw new IOException("파일 업로드 중 오류 발생", e);
        }

        return filePath;  // 업로드된 파일의 경로 반환
    }

    private String generateUniqueFileName(String originalFileName) {
        String baseName = originalFileName.substring(0, originalFileName.lastIndexOf(".")).replaceAll(" ", "");
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String timestamp = String.valueOf(System.currentTimeMillis());
        return baseName + "_" + timestamp + extension;
    }
}