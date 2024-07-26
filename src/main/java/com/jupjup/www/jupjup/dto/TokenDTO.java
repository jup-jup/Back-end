package com.jupjup.www.jupjup.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class TokenDTO {
    private String accessToken;
    private String refreshToken;
}
