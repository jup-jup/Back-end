package com.jupjup.www.jupjup.common.exception;


import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@Configuration
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception e) {
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = ErrorResponse.of(errorCode.getStatus(), errorCode.getCode(), errorCode.getMessage());
        return ResponseEntity
                .status(errorResponse.getStatus())
                .body(errorResponse);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse errorResponse = ErrorResponse.of(errorCode.getStatus(), errorCode.getCode(), e.getMessage());
        return ResponseEntity
                .status(errorResponse.getStatus())
                .body(errorResponse);
    }

    @ExceptionHandler(value = ExpiredJwtException.class)
    public void handleExpiredJwtException(ExpiredJwtException e, HttpServletResponse response) throws IOException {
        log.info("ExpiredJwtException 예외 = > 리프레시 토큰으로 액세스 토큰 재발급");
        response.sendRedirect("/api/v1/auth/reissue");
    }

}
