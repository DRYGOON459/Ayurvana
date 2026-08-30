package com.ayurvana.dto.response;

import com.ayurvana.model.enums.FavoriteType;
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
public class FavoriteItemResponse {

    private UUID id;
    private FavoriteType itemType;
    private UUID itemId;
    private String title;
    private String subtitle;
    private String slug;
    private String imageUrl;
    private Instant createdAt;
}
