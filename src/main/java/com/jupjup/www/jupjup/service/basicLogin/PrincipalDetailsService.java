package com.jupjup.www.jupjup.service.basicLogin;

import com.jupjup.www.jupjup.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @fileName      : PrincipalDetailsService.java
 * @author        : boramkim
 * @since         : 2024. 8. 12.
 * @description    : 일반 로그인 시 필요한 클래스
 */
@RequiredArgsConstructor
@Slf4j
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.jupjup.www.jupjup.domain.entity.User user = userRepository.findByName(username);

        log.info("username: {}", user);

        if (user != null) {
//            return new CustomUserDetails(UserResponse.builder()
//                    .userEmail(user.getUserEmail())
//                    .username(user.getName())
//                    .role(user.getRole())
////                    .password(user.getPassword())
//                    .build());
        }
        throw new UsernameNotFoundException("user not found with username : " + username);
    }
}
