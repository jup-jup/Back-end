package com.jupjup.www.jupjup.controller;

import com.jupjup.www.jupjup.domain.entity.mypage.MyPageSharingList;
import com.jupjup.www.jupjup.model.dto.mypage.MyPageSharingListResponse;
import com.jupjup.www.jupjup.service.mypageSharingService.MypageSharingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBean(JpaMetamodelMappingContext.class) // Jpa 연관 Bean 등록하기
@AutoConfigureMockMvc(addFilters = false)  // Security 필터 비활성화
@WebMvcTest(MypageSharingController.class)
public class MypageSharingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MypageSharingService mypageSharingService;

    @Test
    public void testSharingHistorySuccess() throws Exception {
        Mockito.when(mypageSharingService.mypageSharingList(anyString()))
                .thenReturn(Collections.singletonList(new MyPageSharingList()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/myPage/sharingHistory/testUser"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testSharingHistoryNotFound() throws Exception {
        Mockito.when(mypageSharingService.mypageSharingList(anyString()))
                .thenThrow(new NullPointerException());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/myPage/sharingHistory/testUser"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetListDetailSuccess() throws Exception {
        Mockito.when(mypageSharingService.mypageSharingDetailList(anyLong()))
                .thenReturn(new MyPageSharingList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/myPage/sharingHistoryDetail/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetListDetailNotFound() throws Exception {
        Mockito.when(mypageSharingService.mypageSharingDetailList(anyLong()))
                .thenThrow(new NullPointerException());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/myPage/sharingHistoryDetail/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testModifySuccess() throws Exception {
//        Mockito.when(mypageSharingService.modifyMyPageSharing(anyLong()))
//                .thenReturn(new MyPageSharingList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/myPage/modify/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testModifyNotFound() throws Exception {
        Mockito.when(mypageSharingService.modifyMyPageSharing(anyLong()))
                .thenThrow(new NullPointerException());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/myPage/modify/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testModifySave() throws Exception {
        MyPageSharingListResponse myPageListResponse = new MyPageSharingListResponse();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/myPage/modify/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testReceivedHistory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/myPage/receivedHistory/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testReceivedHistoryDetail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/myPage/receivedHistoryDetail/1"))
                .andExpect(status().isOk());
    }
}