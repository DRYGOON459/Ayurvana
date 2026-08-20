package com.ayurvana.model.entity;

import com.ayurvana.model.enums.DoshaType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "ayurveda_topics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AyurvedaTopic extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "sanskrit_title", length = 200)
    private String sanskritTitle;

    @Column(name = "slug", unique = true, nullable = false, length = 200)
    private String slug;

    @Column(name = "category", nullable = false, length = 100) // 'Fundamentals', 'Dosha', 'Dinacharya', 'Ritucharya', 'Agni & Ama'
    private String category;

    @Enumerated(EnumType.STRING)
    @Column(name = "dosha_association", length = 30)
    private DoshaType doshaAssociation;

    @Column(name = "summary", nullable = false, columnDefinition = "TEXT")
    private String summary;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "traditional_references", columnDefinition = "TEXT")
    private String traditionalReferences;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "is_published", nullable = false)
    @Builder.Default
    private boolean published = true;
}
