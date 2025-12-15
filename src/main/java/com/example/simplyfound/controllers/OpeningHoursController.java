package com.example.simplyfound.controllers;

import com.example.simplyfound.models.OpeningHours;
import com.example.simplyfound.services.OpeningHoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/opening-hours")
public class OpeningHoursController {
    @Autowired
    private OpeningHoursService openingHoursService;

    @PostMapping
    public OpeningHours create(@RequestBody OpeningHours openingHours) {
        return openingHoursService.save(openingHours);
    }

    @GetMapping
    public List<OpeningHours> findAll() {
        return openingHoursService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<OpeningHours> findById(@PathVariable Long id) {
        return openingHoursService.findById(id);
    }

    @PutMapping("/{id}")
    public OpeningHours update(@PathVariable Long id, @RequestBody OpeningHours openingHours) {
        return openingHoursService.update(id, openingHours);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        openingHoursService.deleteById(id);
    }
}

