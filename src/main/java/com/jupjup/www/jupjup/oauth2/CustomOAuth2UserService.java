package com.jupjup.www.jupjup.oauth2;

import com.jupjup.www.jupjup.dto.UserDTO;
import com.jupjup.www.jupjup.entity.UserEntity;
import com.jupjup.www.jupjup.enumClass.OauthRegistrationId;
import com.jupjup.www.jupjup.repository.UserRepository;
import com.jupjup.www.jupjup.response.GoogleResponse;
import com.jupjup.www.jupjup.response.NaverResponse;
import com.jupjup.www.jupjup.response.OAuth2Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import static com.jupjup.www.jupjup.enumClass.OauthRegistrationId.*;

/* 생성된 OAuth2User 객체가 Spring Security 의 인증 컨텍스트에 설정되어 애플리케이션 내에서 인증된 사용자로 사용됩니다.*/
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private OauthRegistrationId registrationId;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationIdString = userRequest.getClientRegistration().getRegistrationId();
        log.info("loadUser registrationId : {}", registrationIdString);
        log.info("oauth2User : {}", oAuth2User.getAttributes());

        try {
            registrationId = valueOf(registrationIdString.toUpperCase());
            OAuth2Response oAuth2Response = getOAuth2Response(oAuth2User, registrationId);
            String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
            return saveOrUpdateUser(username, oAuth2Response);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Unsupported registration id: " + registrationId);
        }

    }

    private OAuth2Response getOAuth2Response(OAuth2User oAuth2User, OauthRegistrationId registrationId) {

        if (registrationId == NAVER) {
            log.info("네이버 로그인");
            return new NaverResponse(oAuth2User.getAttributes());
        } else if (registrationId == GOOGLE) {
            log.info("구글 로그인");
            return new GoogleResponse(oAuth2User.getAttributes());
        } else if(registrationId == KAKAO){
            log.info("카카오 로그인 {}");
            return new GoogleResponse(oAuth2User.getAttributes());
        }else{
            throw new IllegalArgumentException("Unsupported registration id: " + registrationId);
        }
    }

    private OAuth2User saveOrUpdateUser(String username, OAuth2Response oAuth2Response) {

        UserEntity existData = userRepository.findByUsername(username);

        if (existData == null) {
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(username);
            userEntity.setUserEmail(oAuth2Response.getEmail());
            userEntity.setRole("ROLE_USER");
            userRepository.save(userEntity);

            return new CustomUserDetails(UserDTO.builder().username(username).userEmail(oAuth2Response.getEmail()).role("ROLE_USER").build());
        } else {
            existData.setUserEmail(oAuth2Response.getEmail());
            existData.setUsername(oAuth2Response.getName());
            userRepository.save(existData);
            return new CustomUserDetails(UserDTO.builder().username(oAuth2Response.getName()).userEmail(oAuth2Response.getEmail()).build());
        }

    }
}

