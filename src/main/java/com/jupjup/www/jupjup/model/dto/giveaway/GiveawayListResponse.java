package com.jupjup.www.jupjup.model.dto.giveaway;

import com.jupjup.www.jupjup.domain.enums.GiveawayStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class GiveawayListResponse {

    private Long giveawayId;

    private String title; // 제목

    private GiveawayStatus status; // 상태

    private LocalDateTime createdAt;

    // TODO: 여기부터는 후순위 작업 (추가 설계 필요)
    private String location; // TODO: 판매 장소. 장소 데이터 어떤 식으로 저장하고 내려줘야하는지 확인
    private List<String> images; // 이미지 파일 경로
    private Integer chatCnt; // 코멘트 수?
    private Integer viewCnt; // 조회수

}
