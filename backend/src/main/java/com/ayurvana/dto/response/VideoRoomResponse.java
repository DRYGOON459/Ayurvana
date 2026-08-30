package com.ayurvana.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoRoomResponse {

    private UUID appointmentId;
    private String roomId;
    private String roomName;
    private String token;
    private String roomUrl;
    private Instant expiresAt;
    private String participantRole;
    private String participantName;
    private boolean isDoctor;
}
