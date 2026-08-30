import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { BookOpen, Clock, User, ArrowRight } from 'lucide-react';
import articleService from '../../services/articleService';
import Pagination from '../../components/common/Pagination';
import LoadingSpinner from '../../components/common/LoadingSpinner';
import Disclaimer from '../../components/common/Disclaimer';
import FavoriteButton from '../../components/common/FavoriteButton';

export default function ArticleList() {
  const [articles, setArticles] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedCategory, setSelectedCategory] = useState('ALL');
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  const categories = ['ALL', 'Lifestyle', 'Research & Evidence', 'Nutrition', 'Yoga'];

  useEffect(() => {
    setLoading(true);
    const request = selectedCategory === 'ALL'
      ? articleService.getAllArticles(page, 12)
      : articleService.getArticlesByCategory(selectedCategory, page, 12);

    request
      .then((res) => {
        setArticles(res.data.content || []);
        setTotalPages(res.data.totalPages || 0);
        setPage(res.data.page || 0);
      })
      .catch((err) => console.error("Error loading articles", err))
      .finally(() => setLoading(false));
  }, [page, selectedCategory]);

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-10 space-y-10">
      {/* Header */}
      <div className="rounded-3xl bg-gradient-to-r from-earth-900 via-stone-900 to-earth-950 text-white p-8 sm:p-12 shadow-xl relative overflow-hidden">
        <div className="max-w-2xl space-y-4 relative z-10">
          <span className="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-earth-800/60 text-earth-200 text-xs font-semibold uppercase tracking-wider">
            <BookOpen className="w-3.5 h-3.5" />
            Knowledge &amp; Scientific Inquiries
          </span>
          <h1 className="font-serif text-3xl sm:text-4xl lg:text-5xl font-bold tracking-tight">
            Ayurveda &amp; Wellness Articles
          </h1>
          <p className="text-sm sm:text-base text-earth-100/90 leading-relaxed">
            Evidence-grounded health journalism bridging classical Ayurvedic texts with peer-reviewed pharmacological and circadian biological research.
          </p>
        </div>
      </div>

      {/* Category Tabs */}
      <div className="flex flex-wrap items-center gap-2 border-b border-gray-200 pb-4">
        {categories.map((cat) => (
          <button
            key={cat}
            onClick={() => { setSelectedCategory(cat); setPage(0); }}
            className={`px-4 py-1.5 rounded-full text-xs font-semibold transition-all ${
              selectedCategory === cat
                ? 'bg-earth-800 text-white shadow-sm'
                : 'bg-white text-gray-600 hover:bg-gray-50 border border-gray-200'
            }`}
          >
            {cat}
          </button>
        ))}
      </div>

      {/* Articles Grid */}
      {loading ? (
        <LoadingSpinner message="Loading curated wellness articles..." />
      ) : articles.length === 0 ? (
        <div className="text-center py-20 bg-white rounded-3xl border border-gray-200">
          <h3 className="font-serif text-lg font-bold text-gray-700">No articles found in this category</h3>
        </div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {articles.map((art) => (
            <Link
              key={art.id}
              to={`/articles/${art.slug}`}
              className="group flex flex-col justify-between rounded-2xl bg-white border border-gray-200/80 shadow-sm hover:shadow-md hover:border-earth-300 transition-all overflow-hidden"
            >
              <div>
                <div className="h-48 w-full bg-gray-100 overflow-hidden relative">
                  {art.coverImageUrl ? (
                    <img
                      src={art.coverImageUrl}
                      alt={art.title}
                      className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500"
                    />
                  ) : (
                    <div className="w-full h-full flex items-center justify-center text-earth-700 bg-earth-50">
                      <BookOpen className="w-12 h-12 opacity-30" />
                    </div>
                  )}
                  <span className="absolute top-3 left-3 px-2.5 py-1 rounded-full bg-white/90 backdrop-blur-sm text-gray-800 text-[11px] font-semibold">
                    {art.category}
                  </span>

                  <div className="absolute top-3 right-3 z-10">
                    <FavoriteButton itemType="ARTICLE" itemId={art.id} />
                  </div>
                </div>

                <div className="p-5 space-y-3">
                  <div className="flex items-center gap-3 text-xs text-gray-500">
                    <span className="flex items-center gap-1">
                      <User className="w-3.5 h-3.5" />
                      {art.authorName}
                    </span>
                    <span>•</span>
                    <span className="flex items-center gap-1">
                      <Clock className="w-3.5 h-3.5" />
                      {art.readTimeMinutes} min read
                    </span>
                  </div>

                  <h3 className="font-serif text-lg font-bold text-gray-900 group-hover:text-earth-800 transition-colors line-clamp-2">
                    {art.title}
                  </h3>

                  <p className="text-xs sm:text-sm text-gray-600 line-clamp-3 leading-relaxed">
                    {art.summary}
                  </p>
                </div>
              </div>

              <div className="px-5 py-3 border-t border-gray-100 bg-gray-50/50 flex items-center justify-between text-xs font-semibold text-earth-800 group-hover:text-earth-900">
                <span>Read Article</span>
                <ArrowRight className="w-4 h-4 group-hover:translate-x-1 transition-transform" />
              </div>
            </Link>
          ))}
        </div>
      )}

      <Pagination page={page} totalPages={totalPages} onPageChange={setPage} />
      <Disclaimer variant="general" />
    </div>
  );
}
