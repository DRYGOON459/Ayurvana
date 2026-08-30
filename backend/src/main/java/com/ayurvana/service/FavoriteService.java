package com.ayurvana.service;

import com.ayurvana.dto.request.AddFavoriteRequest;
import com.ayurvana.dto.response.FavoriteItemResponse;
import com.ayurvana.dto.response.PagedResponse;
import com.ayurvana.model.enums.FavoriteType;

import java.util.UUID;

public interface FavoriteService {

    FavoriteItemResponse addFavorite(UUID userId, AddFavoriteRequest request);

    void removeFavorite(UUID userId, FavoriteType itemType, UUID itemId);

    boolean isFavorite(UUID userId, FavoriteType itemType, UUID itemId);

    PagedResponse<FavoriteItemResponse> getUserFavorites(UUID userId, FavoriteType itemType, int page, int size);
}
