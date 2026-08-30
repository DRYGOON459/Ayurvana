package com.ayurvana.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversationDetailResponse {

    private UUID id;
    private UUID appointmentId;
    private LocalDate appointmentDate;
    private LocalTime startTime;
    private LocalTime endTime;

    private UUID patientId;
    private String patientName;
    private String patientEmail;

    private UUID doctorId;
    private String doctorName;
    private String doctorQualifications;
    private String doctorSpecialization;
    private String doctorAvatarUrl;

    private String consultationNotes; // Prescription notes
    private Instant createdAt;

    @Builder.Default
    private List<ChatMessageResponse> messages = new ArrayList<>();
}
