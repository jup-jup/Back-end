package com.jupjup.www.jupjup.service.oauth;

import com.jupjup.www.jupjup.domain.entity.User;
import com.jupjup.www.jupjup.model.dto.user.UserResponse;
import com.jupjup.www.jupjup.domain.enums.OauthRegistrationId;
import com.jupjup.www.jupjup.domain.repository.UserRepository;
import com.jupjup.www.jupjup.common.response.GoogleResponse;
import com.jupjup.www.jupjup.common.response.KakaoResponse;
import com.jupjup.www.jupjup.common.response.NaverResponse;
import com.jupjup.www.jupjup.common.response.OAuth2Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import static com.jupjup.www.jupjup.domain.enums.OauthRegistrationId.*;

/**
 * @author : boramkim
 * @fileName : CustomOAuth2UserService.java
 * @description : 생성된 OAuth2User 객체가 Spring Security 의 인증 컨텍스트에 설정되어 애플리케이션 내에서 인증된 사용자로 사용됩니다                  DefaultOAuth2UserService 클래스 내부의 super.loadUser() 를 실행시켜 OAuth2UserRequest 에서 사용자 정보를 받아와 처리합니다.
 * @since : 2024. 8. 1.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private OauthRegistrationId registrationId;


    // OAuth2UserRequest = 리소스 서버에서 보내주는 유저 정보를 가지고 있음
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationIdString = userRequest.getClientRegistration().getRegistrationId();

        try {
            registrationId = valueOf(registrationIdString.toUpperCase());
            OAuth2Response oAuth2Response = getOAuth2Response(oAuth2User, registrationId);
            return saveOrUpdateUser(oAuth2Response);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Unsupported registration id: " + registrationId);
        }

    }


    /**
     * 주어진 OAuth2User와 등록 registrationId 기반으로 적절한 OAuth2Response 객체 생성
     * 각 리소스 제공자(네이버, 구글, 카카오)에 따라 사용자의 속성을 파싱하여 특정 형식의 응답을 반환
     * 지원되지 않는 등록 ID가 입력되면 IllegalArgumentException 발생
     *
     * @param oAuth2User OAuth2 인증을 통해 얻은 사용자 정보
     * @param registrationId 리소스 제공자를 식별하는 ID (예: 네이버, 구글, 카카오)
     * @return OAuth2Response 각 리소스 제공자에 맞게 변환된 사용자 정보를 담은 응답 객체
     * @throws IllegalArgumentException 지원되지 않는 리소스 제공자 ID가 입력될 경우 예외 발생
     */
    private OAuth2Response getOAuth2Response(OAuth2User oAuth2User, OauthRegistrationId registrationId) {

        if (registrationId == NAVER) {
            log.info("네이버 로그인");
            return new NaverResponse(oAuth2User.getAttributes());
        } else if (registrationId == GOOGLE) {
            log.info("구글 로그인");
            return new GoogleResponse(oAuth2User.getAttributes());
        } else if(registrationId == KAKAO){
            log.info("카카오 로그인");
            return new KakaoResponse(oAuth2User.getAttributes());
        }else{
            throw new IllegalArgumentException("지원되지 않는 리소스 제공자 입니다. registration id: " + registrationId);
        }
    }

    /**
     * @author        : boramkim
     * @since         : 2024. 8. 1.
     * @description   : 가입이 안된 유저이면 회원가입되고 가입되어 있다면 기존계정 내용 업데이트
     *                   카카오의 경우 이메일을 내려보내주지 않아, 닉네임+key 로 email insert
     */
    private OAuth2User saveOrUpdateUser(OAuth2Response oAuth2Response) {
        String providerId = oAuth2Response.getProvider() + oAuth2Response.getProviderId();
        String userName = oAuth2Response.getName();
        String userEmail = deriveEmail(oAuth2Response);

        User existingUser = userRepository.findByUserEmailAndProviderKey(userEmail, providerId);

        if (existingUser == null) {
            log.info("회원가입이 완료");
            return registerNewUser(providerId, userName, userEmail);
        } else {
            log.info("기가입 유저 => 정보 업데이트");
            return updateExistingUser(existingUser, userName, userEmail);
        }
    }

    private String deriveEmail(OAuth2Response oAuth2Response) {
        if (oAuth2Response instanceof KakaoResponse kakaoResponse) {
            return kakaoResponse.getName() + "@" + kakaoResponse.getProviderId();
        }
        return oAuth2Response.getEmail();
    }

    private OAuth2User registerNewUser(String providerId, String userName, String userEmail) {
        User user = User.builder()
                .providerKey(providerId)
                .userEmail(userEmail)
                .role("ROLE_USER")
                .username(userName)
                .build();
        userRepository.save(user);
        return new CustomUserDetails(UserResponse.builder()
                .providerId(providerId)
                .username(userName)
                .userEmail(userEmail)
                .role("ROLE_USER")
                .build());
    }

    private OAuth2User updateExistingUser(User user, String userName, String userEmail) {
        user.setUserEmail(userEmail);
        user.setName(userName);
        userRepository.save(user);
        return new CustomUserDetails(UserResponse.builder()
                .username(userName)
                .userEmail(userEmail)
                .build());
    }
}

