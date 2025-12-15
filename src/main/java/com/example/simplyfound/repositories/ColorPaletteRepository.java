package com.example.simplyfound.repositories;

import com.example.simplyfound.models.ColorPalette;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorPaletteRepository extends JpaRepository<ColorPalette, Long> {
    List<ColorPalette> findByCategory(String category);
    
    List<ColorPalette> findByIsPredefined(Boolean isPredefined);
    
    @Query("SELECT cp FROM ColorPalette cp ORDER BY cp.displayOrder ASC, cp.name ASC")
    List<ColorPalette> findAllOrderedByDisplayOrder();
    
    @Query("SELECT DISTINCT cp.category FROM ColorPalette cp WHERE cp.category IS NOT NULL ORDER BY cp.category ASC")
    List<String> findAllDistinctCategories();
}

