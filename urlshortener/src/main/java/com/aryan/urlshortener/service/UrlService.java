package com.aryan.urlshortener.service;

import com.aryan.urlshortener.dto.UrlRequest;
import com.aryan.urlshortener.dto.UrlResponse;
import com.aryan.urlshortener.entity.Url;
import com.aryan.urlshortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    private static final String CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private final Random random = new Random();

    // Generate random short code
    private String generateShortCode() {

        String code;

        do {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < 6; i++) {
                sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
            }

            code = sb.toString();

        } while (urlRepository.findByShortCode(code).isPresent());

        return code;
    }

    // Create Short URL
    public UrlResponse createShortUrl(UrlRequest request) {

        String shortCode = generateShortCode();

        Url url = Url.builder()
                .originalUrl(request.getUrl())
                .shortCode(shortCode)
                .clickCount(0L)
                .build();

        urlRepository.save(url);

        return UrlResponse.builder()
                .originalUrl(url.getOriginalUrl())
                .shortCode(shortCode)
                .shortUrl("http://52.66.241.120:8080/api/urls/redirect/" + shortCode)
                .clickCount(0L)
                .build();
    }

    // Get Original URL
    public Optional<Url> getOriginalUrl(String shortCode) {
        return urlRepository.findByShortCode(shortCode);
    }

    // Increase Click Count
    public void increaseClickCount(Url url) {
        url.setClickCount(url.getClickCount() + 1);
        urlRepository.save(url);
    }

    // Analytics
    public UrlResponse getAnalytics(String shortCode) {

        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Short URL not found"));

        return UrlResponse.builder()
                .originalUrl(url.getOriginalUrl())
                .shortCode(url.getShortCode())
                .shortUrl("http://52.66.241.120:8080/api/urls/redirect/" + url.getShortCode())
                .clickCount(url.getClickCount())
                .build();
    }
}