import api from './api';

export const herbService = {
  getAllHerbs: (page = 0, size = 12, sortBy = 'commonName', sortDir = 'asc') =>
    api.get(`/herbs?page=${page}&size=${size}&sortBy=${sortBy}&sortDir=${sortDir}`),

  searchHerbs: (query, page = 0, size = 12) =>
    api.get(`/herbs/search?query=${encodeURIComponent(query)}&page=${page}&size=${size}`),

  getHerbBySlug: (slug) =>
    api.get(`/herbs/${slug}`),

  getHerbById: (id) =>
    api.get(`/herbs/id/${id}`),
};

export default herbService;
