package com.jupjup.www.jupjup.model.dto.mypage;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class MyPageSharingListRequest {
    private Long id;
    private String title ;          // 제목
    private String content ;        // 내용
    private String tradeLocation;   // 거래장소

    @Override
    public String toString() {
        return "MyPageSharingListRequest{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", tradeLocation='" + tradeLocation + '\'' +
                '}';
    }
}
