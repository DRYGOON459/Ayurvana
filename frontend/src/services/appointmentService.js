import api from './api';

export const appointmentService = {
  getDoctorAvailability: (doctorId, dateString) =>
    api.get(`/appointments/doctor/${doctorId}/availability?date=${dateString}`),

  bookAppointment: (data) =>
    api.post('/appointments/book', data),

  getMyAppointments: (page = 0, size = 10) =>
    api.get(`/appointments/my-appointments?page=${page}&size=${size}`),

  getDoctorSchedule: (page = 0, size = 10) =>
    api.get(`/appointments/doctor-schedule?page=${page}&size=${size}`),

  updateStatus: (appointmentId, status, reason = null) =>
    api.patch(`/appointments/${appointmentId}/status`, { status, reason }),
};

export default appointmentService;
