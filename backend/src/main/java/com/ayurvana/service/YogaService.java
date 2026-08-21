package com.ayurvana.service;

import com.ayurvana.dto.response.PagedResponse;
import com.ayurvana.dto.response.YogaPoseResponse;
import com.ayurvana.model.enums.DifficultyLevel;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface YogaService {

    PagedResponse<YogaPoseResponse> getAllPoses(Pageable pageable);

    PagedResponse<YogaPoseResponse> getPosesByDifficulty(DifficultyLevel difficulty, Pageable pageable);

    PagedResponse<YogaPoseResponse> getPosesByCategory(String category, Pageable pageable);

    YogaPoseResponse getPoseBySlug(String slug);

    YogaPoseResponse getPoseById(UUID id);
}
