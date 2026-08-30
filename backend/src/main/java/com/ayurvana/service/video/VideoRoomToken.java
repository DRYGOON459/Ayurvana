package com.ayurvana.service.video;

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
public class VideoRoomToken {

    private String roomId;
    private String roomName;
    private String token;
    private String roomUrl;
    private Instant expiresAt;
    private String participantRole; // "DOCTOR", "PATIENT"
    private UUID participantId;
    private String participantName;
}
