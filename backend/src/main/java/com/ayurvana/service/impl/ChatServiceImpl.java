package com.ayurvana.service.impl;

import com.ayurvana.dto.response.ChatMessageResponse;
import com.ayurvana.dto.response.ConversationDetailResponse;
import com.ayurvana.exception.ResourceNotFoundException;
import com.ayurvana.model.entity.Appointment;
import com.ayurvana.model.entity.Conversation;
import com.ayurvana.model.entity.Message;
import com.ayurvana.model.entity.User;
import com.ayurvana.repository.AppointmentRepository;
import com.ayurvana.repository.ConversationRepository;
import com.ayurvana.repository.MessageRepository;
import com.ayurvana.repository.UserRepository;
import com.ayurvana.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ConversationDetailResponse getOrCreateConversationForAppointment(UUID appointmentId, UUID currentUserId) {
        Appointment appointment = appointmentRepository.findByIdWithDetails(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", appointmentId));

        boolean isPatient = appointment.getPatient().getId().equals(currentUserId);
        boolean isDoctor = appointment.getDoctor().getUser().getId().equals(currentUserId);

        if (!isPatient && !isDoctor) {
            throw new IllegalArgumentException("You are not authorized to access this consultation chat.");
        }

        Conversation conversation = conversationRepository.findByAppointmentId(appointmentId)
                .orElseGet(() -> {
                    log.info("Creating new clinical conversation for appointment: {}", appointmentId);
                    Conversation newConv = Conversation.builder()
                            .appointment(appointment)
                            .patient(appointment.getPatient())
                            .doctor(appointment.getDoctor().getUser())
                            .build();
                    return conversationRepository.save(newConv);
                });

        return mapToDetail(conversation);
    }

    @Override
    @Transactional
    public ChatMessageResponse sendMessage(UUID conversationId, UUID senderId, String content) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new ResourceNotFoundException("Conversation", "id", conversationId));

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", senderId));

        boolean isPatient = conversation.getPatient().getId().equals(senderId);
        boolean isDoctor = conversation.getDoctor().getId().equals(senderId);

        if (!isPatient && !isDoctor) {
            throw new IllegalArgumentException("You are not a participant in this conversation.");
        }

        Message message = Message.builder()
                .conversation(conversation)
                .sender(sender)
                .content(content.trim())
                .read(false)
                .sentAt(Instant.now())
                .build();

        message = messageRepository.save(message);
        log.info("Saved consultation message from {} in conversation {}", sender.getEmail(), conversationId);

        return mapToMessageResponse(message, conversation);
    }

    @Override
    @Transactional
    public List<ChatMessageResponse> getConversationMessages(UUID conversationId, UUID currentUserId) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new ResourceNotFoundException("Conversation", "id", conversationId));

        boolean isPatient = conversation.getPatient().getId().equals(currentUserId);
        boolean isDoctor = conversation.getDoctor().getId().equals(currentUserId);

        if (!isPatient && !isDoctor) {
            throw new IllegalArgumentException("You are not authorized to view these messages.");
        }

        List<Message> messages = messageRepository.findByConversationIdOrderBySentAtAsc(conversationId);

        // Mark incoming messages as read
        for (Message m : messages) {
            if (!m.getSender().getId().equals(currentUserId) && !m.isRead()) {
                m.setRead(true);
                messageRepository.save(m);
            }
        }

        return messages.stream()
                .map(m -> mapToMessageResponse(m, conversation))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ConversationDetailResponse savePrescriptionNotes(UUID appointmentId, UUID doctorUserId, String notes) {
        Appointment appointment = appointmentRepository.findByIdWithDetails(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", appointmentId));

        if (!appointment.getDoctor().getUser().getId().equals(doctorUserId)) {
            throw new IllegalArgumentException("Only the attending Ayurvedic physician can formulate prescriptions.");
        }

        appointment.setConsultationNotes(notes);
        appointmentRepository.save(appointment);

        Conversation conversation = conversationRepository.findByAppointmentId(appointmentId)
                .orElseGet(() -> {
                    Conversation newConv = Conversation.builder()
                            .appointment(appointment)
                            .patient(appointment.getPatient())
                            .doctor(appointment.getDoctor().getUser())
                            .build();
                    return conversationRepository.save(newConv);
                });

        // Automatically log official prescription message in conversation
        Message prescriptionMsg = Message.builder()
                .conversation(conversation)
                .sender(appointment.getDoctor().getUser())
                .content("🌿 [OFFICIAL AYURVEDIC PRESCRIPTION & GUIDELINES]:\n" + notes)
                .read(false)
                .sentAt(Instant.now())
                .build();
        messageRepository.save(prescriptionMsg);

        return mapToDetail(conversation);
    }

    private ConversationDetailResponse mapToDetail(Conversation c) {
        Appointment a = c.getAppointment();
        List<Message> msgs = messageRepository.findByConversationIdOrderBySentAtAsc(c.getId());

        List<ChatMessageResponse> messageResponses = msgs.stream()
                .map(m -> mapToMessageResponse(m, c))
                .collect(Collectors.toList());

        String docQual = (a != null && a.getDoctor() != null) ? a.getDoctor().getQualifications() : "";
        String docSpec = (a != null && a.getDoctor() != null) ? a.getDoctor().getPrimarySpecialization() : "";
        String notes = (a != null) ? a.getConsultationNotes() : null;

        return ConversationDetailResponse.builder()
                .id(c.getId())
                .appointmentId(a != null ? a.getId() : null)
                .appointmentDate(a != null ? a.getAppointmentDate() : null)
                .startTime(a != null ? a.getStartTime() : null)
                .endTime(a != null ? a.getEndTime() : null)
                .patientId(c.getPatient().getId())
                .patientName(c.getPatient().getFullName())
                .patientEmail(c.getPatient().getEmail())
                .doctorId(c.getDoctor().getId())
                .doctorName(c.getDoctor().getFullName())
                .doctorQualifications(docQual)
                .doctorSpecialization(docSpec)
                .doctorAvatarUrl(c.getDoctor().getAvatarUrl())
                .consultationNotes(notes)
                .createdAt(c.getCreatedAt())
                .messages(messageResponses)
                .build();
    }

    private ChatMessageResponse mapToMessageResponse(Message m, Conversation c) {
        boolean isDoc = m.getSender().getId().equals(c.getDoctor().getId());

        return ChatMessageResponse.builder()
                .id(m.getId())
                .conversationId(c.getId())
                .senderId(m.getSender().getId())
                .senderName(m.getSender().getFullName())
                .senderAvatarUrl(m.getSender().getAvatarUrl())
                .content(m.getContent())
                .read(m.isRead())
                .sentAt(m.getSentAt())
                .isDoctor(isDoc)
                .build();
    }
}
