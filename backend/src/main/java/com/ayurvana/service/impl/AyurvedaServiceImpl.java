package com.ayurvana.service.impl;

import com.ayurvana.dto.response.AyurvedaTopicResponse;
import com.ayurvana.dto.response.PagedResponse;
import com.ayurvana.exception.ResourceNotFoundException;
import com.ayurvana.model.entity.AyurvedaTopic;
import com.ayurvana.model.enums.DoshaType;
import com.ayurvana.repository.AyurvedaTopicRepository;
import com.ayurvana.service.AyurvedaService;
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
public class AyurvedaServiceImpl implements AyurvedaService {

    private final AyurvedaTopicRepository topicRepository;

    @Override
    public PagedResponse<AyurvedaTopicResponse> getAllTopics(Pageable pageable) {
        Page<AyurvedaTopic> page = topicRepository.findAll(pageable);
        return mapToPagedResponse(page);
    }

    @Override
    public PagedResponse<AyurvedaTopicResponse> getTopicsByCategory(String category, Pageable pageable) {
        Page<AyurvedaTopic> page = topicRepository.findByCategoryIgnoreCaseAndPublishedTrue(category, pageable);
        return mapToPagedResponse(page);
    }

    @Override
    public PagedResponse<AyurvedaTopicResponse> getTopicsByDosha(DoshaType doshaType, Pageable pageable) {
        Page<AyurvedaTopic> page = topicRepository.findByDoshaAssociationAndPublishedTrue(doshaType, pageable);
        return mapToPagedResponse(page);
    }

    @Override
    public AyurvedaTopicResponse getTopicBySlug(String slug) {
        AyurvedaTopic topic = topicRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("AyurvedaTopic", "slug", slug));
        return mapToResponse(topic);
    }

    @Override
    public AyurvedaTopicResponse getTopicById(UUID id) {
        AyurvedaTopic topic = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AyurvedaTopic", "id", id));
        return mapToResponse(topic);
    }

    private PagedResponse<AyurvedaTopicResponse> mapToPagedResponse(Page<AyurvedaTopic> page) {
        List<AyurvedaTopicResponse> content = page.getContent().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return PagedResponse.<AyurvedaTopicResponse>builder()
                .content(content)
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }

    private AyurvedaTopicResponse mapToResponse(AyurvedaTopic topic) {
        return AyurvedaTopicResponse.builder()
                .id(topic.getId())
                .title(topic.getTitle())
                .sanskritTitle(topic.getSanskritTitle())
                .slug(topic.getSlug())
                .category(topic.getCategory())
                .doshaAssociation(topic.getDoshaAssociation())
                .summary(topic.getSummary())
                .content(topic.getContent())
                .traditionalReferences(topic.getTraditionalReferences())
                .imageUrl(topic.getImageUrl())
                .build();
    }
}
