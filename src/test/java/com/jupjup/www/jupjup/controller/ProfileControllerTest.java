package com.jupjup.www.jupjup.controller;

import com.jupjup.www.jupjup.model.dto.profile.ProfileRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(ProfileController.class)
@MockBean(JpaMetamodelMappingContext.class) // Jpa 연관 Bean 등록하기
@AutoConfigureMockMvc(addFilters = false)  // Security 필터 비활성화
public class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testModifyPage() throws Exception {
        mockMvc.perform(get("/api/v1/profile/modify"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));  // 예상되는 응답이 있다면 여기에 추가
    }

    @Test
    public void testSaveProfile() throws Exception {
        ProfileRequest profileRequest = new ProfileRequest();
        // 필요한 필드 설정
        profileRequest.setNickName("Test Name");
        profileRequest.setProfileImage("uuid2220");
        // 프로필 객체를 JSON 문자열로 변환
        String requestBody = objectMapper.writeValueAsString(profileRequest);

        mockMvc.perform(post("/api/v1/profile/modify/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(""));  // 예상되는 응답이 있다면 여기에 추가
    }
}