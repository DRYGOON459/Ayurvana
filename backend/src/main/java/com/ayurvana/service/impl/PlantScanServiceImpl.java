package com.ayurvana.service.impl;

import com.ayurvana.dto.response.PagedResponse;
import com.ayurvana.dto.response.PlantScanResponse;
import com.ayurvana.model.entity.Herb;
import com.ayurvana.model.entity.PlantScan;
import com.ayurvana.model.entity.User;
import com.ayurvana.repository.HerbRepository;
import com.ayurvana.repository.PlantScanRepository;
import com.ayurvana.repository.UserRepository;
import com.ayurvana.service.PlantScanService;
import com.ayurvana.service.scanner.PlantIdentificationProvider;
import com.ayurvana.service.scanner.PlantIdentificationResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlantScanServiceImpl implements PlantScanService {

    private final PlantIdentificationProvider identificationProvider;
    private final PlantScanRepository plantScanRepository;
    private final HerbRepository herbRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public PlantScanResponse scanPlant(MultipartFile file, UUID userId) {
        log.info("Processing plant scan request for user: {}", userId);

        PlantIdentificationResult result = identificationProvider.identifyPlant(file);

        User user = null;
        if (userId != null) {
            user = userRepository.findById(userId).orElse(null);
        }

        // Try to match with existing Herb in our database by scientific name or common name
        Optional<Herb> matchedHerb = herbRepository.findByScientificNameIgnoreCase(result.getScientificName());
        if (matchedHerb.isEmpty()) {
            // Extract the first word of the plant name for robust partial matching
            String queryKeyword = result.getPlantName().split(" ")[0];
            matchedHerb = herbRepository.findByCommonNameContainingIgnoreCase(queryKeyword);
        }

        // Placeholder for uploaded image URL
        String imageUrl = "/uploads/scans/" + (file != null ? file.getOriginalFilename() : "plant.jpg");

        PlantScan plantScan = PlantScan.builder()
                .user(user)
                .imageUrl(imageUrl)
                .identifiedPlantName(result.getPlantName())
                .scientificName(result.getScientificName())
                .matchedHerb(matchedHerb.orElse(null))
                .confidenceScore(result.getConfidence())
                .potentiallyToxic(result.isPotentiallyToxic())
                .rawAiResponse(result.getRawDetails())
                .build();

        plantScan = plantScanRepository.save(plantScan);

        return mapToResponse(plantScan, result.getToxicityWarning());
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<PlantScanResponse> getUserScans(UUID userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PlantScan> scanPage = plantScanRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);

        List<PlantScanResponse> content = scanPage.getContent().stream()
                .map(scan -> mapToResponse(scan, scan.isPotentiallyToxic() ? "Potential toxicity hazard flagged." : null))
                .collect(Collectors.toList());

        return PagedResponse.<PlantScanResponse>builder()
                .content(content)
                .page(scanPage.getNumber())
                .size(scanPage.getSize())
                .totalElements(scanPage.getTotalElements())
                .totalPages(scanPage.getTotalPages())
                .last(scanPage.isLast())
                .build();
    }

    private PlantScanResponse mapToResponse(PlantScan scan, String toxicityWarning) {
        double confidencePct = scan.getConfidenceScore() != null
                ? scan.getConfidenceScore().doubleValue() * 100.0
                : 92.5;

        String herbSlug = null;
        String herbName = null;
        String safety = null;

        if (scan.getMatchedHerb() != null) {
            Herb h = scan.getMatchedHerb();
            herbSlug = h.getSlug();
            herbName = h.getCommonName();
            if (h.getSafety() != null) {
                safety = h.getSafety().getContraindications();
            }
        }

        return PlantScanResponse.builder()
                .id(scan.getId())
                .identifiedPlantName(scan.getIdentifiedPlantName())
                .scientificName(scan.getScientificName())
                .confidencePercentage(Math.round(confidencePct * 10.0) / 10.0)
                .potentiallyToxic(scan.isPotentiallyToxic())
                .toxicityWarning(toxicityWarning)
                .matchedHerbSlug(herbSlug)
                .matchedHerbName(herbName)
                .safetyGuidelines(safety)
                .imageUrl(scan.getImageUrl())
                .scannedAt(scan.getCreatedAt())
                .build();
    }
}
