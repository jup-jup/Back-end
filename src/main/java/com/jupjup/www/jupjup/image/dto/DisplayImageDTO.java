package com.jupjup.www.jupjup.image.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.Resource;

@Builder
@Getter
@Setter
public class DisplayImageDTO {

    private String encodedFileName;
    private String contentType;
    private Resource resource;

}
