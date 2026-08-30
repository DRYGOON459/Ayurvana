package com.ayurvana.controller;

import com.ayurvana.dto.response.ApiResponse;
import com.ayurvana.dto.response.VideoRoomResponse;
import com.ayurvana.security.UserPrincipal;
import com.ayurvana.service.VideoConsultationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/video")
@RequiredArgsConstructor
public class VideoConsultationController {

    private final VideoConsultationService videoConsultationService;

    @GetMapping("/appointment/{appointmentId}/token")
    public ResponseEntity<ApiResponse<VideoRoomResponse>> getVideoRoomToken(
            @PathVariable UUID appointmentId,
            @AuthenticationPrincipal UserPrincipal currentUser
    ) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("Authentication required"));
        }

        VideoRoomResponse response = videoConsultationService.getOrCreateVideoRoom(appointmentId, currentUser.getId());
        return ResponseEntity.ok(ApiResponse.success(response, "Video room access token generated"));
    }

    @PostMapping("/appointment/{appointmentId}/end")
    public ResponseEntity<ApiResponse<String>> endVideoCall(
            @PathVariable UUID appointmentId,
            @AuthenticationPrincipal UserPrincipal currentUser
    ) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("Authentication required"));
        }

        videoConsultationService.endVideoRoom(appointmentId, currentUser.getId());
        return ResponseEntity.ok(ApiResponse.success("Video call ended", "Session concluded successfully"));
    }
}
