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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockBean(JpaMetamodelMappingContext.class) // Jpa 연관 Bean 등록하기
@AutoConfigureMockMvc(addFilters = false)  // Security 필터 비활성화
@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RefreshReissueService refreshReissueService;

    @Test
    public void testReissueSuccess() throws Exception {
        // RefreshToken 재발급 성공 시
        Mockito.when(refreshReissueService.refreshTokenReissue(anyString(), anyString(), Mockito.any()))
                .thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/reissue/test@example.com")
                        .cookie(new Cookie("refreshToken", "validToken")))
                .andExpect(status().isOk())
                .andExpect(content().string("액세스 토큰 재발급 완료"));
    }

    @Test
    public void testReissueTokenExpired() throws Exception {
        // RefreshToken 재발급 실패 시 (토큰 만료)
        Mockito.when(refreshReissueService.refreshTokenReissue(anyString(), anyString(), Mockito.any()))
                .thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/reissue/test@example.com")
                        .cookie(new Cookie("refreshToken", "expiredToken")))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("토큰 유효기간 만료 재 로그인 하세요."));
    }

    @Test
    public void testReissueException() throws Exception {
        // RefreshToken 재발급 중 예외 발생 시
        doThrow(new RuntimeException("Unexpected error")).when(refreshReissueService)
                .refreshTokenReissue(anyString(), anyString(), Mockito.any());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/reissue/test@example.com")
                        .cookie(new Cookie("refreshToken", "errorToken")))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("일시적인 서버 장애"));
    }
}