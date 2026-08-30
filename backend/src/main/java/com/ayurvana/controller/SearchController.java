package com.ayurvana.controller;

import com.ayurvana.dto.response.ApiResponse;
import com.ayurvana.dto.response.GlobalSearchResultResponse;
import com.ayurvana.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<ApiResponse<GlobalSearchResultResponse>> globalSearch(
            @RequestParam String q,
            @RequestParam(defaultValue = "6") int limit
    ) {
        GlobalSearchResultResponse results = searchService.globalSearch(q, limit);
        return ResponseEntity.ok(ApiResponse.success(results, "Search completed successfully"));
    }
}
