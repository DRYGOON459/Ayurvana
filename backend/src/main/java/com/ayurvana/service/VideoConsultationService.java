package com.ayurvana.service;

import com.ayurvana.dto.response.VideoRoomResponse;

import java.util.UUID;

public interface VideoConsultationService {

    VideoRoomResponse getOrCreateVideoRoom(UUID appointmentId, UUID currentUserId);

    void endVideoRoom(UUID appointmentId, UUID currentUserId);
}
