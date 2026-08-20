package com.ayurvana.repository;

import com.ayurvana.model.entity.PlantSafety;
import com.ayurvana.model.enums.ToxicityLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlantSafetyRepository extends JpaRepository<PlantSafety, UUID> {

    Optional<PlantSafety> findByHerbId(UUID herbId);

    List<PlantSafety> findByToxicityLevelNot(ToxicityLevel toxicityLevel);
}
