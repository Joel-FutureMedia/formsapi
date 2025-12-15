package com.example.simplyfound.controllers;

import com.example.simplyfound.models.ColorPalette;
import com.example.simplyfound.services.ColorPaletteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/color-palettes")
public class ColorPaletteController {
    @Autowired
    private ColorPaletteService colorPaletteService;

    @PostMapping
    public ColorPalette create(@RequestBody ColorPalette colorPalette) {
        return colorPaletteService.save(colorPalette);
    }

    @GetMapping
    public List<ColorPalette> findAll() {
        return colorPaletteService.findAll();
    }

    @GetMapping("/picker")
    public List<ColorPalette> findAllForPicker() {
        return colorPaletteService.findAllForPicker();
    }

    @GetMapping("/predefined")
    public List<ColorPalette> findPredefinedPalettes() {
        return colorPaletteService.findPredefinedPalettes();
    }

    @GetMapping("/category/{category}")
    public List<ColorPalette> findByCategory(@PathVariable String category) {
        return colorPaletteService.findByCategory(category);
    }

    @GetMapping("/categories")
    public List<String> getAllCategories() {
        return colorPaletteService.getAllCategories();
    }

    @GetMapping("/{id}")
    public Optional<ColorPalette> findById(@PathVariable Long id) {
        return colorPaletteService.findById(id);
    }

    @PutMapping("/{id}")
    public ColorPalette update(@PathVariable Long id, @RequestBody ColorPalette colorPalette) {
        return colorPaletteService.update(id, colorPalette);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        colorPaletteService.deleteById(id);
    }
}

