package com.ayurvana.service.scanner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlantIdentificationResult {

    private String plantName;
    private String scientificName;
    private BigDecimal confidence;
    private boolean potentiallyToxic;
    private String toxicityWarning;
    private String rawDetails;
}
