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
public class HerbSummaryResponse {

    private UUID id;
    private String commonName;
    private String sanskritName;
    private String scientificName;
    private String botanicalFamily;
    private String slug;
    private String primaryDoshaEffect;
    private String rasa;
    private String virya;
    private String imageUrl;
    private ToxicityLevel toxicityLevel;
}
