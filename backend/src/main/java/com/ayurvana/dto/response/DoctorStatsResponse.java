package com.ayurvana.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorStatsResponse {

    private long totalAppointments;
    private long upcomingAppointments;
    private long completedAppointments;
    private BigDecimal totalEarnings;
    private BigDecimal averageRating;
    private int totalReviews;
}
