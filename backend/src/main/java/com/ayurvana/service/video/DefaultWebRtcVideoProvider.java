package com.ayurvana.service.video;

import com.ayurvana.model.entity.Appointment;
import com.ayurvana.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HexFormat;

@Component
@Slf4j
public class DefaultWebRtcVideoProvider implements VideoCallProvider {

    @Override
    public VideoRoomToken createOrGetRoom(Appointment appointment, User participant, boolean isDoctor) {
        String appointmentIdStr = appointment.getId().toString();
        String roomName = "AyurVana-Consultation-" + appointmentIdStr.substring(0, 8);
        String roomId = "ayurvana-" + hashString(appointmentIdStr).substring(0, 16);

        // Public/Self-hosted Jitsi WebRTC room bridge fallback URL
        String roomUrl = "https://meet.jit.si/" + roomId + "#config.prejoinPageEnabled=false";

        // Ephemeral session token
        String token = "tok_webrtc_" + hashString(participant.getId() + ":" + roomId);
        Instant expiresAt = Instant.now().plus(2, ChronoUnit.HOURS);

        log.info("Generated WebRTC video room for appointment: {} with roomId: {}", appointment.getId(), roomId);

        return VideoRoomToken.builder()
                .roomId(roomId)
                .roomName(roomName)
                .token(token)
                .roomUrl(roomUrl)
                .expiresAt(expiresAt)
                .participantRole(isDoctor ? "DOCTOR" : "PATIENT")
                .participantId(participant.getId())
                .participantName(participant.getFullName())
                .build();
    }

    @Override
    public void endRoom(String roomId) {
        log.info("Closed video consultation room: {}", roomId);
    }

    private String hashString(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            return Integer.toHexString(input.hashCode());
        }
    }
}
