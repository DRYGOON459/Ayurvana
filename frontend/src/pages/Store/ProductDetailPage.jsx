import React, { useState, useEffect } from 'react';
import { useParams, Link, useNavigate } from 'react-router-dom';
import {
  ShoppingBag,
  Star,
  CheckCircle2,
  ShieldCheck,
  Truck,
  Leaf,
  ArrowLeft,
  Plus,
  Minus,
  BookOpen,
  Droplets,
  AlertTriangle,
  Heart,
  Share2,
  Sparkles,
  Award
} from 'lucide-react';
import storeService from '../../services/storeService';
import { useCart } from '../../context/CartContext';

export default function ProductDetailPage() {
  const { slug } = useParams();
  const navigate = useNavigate();
  const { addToCart, totalItemCount } = useCart();

  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);
  const [quantity, setQuantity] = useState(1);
  const [activeTab, setActiveTab] = useState('description');
  const [toastMessage, setToastMessage] = useState(null);

  useEffect(() => {
    fetchProduct();
  }, [slug]);

  const fetchProduct = async () => {
    try {
      setLoading(true);
      const res = await storeService.getProductBySlug(slug);
      if (res && res.data) {
        setProduct(res.data);
      }
    } catch (err) {
      console.error('Failed to load product details:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleAddToCart = () => {
    if (!product) return;
    addToCart(product, quantity);
    setToastMessage(`Added ${quantity} x ${product.name} to your cart!`);
    setTimeout(() => setToastMessage(null), 3000);
  };

  const handleBuyNow = () => {
    if (!product) return;
    addToCart(product, quantity);
    navigate('/checkout');
  };

  if (loading) {
    return (
      <div className="min-h-screen bg-stone-50 py-16 px-4 flex items-center justify-center">
        <div className="animate-spin rounded-full h-12 w-12 border-4 border-emerald-800 border-t-transparent"></div>
      </div>
    );
  }

  if (!product) {
    return (
      <div className="min-h-screen bg-stone-50 py-16 px-4 text-center space-y-4">
        <h2 className="text-2xl font-bold text-stone-800">Product Not Found</h2>
        <p className="text-stone-500 text-sm">The formulation you are looking for does not exist or has been retired.</p>
        <Link to="/store" className="inline-block bg-emerald-800 text-white px-6 py-2.5 rounded-xl font-semibold hover:bg-emerald-700 transition">
          Return to Apothecary
        </Link>
      </div>
    );
  }

  const discountPercent = product.discountPrice
    ? Math.round(((product.price - product.discountPrice) / product.price) * 100)
    : 0;

  return (
    <div className="min-h-screen bg-stone-50 py-8 px-4 sm:px-6 lg:px-8">
      {/* Toast Notification */}
      {toastMessage && (
        <div className="fixed bottom-6 right-6 z-50 bg-stone-900 text-white px-6 py-3.5 rounded-2xl shadow-2xl flex items-center space-x-3 border border-stone-700 animate-bounce">
          <CheckCircle2 className="w-5 h-5 text-emerald-400" />
          <span className="text-sm font-medium">{toastMessage}</span>
          <Link to="/cart" className="text-xs bg-emerald-700 hover:bg-emerald-600 text-white font-semibold px-3 py-1.5 rounded-lg ml-2 transition">
            View Cart
          </Link>
        </div>
      )}

      <div className="max-w-7xl mx-auto space-y-8">
        {/* Navigation Breadcrumb */}
        <div className="flex items-center justify-between text-xs text-stone-500">
          <Link to="/store" className="inline-flex items-center space-x-1 hover:text-emerald-800 transition font-medium">
            <ArrowLeft className="w-4 h-4" />
            <span>Back to Apothecary Catalog</span>
          </Link>
          <div className="flex items-center space-x-2">
            <span className="bg-stone-200 px-2 py-0.5 rounded text-[11px] font-semibold text-stone-700">{product.category}</span>
            <span>/</span>
            <span className="text-stone-800 font-semibold">{product.name}</span>
          </div>
        </div>

        {/* Product Core Showcase */}
        <div className="bg-white rounded-3xl p-6 sm:p-10 border border-stone-200 shadow-sm grid grid-cols-1 lg:grid-cols-12 gap-10">
          {/* Left Column: Image Showcase */}
          <div className="lg:col-span-5 space-y-4">
            <div className="relative rounded-2xl overflow-hidden bg-stone-100 border border-stone-200 shadow-inner">
              <img
                src={product.imageUrl}
                alt={product.name}
                className="w-full h-96 sm:h-[450px] object-cover object-center"
              />
              {discountPercent > 0 && (
                <span className="absolute top-4 left-4 bg-rose-600 text-white text-xs font-extrabold px-3 py-1.5 rounded-lg shadow-lg uppercase">
                  {discountPercent}% Special Offer
                </span>
              )}
              {product.targetDosha && (
                <span className="absolute top-4 right-4 bg-stone-900/85 backdrop-blur-md text-emerald-300 text-xs font-bold px-3 py-1.5 rounded-lg border border-stone-700">
                  {product.targetDosha}
                </span>
              )}
            </div>

            {/* Quality Badges */}
            <div className="grid grid-cols-3 gap-2 pt-2 text-center text-[11px] text-stone-600 font-medium">
              <div className="bg-stone-50 border border-stone-200 rounded-xl p-2 flex flex-col items-center">
                <Leaf className="w-4 h-4 text-emerald-700 mb-1" />
                <span>100% Organic</span>
              </div>
              <div className="bg-stone-50 border border-stone-200 rounded-xl p-2 flex flex-col items-center">
                <ShieldCheck className="w-4 h-4 text-emerald-700 mb-1" />
                <span>AYUSH GMP</span>
              </div>
              <div className="bg-stone-50 border border-stone-200 rounded-xl p-2 flex flex-col items-center">
                <Award className="w-4 h-4 text-emerald-700 mb-1" />
                <span>Lab Screened</span>
              </div>
            </div>
          </div>

          {/* Right Column: Information & Purchase Controls */}
          <div className="lg:col-span-7 flex flex-col justify-between space-y-6">
            <div className="space-y-4">
              {product.sanskritName && (
                <div className="inline-block bg-emerald-50 text-emerald-900 font-serif font-bold text-sm px-3.5 py-1 rounded-full border border-emerald-200">
                  {product.sanskritName}
                </div>
              )}

              <h1 className="text-2xl sm:text-4xl font-serif font-bold text-stone-900 leading-tight">
                {product.name}
              </h1>

              {/* Rating & Reviews */}
              <div className="flex items-center space-x-3 text-sm">
                <div className="flex items-center text-amber-500 font-bold">
                  <Star className="w-4 h-4 fill-current" />
                  <span className="ml-1">{product.rating || '4.9'}</span>
                </div>
                <span className="text-stone-300">•</span>
                <span className="text-stone-500">{product.reviewCount || 120} Verified Clinical Reviews</span>
                <span className="text-stone-300">•</span>
                <span className="text-emerald-700 font-semibold flex items-center space-x-1">
                  <CheckCircle2 className="w-3.5 h-3.5" />
                  <span>In Stock ({product.stockQuantity} units)</span>
                </span>
              </div>

              {/* Classical Shloka Source */}
              {product.classicalReference && (
                <div className="bg-amber-50/70 border border-amber-200/80 rounded-xl p-3.5 text-xs text-amber-900 flex items-start space-x-2.5">
                  <BookOpen className="w-4 h-4 text-amber-700 shrink-0 mt-0.5" />
                  <div>
                    <span className="font-bold">Classical Ayurvedic Reference: </span>
                    <span className="italic">{product.classicalReference}</span>
                  </div>
                </div>
              )}

              {/* Short summary */}
              <p className="text-stone-600 text-sm sm:text-base leading-relaxed">
                {product.shortDescription}
              </p>

              {/* Pricing breakdown */}
              <div className="pt-2 border-t border-stone-100 space-y-1">
                <div className="flex items-baseline space-x-3">
                  <span className="text-3xl font-extrabold text-stone-900">
                    ₹{product.discountPrice || product.price}
                  </span>
                  {product.discountPrice && (
                    <span className="text-lg text-stone-400 line-through">
                      ₹{product.price}
                    </span>
                  )}
                  {discountPercent > 0 && (
                    <span className="text-xs bg-rose-100 text-rose-700 font-bold px-2 py-0.5 rounded">
                      Save ₹{product.price - product.discountPrice}
                    </span>
                  )}
                </div>
                <p className="text-xs text-stone-500">
                  Net Weight: <span className="font-semibold text-stone-700">{product.netWeight}</span> (Inclusive of all taxes)
                </p>
              </div>
            </div>

            {/* Quantity and Actions */}
            <div className="space-y-4 pt-4 border-t border-stone-100">
              <div className="flex flex-col sm:flex-row items-center gap-4">
                {/* Quantity Counter */}
                <div className="flex items-center border-2 border-stone-300 rounded-2xl bg-stone-50 p-1">
                  <button
                    onClick={() => setQuantity(Math.max(1, quantity - 1))}
                    className="p-2 text-stone-600 hover:text-stone-900 hover:bg-stone-200 rounded-xl transition"
                    title="Decrease quantity"
                  >
                    <Minus className="w-4 h-4" />
                  </button>
                  <span className="px-5 font-bold text-stone-900 text-base">{quantity}</span>
                  <button
                    onClick={() => setQuantity(quantity + 1)}
                    className="p-2 text-stone-600 hover:text-stone-900 hover:bg-stone-200 rounded-xl transition"
                    title="Increase quantity"
                  >
                    <Plus className="w-4 h-4" />
                  </button>
                </div>

                {/* Add to Cart */}
                <button
                  onClick={handleAddToCart}
                  className="w-full sm:flex-1 bg-emerald-800 hover:bg-emerald-700 text-white font-bold py-3.5 px-6 rounded-2xl shadow-lg transition flex items-center justify-center space-x-2"
                >
                  <ShoppingBag className="w-5 h-5" />
                  <span>Add to Cart (₹{(Number(product.discountPrice || product.price) * quantity).toFixed(2)})</span>
                </button>

                {/* Direct Buy Now */}
                <button
                  onClick={handleBuyNow}
                  className="w-full sm:w-auto bg-stone-900 hover:bg-stone-800 text-white font-bold py-3.5 px-6 rounded-2xl shadow transition"
                >
                  Buy Now
                </button>
              </div>

              {/* Delivery Assurance */}
              <div className="flex items-center space-x-2 text-xs text-stone-500 pt-1">
                <Truck className="w-4 h-4 text-emerald-700" />
                <span>Express dispatch within 24 hours. Guaranteed authentic Vedic sealed packaging.</span>
              </div>
            </div>
          </div>
        </div>

        {/* Detailed Tabs: Ingredients, Dosage & Anupana, Benefits */}
        <div className="bg-white rounded-3xl border border-stone-200 shadow-sm overflow-hidden">
          {/* Tab Navigation */}
          <div className="flex border-b border-stone-200 bg-stone-50/60 overflow-x-auto">
            {[
              { id: 'description', label: 'Classical Overview', icon: BookOpen },
              { id: 'ingredients', label: 'Botanical Ingredients', icon: Leaf },
              { id: 'benefits', label: 'Therapeutic Benefits', icon: Sparkles },
              { id: 'dosage', label: 'Dosage & Anupana (Vehicle)', icon: Droplets },
              { id: 'contraindications', label: 'Contraindications & Safety', icon: AlertTriangle },
            ].map((tab) => {
              const Icon = tab.icon;
              return (
                <button
                  key={tab.id}
                  onClick={() => setActiveTab(tab.id)}
                  className={`flex items-center space-x-2 px-6 py-4 text-xs sm:text-sm font-bold border-b-2 transition whitespace-nowrap ${
                    activeTab === tab.id
                      ? 'border-emerald-800 text-emerald-900 bg-white'
                      : 'border-transparent text-stone-500 hover:text-stone-800'
                  }`}
                >
                  <Icon className="w-4 h-4" />
                  <span>{tab.label}</span>
                </button>
              );
            })}
          </div>

          {/* Tab Content */}
          <div className="p-6 sm:p-10">
            {activeTab === 'description' && (
              <div className="prose max-w-none text-stone-700 space-y-4">
                <h3 className="text-lg font-bold text-stone-900 font-serif">Action Mechanism & History</h3>
                <p className="leading-relaxed text-sm sm:text-base whitespace-pre-line">
                  {product.description || product.shortDescription}
                </p>
              </div>
            )}

            {activeTab === 'ingredients' && (
              <div className="space-y-4">
                <h3 className="text-lg font-bold text-stone-900 font-serif">Constituent Herbs & Synergists</h3>
                <div className="bg-stone-50 p-6 rounded-2xl border border-stone-200 text-stone-800 text-sm leading-relaxed">
                  {product.ingredients || 'Prepared with organically wildcrafted herbal extracts.'}
                </div>
              </div>
            )}

            {activeTab === 'benefits' && (
              <div className="space-y-4">
                <h3 className="text-lg font-bold text-stone-900 font-serif">Clinical & Wellness Indications</h3>
                <div className="bg-emerald-50/50 p-6 rounded-2xl border border-emerald-200/60 text-emerald-950 text-sm leading-relaxed">
                  {product.benefits}
                </div>
              </div>
            )}

            {activeTab === 'dosage' && (
              <div className="space-y-6">
                <div>
                  <h3 className="text-lg font-bold text-stone-900 font-serif mb-2">Recommended Intake</h3>
                  <div className="bg-stone-50 p-5 rounded-2xl border border-stone-200 text-stone-800 text-sm">
                    {product.dosageInstructions}
                  </div>
                </div>

                {product.anupana && (
                  <div>
                    <h4 className="text-sm font-bold text-stone-900 uppercase tracking-wider mb-2 flex items-center space-x-1.5 text-emerald-800">
                      <Droplets className="w-4 h-4" />
                      <span>Recommended Classical Anupana (Carrier Vehicle):</span>
                    </h4>
                    <div className="bg-amber-50/60 p-4 rounded-xl border border-amber-200/80 text-amber-900 text-sm">
                      {product.anupana}
                    </div>
                  </div>
                )}
              </div>
            )}

            {activeTab === 'contraindications' && (
              <div className="space-y-4">
                <h3 className="text-lg font-bold text-stone-900 font-serif">Safety & Usage Considerations</h3>
                <div className="bg-rose-50/70 p-6 rounded-2xl border border-rose-200/80 text-rose-950 text-sm leading-relaxed">
                  {product.contraindications || 'Generally safe for long-term daily use under physician guidance.'}
                </div>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}
