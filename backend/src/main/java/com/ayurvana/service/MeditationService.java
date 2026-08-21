package com.ayurvana.service;

import com.ayurvana.dto.response.MeditationResponse;
import com.ayurvana.dto.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface MeditationService {

    PagedResponse<MeditationResponse> getAllMeditations(Pageable pageable);

    PagedResponse<MeditationResponse> getMeditationsByCategory(String category, Pageable pageable);

    MeditationResponse getMeditationById(UUID id);
}
