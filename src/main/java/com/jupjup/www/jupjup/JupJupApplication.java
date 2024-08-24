package com.jupjup.www.jupjup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JupJupApplication {

    public static void main(String[] args) {
        System.out.println("MYSQL_URL: " + System.getenv("MYSQL_URL"));
        System.out.println("MYSQL_USER: " + System.getenv("MYSQL_USER"));
        SpringApplication.run(JupJupApplication.class, args);

    }

}
