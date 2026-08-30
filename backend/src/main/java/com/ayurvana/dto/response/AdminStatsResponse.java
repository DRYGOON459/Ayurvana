package com.ayurvana.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminStatsResponse {

    private long totalUsers;
    private long totalDoctors;
    private long pendingDoctorVerifications;
    private long totalAppointments;
    private long completedConsultations;
    private long totalHerbs;
}
