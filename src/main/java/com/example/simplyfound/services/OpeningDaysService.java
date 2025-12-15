package com.example.simplyfound.services;

import com.example.simplyfound.models.OpeningDays;
import com.example.simplyfound.repositories.OpeningDaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OpeningDaysService {
    @Autowired
    private OpeningDaysRepository openingDaysRepository;

    public OpeningDays save(OpeningDays openingDays) {
        return openingDaysRepository.save(openingDays);
    }

    public List<OpeningDays> findAll() {
        return openingDaysRepository.findAll();
    }

    public Optional<OpeningDays> findById(Long id) {
        return openingDaysRepository.findById(id);
    }

    public OpeningDays update(Long id, OpeningDays openingDays) {
        OpeningDays existing = openingDaysRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OpeningDays not found"));
        existing.setOpeningDays(openingDays.getOpeningDays());
        return openingDaysRepository.save(existing);
    }

    public void deleteById(Long id) {
        openingDaysRepository.deleteById(id);
    }
}

