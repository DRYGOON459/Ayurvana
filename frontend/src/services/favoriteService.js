import api from './api';

export const favoriteService = {
  addFavorite: (itemType, itemId) =>
    api.post('/favorites', { itemType, itemId }),

  removeFavorite: (itemType, itemId) =>
    api.delete(`/favorites?type=${itemType}&itemId=${itemId}`),

  checkFavorite: (itemType, itemId) =>
    api.get(`/favorites/check?type=${itemType}&itemId=${itemId}`),

  getFavorites: (type = '', page = 0, size = 20) => {
    const typeParam = type && type !== 'ALL' ? `&type=${type}` : '';
    return api.get(`/favorites?page=${page}&size=${size}${typeParam}`);
  },
};

export default favoriteService;
