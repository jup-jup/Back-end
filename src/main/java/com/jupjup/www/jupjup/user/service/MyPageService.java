package com.jupjup.www.jupjup.user.service;

import com.jupjup.www.jupjup.common.exception.CustomException;
import com.jupjup.www.jupjup.common.exception.ErrorCode;
import com.jupjup.www.jupjup.giveaway.dto.GiveawayDetailResponse;
import com.jupjup.www.jupjup.giveaway.dto.GiveawayListResponse;
import com.jupjup.www.jupjup.giveaway.entity.Giveaway;
import com.jupjup.www.jupjup.giveaway.enums.GiveawayStatus;
import com.jupjup.www.jupjup.giveaway.repository.GiveawayRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final GiveawayRepository giveawayRepository;

    public List<GiveawayListResponse> findAllGiverList(Pageable pageable, Long userId) {
        Page<Giveaway> list = giveawayRepository.findAllByGiverId(pageable,userId, GiveawayStatus.COMPLETED);
        return list.stream()
                .map(GiveawayListResponse::toDTO)
                .collect(Collectors.toList());
    }

    public List<GiveawayListResponse> findAllReceiverList(Pageable pageable,Long userId) {
        Page<Giveaway> list = giveawayRepository.findAllByUsersAndStatus(pageable,userId, GiveawayStatus.COMPLETED);
        return list.stream()
                .map(GiveawayListResponse::toDTO)
                .collect(Collectors.toList());
    }

    public GiveawayDetailResponse getDetail(Long id) {
        Giveaway giveaway = giveawayRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST));

        return GiveawayDetailResponse.of(giveaway);
    }

}
