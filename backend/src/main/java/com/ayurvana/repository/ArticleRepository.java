package com.ayurvana.repository;

import com.ayurvana.model.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ArticleRepository extends JpaRepository<Article, UUID> {

    Optional<Article> findBySlug(String slug);

    Page<Article> findByPublishedTrue(Pageable pageable);

    Page<Article> findByCategoryIgnoreCaseAndPublishedTrue(String category, Pageable pageable);

    @org.springframework.data.jpa.repository.Query("SELECT a FROM Article a WHERE a.published = true AND (" +
           "LOWER(a.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(a.category) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(a.summary) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(a.authorName) LIKE LOWER(CONCAT('%', :query, '%')))")
    Page<Article> searchArticles(@org.springframework.data.repository.query.Param("query") String query, Pageable pageable);
}
