package com.ayurvana.repository;

import com.ayurvana.model.entity.PlantScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlantScanRepository extends JpaRepository<PlantScan, UUID> {

    Page<PlantScan> findByUserIdOrderByCreatedAtDesc(UUID userId, Pageable pageable);
}
