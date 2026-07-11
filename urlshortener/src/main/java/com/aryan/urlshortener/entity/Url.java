package com.aryan.urlshortener.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import jakarta.persistence.PrePersist;

@Entity
@Table(name = "urls")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String originalUrl;

    @Column(nullable = false, unique = true, length = 10)
    private String shortCode;



    @PrePersist
    public void prePersist() {
    if (createdAt == null) {
        createdAt = LocalDateTime.now();
    }

    if (clickCount == null) {
        clickCount = 0L;
    }
   }

   @Builder.Default
private Long clickCount = 0L;

@Builder.Default
private LocalDateTime createdAt = LocalDateTime.now();
}