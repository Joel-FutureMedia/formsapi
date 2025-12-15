package com.example.simplyfound.repositories;

import com.example.simplyfound.models.ServiceOrProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceOrProductRepository extends JpaRepository<ServiceOrProduct, Long> {
    List<ServiceOrProduct> findByClientJourneyId(Long clientJourneyId);
}

