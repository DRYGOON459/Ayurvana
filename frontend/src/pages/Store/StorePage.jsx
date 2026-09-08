import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import {
  ShoppingBag,
  Search,
  Filter,
  Star,
  CheckCircle2,
  Sparkles,
  ShieldCheck,
  Truck,
  Leaf,
  ArrowRight,
  Plus,
  Flame,
  Wind,
  Droplets,
  Layers,
  ChevronRight
} from 'lucide-react';
import storeService from '../../services/storeService';
import { useCart } from '../../context/CartContext';

const CATEGORIES = [
  { id: 'ALL', label: 'All Apothecary' },
  { id: 'CHURNA', label: 'Churnas (Herbal Powders)' },
  { id: 'GUGGULU', label: 'Guggulu Formulations' },
  { id: 'ARISHTA_ASAVA', label: 'Arishta & Fermented Elixirs' },
  { id: 'TAILA_GHRITA', label: 'Medicated Oils & Ghee' },
  { id: 'HERBAL_TEA', label: 'Herbal Teas & Infusions' },
  { id: 'EXTRACT_CAPSULE', label: 'Rasayana Extracts & Resins' },
  { id: 'WELLNESS_KIT', label: 'Wellness Jams & Granules' },
];

const DOSHAS = [
  { id: 'ALL', label: 'All Doshas' },
  { id: 'VATA', label: 'Vata Balancing', color: 'bg-amber-100 text-amber-800' },
  { id: 'PITTA', label: 'Pitta Pacifying', color: 'bg-rose-100 text-rose-800' },
  { id: 'KAPHA', label: 'Kapha Stimulating', color: 'bg-blue-100 text-blue-800' },
  { id: 'TRIDOSHIC', label: 'Tridoshic (All Types)', color: 'bg-emerald-100 text-emerald-800' },
];

export default function StorePage() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedCategory, setSelectedCategory] = useState('ALL');
  const [selectedDosha, setSelectedDosha] = useState('ALL');
  const [searchQuery, setSearchQuery] = useState('');
  const [toastMessage, setToastMessage] = useState(null);

  const { addToCart, totalItemCount } = useCart();

  useEffect(() => {
    fetchProducts();
  }, [selectedCategory, selectedDosha]);

  const fetchProducts = async () => {
    try {
      setLoading(true);
      let res;
      if (selectedCategory !== 'ALL') {
        res = await storeService.getProductsByCategory(selectedCategory, { size: 30 });
      } else if (selectedDosha !== 'ALL') {
        res = await storeService.getProductsByDosha(selectedDosha, { size: 30 });
      } else {
        res = await storeService.getAllProducts({ size: 30 });
      }

      if (res && res.data && res.data.content) {
        setProducts(res.data.content);
      } else if (Array.isArray(res?.data)) {
        setProducts(res.data);
      } else {
        setProducts([]);
      }
    } catch (err) {
      console.error('Failed to load products:', err);
      setProducts([]);
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = async (e) => {
    e.preventDefault();
    if (!searchQuery.trim()) {
      fetchProducts();
      return;
    }
    try {
      setLoading(true);
      const res = await storeService.searchProducts(searchQuery.trim());
      if (res && res.data && res.data.content) {
        setProducts(res.data.content);
      } else {
        setProducts([]);
      }
    } catch (err) {
      console.error('Search failed:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleAddToCart = (product, e) => {
    e.preventDefault();
    e.stopPropagation();
    addToCart(product, 1);
    setToastMessage(`Added ${product.name} to cart!`);
    setTimeout(() => setToastMessage(null), 2500);
  };

  return (
    <div className="min-h-screen bg-stone-50 py-8 px-4 sm:px-6 lg:px-8">
      {/* Toast Notification */}
      {toastMessage && (
        <div className="fixed bottom-6 right-6 z-50 bg-stone-900 text-white px-5 py-3 rounded-xl shadow-2xl flex items-center space-x-3 border border-stone-700 animate-bounce">
          <CheckCircle2 className="w-5 h-5 text-emerald-400" />
          <span className="text-sm font-medium">{toastMessage}</span>
          <Link to="/cart" className="text-xs bg-emerald-700 hover:bg-emerald-600 text-white font-semibold px-2.5 py-1 rounded-lg ml-2 transition">
            View Cart
          </Link>
        </div>
      )}

      <div className="max-w-7xl mx-auto space-y-8">
        {/* Hero Section */}
        <div className="relative overflow-hidden rounded-3xl bg-gradient-to-r from-emerald-900 via-teal-950 to-stone-900 text-white shadow-2xl">
          <div className="absolute inset-0 opacity-20 bg-[radial-gradient(#a7f3d0_1px,transparent_1px)] [background-size:16px_16px]"></div>
          <div className="relative p-8 sm:p-12 lg:p-16 flex flex-col md:flex-row items-center justify-between gap-8">
            <div className="space-y-4 max-w-2xl">
              <div className="inline-flex items-center space-x-2 bg-emerald-500/20 text-emerald-300 px-3 py-1.5 rounded-full text-xs font-semibold tracking-wide uppercase border border-emerald-400/30">
                <Sparkles className="w-3.5 h-3.5" />
                <span>Certified Classical Formulations</span>
              </div>
              <h1 className="text-3xl sm:text-5xl font-serif font-bold text-stone-100 tracking-tight">
                Ayurvedic Apothecary & Pharmacy
              </h1>
              <p className="text-stone-300 text-base sm:text-lg leading-relaxed">
                Authentic, lab-tested Shloka formulations, organic single-origin herbs, classical Arishtas, and Rasayana elixirs prepared strictly according to Charaka and Sushruta Samhita.
              </p>
              <div className="flex flex-wrap gap-4 pt-2 text-xs font-medium text-stone-300">
                <div className="flex items-center space-x-1.5 bg-white/10 px-3 py-1.5 rounded-lg backdrop-blur-sm">
                  <ShieldCheck className="w-4 h-4 text-emerald-400" />
                  <span>GMP & AYUSH Standard</span>
                </div>
                <div className="flex items-center space-x-1.5 bg-white/10 px-3 py-1.5 rounded-lg backdrop-blur-sm">
                  <Leaf className="w-4 h-4 text-emerald-400" />
                  <span>100% Organically Wildcrafted</span>
                </div>
                <div className="flex items-center space-x-1.5 bg-white/10 px-3 py-1.5 rounded-lg backdrop-blur-sm">
                  <Truck className="w-4 h-4 text-emerald-400" />
                  <span>Free Shipping on Orders &gt; ₹500</span>
                </div>
              </div>
            </div>

            <div className="flex flex-col sm:flex-row gap-4 items-center">
              <Link
                to="/cart"
                className="relative flex items-center space-x-3 bg-emerald-600 hover:bg-emerald-500 text-white px-6 py-4 rounded-2xl font-bold shadow-lg transition-all duration-300 hover:scale-105"
              >
                <ShoppingBag className="w-6 h-6" />
                <span>Review Cart ({totalItemCount})</span>
                {totalItemCount > 0 && (
                  <span className="absolute -top-2 -right-2 bg-rose-500 text-white text-xs w-6 h-6 rounded-full flex items-center justify-center font-extrabold border-2 border-stone-900 shadow">
                    {totalItemCount}
                  </span>
                )}
              </Link>
            </div>
          </div>
        </div>

        {/* Search & Filter Controls */}
        <div className="bg-white p-6 rounded-2xl shadow-sm border border-stone-200 space-y-6">
          <form onSubmit={handleSearch} className="flex flex-col sm:flex-row gap-3">
            <div className="relative flex-1">
              <Search className="absolute left-4 top-3.5 w-5 h-5 text-stone-400" />
              <input
                type="text"
                placeholder="Search by herb, formulation (e.g., Triphala, Shilajit, Acidity, Joint Pain)..."
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                className="w-full pl-12 pr-4 py-3 bg-stone-50 rounded-xl border border-stone-300 focus:outline-none focus:ring-2 focus:ring-emerald-500 focus:bg-white text-stone-800 transition"
              />
            </div>
            <button
              type="submit"
              className="bg-emerald-800 hover:bg-emerald-700 text-white font-semibold px-6 py-3 rounded-xl transition duration-200 flex items-center justify-center space-x-2"
            >
              <span>Search Store</span>
            </button>
          </form>

          {/* Category Filter Tabs */}
          <div className="space-y-3">
            <div className="flex items-center space-x-2 text-xs font-bold text-stone-500 uppercase tracking-wider">
              <Filter className="w-3.5 h-3.5" />
              <span>Formulation Category:</span>
            </div>
            <div className="flex flex-wrap gap-2">
              {CATEGORIES.map((cat) => (
                <button
                  key={cat.id}
                  onClick={() => {
                    setSelectedCategory(cat.id);
                    setSelectedDosha('ALL');
                  }}
                  className={`px-3.5 py-1.5 rounded-lg text-xs font-medium transition ${
                    selectedCategory === cat.id
                      ? 'bg-emerald-800 text-white shadow-sm'
                      : 'bg-stone-100 hover:bg-stone-200 text-stone-700'
                  }`}
                >
                  {cat.label}
                </button>
              ))}
            </div>
          </div>

          {/* Dosha Filter Tabs */}
          <div className="space-y-3 pt-3 border-t border-stone-100">
            <div className="flex items-center space-x-2 text-xs font-bold text-stone-500 uppercase tracking-wider">
              <Sparkles className="w-3.5 h-3.5" />
              <span>Target Dosha Balance:</span>
            </div>
            <div className="flex flex-wrap gap-2">
              {DOSHAS.map((d) => (
                <button
                  key={d.id}
                  onClick={() => {
                    setSelectedDosha(d.id);
                    setSelectedCategory('ALL');
                  }}
                  className={`px-3.5 py-1.5 rounded-lg text-xs font-medium transition ${
                    selectedDosha === d.id
                      ? 'bg-emerald-800 text-white shadow-sm'
                      : 'bg-stone-100 hover:bg-stone-200 text-stone-700'
                  }`}
                >
                  {d.label}
                </button>
              ))}
            </div>
          </div>
        </div>

        {/* Products Grid */}
        {loading ? (
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
            {[1, 2, 3, 4, 5, 6, 7, 8].map((n) => (
              <div key={n} className="bg-white rounded-2xl p-4 border border-stone-200 shadow-sm animate-pulse space-y-4">
                <div className="h-48 bg-stone-200 rounded-xl"></div>
                <div className="h-4 bg-stone-200 rounded w-3/4"></div>
                <div className="h-3 bg-stone-200 rounded w-1/2"></div>
                <div className="h-8 bg-stone-200 rounded"></div>
              </div>
            ))}
          </div>
        ) : products.length === 0 ? (
          <div className="text-center py-16 bg-white rounded-2xl border border-stone-200 p-8 space-y-4">
            <ShoppingBag className="w-16 h-16 text-stone-300 mx-auto" />
            <h3 className="text-xl font-bold text-stone-800">No products found</h3>
            <p className="text-stone-500 text-sm max-w-md mx-auto">
              We couldn't find any products matching your current filters or search query. Try clearing filters.
            </p>
            <button
              onClick={() => {
                setSelectedCategory('ALL');
                setSelectedDosha('ALL');
                setSearchQuery('');
                fetchProducts();
              }}
              className="bg-emerald-800 text-white text-sm font-semibold px-5 py-2.5 rounded-xl hover:bg-emerald-700 transition"
            >
              Reset Filters
            </button>
          </div>
        ) : (
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
            {products.map((product) => {
              const discountPercent = product.discountPrice
                ? Math.round(((product.price - product.discountPrice) / product.price) * 100)
                : 0;

              return (
                <div
                  key={product.id}
                  className="group bg-white rounded-2xl border border-stone-200 hover:border-emerald-500/40 shadow-sm hover:shadow-xl transition-all duration-300 flex flex-col justify-between overflow-hidden"
                >
                  <Link to={`/store/product/${product.slug}`} className="block relative overflow-hidden bg-stone-100">
                    <img
                      src={product.imageUrl}
                      alt={product.name}
                      className="w-full h-52 object-cover object-center group-hover:scale-105 transition-transform duration-500"
                    />
                    {discountPercent > 0 && (
                      <span className="absolute top-3 left-3 bg-rose-600 text-white text-[10px] font-extrabold uppercase px-2 py-1 rounded-md shadow-md">
                        {discountPercent}% OFF
                      </span>
                    )}
                    {product.targetDosha && (
                      <span className="absolute top-3 right-3 bg-stone-900/80 backdrop-blur-md text-emerald-300 text-[10px] font-bold px-2 py-1 rounded-md border border-stone-700">
                        {product.targetDosha}
                      </span>
                    )}
                    <span className="absolute bottom-2 right-2 bg-white/90 backdrop-blur-sm text-stone-700 text-[11px] font-semibold px-2 py-0.5 rounded shadow-sm">
                      {product.netWeight}
                    </span>
                  </Link>

                  <div className="p-5 flex-1 flex flex-col justify-between space-y-4">
                    <div className="space-y-1.5">
                      {product.sanskritName && (
                        <p className="text-[11px] font-serif text-emerald-800 font-semibold tracking-wide">
                          {product.sanskritName}
                        </p>
                      )}
                      <Link to={`/store/product/${product.slug}`}>
                        <h3 className="text-base font-bold text-stone-900 group-hover:text-emerald-800 transition line-clamp-1">
                          {product.name}
                        </h3>
                      </Link>
                      <p className="text-xs text-stone-500 line-clamp-2 leading-relaxed">
                        {product.shortDescription}
                      </p>
                    </div>

                    <div className="space-y-3 pt-3 border-t border-stone-100">
                      {/* Rating & Stock */}
                      <div className="flex items-center justify-between text-xs">
                        <div className="flex items-center space-x-1 text-amber-500 font-bold">
                          <Star className="w-3.5 h-3.5 fill-current" />
                          <span>{product.rating || '4.8'}</span>
                          <span className="text-stone-400 font-normal">({product.reviewCount || 24})</span>
                        </div>
                        <span className="text-emerald-700 font-semibold flex items-center space-x-1 text-[11px]">
                          <CheckCircle2 className="w-3 h-3" />
                          <span>In Stock</span>
                        </span>
                      </div>

                      {/* Price and Add to Cart */}
                      <div className="flex items-center justify-between pt-1">
                        <div>
                          <div className="flex items-baseline space-x-2">
                            <span className="text-lg font-bold text-stone-900">
                              ₹{product.discountPrice || product.price}
                            </span>
                            {product.discountPrice && (
                              <span className="text-xs text-stone-400 line-through">
                                ₹{product.price}
                              </span>
                            )}
                          </div>
                        </div>

                        <button
                          onClick={(e) => handleAddToCart(product, e)}
                          className="bg-emerald-800 hover:bg-emerald-700 active:scale-95 text-white p-2.5 rounded-xl shadow transition duration-150 flex items-center justify-center group/btn"
                          title="Add to cart"
                        >
                          <Plus className="w-4 h-4 group-hover/btn:scale-125 transition-transform" />
                        </button>
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
