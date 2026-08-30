import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { Heart } from 'lucide-react';
import { useAuth } from '../../context/AuthContext';
import favoriteService from '../../services/favoriteService';

export default function FavoriteButton({ itemType, itemId, className = '' }) {
  const { isAuthenticated } = useAuth();
  const navigate = useNavigate();
  const [isFav, setIsFav] = useState(false);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (isAuthenticated && itemId && itemType) {
      favoriteService.checkFavorite(itemType, itemId)
        .then((res) => {
          setIsFav(res.data.isFavorite || false);
        })
        .catch(() => {});
    }
  }, [isAuthenticated, itemType, itemId]);

  const handleToggle = async (e) => {
    e.preventDefault();
    e.stopPropagation();

    if (!isAuthenticated) {
      navigate('/login');
      return;
    }

    if (loading) return;
    setLoading(true);

    try {
      if (isFav) {
        await favoriteService.removeFavorite(itemType, itemId);
        setIsFav(false);
      } else {
        await favoriteService.addFavorite(itemType, itemId);
        setIsFav(true);
      }
    } catch (err) {
      console.error("Failed to toggle favorite", err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <button
      type="button"
      onClick={handleToggle}
      aria-label={isFav ? "Remove from favorites" : "Save to favorites"}
      className={`p-2 rounded-full transition-all backdrop-blur-sm ${
        isFav
          ? 'bg-rose-50 text-rose-600 hover:bg-rose-100'
          : 'bg-white/80 text-gray-400 hover:text-rose-500 hover:bg-white'
      } shadow-sm border border-gray-100 ${className}`}
    >
      <Heart
        className={`w-4 h-4 transition-transform ${
          isFav ? 'fill-rose-500 scale-110' : 'scale-100'
        }`}
      />
    </button>
  );
}
