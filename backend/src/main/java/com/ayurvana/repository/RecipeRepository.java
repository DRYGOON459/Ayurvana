package com.ayurvana.repository;

import com.ayurvana.model.entity.AyurvedicRecipe;
import com.ayurvana.model.enums.DoshaType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<AyurvedicRecipe, Long> {
    Optional<AyurvedicRecipe> findBySlug(String slug);
    Page<AyurvedicRecipe> findByDoshaSuitability(DoshaType doshaSuitability, Pageable pageable);
    Page<AyurvedicRecipe> findByMealTypeIgnoreCase(String mealType, Pageable pageable);

    @Query("SELECT r FROM AyurvedicRecipe r WHERE " +
           "LOWER(r.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(r.sanskritName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(r.description) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(r.therapeuticBenefits) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<AyurvedicRecipe> searchRecipes(@Param("query") String query, Pageable pageable);
}
