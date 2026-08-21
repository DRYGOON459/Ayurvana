package com.ayurvana.service;

import com.ayurvana.dto.response.ArticleDetailResponse;
import com.ayurvana.dto.response.ArticleSummaryResponse;
import com.ayurvana.dto.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ArticleService {

    PagedResponse<ArticleSummaryResponse> getAllArticles(Pageable pageable);

    PagedResponse<ArticleSummaryResponse> getArticlesByCategory(String category, Pageable pageable);

    ArticleDetailResponse getArticleBySlug(String slug);

    ArticleDetailResponse getArticleById(UUID id);
}
