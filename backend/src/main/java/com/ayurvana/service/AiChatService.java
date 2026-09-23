package com.ayurvana.service;

import com.ayurvana.dto.request.AiChatMessageRequest;
import com.ayurvana.dto.response.AiChatMessageResponse;
import com.ayurvana.dto.response.AiChatSuggestionResponse;

public interface AiChatService {
    AiChatMessageResponse processMessage(AiChatMessageRequest request, String userEmail);
    AiChatSuggestionResponse getSuggestions();
}
