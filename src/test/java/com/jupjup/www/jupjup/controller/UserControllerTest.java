package com.jupjup.www.jupjup.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupjup.www.jupjup.service.JoinService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.jupjup.www.jupjup.domain.repository.RefreshTokenRepository;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testRedirectToAuthorizationWithValidProvider() throws Exception {
        mockMvc.perform(get("/api/v1/user/login")
                        .param("provider", "google"))
                .andExpect(status().isSeeOther())
                .andExpect(header().string(HttpHeaders.LOCATION, "/oauth2/authorize/google"));
    }

    @Test
    public void testLogoutWithValidRefreshToken() throws Exception {
        Mockito.doNothing().when(refreshTokenRepository).deleteAllByRefreshToken(anyString());

        mockMvc.perform(get("/api/v1/user/logout")
                        .cookie(new Cookie("refreshToken", "testRefreshToken")))
                .andExpect(status().isOk())
                .andExpect(content().string("로그아웃 완료"));
    }

    @Test
    public void testLogoutWithoutRefreshToken() throws Exception {
        mockMvc.perform(get("/api/v1/user/logout"))
                .andExpect(status().isBadRequest());
    }
}