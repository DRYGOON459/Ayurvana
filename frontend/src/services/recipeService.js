import api from './api';

export const recipeService = {
  getAllRecipes: (page = 0, size = 12, sortBy = 'title', direction = 'asc') =>
    api.get(`/recipes?page=${page}&size=${size}&sortBy=${sortBy}&direction=${direction}`),

  getRecipeBySlug: (slug) =>
    api.get(`/recipes/${slug}`),

  getRecipesByDosha: (dosha, page = 0, size = 12) =>
    api.get(`/recipes/dosha/${dosha}?page=${page}&size=${size}`),

  getRecipesByMealType: (mealType, page = 0, size = 12) =>
    api.get(`/recipes/meal-type/${encodeURIComponent(mealType)}?page=${page}&size=${size}`),

  searchRecipes: (query, page = 0, size = 12) =>
    api.get(`/recipes/search?query=${encodeURIComponent(query)}&page=${page}&size=${size}`),
};

export default recipeService;
