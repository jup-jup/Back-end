package com.jupjup.www.jupjup.service;


import com.jupjup.www.jupjup.dto.UserDTO;
import com.jupjup.www.jupjup.entity.UserEntity;
import com.jupjup.www.jupjup.oauth2.CustomUserDetails;
import com.jupjup.www.jupjup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByUserEmail(userEmail);

        if (user != null) {

        log.info("userEmail: {}", user.getUserEmail());
        log.info("role: {}", user.getRole());

            return new CustomUserDetails(UserDTO.builder()
                    .userEmail(user.getUserEmail())
                    .username(user.getUsername())
                    .role(user.getRole())
                    .password(user.getPassword())
                    .build());
        }
        throw new UsernameNotFoundException("user not found with username : " + userEmail);
    }


}
