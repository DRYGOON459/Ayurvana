package com.ayurvana.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiChatSuggestionResponse {

    private String welcomeMessage;
    private List<SuggestionCategory> categories;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SuggestionCategory {
        private String category;
        private String icon;
        private List<String> prompts;
    }
}
