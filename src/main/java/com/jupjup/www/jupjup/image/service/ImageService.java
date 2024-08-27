package com.jupjup.www.jupjup.image.service;

import com.jupjup.www.jupjup.image.dto.GetImageResponse;
import com.jupjup.www.jupjup.image.dto.UploadImageResponse;
import com.jupjup.www.jupjup.image.entity.Image;
import com.jupjup.www.jupjup.image.repository.ImageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ImageService {

    // lombok.RequiredArgsConstructor 는 Qualifier 인식 불가
    // 프로젝트 최상위에 lombok.config 생성 후 gradle cache 삭제
    @Qualifier(value = "local") // TODO: local, s3 application.yml 따라서 설정 바꾸는 방법 알아볼 것
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

}
