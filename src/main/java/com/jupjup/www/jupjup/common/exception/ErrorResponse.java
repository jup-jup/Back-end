package com.jupjup.www.jupjup.common.exception;


import lombok.*;

@Getter
@Setter
@Builder
public class ErrorResponse {

    private int code;
    private String message;
    private String details;

    public ErrorResponse(int code, String message, String details) {
        this.code = code;
        this.message = message;
        this.details = details;
    }

    public static ErrorResponse of(int code, String message, String details) {
        return ErrorResponse.builder()
                .code(code)
                .message(message)
                .details(details)
                .build();
    }

}
