package com.jupjup.www.jupjup.common.exception;


import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@Configuration
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    // TODO: CustomException 을 통해 코드를 정의하고 해당 코드를 반환할 수 있도록 할 것

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception e) {
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), "");
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(errorResponse.getCode()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), e.getMessage(), "");
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(errorResponse.getCode()));
    }

    @ExceptionHandler(value = ExpiredJwtException.class)
    public void handleExpiredJwtException(ExpiredJwtException e, HttpServletResponse response) throws IOException {
        log.info("ExpiredJwtException 예외 = > 리프레시 토큰으로 액세스 토큰 재발급");
        response.sendRedirect("/api/v1/auth/reissue");
    }


}
