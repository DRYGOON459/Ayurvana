import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import {
  ShoppingBag,
  Trash2,
  Plus,
  Minus,
  ArrowRight,
  ArrowLeft,
  Tag,
  ShieldCheck,
  Truck,
  CheckCircle2,
  XCircle,
  Sparkles
} from 'lucide-react';
import { useCart } from '../../context/CartContext';

export default function CartPage() {
  const navigate = useNavigate();
  const {
    items,
    updateQuantity,
    removeFromCart,
    clearCart,
    coupon,
    applyCoupon,
    removeCoupon,
    subtotal,
    discountAmount,
    shippingFee,
    taxAmount,
    finalTotal
  } = useCart();

  const [couponInput, setCouponInput] = useState('');
  const [couponFeedback, setCouponFeedback] = useState(null);

  const handleApplyCoupon = (e) => {
    e.preventDefault();
    if (!couponInput.trim()) return;
    const result = applyCoupon(couponInput);
    setCouponFeedback(result);
    if (result.success) {
      setCouponInput('');
    }
  };

  if (items.length === 0) {
    return (
      <div className="min-h-screen bg-stone-50 py-16 px-4 flex items-center justify-center">
        <div className="bg-white p-8 sm:p-12 rounded-3xl border border-stone-200 shadow-sm max-w-lg w-full text-center space-y-6">
          <div className="w-20 h-20 bg-emerald-50 rounded-full flex items-center justify-center mx-auto text-emerald-800">
            <ShoppingBag className="w-10 h-10" />
          </div>
          <div className="space-y-2">
            <h2 className="text-2xl font-bold font-serif text-stone-900">Your Cart is Empty</h2>
            <p className="text-stone-500 text-sm">
              You haven't added any classical herbal formulations or apothecary remedies to your cart yet.
            </p>
          </div>
          <Link
            to="/store"
            className="inline-flex items-center space-x-2 bg-emerald-800 hover:bg-emerald-700 text-white font-bold px-6 py-3.5 rounded-2xl shadow transition"
          >
            <span>Explore Apothecary Store</span>
            <ArrowRight className="w-4 h-4" />
          </Link>
        </div>
      </div>
    );
  }

  const freeShippingRemaining = Math.max(0, 500 - subtotal);

  return (
    <div className="min-h-screen bg-stone-50 py-8 px-4 sm:px-6 lg:px-8">
      <div className="max-w-7xl mx-auto space-y-8">
        {/* Header */}
        <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4">
          <div>
            <div className="inline-flex items-center space-x-2 text-xs font-semibold text-emerald-800 uppercase tracking-wider mb-1">
              <ShoppingBag className="w-4 h-4" />
              <span>Ayurvedic Pharmacy Checkout</span>
            </div>
            <h1 className="text-2xl sm:text-3xl font-serif font-bold text-stone-900">
              Your Shopping Cart ({items.reduce((s, i) => s + i.quantity, 0)} items)
            </h1>
          </div>
          <div className="flex items-center space-x-3">
            <button
              onClick={clearCart}
              className="text-xs text-rose-600 hover:text-rose-800 font-semibold px-3 py-1.5 rounded-lg border border-rose-200 hover:bg-rose-50 transition"
            >
              Clear Cart
            </button>
            <Link
              to="/store"
              className="inline-flex items-center space-x-1.5 text-xs text-stone-600 hover:text-stone-900 font-semibold px-3 py-1.5 rounded-lg border border-stone-300 hover:bg-stone-100 transition"
            >
              <ArrowLeft className="w-3.5 h-3.5" />
              <span>Continue Shopping</span>
            </Link>
          </div>
        </div>

        {/* Free Shipping Alert Bar */}
        <div className="bg-emerald-50 border border-emerald-200 p-4 rounded-2xl flex items-center justify-between text-xs sm:text-sm text-emerald-900">
          <div className="flex items-center space-x-2.5">
            <Truck className="w-5 h-5 text-emerald-700 shrink-0" />
            <span>
              {freeShippingRemaining > 0 ? (
                <>Add <strong className="font-bold">₹{freeShippingRemaining.toFixed(2)}</strong> more to qualify for <strong className="font-bold text-emerald-800">FREE Express Delivery</strong>!</>
              ) : (
                <strong className="font-bold text-emerald-800">🎉 Congratulations! You have qualified for FREE Express Shipping.</strong>
              )}
            </span>
          </div>
          <span className="font-bold text-emerald-800 hidden sm:inline-block">Standard ₹50</span>
        </div>

        {/* Cart Main Content Grid */}
        <div className="grid grid-cols-1 lg:grid-cols-12 gap-8">
          {/* Left Column: Items List */}
          <div className="lg:col-span-8 space-y-4">
            <div className="bg-white rounded-3xl p-6 border border-stone-200 shadow-sm divide-y divide-stone-100">
              {items.map((item) => {
                const unitPrice = item.product.discountPrice || item.product.price;
                const itemTotal = (Number(unitPrice) * item.quantity).toFixed(2);

                return (
                  <div key={item.product.id} className="py-4 first:pt-0 last:pb-0 flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4">
                    <div className="flex items-center space-x-4">
                      <img
                        src={item.product.imageUrl}
                        alt={item.product.name}
                        className="w-20 h-20 rounded-2xl object-cover border border-stone-200 shrink-0"
                      />
                      <div className="space-y-1">
                        {item.product.sanskritName && (
                          <p className="text-[11px] font-serif text-emerald-800 font-semibold">
                            {item.product.sanskritName}
                          </p>
                        )}
                        <Link to={`/store/product/${item.product.slug}`} className="font-bold text-stone-900 hover:text-emerald-800 transition text-sm sm:text-base">
                          {item.product.name}
                        </Link>
                        <p className="text-xs text-stone-400">
                          Net: {item.product.netWeight} | ₹{unitPrice} each
                        </p>
                      </div>
                    </div>

                    <div className="flex items-center justify-between w-full sm:w-auto sm:space-x-6">
                      {/* Quantity Selector */}
                      <div className="flex items-center border border-stone-300 rounded-xl bg-stone-50 p-1">
                        <button
                          onClick={() => updateQuantity(item.product.id, item.quantity - 1)}
                          className="p-1.5 text-stone-600 hover:text-stone-900 hover:bg-stone-200 rounded-lg transition"
                          title="Decrease quantity"
                        >
                          <Minus className="w-3.5 h-3.5" />
                        </button>
                        <span className="px-3 font-bold text-stone-800 text-sm">{item.quantity}</span>
                        <button
                          onClick={() => updateQuantity(item.product.id, item.quantity + 1)}
                          className="p-1.5 text-stone-600 hover:text-stone-900 hover:bg-stone-200 rounded-lg transition"
                          title="Increase quantity"
                        >
                          <Plus className="w-3.5 h-3.5" />
                        </button>
                      </div>

                      {/* Total and Delete */}
                      <div className="text-right flex items-center space-x-4">
                        <span className="font-extrabold text-stone-900 text-base">
                          ₹{itemTotal}
                        </span>
                        <button
                          onClick={() => removeFromCart(item.product.id)}
                          className="text-stone-400 hover:text-rose-600 p-1.5 rounded-lg hover:bg-rose-50 transition"
                          title="Remove item"
                        >
                          <Trash2 className="w-4 h-4" />
                        </button>
                      </div>
                    </div>
                  </div>
                );
              })}
            </div>

            {/* Ayurvedic Guarantees Card */}
            <div className="bg-stone-100/70 rounded-2xl p-4 border border-stone-200 flex flex-wrap gap-4 justify-around text-xs text-stone-600 font-medium">
              <div className="flex items-center space-x-2">
                <ShieldCheck className="w-4 h-4 text-emerald-700" />
                <span>100% Authentic Classical Sourcing</span>
              </div>
              <div className="flex items-center space-x-2">
                <Sparkles className="w-4 h-4 text-emerald-700" />
                <span>Heavy Metal Screened & Certified</span>
              </div>
              <div className="flex items-center space-x-2">
                <Truck className="w-4 h-4 text-emerald-700" />
                <span>Temperature-Controlled Dispatch</span>
              </div>
            </div>
          </div>

          {/* Right Column: Order Summary & Checkout */}
          <div className="lg:col-span-4 space-y-6">
            <div className="bg-white rounded-3xl p-6 border border-stone-200 shadow-sm space-y-6">
              <h2 className="text-lg font-bold font-serif text-stone-900 pb-3 border-b border-stone-100">
                Order Summary
              </h2>

              {/* Coupon Form */}
              <div className="space-y-2">
                <label className="text-xs font-bold text-stone-600 uppercase tracking-wider flex items-center space-x-1">
                  <Tag className="w-3.5 h-3.5 text-emerald-700" />
                  <span>Vedic Discount Coupon:</span>
                </label>
                {coupon ? (
                  <div className="flex items-center justify-between bg-emerald-50 border border-emerald-200 px-3.5 py-2.5 rounded-xl text-xs">
                    <div>
                      <span className="font-extrabold text-emerald-800">{coupon.code}</span>
                      <span className="text-emerald-700 ml-1.5">({coupon.discountPercent}% OFF applied)</span>
                    </div>
                    <button
                      onClick={removeCoupon}
                      className="text-rose-600 hover:text-rose-800 font-bold ml-2"
                      title="Remove coupon"
                    >
                      Remove
                    </button>
                  </div>
                ) : (
                  <form onSubmit={handleApplyCoupon} className="flex gap-2">
                    <input
                      type="text"
                      placeholder="Try AYUR15 or VEDIC20"
                      value={couponInput}
                      onChange={(e) => setCouponInput(e.target.value)}
                      className="flex-1 px-3.5 py-2 text-xs bg-stone-50 border border-stone-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-emerald-600 uppercase"
                    />
                    <button
                      type="submit"
                      className="bg-emerald-800 hover:bg-emerald-700 text-white text-xs font-bold px-4 py-2 rounded-xl transition"
                    >
                      Apply
                    </button>
                  </form>
                )}
                {couponFeedback && (
                  <p className={`text-xs ${couponFeedback.success ? 'text-emerald-700 font-medium' : 'text-rose-600'}`}>
                    {couponFeedback.message}
                  </p>
                )}
              </div>

              {/* Cost Breakdown */}
              <div className="space-y-2.5 text-sm text-stone-600 pt-2 border-t border-stone-100">
                <div className="flex justify-between">
                  <span>Subtotal</span>
                  <span className="font-bold text-stone-800">₹{subtotal.toFixed(2)}</span>
                </div>
                {discountAmount > 0 && (
                  <div className="flex justify-between text-emerald-700 font-semibold">
                    <span>Coupon Discount</span>
                    <span>-₹{discountAmount.toFixed(2)}</span>
                  </div>
                )}
                <div className="flex justify-between">
                  <span>Shipping Fee</span>
                  <span>{shippingFee === 0 ? <strong className="text-emerald-700">FREE</strong> : `₹${shippingFee.toFixed(2)}`}</span>
                </div>
                <div className="flex justify-between">
                  <span>Ayurvedic GST (5%)</span>
                  <span>₹{taxAmount.toFixed(2)}</span>
                </div>
                <div className="pt-3 border-t border-stone-200 flex justify-between items-baseline text-stone-900">
                  <span className="text-base font-bold font-serif">Total Payable</span>
                  <span className="text-2xl font-extrabold text-emerald-900">₹{finalTotal.toFixed(2)}</span>
                </div>
              </div>

              {/* Checkout CTA */}
              <button
                onClick={() => navigate('/checkout')}
                className="w-full bg-emerald-800 hover:bg-emerald-700 text-white font-bold py-4 rounded-2xl shadow-lg transition duration-200 flex items-center justify-center space-x-2"
              >
                <span>Proceed to Secure Checkout</span>
                <ArrowRight className="w-5 h-5" />
              </button>

              <div className="text-center">
                <p className="text-[11px] text-stone-400">
                  🔒 256-Bit SSL Encrypted Industry Checkout
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
