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
public class YogaPoseResponse {

    private UUID id;
    private String englishName;
    private String sanskritName;
    private String slug;
    private DifficultyLevel difficulty;
    private String category;
    private String doshaEffect;
    private String instructions;
    private String benefits;
    private String precautions;
    private String imageUrl;
    private String videoUrl;
    private Integer recommendedDurationSeconds;
}
