import api from './api';

export const authService = {
  login: (email, password) =>
    api.post('/auth/login', { email, password }),

  register: (fullName, email, password, phoneNumber = '', role = 'USER') =>
    api.post('/auth/register', { fullName, email, password, phoneNumber, role }),

  getCurrentUser: () =>
    api.get('/auth/me'),

  updateProfile: (profileData) =>
    api.put('/auth/profile', profileData),
};

export default authService;
