package com.jupjup.www.jupjup.common.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.http.HttpStatus;

@Setter @Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    @JsonProperty
    private String errorMessage;
    @JsonProperty
    private HttpStatus errorCode;

}
