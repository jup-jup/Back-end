package com.jupjup.www.jupjup.user.service;

import com.jupjup.www.jupjup.giveaway.dto.GiveawayListResponse;
import com.jupjup.www.jupjup.giveaway.entity.Giveaway;
import com.jupjup.www.jupjup.giveaway.repository.GiveawayRepository;
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


    public List<GiveawayListResponse> findAll(Pageable pageable) {
        Page<Giveaway> list = giveawayRepository.findAll(pageable);
        return list.stream()
                .map(GiveawayListResponse::toDTO)
                .collect(Collectors.toList());
    }


}
