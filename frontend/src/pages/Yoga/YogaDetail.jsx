import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import { ArrowLeft, SunMedium, Clock, AlertTriangle, CheckCircle2, Heart } from 'lucide-react';
import yogaService from '../../services/yogaService';
import LoadingSpinner from '../../components/common/LoadingSpinner';
import Disclaimer from '../../components/common/Disclaimer';
import FavoriteButton from '../../components/common/FavoriteButton';

export default function YogaDetail() {
  const { slug } = useParams();
  const [pose, setPose] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setLoading(true);
    yogaService.getPoseBySlug(slug)
      .then((res) => setPose(res.data))
      .catch((err) => console.error("Error fetching pose", err))
      .finally(() => setLoading(false));
  }, [slug]);

  if (loading) return <LoadingSpinner message="Retrieving asana alignment guidelines..." />;
  if (!pose) {
    return (
      <div className="max-w-3xl mx-auto px-4 py-20 text-center space-y-4">
        <h2 className="font-serif text-2xl font-bold text-gray-800">Pose Not Found</h2>
        <Link to="/yoga" className="text-amber-700 font-semibold underline">Back to Yoga Sanctuary</Link>
      </div>
    );
  }

  return (
    <article className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-10 space-y-10">
      <Link
        to="/yoga"
        className="inline-flex items-center gap-2 text-xs font-semibold uppercase tracking-wider text-amber-800 hover:text-amber-900 transition-colors"
      >
        <ArrowLeft className="w-4 h-4" />
        <span>Back to Yoga Sanctuary</span>
      </Link>

      {/* Header */}
      <div className="grid grid-cols-1 md:grid-cols-12 gap-8 items-start">
        <div className="md:col-span-5 rounded-3xl overflow-hidden shadow-lg border border-gray-200 bg-gray-50 h-72 md:h-80">
          {pose.imageUrl ? (
            <img src={pose.imageUrl} alt={pose.englishName} className="w-full h-full object-cover" />
          ) : (
            <div className="w-full h-full flex items-center justify-center text-amber-700">
              <SunMedium className="w-16 h-16 opacity-30" />
            </div>
          )}
        </div>

        <div className="md:col-span-7 space-y-4">
          <div className="flex items-center gap-2">
            <span className="px-3 py-1 rounded-full bg-amber-100 text-amber-800 text-xs font-semibold uppercase tracking-wider">
              {pose.category}
            </span>
            <span className="px-3 py-1 rounded-full bg-gray-100 text-gray-800 text-xs font-semibold">
              {pose.difficulty}
            </span>
          </div>

          <div className="flex items-center justify-between gap-4">
            <h1 className="font-serif text-3xl sm:text-4xl font-bold text-gray-950">
              {pose.englishName}
            </h1>
            <FavoriteButton itemType="YOGA" itemId={pose.id} />
          </div>

          <p className="font-serif text-xl italic text-earth-800">
            {pose.sanskritName}
          </p>

          <div className="flex items-center gap-2 text-sm text-gray-600">
            <Clock className="w-4 h-4 text-amber-600" />
            <span>Recommended Hold: <strong>{pose.recommendedDurationSeconds} seconds</strong></span>
          </div>

          {pose.doshaEffect && (
            <div className="p-3.5 rounded-2xl bg-amber-50 border border-amber-200 text-xs text-amber-950">
              <strong>Ayurvedic Energetics:</strong> {pose.doshaEffect}
            </div>
          )}
        </div>
      </div>

      {/* Step-by-Step Instructions */}
      <section className="p-6 rounded-3xl bg-white border border-gray-200 space-y-4">
        <h2 className="font-serif text-2xl font-bold text-gray-900">Step-by-Step Alignment &amp; Breath</h2>
        <p className="text-sm sm:text-base text-gray-700 leading-relaxed whitespace-pre-line">
          {pose.instructions}
        </p>
      </section>

      {/* Benefits & Precautions Grid */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <section className="p-6 rounded-3xl bg-emerald-50/70 border border-emerald-200 space-y-3">
          <div className="flex items-center gap-2 font-serif font-bold text-emerald-950 text-lg">
            <CheckCircle2 className="w-5 h-5 text-emerald-600" />
            <h3>Anatomical &amp; Energetic Benefits</h3>
          </div>
          <p className="text-xs sm:text-sm text-emerald-900 leading-relaxed">
            {pose.benefits}
          </p>
        </section>

        <section className="p-6 rounded-3xl bg-rose-50/70 border border-rose-200 space-y-3">
          <div className="flex items-center gap-2 font-serif font-bold text-rose-950 text-lg">
            <AlertTriangle className="w-5 h-5 text-rose-600" />
            <h3>Contraindications &amp; Precautions</h3>
          </div>
          <p className="text-xs sm:text-sm text-rose-900 leading-relaxed">
            {pose.precautions}
          </p>
        </section>
      </div>

      <Disclaimer variant="general" />
    </article>
  );
}
