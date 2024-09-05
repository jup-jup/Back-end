//package com.jupjup.www.jupjup.controller;
//
//import com.jupjup.www.jupjup.chat.controller.ChatController;
//import com.jupjup.www.jupjup.chat.dto.CreateChatRequest;
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
//@MockBean(JpaMetamodelMappingContext.class) // Jpa 연관 Bean 등록하기
//@AutoConfigureMockMvc(addFilters = false)  // Security 필터 비활성화
//@WebMvcTest(ChatController.class)
//public class ChatControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void testGetChat() throws Exception {
//        Long roomId = 1L;
//        Long chatId = 1L;
//
//        mockMvc.perform(get("/chat-rooms/{roomId}/chats/{id}", roomId, chatId))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testCreateChat() throws Exception {
//        Long roomId = 1L;
//        String createChatRequestJson = "{ \"message\": \"Hello World\", \"senderId\": 1 }";
//
//        mockMvc.perform(post("/chat-rooms/{roomId}/chats/", roomId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(createChatRequestJson))
//                .andExpect(status().isCreated());
//    }
//}