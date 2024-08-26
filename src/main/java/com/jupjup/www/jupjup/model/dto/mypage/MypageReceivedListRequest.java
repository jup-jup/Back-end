package com.jupjup.www.jupjup.model.dto.mypage;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class MypageReceivedListRequest {

    private Long id;                // 게시글 번호
    private String title ;          // 제목
    private String content ;        // 내용
    private String tradeLocation;   // 거래장소

}
