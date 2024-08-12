package com.jupjup.www.jupjup.controller;

import com.jupjup.www.jupjup.config.JWTUtil;
import com.jupjup.www.jupjup.service.RefreshReissueService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpCookie;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import javax.servlet.http.Cookie;


@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RefreshReissueService refreshReissueService;
    @MockBean
    private JWTUtil jwtUtil;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testRefreshReissue() throws Exception {
        // given
//        when(refreshReissueService.refreshTokenReissue(anyString(), anyString(), any())).thenReturn(true);
//        // When & Then
//        mockMvc.perform(post("/reissue")
//                        .header("Cookie", "refreshToken=dummy-refresh-token")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"userEmail\": \"test@example.com\"}"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("액세스 토큰 재발급 완료"));

    }

}