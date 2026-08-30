import React, { useState, useEffect } from 'react';
import { Play, Pause, RotateCcw, Volume2, VolumeX, Sparkles, SunMedium, Clock } from 'lucide-react';
import meditationService from '../../services/meditationService';
import LoadingSpinner from '../../components/common/LoadingSpinner';
import Disclaimer from '../../components/common/Disclaimer';

export default function MeditationHub() {
  const [meditations, setMeditations] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedCategory, setSelectedCategory] = useState('ALL');

  // Interactive Meditation Timer State
  const [timerDuration, setTimerDuration] = useState(10); // in minutes
  const [timeLeft, setTimeLeft] = useState(10 * 60); // in seconds
  const [isRunning, setIsRunning] = useState(false);
  const [ambientSound, setAmbientSound] = useState(false);

  useEffect(() => {
    setLoading(true);
    const request = selectedCategory === 'ALL'
      ? meditationService.getAllMeditations(0, 20)
      : meditationService.getMeditationsByCategory(selectedCategory, 0, 20);

    request
      .then((res) => setMeditations(res.data.content || []))
      .catch((err) => console.error("Failed to load meditations", err))
      .finally(() => setLoading(false));
  }, [selectedCategory]);

  // Timer Tick
  useEffect(() => {
    let interval = null;
    if (isRunning && timeLeft > 0) {
      interval = setInterval(() => {
        setTimeLeft((prev) => prev - 1);
      }, 1000);
    } else if (timeLeft === 0) {
      setIsRunning(false);
    }
    return () => clearInterval(interval);
  }, [isRunning, timeLeft]);

  const handleDurationSelect = (mins) => {
    setIsRunning(false);
    setTimerDuration(mins);
    setTimeLeft(mins * 60);
  };

  const handleReset = () => {
    setIsRunning(false);
    setTimeLeft(timerDuration * 60);
  };

  const formatTime = (seconds) => {
    const m = Math.floor(seconds / 60);
    const s = seconds % 60;
    return `${m.toString().padStart(2, '0')}:${s.toString().padStart(2, '0')}`;
  };

  const categories = ['ALL', 'Pranayama', 'Mindfulness', 'Stress Relief'];

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-10 space-y-12">
      {/* Header Banner */}
      <div className="rounded-3xl bg-gradient-to-r from-teal-900 via-ayur-900 to-teal-950 text-white p-8 sm:p-12 shadow-xl relative overflow-hidden">
        <div className="max-w-2xl space-y-4 relative z-10">
          <span className="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-teal-800/60 text-teal-200 text-xs font-semibold uppercase tracking-wider">
            <SunMedium className="w-3.5 h-3.5" />
            Dhyana &amp; Pranayama
          </span>
          <h1 className="font-serif text-3xl sm:text-4xl lg:text-5xl font-bold tracking-tight">
            Meditation &amp; Breathwork Sanctuary
          </h1>
          <p className="text-sm sm:text-base text-teal-100/90 leading-relaxed">
            Quiet the fluctuating ripples of the mind (Chitta Vritti Nirodha). Engage in classical pranayama breath control techniques, or set an interactive mindfulness session with our acoustic timer.
          </p>
        </div>
      </div>

      {/* Interactive Meditation Timer Widget */}
      <div className="max-w-2xl mx-auto p-8 sm:p-12 rounded-3xl bg-white border border-ayur-200 shadow-xl text-center space-y-8">
        <div className="space-y-2">
          <span className="text-xs uppercase font-bold tracking-wider text-ayur-700">
            Interactive Mindfulness Timer
          </span>
          <h2 className="font-serif text-2xl sm:text-3xl font-bold text-gray-900">
            Sacred Stillness
          </h2>
        </div>

        {/* Circular Display */}
        <div className="relative w-56 h-56 mx-auto flex flex-col items-center justify-center rounded-full bg-gradient-to-b from-ayur-50 to-white border-4 border-ayur-200 shadow-inner">
          <span className="font-mono text-4xl sm:text-5xl font-bold text-ayur-950 tracking-tight">
            {formatTime(timeLeft)}
          </span>
          <span className="text-xs font-medium text-gray-500 mt-2 font-serif">
            {isRunning ? 'Breathe with awareness' : 'Ready to begin'}
          </span>
        </div>

        {/* Duration Selectors */}
        <div className="flex items-center justify-center gap-3">
          {[5, 10, 15, 20].map((mins) => (
            <button
              key={mins}
              onClick={() => handleDurationSelect(mins)}
              className={`px-4 py-2 rounded-xl text-xs font-semibold transition-all ${
                timerDuration === mins
                  ? 'bg-ayur-800 text-white shadow-sm'
                  : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
              }`}
            >
              {mins} Mins
            </button>
          ))}
        </div>

        {/* Timer Controls */}
        <div className="flex items-center justify-center gap-4">
          <button
            onClick={() => setIsRunning(!isRunning)}
            className={`inline-flex items-center gap-2 px-8 py-3.5 rounded-full font-semibold text-sm shadow-md transition-all ${
              isRunning
                ? 'bg-amber-600 hover:bg-amber-700 text-white'
                : 'bg-ayur-800 hover:bg-ayur-700 text-white'
            }`}
          >
            {isRunning ? (
              <>
                <Pause className="w-4 h-4" />
                <span>Pause</span>
              </>
            ) : (
              <>
                <Play className="w-4 h-4" />
                <span>Start Session</span>
              </>
            )}
          </button>

          <button
            onClick={handleReset}
            aria-label="Reset timer"
            className="p-3.5 rounded-full border border-gray-200 text-gray-600 hover:bg-gray-100 transition-colors"
          >
            <RotateCcw className="w-4 h-4" />
          </button>

          <button
            onClick={() => setAmbientSound(!ambientSound)}
            aria-label="Toggle ambient chimes"
            className={`p-3.5 rounded-full border transition-colors ${
              ambientSound
                ? 'border-emerald-300 bg-emerald-50 text-emerald-800'
                : 'border-gray-200 text-gray-400 hover:text-gray-600'
            }`}
          >
            {ambientSound ? <Volume2 className="w-4 h-4" /> : <VolumeX className="w-4 h-4" />}
          </button>
        </div>

        {ambientSound && (
          <p className="text-[11px] text-emerald-700 font-medium">
            🔔 Ambient sound generator active: Soothing Tibetan singing bowl resonance enabled.
          </p>
        )}
      </div>

      {/* Guided Techniques Grid */}
      <div className="space-y-6">
        <div className="flex flex-wrap items-center justify-between gap-4 border-b border-gray-200 pb-4">
          <h2 className="font-serif text-2xl font-bold text-gray-950">
            Classical Pranayama &amp; Meditation Practices
          </h2>
          <div className="flex items-center gap-2">
            {categories.map((cat) => (
              <button
                key={cat}
                onClick={() => setSelectedCategory(cat)}
                className={`px-4 py-1.5 rounded-full text-xs font-semibold transition-all ${
                  selectedCategory === cat
                    ? 'bg-teal-800 text-white shadow-sm'
                    : 'bg-white text-gray-600 hover:bg-gray-50 border border-gray-200'
                }`}
              >
                {cat}
              </button>
            ))}
          </div>
        </div>

        {loading ? (
          <LoadingSpinner message="Retrieving guided pranayama and meditation instructions..." />
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {meditations.map((session) => (
              <div
                key={session.id}
                className="flex flex-col justify-between p-6 rounded-3xl bg-white border border-gray-200/80 shadow-sm hover:shadow-md hover:border-teal-300 transition-all space-y-4"
              >
                <div className="space-y-3">
                  <div className="flex items-center justify-between text-xs">
                    <span className="px-2.5 py-1 rounded-full bg-teal-50 text-teal-800 font-semibold uppercase tracking-wider">
                      {session.category}
                    </span>
                    <span className="flex items-center gap-1 text-gray-500 font-medium">
                      <Clock className="w-3.5 h-3.5" />
                      {session.durationMinutes} mins
                    </span>
                  </div>

                  <h3 className="font-serif text-xl font-bold text-gray-900">
                    {session.title}
                  </h3>

                  <p className="text-xs sm:text-sm text-gray-600 leading-relaxed line-clamp-3">
                    {session.description}
                  </p>

                  {/* Step instructions */}
                  <div className="p-3.5 rounded-2xl bg-teal-50/60 border border-teal-100 text-xs text-teal-950 space-y-1">
                    <strong className="block font-semibold">Instructions:</strong>
                    <p className="leading-relaxed">{session.instructions}</p>
                  </div>
                </div>

                <div className="pt-2 border-t border-gray-100 text-xs text-emerald-800 font-medium flex items-center gap-1.5">
                  <Sparkles className="w-3.5 h-3.5 text-emerald-600" />
                  <span>{session.benefits}</span>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>

      <Disclaimer variant="general" />
    </div>
  );
}
