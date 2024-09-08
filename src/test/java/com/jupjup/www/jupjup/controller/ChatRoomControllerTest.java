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
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(ChatRoomController.class)
//@MockBean(JpaMetamodelMappingContext.class) // Jpa 연관 Bean 등록하기
//@AutoConfigureMockMvc(addFilters = false)  // Security 필터 비활성화
//public class ChatRoomControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void testGetRooms() throws Exception {
//        mockMvc.perform(get("/chat-rooms/"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testGetRoom() throws Exception {
//        Long roomId = 1L;
//        mockMvc.perform(get("/chat-rooms/{id}", roomId))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testCreateRoom() throws Exception {
//        // CreateRoomRequest 객체를 JSON 형식으로 변환
//        String RequestJson = "{ \"name\": \"Test Room\" }";
//
//        mockMvc.perform(post("/chat-rooms/")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(RequestJson))
//                .andExpect(status().isOk());
//    }
//}