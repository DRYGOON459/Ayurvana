import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Leaf, Search, ShieldCheck, AlertTriangle, ArrowRight } from 'lucide-react';
import herbService from '../../services/herbService';
import SearchBar from '../../components/common/SearchBar';
import Pagination from '../../components/common/Pagination';
import LoadingSpinner from '../../components/common/LoadingSpinner';
import Disclaimer from '../../components/common/Disclaimer';
import FavoriteButton from '../../components/common/FavoriteButton';

export default function HerbList() {
  const [herbs, setHerbs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchQuery, setSearchQuery] = useState('');
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  const fetchHerbs = (pageNum = 0, query = '') => {
    setLoading(true);
    const request = query.trim()
      ? herbService.searchHerbs(query.trim(), pageNum, 12)
      : herbService.getAllHerbs(pageNum, 12);

    request
      .then((res) => {
        setHerbs(res.data.content || []);
        setTotalPages(res.data.totalPages || 0);
        setPage(res.data.page || 0);
      })
      .catch((err) => console.error("Error loading herbs", err))
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    fetchHerbs(page, searchQuery);
  }, [page]);

  const handleSearchSubmit = (e) => {
    e?.preventDefault();
    setPage(0);
    fetchHerbs(0, searchQuery);
  };

  const handleClearSearch = () => {
    setSearchQuery('');
    setPage(0);
    fetchHerbs(0, '');
  };

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-10 space-y-10">
      {/* Header */}
      <div className="rounded-3xl bg-gradient-to-r from-emerald-900 via-ayur-900 to-emerald-950 text-white p-8 sm:p-12 shadow-xl relative overflow-hidden">
        <div className="max-w-2xl space-y-4 relative z-10">
          <span className="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-emerald-800/60 text-emerald-200 text-xs font-semibold uppercase tracking-wider">
            <Leaf className="w-3.5 h-3.5" />
            Botanical Pharmacology &amp; Dravyaguna
          </span>
          <h1 className="font-serif text-3xl sm:text-4xl lg:text-5xl font-bold tracking-tight">
            Medicinal Herb Encyclopedia
          </h1>
          <p className="text-sm sm:text-base text-emerald-100/90 leading-relaxed">
            Explore authentic monographs of classical Ayurvedic botanicals. Every entry details traditional pharmacology (Rasa, Virya, Vipaka), modern clinical summaries, toxicity alerts, and bibliographic citations.
          </p>
        </div>
      </div>

      {/* Search & Filter Bar */}
      <div className="flex flex-col sm:flex-row items-center justify-between gap-4">
        <form onSubmit={handleSearchSubmit} className="w-full sm:w-auto flex-1 max-w-md">
          <SearchBar
            value={searchQuery}
            onChange={setSearchQuery}
            onClear={handleClearSearch}
            placeholder="Search by common, Sanskrit, or scientific name..."
          />
        </form>
        <span className="text-xs font-semibold text-gray-500 uppercase tracking-wider">
          Evidence-Grounded Botanical Records
        </span>
      </div>

      {/* Grid of Herb Cards */}
      {loading ? (
        <LoadingSpinner message="Consulting Dravyaguna botanical database..." />
      ) : herbs.length === 0 ? (
        <div className="text-center py-20 bg-white rounded-3xl border border-gray-200 space-y-3">
          <Leaf className="w-12 h-12 text-gray-300 mx-auto" />
          <h3 className="font-serif text-xl font-bold text-gray-700">No herbs matched your search</h3>
          <p className="text-sm text-gray-500">Try searching with a different keyword like "Ashwagandha" or "Tulsi".</p>
          <button
            onClick={handleClearSearch}
            className="px-5 py-2 rounded-full bg-ayur-800 text-white text-xs font-semibold hover:bg-ayur-700"
          >
            Reset Search
          </button>
        </div>
      ) : (
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
          {herbs.map((herb) => (
            <Link
              key={herb.id}
              to={`/herbs/${herb.slug}`}
              className="group flex flex-col justify-between rounded-2xl bg-white border border-gray-200/80 shadow-sm hover:shadow-md hover:border-emerald-300 transition-all overflow-hidden"
            >
              <div>
                {/* Herb Image Banner */}
                <div className="h-48 w-full bg-gray-100 overflow-hidden relative">
                  {herb.imageUrl ? (
                    <img
                      src={herb.imageUrl}
                      alt={herb.commonName}
                      className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500"
                    />
                  ) : (
                    <div className="w-full h-full flex items-center justify-center bg-emerald-50 text-emerald-600">
                      <Leaf className="w-12 h-12 opacity-40" />
                    </div>
                  )}

                  {/* Favorite Button */}
                  <div className="absolute top-3 left-3 z-10">
                    <FavoriteButton itemType="HERB" itemId={herb.id} />
                  </div>

                  {/* Toxicity Badge */}
                  <div className="absolute top-3 right-3">
                    {herb.toxicityLevel === 'SAFE' ? (
                      <span className="inline-flex items-center gap-1 px-2.5 py-1 rounded-full bg-white/90 backdrop-blur-sm text-emerald-800 text-[11px] font-semibold shadow-sm">
                        <ShieldCheck className="w-3.5 h-3.5 text-emerald-600" />
                        <span>Safe</span>
                      </span>
                    ) : (
                      <span className="inline-flex items-center gap-1 px-2.5 py-1 rounded-full bg-amber-500/90 backdrop-blur-sm text-white text-[11px] font-semibold shadow-sm">
                        <AlertTriangle className="w-3.5 h-3.5" />
                        <span>Caution</span>
                      </span>
                    )}
                  </div>
                </div>

                {/* Content */}
                <div className="p-5 space-y-3">
                  <div>
                    <h3 className="font-serif text-lg font-bold text-gray-900 group-hover:text-emerald-800 transition-colors">
                      {herb.commonName}
                    </h3>
                    <p className="text-xs text-earth-700 font-serif italic">
                      {herb.sanskritName} • <span className="font-sans text-gray-500 not-italic">{herb.scientificName}</span>
                    </p>
                  </div>

                  {herb.primaryDoshaEffect && (
                    <p className="text-xs text-gray-600 line-clamp-2 bg-ayur-50/60 p-2.5 rounded-xl border border-ayur-100">
                      {herb.primaryDoshaEffect}
                    </p>
                  )}

                  {/* Rasa & Virya Tags */}
                  <div className="flex flex-wrap gap-1.5 pt-1 text-[11px]">
                    {herb.rasa && (
                      <span className="px-2 py-0.5 rounded-md bg-gray-100 text-gray-700">
                        {herb.rasa.split(',')[0]}
                      </span>
                    )}
                    {herb.virya && (
                      <span className="px-2 py-0.5 rounded-md bg-gray-100 text-gray-700">
                        {herb.virya}
                      </span>
                    )}
                  </div>
                </div>
              </div>

              {/* Card Footer */}
              <div className="px-5 py-3 border-t border-gray-100 bg-gray-50/50 flex items-center justify-between text-xs font-semibold text-emerald-800 group-hover:text-emerald-900">
                <span>View Full Monograph</span>
                <ArrowRight className="w-4 h-4 group-hover:translate-x-1 transition-transform" />
              </div>
            </Link>
          ))}
        </div>
      )}

      {/* Pagination */}
      <Pagination page={page} totalPages={totalPages} onPageChange={setPage} />

      {/* Safety Disclaimer */}
      <Disclaimer variant="general" />
    </div>
  );
}
