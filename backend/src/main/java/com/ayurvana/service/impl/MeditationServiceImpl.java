package com.ayurvana.service.impl;

import com.ayurvana.dto.response.MeditationResponse;
import com.ayurvana.dto.response.PagedResponse;
import com.ayurvana.exception.ResourceNotFoundException;
import com.ayurvana.model.entity.Meditation;
import com.ayurvana.repository.MeditationRepository;
import com.ayurvana.service.MeditationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MeditationServiceImpl implements MeditationService {

    private final MeditationRepository meditationRepository;

    @Override
    public PagedResponse<MeditationResponse> getAllMeditations(Pageable pageable) {
        Page<Meditation> page = meditationRepository.findAll(pageable);
        return mapToPagedResponse(page);
    }

    @Override
    public PagedResponse<MeditationResponse> getMeditationsByCategory(String category, Pageable pageable) {
        Page<Meditation> page = meditationRepository.findByCategoryIgnoreCase(category, pageable);
        return mapToPagedResponse(page);
    }

    @Override
    public MeditationResponse getMeditationById(UUID id) {
        Meditation meditation = meditationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meditation", "id", id));
        return mapToResponse(meditation);
    }

    private PagedResponse<MeditationResponse> mapToPagedResponse(Page<Meditation> page) {
        List<MeditationResponse> content = page.getContent().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return PagedResponse.<MeditationResponse>builder()
                .content(content)
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }

    private MeditationResponse mapToResponse(Meditation meditation) {
        return MeditationResponse.builder()
                .id(meditation.getId())
                .title(meditation.getTitle())
                .category(meditation.getCategory())
                .durationMinutes(meditation.getDurationMinutes())
                .difficulty(meditation.getDifficulty())
                .description(meditation.getDescription())
                .instructions(meditation.getInstructions())
                .benefits(meditation.getBenefits())
                .audioUrl(meditation.getAudioUrl())
                .build();
    }
}
