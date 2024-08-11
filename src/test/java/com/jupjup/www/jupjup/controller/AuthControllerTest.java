//package com.jupjup.www.jupjup.controller;
//
//import com.jupjup.www.jupjup.domain.entity.RefreshEntity;
//import com.jupjup.www.jupjup.config.JWTUtil;
//import com.jupjup.www.jupjup.model.dto.TokenDTO;
//import com.jupjup.www.jupjup.service.AuthService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mock.web.MockHttpServletResponse;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//class AuthControllerTest {
//
//    @InjectMocks
//    private AuthController authController;
//
//    @Mock
//    private AuthService authService;
//
//    @Mock
//    private JWTUtil jwtUtil;
//
//    private MockHttpServletResponse response;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        response = new MockHttpServletResponse();
//    }
//
//    @Test
//    void reissue_ValidRefreshToken_ShouldReturnOk() {
//        // Given
//        String refreshToken = "valid_refresh_token@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@";
//        RefreshEntity refreshEntity = new RefreshEntity();
//        refreshEntity.setUserEmail("test@example.com");
//
//        TokenDTO tokenDTO = new TokenDTO("new_access_token", "new_refresh_token");
//
//        when(JWTUtil.validateRefreshToken(refreshToken)).thenReturn(false);
//        when(authService.refreshTokenRotate(refreshToken, refreshEntity.getUserEmail())).thenReturn(tokenDTO);
//
//        // When
//        ResponseEntity<?> responseEntity = authController.reissue(refreshToken, refreshEntity, response);
//
//        // Then
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals("액세스 토큰 재발급 완료", responseEntity.getBody());
//        assertEquals("Bearer new_access_token", response.getHeader("Authorization"));
//        verify(authService).refreshTokenRotate(refreshToken, refreshEntity.getUserEmail());
//    }
//
//    @Test
//    void reissue_InvalidRefreshToken_ShouldReturnUnauthorized() {
//        // Given
//        String refreshToken = "invalid_refresh_token";
//        RefreshEntity refreshEntity = new RefreshEntity();
//        refreshEntity.setUserEmail("test@example.com");
//
//        when(JWTUtil.validateRefreshToken(refreshToken)).thenReturn(true);
//
//        // When
//        ResponseEntity<?> responseEntity = authController.reissue(refreshToken, refreshEntity, response);
//
//        // Then
//        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
//        assertEquals("토큰 만료 재로그인 요망", responseEntity.getBody());
//        verifyNoInteractions(authService);
//    }
//
//    @Test
//    void reissue_NullRefreshToken_ShouldReturnUnauthorized() {
//        // Given
//        RefreshEntity refreshEntity = new RefreshEntity();
//        refreshEntity.setUserEmail("test@example.com");
//
//        // When
//        ResponseEntity<?> responseEntity = authController.reissue(null, refreshEntity, response);
//
//        // Then
//        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
//        assertEquals("토큰 만료 재로그인 요망", responseEntity.getBody());
//        verifyNoInteractions(authService);
//    }
//}