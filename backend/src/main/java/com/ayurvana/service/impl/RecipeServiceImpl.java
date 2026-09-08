package com.ayurvana.service.impl;

import com.ayurvana.dto.response.RecipeDetailResponse;
import com.ayurvana.dto.response.RecipeSummaryResponse;
import com.ayurvana.exception.ResourceNotFoundException;
import com.ayurvana.model.entity.AyurvedicRecipe;
import com.ayurvana.model.enums.DoshaType;
import com.ayurvana.repository.RecipeRepository;
import com.ayurvana.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<RecipeSummaryResponse> getAllRecipes(Pageable pageable) {
        return recipeRepository.findAll(pageable).map(this::mapToSummary);
    }

    @Override
    @Transactional(readOnly = true)
    public RecipeDetailResponse getRecipeBySlug(String slug) {
        AyurvedicRecipe recipe = recipeRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Ayurvedic Recipe", "slug", slug));
        return mapToDetail(recipe);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RecipeSummaryResponse> getRecipesByDosha(DoshaType dosha, Pageable pageable) {
        return recipeRepository.findByDoshaSuitability(dosha, pageable).map(this::mapToSummary);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RecipeSummaryResponse> getRecipesByMealType(String mealType, Pageable pageable) {
        return recipeRepository.findByMealTypeIgnoreCase(mealType, pageable).map(this::mapToSummary);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RecipeSummaryResponse> searchRecipes(String query, Pageable pageable) {
        return recipeRepository.searchRecipes(query, pageable).map(this::mapToSummary);
    }

    private RecipeSummaryResponse mapToSummary(AyurvedicRecipe recipe) {
        return RecipeSummaryResponse.builder()
                .id(recipe.getId())
                .title(recipe.getTitle())
                .sanskritName(recipe.getSanskritName())
                .slug(recipe.getSlug())
                .doshaSuitability(recipe.getDoshaSuitability())
                .mealType(recipe.getMealType())
                .seasonSuitability(recipe.getSeasonSuitability())
                .prepTimeMinutes(recipe.getPrepTimeMinutes())
                .cookTimeMinutes(recipe.getCookTimeMinutes())
                .servings(recipe.getServings())
                .difficulty(recipe.getDifficulty())
                .description(recipe.getDescription())
                .imageUrl(recipe.getImageUrl())
                .nutritionalHighlight(recipe.getNutritionalHighlight())
                .build();
    }

    private RecipeDetailResponse mapToDetail(AyurvedicRecipe recipe) {
        return RecipeDetailResponse.builder()
                .id(recipe.getId())
                .title(recipe.getTitle())
                .sanskritName(recipe.getSanskritName())
                .slug(recipe.getSlug())
                .doshaSuitability(recipe.getDoshaSuitability())
                .mealType(recipe.getMealType())
                .seasonSuitability(recipe.getSeasonSuitability())
                .prepTimeMinutes(recipe.getPrepTimeMinutes())
                .cookTimeMinutes(recipe.getCookTimeMinutes())
                .servings(recipe.getServings())
                .difficulty(recipe.getDifficulty())
                .description(recipe.getDescription())
                .therapeuticBenefits(recipe.getTherapeuticBenefits())
                .digestiveActionAgni(recipe.getDigestiveActionAgni())
                .imageUrl(recipe.getImageUrl())
                .ingredients(recipe.getIngredients())
                .instructions(recipe.getInstructions())
                .nutritionalHighlight(recipe.getNutritionalHighlight())
                .classicalReference(recipe.getClassicalReference())
                .build();
    }
}
