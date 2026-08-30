package com.ayurvana.service;

import com.ayurvana.dto.request.BookAppointmentRequest;
import com.ayurvana.dto.request.UpdateAppointmentStatusRequest;
import com.ayurvana.dto.response.AppointmentResponse;
import com.ayurvana.dto.response.DayAvailabilityResponse;
import com.ayurvana.dto.response.PagedResponse;

import java.time.LocalDate;
import java.util.UUID;

public interface AppointmentService {

    DayAvailabilityResponse getDoctorAvailabilityForDate(UUID doctorId, LocalDate date);

    AppointmentResponse bookAppointment(UUID patientId, BookAppointmentRequest request);

    PagedResponse<AppointmentResponse> getPatientAppointments(UUID patientId, int page, int size);

    PagedResponse<AppointmentResponse> getDoctorAppointments(UUID doctorUserId, int page, int size);

    AppointmentResponse updateAppointmentStatus(UUID appointmentId, UUID currentUserId, UpdateAppointmentStatusRequest request);
}
