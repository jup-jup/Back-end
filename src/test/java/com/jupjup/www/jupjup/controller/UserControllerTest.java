package com.jupjup.www.jupjup.controller;

import com.jupjup.www.jupjup.domain.repository.RefreshTokenRepository;
import com.jupjup.www.jupjup.service.JoinService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(UserController.class)
@MockBean(JpaMetamodelMappingContext.class) // Jpa 연관 Bean 등록하기
@AutoConfigureMockMvc(addFilters = false)  // Security 필터 비활성화@
public class UserControllerTest {

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
                .andExpect(header().string("Location", "/oauth2/authorize/google"));
    }

    @Test
    public void testRedirectToAuthorizationWithInvalidProvider() throws Exception {
        mockMvc.perform(get("/api/v1/user/login")
                        .param("provider", "invalid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testLogoutWithValidRefreshToken() throws Exception {
        Mockito.doNothing().when(refreshTokenRepository).deleteAllByRefreshToken(anyString());

        mockMvc.perform(get("/api/v1/user/logout")
                        .cookie(new Cookie("refreshToken", "testRefreshToken")))
                .andExpect(status().isOk())
                .andExpect(content().string("로그아웃 완료"));
    }
}