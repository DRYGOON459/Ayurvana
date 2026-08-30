import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Heart, Trash2, ArrowRight, Leaf, SunMedium, BookOpen, User, Sparkles } from 'lucide-react';
import favoriteService from '../../services/favoriteService';
import LoadingSpinner from '../../components/common/LoadingSpinner';
import Pagination from '../../components/common/Pagination';
import Disclaimer from '../../components/common/Disclaimer';

export default function FavoritesDashboard() {
  const [favorites, setFavorites] = useState([]);
  const [loading, setLoading] = useState(true);
  const [activeTab, setActiveTab] = useState('ALL');
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  const fetchFavorites = () => {
    setLoading(true);
    favoriteService.getFavorites(activeTab, page, 12)
      .then((res) => {
        setFavorites(res.data.content || []);
        setTotalPages(res.data.totalPages || 0);
        setPage(res.data.page || 0);
      })
      .catch((err) => console.error("Error loading favorites", err))
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    fetchFavorites();
  }, [activeTab, page]);

  const handleRemove = async (itemType, itemId) => {
    try {
      await favoriteService.removeFavorite(itemType, itemId);
      setFavorites((prev) => prev.filter((item) => !(item.itemType === itemType && item.itemId === itemId)));
    } catch (err) {
      console.error("Failed to remove favorite", err);
    }
  };

  const tabs = [
    { key: 'ALL', label: 'All Saved' },
    { key: 'HERB', label: 'Herbs & Botanicals' },
    { key: 'YOGA', label: 'Yoga Postures' },
    { key: 'ARTICLE', label: 'Articles & Evidence' },
    { key: 'DOCTOR', label: 'Doctors' },
  ];

  const getTargetUrl = (item) => {
    switch (item.itemType) {
      case 'HERB': return `/herbs/${item.slug}`;
      case 'YOGA': return `/yoga/${item.slug}`;
      case 'ARTICLE': return `/articles/${item.slug}`;
      case 'DOCTOR': return `/doctors/${item.slug}`;
      default: return '#';
    }
  };

  const getIcon = (type) => {
    switch (type) {
      case 'HERB': return <Leaf className="w-4 h-4 text-emerald-600" />;
      case 'YOGA': return <SunMedium className="w-4 h-4 text-amber-600" />;
      case 'ARTICLE': return <BookOpen className="w-4 h-4 text-purple-600" />;
      case 'DOCTOR': return <User className="w-4 h-4 text-blue-600" />;
      default: return <Sparkles className="w-4 h-4 text-gray-500" />;
    }
  };

  return (
    <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-10 space-y-8">
      {/* Header */}
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 border-b border-gray-200 pb-6">
        <div>
          <div className="flex items-center gap-2 text-xs font-semibold uppercase tracking-wider text-rose-700">
            <Heart className="w-4 h-4 fill-rose-600" />
            <span>Personal Collection</span>
          </div>
          <h1 className="font-serif text-3xl sm:text-4xl font-bold text-gray-950 mt-1">
            Saved Favorites &amp; Wellness Library
          </h1>
          <p className="text-sm text-gray-600 mt-1">
            Your bookmarked botanical monographs, yoga asanas, and wellness research.
          </p>
        </div>
      </div>

      {/* Tabs */}
      <div className="flex flex-wrap items-center gap-2 border-b border-gray-200 pb-4">
        {tabs.map((t) => (
          <button
            key={t.key}
            onClick={() => { setActiveTab(t.key); setPage(0); }}
            className={`px-4 py-1.5 rounded-full text-xs font-semibold transition-all ${
              activeTab === t.key
                ? 'bg-rose-700 text-white shadow-sm'
                : 'bg-white text-gray-600 hover:bg-gray-50 border border-gray-200'
            }`}
          >
            {t.label}
          </button>
        ))}
      </div>

      {/* Grid or Empty */}
      {loading ? (
        <LoadingSpinner message="Retrieving your saved wellness items..." />
      ) : favorites.length === 0 ? (
        <div className="text-center py-20 bg-white rounded-3xl border border-gray-200 space-y-4">
          <div className="w-14 h-14 rounded-full bg-rose-50 text-rose-600 mx-auto flex items-center justify-center">
            <Heart className="w-7 h-7" />
          </div>
          <h3 className="font-serif text-xl font-bold text-gray-800">
            No saved favorites in this category yet
          </h3>
          <p className="text-xs sm:text-sm text-gray-500 max-w-sm mx-auto">
            Click the heart icon on any herb monograph, yoga posture, or clinical article to bookmark it here for quick reference.
          </p>
          <div className="pt-2 flex items-center justify-center gap-3">
            <Link to="/herbs" className="px-4 py-2 rounded-full bg-ayur-800 text-white text-xs font-semibold">
              Browse Herbs
            </Link>
            <Link to="/yoga" className="px-4 py-2 rounded-full bg-gray-100 text-gray-700 hover:bg-gray-200 text-xs font-semibold">
              Explore Yoga
            </Link>
          </div>
        </div>
      ) : (
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
          {favorites.map((fav) => (
            <div
              key={fav.id}
              className="group rounded-3xl bg-white border border-gray-200/80 shadow-sm hover:shadow-md hover:border-rose-200 transition-all flex flex-col justify-between overflow-hidden"
            >
              <div>
                <div className="h-44 w-full bg-gray-100 relative overflow-hidden">
                  {fav.imageUrl ? (
                    <img src={fav.imageUrl} alt={fav.title} className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500" />
                  ) : (
                    <div className="w-full h-full flex items-center justify-center bg-gray-50 text-gray-400">
                      {getIcon(fav.itemType)}
                    </div>
                  )}

                  <span className="absolute top-3 left-3 px-2.5 py-1 rounded-full bg-white/95 backdrop-blur-sm text-[10px] font-bold text-gray-700 uppercase flex items-center gap-1 shadow-sm">
                    {getIcon(fav.itemType)}
                    <span>{fav.itemType}</span>
                  </span>

                  <button
                    onClick={() => handleRemove(fav.itemType, fav.itemId)}
                    aria-label="Remove from favorites"
                    className="absolute top-3 right-3 p-2 rounded-full bg-white/90 hover:bg-rose-50 text-gray-400 hover:text-rose-600 transition-colors shadow-sm"
                  >
                    <Trash2 className="w-4 h-4" />
                  </button>
                </div>

                <div className="p-5 space-y-2">
                  <h3 className="font-serif text-lg font-bold text-gray-900 group-hover:text-rose-900 transition-colors line-clamp-1">
                    {fav.title}
                  </h3>

                  {fav.subtitle && (
                    <p className="text-xs text-earth-800 font-serif italic line-clamp-1">
                      {fav.subtitle}
                    </p>
                  )}
                </div>
              </div>

              <div className="p-5 pt-0">
                <Link
                  to={getTargetUrl(fav)}
                  className="w-full py-2.5 rounded-xl bg-gray-50 hover:bg-rose-50 text-gray-700 hover:text-rose-700 text-xs font-semibold transition-colors flex items-center justify-center gap-1.5"
                >
                  <span>View Details</span>
                  <ArrowRight className="w-3.5 h-3.5" />
                </Link>
              </div>
            </div>
          ))}
        </div>
      )}

      <Pagination page={page} totalPages={totalPages} onPageChange={setPage} />
      <Disclaimer variant="general" />
    </div>
  );
}
