package com.example.simplyfound.services;

import com.example.simplyfound.models.ColorPalette;
import com.example.simplyfound.repositories.ColorPaletteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ColorPaletteService {
    @Autowired
    private ColorPaletteRepository colorPaletteRepository;

    public ColorPalette save(ColorPalette colorPalette) {
        if (colorPalette.getDisplayOrder() == null) {
            colorPalette.setDisplayOrder(0);
        }
        if (colorPalette.getIsPredefined() == null) {
            colorPalette.setIsPredefined(false);
        }
        return colorPaletteRepository.save(colorPalette);
    }

    public List<ColorPalette> findAll() {
        return colorPaletteRepository.findAll();
    }

    public List<ColorPalette> findAllForPicker() {
        return colorPaletteRepository.findAllOrderedByDisplayOrder();
    }

    public List<ColorPalette> findByCategory(String category) {
        return colorPaletteRepository.findByCategory(category);
    }

    public List<ColorPalette> findPredefinedPalettes() {
        return colorPaletteRepository.findByIsPredefined(true);
    }

    public List<String> getAllCategories() {
        return colorPaletteRepository.findAllDistinctCategories();
    }

    public Optional<ColorPalette> findById(Long id) {
        return colorPaletteRepository.findById(id);
    }

    public ColorPalette update(Long id, ColorPalette colorPalette) {
        ColorPalette existing = colorPaletteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ColorPalette not found"));
        existing.setName(colorPalette.getName());
        existing.setColor1(colorPalette.getColor1());
        existing.setColor2(colorPalette.getColor2());
        existing.setColor3(colorPalette.getColor3());
        existing.setColor4(colorPalette.getColor4());
        existing.setColor5(colorPalette.getColor5());
        existing.setDescription(colorPalette.getDescription());
        existing.setCategory(colorPalette.getCategory());
        existing.setIsPredefined(colorPalette.getIsPredefined());
        existing.setDisplayOrder(colorPalette.getDisplayOrder());
        return colorPaletteRepository.save(existing);
    }

    public void deleteById(Long id) {
        colorPaletteRepository.deleteById(id);
    }
}

