package com.ayurvana.model.entity;

import com.ayurvana.model.enums.EvidenceType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "plant_references")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlantReference {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "herb_id", nullable = false)
    private Herb herb;

    @Column(name = "source_title", nullable = false, length = 300)
    private String sourceTitle;

    @Column(name = "citation", nullable = false, length = 500)
    private String citation;

    @Column(name = "url", length = 500)
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(name = "evidence_type", nullable = false, length = 100)
    private EvidenceType evidenceType;
}
