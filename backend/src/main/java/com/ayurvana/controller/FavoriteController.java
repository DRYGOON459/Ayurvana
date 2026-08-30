package com.ayurvana.controller;

import com.ayurvana.dto.request.AddFavoriteRequest;
import com.ayurvana.dto.response.ApiResponse;
import com.ayurvana.dto.response.FavoriteItemResponse;
import com.ayurvana.dto.response.PagedResponse;
import com.ayurvana.model.enums.FavoriteType;
import com.ayurvana.security.UserPrincipal;
import com.ayurvana.service.FavoriteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping
    public ResponseEntity<ApiResponse<FavoriteItemResponse>> addFavorite(
            @Valid @RequestBody AddFavoriteRequest request,
            @AuthenticationPrincipal UserPrincipal currentUser
    ) {
        FavoriteItemResponse response = favoriteService.addFavorite(currentUser.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Item added to favorites"));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> removeFavorite(
            @RequestParam FavoriteType type,
            @RequestParam UUID itemId,
            @AuthenticationPrincipal UserPrincipal currentUser
    ) {
        favoriteService.removeFavorite(currentUser.getId(), type, itemId);
        return ResponseEntity.ok(ApiResponse.success(null, "Item removed from favorites"));
    }

    @GetMapping("/check")
    public ResponseEntity<ApiResponse<Map<String, Boolean>>> checkFavorite(
            @RequestParam FavoriteType type,
            @RequestParam UUID itemId,
            @AuthenticationPrincipal UserPrincipal currentUser
    ) {
        boolean isFav = (currentUser != null) && favoriteService.isFavorite(currentUser.getId(), type, itemId);
        return ResponseEntity.ok(ApiResponse.success(Map.of("isFavorite", isFav), "Favorite status checked"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<FavoriteItemResponse>>> getUserFavorites(
            @RequestParam(required = false) FavoriteType type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @AuthenticationPrincipal UserPrincipal currentUser
    ) {
        PagedResponse<FavoriteItemResponse> favorites =
                favoriteService.getUserFavorites(currentUser.getId(), type, page, size);
        return ResponseEntity.ok(ApiResponse.success(favorites, "Favorites retrieved successfully"));
    }
}
