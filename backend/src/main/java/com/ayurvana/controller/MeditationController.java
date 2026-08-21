package com.ayurvana.controller;

import com.ayurvana.dto.response.ApiResponse;
import com.ayurvana.dto.response.MeditationResponse;
import com.ayurvana.dto.response.PagedResponse;
import com.ayurvana.service.MeditationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/meditations")
@RequiredArgsConstructor
public class MeditationController {

    private final MeditationService meditationService;

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<MeditationResponse>>> getAllMeditations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PagedResponse<MeditationResponse> response = meditationService.getAllMeditations(pageable);
        return ResponseEntity.ok(ApiResponse.success(response, "Meditations retrieved successfully"));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<PagedResponse<MeditationResponse>>> getMeditationsByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PagedResponse<MeditationResponse> response = meditationService.getMeditationsByCategory(category, pageable);
        return ResponseEntity.ok(ApiResponse.success(response, "Meditations retrieved by category"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MeditationResponse>> getMeditationById(@PathVariable UUID id) {
        MeditationResponse response = meditationService.getMeditationById(id);
        return ResponseEntity.ok(ApiResponse.success(response, "Meditation session retrieved successfully"));
    }
}
