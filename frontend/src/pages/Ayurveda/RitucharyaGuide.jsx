import React, { useState } from 'react';
import { Calendar, Sun, CloudRain, Snowflake, Wind, Sparkles, CheckCircle2, XCircle, BookOpen, AlertCircle } from 'lucide-react';
import Disclaimer from '../../components/common/Disclaimer';

const seasons = [
  {
    id: 'shishira',
    name: 'Śiśira (Late Winter)',
    months: 'Mid-January to Mid-March (Magha – Phalguna)',
    solstice: 'Adana Kala (Northern / Debilitating Solar Solstice begins)',
    dominantDosha: 'Kapha Accumulates (Sanchaya); Agni is Peak',
    element: 'Cold, dry, and windy',
    icon: Snowflake,
    color: 'blue',
    dietFavor: 'Nourishing warm soups, wheat, unrefined sugarcane jaggery, dairy with ginger and saffron, sesame seeds, sour and salty foods.',
    dietAvoid: 'Cold drinks, dry salads, light fasting foods, bitter and astringent cold foods.',
    lifestyle: 'Warm oil Abhyanga with sesame oil, sun exposure (Atapa Sevana), warm baths, regular invigorating exercise (Vyayama).',
    quote: 'Charaka Samhita: "During cold weather, digestive fire is trapped within by cold ambient air, becoming exceptionally powerful."'
  },
  {
    id: 'vasanta',
    name: 'Vasanta (Spring)',
    months: 'Mid-March to Mid-May (Chaitra – Vaishakha)',
    solstice: 'Adana Kala (Solar heat begins to draw bodily moisture)',
    dominantDosha: 'Kapha Liquefies (Prakopa) causing allergies & sluggishness',
    element: 'Warm, moist, blossoming',
    icon: Sparkles,
    color: 'emerald',
    dietFavor: 'Light old grains (barley, aged rice, roasted chickpea flour), bitter greens (neem, fenugreek), dry ginger tea, honey, pungent spices.',
    dietAvoid: 'Heavy sweet desserts, dairy, cold ice creams, day-sleeping (Divasvapna), sour and oily fried foods.',
    lifestyle: 'Vamana Panchakarma (therapeutic emesis), Udvartana (dry herbal powder scrub massage), vigorous daily exercise, nasal Nasya.',
    quote: 'Ashtanga Hridaya: "In spring, the sun liquefies the Kapha accumulated in winter, dampening digestive fire and triggering respiratory ailments."'
  },
  {
    id: 'grishma',
    name: 'Grīṣma (Summer)',
    months: 'Mid-May to Mid-July (Jyeshtha – Ashadha)',
    solstice: 'Adana Kala (Intense solar radiation depletes bodily strength/Bala)',
    dominantDosha: 'Vata Accumulates (Sanchaya); Pitta Rises; Kapha Pacified',
    element: 'Intensely hot, dry, dehydrating',
    icon: Sun,
    color: 'amber',
    dietFavor: 'Sweet cooling fruits (sweet melons, mangoes, grapes), coconut water, milk with cardamom, ghee, rice porridge, soaked raisins.',
    dietAvoid: 'Pungent chili, sour vinegar, salty chips, alcoholic beverages, excessive hot spices.',
    lifestyle: 'Rest during peak afternoon heat, apply cooling sandalwood paste (Chandana Lepa), moonbathing at night (Chandrika Sevana), gentle swimming.',
    quote: 'Sushruta Samhita: "The sun extracts the vital water essence (Sneha) of all living beings in Grishma."'
  },
  {
    id: 'varsha',
    name: 'Varṣā (Monsoon / Rainy Season)',
    months: 'Mid-July to Mid-September (Shravana – Bhadrapada)',
    solstice: 'Visarga Kala (Southern / Nourishing Lunar Solstice begins)',
    dominantDosha: 'Vata Aggravates (Prakopa); Agni is severely depressed',
    element: 'Humid, rainy, damp, low sunshine',
    icon: CloudRain,
    color: 'indigo',
    dietFavor: 'Warm freshly cooked food, aged basmati rice, split yellow mung soup, warm ghee, boiled water, rock salt, ginger and black pepper.',
    dietAvoid: 'Uncooked leafy salads, river water (heavy in silt), stale leftover food, curd/yoghurt, raw uncooked sprouts.',
    lifestyle: 'Basti Panchakarma (medicated enema—supreme Vata pacifier), dry clothes, fumigation with Guggulu incense, avoid wet breeze.',
    quote: 'Charaka Samhita: "In the rainy season, the digestive fire is weakened by humidity, acidic water, and gaseous atmospheric vapors."'
  },
  {
    id: 'sharad',
    name: 'Śarad (Autumn)',
    months: 'Mid-September to Mid-November (Ashvina – Kartika)',
    solstice: 'Visarga Kala (Moonlight gains strength; clear blue skies)',
    dominantDosha: 'Pitta Aggravates (Prakopa) due to sudden post-rain heat',
    element: 'Clear sky, bright sun, cooling night breeze',
    icon: Sun,
    color: 'rose',
    dietFavor: 'Sweet, bitter, and astringent foods; bitter ghee (Tikta Ghrita), basmati rice, barley, amla, pomegranates, sweet ripe pears.',
    dietAvoid: 'Spicy chili, sour fermented food, mustard oil, curd, heavy fried foods, daytime sleep.',
    lifestyle: 'Virechana Panchakarma (therapeutic purgation), drinking water exposed to moonlight (Hamsodaka), wearing pearls and cool fabrics.',
    quote: 'Ashtanga Hridaya: "The body habituated to rain and cold suddenly exposed to autumn rays triggers intense Pitta aggravation."'
  },
  {
    id: 'hemanta',
    name: 'Hemanta (Early Winter)',
    months: 'Mid-November to Mid-January (Margashirsha – Pausha)',
    solstice: 'Visarga Kala (Physical strength / Bala reaches annual maximum)',
    dominantDosha: 'Vata Pacified; Kapha builds healthy tissue & Ojas',
    element: 'Crisp cold, pleasant, invigorating',
    icon: Snowflake,
    color: 'teal',
    dietFavor: 'Rich unctuous meals, milk puddings, ghee, nuts, sesame seeds, sweet and sour warm curries, Chyawanprash, herbal wines (Asavas).',
    dietAvoid: 'Light airy foods, cold drinks, skipping meals (the strong Agni will consume body tissues if unfed).',
    lifestyle: 'Abhyanga with warm Bala-Ashwagandha oil, sunbathing, wearing warm wool, resistance exercise up to full capacity.',
    quote: 'Charaka Samhita: "Hemanta brings peak physical vigor and anabolic cellular nourishment to those who eat warmly and exercise."'
  }
];

export default function RitucharyaGuide() {
  const [selectedSeason, setSelectedSeason] = useState(seasons[1]); // Default to Vasanta

  return (
    <div className="min-h-screen bg-sand-50 py-10 px-4 sm:px-6 lg:px-8">
      <div className="max-w-6xl mx-auto space-y-10">
        
        {/* Header */}
        <div className="text-center space-y-3">
          <div className="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-emerald-100 text-emerald-800 text-xs font-semibold tracking-wide uppercase">
            <Calendar className="w-3.5 h-3.5" />
            Seasonal Chrono-Biology • Ṛtucaryā
          </div>
          <h1 className="font-serif text-3xl sm:text-4xl lg:text-5xl font-bold text-earth-950">
            Ritucharya: Ayurvedic Seasonal Living Guide
          </h1>
          <p className="text-gray-600 max-w-2xl mx-auto text-base">
            Align your diet, cleansing protocols, and daily habits with the six Ayurvedic seasons to prevent doshic accumulation and build immunity.
          </p>
        </div>

        {/* 6 Seasons Tab Selector */}
        <div className="bg-white rounded-3xl p-3 sm:p-4 border border-gray-200 shadow-sm overflow-x-auto">
          <div className="flex items-center gap-2 min-w-max">
            {seasons.map((s) => {
              const Icon = s.icon;
              const isSelected = selectedSeason.id === s.id;

              return (
                <button
                  key={s.id}
                  onClick={() => setSelectedSeason(s)}
                  className={`px-4 py-3 rounded-2xl text-xs font-bold transition-all flex items-center gap-2 ${
                    isSelected
                      ? 'bg-earth-900 text-white shadow-md'
                      : 'bg-sand-50 text-earth-800 hover:bg-sand-100'
                  }`}
                >
                  <Icon className="w-4 h-4" />
                  {s.name}
                </button>
              );
            })}
          </div>
        </div>

        {/* Detailed Season Hero Banner */}
        <div className="bg-gradient-to-r from-earth-900 to-earth-950 text-white rounded-3xl p-6 sm:p-8 shadow-xl space-y-6">
          <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 border-b border-white/10 pb-5">
            <div className="space-y-1">
              <span className="text-xs font-bold uppercase tracking-wider text-amber-300">
                {selectedSeason.solstice}
              </span>
              <h2 className="font-serif text-2xl sm:text-3xl font-bold text-sand-50 flex items-center gap-3">
                <selectedSeason.icon className="w-8 h-8 text-amber-400" />
                {selectedSeason.name}
              </h2>
              <p className="text-xs text-sand-200">{selectedSeason.months}</p>
            </div>

            <div className="bg-white/10 backdrop-blur-md rounded-2xl p-4 border border-white/10 text-right">
              <span className="text-[11px] text-sand-300 uppercase block">Doshic Dynamics</span>
              <span className="font-serif text-sm font-bold text-amber-300">{selectedSeason.dominantDosha}</span>
            </div>
          </div>

          <p className="text-sm text-sand-100 italic border-l-2 border-amber-400 pl-4 py-1">
            {selectedSeason.quote}
          </p>
        </div>

        {/* Dietary & Lifestyle Regimen Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          
          {/* Diet Card */}
          <div className="bg-white rounded-3xl p-6 sm:p-7 border border-gray-200 shadow-sm space-y-4">
            <h3 className="font-serif text-xl font-bold text-earth-950 flex items-center gap-2 border-b border-gray-100 pb-3">
              <Sparkles className="w-5 h-5 text-emerald-700" />
              Seasonal Dietetics (Ahara)
            </h3>

            <div className="space-y-3 text-xs">
              <div className="bg-emerald-50/60 rounded-2xl p-4 border border-emerald-200 space-y-1">
                <span className="font-bold text-emerald-900 flex items-center gap-1.5 uppercase tracking-wider text-[11px]">
                  <CheckCircle2 className="w-4 h-4 text-emerald-600" />
                  Prescribed &amp; Wholesome Foods (Pathya)
                </span>
                <p className="text-gray-700 leading-relaxed pt-1">{selectedSeason.dietFavor}</p>
              </div>

              <div className="bg-rose-50/60 rounded-2xl p-4 border border-rose-200 space-y-1">
                <span className="font-bold text-rose-900 flex items-center gap-1.5 uppercase tracking-wider text-[11px]">
                  <XCircle className="w-4 h-4 text-rose-600" />
                  Unwholesome &amp; Prohibited Foods (Apathya)
                </span>
                <p className="text-gray-700 leading-relaxed pt-1">{selectedSeason.dietAvoid}</p>
              </div>
            </div>
          </div>

          {/* Lifestyle Card */}
          <div className="bg-white rounded-3xl p-6 sm:p-7 border border-gray-200 shadow-sm space-y-4">
            <h3 className="font-serif text-xl font-bold text-earth-950 flex items-center gap-2 border-b border-gray-100 pb-3">
              <BookOpen className="w-5 h-5 text-emerald-700" />
              Seasonal Lifestyle &amp; Detox (Vihara)
            </h3>

            <div className="bg-sand-50 rounded-2xl p-5 border border-sand-200 space-y-3 text-xs">
              <h4 className="font-bold text-earth-900 uppercase tracking-wider text-[11px]">
                Recommended Daily Conduct &amp; Panchakarma
              </h4>
              <p className="text-gray-700 leading-relaxed">{selectedSeason.lifestyle}</p>
              
              <div className="pt-2 border-t border-sand-200/80 text-[11px] text-gray-500">
                <strong>Environmental Quality:</strong> {selectedSeason.element}
              </div>
            </div>
          </div>
        </div>

        {/* Clinical Disclaimer */}
        <Disclaimer text="Ritucharya principles are classical guidelines for temperate and tropical zones. Adjust according to local geography, altitude, current weather extremes, and consultation with your registered Vaidya." />
      </div>
    </div>
  );
}
