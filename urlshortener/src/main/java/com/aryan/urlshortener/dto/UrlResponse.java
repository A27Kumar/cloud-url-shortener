package com.aryan.urlshortener.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UrlResponse {

    private String originalUrl;
    private String shortCode;
    private String shortUrl;
    private Long clickCount;

}