package com.jupjup.www.jupjup.response;


import lombok.RequiredArgsConstructor;

import java.util.Map;

public class KakaoResponse implements OAuth2Response {

    private final Map<String, Object> attributes;
    private final Map<String, Object> properties;
    private final Map<String, Object> kakaoAccount;


    public KakaoResponse(Map<String, Object> attributes) {
        this.attributes = attributes; // 전체 attributes 저장
        this.properties = (Map<String, Object>) attributes.get("properties"); // 사용자 프로필 정보
        this.kakaoAccount = (Map<String, Object>) attributes.get("kakao_account"); // 카카오 계정 정보
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getEmail() {
        return properties.get("").toString();
    }

    @Override
    public String getName() {
        if (properties != null) {
            return (String) properties.get("nickname");
        }
        return null;
    }

    /**
     * @author        : boramkim
     * @since         : 2024. 8. 3.
     * @description   : 카톡 프로필 추출 메서드
     */
    public String getProfileImageUrl() {
        if (kakaoAccount != null && kakaoAccount.containsKey("profile")) {
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            return (String) profile.get("profile_image_url");
        }
        return null;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

}
