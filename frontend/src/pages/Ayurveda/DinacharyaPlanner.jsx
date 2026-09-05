import React, { useState, useEffect } from 'react';
import { Clock, Sun, Moon, Sunrise, Sunset, CheckCircle2, Circle, Sparkles, Flame, Wind, Droplets, Info } from 'lucide-react';
import Disclaimer from '../../components/common/Disclaimer';

const doshaTimeWindows = [
  {
    startHour: 6,
    endHour: 10,
    dosha: 'KAPHA',
    element: 'Earth + Water',
    color: 'emerald',
    icon: Sunrise,
    title: 'Morning Kapha Phase (6:00 AM – 10:00 AM)',
    description: 'Heaviness and physical stability dominate. The ideal window to shake off sluggishness through invigorating movement and energizing pranayama.',
    action: 'Wake before 6:00 AM, practice tongue scraping, drink warm water (Ushapan), perform Abhyanga oil massage, and engage in vigorous Yoga/exercise.',
    quote: 'Charaka Samhita: "Exercise up to half of one’s physical capacity in the morning before breakfast."'
  },
  {
    startHour: 10,
    endHour: 14,
    dosha: 'PITTA',
    element: 'Fire + Water',
    color: 'amber',
    icon: Sun,
    title: 'Midday Pitta Phase (10:00 AM – 2:00 PM)',
    description: 'The solar fire and internal digestive fire (Jatharagni) peak simultaneously. Optimal time for heavy mental work and the largest meal of the day.',
    action: 'Eat your primary nutritious lunch when digestion is strongest. Take a 10-minute gentle post-meal walk (Shatapadi). Avoid intense arguments or excessive sun exposure.',
    quote: 'Ashtanga Hridaya: "Midday is the seat of Pitta; nourish the Agni with wholesome, freshly prepared food."'
  },
  {
    startHour: 14,
    endHour: 18,
    dosha: 'VATA',
    element: 'Air + Space',
    color: 'blue',
    icon: Wind,
    title: 'Afternoon Vata Phase (2:00 PM – 6:00 PM)',
    description: 'Mental agility, communication, and creative problem-solving are heightened. Physical energy begins to lighten.',
    action: 'Engage in creative projects, strategic planning, and intellectual tasks. Sip warm herbal tea (cumin, coriander, fennel) to prevent afternoon Vata bloating.',
    quote: 'Sushruta Samhita: "Vata governs mental transmission and sensory agility in the late afternoon."'
  },
  {
    startHour: 18,
    endHour: 22,
    dosha: 'KAPHA',
    element: 'Earth + Water',
    color: 'emerald',
    icon: Sunset,
    title: 'Evening Kapha Phase (6:00 PM – 10:00 PM)',
    description: 'Natural cooling and winding down. Melatonin synthesis increases as biological rhythms prepare the body for cellular repair.',
    action: 'Consume a light, warm dinner by 7:30 PM. Disconnect from digital screens 1 hour before bed. Practice gentle Nadi Shodhana or restorative Shavasana.',
    quote: 'Charaka Samhita: "Sleep taken at night in proper measure brings happiness, nourishment, strength, and longevity."'
  },
  {
    startHour: 22,
    endHour: 2,
    dosha: 'PITTA',
    element: 'Fire + Water',
    color: 'amber',
    icon: Moon,
    title: 'Night Pitta Phase (10:00 PM – 2:00 AM)',
    description: 'Internal metabolic cleanup, hepatic detoxification, and cellular memory consolidation occur while you sleep.',
    action: 'Be asleep by 10:00 PM. Late-night wakefulness disrupts liver bile metabolism and creates toxic Ama.',
    quote: 'Ashtanga Hridaya: "Staying awake during night Pitta creates dryness, burning sensations, and impaired vision."'
  },
  {
    startHour: 2,
    endHour: 6,
    dosha: 'VATA',
    element: 'Air + Space',
    color: 'blue',
    icon: Sparkles,
    title: 'Amritavela / Brahma Muhurta (2:00 AM – 6:00 AM)',
    description: 'Subtle spiritual energy, clarity, and lightness (Sattva) pervade the atmosphere before sunrise.',
    action: 'Wake during Brahma Muhurta (45-90 minutes before dawn) for meditation, mantra recitation, and deep creative intuition.',
    quote: 'Ashtanga Hridaya: "One should wake in the Brahma Muhurta to protect life and preserve health."'
  }
];

const defaultRoutines = [
  { id: 'wake', time: '5:30 AM', title: 'Brahma Muhurta Awakening', desc: 'Awaken with the sunrise when Sattva is peak.' },
  { id: 'ushapan', time: '6:00 AM', title: 'Ushapan (Warm Water)', desc: 'Drink 1-2 cups of warm copper-infused water to stimulate peristalsis.' },
  { id: 'jihwa', time: '6:15 AM', title: 'Jihwa Nirlekhana (Tongue Scraping)', desc: 'Gently scrape tongue 7-10 times with copper/silver scraper to remove overnight Ama.' },
  { id: 'gandusha', time: '6:30 AM', title: 'Gandusha / Kavala (Oil Pulling)', desc: 'Swish warm sesame oil for 5-10 minutes to strengthen gums and jaw.' },
  { id: 'abhyanga', time: '6:45 AM', title: 'Abhyanga (Warm Oil Self-Massage)', desc: 'Apply warm sesame or herb-infused oil in circular motions on joints.' },
  { id: 'yoga', time: '7:15 AM', title: 'Yoga Asana & Pranayama', desc: 'Practice 20-30 minutes of Sun Salutations and Nadi Shodhana.' },
  { id: 'breakfast', time: '8:00 AM', title: 'Light Warm Breakfast', desc: 'Cooked spiced porridge or stewed apples with cloves and cinnamon.' },
  { id: 'lunch', time: '12:30 PM', title: 'Pradhana Ahara (Midday Feast)', desc: 'The largest meal with all 6 tastes when digestive Agni is peak.' },
  { id: 'postwalk', time: '1:15 PM', title: 'Shatapadi (100 Steps Walk)', desc: 'Gentle mindful walk promoting gastric emptying without exertion.' },
  { id: 'dinner', time: '7:00 PM', title: 'Laghu Ahara (Light Dinner)', desc: 'Easy-to-digest kitchari, soup, or steamed vegetables.' },
  { id: 'winddown', time: '9:00 PM', title: 'Digital Sundown & Golden Milk', desc: 'Warm milk with nutmeg, turmeric, and ashwagandha; no screens.' },
  { id: 'sleep', time: '10:00 PM', title: 'Nidra (Restful Sleep)', desc: 'Rest before 10:00 PM to allow liver Pitta detoxification.' }
];

export default function DinacharyaPlanner() {
  const [currentHour, setCurrentHour] = useState(new Date().getHours());
  const [activeWindow, setActiveWindow] = useState(doshaTimeWindows[0]);
  const [completedTasks, setCompletedTasks] = useState(() => {
    try {
      const saved = localStorage.getItem('ayurvana_dinacharya_tasks');
      return saved ? JSON.parse(saved) : {};
    } catch {
      return {};
    }
  });

  useEffect(() => {
    const updateTime = () => {
      const hour = new Date().getHours();
      setCurrentHour(hour);
      
      const found = doshaTimeWindows.find(w => {
        if (w.startHour < w.endHour) {
          return hour >= w.startHour && hour < w.endHour;
        } else {
          // Crosses midnight (e.g. 22 to 2, or 2 to 6)
          return hour >= w.startHour || hour < w.endHour;
        }
      });
      if (found) setActiveWindow(found);
    };

    updateTime();
    const timer = setInterval(updateTime, 60000);
    return () => clearInterval(timer);
  }, []);

  const toggleTask = (id) => {
    const updated = { ...completedTasks, [id]: !completedTasks[id] };
    setCompletedTasks(updated);
    try {
      localStorage.setItem('ayurvana_dinacharya_tasks', JSON.stringify(updated));
    } catch (e) {
      console.error(e);
    }
  };

  const completedCount = Object.values(completedTasks).filter(Boolean).length;
  const progressPercent = Math.round((completedCount / defaultRoutines.length) * 100);

  return (
    <div className="min-h-screen bg-sand-50 py-10 px-4 sm:px-6 lg:px-8">
      <div className="max-w-6xl mx-auto space-y-10">
        
        {/* Header */}
        <div className="text-center space-y-3">
          <div className="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-emerald-100 text-emerald-800 text-xs font-semibold tracking-wide uppercase">
            <Clock className="w-3.5 h-3.5" />
            Circadian Biology & Chrono-Medicine
          </div>
          <h1 className="font-serif text-3xl sm:text-4xl lg:text-5xl font-bold text-earth-950">
            Dinacharya: Ayurvedic Circadian Planner
          </h1>
          <p className="text-gray-600 max-w-2xl mx-auto text-base">
            Synchronize your daily biology with the 4-hour solar and doshic energy waves discovered by classical Vaidyas over 3,000 years ago.
          </p>
        </div>

        {/* Live Active Circadian Clock Banner */}
        <div className="bg-gradient-to-r from-earth-900 to-earth-950 text-white rounded-3xl p-6 sm:p-8 shadow-xl relative overflow-hidden">
          <div className="relative z-10 flex flex-col md:flex-row items-start md:items-center justify-between gap-6">
            <div className="space-y-2">
              <div className="flex items-center gap-3">
                <span className="relative flex h-3 w-3">
                  <span className="animate-ping absolute inline-flex h-full w-full rounded-full bg-emerald-400 opacity-75"></span>
                  <span className="relative inline-flex rounded-full h-3 w-3 bg-emerald-500"></span>
                </span>
                <span className="text-xs font-semibold tracking-wider uppercase text-sand-300">
                  Current Biological Time Window
                </span>
              </div>
              <h2 className="font-serif text-2xl sm:text-3xl font-bold text-sand-50 flex items-center gap-3">
                <activeWindow.icon className="w-7 h-7 text-amber-400" />
                {activeWindow.title}
              </h2>
              <p className="text-sand-200 text-sm max-w-2xl leading-relaxed">
                {activeWindow.description}
              </p>
              <div className="pt-2 flex items-center gap-2 text-xs text-amber-300 font-medium">
                <Sparkles className="w-4 h-4" />
                <strong>Prescribed Action:</strong> {activeWindow.action}
              </div>
            </div>

            <div className="bg-white/10 backdrop-blur-md rounded-2xl p-4 border border-white/10 text-center min-w-[160px] self-stretch md:self-auto flex flex-col justify-center">
              <span className="text-xs text-sand-300 uppercase tracking-wider">Dominant Dosha</span>
              <span className="font-serif text-2xl font-bold text-amber-300">{activeWindow.dosha}</span>
              <span className="text-[11px] text-sand-200">{activeWindow.element}</span>
            </div>
          </div>
        </div>

        {/* 24-Hour Circadian Cycles Grid */}
        <div className="space-y-4">
          <div className="flex items-center justify-between">
            <h3 className="font-serif text-xl font-bold text-earth-950">The Six 4-Hour Circadian Cycles</h3>
            <span className="text-xs text-gray-500">Click any cycle to inspect Ayurvedic guidelines</span>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
            {doshaTimeWindows.map((window, idx) => {
              const Icon = window.icon;
              const isSelected = activeWindow.title === window.title;

              return (
                <div
                  key={idx}
                  onClick={() => setActiveWindow(window)}
                  className={`p-5 rounded-2xl border transition-all cursor-pointer bg-white ${
                    isSelected
                      ? 'border-emerald-600 ring-2 ring-emerald-500/20 shadow-md scale-[1.02]'
                      : 'border-gray-200 hover:border-emerald-300 hover:shadow-sm'
                  }`}
                >
                  <div className="flex items-center justify-between mb-3">
                    <div className="flex items-center gap-2.5">
                      <div className={`p-2 rounded-xl ${
                        window.dosha === 'KAPHA' ? 'bg-emerald-100 text-emerald-800' :
                        window.dosha === 'PITTA' ? 'bg-amber-100 text-amber-800' : 'bg-blue-100 text-blue-800'
                      }`}>
                        <Icon className="w-4 h-4" />
                      </div>
                      <span className="font-serif font-bold text-sm text-earth-950">{window.dosha} Phase</span>
                    </div>
                    <span className="text-xs font-semibold px-2 py-0.5 rounded-full bg-gray-100 text-gray-700">
                      {window.startHour}:00 - {window.endHour}:00
                    </span>
                  </div>

                  <h4 className="font-semibold text-xs text-earth-900 mb-1.5">{window.title}</h4>
                  <p className="text-xs text-gray-600 line-clamp-2 leading-relaxed mb-3">
                    {window.description}
                  </p>

                  <div className="text-[11px] text-gray-500 italic border-t border-gray-100 pt-2">
                    {window.quote}
                  </div>
                </div>
              );
            })}
          </div>
        </div>

        {/* Daily Routine Checklist */}
        <div className="bg-white rounded-3xl p-6 sm:p-8 border border-gray-200 shadow-sm space-y-6">
          <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 border-b border-gray-100 pb-5">
            <div>
              <h3 className="font-serif text-xl font-bold text-earth-950">Daily Dinacharya Protocol</h3>
              <p className="text-xs text-gray-500">Track your daily Ayurvedic habits to anchor cellular balance.</p>
            </div>
            
            <div className="flex items-center gap-3">
              <div className="text-right">
                <span className="text-xs text-gray-500">Progress: </span>
                <span className="font-bold text-sm text-emerald-800">{completedCount} / {defaultRoutines.length} ({progressPercent}%)</span>
              </div>
              <div className="w-24 h-2 bg-gray-100 rounded-full overflow-hidden">
                <div
                  className="h-full bg-emerald-600 transition-all duration-300"
                  style={{ width: `${progressPercent}%` }}
                />
              </div>
            </div>
          </div>

          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-3.5">
            {defaultRoutines.map((routine) => {
              const isDone = !!completedTasks[routine.id];

              return (
                <div
                  key={routine.id}
                  onClick={() => toggleTask(routine.id)}
                  className={`p-4 rounded-2xl border transition-all cursor-pointer flex items-start gap-3 select-none ${
                    isDone
                      ? 'bg-emerald-50/60 border-emerald-300 shadow-none'
                      : 'bg-white border-gray-200 hover:border-emerald-200 hover:bg-sand-50/50'
                  }`}
                >
                  <button className="mt-0.5 flex-shrink-0 text-emerald-700">
                    {isDone ? (
                      <CheckCircle2 className="w-5 h-5 text-emerald-600 fill-emerald-100" />
                    ) : (
                      <Circle className="w-5 h-5 text-gray-300" />
                    )}
                  </button>

                  <div className="space-y-0.5">
                    <div className="flex items-center gap-2">
                      <span className="text-[11px] font-bold px-1.5 py-0.5 rounded bg-earth-100 text-earth-800">
                        {routine.time}
                      </span>
                      <h4 className={`text-xs font-bold ${isDone ? 'line-through text-gray-400' : 'text-earth-950'}`}>
                        {routine.title}
                      </h4>
                    </div>
                    <p className={`text-[11px] leading-relaxed ${isDone ? 'text-gray-400' : 'text-gray-600'}`}>
                      {routine.desc}
                    </p>
                  </div>
                </div>
              );
            })}
          </div>
        </div>

        {/* Clinical Disclaimer */}
        <Disclaimer text="Dinacharya guidelines are derived from classical Charaka Samhita and Ashtanga Hridaya texts for wellness support. Adjust timings according to your individual constitution (Prakriti), season (Ritucharya), and advice of your Vaidya." />
      </div>
    </div>
  );
}
