package com.jupjup.www.jupjup.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OauthRegistrationId {
    NAVER("Naver", "https://www.naver.com"),
    GOOGLE("Google", "https://www.google.com"),
    KAKAO("Kakao", "https://www.kakao.com");

    private final String displayName;
    private final String url;

}