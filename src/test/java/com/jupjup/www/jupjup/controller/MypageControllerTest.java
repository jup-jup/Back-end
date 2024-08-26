//package com.jupjup.www.jupjup.controller;
//
//import com.jupjup.www.jupjup.model.dto.mypage.MyPageSharingListRequest;
//import com.jupjup.www.jupjup.service.mypageService.MypageSharingService;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Collections;
//
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(MypageController.class)
//public class MypageControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private MypageSharingService mypageSharingService;
//
//    @Test
//    void testSharingHistory() throws Exception {
//        Mockito.when(mypageSharingService.getMyPageSharingListByUserName(anyString()))
//                .thenReturn(Collections.emptyList());
//
//        mockMvc.perform(get("/api/v1/myPage/sharingHistory/testuser"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.length()").value(0));
//    }
//
//    @Test
//    void testGetListDetail() throws Exception {
//        Mockito.when(mypageSharingService.getMyPageSharingById(anyLong()))
//                .thenReturn(new MyPageSharingListRequest());
//
//        mockMvc.perform(get("/api/v1/myPage/sharingHistoryDetail/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").exists());
//    }
//
//    @Test
//    void testModifyGet() throws Exception {
//        Mockito.when(mypageSharingService.getMyPageSharingById(anyLong()))
//                .thenReturn(new MyPageSharingListRequest());
//
//        mockMvc.perform(get("/api/v1/myPage/modify/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").exists());
//    }
//
//    @Test
//    void testModifyPost() throws Exception {
//        Mockito.when(mypageSharingService.updateItem(Mockito.any(MyPageSharingListRequest.class)))
//                .thenReturn(true);
//
//        mockMvc.perform(post("/api/v1/myPage/modify/save")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"id\": 1, \"title\": \"Updated Title\"}"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void testReceivedHistory() throws Exception {
//        Mockito.when(mypageSharingService.getMyPageReceivedListByUserName(anyString()))
//                .thenReturn(Collections.emptyList());
//
//        mockMvc.perform(get("/api/v1/myPage/receivedHistory/testuser"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.length()").value(0));
//    }
//
//    @Test
//    void testReceivedHistoryDetail() throws Exception {
//        Mockito.when(mypageSharingService.getMyPageReceivedById(anyLong()))
//                .thenReturn(new MyPageSharingListRequest());
//
//        mockMvc.perform(get("/api/v1/myPage/receivedHistoryDetail/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").exists());
//    }
//}