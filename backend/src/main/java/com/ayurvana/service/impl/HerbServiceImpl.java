package com.ayurvana.service.impl;

import com.ayurvana.dto.response.*;
import com.ayurvana.exception.ResourceNotFoundException;
import com.ayurvana.model.entity.Herb;
import com.ayurvana.model.entity.PlantReference;
import com.ayurvana.model.entity.PlantSafety;
import com.ayurvana.repository.HerbRepository;
import com.ayurvana.service.HerbService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HerbServiceImpl implements HerbService {

    private final HerbRepository herbRepository;

    @Override
    public PagedResponse<HerbSummaryResponse> getAllHerbs(Pageable pageable) {
        Page<Herb> page = herbRepository.findByPublishedTrue(pageable);
        List<HerbSummaryResponse> content = page.getContent().stream()
                .map(this::mapToSummaryResponse)
                .collect(Collectors.toList());

        return PagedResponse.<HerbSummaryResponse>builder()
                .content(content)
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }

    @Override
    public PagedResponse<HerbSummaryResponse> searchHerbs(String query, Pageable pageable) {
        Page<Herb> page = herbRepository.searchHerbs(query, pageable);
        List<HerbSummaryResponse> content = page.getContent().stream()
                .map(this::mapToSummaryResponse)
                .collect(Collectors.toList());

        return PagedResponse.<HerbSummaryResponse>builder()
                .content(content)
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }

    @Override
    public HerbDetailResponse getHerbBySlug(String slug) {
        Herb herb = herbRepository.findBySlugWithSafetyAndReferences(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Herb", "slug", slug));
        return mapToDetailResponse(herb);
    }

    @Override
    public HerbDetailResponse getHerbById(UUID id) {
        Herb herb = herbRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Herb", "id", id));
        return mapToDetailResponse(herb);
    }

    private HerbSummaryResponse mapToSummaryResponse(Herb herb) {
        return HerbSummaryResponse.builder()
                .id(herb.getId())
                .commonName(herb.getCommonName())
                .sanskritName(herb.getSanskritName())
                .scientificName(herb.getScientificName())
                .botanicalFamily(herb.getBotanicalFamily())
                .slug(herb.getSlug())
                .primaryDoshaEffect(herb.getPrimaryDoshaEffect())
                .rasa(herb.getRasa())
                .virya(herb.getVirya())
                .imageUrl(herb.getImageUrl())
                .toxicityLevel(herb.getSafety() != null ? herb.getSafety().getToxicityLevel() : null)
                .build();
    }

    private HerbDetailResponse mapToDetailResponse(Herb herb) {
        PlantSafety safety = herb.getSafety();
        PlantSafetyResponse safetyResponse = null;
        if (safety != null) {
            safetyResponse = PlantSafetyResponse.builder()
                    .id(safety.getId())
                    .toxicityLevel(safety.getToxicityLevel())
                    .contraindications(safety.getContraindications())
                    .interactionWarnings(safety.getInteractionWarnings())
                    .pregnancySafety(safety.getPregnancySafety())
                    .overdoseSymptoms(safety.getOverdoseSymptoms())
                    .emergencyAction(safety.getEmergencyAction())
                    .build();
        }

        List<PlantReferenceResponse> referenceResponses = Collections.emptyList();
        if (herb.getReferences() != null) {
            referenceResponses = herb.getReferences().stream()
                    .map(this::mapToReferenceResponse)
                    .collect(Collectors.toList());
        }

        return HerbDetailResponse.builder()
                .id(herb.getId())
                .commonName(herb.getCommonName())
                .sanskritName(herb.getSanskritName())
                .scientificName(herb.getScientificName())
                .botanicalFamily(herb.getBotanicalFamily())
                .slug(herb.getSlug())
                .rasa(herb.getRasa())
                .virya(herb.getVirya())
                .vipaka(herb.getVipaka())
                .primaryDoshaEffect(herb.getPrimaryDoshaEffect())
                .partsUsed(herb.getPartsUsed())
                .description(herb.getDescription())
                .traditionalUses(herb.getTraditionalUses())
                .modernResearchSummary(herb.getModernResearchSummary())
                .dosageAndPreparations(herb.getDosageAndPreparations())
                .imageUrl(herb.getImageUrl())
                .safety(safetyResponse)
                .references(referenceResponses)
                .build();
    }

    private PlantReferenceResponse mapToReferenceResponse(PlantReference ref) {
        return PlantReferenceResponse.builder()
                .id(ref.getId())
                .sourceTitle(ref.getSourceTitle())
                .citation(ref.getCitation())
                .url(ref.getUrl())
                .evidenceType(ref.getEvidenceType())
                .build();
    }
}
