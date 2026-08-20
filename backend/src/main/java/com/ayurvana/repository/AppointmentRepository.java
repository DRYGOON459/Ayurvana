package com.ayurvana.repository;

import com.ayurvana.model.entity.Appointment;
import com.ayurvana.model.enums.AppointmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    Page<Appointment> findByPatientId(UUID patientId, Pageable pageable);

    Page<Appointment> findByDoctorId(UUID doctorId, Pageable pageable);

    List<Appointment> findByDoctorIdAndAppointmentDate(UUID doctorId, LocalDate appointmentDate);

    boolean existsByDoctorIdAndAppointmentDateAndStartTimeAndStatusNot(
            UUID doctorId,
            LocalDate appointmentDate,
            LocalTime startTime,
            AppointmentStatus excludedStatus
    );

    @Query("SELECT a FROM Appointment a JOIN FETCH a.patient p JOIN FETCH a.doctor d JOIN FETCH d.user du WHERE a.id = :id")
    Optional<Appointment> findByIdWithDetails(@Param("id") UUID id);
}
