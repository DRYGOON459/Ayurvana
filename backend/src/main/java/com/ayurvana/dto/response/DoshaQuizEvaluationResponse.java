package com.ayurvana.dto.response;

import com.ayurvana.model.enums.DoshaType;
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
public class DoshaQuizEvaluationResponse {
    private DoshaType primaryDosha;
    private DoshaType secondaryDosha;
    private String prakritiConstitution; // e.g. "Vata-Pitta (Bi-doshic)", "Tridoshic (Balanced)"
    private BigDecimal vataPercentage;
    private BigDecimal pittaPercentage;
    private BigDecimal kaphaPercentage;
    private Integer vataScore;
    private Integer pittaScore;
    private Integer kaphaScore;
    private String constitutionalSummary;
    private List<String> dietaryRecommendations;
    private List<String> lifestylePractices;
    private List<String> balancingHerbs;
    private List<String> recommendedYogaPoses;
    private List<String> foodsToFavour;
    private List<String> foodsToAvoid;
}
