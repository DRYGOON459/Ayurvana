package com.ayurvana.service;

import com.ayurvana.dto.response.RecipeDetailResponse;
import com.ayurvana.dto.response.RecipeSummaryResponse;
import com.ayurvana.model.enums.DoshaType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecipeService {
    Page<RecipeSummaryResponse> getAllRecipes(Pageable pageable);
    RecipeDetailResponse getRecipeBySlug(String slug);
    Page<RecipeSummaryResponse> getRecipesByDosha(DoshaType dosha, Pageable pageable);
    Page<RecipeSummaryResponse> getRecipesByMealType(String mealType, Pageable pageable);
    Page<RecipeSummaryResponse> searchRecipes(String query, Pageable pageable);
}
