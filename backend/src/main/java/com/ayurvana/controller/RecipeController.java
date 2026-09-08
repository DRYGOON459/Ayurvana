package com.ayurvana.controller;

import com.ayurvana.dto.response.ApiResponse;
import com.ayurvana.dto.response.PagedResponse;
import com.ayurvana.dto.response.RecipeDetailResponse;
import com.ayurvana.dto.response.RecipeSummaryResponse;
import com.ayurvana.model.enums.DoshaType;
import com.ayurvana.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<RecipeSummaryResponse>>> getAllRecipes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<RecipeSummaryResponse> paged = recipeService.getAllRecipes(pageable);
        PagedResponse<RecipeSummaryResponse> response = PagedResponse.<RecipeSummaryResponse>builder()
                .content(paged.getContent())
                .pageNumber(paged.getNumber())
                .pageSize(paged.getSize())
                .totalElements(paged.getTotalElements())
                .totalPages(paged.getTotalPages())
                .last(paged.isLast())
                .build();
        return ResponseEntity.ok(ApiResponse.success(response, "Ayurvedic recipes retrieved successfully"));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ApiResponse<RecipeDetailResponse>> getRecipeBySlug(@PathVariable String slug) {
        RecipeDetailResponse detail = recipeService.getRecipeBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success(detail, "Recipe detail retrieved successfully"));
    }

    @GetMapping("/dosha/{dosha}")
    public ResponseEntity<ApiResponse<PagedResponse<RecipeSummaryResponse>>> getRecipesByDosha(
            @PathVariable DoshaType dosha,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("title").ascending());
        Page<RecipeSummaryResponse> paged = recipeService.getRecipesByDosha(dosha, pageable);
        PagedResponse<RecipeSummaryResponse> response = PagedResponse.<RecipeSummaryResponse>builder()
                .content(paged.getContent())
                .pageNumber(paged.getNumber())
                .pageSize(paged.getSize())
                .totalElements(paged.getTotalElements())
                .totalPages(paged.getTotalPages())
                .last(paged.isLast())
                .build();
        return ResponseEntity.ok(ApiResponse.success(response, "Recipes by Dosha retrieved successfully"));
    }

    @GetMapping("/meal-type/{mealType}")
    public ResponseEntity<ApiResponse<PagedResponse<RecipeSummaryResponse>>> getRecipesByMealType(
            @PathVariable String mealType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("title").ascending());
        Page<RecipeSummaryResponse> paged = recipeService.getRecipesByMealType(mealType, pageable);
        PagedResponse<RecipeSummaryResponse> response = PagedResponse.<RecipeSummaryResponse>builder()
                .content(paged.getContent())
                .pageNumber(paged.getNumber())
                .pageSize(paged.getSize())
                .totalElements(paged.getTotalElements())
                .totalPages(paged.getTotalPages())
                .last(paged.isLast())
                .build();
        return ResponseEntity.ok(ApiResponse.success(response, "Recipes by meal type retrieved successfully"));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<PagedResponse<RecipeSummaryResponse>>> searchRecipes(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RecipeSummaryResponse> paged = recipeService.searchRecipes(query, pageable);
        PagedResponse<RecipeSummaryResponse> response = PagedResponse.<RecipeSummaryResponse>builder()
                .content(paged.getContent())
                .pageNumber(paged.getNumber())
                .pageSize(paged.getSize())
                .totalElements(paged.getTotalElements())
                .totalPages(paged.getTotalPages())
                .last(paged.isLast())
                .build();
        return ResponseEntity.ok(ApiResponse.success(response, "Recipe search results"));
    }
}
