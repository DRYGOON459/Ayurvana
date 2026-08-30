import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import { ArrowLeft, Leaf, Shield, BookOpen, ExternalLink, Activity, Sparkles, Heart } from 'lucide-react';
import herbService from '../../services/herbService';
import SafetyWarning from '../../components/common/SafetyWarning';
import LoadingSpinner from '../../components/common/LoadingSpinner';
import Disclaimer from '../../components/common/Disclaimer';
import FavoriteButton from '../../components/common/FavoriteButton';

export default function HerbDetail() {
  const { slug } = useParams();
  const [herb, setHerb] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setLoading(true);
    herbService.getHerbBySlug(slug)
      .then((res) => setHerb(res.data))
      .catch((err) => console.error("Error fetching herb", err))
      .finally(() => setLoading(false));
  }, [slug]);

  if (loading) return <LoadingSpinner message="Retrieving botanical monograph & safety records..." />;
  if (!herb) {
    return (
      <div className="max-w-3xl mx-auto px-4 py-20 text-center space-y-4">
        <h2 className="font-serif text-2xl font-bold text-gray-800">Herb Not Found</h2>
        <Link to="/herbs" className="text-emerald-700 font-semibold underline">Back to Herb Encyclopedia</Link>
      </div>
    );
  }

  return (
    <article className="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8 py-10 space-y-10">
      {/* Back Button */}
      <Link
        to="/herbs"
        className="inline-flex items-center gap-2 text-xs font-semibold uppercase tracking-wider text-emerald-800 hover:text-emerald-900 transition-colors"
      >
        <ArrowLeft className="w-4 h-4" />
        <span>Back to Herb Encyclopedia</span>
      </Link>

      {/* Hero Header */}
      <div className="grid grid-cols-1 md:grid-cols-12 gap-8 items-start">
        {/* Left: Image Card */}
        <div className="md:col-span-5 rounded-3xl overflow-hidden shadow-lg border border-gray-200 bg-gray-50 h-72 md:h-96">
          {herb.imageUrl ? (
            <img src={herb.imageUrl} alt={herb.commonName} className="w-full h-full object-cover" />
          ) : (
            <div className="w-full h-full flex items-center justify-center text-emerald-600">
              <Leaf className="w-16 h-16 opacity-30" />
            </div>
          )}
        </div>

        {/* Right: Header Information */}
        <div className="md:col-span-7 space-y-4">
          <div className="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-emerald-100 text-emerald-800 text-xs font-semibold uppercase tracking-wider">
            <Leaf className="w-3.5 h-3.5" />
            <span>Botanical Monograph</span>
          </div>

          <div className="flex items-center justify-between gap-4">
            <h1 className="font-serif text-3xl sm:text-4xl font-bold text-gray-950">
              {herb.commonName}
            </h1>
            <FavoriteButton itemType="HERB" itemId={herb.id} />
          </div>

          <div className="text-sm space-y-1 text-gray-600">
            <p className="font-serif text-lg italic text-earth-800 font-medium">
              Sanskrit: {herb.sanskritName}
            </p>
            <p className="font-medium text-gray-700">
              Botanical: <span className="italic">{herb.scientificName}</span> ({herb.botanicalFamily})
            </p>
            {herb.partsUsed && (
              <p className="text-xs text-gray-500">
                Parts Used: <strong className="text-gray-700">{herb.partsUsed}</strong>
              </p>
            )}
          </div>

          {/* Pharmacological Attributes Grid */}
          <div className="grid grid-cols-3 gap-2 pt-2 text-center text-xs">
            <div className="p-3 rounded-2xl bg-earth-50 border border-earth-200">
              <span className="text-[10px] uppercase font-bold text-earth-700 block">Rasa (Taste)</span>
              <span className="font-medium text-gray-800 mt-0.5 block">{herb.rasa || "N/A"}</span>
            </div>
            <div className="p-3 rounded-2xl bg-earth-50 border border-earth-200">
              <span className="text-[10px] uppercase font-bold text-earth-700 block">Virya (Potency)</span>
              <span className="font-medium text-gray-800 mt-0.5 block">{herb.virya || "N/A"}</span>
            </div>
            <div className="p-3 rounded-2xl bg-earth-50 border border-earth-200">
              <span className="text-[10px] uppercase font-bold text-earth-700 block">Vipaka (Post-Digestive)</span>
              <span className="font-medium text-gray-800 mt-0.5 block">{herb.vipaka || "N/A"}</span>
            </div>
          </div>

          {herb.primaryDoshaEffect && (
            <div className="p-3.5 rounded-2xl bg-ayur-50 border border-ayur-200 text-xs text-ayur-900 leading-relaxed">
              <strong>Dosha Action:</strong> {herb.primaryDoshaEffect}
            </div>
          )}
        </div>
      </div>

      {/* Safety & Toxicity Box */}
      {herb.safety && (
        <SafetyWarning
          toxicityLevel={herb.safety.toxicityLevel}
          contraindications={herb.safety.contraindications}
          pregnancySafety={herb.safety.pregnancySafety}
          interactionWarnings={herb.safety.interactionWarnings}
        />
      )}

      {/* Detailed Description */}
      <section className="space-y-4">
        <h2 className="font-serif text-2xl font-bold text-gray-900">Botanical Description</h2>
        <p className="text-sm sm:text-base text-gray-700 leading-relaxed bg-white p-6 rounded-2xl border border-gray-200">
          {herb.description}
        </p>
      </section>

      {/* Traditional Uses & Modern Research Grid */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <section className="p-6 rounded-2xl bg-white border border-gray-200 space-y-3">
          <div className="flex items-center gap-2 text-ayur-800 font-serif font-bold text-lg">
            <Sparkles className="w-5 h-5 text-amber-500" />
            <h3>Traditional Ayurvedic Applications</h3>
          </div>
          <p className="text-xs sm:text-sm text-gray-700 leading-relaxed">
            {herb.traditionalUses || "Documented in classical Samhitas for therapeutic balance and Rasayana rejuvenation."}
          </p>
        </section>

        <section className="p-6 rounded-2xl bg-white border border-gray-200 space-y-3">
          <div className="flex items-center gap-2 text-blue-900 font-serif font-bold text-lg">
            <Activity className="w-5 h-5 text-blue-600" />
            <h3>Modern Pharmacological Research</h3>
          </div>
          <p className="text-xs sm:text-sm text-gray-700 leading-relaxed">
            {herb.modernResearchSummary || "Clinical pharmacological studies evaluating bioactive phytoconstituents and biochemical pathways."}
          </p>
        </section>
      </div>

      {/* Dosage and Administration */}
      {herb.dosageAndPreparations && (
        <section className="p-6 rounded-2xl bg-ayur-50/80 border border-ayur-200 space-y-2">
          <h3 className="font-serif font-bold text-base text-ayur-900">Recommended Classical Preparations &amp; Dosage</h3>
          <p className="text-xs sm:text-sm text-gray-800 leading-relaxed">
            {herb.dosageAndPreparations}
          </p>
        </section>
      )}

      {/* Scientific & Classical Citations */}
      {herb.references && herb.references.length > 0 && (
        <section className="p-6 rounded-2xl bg-white border border-gray-200 space-y-4">
          <div className="flex items-center gap-2 text-gray-900 font-serif font-bold text-lg">
            <BookOpen className="w-5 h-5 text-emerald-700" />
            <h3>Evidence-Grounded Citations &amp; Classical Literature</h3>
          </div>

          <div className="space-y-3">
            {herb.references.map((ref) => (
              <div key={ref.id} className="p-3.5 rounded-xl bg-gray-50 border border-gray-100 text-xs text-gray-700 space-y-1">
                <div className="flex items-center justify-between">
                  <span className="font-semibold text-gray-900">{ref.sourceTitle}</span>
                  <span className="px-2 py-0.5 rounded text-[10px] uppercase font-bold bg-white text-gray-600 border border-gray-200">
                    {ref.evidenceType.replace('_', ' ')}
                  </span>
                </div>
                <p className="text-gray-600">{ref.citation}</p>
                {ref.url && (
                  <a
                    href={ref.url}
                    target="_blank"
                    rel="noopener noreferrer"
                    className="inline-flex items-center gap-1 text-emerald-700 font-semibold hover:underline mt-1"
                  >
                    <span>View PubMed Citation</span>
                    <ExternalLink className="w-3 h-3" />
                  </a>
                )}
              </div>
            ))}
          </div>
        </section>
      )}

      <Disclaimer variant="general" />
    </article>
  );
}
