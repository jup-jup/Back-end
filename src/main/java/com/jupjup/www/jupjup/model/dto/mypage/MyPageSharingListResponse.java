package com.jupjup.www.jupjup.model.dto.mypage;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyPageSharingListResponse {

    private Long id;
    private String title; // 제목
    private String content; // 내용
    private String location; // 거래 장소
    private String status; // 예약 여부 (나눔중,예약중,나눔완료)
    private Long chatCount; // 댓글수
    private Long viewCount; // 조회수
    private String representativeImage; // 대표 이미지 URL 또는 경로
    private LocalDateTime receivedAt; // 나눔 날짜

}
