package com.ayurvana.controller;

import com.ayurvana.dto.response.ApiResponse;
import com.ayurvana.dto.response.ArticleDetailResponse;
import com.ayurvana.dto.response.ArticleSummaryResponse;
import com.ayurvana.dto.response.PagedResponse;
import com.ayurvana.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<ArticleSummaryResponse>>> getAllArticles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        PagedResponse<ArticleSummaryResponse> response = articleService.getAllArticles(pageable);
        return ResponseEntity.ok(ApiResponse.success(response, "Articles retrieved successfully"));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<PagedResponse<ArticleSummaryResponse>>> getArticlesByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        PagedResponse<ArticleSummaryResponse> response = articleService.getArticlesByCategory(category, pageable);
        return ResponseEntity.ok(ApiResponse.success(response, "Articles retrieved by category"));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ApiResponse<ArticleDetailResponse>> getArticleBySlug(@PathVariable String slug) {
        ArticleDetailResponse response = articleService.getArticleBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success(response, "Article details retrieved successfully"));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponse<ArticleDetailResponse>> getArticleById(@PathVariable UUID id) {
        ArticleDetailResponse response = articleService.getArticleById(id);
        return ResponseEntity.ok(ApiResponse.success(response, "Article details retrieved successfully"));
    }
}
