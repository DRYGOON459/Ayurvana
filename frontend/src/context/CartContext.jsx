import React, { createContext, useContext, useState, useEffect } from 'react';

const CartContext = createContext();

export const CartProvider = ({ children }) => {
  const [items, setItems] = useState(() => {
    try {
      const saved = localStorage.getItem('ayurvana_cart');
      return saved ? JSON.parse(saved) : [];
    } catch (e) {
      return [];
    }
  });

  const [coupon, setCoupon] = useState(() => {
    try {
      const saved = localStorage.getItem('ayurvana_coupon');
      return saved ? JSON.parse(saved) : null;
    } catch (e) {
      return null;
    }
  });

  const [shippingAddress, setShippingAddress] = useState(() => {
    try {
      const saved = localStorage.getItem('ayurvana_shipping_address');
      return saved ? JSON.parse(saved) : {
        recipientName: '',
        phoneNumber: '',
        streetAddress: '',
        city: '',
        state: '',
        pincode: '',
        notes: ''
      };
    } catch (e) {
      return {
        recipientName: '',
        phoneNumber: '',
        streetAddress: '',
        city: '',
        state: '',
        pincode: '',
        notes: ''
      };
    }
  });

  useEffect(() => {
    localStorage.setItem('ayurvana_cart', JSON.stringify(items));
  }, [items]);

  useEffect(() => {
    if (coupon) {
      localStorage.setItem('ayurvana_coupon', JSON.stringify(coupon));
    } else {
      localStorage.removeItem('ayurvana_coupon');
    }
  }, [coupon]);

  useEffect(() => {
    localStorage.setItem('ayurvana_shipping_address', JSON.stringify(shippingAddress));
  }, [shippingAddress]);

  const addToCart = (product, quantity = 1) => {
    setItems(prevItems => {
      const existing = prevItems.find(item => item.product.id === product.id);
      if (existing) {
        return prevItems.map(item =>
          item.product.id === product.id
            ? { ...item, quantity: item.quantity + quantity }
            : item
        );
      }
      return [...prevItems, { product, quantity }];
    });
  };

  const updateQuantity = (productId, quantity) => {
    if (quantity <= 0) {
      removeFromCart(productId);
      return;
    }
    setItems(prevItems =>
      prevItems.map(item =>
        item.product.id === productId ? { ...item, quantity } : item
      )
    );
  };

  const removeFromCart = (productId) => {
    setItems(prevItems => prevItems.filter(item => item.product.id !== productId));
  };

  const clearCart = () => {
    setItems([]);
    setCoupon(null);
  };

  const applyCoupon = (code) => {
    const cleanCode = code.trim().toUpperCase();
    if (cleanCode === 'AYUR15') {
      const c = { code: 'AYUR15', discountPercent: 15, label: '15% Vedic Health Discount' };
      setCoupon(c);
      return { success: true, message: 'AYUR15 applied! 15% discount saved.' };
    } else if (cleanCode === 'VEDIC20') {
      const c = { code: 'VEDIC20', discountPercent: 20, label: '20% Classical Rasayana Discount' };
      setCoupon(c);
      return { success: true, message: 'VEDIC20 applied! 20% discount saved.' };
    } else {
      return { success: false, message: 'Invalid coupon code. Try AYUR15 or VEDIC20.' };
    }
  };

  const removeCoupon = () => {
    setCoupon(null);
  };

  // Pricing calculations
  const totalItemCount = items.reduce((sum, item) => sum + item.quantity, 0);

  const subtotal = items.reduce((sum, item) => {
    const price = item.product.discountPrice || item.product.price;
    return sum + (Number(price) * item.quantity);
  }, 0);

  const discountAmount = coupon
    ? Number((subtotal * (coupon.discountPercent / 100)).toFixed(2))
    : 0;

  const discountedSubtotal = Math.max(0, subtotal - discountAmount);
  const shippingFee = subtotal === 0 || subtotal >= 500 ? 0 : 50;
  const taxAmount = Number((discountedSubtotal * 0.05).toFixed(2)); // 5% Ayurvedic GST
  const finalTotal = Number((discountedSubtotal + shippingFee + taxAmount).toFixed(2));

  return (
    <CartContext.Provider
      value={{
        items,
        addToCart,
        updateQuantity,
        removeFromCart,
        clearCart,
        coupon,
        applyCoupon,
        removeCoupon,
        shippingAddress,
        setShippingAddress,
        totalItemCount,
        subtotal,
        discountAmount,
        shippingFee,
        taxAmount,
        finalTotal
      }}
    >
      {children}
    </CartContext.Provider>
  );
};

export const useCart = () => {
  const context = useContext(CartContext);
  if (!context) {
    throw new Error('useCart must be used within a CartProvider');
  }
  return context;
};
