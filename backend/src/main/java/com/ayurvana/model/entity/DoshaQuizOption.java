package com.ayurvana.model.entity;

import com.ayurvana.model.enums.DoshaType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "dosha_quiz_options")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoshaQuizOption extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    @JsonIgnore
    private DoshaQuizQuestion question;

    @Column(name = "option_text", nullable = false, columnDefinition = "TEXT")
    private String optionText;

    @Enumerated(EnumType.STRING)
    @Column(name = "dosha_type", nullable = false)
    private DoshaType doshaType; // VATA, PITTA, KAPHA

    @Column(name = "score_weight")
    @Builder.Default
    private Integer scoreWeight = 1;

    @Column(name = "option_order")
    private Integer optionOrder;
}
