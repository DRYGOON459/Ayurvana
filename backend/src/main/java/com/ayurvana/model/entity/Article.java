package com.ayurvana.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "articles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Article extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "slug", unique = true, nullable = false, length = 255)
    private String slug;

    @Column(name = "category", nullable = false, length = 100) // 'Ayurveda', 'Nutrition', 'Yoga', 'Lifestyle', 'Herbs'
    private String category;

    @Column(name = "author_name", nullable = false, length = 150)
    private String authorName;

    @Column(name = "read_time_minutes", nullable = false)
    @Builder.Default
    private Integer readTimeMinutes = 5;

    @Column(name = "cover_image_url", length = 500)
    private String coverImageUrl;

    @Column(name = "summary", nullable = false, columnDefinition = "TEXT")
    private String summary;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "references_content", columnDefinition = "TEXT")
    private String referencesContent;

    @Column(name = "is_published", nullable = false)
    @Builder.Default
    private boolean published = true;
}
