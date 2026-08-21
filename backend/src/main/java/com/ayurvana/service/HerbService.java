package com.ayurvana.service;

import com.ayurvana.dto.response.HerbDetailResponse;
import com.ayurvana.dto.response.HerbSummaryResponse;
import com.ayurvana.dto.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface HerbService {

    PagedResponse<HerbSummaryResponse> getAllHerbs(Pageable pageable);

    PagedResponse<HerbSummaryResponse> searchHerbs(String query, Pageable pageable);

    HerbDetailResponse getHerbBySlug(String slug);

    HerbDetailResponse getHerbById(UUID id);
}
