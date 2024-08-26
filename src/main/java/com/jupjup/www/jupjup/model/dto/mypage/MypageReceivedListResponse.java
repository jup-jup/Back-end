package com.jupjup.www.jupjup.model.dto.mypage;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@Builder
public class MypageReceivedListResponse {

        private Long id;
        private String title; // 제목
        private String content; // 내용
        private String location; // 거래 장소
        private int chatCount; // 채딩 수
        private int viewCount; // 조회수
        private String representativeImage; // 대표 이미지 URL 또는 경로
        private LocalDateTime receivedAt; // 받은 날짜
        private String giverName; // 나눔한 닉네임


}
