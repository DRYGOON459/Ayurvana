import api from './api';

export const meditationService = {
  getAllMeditations: (page = 0, size = 12) =>
    api.get(`/meditations?page=${page}&size=${size}`),

  getMeditationsByCategory: (category, page = 0, size = 12) =>
    api.get(`/meditations/category/${encodeURIComponent(category)}?page=${page}&size=${size}`),

  getMeditationById: (id) =>
    api.get(`/meditations/${id}`),
};

export default meditationService;
