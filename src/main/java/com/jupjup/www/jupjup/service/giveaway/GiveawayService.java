package com.jupjup.www.jupjup.service.giveaway;

import com.jupjup.www.jupjup.domain.entity.User;
import com.jupjup.www.jupjup.domain.entity.giveaway.Giveaway;
import com.jupjup.www.jupjup.domain.repository.GiveawayRepository;
import com.jupjup.www.jupjup.domain.repository.UserRepository;
import com.jupjup.www.jupjup.image.entity.Image;
import com.jupjup.www.jupjup.image.repository.ImageRepository;
import com.jupjup.www.jupjup.model.dto.giveaway.CreateGiveawayRequest;
import com.jupjup.www.jupjup.model.dto.giveaway.GiveawayDetailResponse;
import com.jupjup.www.jupjup.model.dto.giveaway.GiveawayListResponse;
import com.jupjup.www.jupjup.model.dto.giveaway.UpdateGiveawayRequest;
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
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    public Giveaway save(CreateGiveawayRequest request, String userEmail) {
        Long userId = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 유저입니다."))
                .getId();

        List<Image> images = imageRepository.findAllById(request.getImageIds());

        Giveaway giveaway = Giveaway.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .giverId(userId)
                .images(images)
                .build();

        return giveawayRepository.save(giveaway);
    }

    public List<GiveawayListResponse> findAll(Pageable pageable) {
        Page<Giveaway> list = giveawayRepository.findAll(pageable);
        return list.stream()
                .map(GiveawayListResponse::toDTO)
                .collect(Collectors.toList());
    }

    public GiveawayDetailResponse findById(Long id) {
        Giveaway giveaway = giveawayRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 나눔 id"));

        // TODO: 일단 예외처리는 했는데 탈퇴한 유저 같은 경우?
        User giver = userRepository.findById(giveaway.getGiverId())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 유저 아이디"));

        // TODO: Entity to DTO 로직을 좀 더 예쁘고 수정에 용이하게 하는 방법이 없을까?
        return GiveawayDetailResponse.toDTO(giveaway);
    }

    @Transactional
    public Giveaway update(Long id, UpdateGiveawayRequest request, String userEmail) {
        Giveaway giveaway = authorizeGiveawayUser(id, userEmail);

        List<Image> images = List.of();
        if (!request.getImageIds().isEmpty()) {
            images = imageRepository.findAllById(request.getImageIds());
        }

        giveaway.update(request.getTitle(), request.getDescription(), request.getStatus(), images);

        return giveaway;
    }

    public void delete(Long id, String userEmail) {
        Giveaway giveaway = authorizeGiveawayUser(id, userEmail);
        giveawayRepository.delete(giveaway);
    }

    public Giveaway authorizeGiveawayUser(Long id, String userEmail) {
        Long userId = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 유저입니다."))
                .getId();

        Giveaway giveaway = giveawayRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 나눔 id"));

        if (!userId.equals(giveaway.getGiverId())) {
            throw new IllegalArgumentException("잘못된 유저 요청입니다.");
        }

        return giveaway;
    }

}
