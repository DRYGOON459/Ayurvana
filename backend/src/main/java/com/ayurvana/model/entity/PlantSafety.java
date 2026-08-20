package com.ayurvana.model.entity;

import com.ayurvana.model.enums.ToxicityLevel;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "plant_safeties")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlantSafety {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "herb_id", unique = true, nullable = false)
    private Herb herb;

    @Enumerated(EnumType.STRING)
    @Column(name = "toxicity_level", nullable = false, length = 50)
    @Builder.Default
    private ToxicityLevel toxicityLevel = ToxicityLevel.SAFE;

    @Column(name = "contraindications", columnDefinition = "TEXT")
    private String contraindications;

    @Column(name = "interaction_warnings", columnDefinition = "TEXT")
    private String interactionWarnings;

    @Column(name = "pregnancy_safety", columnDefinition = "TEXT")
    private String pregnancySafety;

    @Column(name = "overdose_symptoms", columnDefinition = "TEXT")
    private String overdoseSymptoms;

    @Column(name = "emergency_action", columnDefinition = "TEXT")
    private String emergencyAction;
}
