import axios from 'axios';

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL || '/api/v1',
  headers: {
    'Content-Type': 'application/json',
  },
  timeout: 10000,
});

// Request Interceptor: Automatically attaches JWT Bearer token if present
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('ayurvana_token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// Response Interceptor: Handles global errors (e.g. 401 Unauthorized)
api.interceptors.response.use(
  (response) => response.data,
  (error) => {
    if (error.response && error.response.status === 401) {
      // Clear token on 401
      localStorage.removeItem('ayurvana_token');
      localStorage.removeItem('ayurvana_user');
    }
    return Promise.reject(error);
  }
);

export default api;
