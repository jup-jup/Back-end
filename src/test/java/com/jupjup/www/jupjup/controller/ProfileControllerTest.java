package com.jupjup.www.jupjup.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProfileController.class) // ProfileController를 대상으로 테스트
@MockBean(JpaMetamodelMappingContext.class) // Jpa 연관 Bean 등록하기
@AutoConfigureMockMvc(addFilters = false)  // Security 필터 비활성화
public class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testModify() throws Exception {
        mockMvc.perform(get("/api/v1/profile/modify/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testSave() throws Exception {
        // ProfileRequest 객체를 JSON 형식으로 변환
        String profileJson = "{ \"name\": \"John Doe\", \"age\": 30 }";

        mockMvc.perform(post("/api/v1/profile/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(profileJson))
                .andExpect(status().isOk());
    }
}