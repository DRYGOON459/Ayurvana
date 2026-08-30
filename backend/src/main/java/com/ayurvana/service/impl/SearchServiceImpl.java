package com.ayurvana.service.impl;

import com.ayurvana.dto.response.GlobalSearchResultResponse;
import com.ayurvana.dto.response.SearchResultItem;
import com.ayurvana.model.entity.Article;
import com.ayurvana.model.entity.AyurvedaTopic;
import com.ayurvana.model.entity.Herb;
import com.ayurvana.model.entity.YogaPose;
import com.ayurvana.repository.ArticleRepository;
import com.ayurvana.repository.AyurvedaTopicRepository;
import com.ayurvana.repository.HerbRepository;
import com.ayurvana.repository.YogaPoseRepository;
import com.ayurvana.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchServiceImpl implements SearchService {

    private final HerbRepository herbRepository;
    private final YogaPoseRepository yogaPoseRepository;
    private final AyurvedaTopicRepository topicRepository;
    private final ArticleRepository articleRepository;

    @Override
    public GlobalSearchResultResponse globalSearch(String query, int limitPerCategory) {
        if (query == null || query.trim().isEmpty()) {
            return GlobalSearchResultResponse.builder()
                    .query("")
                    .totalMatches(0)
                    .herbs(new ArrayList<>())
                    .yoga(new ArrayList<>())
                    .ayurveda(new ArrayList<>())
                    .articles(new ArrayList<>())
                    .build();
        }

        String cleanQuery = query.trim();
        PageRequest pageRequest = PageRequest.of(0, limitPerCategory);

        // 1. Search Herbs
        List<SearchResultItem> herbResults = herbRepository.searchHerbs(cleanQuery, pageRequest)
                .getContent().stream()
                .map(this::mapHerbToSearchResult)
                .collect(Collectors.toList());

        // 2. Search Yoga Poses
        List<SearchResultItem> yogaResults = yogaPoseRepository.searchPoses(cleanQuery, pageRequest)
                .getContent().stream()
                .map(this::mapYogaToSearchResult)
                .collect(Collectors.toList());

        // 3. Search Ayurveda Topics
        List<SearchResultItem> topicResults = topicRepository.searchTopics(cleanQuery, pageRequest)
                .getContent().stream()
                .map(this::mapTopicToSearchResult)
                .collect(Collectors.toList());

        // 4. Search Articles
        List<SearchResultItem> articleResults = articleRepository.searchArticles(cleanQuery, pageRequest)
                .getContent().stream()
                .map(this::mapArticleToSearchResult)
                .collect(Collectors.toList());

        int total = herbResults.size() + yogaResults.size() + topicResults.size() + articleResults.size();

        return GlobalSearchResultResponse.builder()
                .query(cleanQuery)
                .totalMatches(total)
                .herbs(herbResults)
                .yoga(yogaResults)
                .ayurveda(topicResults)
                .articles(articleResults)
                .build();
    }

    private SearchResultItem mapHerbToSearchResult(Herb herb) {
        return SearchResultItem.builder()
                .id(herb.getId())
                .type("HERB")
                .title(herb.getCommonName())
                .subtitle(herb.getSanskritName() + " (" + herb.getScientificName() + ")")
                .slug(herb.getSlug())
                .category(herb.getBotanicalFamily())
                .snippet(herb.getPrimaryDoshaEffect())
                .imageUrl(herb.getImageUrl())
                .build();
    }

    private SearchResultItem mapYogaToSearchResult(YogaPose pose) {
        return SearchResultItem.builder()
                .id(pose.getId())
                .type("YOGA")
                .title(pose.getEnglishName())
                .subtitle(pose.getSanskritName())
                .slug(pose.getSlug())
                .category(pose.getCategory())
                .snippet(pose.getBenefits())
                .imageUrl(pose.getImageUrl())
                .build();
    }

    private SearchResultItem mapTopicToSearchResult(AyurvedaTopic topic) {
        return SearchResultItem.builder()
                .id(topic.getId())
                .type("AYURVEDA")
                .title(topic.getTitle())
                .subtitle(topic.getSanskritTitle())
                .slug(topic.getSlug())
                .category(topic.getCategory())
                .snippet(topic.getSummary())
                .imageUrl(topic.getImageUrl())
                .build();
    }

    private SearchResultItem mapArticleToSearchResult(Article article) {
        return SearchResultItem.builder()
                .id(article.getId())
                .type("ARTICLE")
                .title(article.getTitle())
                .subtitle("By " + article.getAuthorName())
                .slug(article.getSlug())
                .category(article.getCategory())
                .snippet(article.getSummary())
                .imageUrl(article.getCoverImageUrl())
                .build();
    }
}
