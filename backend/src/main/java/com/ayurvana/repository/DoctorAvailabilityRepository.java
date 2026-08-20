package com.ayurvana.repository;

import com.ayurvana.model.entity.DoctorAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailability, UUID> {

    List<DoctorAvailability> findByDoctorProfileIdAndActiveTrue(UUID doctorProfileId);

    List<DoctorAvailability> findByDoctorProfileIdAndDayOfWeekAndActiveTrue(UUID doctorProfileId, Integer dayOfWeek);
}
