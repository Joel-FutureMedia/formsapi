package com.example.simplyfound.repositories;

import com.example.simplyfound.models.LayoutStyle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LayoutStyleRepository extends JpaRepository<LayoutStyle, Long> {
}

