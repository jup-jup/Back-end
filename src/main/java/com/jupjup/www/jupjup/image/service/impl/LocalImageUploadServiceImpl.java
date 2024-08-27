package com.jupjup.www.jupjup.image.service.impl;

import com.jupjup.www.jupjup.image.service.ImageUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Objects;

@Service("local")
public class LocalImageUploadServiceImpl implements ImageUploadService {

    private final String baseDir = new File("images/").getAbsolutePath();

    @Override
    public String upload(MultipartFile file, Long userId) throws IOException {
        String fileName = generateUniqueFileName(Objects.requireNonNull(file.getOriginalFilename()));

        String filePath = "/" + userId + "/" + LocalDate.now() + "/";
        String uploadPath = baseDir + filePath + fileName;

        Path dir = Paths.get(baseDir + filePath);
        if (!Files.exists(dir)) {
            Files.createDirectories(dir);
        }

        File f = new File(uploadPath);
        file.transferTo(f);

        return filePath + fileName;
    }

    // TODO: 공통 기능인데 어디에 위치하는게 좋을지..?
    public String generateUniqueFileName(String originalFileName) {
        String baseName = originalFileName.substring(0, originalFileName.lastIndexOf(".")).replaceAll(" ", "");
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String timestamp = String.valueOf(System.currentTimeMillis());
        return baseName + "_" + timestamp + extension;
    }

}
