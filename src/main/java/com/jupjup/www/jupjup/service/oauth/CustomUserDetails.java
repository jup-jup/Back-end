package com.jupjup.www.jupjup.service.oauth;

import com.jupjup.www.jupjup.model.dto.user.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Slf4j
public record CustomUserDetails(UserResponse userDTO) implements OAuth2User {

    @Override
    // 각 소셜 로그인 인증 서버가 응답하는 속성값이 달라 획일화가 어려워 사용하지 않음
    public Map<String, Object> getAttributes() {
        return null;
    }

    // 권한 리턴 메소드
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList((GrantedAuthority) userDTO::getRole);
    }

    @Override
    public String getName() {
        return userDTO.getUsername();
    }

    public String getUserEmail(){
        return userDTO.getUserEmail();
    }

    public String getProviderId(){
        return userDTO.getProviderId();
    }

}
