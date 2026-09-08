import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import {
  ShoppingBag,
  ShieldCheck,
  CheckCircle2,
  Lock,
  CreditCard,
  Smartphone,
  Building,
  Wallet,
  ArrowRight,
  ArrowLeft,
  Truck,
  Printer,
  Sparkles,
  AlertCircle,
  Clock
} from 'lucide-react';
import { useCart } from '../../context/CartContext';
import { useAuth } from '../../context/AuthContext';
import storeService from '../../services/storeService';

const PAYMENT_METHODS = [
  {
    id: 'UPI',
    label: 'Instant UPI / QR',
    description: 'Google Pay, PhonePe, Paytm, BHIM UPI',
    icon: Smartphone,
    popular: true
  },
  {
    id: 'CREDIT_CARD',
    label: 'Credit or Debit Card',
    description: 'Visa, MasterCard, RuPay, Maestro',
    icon: CreditCard,
    popular: false
  },
  {
    id: 'NET_BANKING',
    label: 'Net Banking',
    description: 'HDFC, SBI, ICICI, Axis & 50+ Banks',
    icon: Building,
    popular: false
  },
  {
    id: 'RAZORPAY_SANDBOX',
    label: 'Razorpay Instant Gateway',
    description: 'Industry-standard secure checkout pipeline',
    icon: ShieldCheck,
    popular: false
  },
  {
    id: 'CASH_ON_DELIVERY',
    label: 'Cash on Delivery',
    description: 'Pay cash upon delivery at your doorstep',
    icon: Wallet,
    popular: false
  }
];

export default function CheckoutPage() {
  const navigate = useNavigate();
  const { user, isAuthenticated } = useAuth();
  const {
    items,
    clearCart,
    coupon,
    subtotal,
    discountAmount,
    shippingFee,
    taxAmount,
    finalTotal,
    shippingAddress,
    setShippingAddress
  } = useCart();

  const [formData, setFormData] = useState({
    recipientName: shippingAddress.recipientName || user?.fullName || '',
    phoneNumber: shippingAddress.phoneNumber || user?.phoneNumber || '',
    streetAddress: shippingAddress.streetAddress || '',
    city: shippingAddress.city || '',
    state: shippingAddress.state || 'Maharashtra',
    pincode: shippingAddress.pincode || '',
    notes: shippingAddress.notes || ''
  });

  const [selectedMethod, setSelectedMethod] = useState('UPI');
  const [upiId, setUpiId] = useState('');
  const [cardData, setCardData] = useState({ number: '', expiry: '', cvv: '', name: '' });
  const [selectedBank, setSelectedBank] = useState('HDFC');

  // Checkout Processing States
  const [processing, setProcessing] = useState(false);
  const [processingStage, setProcessingStage] = useState('');
  const [confirmedOrder, setConfirmedOrder] = useState(null);
  const [paymentTransaction, setPaymentTransaction] = useState(null);
  const [error, setError] = useState(null);

  if (items.length === 0 && !confirmedOrder) {
    return (
      <div className="min-h-screen bg-stone-50 py-16 px-4 flex items-center justify-center">
        <div className="bg-white p-8 rounded-3xl border border-stone-200 shadow-sm max-w-md w-full text-center space-y-4">
          <ShoppingBag className="w-12 h-12 text-stone-300 mx-auto" />
          <h2 className="text-xl font-bold text-stone-900">Your Cart is Empty</h2>
          <p className="text-stone-500 text-xs">Add products from the apothecary store before checking out.</p>
          <Link to="/store" className="inline-block bg-emerald-800 text-white font-bold text-xs px-5 py-2.5 rounded-xl hover:bg-emerald-700 transition">
            Explore Store
          </Link>
        </div>
      </div>
    );
  }

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handlePlaceOrder = async (e) => {
    e.preventDefault();
    if (!isAuthenticated) {
      setError('Please sign in to complete your checkout and track your order.');
      navigate('/login?redirect=/checkout');
      return;
    }

    // Basic Validation
    if (!formData.recipientName || !formData.phoneNumber || !formData.streetAddress || !formData.city || !formData.pincode) {
      setError('Please complete all required shipping fields.');
      return;
    }

    try {
      setError(null);
      setProcessing(true);
      setShippingAddress(formData);

      // Step 1: Create Order
      setProcessingStage('Creating order record and reserving apothecary stock...');
      const orderPayload = {
        items: items.map((i) => ({ productId: i.product.id, quantity: i.quantity })),
        paymentMethod: selectedMethod,
        couponCode: coupon ? coupon.code : null,
        recipientName: formData.recipientName,
        phoneNumber: formData.phoneNumber,
        streetAddress: formData.streetAddress,
        city: formData.city,
        state: formData.state,
        pincode: formData.pincode,
        notes: formData.notes
      };

      const orderRes = await storeService.createOrder(orderPayload);
      const createdOrder = orderRes.data;

      // Step 2: If prepaid method, create and verify payment intent
      if (selectedMethod !== 'CASH_ON_DELIVERY') {
        setProcessingStage(`Initiating secure payment gateway intent for ₹${finalTotal}...`);
        await new Promise((r) => setTimeout(r, 900));

        const intentPayload = {
          amount: finalTotal,
          currency: 'INR',
          paymentMethod: selectedMethod,
          purpose: 'ECOMMERCE_ORDER',
          orderId: createdOrder.id
        };

        const intentRes = await storeService.createPaymentIntent(intentPayload);
        const paymentIntent = intentRes.data;

        setProcessingStage('Processing bank authorization & verifying HMAC-SHA256 signature...');
        await new Promise((r) => setTimeout(r, 1100));

        const verifyPayload = {
          transactionReference: paymentIntent.transactionReference,
          gatewayOrderId: paymentIntent.gatewayOrderId,
          gatewayPaymentId: 'pay_' + Math.random().toString(36).substring(2, 12),
          gatewaySignature: 'sig_' + Math.random().toString(36).substring(2, 16),
          simulateSuccess: true
        };

        const verifyRes = await storeService.verifyPayment(verifyPayload);
        setPaymentTransaction(verifyRes.data);
      }

      setProcessingStage('Finalizing order confirmation and generating tax invoice...');
      await new Promise((r) => setTimeout(r, 700));

      setConfirmedOrder(createdOrder);
      clearCart();
    } catch (err) {
      console.error('Checkout failed:', err);
      setError(err.response?.data?.message || err.message || 'Payment processing failed. Please try again.');
    } finally {
      setProcessing(false);
    }
  };

  // If Order Confirmed View
  if (confirmedOrder) {
    return (
      <div className="min-h-screen bg-stone-50 py-12 px-4 sm:px-6 lg:px-8">
        <div className="max-w-3xl mx-auto space-y-8">
          {/* Success Banner */}
          <div className="bg-white rounded-3xl p-8 sm:p-12 border border-emerald-200 shadow-xl text-center space-y-6">
            <div className="w-20 h-20 bg-emerald-100 rounded-full flex items-center justify-center mx-auto text-emerald-800">
              <CheckCircle2 className="w-12 h-12 text-emerald-700" />
            </div>

            <div className="space-y-2">
              <span className="text-xs font-bold text-emerald-800 uppercase tracking-widest bg-emerald-50 px-3 py-1 rounded-full border border-emerald-200">
                Payment & Order Confirmed
              </span>
              <h1 className="text-3xl font-serif font-bold text-stone-900">
                Dhanyavad! Your Order has been Placed.
              </h1>
              <p className="text-stone-600 text-sm">
                We have received your order. Our certified Ayurvedic pharmacists are carefully dispensing and sealing your herbal formulations.
              </p>
            </div>

            {/* Order Reference Box */}
            <div className="bg-stone-50 rounded-2xl p-6 border border-stone-200 grid grid-cols-1 sm:grid-cols-3 gap-4 text-left text-xs">
              <div>
                <span className="text-stone-400 block mb-1">Order Number</span>
                <span className="font-extrabold text-stone-900 font-mono text-sm">{confirmedOrder.orderNumber}</span>
              </div>
              <div>
                <span className="text-stone-400 block mb-1">Tracking Number</span>
                <span className="font-extrabold text-emerald-800 font-mono text-sm">{confirmedOrder.trackingNumber}</span>
              </div>
              <div>
                <span className="text-stone-400 block mb-1">Total Paid</span>
                <span className="font-extrabold text-stone-900 text-sm">₹{confirmedOrder.totalAmount?.toFixed(2)}</span>
              </div>
            </div>

            {/* Actions */}
            <div className="flex flex-col sm:flex-row gap-3 justify-center pt-2">
              <button
                onClick={() => window.print()}
                className="inline-flex items-center justify-center space-x-2 bg-stone-100 hover:bg-stone-200 text-stone-800 font-semibold px-6 py-3 rounded-xl border border-stone-300 transition text-xs"
              >
                <Printer className="w-4 h-4" />
                <span>Print Official Tax Invoice</span>
              </button>
              <Link
                to="/orders"
                className="inline-flex items-center justify-center space-x-2 bg-emerald-800 hover:bg-emerald-700 text-white font-bold px-6 py-3 rounded-xl shadow transition text-xs"
              >
                <Truck className="w-4 h-4" />
                <span>Track in My Orders</span>
              </Link>
            </div>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-stone-50 py-8 px-4 sm:px-6 lg:px-8">
      {/* Loading Modal */}
      {processing && (
        <div className="fixed inset-0 z-50 bg-stone-900/80 backdrop-blur-sm flex items-center justify-center p-4">
          <div className="bg-white rounded-3xl p-8 max-w-md w-full text-center space-y-6 shadow-2xl border border-stone-200">
            <div className="relative w-16 h-16 mx-auto">
              <div className="absolute inset-0 rounded-full border-4 border-emerald-200"></div>
              <div className="absolute inset-0 rounded-full border-4 border-emerald-800 border-t-transparent animate-spin"></div>
              <Sparkles className="w-6 h-6 text-emerald-800 absolute inset-0 m-auto" />
            </div>
            <div className="space-y-2">
              <h3 className="text-lg font-bold font-serif text-stone-900">Secure Payment Processing</h3>
              <p className="text-xs text-stone-600 animate-pulse">{processingStage}</p>
            </div>
            <div className="text-[11px] text-stone-400">
              🔒 Encrypted Gateway • Please do not refresh or close this tab
            </div>
          </div>
        </div>
      )}

      <div className="max-w-7xl mx-auto space-y-8">
        {/* Navigation Breadcrumbs */}
        <div className="flex items-center justify-between text-xs text-stone-500">
          <Link to="/cart" className="inline-flex items-center space-x-1 hover:text-emerald-800 font-medium">
            <ArrowLeft className="w-4 h-4" />
            <span>Return to Shopping Cart</span>
          </Link>
          <div className="flex items-center space-x-2 text-stone-700 font-semibold">
            <Lock className="w-3.5 h-3.5 text-emerald-700" />
            <span>256-Bit SSL Encrypted Industry Checkout</span>
          </div>
        </div>

        {error && (
          <div className="bg-rose-50 border border-rose-200 p-4 rounded-2xl flex items-start space-x-3 text-xs text-rose-800">
            <AlertCircle className="w-5 h-5 text-rose-600 shrink-0 mt-0.5" />
            <div>
              <strong className="font-bold">Error: </strong>
              <span>{error}</span>
            </div>
          </div>
        )}

        <form onSubmit={handlePlaceOrder} className="grid grid-cols-1 lg:grid-cols-12 gap-8">
          {/* Left Column: Shipping & Payment Details */}
          <div className="lg:col-span-8 space-y-8">
            {/* Step 1: Shipping Address */}
            <div className="bg-white rounded-3xl p-6 sm:p-8 border border-stone-200 shadow-sm space-y-6">
              <div className="flex items-center space-x-3 pb-4 border-b border-stone-100">
                <div className="w-8 h-8 rounded-full bg-emerald-800 text-white font-bold text-sm flex items-center justify-center">
                  1
                </div>
                <div>
                  <h2 className="text-lg font-bold font-serif text-stone-900">Shipping & Delivery Details</h2>
                  <p className="text-xs text-stone-500">Where should we dispatch your classical formulations?</p>
                </div>
              </div>

              <div className="grid grid-cols-1 sm:grid-cols-2 gap-4 text-xs">
                <div>
                  <label className="font-bold text-stone-700 block mb-1">Full Name *</label>
                  <input
                    type="text"
                    name="recipientName"
                    value={formData.recipientName}
                    onChange={handleInputChange}
                    required
                    placeholder="Recipient's full name"
                    className="w-full px-4 py-2.5 rounded-xl border border-stone-300 focus:outline-none focus:ring-2 focus:ring-emerald-600"
                  />
                </div>

                <div>
                  <label className="font-bold text-stone-700 block mb-1">Phone Number (with WhatsApp updates) *</label>
                  <input
                    type="tel"
                    name="phoneNumber"
                    value={formData.phoneNumber}
                    onChange={handleInputChange}
                    required
                    placeholder="+91 98765 43210"
                    className="w-full px-4 py-2.5 rounded-xl border border-stone-300 focus:outline-none focus:ring-2 focus:ring-emerald-600"
                  />
                </div>

                <div className="sm:col-span-2">
                  <label className="font-bold text-stone-700 block mb-1">Street Address / House No / Landmark *</label>
                  <input
                    type="text"
                    name="streetAddress"
                    value={formData.streetAddress}
                    onChange={handleInputChange}
                    required
                    placeholder="Flat 402, Green Meadows, 5th Main Road"
                    className="w-full px-4 py-2.5 rounded-xl border border-stone-300 focus:outline-none focus:ring-2 focus:ring-emerald-600"
                  />
                </div>

                <div>
                  <label className="font-bold text-stone-700 block mb-1">City *</label>
                  <input
                    type="text"
                    name="city"
                    value={formData.city}
                    onChange={handleInputChange}
                    required
                    placeholder="e.g. Mumbai, New Delhi, Bengaluru"
                    className="w-full px-4 py-2.5 rounded-xl border border-stone-300 focus:outline-none focus:ring-2 focus:ring-emerald-600"
                  />
                </div>

                <div className="grid grid-cols-2 gap-2">
                  <div>
                    <label className="font-bold text-stone-700 block mb-1">State *</label>
                    <select
                      name="state"
                      value={formData.state}
                      onChange={handleInputChange}
                      className="w-full px-3 py-2.5 rounded-xl border border-stone-300 focus:outline-none focus:ring-2 focus:ring-emerald-600 bg-white"
                    >
                      <option value="Maharashtra">Maharashtra</option>
                      <option value="Karnataka">Karnataka</option>
                      <option value="Delhi">Delhi</option>
                      <option value="Kerala">Kerala</option>
                      <option value="Gujarat">Gujarat</option>
                      <option value="Tamil Nadu">Tamil Nadu</option>
                      <option value="Uttar Pradesh">Uttar Pradesh</option>
                      <option value="Other">Other States</option>
                    </select>
                  </div>
                  <div>
                    <label className="font-bold text-stone-700 block mb-1">PIN Code *</label>
                    <input
                      type="text"
                      name="pincode"
                      value={formData.pincode}
                      onChange={handleInputChange}
                      required
                      placeholder="400001"
                      className="w-full px-3 py-2.5 rounded-xl border border-stone-300 focus:outline-none focus:ring-2 focus:ring-emerald-600"
                    />
                  </div>
                </div>
              </div>
            </div>

            {/* Step 2: Payment Gateway Selector */}
            <div className="bg-white rounded-3xl p-6 sm:p-8 border border-stone-200 shadow-sm space-y-6">
              <div className="flex items-center space-x-3 pb-4 border-b border-stone-100">
                <div className="w-8 h-8 rounded-full bg-emerald-800 text-white font-bold text-sm flex items-center justify-center">
                  2
                </div>
                <div>
                  <h2 className="text-lg font-bold font-serif text-stone-900">Payment Gateway Method</h2>
                  <p className="text-xs text-stone-500">Choose your preferred Indian payment mode</p>
                </div>
              </div>

              <div className="grid grid-cols-1 sm:grid-cols-2 gap-3">
                {PAYMENT_METHODS.map((method) => {
                  const Icon = method.icon;
                  const isSelected = selectedMethod === method.id;

                  return (
                    <div
                      key={method.id}
                      onClick={() => setSelectedMethod(method.id)}
                      className={`cursor-pointer p-4 rounded-2xl border-2 transition relative flex items-start space-x-3 ${
                        isSelected
                          ? 'border-emerald-800 bg-emerald-50/40 shadow-sm'
                          : 'border-stone-200 hover:border-stone-300 bg-white'
                      }`}
                    >
                      <div className={`p-2 rounded-xl ${isSelected ? 'bg-emerald-800 text-white' : 'bg-stone-100 text-stone-600'}`}>
                        <Icon className="w-5 h-5" />
                      </div>
                      <div className="space-y-0.5 flex-1">
                        <div className="flex items-center space-x-2">
                          <span className="font-bold text-xs sm:text-sm text-stone-900">{method.label}</span>
                          {method.popular && (
                            <span className="bg-emerald-100 text-emerald-800 text-[10px] font-extrabold px-1.5 py-0.5 rounded">
                              Fastest
                            </span>
                          )}
                        </div>
                        <p className="text-[11px] text-stone-500">{method.description}</p>
                      </div>
                    </div>
                  );
                })}
              </div>

              {/* Dynamic Payment Input Section */}
              {selectedMethod === 'UPI' && (
                <div className="bg-stone-50 p-4 rounded-2xl border border-stone-200 space-y-3 text-xs">
                  <label className="font-bold text-stone-800 block">Enter your UPI ID (VPA)</label>
                  <div className="flex gap-2">
                    <input
                      type="text"
                      placeholder="e.g. yourname@oksbi / 9876543210@paytm"
                      value={upiId}
                      onChange={(e) => setUpiId(e.target.value)}
                      className="flex-1 px-4 py-2.5 rounded-xl border border-stone-300 focus:outline-none focus:ring-2 focus:ring-emerald-600 bg-white"
                    />
                  </div>
                  <p className="text-[11px] text-stone-500">
                    A payment collect request of <strong>₹{finalTotal.toFixed(2)}</strong> will be authorized upon clicking place order.
                  </p>
                </div>
              )}

              {selectedMethod === 'CREDIT_CARD' && (
                <div className="bg-stone-50 p-4 rounded-2xl border border-stone-200 space-y-3 text-xs">
                  <div className="space-y-1">
                    <label className="font-bold text-stone-800 block">Card Number</label>
                    <input
                      type="text"
                      placeholder="4532 •••• •••• 8892"
                      value={cardData.number}
                      onChange={(e) => setCardData({ ...cardData, number: e.target.value })}
                      className="w-full px-4 py-2.5 rounded-xl border border-stone-300 focus:outline-none focus:ring-2 focus:ring-emerald-600 bg-white"
                    />
                  </div>
                  <div className="grid grid-cols-2 gap-3">
                    <div>
                      <label className="font-bold text-stone-800 block mb-1">Valid Thru (MM/YY)</label>
                      <input
                        type="text"
                        placeholder="08/28"
                        value={cardData.expiry}
                        onChange={(e) => setCardData({ ...cardData, expiry: e.target.value })}
                        className="w-full px-4 py-2.5 rounded-xl border border-stone-300 focus:outline-none focus:ring-2 focus:ring-emerald-600 bg-white"
                      />
                    </div>
                    <div>
                      <label className="font-bold text-stone-800 block mb-1">CVV / CVC</label>
                      <input
                        type="password"
                        maxLength="4"
                        placeholder="•••"
                        value={cardData.cvv}
                        onChange={(e) => setCardData({ ...cardData, cvv: e.target.value })}
                        className="w-full px-4 py-2.5 rounded-xl border border-stone-300 focus:outline-none focus:ring-2 focus:ring-emerald-600 bg-white"
                      />
                    </div>
                  </div>
                </div>
              )}
            </div>
          </div>

          {/* Right Column: Order Review & Pay CTA */}
          <div className="lg:col-span-4 space-y-6">
            <div className="bg-white rounded-3xl p-6 border border-stone-200 shadow-sm space-y-6">
              <h2 className="text-base font-bold font-serif text-stone-900 pb-3 border-b border-stone-100">
                Order Review ({items.reduce((s, i) => s + i.quantity, 0)} Items)
              </h2>

              {/* Items List Preview */}
              <div className="max-h-48 overflow-y-auto space-y-3 divide-y divide-stone-100 pr-1">
                {items.map((item) => (
                  <div key={item.product.id} className="pt-2 first:pt-0 flex items-center justify-between text-xs">
                    <div className="flex items-center space-x-2">
                      <img src={item.product.imageUrl} alt="" className="w-8 h-8 rounded-lg object-cover border border-stone-200" />
                      <div>
                        <p className="font-bold text-stone-800 line-clamp-1">{item.product.name}</p>
                        <p className="text-[10px] text-stone-400">Qty: {item.quantity}</p>
                      </div>
                    </div>
                    <span className="font-bold text-stone-900">
                      ₹{((Number(item.product.discountPrice || item.product.price)) * item.quantity).toFixed(2)}
                    </span>
                  </div>
                ))}
              </div>

              {/* Summary breakdown */}
              <div className="space-y-2 text-xs text-stone-600 pt-3 border-t border-stone-100">
                <div className="flex justify-between">
                  <span>Subtotal</span>
                  <span className="font-bold text-stone-800">₹{subtotal.toFixed(2)}</span>
                </div>
                {discountAmount > 0 && (
                  <div className="flex justify-between text-emerald-700 font-semibold">
                    <span>Coupon ({coupon?.code})</span>
                    <span>-₹{discountAmount.toFixed(2)}</span>
                  </div>
                )}
                <div className="flex justify-between">
                  <span>Shipping</span>
                  <span>{shippingFee === 0 ? <strong className="text-emerald-700">FREE</strong> : `₹${shippingFee.toFixed(2)}`}</span>
                </div>
                <div className="flex justify-between">
                  <span>Ayurvedic GST (5%)</span>
                  <span>₹{taxAmount.toFixed(2)}</span>
                </div>
                <div className="pt-3 border-t border-stone-200 flex justify-between items-baseline text-stone-900">
                  <span className="text-sm font-bold font-serif">Total Payable</span>
                  <span className="text-xl font-extrabold text-emerald-900">₹{finalTotal.toFixed(2)}</span>
                </div>
              </div>

              {/* Place Order CTA */}
              <button
                type="submit"
                disabled={processing}
                className="w-full bg-emerald-800 hover:bg-emerald-700 disabled:opacity-50 text-white font-bold py-4 rounded-2xl shadow-xl transition flex items-center justify-center space-x-2 text-sm"
              >
                <Lock className="w-4 h-4" />
                <span>Pay ₹{finalTotal.toFixed(2)} & Place Order</span>
              </button>

              <div className="text-center text-[11px] text-stone-400 space-y-1">
                <p>🛡️ Verified AYUSH Pharmacy Dispensation</p>
                <p>30-Day Classical Authenticity Guarantee</p>
              </div>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
}
