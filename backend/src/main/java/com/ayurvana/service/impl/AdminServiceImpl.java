package com.ayurvana.service.impl;

import com.ayurvana.dto.request.VerifyDoctorRequest;
import com.ayurvana.dto.response.AdminStatsResponse;
import com.ayurvana.dto.response.DoctorDetailResponse;
import com.ayurvana.dto.response.PagedResponse;
import com.ayurvana.exception.ResourceNotFoundException;
import com.ayurvana.model.entity.Appointment;
import com.ayurvana.model.entity.DoctorProfile;
import com.ayurvana.model.enums.AppointmentStatus;
import com.ayurvana.model.enums.VerificationStatus;
import com.ayurvana.repository.AppointmentRepository;
import com.ayurvana.repository.DoctorProfileRepository;
import com.ayurvana.repository.HerbRepository;
import com.ayurvana.repository.UserRepository;
import com.ayurvana.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {

    private final DoctorProfileRepository doctorProfileRepository;
    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;
    private final HerbRepository herbRepository;

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<DoctorDetailResponse> getPendingDoctors(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<DoctorProfile> pendingPage = doctorProfileRepository.findByVerificationStatus(VerificationStatus.PENDING, pageable);

        List<DoctorDetailResponse> content = pendingPage.getContent().stream()
                .map(this::mapToDetail)
                .collect(Collectors.toList());

        return PagedResponse.<DoctorDetailResponse>builder()
                .content(content)
                .page(pendingPage.getNumber())
                .size(pendingPage.getSize())
                .totalElements(pendingPage.getTotalElements())
                .totalPages(pendingPage.getTotalPages())
                .last(pendingPage.isLast())
                .build();
    }

    @Override
    @Transactional
    public DoctorDetailResponse verifyDoctor(UUID doctorId, VerifyDoctorRequest request) {
        DoctorProfile doctor = doctorProfileRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("DoctorProfile", "id", doctorId));

        doctor.setVerificationStatus(request.getStatus());
        doctor.setVerified(request.getStatus() == VerificationStatus.APPROVED);

        doctor = doctorProfileRepository.save(doctor);
        log.info("Admin updated doctor {} verification status to {}", doctor.getId(), request.getStatus());

        return mapToDetail(doctor);
    }

    @Override
    @Transactional(readOnly = true)
    public AdminStatsResponse getPlatformStats() {
        long users = userRepository.count();
        long doctors = doctorProfileRepository.count();
        long pendingVerifications = doctorProfileRepository.findByVerificationStatus(VerificationStatus.PENDING, Pageable.unpaged()).getTotalElements();
        long appointments = appointmentRepository.count();
        List<Appointment> allAppointments = appointmentRepository.findAll();
        long completed = allAppointments.stream().filter(a -> a.getStatus() == AppointmentStatus.COMPLETED).count();
        long herbs = herbRepository.count();

        return AdminStatsResponse.builder()
                .totalUsers(users)
                .totalDoctors(doctors)
                .pendingDoctorVerifications(pendingVerifications)
                .totalAppointments(appointments)
                .completedConsultations(completed)
                .totalHerbs(herbs)
                .build();
    }

    private DoctorDetailResponse mapToDetail(DoctorProfile d) {
        String name = d.getUser() != null ? d.getUser().getFullName() : "Ayurvedic Doctor";
        String email = d.getUser() != null ? d.getUser().getEmail() : "";
        String phone = d.getUser() != null ? d.getUser().getPhoneNumber() : "";
        String avatar = d.getUser() != null ? d.getUser().getAvatarUrl() : null;
        UUID uId = d.getUser() != null ? d.getUser().getId() : null;

        return DoctorDetailResponse.builder()
                .id(d.getId())
                .userId(uId)
                .fullName(name)
                .email(email)
                .phoneNumber(phone)
                .avatarUrl(avatar)
                .qualifications(d.getQualifications())
                .medicalLicenseNumber(d.getMedicalLicenseNumber())
                .stateMedicalCouncil(d.getStateMedicalCouncil())
                .yearsOfExperience(d.getYearsOfExperience())
                .primarySpecialization(d.getPrimarySpecialization())
                .languagesSpoken(d.getLanguagesSpoken())
                .consultationFee(d.getConsultationFee())
                .bio(d.getBio())
                .verified(d.isVerified())
                .verificationStatus(d.getVerificationStatus().name())
                .averageRating(d.getAverageRating())
                .totalReviews(d.getTotalReviews())
                .build();
    }
}
