//package com.jupjup.www.jupjup.controller;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@MockBean(JpaMetamodelMappingContext.class) // Jpa 연관 Bean 등록하기
//@AutoConfigureMockMvc(addFilters = false)  // Security 필터 비활성화
//@WebMvcTest(GiveawayController.class)
//public class GiveawayControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void testGetList() throws Exception {
//        mockMvc.perform(get("/giveaways/"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testGetGiveaway() throws Exception {
//        Long giveawayId = 1L;
//        mockMvc.perform(get("/giveaways/{id}", giveawayId))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testAddGiveaway() throws Exception {
//        // CreateGiveawayRequest 객체를 JSON 형식으로 변환
//        String createGiveawayRequestJson = "{ \"title\": \"Test Giveaway\", \"description\": \"This is a test giveaway.\" }";
//
//        mockMvc.perform(post("/giveaways/")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(createGiveawayRequestJson))
//                .andExpect(status().isCreated());
//    }
//
//    @Test
//    public void testUpdateGiveaway() throws Exception {
//        Long giveawayId = 1L;
//        String updateGiveawayRequestJson = "{ \"title\": \"Updated Giveaway\", \"description\": \"This is an updated giveaway.\" }";
//
//        mockMvc.perform(put("/giveaways/{id}", giveawayId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(updateGiveawayRequestJson))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testDeleteGiveaway() throws Exception {
//        Long giveawayId = 1L;
//        mockMvc.perform(delete("/giveaways/{id}", giveawayId))
//                .andExpect(status().isNoContent());
//    }
//}