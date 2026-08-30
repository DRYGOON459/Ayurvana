package com.ayurvana.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDetailResponse {

    private UUID id;
    private UUID userId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String avatarUrl;
    private String qualifications;
    private String medicalLicenseNumber;
    private String stateMedicalCouncil;
    private Integer yearsOfExperience;
    private String primarySpecialization;
    private String languagesSpoken;
    private BigDecimal consultationFee;
    private String bio;
    private boolean verified;
    private String verificationStatus;
    private BigDecimal averageRating;
    private Integer totalReviews;
}
