package com.ayurvana.controller;

import com.ayurvana.dto.response.ApiResponse;
import com.ayurvana.dto.response.AyurvedaTopicResponse;
import com.ayurvana.dto.response.PagedResponse;
import com.ayurvana.model.enums.DoshaType;
import com.ayurvana.service.AyurvedaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/ayurveda")
@RequiredArgsConstructor
public class AyurvedaController {

    private final AyurvedaService ayurvedaService;

    @GetMapping("/topics")
    public ResponseEntity<ApiResponse<PagedResponse<AyurvedaTopicResponse>>> getAllTopics(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PagedResponse<AyurvedaTopicResponse> response = ayurvedaService.getAllTopics(pageable);
        return ResponseEntity.ok(ApiResponse.success(response, "Ayurveda topics retrieved successfully"));
    }

    @GetMapping("/topics/category/{category}")
    public ResponseEntity<ApiResponse<PagedResponse<AyurvedaTopicResponse>>> getTopicsByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PagedResponse<AyurvedaTopicResponse> response = ayurvedaService.getTopicsByCategory(category, pageable);
        return ResponseEntity.ok(ApiResponse.success(response, "Ayurveda topics retrieved by category"));
    }

    @GetMapping("/topics/dosha/{doshaType}")
    public ResponseEntity<ApiResponse<PagedResponse<AyurvedaTopicResponse>>> getTopicsByDosha(
            @PathVariable DoshaType doshaType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PagedResponse<AyurvedaTopicResponse> response = ayurvedaService.getTopicsByDosha(doshaType, pageable);
        return ResponseEntity.ok(ApiResponse.success(response, "Ayurveda topics retrieved by dosha"));
    }

    @GetMapping("/topics/{slug}")
    public ResponseEntity<ApiResponse<AyurvedaTopicResponse>> getTopicBySlug(@PathVariable String slug) {
        AyurvedaTopicResponse response = ayurvedaService.getTopicBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success(response, "Ayurveda topic retrieved successfully"));
    }

    @GetMapping("/topics/id/{id}")
    public ResponseEntity<ApiResponse<AyurvedaTopicResponse>> getTopicById(@PathVariable UUID id) {
        AyurvedaTopicResponse response = ayurvedaService.getTopicById(id);
        return ResponseEntity.ok(ApiResponse.success(response, "Ayurveda topic retrieved successfully"));
    }
}
