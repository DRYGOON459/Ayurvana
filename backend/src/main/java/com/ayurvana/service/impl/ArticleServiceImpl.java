package com.ayurvana.service.impl;

import com.ayurvana.dto.response.ArticleDetailResponse;
import com.ayurvana.dto.response.ArticleSummaryResponse;
import com.ayurvana.dto.response.PagedResponse;
import com.ayurvana.exception.ResourceNotFoundException;
import com.ayurvana.model.entity.Article;
import com.ayurvana.repository.ArticleRepository;
import com.ayurvana.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    @Override
    public PagedResponse<ArticleSummaryResponse> getAllArticles(Pageable pageable) {
        Page<Article> page = articleRepository.findByPublishedTrue(pageable);
        return mapToPagedSummaryResponse(page);
    }

    @Override
    public PagedResponse<ArticleSummaryResponse> getArticlesByCategory(String category, Pageable pageable) {
        Page<Article> page = articleRepository.findByCategoryIgnoreCaseAndPublishedTrue(category, pageable);
        return mapToPagedSummaryResponse(page);
    }

    @Override
    public ArticleDetailResponse getArticleBySlug(String slug) {
        Article article = articleRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "slug", slug));
        return mapToDetailResponse(article);
    }

    @Override
    public ArticleDetailResponse getArticleById(UUID id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", id));
        return mapToDetailResponse(article);
    }

    private PagedResponse<ArticleSummaryResponse> mapToPagedSummaryResponse(Page<Article> page) {
        List<ArticleSummaryResponse> content = page.getContent().stream()
                .map(this::mapToSummaryResponse)
                .collect(Collectors.toList());

        return PagedResponse.<ArticleSummaryResponse>builder()
                .content(content)
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }

    private ArticleSummaryResponse mapToSummaryResponse(Article article) {
        return ArticleSummaryResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .slug(article.getSlug())
                .category(article.getCategory())
                .authorName(article.getAuthorName())
                .readTimeMinutes(article.getReadTimeMinutes())
                .coverImageUrl(article.getCoverImageUrl())
                .summary(article.getSummary())
                .createdAt(article.getCreatedAt())
                .build();
    }

    private ArticleDetailResponse mapToDetailResponse(Article article) {
        return ArticleDetailResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .slug(article.getSlug())
                .category(article.getCategory())
                .authorName(article.getAuthorName())
                .readTimeMinutes(article.getReadTimeMinutes())
                .coverImageUrl(article.getCoverImageUrl())
                .summary(article.getSummary())
                .content(article.getContent())
                .referencesContent(article.getReferencesContent())
                .createdAt(article.getCreatedAt())
                .build();
    }
}
