package com.ayurvana.controller;

import com.ayurvana.dto.request.VerifyDoctorRequest;
import com.ayurvana.dto.response.AdminStatsResponse;
import com.ayurvana.dto.response.ApiResponse;
import com.ayurvana.dto.response.DoctorDetailResponse;
import com.ayurvana.dto.response.PagedResponse;
import com.ayurvana.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<AdminStatsResponse>> getPlatformStats() {
        AdminStatsResponse stats = adminService.getPlatformStats();
        return ResponseEntity.ok(ApiResponse.success(stats, "Platform statistics retrieved"));
    }

    @GetMapping("/doctors/pending")
    public ResponseEntity<ApiResponse<PagedResponse<DoctorDetailResponse>>> getPendingDoctors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PagedResponse<DoctorDetailResponse> response = adminService.getPendingDoctors(page, size);
        return ResponseEntity.ok(ApiResponse.success(response, "Pending doctor verifications retrieved"));
    }

    @PostMapping("/doctors/{doctorId}/verify")
    public ResponseEntity<ApiResponse<DoctorDetailResponse>> verifyDoctor(
            @PathVariable UUID doctorId,
            @Valid @RequestBody VerifyDoctorRequest request
    ) {
        DoctorDetailResponse response = adminService.verifyDoctor(doctorId, request);
        return ResponseEntity.ok(ApiResponse.success(response, "Doctor verification decision recorded"));
    }
}
