package com.example.simplyfound.repositories;

import com.example.simplyfound.models.OptionalImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionalImageRepository extends JpaRepository<OptionalImage, Long> {
    List<OptionalImage> findByClientJourneyId(Long clientJourneyId);
}

