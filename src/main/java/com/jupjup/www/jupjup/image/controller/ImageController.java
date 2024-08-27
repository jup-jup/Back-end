package com.jupjup.www.jupjup.image.controller;

import com.jupjup.www.jupjup.config.JWTUtil;
import com.jupjup.www.jupjup.image.dto.DisplayImageDTO;
import com.jupjup.www.jupjup.image.dto.GetImageResponse;
import com.jupjup.www.jupjup.image.dto.UploadImageResponse;
import com.jupjup.www.jupjup.image.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "Image", description = "이미지 관련 API")
@SecurityRequirement(name = "JWT")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    private final ImageService imageService;

    private static final String BEARER_PREFIX = "Bearer ";

    @Operation(summary = "upload image", description = "이미지 업로드 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UploadImageResponse.class)))
            }),
            @ApiResponse(responseCode = "500", description = "파일 업로드 에러")
    })
    @PostMapping("")
    public ResponseEntity<?> upload(@RequestBody List<MultipartFile> files, @Valid @RequestHeader("Authorization") String header) {
        // TODO: authorization header 에서 userId 뽑아오는 방법이 이게 최선일까..
        String token = header.substring(BEARER_PREFIX.length());
        Long userId = JWTUtil.getUserIdFromAccessToken(token);

        try {
            List<UploadImageResponse> images = imageService.save(files, userId);
            return ResponseEntity
                    .ok()
                    .body(images);
        } catch (IOException e) {
            return ResponseEntity
                    .internalServerError()
                    .body(e.getMessage());
        }
    }

    @Operation(summary = "get image", description = "이미지 메타 정보를 가져오는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetImageResponse.class)))
            }),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 id 입니다.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getImage(@PathVariable Long id) {
        try {
            GetImageResponse resp = imageService.find(id);
            return ResponseEntity
                    .ok()
                    .body(resp);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @Operation(summary = "display image", description = "실제 이미지를 보여주기 위한 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    headers = {@Header(name = HttpHeaders.CONTENT_DISPOSITION)},
                    content = {
                            @Content(mediaType = "image/png, application/octet-stream",
                                    schema = @Schema(implementation = Resource.class),
                                    array = @ArraySchema(schema = @Schema(implementation = GetImageResponse.class)))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 id 입니다."),
            @ApiResponse(responseCode = "500", description = "서버에 파일이 없는 경우")
    })
    @GetMapping("/display/{id}")
    public ResponseEntity<?> showImage(@PathVariable Long id) {
        try {
            DisplayImageDTO dto = imageService.display(id);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(dto.getContentType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dto.getEncodedFileName() + "\"")
                    .body(dto.getResource());
        } catch (IOException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        } catch (IllegalArgumentException e) {
            // TODO: 값이 없는 경우 200으로 처리할지 400으로 처리할지, 404로 처리할지..
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

}
