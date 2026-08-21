package com.ayurvana.controller;

import com.ayurvana.dto.response.ApiResponse;
import com.ayurvana.dto.response.PagedResponse;
import com.ayurvana.dto.response.YogaPoseResponse;
import com.ayurvana.model.enums.DifficultyLevel;
import com.ayurvana.service.YogaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/yoga")
@RequiredArgsConstructor
public class YogaController {

    private final YogaService yogaService;

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<YogaPoseResponse>>> getAllPoses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "englishName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        PagedResponse<YogaPoseResponse> response = yogaService.getAllPoses(pageable);
        return ResponseEntity.ok(ApiResponse.success(response, "Yoga poses retrieved successfully"));
    }

    @GetMapping("/difficulty/{difficulty}")
    public ResponseEntity<ApiResponse<PagedResponse<YogaPoseResponse>>> getPosesByDifficulty(
            @PathVariable DifficultyLevel difficulty,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PagedResponse<YogaPoseResponse> response = yogaService.getPosesByDifficulty(difficulty, pageable);
        return ResponseEntity.ok(ApiResponse.success(response, "Yoga poses retrieved by difficulty"));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<PagedResponse<YogaPoseResponse>>> getPosesByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PagedResponse<YogaPoseResponse> response = yogaService.getPosesByCategory(category, pageable);
        return ResponseEntity.ok(ApiResponse.success(response, "Yoga poses retrieved by category"));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ApiResponse<YogaPoseResponse>> getPoseBySlug(@PathVariable String slug) {
        YogaPoseResponse response = yogaService.getPoseBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success(response, "Yoga pose retrieved successfully"));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponse<YogaPoseResponse>> getPoseById(@PathVariable UUID id) {
        YogaPoseResponse response = yogaService.getPoseById(id);
        return ResponseEntity.ok(ApiResponse.success(response, "Yoga pose retrieved successfully"));
    }
}
