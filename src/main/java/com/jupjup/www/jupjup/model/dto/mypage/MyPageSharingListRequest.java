package com.jupjup.www.jupjup.model.dto.mypage;


import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyPageSharingListRequest {
    private Long id;                // 게시글 번호
    private String title ;          // 제목
    private String content ;        // 내용
    private String tradeLocation;   // 거래장소

}
