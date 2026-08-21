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
public class ArticleDetailResponse {

    private UUID id;
    private String title;
    private String slug;
    private String category;
    private String authorName;
    private Integer readTimeMinutes;
    private String coverImageUrl;
    private String summary;
    private String content;
    private String referencesContent;
    private Instant createdAt;
}
