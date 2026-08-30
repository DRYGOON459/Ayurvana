import api from './api';

export const articleService = {
  getAllArticles: (page = 0, size = 12, sortBy = 'createdAt', sortDir = 'desc') =>
    api.get(`/articles?page=${page}&size=${size}&sortBy=${sortBy}&sortDir=${sortDir}`),

  getArticlesByCategory: (category, page = 0, size = 12) =>
    api.get(`/articles/category/${encodeURIComponent(category)}?page=${page}&size=${size}`),

  getArticleBySlug: (slug) =>
    api.get(`/articles/${slug}`),

  getArticleById: (id) =>
    api.get(`/articles/id/${id}`),
};

export default articleService;
