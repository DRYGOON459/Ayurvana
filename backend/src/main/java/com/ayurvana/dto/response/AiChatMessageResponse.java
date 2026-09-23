package com.ayurvana.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiChatMessageResponse {

    private String reply;
    private String sanskritShloka;
    private String shlokaMeaning;
    private String classicalTreatiseReference;
    private List<String> quickReplies;
    private List<ProductCard> recommendedProducts;
    private List<RecipeCard> recommendedRecipes;
    private List<HerbCard> recommendedHerbs;
    private List<DoctorCard> recommendedDoctors;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductCard {
        private Long id;
        private String name;
        private String sanskritName;
        private String slug;
        private String category;
        private String targetDosha;
        private BigDecimal price;
        private BigDecimal discountPrice;
        private String imageUrl;
        private String shortDescription;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecipeCard {
        private Long id;
        private String title;
        private String sanskritName;
        private String slug;
        private String doshaSuitability;
        private String mealType;
        private Integer cookTimeMinutes;
        private String imageUrl;
        private String description;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HerbCard {
        private String commonName;
        private String sanskritName;
        private String scientificName;
        private String slug;
        private String primaryDoshaEffect;
        private String rasa;
        private String imageUrl;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DoctorCard {
        private Long id;
        private String name;
        private String primarySpecialization;
        private Integer yearsOfExperience;
        private BigDecimal consultationFee;
        private BigDecimal rating;
        private String languagesSpoken;
    }
}
