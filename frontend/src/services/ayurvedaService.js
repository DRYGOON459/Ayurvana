import api from './api';

export const ayurvedaService = {
  getAllTopics: (page = 0, size = 12) =>
    api.get(`/ayurveda/topics?page=${page}&size=${size}`),

  getTopicsByCategory: (category, page = 0, size = 12) =>
    api.get(`/ayurveda/topics/category/${encodeURIComponent(category)}?page=${page}&size=${size}`),

  getTopicsByDosha: (doshaType, page = 0, size = 12) =>
    api.get(`/ayurveda/topics/dosha/${doshaType}?page=${page}&size=${size}`),

  getTopicBySlug: (slug) =>
    api.get(`/ayurveda/topics/${slug}`),

  getTopicById: (id) =>
    api.get(`/ayurveda/topics/id/${id}`),
};

export default ayurvedaService;
