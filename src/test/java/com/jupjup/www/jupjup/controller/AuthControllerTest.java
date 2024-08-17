package com.jupjup.www.jupjup.controller;

import com.jupjup.www.jupjup.service.refreshService.RefreshReissueService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@MockBean(JpaMetamodelMappingContext.class) // Jpa 연관 Bean 등록하기
@AutoConfigureMockMvc(addFilters = false)  // Security 필터 비활성화
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RefreshReissueService refreshReissueService;

    @Test
    public void testReissue_Success() throws Exception {
        // Given: Mocking the service method to return true
        Mockito.when(refreshReissueService.refreshTokenReissue(anyString(), anyString(), any()))
                .thenReturn(true);

        // When & Then
        mockMvc.perform(post("/api/v1/auth/reissue")
                        .cookie(new Cookie("refreshToken", "mockRefreshToken"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"userEmail\": \"test@example.com\" }"))
                .andExpect(status().isOk())
                .andExpect(content().string("액세스 토큰 재발급 완료"));
    }

    @Test
    public void testReissue_Failure() throws Exception {
        // Given: Mocking the service method to return false
        Mockito.when(refreshReissueService.refreshTokenReissue(anyString(), anyString(), any()))
                .thenReturn(false);

        // When & Then
        mockMvc.perform(post("/api/v1/auth/reissue")
                        .cookie(new Cookie("refreshToken", "mockRefreshToken"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"userEmail\": \"test@example.com\" }"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("토큰 재발급 실패"));
    }

    @Test
    public void testReissue_Exception() throws Exception {
        // Given: Mocking the service method to throw an exception
        Mockito.when(refreshReissueService.refreshTokenReissue(anyString(), anyString(), any()))
                .thenThrow(new RuntimeException("Mock exception"));

        // When & Then
        mockMvc.perform(post("/api/v1/auth/reissue")
                        .cookie(new Cookie("refreshToken", "mockRefreshToken"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"userEmail\": \"test@example.com\" }"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("일시적인 서버 장애"));
    }
}