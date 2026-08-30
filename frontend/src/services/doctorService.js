import api from './api';

export const doctorService = {
  getVerifiedDoctors: (specialization = '', query = '', page = 0, size = 12) => {
    let url = `/doctors?page=${page}&size=${size}`;
    if (specialization && specialization !== 'ALL') {
      url += `&specialization=${encodeURIComponent(specialization)}`;
    }
    if (query && query.trim()) {
      url += `&q=${encodeURIComponent(query.trim())}`;
    }
    return api.get(url);
  },

  getDoctorById: (id) =>
    api.get(`/doctors/${id}`),

  onboardDoctor: (data) =>
    api.post('/doctors/onboard', data),

  getMyDoctorProfile: () =>
    api.get('/doctors/me'),

  getMyDoctorStats: () =>
    api.get('/doctors/me/stats'),
};

export default doctorService;
