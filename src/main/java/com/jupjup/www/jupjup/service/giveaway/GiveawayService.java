package com.jupjup.www.jupjup.service.giveaway;

import com.jupjup.www.jupjup.domain.entity.giveaway.Giveaway;
import com.jupjup.www.jupjup.domain.repository.GiveawayRepository;
import com.jupjup.www.jupjup.domain.repository.UserRepository;
import com.jupjup.www.jupjup.model.dto.giveaway.CreateGiveawayRequest;
import com.jupjup.www.jupjup.model.dto.giveaway.GiveawayListResponse;
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
            throw new IllegalArgumentException("unregistered user");
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

}
