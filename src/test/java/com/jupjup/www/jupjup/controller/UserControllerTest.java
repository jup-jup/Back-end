package com.jupjup.www.jupjup.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupjup.www.jupjup.domain.repository.RefreshTokenRepository;
import com.jupjup.www.jupjup.model.dto.UserDTO;
import com.jupjup.www.jupjup.service.JoinService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
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
    @WithMockUser(username="admin", roles={"USER","ADMIN"}) // 해당 사용자에게 USER 및 ADMIN 권한 부여
    void testJoin() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserEmail("test@example.com");
        userDTO.setPassword("password");

        // 모든 UserDTO 입력에 대해 "회원가입완료"를 반환하도록 설정
        given(joinService.joinProcess(any(UserDTO.class))).willReturn("회원가입 완료");

        mockMvc.perform(post("/api/v1/user/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO))) // ObjectMapper 사용
                .andExpect(status().isOk())
                .andExpect(content().string("회원가입 완료"));
    }

    @Test
    void testLogout() throws Exception {
        String userEmail = "test@example.com";
        doNothing().when(refreshTokenRepository).deleteByUserEmail(userEmail);

        mockMvc.perform(post("/api/v1/user/logout")
                        .param("userEmail", userEmail))
                .andExpect(status().isOk())
                .andExpect(content().string("로그아웃 완료"));
    }
}