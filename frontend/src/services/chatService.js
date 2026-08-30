import api from './api';

export const chatService = {
  getConversationByAppointment: (appointmentId) =>
    api.get(`/chat/appointment/${appointmentId}`),

  getMessages: (conversationId) =>
    api.get(`/chat/${conversationId}/messages`),

  sendMessage: (conversationId, content) =>
    api.post(`/chat/${conversationId}/messages`, { content }),

  savePrescription: (appointmentId, notes) =>
    api.post(`/chat/appointment/${appointmentId}/prescription`, { notes }),
};

export default chatService;
