package com.jupjup.www.jupjup.service.giveaway;

import com.jupjup.www.jupjup.domain.entity.User;
import com.jupjup.www.jupjup.domain.entity.giveaway.Giveaway;
import com.jupjup.www.jupjup.domain.repository.GiveawayRepository;
import com.jupjup.www.jupjup.domain.repository.UserRepository;
import com.jupjup.www.jupjup.model.dto.giveaway.CreateGiveawayRequest;
import com.jupjup.www.jupjup.model.dto.giveaway.GiveawayDetailResponse;
import com.jupjup.www.jupjup.model.dto.giveaway.GiveawayListResponse;
import com.jupjup.www.jupjup.model.dto.giveaway.UpdateGiveawayRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GiveawayService {

    private final GiveawayRepository giveawayRepository;
    private final UserRepository userRepository;

    public Giveaway save(CreateGiveawayRequest request, String userEmail) {
        Long userId = userRepository.findByUserEmail(userEmail).getId();
        if (userId == null) {
            throw new IllegalArgumentException("등록되지 않은 유저입니다.");
        }

        Giveaway giveaway = Giveaway.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .giverId(userId)
                .build();

        return giveawayRepository.save(giveaway);
    }

    public List<GiveawayListResponse> findAll() {
        List<Giveaway> list = giveawayRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        return list.stream()
                .map(o -> GiveawayListResponse.builder()
                        .giveawayId(o.getId())
                        .title(o.getTitle())
                        .status(o.getStatus())
                        .createdAt(o.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    public GiveawayDetailResponse findById(Long id) {
        Giveaway giveaway = giveawayRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 나눔 id"));

        // TODO: 일단 예외처리는 했는데 탈퇴한 유저 같은 경우?
        User giver = userRepository.findById(giveaway.getGiverId())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 유저 아이디"));

        // TODO: Entity to DTO 로직을 좀 더 예쁘고 수정에 용이하게 하는 방법이 없을까?
        return GiveawayDetailResponse.builder()
                .giveawayId(giveaway.getId())
                .title(giveaway.getTitle())
                .description(giveaway.getTitle())
                .status(giveaway.getStatus())
                .giverId(giver.getId())
                .giverName(giver.getName())
                .createdAt(giveaway.getCreatedAt())
                .build();
    }

    @Transactional
    public Giveaway update(Long id, UpdateGiveawayRequest request, String userEmail) {
        Long userId = userRepository.findByUserEmail(userEmail).getId();
        if (userId == null) {
            throw new IllegalArgumentException("등록되지 않은 유저입니다.");
        }

        Giveaway giveaway = giveawayRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 나눔 id"));

        if (!userId.equals(giveaway.getGiverId())) {
            throw new IllegalArgumentException("잘못된 유저 요청입니다.");
        }

        giveaway.update(request.getTitle(), request.getDescription(), request.getStatus());

        return giveaway;
    }

}
