package com.jupjup.www.jupjup.controller;

import com.jupjup.www.jupjup.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.test.context.support.WithMockUser;
=======
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
>>>>>>> boram
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@WebMvcTest(MyPageController.class)
@MockBean(JpaMetamodelMappingContext.class) // Jpa 연관 Bean 등록하기
@AutoConfigureMockMvc(addFilters = false)  // Security 필터 비활성화
public class MyPageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testSharingHistory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/myPage/sharingHistory"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetListDetail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/myPage/sharingHistoryDetail/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testModify() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/myPage/modify/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testReceivedHistory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/myPage/receivedHistory"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testReceivedHistoryDetail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/myPage/receivedHistoryDetail/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}