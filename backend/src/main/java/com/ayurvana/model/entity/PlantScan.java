package com.ayurvana.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "plant_scans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlantScan extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "image_url", nullable = false, length = 500)
    private String imageUrl;

    @Column(name = "identified_plant_name", length = 200)
    private String identifiedPlantName;

    @Column(name = "scientific_name", length = 200)
    private String scientificName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matched_herb_id")
    private Herb matchedHerb;

    @Column(name = "confidence_score", precision = 5, scale = 4)
    private BigDecimal confidenceScore;

    @Column(name = "is_potentially_toxic", nullable = false)
    @Builder.Default
    private boolean potentiallyToxic = false;

    @Column(name = "raw_ai_response", columnDefinition = "TEXT")
    private String rawAiResponse;
}
