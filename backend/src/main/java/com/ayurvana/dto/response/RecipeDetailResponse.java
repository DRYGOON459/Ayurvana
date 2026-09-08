package com.ayurvana.dto.response;

import com.ayurvana.model.enums.DoshaType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDetailResponse {
    private Long id;
    private String title;
    private String sanskritName;
    private String slug;
    private DoshaType doshaSuitability;
    private String mealType;
    private String seasonSuitability;
    private Integer prepTimeMinutes;
    private Integer cookTimeMinutes;
    private Integer servings;
    private String difficulty;
    private String description;
    private String therapeuticBenefits;
    private String digestiveActionAgni;
    private String imageUrl;
    private List<String> ingredients;
    private List<String> instructions;
    private String nutritionalHighlight;
    private String classicalReference;
}
