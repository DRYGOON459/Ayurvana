package com.ayurvana.controller;

import com.ayurvana.dto.response.ApiResponse;
import com.ayurvana.dto.response.PagedResponse;
import com.ayurvana.dto.response.PlantScanResponse;
import com.ayurvana.security.UserPrincipal;
import com.ayurvana.service.PlantScanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/plant-scanner")
@RequiredArgsConstructor
public class PlantScanController {

    private final PlantScanService plantScanService;

    @PostMapping(value = "/scan", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<PlantScanResponse>> scanPlant(
            @RequestParam("image") MultipartFile image,
            @AuthenticationPrincipal UserPrincipal currentUser
    ) {
        UUID userId = (currentUser != null) ? currentUser.getId() : null;
        PlantScanResponse response = plantScanService.scanPlant(image, userId);
        return ResponseEntity.ok(ApiResponse.success(response, "Plant identified successfully"));
    }

    @GetMapping("/history")
    public ResponseEntity<ApiResponse<PagedResponse<PlantScanResponse>>> getScanHistory(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        if (currentUser == null) {
            return ResponseEntity.ok(ApiResponse.success(
                    PagedResponse.<PlantScanResponse>builder().page(0).size(size).totalElements(0).totalPages(0).last(true).build(),
                    "Guest scan history is empty"
            ));
        }

        PagedResponse<PlantScanResponse> history = plantScanService.getUserScans(currentUser.getId(), page, size);
        return ResponseEntity.ok(ApiResponse.success(history, "Scan history retrieved"));
    }
}
