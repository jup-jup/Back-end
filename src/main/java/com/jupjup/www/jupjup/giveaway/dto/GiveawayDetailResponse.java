package com.jupjup.www.jupjup.giveaway.dto;

import com.jupjup.www.jupjup.giveaway.entity.Giveaway;
import com.jupjup.www.jupjup.giveaway.enums.GiveawayStatus;
import com.jupjup.www.jupjup.image.dto.GetImageResponse;
import com.jupjup.www.jupjup.user.dto.user.UserDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
public class GiveawayDetailResponse {

    // 기획서 pdf 에 내가 올린 게시물인지, 남이 올린 게시물인지 확인하는 데이터가 필요하다고 되어 있는데 이 부분은 token 에 본인 id 가 있고,
    // 작성자 user_id 도 내려주니 이 부분은 클라이언트에서 데이터 확인 후 처리하면 될듯합니다.

    private Long giveawayId;

    private String title; // 나눔 제목

    private String description; // 나눔 설명

    private GiveawayStatus status; // 상태

    private UserDTO giver;

    private LocalDateTime createdAt; // 등록 날짜

    private List<GetImageResponse> images;

    private String location;

    private Long viewCnt; // 조회수

    private int chatCnt; // 채팅방 수

    public static GiveawayDetailResponse of(Giveaway giveaway) {
        return GiveawayDetailResponse.builder()
                .giveawayId(giveaway.getId())
                .title(giveaway.getTitle())
                .description(giveaway.getDescription())
                .status(giveaway.getStatus())
                .giver(UserDTO.of(giveaway.getGiver()))
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
