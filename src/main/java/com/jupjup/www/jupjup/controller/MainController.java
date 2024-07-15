package com.jupjup.www.jupjup.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MainController {

    @GetMapping("/")
    public ResponseEntity<String> index() {
        log.debug("hello boram world !");
        return ResponseEntity.status(200).body("ok");
    }

}
