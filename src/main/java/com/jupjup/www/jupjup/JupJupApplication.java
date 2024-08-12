package com.jupjup.www.jupjup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class JupJupApplication {

    public static void main(String[] args) {
        SpringApplication.run(JupJupApplication.class, args);

    }

}
