package com.example.simplyfound.controllers;

import com.example.simplyfound.models.Industry;
import com.example.simplyfound.services.IndustryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/industries")
public class IndustryController {
    @Autowired
    private IndustryService industryService;

    @PostMapping
    public Industry create(@RequestBody Industry industry) {
        return industryService.save(industry);
    }

    @GetMapping
    public List<Industry> findAll() {
        return industryService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Industry> findById(@PathVariable Long id) {
        return industryService.findById(id);
    }

    @PutMapping("/{id}")
    public Industry update(@PathVariable Long id, @RequestBody Industry industry) {
        return industryService.update(id, industry);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        industryService.deleteById(id);
    }
}

