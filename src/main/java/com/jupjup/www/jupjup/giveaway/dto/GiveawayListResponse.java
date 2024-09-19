package com.jupjup.www.jupjup.giveaway.dto;

import com.jupjup.www.jupjup.giveaway.entity.Giveaway;
import com.jupjup.www.jupjup.giveaway.enums.GiveawayStatus;
import com.jupjup.www.jupjup.image.dto.GetImageResponse;
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

    private String description;

    private String userName;

    private GiveawayStatus status; // 상태

    private LocalDateTime createdAt;

    private List<GetImageResponse> images;

    private String location;

    private Long viewCnt; // 조회수

    private int chatCnt; // 채팅방 수

    public GiveawayListResponse(Long id) {
        this.giveawayId = id;
    }

    public static GiveawayListResponse toDTO(Giveaway giveaway) {
        return GiveawayListResponse.builder()
                .userName(giveaway.getGiver().getName())
                .viewCnt(giveaway.getViewCount())
                .giveawayId(giveaway.getId())
                .title(giveaway.getTitle())
                .description(giveaway.getDescription())
                .status(giveaway.getStatus())
                .createdAt(giveaway.getCreatedAt())
                .images(giveaway.getImages().stream()
                        .map(GetImageResponse::toDTO)
                        .toList())
                .location(giveaway.getLocation())
                .viewCnt(giveaway.getViewCount())
                .chatCnt(giveaway.getChatRooms().size())
                .build();
    }

}
