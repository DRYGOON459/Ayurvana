package com.ayurvana.repository;

import com.ayurvana.model.entity.AyurvedaTopic;
import com.ayurvana.model.enums.DoshaType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AyurvedaTopicRepository extends JpaRepository<AyurvedaTopic, UUID> {

    Optional<AyurvedaTopic> findBySlug(String slug);

    Page<AyurvedaTopic> findByCategoryIgnoreCaseAndPublishedTrue(String category, Pageable pageable);

    Page<AyurvedaTopic> findByDoshaAssociationAndPublishedTrue(DoshaType doshaType, Pageable pageable);

    @org.springframework.data.jpa.repository.Query("SELECT t FROM AyurvedaTopic t WHERE t.published = true AND (" +
           "LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(t.sanskritTitle) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(t.category) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(t.summary) LIKE LOWER(CONCAT('%', :query, '%')))")
    Page<AyurvedaTopic> searchTopics(@org.springframework.data.repository.query.Param("query") String query, Pageable pageable);
}
