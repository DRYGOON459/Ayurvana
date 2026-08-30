package com.ayurvana.dto.request;

import com.ayurvana.model.enums.VerificationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerifyDoctorRequest {

    @NotNull(message = "Verification status is required")
    private VerificationStatus status;

    private String notes;
}
