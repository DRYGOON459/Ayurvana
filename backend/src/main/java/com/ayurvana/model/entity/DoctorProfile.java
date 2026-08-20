package com.ayurvana.model.entity;

import com.ayurvana.model.enums.VerificationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "doctor_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorProfile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    @Column(name = "qualifications", nullable = false, length = 255)
    private String qualifications; // e.g. "BAMS, MD (Ayurveda)"

    @Column(name = "medical_license_number", unique = true, nullable = false, length = 100)
    private String medicalLicenseNumber;

    @Column(name = "state_medical_council", nullable = false, length = 150)
    private String stateMedicalCouncil;

    @Column(name = "years_of_experience", nullable = false)
    @Builder.Default
    private Integer yearsOfExperience = 0;

    @Column(name = "primary_specialization", nullable = false, length = 100)
    private String primarySpecialization;

    @Column(name = "languages_spoken", nullable = false, length = 255)
    private String languagesSpoken; // "English, Hindi, Sanskrit"

    @Column(name = "consultation_fee", nullable = false, precision = 10, scale = 2)
    private BigDecimal consultationFee;

    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;

    @Column(name = "is_verified", nullable = false)
    @Builder.Default
    private boolean verified = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status", nullable = false, length = 30)
    @Builder.Default
    private VerificationStatus verificationStatus = VerificationStatus.PENDING;

    @Column(name = "average_rating", precision = 3, scale = 2)
    @Builder.Default
    private BigDecimal averageRating = BigDecimal.ZERO;

    @Column(name = "total_reviews")
    @Builder.Default
    private Integer totalReviews = 0;

    @OneToMany(mappedBy = "doctorProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DoctorAvailability> availabilities = new ArrayList<>();

    @OneToMany(mappedBy = "doctorProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DoctorVerification> verificationDocuments = new ArrayList<>();
}
