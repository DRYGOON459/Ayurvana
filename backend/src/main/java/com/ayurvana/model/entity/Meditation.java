package com.ayurvana.model.entity;

import com.ayurvana.model.enums.DifficultyLevel;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "meditations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Meditation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Column(name = "category", nullable = false, length = 100)
    private String category; // 'Mindfulness', 'Pranayama', 'Chakra', 'Sleep', 'Stress Relief'

    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty", nullable = false, length = 30)
    private DifficultyLevel difficulty;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "instructions", nullable = false, columnDefinition = "TEXT")
    private String instructions;

    @Column(name = "benefits", nullable = false, columnDefinition = "TEXT")
    private String benefits;

    @Column(name = "audio_url", length = 500)
    private String audioUrl;
}
