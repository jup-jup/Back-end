package com.jupjup.www.jupjup.user.service;

import com.jupjup.www.jupjup.giveaway.dto.GiveawayListResponse;
import com.jupjup.www.jupjup.giveaway.entity.Giveaway;
import com.jupjup.www.jupjup.giveaway.enums.GiveawayStatus;
import com.jupjup.www.jupjup.giveaway.repository.GiveawayRepository;
import com.jupjup.www.jupjup.user.enums.MyPageType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final GiveawayRepository giveawayRepository;

    public List<GiveawayListResponse> findAllGiverList(Pageable pageable, Long userId) {
        Page<Giveaway> list = giveawayRepository.findAllByGiverId(pageable,userId);
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

    public static ResponseEntity<?> test(String type, Object giverData, Object receiverData){

        if (type.toLowerCase().equals(MyPageType.GIVER.getType())) {
            return ResponseEntity.ok(giverData);
        } else if (type.toLowerCase().equals(MyPageType.RECEIVER.getType())) {
            return ResponseEntity.ok(receiverData);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
