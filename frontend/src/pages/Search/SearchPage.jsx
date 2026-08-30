import React, { useState, useEffect } from 'react';
import { Link, useSearchParams } from 'react-router-dom';
import { Search, Leaf, SunMedium, Sparkles, BookOpen, ArrowRight, X } from 'lucide-react';
import useDebounce from '../../hooks/useDebounce';
import searchService from '../../services/searchService';
import LoadingSpinner from '../../components/common/LoadingSpinner';
import Disclaimer from '../../components/common/Disclaimer';

export default function SearchPage() {
  const [searchParams, setSearchParams] = useSearchParams();
  const initialQuery = searchParams.get('q') || '';

  const [query, setQuery] = useState(initialQuery);
  const debouncedQuery = useDebounce(query, 300);

  const [results, setResults] = useState(null);
  const [loading, setLoading] = useState(false);
  const [activeTab, setActiveTab] = useState('ALL');

  useEffect(() => {
    if (!debouncedQuery.trim()) {
      setResults(null);
      setLoading(false);
      return;
    }

    setLoading(true);
    setSearchParams({ q: debouncedQuery });

    searchService.globalSearch(debouncedQuery, 8)
      .then((res) => setResults(res.data))
      .catch((err) => console.error("Search error", err))
      .finally(() => setLoading(false));
  }, [debouncedQuery]);

  const quickSearches = ["Ashwagandha", "Tulsi", "Vata", "Dinacharya", "Cobra", "Pranayama", "Stress"];

  const getFilteredItems = () => {
    if (!results) return [];
    if (activeTab === 'HERBS') return results.herbs || [];
    if (activeTab === 'YOGA') return results.yoga || [];
    if (activeTab === 'AYURVEDA') return results.ayurveda || [];
    if (activeTab === 'ARTICLES') return results.articles || [];

    // 'ALL' merges all categorized results
    return [
      ...(results.herbs || []),
      ...(results.yoga || []),
      ...(results.ayurveda || []),
      ...(results.articles || []),
    ];
  };

  const filteredItems = getFilteredItems();

  const getIconForType = (type) => {
    switch (type) {
      case 'HERB': return <Leaf className="w-4 h-4 text-emerald-600" />;
      case 'YOGA': return <SunMedium className="w-4 h-4 text-amber-600" />;
      case 'AYURVEDA': return <Sparkles className="w-4 h-4 text-sky-600" />;
      case 'ARTICLE': return <BookOpen className="w-4 h-4 text-purple-600" />;
      default: return <Search className="w-4 h-4 text-gray-500" />;
    }
  };

  const getUrlForType = (type, slug) => {
    switch (type) {
      case 'HERB': return `/herbs/${slug}`;
      case 'YOGA': return `/yoga/${slug}`;
      case 'AYURVEDA': return `/ayurveda/topics/${slug}`;
      case 'ARTICLE': return `/articles/${slug}`;
      default: return '#';
    }
  };

  return (
    <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-10 space-y-8">
      {/* Header */}
      <div className="text-center max-w-2xl mx-auto space-y-4">
        <h1 className="font-serif text-3xl sm:text-4xl font-bold text-ayur-950">
          Universal Ayurvedic Search
        </h1>
        <p className="text-sm text-gray-600">
          Find botanical remedies, yoga asanas, philosophical principles, and clinical articles instantly across the AyurVana knowledge graph.
        </p>

        {/* Search Input Bar */}
        <div className="relative max-w-xl mx-auto mt-4">
          <Search className="w-5 h-5 text-gray-400 absolute left-4 top-1/2 -translate-y-1/2" />
          <input
            type="text"
            value={query}
            onChange={(e) => setQuery(e.target.value)}
            placeholder="Search herbs, symptoms, doshas, postures, or articles..."
            className="w-full pl-12 pr-12 py-3.5 rounded-full border border-gray-300 bg-white text-base shadow-sm focus:outline-none focus:ring-2 focus:ring-ayur-500 focus:border-transparent transition-all"
          />
          {query && (
            <button
              onClick={() => { setQuery(''); setResults(null); }}
              aria-label="Clear search"
              className="absolute right-4 top-1/2 -translate-y-1/2 p-1 text-gray-400 hover:text-gray-600 rounded-full"
            >
              <X className="w-4 h-4" />
            </button>
          )}
        </div>

        {/* Quick Search Chips */}
        <div className="flex flex-wrap items-center justify-center gap-2 pt-2">
          <span className="text-xs font-semibold text-gray-500">Suggested:</span>
          {quickSearches.map((term) => (
            <button
              key={term}
              onClick={() => setQuery(term)}
              className="px-3 py-1 rounded-full text-xs font-medium bg-ayur-50 text-ayur-800 border border-ayur-200 hover:bg-ayur-100 transition-colors"
            >
              {term}
            </button>
          ))}
        </div>
      </div>

      {/* Tabs */}
      {results && results.totalMatches > 0 && (
        <div className="flex flex-wrap items-center justify-center gap-2 border-b border-gray-200 pb-4">
          {[
            { key: 'ALL', label: `All Results (${results.totalMatches})` },
            { key: 'HERBS', label: `Herbs (${results.herbs.length})` },
            { key: 'YOGA', label: `Yoga Poses (${results.yoga.length})` },
            { key: 'AYURVEDA', label: `Ayurveda Topics (${results.ayurveda.length})` },
            { key: 'ARTICLES', label: `Articles (${results.articles.length})` },
          ].map((tab) => (
            <button
              key={tab.key}
              onClick={() => setActiveTab(tab.key)}
              className={`px-4 py-1.5 rounded-full text-xs font-semibold transition-all ${
                activeTab === tab.key
                  ? 'bg-ayur-800 text-white shadow-sm'
                  : 'bg-white text-gray-600 hover:bg-gray-100 border border-gray-200'
              }`}
            >
              {tab.label}
            </button>
          ))}
        </div>
      )}

      {/* Results Section */}
      {loading ? (
        <LoadingSpinner message="Searching AyurVana knowledge base..." />
      ) : query && results && results.totalMatches === 0 ? (
        <div className="text-center py-16 bg-white rounded-3xl border border-gray-200 space-y-2">
          <h3 className="font-serif text-xl font-bold text-gray-700">
            No matches found for "{query}"
          </h3>
          <p className="text-sm text-gray-500">
            Try checking spelling or searching for a general topic like "Pitta", "Neem", or "Pranayama".
          </p>
        </div>
      ) : filteredItems.length > 0 ? (
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          {filteredItems.map((item) => (
            <Link
              key={item.id}
              to={getUrlForType(item.type, item.slug)}
              className="group p-5 rounded-2xl bg-white border border-gray-200 shadow-sm hover:shadow-md hover:border-ayur-400 transition-all flex flex-col justify-between"
            >
              <div className="space-y-2">
                <div className="flex items-center justify-between text-xs">
                  <span className="inline-flex items-center gap-1.5 font-bold uppercase tracking-wider text-gray-700">
                    {getIconForType(item.type)}
                    <span>{item.type}</span>
                  </span>
                  {item.category && (
                    <span className="px-2 py-0.5 rounded text-[11px] bg-gray-100 text-gray-600">
                      {item.category}
                    </span>
                  )}
                </div>

                <h3 className="font-serif text-lg font-bold text-gray-900 group-hover:text-ayur-800 transition-colors">
                  {item.title}
                </h3>

                {item.subtitle && (
                  <p className="text-xs text-earth-700 font-serif italic">
                    {item.subtitle}
                  </p>
                )}

                {item.snippet && (
                  <p className="text-xs text-gray-600 line-clamp-2 leading-relaxed pt-1">
                    {item.snippet}
                  </p>
                )}
              </div>

              <div className="pt-4 mt-3 border-t border-gray-100 flex items-center justify-between text-xs font-semibold text-ayur-700 group-hover:text-ayur-900">
                <span>View Details</span>
                <ArrowRight className="w-4 h-4 group-hover:translate-x-1 transition-transform" />
              </div>
            </Link>
          ))}
        </div>
      ) : null}

      <Disclaimer variant="general" />
    </div>
  );
}
