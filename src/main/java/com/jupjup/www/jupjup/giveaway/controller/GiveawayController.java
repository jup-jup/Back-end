package com.jupjup.www.jupjup.giveaway.controller;

import com.jupjup.www.jupjup.config.security.JWTUtil;
import com.jupjup.www.jupjup.giveaway.dto.*;
import com.jupjup.www.jupjup.giveaway.entity.Giveaway;
import com.jupjup.www.jupjup.giveaway.service.GiveawayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@Tag(name = "Giveaway", description = "나눔 API")
@RequiredArgsConstructor
@RestController
// TODO: 전체 서버 관점에서 /api/v1 prefix 를 사용한다면 application.yml 에서 설정하는 것이 좋아보임
@RequestMapping("/api/v1/giveaways")

public class GiveawayController {

    private final GiveawayService giveawayService;
    private static final String BEARER_PREFIX = "Bearer ";

    @Operation(summary = "add giveaway", description = "나눔 올리기 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "나눔 정상 생성 완료", headers = @Header(name = HttpHeaders.LOCATION, description = "나눔 세부정보 url"))
    })
    @PostMapping("")
    public ResponseEntity<?> addGiveaway(@RequestBody CreateGiveawayRequest request, @Valid @RequestHeader("Authorization") String header) {
        String token = header.substring(BEARER_PREFIX.length());
        Long userId = JWTUtil.getUserIdFromAccessToken(token);
        Giveaway giveaway = giveawayService.save(request, userId);

        return ResponseEntity
                .created(URI.create(String.format("/api/v1/giveaways/%d", giveaway.getId())))
                .build();
    }

    @Operation(summary = "get giveaway list", description = "나눔 리스트를 위한 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GiveawayListResponse.class)))}
            )
    })
    @GetMapping("/list")
    public List<GiveawayListResponse> getList(
            @PageableDefault(size = 30, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return giveawayService.findAll(pageable);
    }

    @Operation(summary = "get giveaway detail", description = "나눔 세부정보를 위한 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(schema = @Schema(implementation = GiveawayDetailResponse.class))}),
            @ApiResponse(responseCode = "404", description = "잘못된 나눔 id 입니다.")
    })
    @GetMapping("/detail/{id}")
    public GiveawayDetailResponse getGiveaway(@PathVariable Long id) {
        return giveawayService.getDetail(id);
    }

    @Operation(summary = "update giveaway", description = "나눔 수정을 위한 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "404", description = "잘못된 나눔 id 입니다.")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateGiveaway(@PathVariable Long id
            , @RequestBody UpdateGiveawayRequest request
            , @Valid @RequestHeader("Authorization") String header) {
        String token = header.substring(BEARER_PREFIX.length());
        Long userId = JWTUtil.getUserIdFromAccessToken(token);

        giveawayService.update(id, request, userId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @Operation(summary = "update giveaway status", description = "나눔 상태 업데이트를 위한 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "400", description = "잘못된 나눔 id 입니다. / 잘못된 상태 변경 요청입니다.")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateGiveawayStatus(
            @PathVariable Long id,
            @RequestBody UpdateGiveawayStatusRequest request,
            @Valid @RequestHeader("Authorization") String header
    ) {
        String token = header.substring(BEARER_PREFIX.length());
        Long userId = JWTUtil.getUserIdFromAccessToken(token);

        giveawayService.updateStatus(id, request, userId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @Operation(summary = "delete giveaway", description = "나눔 삭제를 위한 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "404", description = "잘못된 나눔 id 입니다.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGiveaway(@PathVariable Long id, @Valid @RequestHeader("Authorization") String header) {
        String token = header.substring(BEARER_PREFIX.length());
        Long userId = JWTUtil.getUserIdFromAccessToken(token);
        giveawayService.delete(id, userId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("/search")
    public List<GiveawayListResponse> searchGiveaway(
            @RequestParam String keyword,
            @PageableDefault(sort = "created_at", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return giveawayService.searchAllByKeyword(keyword, pageable);
    }

}
