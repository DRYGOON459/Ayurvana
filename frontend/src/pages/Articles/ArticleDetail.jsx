import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import { ArrowLeft, BookOpen, Clock, User, ScrollText, Calendar } from 'lucide-react';
import articleService from '../../services/articleService';
import LoadingSpinner from '../../components/common/LoadingSpinner';
import Disclaimer from '../../components/common/Disclaimer';
import FavoriteButton from '../../components/common/FavoriteButton';

export default function ArticleDetail() {
  const { slug } = useParams();
  const [article, setArticle] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setLoading(true);
    articleService.getArticleBySlug(slug)
      .then((res) => setArticle(res.data))
      .catch((err) => console.error("Error loading article", err))
      .finally(() => setLoading(false));
  }, [slug]);

  if (loading) return <LoadingSpinner message="Retrieving article and citations..." />;
  if (!article) {
    return (
      <div className="max-w-3xl mx-auto px-4 py-20 text-center space-y-4">
        <h2 className="font-serif text-2xl font-bold text-gray-800">Article Not Found</h2>
        <Link to="/articles" className="text-earth-700 font-semibold underline">Back to Articles Hub</Link>
      </div>
    );
  }

  return (
    <article className="max-w-3xl mx-auto px-4 sm:px-6 lg:px-8 py-10 space-y-8">
      <Link
        to="/articles"
        className="inline-flex items-center gap-2 text-xs font-semibold uppercase tracking-wider text-earth-800 hover:text-earth-900 transition-colors"
      >
        <ArrowLeft className="w-4 h-4" />
        <span>Back to Articles</span>
      </Link>

      <header className="space-y-4 border-b border-gray-200 pb-8">
        <span className="px-3 py-1 rounded-full bg-earth-100 text-earth-800 text-xs font-semibold uppercase tracking-wider">
          {article.category}
        </span>

        <div className="flex items-start justify-between gap-4">
          <h1 className="font-serif text-3xl sm:text-4xl lg:text-5xl font-bold text-gray-950 leading-tight">
            {article.title}
          </h1>
          <FavoriteButton itemType="ARTICLE" itemId={article.id} />
        </div>

        <div className="flex flex-wrap items-center gap-4 text-xs text-gray-600 pt-1">
          <span className="flex items-center gap-1.5 font-medium text-gray-800">
            <User className="w-4 h-4 text-earth-700" />
            {article.authorName}
          </span>
          <span>•</span>
          <span className="flex items-center gap-1.5">
            <Clock className="w-4 h-4 text-gray-400" />
            {article.readTimeMinutes} min read
          </span>
        </div>

        {article.coverImageUrl && (
          <div className="h-64 sm:h-80 w-full rounded-3xl overflow-hidden shadow-md my-4">
            <img src={article.coverImageUrl} alt={article.title} className="w-full h-full object-cover" />
          </div>
        )}

        <p className="text-base sm:text-lg text-gray-700 leading-relaxed font-serif bg-earth-50/70 p-5 rounded-2xl border border-earth-100">
          {article.summary}
        </p>
      </header>

      {/* Article Body */}
      <section className="prose prose-stone max-w-none text-gray-800 leading-relaxed space-y-6 text-sm sm:text-base">
        {article.content.split('\n\n').map((paragraph, idx) => (
          <p key={idx} className="whitespace-pre-line leading-relaxed">{paragraph}</p>
        ))}
      </section>

      {/* Scientific & Textual References */}
      {article.referencesContent && (
        <section className="p-6 rounded-2xl bg-earth-50 border border-earth-200/80 space-y-2">
          <div className="flex items-center gap-2 font-serif font-bold text-earth-950 text-base">
            <ScrollText className="w-4 h-4 text-earth-700" />
            <h3>Scientific References &amp; Literature Grounding</h3>
          </div>
          <p className="text-xs sm:text-sm text-earth-800 italic leading-relaxed">
            {article.referencesContent}
          </p>
        </section>
      )}

      <Disclaimer variant="general" />
    </article>
  );
}
