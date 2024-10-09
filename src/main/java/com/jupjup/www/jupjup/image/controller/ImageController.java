package com.jupjup.www.jupjup.image.controller;

import com.jupjup.www.jupjup.config.security.JWTUtil;
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

    @Operation(summary = "upload image", description = "이미지 업로드 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UploadImageResponse.class)))
            }),
            @ApiResponse(responseCode = "500", description = "파일 업로드 에러")
    })
    @PostMapping("")
    public List<UploadImageResponse> upload(@RequestParam("files") List<MultipartFile> files, @Valid @RequestHeader("Authorization") String accessToken) throws IOException {
        // TODO: authorization header 에서 userId 뽑아오는 방법이 이게 최선일까..
        // boram : JWTUtil 에 별도로 빼서 중복코드 없애봤어요 !
        Long userId = JWTUtil.parseUserIdFromToken(accessToken);
        return imageService.save(files, userId);
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
    public GetImageResponse getImage(@PathVariable Long id) {
        return imageService.find(id);
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
    public ResponseEntity<?> showImage(@PathVariable Long id) throws IOException {
        DisplayImageDTO dto = imageService.display(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dto.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dto.getEncodedFileName() + "\"")
                .body(dto.getResource());
    }

}
