package com.ayurvana.model.entity;

import com.ayurvana.model.enums.DoshaType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ayurvedic_recipes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AyurvedicRecipe extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(name = "sanskrit_name", length = 150)
    private String sanskritName;

    @Column(nullable = false, unique = true, length = 150)
    private String slug;

    @Enumerated(EnumType.STRING)
    @Column(name = "dosha_suitability", nullable = false)
    private DoshaType doshaSuitability;

    @Column(name = "meal_type", length = 50)
    private String mealType; // Breakfast, Lunch, Dinner, Beverage, Herbal Remedy, Dessert

    @Column(name = "season_suitability", length = 100)
    private String seasonSuitability; // All Seasons, Vasanta (Spring), Grishma (Summer), Sharad (Autumn), Hemanta (Winter)

    @Column(name = "prep_time_minutes")
    private Integer prepTimeMinutes;

    @Column(name = "cook_time_minutes")
    private Integer cookTimeMinutes;

    @Column(name = "servings")
    private Integer servings;

    @Column(name = "difficulty", length = 30)
    private String difficulty; // Easy, Moderate, Advanced

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "therapeutic_benefits", columnDefinition = "TEXT")
    private String therapeuticBenefits;

    @Column(name = "digestive_action_agni", columnDefinition = "TEXT")
    private String digestiveActionAgni;

    @Column(name = "imageUrl", length = 500)
    private String imageUrl;

    @ElementCollection
    @CollectionTable(name = "recipe_ingredients", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "ingredient", columnDefinition = "TEXT")
    @Builder.Default
    private List<String> ingredients = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "recipe_instructions", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "instruction", columnDefinition = "TEXT")
    @OrderColumn(name = "step_order")
    @Builder.Default
    private List<String> instructions = new ArrayList<>();

    @Column(name = "nutritional_highlight", length = 255)
    private String nutritionalHighlight;

    @Column(name = "classical_reference", length = 255)
    private String classicalReference;
}
