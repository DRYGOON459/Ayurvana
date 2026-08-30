package com.ayurvana.service;

import com.ayurvana.dto.response.PagedResponse;
import com.ayurvana.dto.response.PlantScanResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface PlantScanService {

    PlantScanResponse scanPlant(MultipartFile file, UUID userId);

    PagedResponse<PlantScanResponse> getUserScans(UUID userId, int page, int size);
}
