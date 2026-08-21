package com.ayurvana.controller;

import com.ayurvana.dto.response.ApiResponse;
import com.ayurvana.dto.response.HerbDetailResponse;
import com.ayurvana.dto.response.HerbSummaryResponse;
import com.ayurvana.dto.response.PagedResponse;
import com.ayurvana.service.HerbService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/herbs")
@RequiredArgsConstructor
public class HerbController {

    private final HerbService herbService;

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<HerbSummaryResponse>>> getAllHerbs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "commonName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        PagedResponse<HerbSummaryResponse> response = herbService.getAllHerbs(pageable);
        return ResponseEntity.ok(ApiResponse.success(response, "Herbs retrieved successfully"));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<PagedResponse<HerbSummaryResponse>>> searchHerbs(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PagedResponse<HerbSummaryResponse> response = herbService.searchHerbs(query, pageable);
        return ResponseEntity.ok(ApiResponse.success(response, "Herb search completed"));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ApiResponse<HerbDetailResponse>> getHerbBySlug(@PathVariable String slug) {
        HerbDetailResponse response = herbService.getHerbBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success(response, "Herb details retrieved successfully"));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponse<HerbDetailResponse>> getHerbById(@PathVariable UUID id) {
        HerbDetailResponse response = herbService.getHerbById(id);
        return ResponseEntity.ok(ApiResponse.success(response, "Herb details retrieved successfully"));
    }
}
