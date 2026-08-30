import api from './api';

export const searchService = {
  globalSearch: (query, limit = 6) =>
    api.get(`/search?q=${encodeURIComponent(query)}&limit=${limit}`),
};

export default searchService;
