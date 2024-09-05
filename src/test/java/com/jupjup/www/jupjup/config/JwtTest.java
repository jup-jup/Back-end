package com.jupjup.www.jupjup.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtTest {
    @Test
    public void isValidateTokenTest() throws InterruptedException {
        final Long userId = 1L;
        final String userName = "name";
        final String userEmail = "name@email.com";

        String validToken = JWTUtil.generateAccessToken(userId, userName, userEmail);

        boolean validTokenResult = JWTUtil.validateAccessToken(validToken);
        Assertions.assertTrue(validTokenResult);
    }

}
