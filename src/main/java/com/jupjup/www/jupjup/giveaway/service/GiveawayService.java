package com.jupjup.www.jupjup.giveaway.service;

import com.jupjup.www.jupjup.user.entity.User;
import com.jupjup.www.jupjup.giveaway.entity.Giveaway;
import com.jupjup.www.jupjup.giveaway.repository.GiveawayRepository;
import com.jupjup.www.jupjup.user.repository.UserRepository;
import com.jupjup.www.jupjup.image.entity.Image;
import com.jupjup.www.jupjup.image.repository.ImageRepository;
import com.jupjup.www.jupjup.giveaway.dto.CreateGiveawayRequest;
import com.jupjup.www.jupjup.giveaway.dto.GiveawayDetailResponse;
import com.jupjup.www.jupjup.giveaway.dto.GiveawayListResponse;
import com.jupjup.www.jupjup.giveaway.dto.UpdateGiveawayRequest;
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
//        Page<Giveaway> list = giveawayRepository.findAll(pageable);
        // 유저 정보 함께 리턴
        Page<Giveaway> list = giveawayRepository.findAllWithUser(pageable);
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

        return GiveawayDetailResponse.toDTO(giveaway);
    }

    @Transactional
    public Giveaway update(Long id, UpdateGiveawayRequest request, Long userId) {
        Giveaway giveaway = authorizeGiveawayUser(id, userId);

        List<Image> images = List.of();
        if (!request.getImageIds().isEmpty()) {
            images = imageRepository.findAllById(request.getImageIds());
        }
        giveaway.update(request.getTitle(), request.getDescription(), request.getStatus(), images);

        return giveaway;
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

}
