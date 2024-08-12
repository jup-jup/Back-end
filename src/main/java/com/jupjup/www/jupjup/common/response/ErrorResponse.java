package com.jupjup.www.jupjup.common.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {
    @JsonProperty
    private String errorMessage;
    @JsonProperty
    private int errorCode;

}
