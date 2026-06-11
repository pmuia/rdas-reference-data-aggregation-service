package com.example.rdas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class RdasApplication {
    public static void main(String[] args) {
        SpringApplication.run(RdasApplication.class, args);
    }
}
