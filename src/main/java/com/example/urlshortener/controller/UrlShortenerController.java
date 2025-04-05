package com.example.urlshortener.controller;

import com.example.urlshortener.service.UrlShortenerService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/")
public class UrlShortenerController {

    @Autowired
    private UrlShortenerService urlShortenerService;

    // 提交原始链接，返回短码
    @PostMapping("/shorten")
    public ResponseEntity<String> createShortUrl(@RequestBody UrlRequest request) {
        String shortCode = urlShortenerService.shortenUrl(request.getOriginalUrl());
        return ResponseEntity.ok("http://localhost:8080/r/" + shortCode);
    }

    // 访问短码，跳转到原始链接
    @GetMapping("/r/{code}")
    public void redirectToOriginalUrl(@PathVariable("code") String code,
                                      HttpServletResponse response) throws IOException {
        String originalUrl = urlShortenerService.getOriginalUrl(code);
        if (originalUrl != null) {
            response.sendRedirect(originalUrl);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid or expired short URL.");
        }
    }

    // 简单的请求 DTO
    @Data
    static class UrlRequest {
        private String originalUrl;
    }
}
