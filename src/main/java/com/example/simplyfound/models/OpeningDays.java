package com.example.simplyfound.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "opening_days")
public class OpeningDays {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String openingDays;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public OpeningDays() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpeningDays() {
        return openingDays;
    }

    public void setOpeningDays(String openingDays) {
        this.openingDays = openingDays;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

