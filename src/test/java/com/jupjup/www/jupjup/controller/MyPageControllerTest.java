package com.jupjup.www.jupjup.controller;

import com.jupjup.www.jupjup.controller.MyPageController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MyPageController.class)
public class MyPageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser // 인증된 사용자로 요청 실행
    public void testSharingHistory() throws Exception {
        mockMvc.perform(get("/api/v1/myPage/sharingHistory"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testSharingHistoryDetail() throws Exception {
        mockMvc.perform(get("/api/v1/myPage/sharingHistoryDetail/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testModify() throws Exception {
        mockMvc.perform(get("/api/v1/myPage/modify/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testReceivedHistory() throws Exception {
        mockMvc.perform(get("/api/v1/myPage/receivedHistory"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testReceivedHistoryDetail() throws Exception {
        mockMvc.perform(get("/api/v1/myPage/receivedHistoryDetail/1"))
                .andExpect(status().isOk());
    }
}