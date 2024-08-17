package com.jupjup.www.jupjup.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupjup.www.jupjup.service.JoinService;
import org.junit.jupiter.api.Test;
import com.jupjup.www.jupjup.domain.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(UserController.class)
@MockBean(JpaMetamodelMappingContext.class) // Jpa 연관 Bean 등록하기
@AutoConfigureMockMvc(addFilters = false)  // Security 필터 비활성화
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JoinService joinService;

    @MockBean
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private ObjectMapper objectMapper; // JSON 변환을 위한 ObjectMapper

    @Test
    void testLogout() throws Exception {

        String userEmail = "test@example.com";

        doNothing().when(refreshTokenRepository).deleteByUserEmail(userEmail);

        mockMvc.perform(post("/api/v1/user/logout/{boram}", userEmail)
                        .param("userEmail", userEmail))
                .andExpect(status().isOk())
                .andExpect(content().string("로그아웃 완료"));
    }
}