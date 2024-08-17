package com.jupjup.www.jupjup.model.dto.mypage;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MyPageListResponse {

    private String username;        // 유저 이름
    private String userProfile;     // 이건 뭔지 모르겠으나 일단 만들어 달래서 만듦
    private String title;          // 제목
    private String content;        // 내용
    private int reservationStatus;  // 0,1,2 (나눔중,예약중,나눔완료)
    private String preferredPlace;   // 희망 장소
    private LocalDateTime createTime; // 생성일
    private String favoriteCount;    // 찜하기 수
    private long viewCount;         // 조회수
    private MyPageListImageResponse myPageListImageResponse; // 이미지 리스트


}
