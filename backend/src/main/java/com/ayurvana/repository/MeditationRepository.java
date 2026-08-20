package com.ayurvana.repository;

import com.ayurvana.model.entity.Meditation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MeditationRepository extends JpaRepository<Meditation, UUID> {

    Page<Meditation> findByCategoryIgnoreCase(String category, Pageable pageable);
}
