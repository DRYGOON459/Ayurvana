package com.ayurvana.service;

import com.ayurvana.dto.response.ChatMessageResponse;
import com.ayurvana.dto.response.ConversationDetailResponse;

import java.util.List;
import java.util.UUID;

public interface ChatService {

    ConversationDetailResponse getOrCreateConversationForAppointment(UUID appointmentId, UUID currentUserId);

    ChatMessageResponse sendMessage(UUID conversationId, UUID senderId, String content);

    List<ChatMessageResponse> getConversationMessages(UUID conversationId, UUID currentUserId);

    ConversationDetailResponse savePrescriptionNotes(UUID appointmentId, UUID doctorUserId, String notes);
}
