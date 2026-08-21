package com.ayurvana.service;

import com.ayurvana.dto.response.AyurvedaTopicResponse;
import com.ayurvana.dto.response.PagedResponse;
import com.ayurvana.model.enums.DoshaType;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AyurvedaService {

    PagedResponse<AyurvedaTopicResponse> getAllTopics(Pageable pageable);

    PagedResponse<AyurvedaTopicResponse> getTopicsByCategory(String category, Pageable pageable);

    PagedResponse<AyurvedaTopicResponse> getTopicsByDosha(DoshaType doshaType, Pageable pageable);

    AyurvedaTopicResponse getTopicBySlug(String slug);

    AyurvedaTopicResponse getTopicById(UUID id);
}
