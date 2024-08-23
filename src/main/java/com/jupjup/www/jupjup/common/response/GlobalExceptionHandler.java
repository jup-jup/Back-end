package com.jupjup.www.jupjup.common.response;


import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@Configuration
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ExpiredJwtException.class)
    public void handleExpiredJwtException(ExpiredJwtException e, HttpServletResponse response) throws IOException {
        log.info("ExpiredJwtException 예외 = > 리프레시 토큰으로 액세스 토큰 재발급");
        response.sendRedirect("/api/v1/auth/reissue");
    }


}
