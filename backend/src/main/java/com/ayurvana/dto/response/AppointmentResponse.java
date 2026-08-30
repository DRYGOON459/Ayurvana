package com.ayurvana.dto.response;

import com.ayurvana.model.enums.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponse {

    private UUID id;
    private UUID doctorId;
    private String doctorName;
    private String doctorQualifications;
    private String doctorSpecialization;
    private String doctorAvatarUrl;

    private UUID patientId;
    private String patientName;
    private String patientEmail;

    private LocalDate appointmentDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private AppointmentStatus status;
    private BigDecimal consultationFee;
    private String patientNotes;
    private String cancellationReason;
    private Instant createdAt;
}
