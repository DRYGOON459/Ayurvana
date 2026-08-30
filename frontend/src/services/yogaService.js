import api from './api';

export const yogaService = {
  getAllPoses: (page = 0, size = 12, sortBy = 'englishName', sortDir = 'asc') =>
    api.get(`/yoga?page=${page}&size=${size}&sortBy=${sortBy}&sortDir=${sortDir}`),

  getPosesByDifficulty: (difficulty, page = 0, size = 12) =>
    api.get(`/yoga/difficulty/${difficulty}?page=${page}&size=${size}`),

  getPosesByCategory: (category, page = 0, size = 12) =>
    api.get(`/yoga/category/${encodeURIComponent(category)}?page=${page}&size=${size}`),

  getPoseBySlug: (slug) =>
    api.get(`/yoga/${slug}`),

  getPoseById: (id) =>
    api.get(`/yoga/id/${id}`),
};

export default yogaService;
