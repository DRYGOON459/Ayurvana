import api from './api';

export const plantScannerService = {
  scanPlant: (imageFile) => {
    const formData = new FormData();
    formData.append('image', imageFile);

    return api.post('/plant-scanner/scan', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
  },

  getHistory: (page = 0, size = 10) =>
    api.get(`/plant-scanner/history?page=${page}&size=${size}`),
};

export default plantScannerService;
