package com.example.simplyfound.services;

import com.example.simplyfound.models.Industry;
import com.example.simplyfound.repositories.IndustryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IndustryService {
    @Autowired
    private IndustryRepository industryRepository;

    public Industry save(Industry industry) {
        return industryRepository.save(industry);
    }

    public List<Industry> findAll() {
        return industryRepository.findAll();
    }

    public Optional<Industry> findById(Long id) {
        return industryRepository.findById(id);
    }

    public Industry update(Long id, Industry industry) {
        Industry existingIndustry = industryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Industry not found"));
        existingIndustry.setIndustry(industry.getIndustry());
        return industryRepository.save(existingIndustry);
    }

    public void deleteById(Long id) {
        industryRepository.deleteById(id);
    }
}

