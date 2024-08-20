package com.jupjup.www.jupjup.controller;

import com.jupjup.www.jupjup.domain.entity.giveaway.Giveaway;
import com.jupjup.www.jupjup.model.dto.giveaway.CreateGiveawayRequest;
import com.jupjup.www.jupjup.model.dto.giveaway.GiveawayDetailResponse;
import com.jupjup.www.jupjup.model.dto.giveaway.GiveawayListResponse;
import com.jupjup.www.jupjup.model.dto.giveaway.UpdateGiveawayRequest;
import com.jupjup.www.jupjup.service.giveaway.GiveawayService;
import com.jupjup.www.jupjup.service.oauth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RequiredArgsConstructor
@RestController
// TODO: 전체 서버 관점에서 /api/v1 prefix 를 사용한다면 application.yml 에서 설정하는 것이 좋아보임
@RequestMapping("/api/v1/giveaways")
public class GiveawayController {

    private final GiveawayService giveawayService;

    // 나눔 리스트
    @GetMapping("")
    public ResponseEntity<List<GiveawayListResponse>> getList() {

        return ResponseEntity
                .ok()
                .build();
    }

    // 나눔 상세 페이지
    @GetMapping("/{id}")
    public ResponseEntity<GiveawayDetailResponse> getGiveaway(@PathVariable Long id) {

        return ResponseEntity
                .ok()
                .build();
    }

    // 나눔 올리기
    @PostMapping("")
    public ResponseEntity<?> addGiveaway(@RequestBody CreateGiveawayRequest request, Authentication authentication) {
        try {
            CustomUserDetails customOAuth2User = (CustomUserDetails) authentication.getPrincipal();
            Giveaway giveaway = giveawayService.save(request, customOAuth2User.getUserEmail());

            return ResponseEntity
                    .created(URI.create(String.format("/giveaways/%d", giveaway.getId())))
                    .build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // 나눔 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<?> updateGiveaway(@PathVariable Long id,
                                            @RequestBody UpdateGiveawayRequest request) {

        return ResponseEntity
                .ok()
                .build();
    }

    // 나눔 업데이트
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGiveaway(@PathVariable Long id) {

        return ResponseEntity
                .noContent()
                .build();
    }

}
