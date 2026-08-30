package com.ayurvana.service.impl;

import com.ayurvana.dto.request.AddFavoriteRequest;
import com.ayurvana.dto.response.FavoriteItemResponse;
import com.ayurvana.dto.response.PagedResponse;
import com.ayurvana.exception.ResourceNotFoundException;
import com.ayurvana.model.entity.Favorite;
import com.ayurvana.model.entity.User;
import com.ayurvana.model.enums.FavoriteType;
import com.ayurvana.repository.*;
import com.ayurvana.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final HerbRepository herbRepository;
    private final YogaPoseRepository yogaPoseRepository;
    private final ArticleRepository articleRepository;
    private final DoctorProfileRepository doctorProfileRepository;

    @Override
    @Transactional
    public FavoriteItemResponse addFavorite(UUID userId, AddFavoriteRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Optional<Favorite> existing = favoriteRepository.findByUserIdAndItemTypeAndItemId(
                userId, request.getItemType(), request.getItemId()
        );

        Favorite favorite;
        if (existing.isPresent()) {
            favorite = existing.get();
        } else {
            favorite = Favorite.builder()
                    .user(user)
                    .itemType(request.getItemType())
                    .itemId(request.getItemId())
                    .build();
            favorite = favoriteRepository.save(favorite);
        }

        return resolveFavoriteDetails(favorite);
    }

    @Override
    @Transactional
    public void removeFavorite(UUID userId, FavoriteType itemType, UUID itemId) {
        favoriteRepository.deleteByUserIdAndItemTypeAndItemId(userId, itemType, itemId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isFavorite(UUID userId, FavoriteType itemType, UUID itemId) {
        return favoriteRepository.existsByUserIdAndItemTypeAndItemId(userId, itemType, itemId);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<FavoriteItemResponse> getUserFavorites(UUID userId, FavoriteType itemType, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<Favorite> favoritesPage = (itemType == null)
                ? favoriteRepository.findByUserId(userId, pageable)
                : favoriteRepository.findByUserIdAndItemType(userId, itemType, pageable);

        List<FavoriteItemResponse> content = favoritesPage.getContent().stream()
                .map(this::resolveFavoriteDetails)
                .collect(Collectors.toList());

        return PagedResponse.<FavoriteItemResponse>builder()
                .content(content)
                .page(favoritesPage.getNumber())
                .size(favoritesPage.getSize())
                .totalElements(favoritesPage.getTotalElements())
                .totalPages(favoritesPage.getTotalPages())
                .last(favoritesPage.isLast())
                .build();
    }

    private FavoriteItemResponse resolveFavoriteDetails(Favorite fav) {
        String title = "Saved Item";
        String subtitle = "";
        String slug = "";
        String imageUrl = null;

        switch (fav.getItemType()) {
            case HERB:
                var herbOpt = herbRepository.findById(fav.getItemId());
                if (herbOpt.isPresent()) {
                    var h = herbOpt.get();
                    title = h.getCommonName();
                    subtitle = h.getSanskritName();
                    slug = h.getSlug();
                    imageUrl = h.getImageUrl();
                }
                break;

            case YOGA:
                var yogaOpt = yogaPoseRepository.findById(fav.getItemId());
                if (yogaOpt.isPresent()) {
                    var y = yogaOpt.get();
                    title = y.getEnglishName();
                    subtitle = y.getSanskritName();
                    slug = y.getSlug();
                    imageUrl = y.getImageUrl();
                }
                break;

            case ARTICLE:
                var articleOpt = articleRepository.findById(fav.getItemId());
                if (articleOpt.isPresent()) {
                    var a = articleOpt.get();
                    title = a.getTitle();
                    subtitle = "By " + a.getAuthorName();
                    slug = a.getSlug();
                    imageUrl = a.getCoverImageUrl();
                }
                break;

            case DOCTOR:
                var doctorOpt = doctorProfileRepository.findById(fav.getItemId());
                if (doctorOpt.isPresent()) {
                    var d = doctorOpt.get();
                    title = d.getUser() != null ? d.getUser().getFullName() : "Ayurvedic Doctor";
                    subtitle = d.getPrimarySpecialization();
                    slug = d.getId().toString();
                    imageUrl = d.getUser() != null ? d.getUser().getAvatarUrl() : null;
                }
                break;

            default:
                break;
        }

        return FavoriteItemResponse.builder()
                .id(fav.getId())
                .itemType(fav.getItemType())
                .itemId(fav.getItemId())
                .title(title)
                .subtitle(subtitle)
                .slug(slug)
                .imageUrl(imageUrl)
                .createdAt(fav.getCreatedAt())
                .build();
    }
}
