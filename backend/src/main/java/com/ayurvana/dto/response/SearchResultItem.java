package com.ayurvana.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchResultItem {

    private UUID id;
    private String type; // 'HERB', 'YOGA', 'AYURVEDA', 'ARTICLE'
    private String title;
    private String subtitle;
    private String slug;
    private String category;
    private String snippet;
    private String imageUrl;
}
