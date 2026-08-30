package com.ayurvana.controller;

import com.ayurvana.dto.request.DoctorOnboardingRequest;
import com.ayurvana.dto.response.ApiResponse;
import com.ayurvana.dto.response.DoctorDetailResponse;
import com.ayurvana.dto.response.DoctorSummaryResponse;
import com.ayurvana.dto.response.PagedResponse;
import com.ayurvana.security.UserPrincipal;
import com.ayurvana.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<DoctorSummaryResponse>>> getVerifiedDoctors(
            @RequestParam(required = false) String specialization,
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        PagedResponse<DoctorSummaryResponse> response = doctorService.getVerifiedDoctors(specialization, q, page, size);
        return ResponseEntity.ok(ApiResponse.success(response, "Verified doctors retrieved successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorDetailResponse>> getDoctorById(@PathVariable UUID id) {
        DoctorDetailResponse response = doctorService.getDoctorById(id);
        return ResponseEntity.ok(ApiResponse.success(response, "Doctor profile retrieved successfully"));
    }

    @PostMapping("/onboard")
    public ResponseEntity<ApiResponse<DoctorDetailResponse>> onboardDoctor(
            @Valid @RequestBody DoctorOnboardingRequest request,
            @AuthenticationPrincipal UserPrincipal currentUser
    ) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("Not authenticated"));
        }
        DoctorDetailResponse response = doctorService.onboardDoctor(currentUser.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response, "Doctor credentials submitted successfully"));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<DoctorDetailResponse>> getMyDoctorProfile(
            @AuthenticationPrincipal UserPrincipal currentUser
    ) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("Not authenticated"));
        }
        DoctorDetailResponse response = doctorService.getDoctorByUserId(currentUser.getId());
        return ResponseEntity.ok(ApiResponse.success(response, "Your doctor profile retrieved"));
    }

    @GetMapping("/me/stats")
    public ResponseEntity<ApiResponse<com.ayurvana.dto.response.DoctorStatsResponse>> getMyDoctorStats(
            @AuthenticationPrincipal UserPrincipal currentUser
    ) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("Not authenticated"));
        }
        com.ayurvana.dto.response.DoctorStatsResponse stats = doctorService.getDoctorStats(currentUser.getId());
        return ResponseEntity.ok(ApiResponse.success(stats, "Doctor stats retrieved"));
    }
}
