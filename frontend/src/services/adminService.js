import api from './api';

export const adminService = {
  getPlatformStats: () =>
    api.get('/admin/stats'),

  getPendingDoctors: (page = 0, size = 10) =>
    api.get(`/admin/doctors/pending?page=${page}&size=${size}`),

  verifyDoctor: (doctorId, status, notes = null) =>
    api.post(`/admin/doctors/${doctorId}/verify`, { status, notes }),
};

export default adminService;
