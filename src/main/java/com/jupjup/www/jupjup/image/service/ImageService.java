package com.jupjup.www.jupjup.image.service;

import com.jupjup.www.jupjup.image.dto.DisplayImageDTO;
import com.jupjup.www.jupjup.image.dto.GetImageResponse;
import com.jupjup.www.jupjup.image.dto.UploadImageResponse;
import com.jupjup.www.jupjup.image.entity.Image;
import com.jupjup.www.jupjup.image.repository.ImageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Profile("s3")
@Service
public class ImageService {

    // lombok.RequiredArgsConstructor 는 Qualifier 인식 불가
    // 프로젝트 최상위에 lombok.config 생성 후 gradle cache 삭제
    @Qualifier("s3")
    private final ImageUploadService imageUploadService;
    private final ImageRepository imageRepository;

    @Transactional
    public List<UploadImageResponse> save(List<MultipartFile> files, Long userId) throws IOException {
        List<Image> images = new ArrayList<>();
        for (MultipartFile file : files) {
            // TODO: 일반 파일인지 이미지인지 확인하는 로직이 필요할까..? 아니면 모든 "파일" 형식에 대한걸로 개발해야할까..?
            String fileName = file.getOriginalFilename();
            String uploadPath = imageUploadService.upload(file, userId);

            Image image = Image.builder()
                    .path(uploadPath) // 경로엔 저장된 이름까지
                    .fileName(fileName) // 이름에는 원본 이름
                    .userId(userId)
                    .build();

            images.add(image);
        }

        imageRepository.saveAll(images);

        return images.stream()
                .map(UploadImageResponse::toDTO)
                .toList();
    }

    public GetImageResponse find(Long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id 입니다."));

        return GetImageResponse.toDTO(image);
    }

    public DisplayImageDTO display(Long id) throws IOException {
        GetImageResponse image = find(id);

        String baseDir = new File("images/").getAbsolutePath(); // TODO: AWS S3 사용하면 변경 필요
        Path filePath = Paths.get(baseDir).resolve(image.getPath());
        Resource resource = new UrlResource("file:" + baseDir + image.getPath());

        if (!resource.exists() && !resource.isReadable()) {
            return DisplayImageDTO.builder().build();
        }

        // 파일의 MIME 타입을 결정
        String contentType = Files.probeContentType(filePath);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        //한글 파일 이름이나 특수 문자의 경우 깨질 수 있으니 인코딩 한번 해주기
        String encodedFileName = UriUtils.encode(image.getFileName(), StandardCharsets.UTF_8);

        return DisplayImageDTO.builder()
                .encodedFileName(encodedFileName)
                .contentType(contentType)
                .resource(resource)
                .build();
    }

}
