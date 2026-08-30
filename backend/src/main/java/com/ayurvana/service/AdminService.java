package com.ayurvana.service;

import com.ayurvana.dto.request.VerifyDoctorRequest;
import com.ayurvana.dto.response.AdminStatsResponse;
import com.ayurvana.dto.response.DoctorDetailResponse;
import com.ayurvana.dto.response.PagedResponse;

import java.util.UUID;

public interface AdminService {

    PagedResponse<DoctorDetailResponse> getPendingDoctors(int page, int size);

    DoctorDetailResponse verifyDoctor(UUID doctorId, VerifyDoctorRequest request);

    AdminStatsResponse getPlatformStats();
}
