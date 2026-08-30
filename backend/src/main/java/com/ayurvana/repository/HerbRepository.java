package com.ayurvana.repository;

import com.ayurvana.model.entity.Herb;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface HerbRepository extends JpaRepository<Herb, UUID> {

    Optional<Herb> findBySlug(String slug);

    Optional<Herb> findByScientificNameIgnoreCase(String scientificName);

    Optional<Herb> findByCommonNameContainingIgnoreCase(String commonName);

    Page<Herb> findByPublishedTrue(Pageable pageable);

    @Query("SELECT h FROM Herb h LEFT JOIN FETCH h.safety s LEFT JOIN FETCH h.references r WHERE h.slug = :slug")
    Optional<Herb> findBySlugWithSafetyAndReferences(@Param("slug") String slug);

    @Query("SELECT h FROM Herb h WHERE h.published = true AND " +
           "(LOWER(h.commonName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(h.sanskritName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(h.scientificName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(h.primaryDoshaEffect) LIKE LOWER(CONCAT('%', :query, '%')))")
    Page<Herb> searchHerbs(@Param("query") String query, Pageable pageable);
}
