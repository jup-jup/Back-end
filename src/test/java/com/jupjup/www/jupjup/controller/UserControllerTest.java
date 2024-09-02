//package com.jupjup.www.jupjup.controller;
//
//import com.jupjup.www.jupjup.user.repository.RefreshTokenRepository;
//import com.jupjup.www.jupjup.service.basicLogin.JoinService;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
//import org.springframework.http.HttpStatus;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.util.Arrays;
//
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(UserController.class)
//@MockBean(JpaMetamodelMappingContext.class) // Jpa 연관 Bean 등록하기
//@AutoConfigureMockMvc(addFilters = false)  // Security 필터 비활성화
//public class UserControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private JoinService joinService;
//
//    @MockBean
//    private RefreshTokenRepository refreshTokenRepository;
//
//    @Test
//    public void testRedirectToAuthorizationSuccess() throws Exception {
//        String provider = "google";
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/login")
//                        .param("provider", provider))
//                .andExpect(status().isSeeOther())
//                .andExpect(header().string("Location", "/oauth2/authorize/" + provider));
//    }
//
//    @Test
//    public void testRedirectToAuthorizationUnsupportedProvider() throws Exception {
//        String provider = "unsupported";
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/login")
//                        .param("provider", provider))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void testLogoutSuccess() throws Exception {
//        String userEmail = "test@example.com";
//
//        // 모킹된 메서드 설정
//        Mockito.doNothing().when(refreshTokenRepository).deleteByUserEmail(anyString());
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/logout/{userEmail}", userEmail))
//                .andExpect(status().isOk())
//                .andExpect(content().string("로그아웃 완료"));
//
//        // 리포지토리가 올바르게 호출되었는지 검증
//        Mockito.verify(refreshTokenRepository, Mockito.times(1)).deleteByUserEmail(userEmail);
//    }
//}