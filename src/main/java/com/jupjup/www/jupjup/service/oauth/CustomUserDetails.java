package com.jupjup.www.jupjup.service.oauth;

<<<<<<< HEAD:src/main/java/com/jupjup/www/jupjup/oauth2/CustomUserDetails.java
import com.jupjup.www.jupjup.dto.UserDTO;
import lombok.Getter;
=======
import com.jupjup.www.jupjup.model.dto.UserDTO;
>>>>>>> 64a38bb472643331d3dc8c2a629bb3d116f29949:src/main/java/com/jupjup/www/jupjup/service/oauth/CustomUserDetails.java
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Slf4j
public record CustomUserDetails(UserDTO userDTO) implements UserDetails, OAuth2User {

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

    // userDetails override method
    @Override
    public String getPassword() {
        return userDTO.getPassword();
    }

    @Override
    public String getUsername() {
        return userDTO.getUsername();
    }

    @Override
    public String getName() {
        return userDTO.getUsername();
    }

    public String getUserEmail(){
        return userDTO.getUserEmail();
    }

    public String getId(){
        return userDTO.getId();
    }

}
