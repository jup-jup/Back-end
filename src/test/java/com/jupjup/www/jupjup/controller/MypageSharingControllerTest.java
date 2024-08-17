package com.jupjup.www.jupjup.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@WebMvcTest(MypageSharingController.class)
@MockBean(JpaMetamodelMappingContext.class) // Jpa 연관 Bean 등록하기
@AutoConfigureMockMvc(addFilters = false)  // Security 필터 비활성화
class MypageSharingControllerTest {


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