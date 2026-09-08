import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import {
  Package,
  Truck,
  CheckCircle2,
  Clock,
  Printer,
  ChevronRight,
  ShoppingBag,
  ExternalLink,
  MapPin,
  Calendar,
  AlertCircle
} from 'lucide-react';
import storeService from '../../services/storeService';
import { useAuth } from '../../context/AuthContext';

export default function MyOrders() {
  const { user, isAuthenticated } = useAuth();
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (isAuthenticated) {
      fetchOrders();
    }
  }, [isAuthenticated]);

  const fetchOrders = async () => {
    try {
      setLoading(true);
      const res = await storeService.getMyOrders({ size: 20 });
      if (res && res.data && res.data.content) {
        setOrders(res.data.content);
      } else if (Array.isArray(res?.data)) {
        setOrders(res.data);
      } else {
        setOrders([]);
      }
    } catch (err) {
      console.error('Failed to load orders:', err);
      setError('Failed to retrieve your order history.');
    } finally {
      setLoading(false);
    }
  };

  if (!isAuthenticated) {
    return (
      <div className="min-h-screen bg-stone-50 py-16 px-4 flex items-center justify-center">
        <div className="bg-white p-8 rounded-3xl border border-stone-200 shadow-sm max-w-md w-full text-center space-y-4">
          <Package className="w-12 h-12 text-stone-300 mx-auto" />
          <h2 className="text-xl font-bold text-stone-900">Sign in to View Orders</h2>
          <p className="text-stone-500 text-xs">Please log in to your AyurVana account to track your shipments.</p>
          <Link to="/login?redirect=/orders" className="inline-block bg-emerald-800 text-white font-bold text-xs px-5 py-2.5 rounded-xl hover:bg-emerald-700 transition">
            Sign In
          </Link>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-stone-50 py-8 px-4 sm:px-6 lg:px-8">
      <div className="max-w-6xl mx-auto space-y-8">
        {/* Header */}
        <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4">
          <div>
            <div className="inline-flex items-center space-x-2 text-xs font-semibold text-emerald-800 uppercase tracking-wider mb-1">
              <Package className="w-4 h-4" />
              <span>Apothecary Purchases & Deliveries</span>
            </div>
            <h1 className="text-2xl sm:text-3xl font-serif font-bold text-stone-900">
              My Orders & Pharmacy Shipments
            </h1>
          </div>
          <Link
            to="/store"
            className="inline-flex items-center space-x-2 bg-emerald-800 hover:bg-emerald-700 text-white font-bold text-xs px-5 py-2.5 rounded-xl shadow transition"
          >
            <ShoppingBag className="w-4 h-4" />
            <span>Browse Apothecary Store</span>
          </Link>
        </div>

        {loading ? (
          <div className="space-y-4">
            {[1, 2, 3].map((n) => (
              <div key={n} className="bg-white rounded-3xl p-6 border border-stone-200 shadow-sm animate-pulse space-y-4">
                <div className="h-5 bg-stone-200 rounded w-1/4"></div>
                <div className="h-20 bg-stone-100 rounded-2xl"></div>
                <div className="h-4 bg-stone-200 rounded w-1/2"></div>
              </div>
            ))}
          </div>
        ) : orders.length === 0 ? (
          <div className="text-center py-16 bg-white rounded-3xl border border-stone-200 p-8 space-y-4">
            <Package className="w-16 h-16 text-stone-300 mx-auto" />
            <h3 className="text-xl font-bold text-stone-800">No Orders Placed Yet</h3>
            <p className="text-stone-500 text-sm max-w-md mx-auto">
              You haven't ordered any authentic classical remedies or herbal extracts yet. Visit our store to find doctor-formulated natural wellness remedies.
            </p>
            <Link
              to="/store"
              className="inline-block bg-emerald-800 text-white text-sm font-semibold px-6 py-3 rounded-xl hover:bg-emerald-700 transition shadow"
            >
              Explore Ayurvedic Store
            </Link>
          </div>
        ) : (
          <div className="space-y-6">
            {orders.map((order) => {
              const orderDate = new Date(order.createdAt).toLocaleDateString('en-IN', {
                year: 'numeric',
                month: 'short',
                day: 'numeric',
                hour: '2-digit',
                minute: '2-digit'
              });

              return (
                <div
                  key={order.id}
                  className="bg-white rounded-3xl border border-stone-200 shadow-sm overflow-hidden divide-y divide-stone-100 transition hover:border-emerald-500/30 hover:shadow-md"
                >
                  {/* Order Header Summary */}
                  <div className="p-6 bg-stone-50/70 flex flex-wrap items-center justify-between gap-4 text-xs">
                    <div className="flex flex-wrap items-center gap-4">
                      <div>
                        <span className="text-stone-400 block mb-0.5">Order Placed</span>
                        <span className="font-bold text-stone-800 flex items-center space-x-1">
                          <Calendar className="w-3.5 h-3.5 text-stone-400" />
                          <span>{orderDate}</span>
                        </span>
                      </div>
                      <div>
                        <span className="text-stone-400 block mb-0.5">Order Number</span>
                        <span className="font-extrabold text-stone-900 font-mono">{order.orderNumber}</span>
                      </div>
                      <div>
                        <span className="text-stone-400 block mb-0.5">Tracking Number</span>
                        <span className="font-extrabold text-emerald-800 font-mono">{order.trackingNumber}</span>
                      </div>
                      <div>
                        <span className="text-stone-400 block mb-0.5">Total Amount</span>
                        <span className="font-extrabold text-stone-900">₹{order.totalAmount?.toFixed(2)}</span>
                      </div>
                    </div>

                    <div className="flex items-center space-x-3">
                      <span className={`px-2.5 py-1 rounded-full text-[11px] font-extrabold uppercase ${
                        order.orderStatus === 'DELIVERED'
                          ? 'bg-emerald-100 text-emerald-800 border border-emerald-300'
                          : order.orderStatus === 'CONFIRMED' || order.orderStatus === 'PROCESSING'
                          ? 'bg-amber-100 text-amber-800 border border-amber-300'
                          : 'bg-stone-200 text-stone-700'
                      }`}>
                        {order.orderStatus}
                      </span>
                      <button
                        onClick={() => window.print()}
                        className="inline-flex items-center space-x-1.5 text-stone-600 hover:text-stone-900 bg-white border border-stone-300 px-3 py-1.5 rounded-lg transition"
                      >
                        <Printer className="w-3.5 h-3.5" />
                        <span>Tax Invoice</span>
                      </button>
                    </div>
                  </div>

                  {/* Order Items */}
                  <div className="p-6 space-y-4">
                    <div className="divide-y divide-stone-100">
                      {order.items?.map((item) => (
                        <div key={item.id} className="py-3 first:pt-0 last:pb-0 flex items-center justify-between text-xs sm:text-sm">
                          <div className="flex items-center space-x-3">
                            <img
                              src={item.productImageUrl}
                              alt={item.productName}
                              className="w-14 h-14 rounded-xl object-cover border border-stone-200"
                            />
                            <div>
                              <p className="font-bold text-stone-900">{item.productName}</p>
                              <p className="text-xs text-stone-400">Qty: {item.quantity} × ₹{item.unitPrice?.toFixed(2)}</p>
                            </div>
                          </div>
                          <span className="font-bold text-stone-900">
                            ₹{item.totalPrice?.toFixed(2)}
                          </span>
                        </div>
                      ))}
                    </div>

                    {/* Delivery & Timeline footer */}
                    <div className="pt-4 border-t border-stone-100 flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4 text-xs text-stone-500">
                      <div className="flex items-center space-x-2">
                        <MapPin className="w-4 h-4 text-emerald-700 shrink-0" />
                        <span>
                          Delivering to: <strong className="text-stone-800">{order.recipientName}</strong>, {order.streetAddress}, {order.city} - {order.pincode} (Ph: {order.phoneNumber})
                        </span>
                      </div>
                      <div className="flex items-center space-x-1.5 text-emerald-800 font-semibold">
                        <CheckCircle2 className="w-4 h-4 text-emerald-600" />
                        <span>Payment Method: {order.paymentMethod}</span>
                      </div>
                    </div>
                  </div>
                </div>
              );
            })}
          </div>
        )}
      </div>
    </div>
  );
}
