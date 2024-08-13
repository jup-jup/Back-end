package com.jupjup.www.jupjup.controller;

import com.jupjup.www.jupjup.JupJupApplication;
import com.jupjup.www.jupjup.domain.repository.RefreshTokenRepository;
import com.jupjup.www.jupjup.service.JoinService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest(classes = JupJupApplication.class) // 메인 애플리케이션 클래스 지정
@AutoConfigureMockMvc // MockMvc 자동 설정
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JoinService joinService;

    @MockBean
    private RefreshTokenRepository refreshTokenRepository;


    @Test
    @WithMockUser
    public void testValidProvider() throws Exception {
        mockMvc.perform(get("/api/v1/user/login")
                        .param("provider", "google"))
                .andExpect(status().is3xxRedirection()) // 리다이렉션 상태 코드 검증
                .andExpect(header().string("Location", "/oauth2/authorize/google")); // 리다이렉션 경로 검증
    }

    @Test
    @WithMockUser
    public void testInvalidProvider() throws Exception {
        mockMvc.perform(get("/api/v1/user/login")
                        .param("provider", "invalid"))
                .andExpect(status().isBadRequest());
    }



    @Test
    @WithMockUser
    public void testLogout() throws Exception {
        String userEmail = "test@example.com";
        doNothing().when(refreshTokenRepository).deleteByUserEmail(userEmail);

        mockMvc.perform(post("/api/v1/user/logout")
                        .param("userEmail", userEmail))
                .andExpect(status().isOk())
                .andExpect(content().string("로그아웃 완료"));
    }
}