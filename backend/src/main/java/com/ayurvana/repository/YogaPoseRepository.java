package com.ayurvana.repository;

import com.ayurvana.model.entity.YogaPose;
import com.ayurvana.model.enums.DifficultyLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface YogaPoseRepository extends JpaRepository<YogaPose, UUID> {

    Optional<YogaPose> findBySlug(String slug);

    Page<YogaPose> findByDifficulty(DifficultyLevel difficulty, Pageable pageable);

    Page<YogaPose> findByCategoryIgnoreCase(String category, Pageable pageable);
}
