package com.jupjup.www.jupjup.giveaway.dto;

import com.jupjup.www.jupjup.giveaway.entity.Giveaway;
import com.jupjup.www.jupjup.giveaway.enums.GiveawayStatus;
import com.jupjup.www.jupjup.image.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class GiveawayListResponse {

    private Long giveawayId;

    private String title; // 제목

    private GiveawayStatus status; // 상태

    private LocalDateTime createdAt;

    private List<Long> imageIds; // 이미지 아이디 리스트

    // TODO: 여기부터는 후순위 작업 (추가 설계 필요)
    private String location; // TODO: 판매 장소. 장소 데이터 어떤 식으로 저장하고 내려줘야하는지 확인
    private Integer chatCnt; // 코멘트 수?
    private Integer viewCnt; // 조회수

    public GiveawayListResponse(Long id) {
        this.giveawayId = id;
    }

    public static GiveawayListResponse toDTO(Giveaway giveaway) {
        return GiveawayListResponse.builder()
                .giveawayId(giveaway.getId())
                .title(giveaway.getTitle())
                .status(giveaway.getStatus())
                .createdAt(giveaway.getCreatedAt())
                .imageIds(giveaway.getImages().stream()
                        .map(Image::getId)
                        .toList())
                .build();
    }

}
