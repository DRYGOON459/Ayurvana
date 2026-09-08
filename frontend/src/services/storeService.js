import api from './api';

export const storeService = {
  // Products
  getAllProducts: (params = {}) => {
    return api.get('/products', { params });
  },

  getFeaturedProducts: () => {
    return api.get('/products/featured');
  },

  getProductBySlug: (slug) => {
    return api.get(`/products/${slug}`);
  },

  getProductsByCategory: (category, params = {}) => {
    return api.get(`/products/category/${category}`, { params });
  },

  getProductsByDosha: (dosha, params = {}) => {
    return api.get(`/products/dosha/${dosha}`, { params });
  },

  searchProducts: (query, params = {}) => {
    return api.get('/products/search', { params: { query, ...params } });
  },

  // Orders
  createOrder: (orderData) => {
    return api.post('/orders', orderData);
  },

  getMyOrders: (params = {}) => {
    return api.get('/orders/user/my-orders', { params });
  },

  getOrderByNumber: (orderNumber) => {
    return api.get(`/orders/${orderNumber}`);
  },

  // Payment Gateway
  createPaymentIntent: (paymentData) => {
    return api.post('/payments/create-intent', paymentData);
  },

  verifyPayment: (verifyData) => {
    return api.post('/payments/verify', verifyData);
  },

  getPaymentDetails: (reference) => {
    return api.get(`/payments/${reference}`);
  },

  getUserTransactions: (params = {}) => {
    return api.get('/payments/user/transactions', { params });
  },

  // Password Recovery
  forgotPassword: (email) => {
    return api.post('/auth/forgot-password', { email });
  },

  resetPassword: (token, newPassword) => {
    return api.post('/auth/reset-password', { token, newPassword });
  },
};

export default storeService;
