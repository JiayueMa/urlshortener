package com.example.urlshortener.service;

import com.example.urlshortener.entity.UrlMapping;
import com.example.urlshortener.repository.UrlMappingRepository;
import com.example.urlshortener.util.ShortCodeGenerator;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;

@Service
public class UrlShortenerService {

    @Autowired
    private UrlMappingRepository urlMappingRepository;

    public String shortenUrl(String originalUrl) {
        String shortCode = ShortCodeGenerator.generate();

        UrlMapping mapping = UrlMapping.builder()
                .originalUrl(originalUrl)
                .shortCode(shortCode)
                .createdAt(LocalDateTime.now())
                .expireAt(LocalDateTime.now().plusDays(7))  // 设置7天过期
                .build();

        urlMappingRepository.save(mapping);

        return shortCode;
    }

 //   @Cacheable(value = "urlCache", key = "#shortCode")
    public String getOriginalUrl(String shortCode) {
        return urlMappingRepository.findByShortCode(shortCode)
                .filter(mapping -> mapping.getExpireAt().isAfter(LocalDateTime.now()))
                .map(UrlMapping::getOriginalUrl)
                .orElse(null);
    }
}
