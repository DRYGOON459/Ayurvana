package com.ayurvana.model.entity;

import com.ayurvana.model.enums.DoshaType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dosha_assessment_results")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoshaAssessmentResult extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "primary_dosha", nullable = false)
    private DoshaType primaryDosha;

    @Enumerated(EnumType.STRING)
    @Column(name = "secondary_dosha")
    private DoshaType secondaryDosha;

    @Column(name = "prakriti_constitution", length = 100)
    private String prakritiConstitution; // e.g., "Vata-Pitta", "Tridoshic", "Pitta-Kapha"

    @Column(name = "vata_percentage", precision = 5, scale = 2)
    private BigDecimal vataPercentage;

    @Column(name = "pitta_percentage", precision = 5, scale = 2)
    private BigDecimal pittaPercentage;

    @Column(name = "kapha_percentage", precision = 5, scale = 2)
    private BigDecimal kaphaPercentage;

    @Column(name = "dietary_recommendations", columnDefinition = "TEXT")
    private String dietaryRecommendations;

    @Column(name = "lifestyle_recommendations", columnDefinition = "TEXT")
    private String lifestyleRecommendations;

    @Column(name = "recommended_herbs", columnDefinition = "TEXT")
    private String recommendedHerbs;

    @Column(name = "recommended_yoga", columnDefinition = "TEXT")
    private String recommendedYoga;
}
