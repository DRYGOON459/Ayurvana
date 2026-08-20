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
}
