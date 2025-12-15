package com.example.simplyfound.repositories;

import com.example.simplyfound.models.OpeningDays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpeningDaysRepository extends JpaRepository<OpeningDays, Long> {
}

