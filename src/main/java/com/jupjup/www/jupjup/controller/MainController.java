package com.jupjup.www.jupjup.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class MainController {

    @GetMapping("/")
    public ResponseEntity<String> index() {
        return ResponseEntity.status(200).body("ok");
    }

}
