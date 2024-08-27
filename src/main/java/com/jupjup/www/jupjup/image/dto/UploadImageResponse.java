package com.jupjup.www.jupjup.image.dto;

import com.jupjup.www.jupjup.image.entity.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
public class UploadImageResponse {

    public Long id;
    public String path;
    public String name;
    public LocalDateTime createdAt;

    public static UploadImageResponse toDTO(Image image) {
        return UploadImageResponse.builder()
                .id(image.getId())
                .path(image.getPath())
                .name(image.getFileName())
                .createdAt(image.getCreatedAt())
                .build();
    }

}
