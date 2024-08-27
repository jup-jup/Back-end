package com.jupjup.www.jupjup.image.service.impl;

import com.jupjup.www.jupjup.image.service.ImageUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service("s3")
public class S3ImageUploadServiceImpl implements ImageUploadService {

    @Override
    public String upload(MultipartFile file, Long userId) throws IOException {
        // TODO: AWS S3 파일 업로드하는 기능 구현

        return "";
    }

}
