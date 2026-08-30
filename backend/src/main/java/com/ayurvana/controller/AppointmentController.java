package com.ayurvana.controller;

import com.ayurvana.dto.request.BookAppointmentRequest;
import com.ayurvana.dto.request.UpdateAppointmentStatusRequest;
import com.ayurvana.dto.response.ApiResponse;
import com.ayurvana.dto.response.AppointmentResponse;
import com.ayurvana.dto.response.DayAvailabilityResponse;
import com.ayurvana.dto.response.PagedResponse;
import com.ayurvana.security.UserPrincipal;
import com.ayurvana.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping("/doctor/{doctorId}/availability")
    public ResponseEntity<ApiResponse<DayAvailabilityResponse>> getDoctorAvailability(
            @PathVariable UUID doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        DayAvailabilityResponse response = appointmentService.getDoctorAvailabilityForDate(doctorId, date);
        return ResponseEntity.ok(ApiResponse.success(response, "Doctor availability retrieved"));
    }

    @PostMapping("/book")
    public ResponseEntity<ApiResponse<AppointmentResponse>> bookAppointment(
            @Valid @RequestBody BookAppointmentRequest request,
            @AuthenticationPrincipal UserPrincipal currentUser
    ) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("Authentication required to book consultations"));
        }

        AppointmentResponse response = appointmentService.bookAppointment(currentUser.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response, "Appointment booked successfully"));
    }

    @GetMapping("/my-appointments")
    public ResponseEntity<ApiResponse<PagedResponse<AppointmentResponse>>> getMyAppointments(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("Authentication required"));
        }

        PagedResponse<AppointmentResponse> response = appointmentService.getPatientAppointments(currentUser.getId(), page, size);
        return ResponseEntity.ok(ApiResponse.success(response, "Your appointments retrieved"));
    }

    @GetMapping("/doctor-schedule")
    public ResponseEntity<ApiResponse<PagedResponse<AppointmentResponse>>> getDoctorSchedule(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("Authentication required"));
        }

        PagedResponse<AppointmentResponse> response = appointmentService.getDoctorAppointments(currentUser.getId(), page, size);
        return ResponseEntity.ok(ApiResponse.success(response, "Doctor consultation schedule retrieved"));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<AppointmentResponse>> updateAppointmentStatus(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateAppointmentStatusRequest request,
            @AuthenticationPrincipal UserPrincipal currentUser
    ) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("Authentication required"));
        }

        AppointmentResponse response = appointmentService.updateAppointmentStatus(id, currentUser.getId(), request);
        return ResponseEntity.ok(ApiResponse.success(response, "Appointment status updated"));
    }
}
