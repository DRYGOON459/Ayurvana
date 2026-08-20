package com.ayurvana.model.entity;

import com.ayurvana.model.enums.DifficultyLevel;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "yoga_poses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class YogaPose extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "english_name", nullable = false, length = 150)
    private String englishName;

    @Column(name = "sanskrit_name", nullable = false, length = 150)
    private String sanskritName;

    @Column(name = "slug", unique = true, nullable = false, length = 200)
    private String slug;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty", nullable = false, length = 30)
    private DifficultyLevel difficulty;

    @Column(name = "category", nullable = false, length = 100)
    private String category; // 'Standing', 'Forward Bend', 'Backbend', 'Twist', 'Inversion'

    @Column(name = "dosha_effect", length = 100) // 'Calms Vata, Stimulates Agni'
    private String doshaEffect;

    @Column(name = "instructions", nullable = false, columnDefinition = "TEXT")
    private String instructions;

    @Column(name = "benefits", nullable = false, columnDefinition = "TEXT")
    private String benefits;

    @Column(name = "precautions", nullable = false, columnDefinition = "TEXT")
    private String precautions;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "video_url", length = 500)
    private String videoUrl;

    @Column(name = "recommended_duration_seconds")
    @Builder.Default
    private Integer recommendedDurationSeconds = 60;
}
