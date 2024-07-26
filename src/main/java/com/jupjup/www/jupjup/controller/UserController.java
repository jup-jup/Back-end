package com.jupjup.www.jupjup.controller;


import com.jupjup.www.jupjup.oauth2.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@Slf4j
public class UserController {

    @GetMapping("/user")
    public ResponseEntity<?> user() {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails UserDetails = (CustomUserDetails) authentication.getPrincipal();
            Collection<? extends GrantedAuthority> authorities = UserDetails.getAuthorities();

            // 권한 확인
            boolean hasAdminRole = authorities.stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
            boolean hasUserRole = authorities.stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER"));

            log.info("Has ROLE_ADMIN: {}" ,hasAdminRole);
            log.info("Has ROLE_USER: {}" ,hasUserRole);

        } catch (NullPointerException e) {
            log.info("userName is null");
            return ResponseEntity.status(401).body("UserName is null");
        }
        return ResponseEntity.status(200).body("success!");

    }

}
