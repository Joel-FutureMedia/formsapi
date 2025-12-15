package com.example.simplyfound.controllers;

import com.example.simplyfound.models.LayoutStyle;
import com.example.simplyfound.services.LayoutStyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/layout-styles")
public class LayoutStyleController {
    @Autowired
    private LayoutStyleService layoutStyleService;

    @PostMapping
    public LayoutStyle create(@RequestBody LayoutStyle layoutStyle) {
        return layoutStyleService.save(layoutStyle);
    }

    @GetMapping
    public List<LayoutStyle> findAll() {
        return layoutStyleService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<LayoutStyle> findById(@PathVariable Long id) {
        return layoutStyleService.findById(id);
    }

    @PutMapping("/{id}")
    public LayoutStyle update(@PathVariable Long id, @RequestBody LayoutStyle layoutStyle) {
        return layoutStyleService.update(id, layoutStyle);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        layoutStyleService.deleteById(id);
    }
}

