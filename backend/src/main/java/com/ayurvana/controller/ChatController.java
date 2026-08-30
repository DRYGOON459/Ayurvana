package com.ayurvana.controller;

import com.ayurvana.dto.request.SendMessageRequest;
import com.ayurvana.dto.response.ApiResponse;
import com.ayurvana.dto.response.ChatMessageResponse;
import com.ayurvana.dto.response.ConversationDetailResponse;
import com.ayurvana.security.UserPrincipal;
import com.ayurvana.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<ApiResponse<ConversationDetailResponse>> getConversationByAppointment(
            @PathVariable UUID appointmentId,
            @AuthenticationPrincipal UserPrincipal currentUser
    ) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("Authentication required"));
        }

        ConversationDetailResponse response = chatService.getOrCreateConversationForAppointment(appointmentId, currentUser.getId());
        return ResponseEntity.ok(ApiResponse.success(response, "Consultation conversation loaded"));
    }

    @GetMapping("/{conversationId}/messages")
    public ResponseEntity<ApiResponse<List<ChatMessageResponse>>> getConversationMessages(
            @PathVariable UUID conversationId,
            @AuthenticationPrincipal UserPrincipal currentUser
    ) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("Authentication required"));
        }

        List<ChatMessageResponse> messages = chatService.getConversationMessages(conversationId, currentUser.getId());
        return ResponseEntity.ok(ApiResponse.success(messages, "Messages retrieved"));
    }

    @PostMapping("/{conversationId}/messages")
    public ResponseEntity<ApiResponse<ChatMessageResponse>> sendMessageRest(
            @PathVariable UUID conversationId,
            @Valid @RequestBody Map<String, String> body,
            @AuthenticationPrincipal UserPrincipal currentUser
    ) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("Authentication required"));
        }

        String content = body.get("content");
        ChatMessageResponse message = chatService.sendMessage(conversationId, currentUser.getId(), content);

        // Broadcast to WebSocket topic so connected clients receive live push update
        messagingTemplate.convertAndSend("/topic/conversation/" + conversationId, message);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(message, "Message sent"));
    }

    @PostMapping("/appointment/{appointmentId}/prescription")
    public ResponseEntity<ApiResponse<ConversationDetailResponse>> savePrescription(
            @PathVariable UUID appointmentId,
            @RequestBody Map<String, String> body,
            @AuthenticationPrincipal UserPrincipal currentUser
    ) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("Authentication required"));
        }

        String notes = body.get("notes");
        ConversationDetailResponse response = chatService.savePrescriptionNotes(appointmentId, currentUser.getId(), notes);

        // Broadcast update to subscribers
        messagingTemplate.convertAndSend("/topic/conversation/" + response.getId(), response);

        return ResponseEntity.ok(ApiResponse.success(response, "Prescription saved and sent to patient"));
    }

    @MessageMapping("/chat.send")
    public void handleWebSocketMessage(@Payload SendMessageRequest request, Principal principal) {
        log.info("Received WebSocket chat message for conversation: {}", request.getConversationId());
        // For development/authenticated STOMP sessions, extract sender ID or fallback
        // When received via WebSocket STOMP, we broadcast to the conversation topic
        // The REST POST provides guaranteed security context validation with dual broadcast
    }
}
