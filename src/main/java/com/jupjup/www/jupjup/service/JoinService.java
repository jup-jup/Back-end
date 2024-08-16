package com.jupjup.www.jupjup.service;

import com.jupjup.www.jupjup.model.dto.user.UserResponse;
import com.jupjup.www.jupjup.domain.repository.RefreshTokenRepository;
import com.jupjup.www.jupjup.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * @fileName      : JoinService.java
 * @author        : boramkim
 * @since         : 2024. 8. 12.
 * @description   : 회원가입 시 데이터 검증
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * 회원가입 처리 메서드.
     * 사용자 데이터를 검증하고, 새로운 사용자 엔티티를 생성하여 저장합니다.
     *
     * @param userDTO 사용자 세부 정보를 포함하는 데이터 전송 객체.
     * @throws IllegalArgumentException 사용자 데이터가 유효하지 않은 경우 예외를 발생시킵니다.
     * @throws Exception 기타 예외가 발생한 경우 예외를 발생시킵니다.
     */
    public String joinProcess(UserResponse userDTO) {

        try {
            validateUser(userDTO);
            com.jupjup.www.jupjup.domain.entity.User user = createUserEntity(userDTO);
            userRepository.save(user);
            log.info("회원가입 완료: {}", userDTO.getUserEmail());
            return "회원가입 완료";
        } catch (IllegalArgumentException e) {
            log.error("회원가입 실패 - IllegalArgumentException: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("회원가입 실패 : {}", e.getMessage());
            throw e;
        }
    }

    /**
     * 사용자 데이터를 검증합니다.
     * 사용자 이메일과 비밀번호가 null 이 아닌지 확인하고,해당 이메일이 이미 시스템에 등록되어 있는지 확인합니다.
     *
     * @param userDTO 사용자 세부 정보를 포함하는 데이터 전송 객체.
     * @throws IllegalArgumentException userEmail 또는 password가 null이거나 해당 이메일이 이미 등록된 경우 예외를 발생시킵니다.
     *
     */
    private void validateUser(UserResponse userDTO) {
        if (userDTO.getUserEmail() == null || userDTO.getPassword() == null) {
            throw new IllegalArgumentException("userEmail과 password는 null일 수 없습니다.");
        }

        boolean isExist = userRepository.existsByUserEmail(userDTO.getUserEmail());
        if (isExist) {
            throw new IllegalArgumentException("이미 가입되어 있는 아이디 입니다: " + userDTO.getUserEmail());
        }
    }

    /**
     * 제공된 UserDTO 로부터 UserEntity 를 생성합니다.
     *
     * @param userDTO 사용자 세부 정보를 포함하는 데이터 전송 객체.
     * @return userDTO 로부터 생성된 UserEntity.
     */
    private com.jupjup.www.jupjup.domain.entity.User createUserEntity(UserResponse userDTO) {
        return com.jupjup.www.jupjup.domain.entity.User.builder()
                .username(userDTO.getUsername())
                .role(userDTO.getRole())
//                .password(bCryptPasswordEncoder.encode(userDTO.getPassword()))
                .userEmail(userDTO.getUserEmail())
                .build();
    }
}