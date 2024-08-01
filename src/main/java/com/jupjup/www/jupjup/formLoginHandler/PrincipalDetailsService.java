package com.jupjup.www.jupjup.formLoginHandler;

import com.jupjup.www.jupjup.dto.UserDTO;
import com.jupjup.www.jupjup.entity.UserEntity;
import com.jupjup.www.jupjup.oauth2.CustomUserDetails;
import com.jupjup.www.jupjup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

//@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByUsername(username);

        log.info("username: {}", user);

        if (user != null) {
            return new CustomUserDetails(UserDTO.builder()
                    .userEmail(user.getUserEmail())
                    .username(user.getUsername())
                    .role(user.getRole())
//                    .password(user.getPassword())
                    .build());
        }
        throw new UsernameNotFoundException("user not found with username : " + username);
    }
}
