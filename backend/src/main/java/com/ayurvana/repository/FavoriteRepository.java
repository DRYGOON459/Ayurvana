package com.ayurvana.repository;

import com.ayurvana.model.entity.Favorite;
import com.ayurvana.model.enums.FavoriteType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, UUID> {

    Page<Favorite> findByUserId(UUID userId, Pageable pageable);

    Page<Favorite> findByUserIdAndItemType(UUID userId, FavoriteType itemType, Pageable pageable);

    Optional<Favorite> findByUserIdAndItemTypeAndItemId(UUID userId, FavoriteType itemType, UUID itemId);

    boolean existsByUserIdAndItemTypeAndItemId(UUID userId, FavoriteType itemType, UUID itemId);

    void deleteByUserIdAndItemTypeAndItemId(UUID userId, FavoriteType itemType, UUID itemId);
}
