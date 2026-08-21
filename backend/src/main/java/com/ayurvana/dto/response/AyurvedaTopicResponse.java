package com.ayurvana.dto.response;

import com.ayurvana.model.enums.DoshaType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AyurvedaTopicResponse {

    private UUID id;
    private String title;
    private String sanskritTitle;
    private String slug;
    private String category;
    private DoshaType doshaAssociation;
    private String summary;
    private String content;
    private String traditionalReferences;
    private String imageUrl;
}
