package com.jupjup.www.jupjup.service.giveaway;

import com.jupjup.www.jupjup.domain.entity.giveaway.Giveaway;
import com.jupjup.www.jupjup.domain.repository.GiveawayRepository;
import com.jupjup.www.jupjup.domain.repository.UserRepository;
import com.jupjup.www.jupjup.model.dto.giveaway.CreateGiveawayRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GiveawayService {

    private final GiveawayRepository giveawayRepository;
    private final UserRepository userRepository;

    public Giveaway save(CreateGiveawayRequest request, String userEmail) {
        // TODO: userRepository 쪽이긴한데 해당 유저가 없는 경우도 처리 필요할듯해보임
        Long userId = userRepository.findByUserEmail(userEmail).getId();

        Giveaway giveaway = Giveaway.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .giverId(userId)
                .build();

        return giveawayRepository.save(giveaway);
    }

}
