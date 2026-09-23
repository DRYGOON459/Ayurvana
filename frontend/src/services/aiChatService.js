import api from './api';

export const aiChatService = {
  sendMessage: (message, userDosha = null, history = []) =>
    api.post('/ai-chat/message', { message, userDosha, history }),

  getSuggestions: () =>
    api.get('/ai-chat/suggestions'),
};

export default aiChatService;
