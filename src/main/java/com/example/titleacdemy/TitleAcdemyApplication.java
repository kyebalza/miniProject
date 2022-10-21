package com.example.titleacdemy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TitleAcdemyApplication {

    public static void main(String[] args) {
        SpringApplication.run(TitleAcdemyApplication.class, args);
    }

}
