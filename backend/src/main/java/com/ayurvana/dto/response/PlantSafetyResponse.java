package com.ayurvana.dto.response;

import com.ayurvana.model.enums.ToxicityLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlantSafetyResponse {

    private UUID id;
    private ToxicityLevel toxicityLevel;
    private String contraindications;
    private String interactionWarnings;
    private String pregnancySafety;
    private String overdoseSymptoms;
    private String emergencyAction;
}
