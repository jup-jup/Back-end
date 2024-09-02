package com.jupjup.www.jupjup.giveaway.controller;

import com.jupjup.www.jupjup.config.JWTUtil;
import com.jupjup.www.jupjup.giveaway.entity.Giveaway;
import com.jupjup.www.jupjup.giveaway.dto.CreateGiveawayRequest;
import com.jupjup.www.jupjup.giveaway.dto.GiveawayDetailResponse;
import com.jupjup.www.jupjup.giveaway.dto.GiveawayListResponse;
import com.jupjup.www.jupjup.giveaway.dto.UpdateGiveawayRequest;
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
import org.springframework.http.HttpStatus;
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
            @ApiResponse(responseCode = "201", description = "나눔 정상 생성 완료",
                    headers = @Header(name = HttpHeaders.LOCATION, description = "나눔 세부정보 url")),
            @ApiResponse(responseCode = "401", description = "등록되지 않은 유저입니다.")
    })
    @PostMapping("")
    public ResponseEntity<?> addGiveaway(@RequestBody CreateGiveawayRequest request, @Valid @RequestHeader("Authorization") String header) {
        try {
            String token = header.substring(BEARER_PREFIX.length());
            Long userId = JWTUtil.getUserIdFromAccessToken(token);
            Giveaway giveaway = giveawayService.save(request, userId);

            return ResponseEntity
                    .created(URI.create(String.format("/api/v1/giveaways/%d", giveaway.getId())))
                    .build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        }
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
    public ResponseEntity<List<GiveawayListResponse>> getList(
            @PageableDefault(size = 30, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        List<GiveawayListResponse> list = giveawayService.findAll(pageable);
        return ResponseEntity
                .ok()
                .body(list);
    }

    @Operation(summary = "get giveaway detail", description = "나눔 세부정보를 위한 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(schema = @Schema(implementation = GiveawayDetailResponse.class))}),
            @ApiResponse(responseCode = "404", description = "잘못된 나눔 id")
    })
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getGiveaway(@PathVariable Long id) {

        try {
            GiveawayDetailResponse detail = giveawayService.findById(id);
            return ResponseEntity
                    .ok()
                    .body(detail);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @Operation(summary = "update giveaway", description = "나눔 수정을 위한 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "404", description = "잘못된 나눔 id")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateGiveaway(@PathVariable Long id
            , @RequestBody UpdateGiveawayRequest request
            , @Valid @RequestHeader("Authorization") String header) {

        try {
            String token = header.substring(BEARER_PREFIX.length());
            Long userId = JWTUtil.getUserIdFromAccessToken(token);

            Giveaway giveaway = giveawayService.update(id, request, userId);

            return ResponseEntity
                    .noContent()
                    .build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    // 나눔 업데이트
    @Operation(summary = "delete giveaway", description = "나눔 삭제를 위한 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "404", description = "잘못된 나눔 id")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGiveaway(@PathVariable Long id, @Valid @RequestHeader("Authorization") String header) {
        try {
            String token = header.substring(BEARER_PREFIX.length());
            Long userId = JWTUtil.getUserIdFromAccessToken(token);
            giveawayService.delete(id, userId);

            return ResponseEntity
                    .noContent()
                    .build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

}
