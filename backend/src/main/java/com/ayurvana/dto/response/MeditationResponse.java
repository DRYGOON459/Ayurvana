package com.ayurvana.dto.response;

import com.ayurvana.model.enums.DifficultyLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeditationResponse {

    private UUID id;
    private String title;
    private String category;
    private Integer durationMinutes;
    private DifficultyLevel difficulty;
    private String description;
    private String instructions;
    private String benefits;
    private String audioUrl;
}
