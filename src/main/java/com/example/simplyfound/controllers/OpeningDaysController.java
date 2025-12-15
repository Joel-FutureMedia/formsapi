package com.example.simplyfound.controllers;

import com.example.simplyfound.models.OpeningDays;
import com.example.simplyfound.services.OpeningDaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/opening-days")
public class OpeningDaysController {
    @Autowired
    private OpeningDaysService openingDaysService;

    @PostMapping
    public OpeningDays create(@RequestBody OpeningDays openingDays) {
        return openingDaysService.save(openingDays);
    }

    @GetMapping
    public List<OpeningDays> findAll() {
        return openingDaysService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<OpeningDays> findById(@PathVariable Long id) {
        return openingDaysService.findById(id);
    }

    @PutMapping("/{id}")
    public OpeningDays update(@PathVariable Long id, @RequestBody OpeningDays openingDays) {
        return openingDaysService.update(id, openingDays);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        openingDaysService.deleteById(id);
    }
}

