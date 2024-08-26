package com.jupjup.www.jupjup.image.service;

import com.jupjup.www.jupjup.image.dto.UploadImageResponse;
import com.jupjup.www.jupjup.image.entity.Image;
import com.jupjup.www.jupjup.image.repository.ImageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    // TODO: 일단은 로컬 경로에 저장하도록 지정. AWS 로 옮기게 된다면 수정할 것.
    // 저장할 파일경로 지정
    private final String baseDir = new File("images/").getAbsolutePath();


    @Transactional
    public List<UploadImageResponse> save(List<MultipartFile> files, Long userId) throws IOException {
        List<Image> images = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            String uploadDir = baseDir + "/" + userId + "/" + LocalDate.now() + "/";

            // 저장소에 실제 파일 업로드
            Path dir = Paths.get(uploadDir);
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }
            File f = new File(uploadDir + fileName);
            file.transferTo(f);

            // 메타 정보 저장을 위함
            Image image = Image.builder()
                    .path(uploadDir + fileName)
                    .fileName(fileName)
                    .userId(userId)
                    .build();

            images.add(image);
        }

        imageRepository.saveAll(images);

        return images.stream()
                .map(UploadImageResponse::toDTO)
                .toList();
    }

}
