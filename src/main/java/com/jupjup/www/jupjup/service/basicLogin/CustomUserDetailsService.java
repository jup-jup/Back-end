package com.jupjup.www.jupjup.service.basicLogin;


import com.jupjup.www.jupjup.model.dto.UserResponse;
import com.jupjup.www.jupjup.domain.repository.UserRepository;
import com.jupjup.www.jupjup.service.oauth.CustomUserDetails;
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

        com.jupjup.www.jupjup.domain.entity.User user = userRepository.findByUserEmail(userEmail);

        if (user != null) {

        log.info("userEmail: {}", user.getUserEmail());
        log.info("role: {}", user.getRole());

            return new CustomUserDetails(UserResponse.builder()
                    .userEmail(user.getUserEmail())
                    .username(user.getName())
                    .role(user.getRole())
                    .build());
        }
        throw new UsernameNotFoundException("user not found with username : " + userEmail);
    }


}
