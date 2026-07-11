package com.aryan.urlshortener.controller;

import com.aryan.urlshortener.dto.UrlRequest;
import com.aryan.urlshortener.dto.UrlResponse;
import com.aryan.urlshortener.entity.Url;
import com.aryan.urlshortener.service.UrlService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api/urls")
@CrossOrigin(origins = "*")
public class UrlController {

    @Autowired
    private UrlService urlService;

    // Create Short URL
    @PostMapping
    public ResponseEntity<UrlResponse> createShortUrl(@Valid @RequestBody UrlRequest request) {
        return ResponseEntity.ok(urlService.createShortUrl(request));
    }

    // Analytics
    @GetMapping("/{shortCode}")
    public ResponseEntity<UrlResponse> getAnalytics(@PathVariable String shortCode) {
        return ResponseEntity.ok(urlService.getAnalytics(shortCode));
    }

    // Redirect
    @GetMapping("/redirect/{shortCode}")
    public RedirectView redirect(@PathVariable String shortCode) {

        Url url = urlService.getOriginalUrl(shortCode)
                .orElseThrow(() -> new RuntimeException("Short URL not found"));

        urlService.increaseClickCount(url);

        return new RedirectView(url.getOriginalUrl());
    }
}