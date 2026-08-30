import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Sparkles, Compass, Flame, Wind, Droplets, ArrowRight } from 'lucide-react';
import ayurvedaService from '../../services/ayurvedaService';
import LoadingSpinner from '../../components/common/LoadingSpinner';
import Disclaimer from '../../components/common/Disclaimer';

export default function AyurvedaHub() {
  const [topics, setTopics] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedCategory, setSelectedCategory] = useState('ALL');

  useEffect(() => {
    setLoading(true);
    const fetchTopics = selectedCategory === 'ALL'
      ? ayurvedaService.getAllTopics(0, 50)
      : ayurvedaService.getTopicsByCategory(selectedCategory, 0, 50);

    fetchTopics
      .then((res) => setTopics(res.data.content || []))
      .catch((err) => console.error("Failed to load topics", err))
      .finally(() => setLoading(false));
  }, [selectedCategory]);

  const categories = ['ALL', 'Fundamentals', 'Dosha', 'Dinacharya'];

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-10 space-y-12">
      {/* Header Banner */}
      <div className="rounded-3xl bg-gradient-to-br from-ayur-900 via-ayur-800 to-earth-900 text-white p-8 sm:p-12 shadow-xl relative overflow-hidden">
        <div className="max-w-2xl space-y-4 relative z-10">
          <span className="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-ayur-700/60 text-ayur-200 text-xs font-semibold uppercase tracking-wider">
            <Sparkles className="w-3.5 h-3.5" />
            Vedic Philosophy &amp; Biology
          </span>
          <h1 className="font-serif text-3xl sm:text-4xl lg:text-5xl font-bold tracking-tight">
            Ayurveda Knowledge Center
          </h1>
          <p className="text-sm sm:text-base text-ayur-100/90 leading-relaxed">
            Discover the 5,000-year-old living science of life. Explore the Panchamahabhutas (Five Elements), understand your unique biological dosha constitution, and harmonize your daily rhythms with Dinacharya.
          </p>
          <div className="pt-2">
            <Link
              to="/ayurveda/quiz"
              className="inline-flex items-center gap-2 px-6 py-3 rounded-full bg-amber-400 text-ayur-950 font-semibold text-sm hover:bg-amber-300 shadow-md transition-all hover:scale-105"
            >
              <Compass className="w-4 h-4" />
              <span>Discover Your Prakriti (Dosha Quiz)</span>
            </Link>
          </div>
        </div>
      </div>

      {/* Tridosha Concept Cards */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div className="p-6 rounded-2xl bg-sky-50/70 border border-sky-200/80 space-y-3">
          <div className="w-12 h-12 rounded-xl bg-sky-100 text-sky-800 flex items-center justify-center">
            <Wind className="w-6 h-6" />
          </div>
          <h3 className="font-serif text-xl font-bold text-sky-950">Vata Dosha</h3>
          <span className="text-xs uppercase font-semibold tracking-wider text-sky-700 block">Ether &amp; Air • Kinetic Principle</span>
          <p className="text-xs sm:text-sm text-gray-700 leading-relaxed">
            Governs bodily movement, nerve impulses, breath, and circulation. Balanced Vata is creative and agile; out of balance it manifests as anxiety, insomnia, dry skin, and constipation.
          </p>
        </div>

        <div className="p-6 rounded-2xl bg-amber-50/70 border border-amber-200/80 space-y-3">
          <div className="w-12 h-12 rounded-xl bg-amber-100 text-amber-800 flex items-center justify-center">
            <Flame className="w-6 h-6" />
          </div>
          <h3 className="font-serif text-xl font-bold text-amber-950">Pitta Dosha</h3>
          <span className="text-xs uppercase font-semibold tracking-wider text-amber-700 block">Fire &amp; Water • Metabolic Principle</span>
          <p className="text-xs sm:text-sm text-gray-700 leading-relaxed">
            Controls enzymatic digestion, metabolic transformation, body temperature, and intellect. Balanced Pitta brings courage and sharp focus; in excess it drives inflammation, heartburn, and irritability.
          </p>
        </div>

        <div className="p-6 rounded-2xl bg-emerald-50/70 border border-emerald-200/80 space-y-3">
          <div className="w-12 h-12 rounded-xl bg-emerald-100 text-emerald-800 flex items-center justify-center">
            <Droplets className="w-6 h-6" />
          </div>
          <h3 className="font-serif text-xl font-bold text-emerald-950">Kapha Dosha</h3>
          <span className="text-xs uppercase font-semibold tracking-wider text-emerald-700 block">Earth &amp; Water • Structural Principle</span>
          <p className="text-xs sm:text-sm text-gray-700 leading-relaxed">
            Maintains physical cohesion, joint lubrication, stamina, and immune resilience. Balanced Kapha yields calm compassion and endurance; aggravated Kapha causes lethargy, weight gain, and congestion.
          </p>
        </div>
      </div>

      {/* Category Filter Tabs */}
      <div className="space-y-6">
        <div className="flex flex-wrap items-center justify-between gap-4 border-b border-gray-200 pb-4">
          <h2 className="font-serif text-2xl font-bold text-ayur-950">Curated Wisdom Guides</h2>
          <div className="flex items-center gap-2">
            {categories.map((cat) => (
              <button
                key={cat}
                onClick={() => setSelectedCategory(cat)}
                className={`px-4 py-1.5 rounded-full text-xs font-semibold transition-all ${
                  selectedCategory === cat
                    ? 'bg-ayur-800 text-white shadow-sm'
                    : 'bg-white text-gray-600 hover:bg-ayur-50 border border-gray-200'
                }`}
              >
                {cat}
              </button>
            ))}
          </div>
        </div>

        {loading ? (
          <LoadingSpinner message="Loading authentic Ayurveda topics..." />
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {topics.map((topic) => (
              <Link
                key={topic.id}
                to={`/ayurveda/topics/${topic.slug}`}
                className="group flex flex-col justify-between p-6 rounded-2xl bg-white border border-ayur-100 shadow-sm hover:shadow-md hover:border-ayur-300 transition-all"
              >
                <div className="space-y-3">
                  <div className="flex items-center justify-between text-xs text-ayur-700 font-semibold uppercase tracking-wider">
                    <span>{topic.category}</span>
                    {topic.sanskritTitle && (
                      <span className="font-normal text-earth-700">{topic.sanskritTitle}</span>
                    )}
                  </div>
                  <h3 className="font-serif text-xl font-bold text-gray-900 group-hover:text-ayur-800 transition-colors">
                    {topic.title}
                  </h3>
                  <p className="text-xs sm:text-sm text-gray-600 line-clamp-3 leading-relaxed">
                    {topic.summary}
                  </p>
                </div>

                <div className="pt-4 mt-4 border-t border-gray-100 flex items-center justify-between text-xs font-semibold text-ayur-700 group-hover:text-ayur-900">
                  <span>Read In-Depth Guide</span>
                  <ArrowRight className="w-4 h-4 group-hover:translate-x-1 transition-transform" />
                </div>
              </Link>
            ))}
          </div>
        )}
      </div>

      {/* Medical Disclaimer */}
      <Disclaimer variant="general" />
    </div>
  );
}
