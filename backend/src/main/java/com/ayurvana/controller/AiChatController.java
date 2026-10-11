package com.ayurvana.controller;

import com.ayurvana.dto.request.AiChatMessageRequest;
import com.ayurvana.dto.response.AiChatMessageResponse;
import com.ayurvana.dto.response.AiChatSuggestionResponse;
import com.ayurvana.dto.response.ApiResponse;
import com.ayurvana.service.AiChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai-chat")
@RequiredArgsConstructor
public class AiChatController {

    private final AiChatService aiChatService;

    @PostMapping("/message")
    public ResponseEntity<ApiResponse<AiChatMessageResponse>> sendMessage(
            @Valid @RequestBody AiChatMessageRequest request,
            Authentication authentication) {

        String userEmail = authentication != null ? authentication.getName() : null;
        AiChatMessageResponse response = aiChatService.processMessage(request, userEmail);
        return ResponseEntity.ok(ApiResponse.success(response, "Vaidya AI response generated"));
    }

    @GetMapping("/suggestions")
    public ResponseEntity<ApiResponse<AiChatSuggestionResponse>> getSuggestions() {
        AiChatSuggestionResponse suggestions = aiChatService.getSuggestions();
        return ResponseEntity.ok(ApiResponse.success(suggestions, "AI suggestions retrieved"));
    }
}
