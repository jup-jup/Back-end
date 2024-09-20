package com.jupjup.www.jupjup.giveaway.service;

import com.jupjup.www.jupjup.giveaway.dto.*;
import com.jupjup.www.jupjup.giveaway.entity.Giveaway;
import com.jupjup.www.jupjup.giveaway.repository.GiveawayRepository;
import com.jupjup.www.jupjup.image.entity.Image;
import com.jupjup.www.jupjup.image.repository.ImageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GiveawayService {

    private final GiveawayRepository giveawayRepository;
    private final ImageRepository imageRepository;

    public Giveaway save(CreateGiveawayRequest request, Long userId) {
        List<Image> images = imageRepository.findAllById(request.getImageIds());

        Giveaway giveaway = Giveaway.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .giverId(userId)
                .images(images)
                .location(request.getLocation())
                .build();

        return giveawayRepository.save(giveaway);
    }

    public List<GiveawayListResponse> findAll(Pageable pageable) {
        // 유저 정보, 채팅방 수 함께 리턴
        Page<Giveaway> list = giveawayRepository.findAllGiveawaysWithUsersAndRooms(pageable);
        return list.stream()
                .map(GiveawayListResponse::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public GiveawayDetailResponse getDetail(Long id) {
        Giveaway giveaway = findById(id);
        giveaway.updateViewCnt();

        return GiveawayDetailResponse.of(giveaway);
    }

    public Giveaway findById(Long id) {
        return giveawayRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 나눔 id"));
    }

    @Transactional
    public Giveaway update(Long id, UpdateGiveawayRequest request, Long userId) {
        Giveaway giveaway = authorizeGiveawayUser(id, userId);

        List<Image> images = List.of();
        if (!request.getImageIds().isEmpty()) {
            images = imageRepository.findAllById(request.getImageIds());
        }
        giveaway.update(request.getTitle(), request.getDescription(), images);

        return giveaway;
    }

    /*
    PENDING
        나눔하는 유저만 변경 가능
        RESERVED 상태에서 거래 취소로 돌리는 경우 예약자 정보도 제거
    RESERVED
        나눔하는 유저만 변경 가능
        상대 user_id 도 함께 넘겨야 함.
    COMPLETED
        나눔하는 유저, 나눔받는 유저 모두 상태 변경 가능
        예약 상태에서만 변경 가능
        예약 상태에서 예약자로 등록된 상태여야 함
     */
    public void updateStatus(Long id, UpdateGiveawayStatusRequest request, Long userId) {
        Giveaway giveaway = giveawayRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 나눔 id"));

        if (request.getStatus().equals(giveaway.getStatus())) {
            return;
        }

        if (!giveaway.validateUpdateStatus(request.getStatus(), userId)) {
            throw new IllegalArgumentException("잘못된 상태 변경 요청입니다.");
        }

        giveaway.updateStatus(request.getStatus(), request.getReceiverId());
    }


    public void delete(Long id, Long userId) {
        Giveaway giveaway = authorizeGiveawayUser(id, userId);
        giveawayRepository.delete(giveaway);
    }

    public Giveaway authorizeGiveawayUser(Long id, Long userId) {
        Giveaway giveaway = giveawayRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 나눔 id"));

        if (!userId.equals(giveaway.getGiverId())) {
            throw new IllegalArgumentException("잘못된 유저 요청입니다.");
        }
        return giveaway;
    }

    public List<GiveawayListResponse> searchAllByKeyword(String keyword, Pageable pageable) {
        // 유저 정보, 채팅방 수 함께 리턴
        Page<Giveaway> list = giveawayRepository.findAllByKeywordWithUsersAndRooms(keyword + "*", pageable);
        return list.stream()
                .map(GiveawayListResponse::toDTO)
                .collect(Collectors.toList());
    }

}
