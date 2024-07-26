package com.jupjup.www.jupjup.servicr;

import com.jupjup.www.jupjup.dto.UserDTO;
import com.jupjup.www.jupjup.entity.UserEntity;
import com.jupjup.www.jupjup.repository.RefreshTokenRepository;
import com.jupjup.www.jupjup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void joinProcess(UserDTO userDTO) {

        try {
            validateUser(userDTO);
            UserEntity user = createUserEntity(userDTO);
            userRepository.save(user);
            log.info("회원가입 완료: {}", userDTO.getUserEmail());
        } catch (IllegalArgumentException e) {
            log.error("회원가입 실패 - IllegalArgumentException: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("회원가입 실패 - 예외 발생: {}", e.getMessage());
            throw e;
        }
    }

    private void validateUser(UserDTO userDTO) {
        if (userDTO.getUserEmail() == null || userDTO.getPassword() == null) {
            throw new IllegalArgumentException("userEmail과 password는 null일 수 없습니다.");
        }

        boolean isExist = userRepository.existsByUserEmail(userDTO.getUserEmail());
        if (isExist) {
            throw new IllegalArgumentException("이미 가입되어 있는 아이디입니다: " + userDTO.getUserEmail());
        }
    }

    private UserEntity createUserEntity(UserDTO userDTO) {
        return UserEntity.builder()
                .username(userDTO.getUsername())
                .role(userDTO.getRole())
                .password(bCryptPasswordEncoder.encode(userDTO.getPassword()))
                .userEmail(userDTO.getUserEmail())
                .build();
    }
}