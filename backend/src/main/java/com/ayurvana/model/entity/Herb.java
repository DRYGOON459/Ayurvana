package com.ayurvana.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "herbs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Herb extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "common_name", nullable = false, length = 150)
    private String commonName;

    @Column(name = "sanskrit_name", nullable = false, length = 150)
    private String sanskritName;

    @Column(name = "scientific_name", unique = true, nullable = false, length = 200)
    private String scientificName;

    @Column(name = "botanical_family", nullable = false, length = 150)
    private String botanicalFamily;

    @Column(name = "slug", unique = true, nullable = false, length = 200)
    private String slug;

    @Column(name = "rasa", length = 100) // Taste: Madhura, Tikta, Kashaya, etc.
    private String rasa;

    @Column(name = "virya", length = 50) // Potency: Ushna (Hot) / Sheeta (Cold)
    private String virya;

    @Column(name = "vipaka", length = 50) // Post-digestive effect: Madhura, Katu, Amla
    private String vipaka;

    @Column(name = "primary_dosha_effect", length = 100) // "Balances Vata and Kapha"
    private String primaryDoshaEffect;

    @Column(name = "parts_used", length = 255) // "Root, Leaves, Bark"
    private String partsUsed;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "traditional_uses", columnDefinition = "TEXT")
    private String traditionalUses;

    @Column(name = "modern_research_summary", columnDefinition = "TEXT")
    private String modernResearchSummary;

    @Column(name = "dosage_and_preparations", columnDefinition = "TEXT")
    private String dosageAndPreparations;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "is_published", nullable = false)
    @Builder.Default
    private boolean published = true;

    @OneToOne(mappedBy = "herb", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private PlantSafety safety;

    @OneToMany(mappedBy = "herb", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PlantReference> references = new ArrayList<>();
}
