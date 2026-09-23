package com.ayurvana.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiChatMessageRequest {

    @NotBlank(message = "Message content cannot be blank")
    private String message;

    private String userDosha; // e.g. "VATA", "PITTA", "KAPHA", "TRIDOSHIC"
    
    private List<HistoryItem> history;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HistoryItem {
        private String role; // "user" or "assistant"
        private String content;
    }
}
