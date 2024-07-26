package com.jupjup.www.jupjup.controller;


import com.jupjup.www.jupjup.dto.UserDTO;
import com.jupjup.www.jupjup.repository.RefreshTokenRepository;
import com.jupjup.www.jupjup.servicr.JoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
//@RequestMapping("/user")
public class LoginController {

    private final JoinService joinService;
    private final RefreshTokenRepository refreshTokenRepository;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody UserDTO userDTO) {
        joinService.joinProcess(userDTO);
        return ResponseEntity.ok("회원가입완료");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody UserDTO userDTO) {
        System.out.println("실행 !!!!!!");
        System.out.println("userw! " + userDTO.getUserEmail());
        refreshTokenRepository.deleteByUserEmail(userDTO.getUserEmail());
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }


}
