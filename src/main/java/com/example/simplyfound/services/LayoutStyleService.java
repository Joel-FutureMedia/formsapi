package com.example.simplyfound.services;

import com.example.simplyfound.models.LayoutStyle;
import com.example.simplyfound.repositories.LayoutStyleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LayoutStyleService {
    @Autowired
    private LayoutStyleRepository layoutStyleRepository;

    public LayoutStyle save(LayoutStyle layoutStyle) {
        return layoutStyleRepository.save(layoutStyle);
    }

    public List<LayoutStyle> findAll() {
        return layoutStyleRepository.findAll();
    }

    public Optional<LayoutStyle> findById(Long id) {
        return layoutStyleRepository.findById(id);
    }

    public LayoutStyle update(Long id, LayoutStyle layoutStyle) {
        LayoutStyle existing = layoutStyleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("LayoutStyle not found"));
        existing.setStyle(layoutStyle.getStyle());
        return layoutStyleRepository.save(existing);
    }

    public void deleteById(Long id) {
        layoutStyleRepository.deleteById(id);
    }
}

