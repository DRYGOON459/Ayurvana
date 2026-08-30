package com.ayurvana.service;

import com.ayurvana.dto.request.DoctorOnboardingRequest;
import com.ayurvana.dto.response.DoctorDetailResponse;
import com.ayurvana.dto.response.DoctorSummaryResponse;
import com.ayurvana.dto.response.PagedResponse;

import java.util.UUID;

public interface DoctorService {

    PagedResponse<DoctorSummaryResponse> getVerifiedDoctors(String specialization, String query, int page, int size);

    DoctorDetailResponse getDoctorById(UUID doctorId);

    DoctorDetailResponse getDoctorByUserId(UUID userId);

    DoctorDetailResponse onboardDoctor(UUID userId, DoctorOnboardingRequest request);

    com.ayurvana.dto.response.DoctorStatsResponse getDoctorStats(UUID userId);
}
