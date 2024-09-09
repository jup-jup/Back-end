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

    private String userName;

    private GiveawayStatus status; // 상태

    private LocalDateTime createdAt;

    private List<Long> imageIds; // 이미지 아이디 리스트

    private String location;

    private Long viewCnt; // 조회수

    // TODO: 여기부터는 후순위 작업 (추가 설계 필요)
    // boram : chatCnt 이거 주석안하면 DTO 생성 시 null 값이 들어간 채 리스트가 내려질까요 ?/
//    private Long chatCnt; // 코멘트 수?

    public GiveawayListResponse(Long id) {
        this.giveawayId = id;
    }

    public static GiveawayListResponse toDTO(Giveaway giveaway) {
        return GiveawayListResponse.builder()
                .userName(giveaway.getGiver().getName())
                .viewCnt(giveaway.getViewCount())
                .giveawayId(giveaway.getId())
                .title(giveaway.getTitle())
                .status(giveaway.getStatus())
                .createdAt(giveaway.getCreatedAt())
                .imageIds(giveaway.getImages().stream()
                        .map(Image::getId)
                        .toList())
                .location(giveaway.getLocation())
                .viewCnt(giveaway.getViewCount())
                .build();
    }

}
