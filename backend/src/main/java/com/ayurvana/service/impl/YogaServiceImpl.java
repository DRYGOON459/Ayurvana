package com.ayurvana.service.impl;

import com.ayurvana.dto.response.PagedResponse;
import com.ayurvana.dto.response.YogaPoseResponse;
import com.ayurvana.exception.ResourceNotFoundException;
import com.ayurvana.model.entity.YogaPose;
import com.ayurvana.model.enums.DifficultyLevel;
import com.ayurvana.repository.YogaPoseRepository;
import com.ayurvana.service.YogaService;
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
public class YogaServiceImpl implements YogaService {

    private final YogaPoseRepository yogaPoseRepository;

    @Override
    public PagedResponse<YogaPoseResponse> getAllPoses(Pageable pageable) {
        Page<YogaPose> page = yogaPoseRepository.findAll(pageable);
        return mapToPagedResponse(page);
    }

    @Override
    public PagedResponse<YogaPoseResponse> getPosesByDifficulty(DifficultyLevel difficulty, Pageable pageable) {
        Page<YogaPose> page = yogaPoseRepository.findByDifficulty(difficulty, pageable);
        return mapToPagedResponse(page);
    }

    @Override
    public PagedResponse<YogaPoseResponse> getPosesByCategory(String category, Pageable pageable) {
        Page<YogaPose> page = yogaPoseRepository.findByCategoryIgnoreCase(category, pageable);
        return mapToPagedResponse(page);
    }

    @Override
    public YogaPoseResponse getPoseBySlug(String slug) {
        YogaPose pose = yogaPoseRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("YogaPose", "slug", slug));
        return mapToResponse(pose);
    }

    @Override
    public YogaPoseResponse getPoseById(UUID id) {
        YogaPose pose = yogaPoseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("YogaPose", "id", id));
        return mapToResponse(pose);
    }

    private PagedResponse<YogaPoseResponse> mapToPagedResponse(Page<YogaPose> page) {
        List<YogaPoseResponse> content = page.getContent().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return PagedResponse.<YogaPoseResponse>builder()
                .content(content)
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }

    private YogaPoseResponse mapToResponse(YogaPose pose) {
        return YogaPoseResponse.builder()
                .id(pose.getId())
                .englishName(pose.getEnglishName())
                .sanskritName(pose.getSanskritName())
                .slug(pose.getSlug())
                .difficulty(pose.getDifficulty())
                .category(pose.getCategory())
                .doshaEffect(pose.getDoshaEffect())
                .instructions(pose.getInstructions())
                .benefits(pose.getBenefits())
                .precautions(pose.getPrecautions())
                .imageUrl(pose.getImageUrl())
                .videoUrl(pose.getVideoUrl())
                .recommendedDurationSeconds(pose.getRecommendedDurationSeconds())
                .build();
    }
}
