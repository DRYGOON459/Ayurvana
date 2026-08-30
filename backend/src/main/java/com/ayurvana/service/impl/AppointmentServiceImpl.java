package com.ayurvana.service.impl;

import com.ayurvana.dto.request.BookAppointmentRequest;
import com.ayurvana.dto.request.UpdateAppointmentStatusRequest;
import com.ayurvana.dto.response.AppointmentResponse;
import com.ayurvana.dto.response.DayAvailabilityResponse;
import com.ayurvana.dto.response.PagedResponse;
import com.ayurvana.dto.response.SlotResponse;
import com.ayurvana.exception.ResourceNotFoundException;
import com.ayurvana.model.entity.Appointment;
import com.ayurvana.model.entity.DoctorAvailability;
import com.ayurvana.model.entity.DoctorProfile;
import com.ayurvana.model.entity.User;
import com.ayurvana.model.enums.AppointmentStatus;
import com.ayurvana.repository.AppointmentRepository;
import com.ayurvana.repository.DoctorAvailabilityRepository;
import com.ayurvana.repository.DoctorProfileRepository;
import com.ayurvana.repository.UserRepository;
import com.ayurvana.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorProfileRepository doctorProfileRepository;
    private final DoctorAvailabilityRepository doctorAvailabilityRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public DayAvailabilityResponse getDoctorAvailabilityForDate(UUID doctorId, LocalDate date) {
        DoctorProfile doctor = doctorProfileRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("DoctorProfile", "id", doctorId));

        int dayOfWeek = date.getDayOfWeek().getValue(); // 1 = Monday, 7 = Sunday
        List<DoctorAvailability> availabilities = doctorAvailabilityRepository
                .findByDoctorProfileIdAndDayOfWeekAndActiveTrue(doctorId, dayOfWeek);

        // Fetch existing appointments on that date
        List<Appointment> existingAppointments = appointmentRepository
                .findByDoctorIdAndAppointmentDate(doctorId, date);

        Set<LocalTime> bookedStartTimes = existingAppointments.stream()
                .filter(a -> a.getStatus() != AppointmentStatus.CANCELLED)
                .map(Appointment::getStartTime)
                .collect(Collectors.toSet());

        List<SlotResponse> slots = new ArrayList<>();
        LocalTime nowTime = LocalTime.now();
        boolean isToday = date.equals(LocalDate.now());

        if (!availabilities.isEmpty()) {
            for (DoctorAvailability avail : availabilities) {
                int duration = avail.getSlotDurationMinutes() > 0 ? avail.getSlotDurationMinutes() : 30;
                LocalTime current = avail.getStartTime();
                while (current.plusMinutes(duration).compareTo(avail.getEndTime()) <= 0) {
                    LocalTime end = current.plusMinutes(duration);
                    boolean isPast = isToday && current.isBefore(nowTime);
                    boolean isBooked = bookedStartTimes.contains(current);

                    slots.add(SlotResponse.builder()
                            .startTime(current)
                            .endTime(end)
                            .available(!isPast && !isBooked)
                            .build());
                    current = end;
                }
            }
        } else {
            // Default clinical consultation schedule: 09:00 - 13:00 and 14:00 - 17:00 (30-min intervals)
            List<LocalTime[]> intervals = List.of(
                    new LocalTime[]{LocalTime.of(9, 0), LocalTime.of(13, 0)},
                    new LocalTime[]{LocalTime.of(14, 0), LocalTime.of(17, 0)}
            );

            for (LocalTime[] interval : intervals) {
                LocalTime current = interval[0];
                LocalTime endLimit = interval[1];
                while (current.plusMinutes(30).compareTo(endLimit) <= 0) {
                    LocalTime end = current.plusMinutes(30);
                    boolean isPast = isToday && current.isBefore(nowTime);
                    boolean isBooked = bookedStartTimes.contains(current);

                    slots.add(SlotResponse.builder()
                            .startTime(current)
                            .endTime(end)
                            .available(!isPast && !isBooked)
                            .build());
                    current = end;
                }
            }
        }

        return DayAvailabilityResponse.builder()
                .date(date)
                .dayOfWeek(dayOfWeek)
                .slots(slots)
                .build();
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public AppointmentResponse bookAppointment(UUID patientId, BookAppointmentRequest request) {
        log.info("Attempting to book appointment: doctor={}, patient={}, date={}, time={}",
                request.getDoctorId(), patientId, request.getAppointmentDate(), request.getStartTime());

        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", patientId));

        DoctorProfile doctor = doctorProfileRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("DoctorProfile", "id", request.getDoctorId()));

        if (patient.getId().equals(doctor.getUser().getId())) {
            throw new IllegalArgumentException("Doctors cannot book consultations with themselves.");
        }

        // Concurrency Check 1: Spring Data JPA Existence Check
        boolean isSlotTaken = appointmentRepository.existsByDoctorIdAndAppointmentDateAndStartTimeAndStatusNot(
                doctor.getId(),
                request.getAppointmentDate(),
                request.getStartTime(),
                AppointmentStatus.CANCELLED
        );

        if (isSlotTaken) {
            throw new IllegalStateException("This consultation slot has just been reserved by another patient. Please select another slot.");
        }

        try {
            Appointment appointment = Appointment.builder()
                    .patient(patient)
                    .doctor(doctor)
                    .appointmentDate(request.getAppointmentDate())
                    .startTime(request.getStartTime())
                    .endTime(request.getEndTime())
                    .status(AppointmentStatus.CONFIRMED) // Instant confirmation for platform simplicity
                    .consultationNotes(request.getPatientNotes())
                    .build();

            appointment = appointmentRepository.save(appointment);
            log.info("Successfully reserved appointment: id={}", appointment.getId());
            return mapToResponse(appointment);
        } catch (DataIntegrityViolationException ex) {
            // Concurrency Check 2: Database Unique Constraint collision catch
            log.warn("Database unique constraint triggered on double-booking collision: {}", ex.getMessage());
            throw new IllegalStateException("This consultation slot is already reserved. Please select another slot.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<AppointmentResponse> getPatientAppointments(UUID patientId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("appointmentDate").descending().and(Sort.by("startTime").descending()));
        Page<Appointment> appointmentPage = appointmentRepository.findByPatientId(patientId, pageable);

        List<AppointmentResponse> content = appointmentPage.getContent().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return PagedResponse.<AppointmentResponse>builder()
                .content(content)
                .page(appointmentPage.getNumber())
                .size(appointmentPage.getSize())
                .totalElements(appointmentPage.getTotalElements())
                .totalPages(appointmentPage.getTotalPages())
                .last(appointmentPage.isLast())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<AppointmentResponse> getDoctorAppointments(UUID doctorUserId, int page, int size) {
        DoctorProfile doctor = doctorProfileRepository.findByUserId(doctorUserId)
                .orElseThrow(() -> new ResourceNotFoundException("DoctorProfile", "userId", doctorUserId));

        Pageable pageable = PageRequest.of(page, size, Sort.by("appointmentDate").descending().and(Sort.by("startTime").descending()));
        Page<Appointment> appointmentPage = appointmentRepository.findByDoctorId(doctor.getId(), pageable);

        List<AppointmentResponse> content = appointmentPage.getContent().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return PagedResponse.<AppointmentResponse>builder()
                .content(content)
                .page(appointmentPage.getNumber())
                .size(appointmentPage.getSize())
                .totalElements(appointmentPage.getTotalElements())
                .totalPages(appointmentPage.getTotalPages())
                .last(appointmentPage.isLast())
                .build();
    }

    @Override
    @Transactional
    public AppointmentResponse updateAppointmentStatus(UUID appointmentId, UUID currentUserId, UpdateAppointmentStatusRequest request) {
        Appointment appointment = appointmentRepository.findByIdWithDetails(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", appointmentId));

        boolean isPatient = appointment.getPatient().getId().equals(currentUserId);
        boolean isDoctor = appointment.getDoctor().getUser().getId().equals(currentUserId);

        if (!isPatient && !isDoctor) {
            throw new IllegalArgumentException("You are not authorized to update this appointment.");
        }

        // Patients can only cancel pending/confirmed appointments
        if (isPatient && !isDoctor && request.getStatus() != AppointmentStatus.CANCELLED) {
            throw new IllegalArgumentException("Patients are only permitted to cancel scheduled appointments.");
        }

        appointment.setStatus(request.getStatus());
        if (request.getStatus() == AppointmentStatus.CANCELLED && request.getReason() != null) {
            appointment.setCancellationReason(request.getReason());
        }

        appointment = appointmentRepository.save(appointment);
        log.info("Updated appointment {} status to {}", appointmentId, request.getStatus());

        return mapToResponse(appointment);
    }

    private AppointmentResponse mapToResponse(Appointment a) {
        String docName = a.getDoctor().getUser() != null ? a.getDoctor().getUser().getFullName() : "Ayurvedic Doctor";
        String docAvatar = a.getDoctor().getUser() != null ? a.getDoctor().getUser().getAvatarUrl() : null;

        return AppointmentResponse.builder()
                .id(a.getId())
                .doctorId(a.getDoctor().getId())
                .doctorName(docName)
                .doctorQualifications(a.getDoctor().getQualifications())
                .doctorSpecialization(a.getDoctor().getPrimarySpecialization())
                .doctorAvatarUrl(docAvatar)
                .patientId(a.getPatient().getId())
                .patientName(a.getPatient().getFullName())
                .patientEmail(a.getPatient().getEmail())
                .appointmentDate(a.getAppointmentDate())
                .startTime(a.getStartTime())
                .endTime(a.getEndTime())
                .status(a.getStatus())
                .consultationFee(a.getDoctor().getConsultationFee())
                .patientNotes(a.getConsultationNotes())
                .cancellationReason(a.getCancellationReason())
                .createdAt(a.getCreatedAt())
                .build();
    }
}
