package com.jupjup.www.jupjup.controller;

import com.jupjup.www.jupjup.oauth2.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

@Controller
@Slf4j
public class TestController {

    @GetMapping("/index")
    public String showIndex() {
        return "index";
    }

    @GetMapping("/joinForm")
    public String showJoinForm() {
        return "joinForm";
    }

    @GetMapping("/loginForm")
    public String showLoginForm() {
        return "loginForm";
    }

    @GetMapping("/loginError")
    public String showLoginError() {
        return "loginError";
    }

    @GetMapping("/loginSuccess")
    public String showLoginSuccess() {
        return "loginSuccess";
    }

    @GetMapping("/user")
    @ResponseBody
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