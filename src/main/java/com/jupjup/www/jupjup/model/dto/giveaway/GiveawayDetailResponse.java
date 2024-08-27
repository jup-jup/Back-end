package com.jupjup.www.jupjup.model.dto.giveaway;

import com.jupjup.www.jupjup.domain.entity.giveaway.Giveaway;
import com.jupjup.www.jupjup.domain.enums.GiveawayStatus;
import com.jupjup.www.jupjup.image.entity.Image;
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

    private Long giverId; // 글쓴이 id

    private String giverName; // 글쓴이 이름

    private String giverProfile; // TODO: 프로필이 무엇인지?

    private boolean isWishlist; // 찜 목록 (관심 목록)

    private LocalDateTime createdAt; // 등록 날짜

    private List<Long> imageIds; // 이미지 아이디 리스트

    // TODO: 여기부터는 후순위 작업 (추가 설계 필요)
    private String location; // TODO: 장소 데이터 어떤 식으로 저장하고 내려줘야하는지 확인
    private Integer chatCnt; // 코멘트 수?
    private Integer viewCnt;

    public static GiveawayDetailResponse toDTO(Giveaway giveaway) {
        return GiveawayDetailResponse.builder()
                .giveawayId(giveaway.getGiverId())
                .title(giveaway.getTitle())
                .description(giveaway.getDescription())
                .status(giveaway.getStatus())
                .giverId(giveaway.getGiverId())
//                .giverProfile()
                .createdAt(giveaway.getCreatedAt())
                .imageIds(giveaway.getImages().stream()
                        .map(Image::getId)
                        .toList())
                .build();
    }

}
