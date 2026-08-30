import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { SunMedium, ArrowRight, Activity, Clock } from 'lucide-react';
import yogaService from '../../services/yogaService';
import Pagination from '../../components/common/Pagination';
import LoadingSpinner from '../../components/common/LoadingSpinner';
import Disclaimer from '../../components/common/Disclaimer';
import FavoriteButton from '../../components/common/FavoriteButton';

export default function YogaList() {
  const [poses, setPoses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedDifficulty, setSelectedDifficulty] = useState('ALL');
  const [selectedCategory, setSelectedCategory] = useState('ALL');
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  const categories = ['ALL', 'Standing', 'Balance', 'Backbend', 'Inversion', 'Restorative'];
  const difficulties = ['ALL', 'BEGINNER', 'INTERMEDIATE', 'ADVANCED'];

  useEffect(() => {
    setLoading(true);
    let request;
    if (selectedDifficulty !== 'ALL') {
      request = yogaService.getPosesByDifficulty(selectedDifficulty, page, 12);
    } else if (selectedCategory !== 'ALL') {
      request = yogaService.getPosesByCategory(selectedCategory, page, 12);
    } else {
      request = yogaService.getAllPoses(page, 12);
    }

    request
      .then((res) => {
        setPoses(res.data.content || []);
        setTotalPages(res.data.totalPages || 0);
        setPage(res.data.page || 0);
      })
      .catch((err) => console.error("Error loading yoga poses", err))
      .finally(() => setLoading(false));
  }, [page, selectedDifficulty, selectedCategory]);

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-10 space-y-10">
      {/* Hero Header */}
      <div className="rounded-3xl bg-gradient-to-r from-amber-900 via-earth-900 to-amber-950 text-white p-8 sm:p-12 shadow-xl relative overflow-hidden">
        <div className="max-w-2xl space-y-4 relative z-10">
          <span className="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-amber-800/60 text-amber-200 text-xs font-semibold uppercase tracking-wider">
            <SunMedium className="w-3.5 h-3.5" />
            Asana &amp; Bioenergetics
          </span>
          <h1 className="font-serif text-3xl sm:text-4xl lg:text-5xl font-bold tracking-tight">
            Yoga &amp; Asana Sanctuary
          </h1>
          <p className="text-sm sm:text-base text-amber-100/90 leading-relaxed">
            Cultivate stability, flexibility, and pranic flow. Discover classical asanas cataloged by difficulty, physical alignment cues, Ayurvedic Dosha balancing effects, and precautions.
          </p>
        </div>
      </div>

      {/* Filter Tabs */}
      <div className="flex flex-col md:flex-row items-start md:items-center justify-between gap-4 border-b border-gray-200 pb-4">
        {/* Category Filters */}
        <div className="flex flex-wrap items-center gap-2">
          {categories.map((cat) => (
            <button
              key={cat}
              onClick={() => { setSelectedCategory(cat); setSelectedDifficulty('ALL'); setPage(0); }}
              className={`px-4 py-1.5 rounded-full text-xs font-semibold transition-all ${
                selectedCategory === cat && selectedDifficulty === 'ALL'
                  ? 'bg-earth-800 text-white shadow-sm'
                  : 'bg-white text-gray-600 hover:bg-gray-50 border border-gray-200'
              }`}
            >
              {cat}
            </button>
          ))}
        </div>

        {/* Difficulty Filter */}
        <div className="flex items-center gap-2">
          <span className="text-xs text-gray-500 font-semibold uppercase">Level:</span>
          {difficulties.map((diff) => (
            <button
              key={diff}
              onClick={() => { setSelectedDifficulty(diff); setSelectedCategory('ALL'); setPage(0); }}
              className={`px-3 py-1 rounded-full text-[11px] font-semibold transition-all ${
                selectedDifficulty === diff
                  ? 'bg-amber-600 text-white'
                  : 'bg-gray-100 text-gray-600 hover:bg-gray-200'
              }`}
            >
              {diff}
            </button>
          ))}
        </div>
      </div>

      {/* Poses Grid */}
      {loading ? (
        <LoadingSpinner message="Consulting classical Yoga Asana library..." />
      ) : poses.length === 0 ? (
        <div className="text-center py-20 bg-white rounded-3xl border border-gray-200">
          <h3 className="font-serif text-lg font-bold text-gray-700">No poses found for this filter</h3>
        </div>
      ) : (
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
          {poses.map((pose) => (
            <Link
              key={pose.id}
              to={`/yoga/${pose.slug}`}
              className="group flex flex-col justify-between rounded-2xl bg-white border border-gray-200/80 shadow-sm hover:shadow-md hover:border-amber-300 transition-all overflow-hidden"
            >
              <div>
                {/* Image */}
                <div className="h-48 w-full bg-gray-100 overflow-hidden relative">
                  {pose.imageUrl ? (
                    <img
                      src={pose.imageUrl}
                      alt={pose.englishName}
                      className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500"
                    />
                  ) : (
                    <div className="w-full h-full flex items-center justify-center bg-amber-50 text-amber-700">
                      <SunMedium className="w-12 h-12 opacity-30" />
                    </div>
                  )}

                  <div className="absolute top-3 left-3 flex gap-2">
                    <span className="px-2.5 py-1 rounded-full bg-white/90 backdrop-blur-sm text-gray-800 text-[11px] font-semibold">
                      {pose.category}
                    </span>
                    <span className="px-2.5 py-1 rounded-full bg-amber-500 text-white text-[11px] font-semibold">
                      {pose.difficulty}
                    </span>
                  </div>

                  <div className="absolute top-3 right-3 z-10">
                    <FavoriteButton itemType="YOGA" itemId={pose.id} />
                  </div>
                </div>

                {/* Content */}
                <div className="p-5 space-y-3">
                  <div>
                    <h3 className="font-serif text-lg font-bold text-gray-900 group-hover:text-amber-800 transition-colors">
                      {pose.englishName}
                    </h3>
                    <p className="text-xs font-serif italic text-earth-700">
                      {pose.sanskritName}
                    </p>
                  </div>

                  {pose.doshaEffect && (
                    <p className="text-xs text-gray-600 line-clamp-2 bg-amber-50/60 p-2 rounded-xl border border-amber-100">
                      {pose.doshaEffect}
                    </p>
                  )}

                  <div className="flex items-center gap-3 text-xs text-gray-500 pt-1">
                    <span className="flex items-center gap-1">
                      <Clock className="w-3.5 h-3.5 text-amber-600" />
                      {pose.recommendedDurationSeconds}s hold
                    </span>
                  </div>
                </div>
              </div>

              <div className="px-5 py-3 border-t border-gray-100 bg-gray-50/50 flex items-center justify-between text-xs font-semibold text-amber-800 group-hover:text-amber-900">
                <span>View Pose Instructions</span>
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
