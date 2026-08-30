package com.ayurvana.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorOnboardingRequest {

    @NotBlank(message = "Qualifications are required (e.g. BAMS, MD Ayurveda)")
    private String qualifications;

    @NotBlank(message = "Medical license or registration number is required")
    private String medicalLicenseNumber;

    @NotBlank(message = "State medical council is required")
    private String stateMedicalCouncil;

    @NotNull(message = "Years of experience is required")
    @Min(value = 0, message = "Experience cannot be negative")
    private Integer yearsOfExperience;

    @NotBlank(message = "Primary specialization is required (e.g. Kayachikitsa, Panchakarma)")
    private String primarySpecialization;

    @NotBlank(message = "Languages spoken are required")
    private String languagesSpoken;

    @NotNull(message = "Consultation fee is required")
    @DecimalMin(value = "0.0", message = "Consultation fee cannot be negative")
    private BigDecimal consultationFee;

    private String bio;
}
