package com.jupjup.www.jupjup.image.dto;

import com.jupjup.www.jupjup.image.entity.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class GetImageResponse {

    private Long id;
    private String path;
    private String fileName;
    private Long userId;
    private LocalDateTime createdAt;

    public static GetImageResponse toDTO(Image image) {
        return GetImageResponse.builder()
                .id(image.getId())
                .path(image.getPath())
                .fileName(image.getFileName())
                .userId(image.getUserId())
                .createdAt(image.getCreatedAt())
                .build();
    }

}
