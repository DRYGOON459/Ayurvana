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
public class DoctorSummaryResponse {

    private UUID id;
    private String fullName;
    private String avatarUrl;
    private String qualifications;
    private String primarySpecialization;
    private Integer yearsOfExperience;
    private BigDecimal consultationFee;
    private BigDecimal averageRating;
    private Integer totalReviews;
    private String languagesSpoken;
    private boolean verified;
    private String verificationStatus;
}
