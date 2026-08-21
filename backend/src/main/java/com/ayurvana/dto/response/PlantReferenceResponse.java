package com.ayurvana.dto.response;

import com.ayurvana.model.enums.EvidenceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlantReferenceResponse {

    private UUID id;
    private String sourceTitle;
    private String citation;
    private String url;
    private EvidenceType evidenceType;
}
