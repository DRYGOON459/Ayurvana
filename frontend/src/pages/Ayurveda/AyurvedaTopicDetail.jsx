import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import { ArrowLeft, BookOpen, Sparkles, ScrollText } from 'lucide-react';
import ayurvedaService from '../../services/ayurvedaService';
import LoadingSpinner from '../../components/common/LoadingSpinner';
import Disclaimer from '../../components/common/Disclaimer';

export default function AyurvedaTopicDetail() {
  const { slug } = useParams();
  const [topic, setTopic] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setLoading(true);
    ayurvedaService.getTopicBySlug(slug)
      .then((res) => setTopic(res.data))
      .catch((err) => console.error("Topic fetch error", err))
      .finally(() => setLoading(false));
  }, [slug]);

  if (loading) return <LoadingSpinner message="Retrieving classical Ayurvedic text..." />;
  if (!topic) {
    return (
      <div className="max-w-3xl mx-auto px-4 py-20 text-center space-y-4">
        <h2 className="font-serif text-2xl font-bold text-gray-800">Topic Not Found</h2>
        <Link to="/ayurveda" className="text-ayur-700 font-semibold underline">Back to Knowledge Center</Link>
      </div>
    );
  }

  return (
    <article className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-10 space-y-8">
      <Link
        to="/ayurveda"
        className="inline-flex items-center gap-2 text-xs font-semibold uppercase tracking-wider text-ayur-700 hover:text-ayur-900 transition-colors"
      >
        <ArrowLeft className="w-4 h-4" />
        <span>Back to Ayurveda Hub</span>
      </Link>

      <header className="space-y-4 border-b border-gray-200 pb-8">
        <div className="flex flex-wrap items-center gap-3">
          <span className="px-3 py-1 rounded-full bg-ayur-100 text-ayur-800 text-xs font-semibold uppercase tracking-wider">
            {topic.category}
          </span>
          {topic.doshaAssociation && (
            <span className="px-3 py-1 rounded-full bg-earth-100 text-earth-800 text-xs font-medium">
              {topic.doshaAssociation}
            </span>
          )}
        </div>

        <h1 className="font-serif text-3xl sm:text-4xl lg:text-5xl font-bold text-ayur-950">
          {topic.title}
        </h1>

        {topic.sanskritTitle && (
          <p className="text-lg font-serif italic text-earth-700">{topic.sanskritTitle}</p>
        )}

        <p className="text-base sm:text-lg text-gray-600 leading-relaxed font-serif bg-ayur-50/70 p-4 rounded-xl border border-ayur-100">
          {topic.summary}
        </p>
      </header>

      {/* Main Educational Content */}
      <section className="prose prose-stone max-w-none text-gray-700 leading-relaxed space-y-6 text-sm sm:text-base">
        {topic.content.split('\n\n').map((paragraph, idx) => (
          <p key={idx} className="whitespace-pre-line">{paragraph}</p>
        ))}
      </section>

      {/* Classical References */}
      {topic.traditionalReferences && (
        <section className="p-6 rounded-2xl bg-earth-50 border border-earth-200/80 space-y-3">
          <div className="flex items-center gap-2 text-earth-900 font-serif font-bold">
            <ScrollText className="w-5 h-5 text-earth-700" />
            <h3>Classical Ayurvedic References</h3>
          </div>
          <p className="text-xs sm:text-sm text-earth-800 italic">
            {topic.traditionalReferences}
          </p>
        </section>
      )}

      <Disclaimer variant="general" />
    </article>
  );
}
