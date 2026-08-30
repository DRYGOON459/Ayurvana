package com.ayurvana.repository;

import com.ayurvana.model.entity.DoctorProfile;
import com.ayurvana.model.enums.VerificationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DoctorProfileRepository extends JpaRepository<DoctorProfile, UUID> {

    Optional<DoctorProfile> findByUserId(UUID userId);

    Optional<DoctorProfile> findByMedicalLicenseNumber(String medicalLicenseNumber);

    Page<DoctorProfile> findByVerifiedTrue(Pageable pageable);

    Page<DoctorProfile> findByVerificationStatus(VerificationStatus status, Pageable pageable);

    @Query("SELECT d FROM DoctorProfile d JOIN FETCH d.user u WHERE d.verified = true AND " +
           "(:specialization IS NULL OR LOWER(d.primarySpecialization) LIKE LOWER(CONCAT('%', :specialization, '%'))) AND " +
           "(:language IS NULL OR LOWER(d.languagesSpoken) LIKE LOWER(CONCAT('%', :language, '%')))")
    Page<DoctorProfile> searchVerifiedDoctors(
            @Param("specialization") String specialization,
            @Param("language") String language,
            Pageable pageable
    );

    @Query(value = "SELECT d FROM DoctorProfile d JOIN FETCH d.user u WHERE d.verified = true AND " +
           "(:specialization IS NULL OR LOWER(d.primarySpecialization) LIKE LOWER(CONCAT('%', :specialization, '%'))) AND " +
           "(:query IS NULL OR LOWER(u.fullName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           " LOWER(d.qualifications) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           " LOWER(d.primarySpecialization) LIKE LOWER(CONCAT('%', :query, '%')))",
           countQuery = "SELECT count(d) FROM DoctorProfile d JOIN d.user u WHERE d.verified = true AND " +
           "(:specialization IS NULL OR LOWER(d.primarySpecialization) LIKE LOWER(CONCAT('%', :specialization, '%'))) AND " +
           "(:query IS NULL OR LOWER(u.fullName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           " LOWER(d.qualifications) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           " LOWER(d.primarySpecialization) LIKE LOWER(CONCAT('%', :query, '%')))")
    Page<DoctorProfile> findVerifiedWithFilters(
            @Param("specialization") String specialization,
            @Param("query") String query,
            Pageable pageable
    );
}
