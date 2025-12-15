package com.example.simplyfound.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "layout_styles")
public class LayoutStyle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String style;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public LayoutStyle() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

