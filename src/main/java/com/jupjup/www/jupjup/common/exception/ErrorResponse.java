package com.jupjup.www.jupjup.common.exception;


import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ErrorResponse {

    private HttpStatus status; // http status
    private String code; // custom error code
    private String message; // custom error message

    public ErrorResponse(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;

        if (this.status == null) {
            this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        if (this.code == null) {
            this.code = "GEN_501";
        }

        if (this.message == null) {
            this.message = "Internal Server Error";
        }
    }

    public static ErrorResponse of(HttpStatus status, String code, String message) {
        return ErrorResponse.builder()
                .status(status)
                .code(code)
                .message(message)
                .build();
    }

}
