package com.ayurvana.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HerbDetailResponse {

    private UUID id;
    private String commonName;
    private String sanskritName;
    private String scientificName;
    private String botanicalFamily;
    private String slug;
    private String rasa;
    private String virya;
    private String vipaka;
    private String primaryDoshaEffect;
    private String partsUsed;
    private String description;
    private String traditionalUses;
    private String modernResearchSummary;
    private String dosageAndPreparations;
    private String imageUrl;
    private PlantSafetyResponse safety;
    private List<PlantReferenceResponse> references;
}
