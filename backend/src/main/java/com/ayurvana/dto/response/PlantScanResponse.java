package com.ayurvana.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlantScanResponse {

    private UUID id;
    private String identifiedPlantName;
    private String scientificName;
    private double confidencePercentage;
    private boolean potentiallyToxic;
    private String toxicityWarning;
    private String matchedHerbSlug;
    private String matchedHerbName;
    private String safetyGuidelines;
    private String imageUrl;
    private Instant scannedAt;
}
