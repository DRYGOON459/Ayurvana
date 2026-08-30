import api from './api';

export const videoService = {
  getRoomToken: (appointmentId) =>
    api.get(`/video/appointment/${appointmentId}/token`),

  endVideoCall: (appointmentId) =>
    api.post(`/video/appointment/${appointmentId}/end`),
};

export default videoService;
