package com.example.urlshortener;
import org.springframework.cache.annotation.EnableCaching;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCaching
public class UrlshortenerApplication {
    public static void main(String[] args) {
        SpringApplication.run(UrlshortenerApplication.class, args);
    }
}
