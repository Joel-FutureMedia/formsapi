package com.example.simplyfound.repositories;

import com.example.simplyfound.models.ClientJourney;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientJourneyRepository extends JpaRepository<ClientJourney, Long> {
}

