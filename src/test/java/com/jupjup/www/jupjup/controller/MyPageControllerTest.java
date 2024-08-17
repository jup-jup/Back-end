package com.jupjup.www.jupjup.controller;

import com.jupjup.www.jupjup.model.dto.mypage.MyPageListResponse;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(MyPageController.class)
@MockBean(JpaMetamodelMappingContext.class) // Jpa 연관 Bean 등록하기
@AutoConfigureMockMvc(addFilters = false)  // Security 필터 비활성화@
public class MyPageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testSharingHistory() throws Exception {
        mockMvc.perform(get("/api/v1/myPage/sharingHistory"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));  // 예상되는 응답 내용이 있다면 여기에 추가
    }

    @Test
    public void testGetListDetail() throws Exception {
        mockMvc.perform(get("/api/v1/myPage/sharingHistoryDetail/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));  // 예상되는 응답 내용이 있다면 여기에 추가
    }

    @Test
    public void testModifyPage() throws Exception {
        mockMvc.perform(get("/api/v1/myPage/modify"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));  // 예상되는 응답 내용이 있다면 여기에 추가
    }

    @Test
    public void testModify() throws Exception {

        mockMvc.perform(post("/api/v1/myPage/modify/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))  // 실제로는 myPageListResponse 객체를 JSON으로 변환하여 넣어야 합니다.
                .andExpect(status().isOk())
                .andExpect(content().string(""));  // 예상되는 응답 내용이 있다면 여기에 추가
    }

    @Test
    public void testReceivedHistory() throws Exception {
        mockMvc.perform(get("/api/v1/myPage/receivedHistory"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));  // 예상되는 응답 내용이 있다면 여기에 추가
    }

    @Test
    public void testReceivedHistoryDetail() throws Exception {
        mockMvc.perform(get("/api/v1/myPage/receivedHistoryDetail/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));  // 예상되는 응답 내용이 있다면 여기에 추가
    }
}