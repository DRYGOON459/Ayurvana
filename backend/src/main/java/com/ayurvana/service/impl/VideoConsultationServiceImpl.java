package com.ayurvana.service.impl;

import com.ayurvana.dto.response.VideoRoomResponse;
import com.ayurvana.exception.ResourceNotFoundException;
import com.ayurvana.model.entity.Appointment;
import com.ayurvana.model.entity.User;
import com.ayurvana.model.enums.AppointmentStatus;
import com.ayurvana.repository.AppointmentRepository;
import com.ayurvana.repository.UserRepository;
import com.ayurvana.service.VideoConsultationService;
import com.ayurvana.service.video.VideoCallProvider;
import com.ayurvana.service.video.VideoRoomToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class VideoConsultationServiceImpl implements VideoConsultationService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final VideoCallProvider videoCallProvider;

    @Override
    @Transactional(readOnly = true)
    public VideoRoomResponse getOrCreateVideoRoom(UUID appointmentId, UUID currentUserId) {
        Appointment appointment = appointmentRepository.findByIdWithDetails(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", appointmentId));

        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", currentUserId));

        boolean isPatient = appointment.getPatient().getId().equals(currentUserId);
        boolean isDoctor = appointment.getDoctor().getUser().getId().equals(currentUserId);

        if (!isPatient && !isDoctor) {
            throw new IllegalArgumentException("You are not authorized to join this video consultation.");
        }

        VideoRoomToken roomToken = videoCallProvider.createOrGetRoom(appointment, currentUser, isDoctor);

        return VideoRoomResponse.builder()
                .appointmentId(appointmentId)
                .roomId(roomToken.getRoomId())
                .roomName(roomToken.getRoomName())
                .token(roomToken.getToken())
                .roomUrl(roomToken.getRoomUrl())
                .expiresAt(roomToken.getExpiresAt())
                .participantRole(roomToken.getParticipantRole())
                .participantName(roomToken.getParticipantName())
                .isDoctor(isDoctor)
                .build();
    }

    @Override
    @Transactional
    public void endVideoRoom(UUID appointmentId, UUID currentUserId) {
        Appointment appointment = appointmentRepository.findByIdWithDetails(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", appointmentId));

        boolean isDoctor = appointment.getDoctor().getUser().getId().equals(currentUserId);

        if (isDoctor) {
            // When doctor concludes consultation, mark appointment completed
            appointment.setStatus(AppointmentStatus.COMPLETED);
            appointmentRepository.save(appointment);
            log.info("Marked appointment {} as COMPLETED after video consultation concluded", appointmentId);
        }
    }
}
