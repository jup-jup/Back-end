package com.jupjup.www.jupjup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
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
}