package com.jupjup.www.jupjup.model.dto.mypage;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyPageSharingListResponse {
    private String title ;          // 제목
    private String content ;        // 내용
    private String tradeLocation;   // 거래장소
//    private MyPageSharingListImageResponse myPageListImageResponse;  // 이미지들
}
