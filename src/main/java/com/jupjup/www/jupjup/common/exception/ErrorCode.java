package com.jupjup.www.jupjup.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/*
    코드 정의는 4xx 대는 NAME_4**, 5xx 대는 NAME_5** 로 하며, 공통 도메인에서 사용할 수 있는 에러 코드의 경우 GEN_ 으로 시작한다.
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 4xx
    // 공통
    NOT_EXIST(HttpStatus.BAD_REQUEST, "GEN_401", "Not Exist"),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "GEN_402", "Invalid Request"),
    NO_PERMISSION(HttpStatus.BAD_REQUEST, "GEN_403", "No Permission"),

    // auth
    INVALID_TOKEN_SECRET_KEY(HttpStatus.UNAUTHORIZED, "AUTH_401", "The provided keyValue is not a valid SecretKey."),
    UNSUPPORTED_RESOURCE_PROVIDER(HttpStatus.BAD_REQUEST, "AUTH_402", "Unsupported registration provider"),

    // 5xx
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "GEN_501", "Internal Server Error"),

    // 파일
    FILE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "FILE_501", "An error occurred during file upload."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
