import React, { useState } from 'react';
import { Utensils, Sparkles, CheckCircle2, XCircle, AlertTriangle, Filter, ChevronRight, Apple, Flame, Wind, Droplets } from 'lucide-react';
import Disclaimer from '../../components/common/Disclaimer';

const foodCategories = [
  {
    category: 'Grains & Cereals',
    vata: { favor: 'Oats (cooked), Basmati Rice, Wheat, Quinoa', avoid: 'Dry cereal, raw oats, barley, cold corn crackers' },
    pitta: { favor: 'Basmati Rice, Barley, Oats, Wheat', avoid: 'Rye, corn, millet, sour sourdough' },
    kapha: { favor: 'Barley, Millet, Buckwheat, Quinoa, Rye', avoid: 'Wheat, white rice, moist heavy oats, yeasted breads' }
  },
  {
    category: 'Vegetables',
    vata: { favor: 'Cooked carrots, sweet potatoes, zucchini, asparagus, beets, spinach (in ghee)', avoid: 'Raw salads, raw broccoli, raw cabbage, raw cauliflower, nightshades' },
    pitta: { favor: 'Cucumbers, zucchini, asparagus, leafy greens, bitter gourd, cilantro, okra', avoid: 'Hot peppers, raw onions, garlic, tomatoes, radishes, sour eggplant' },
    kapha: { favor: 'Bitter greens, kale, broccoli, cabbage, cauliflower, radishes, garlic, ginger', avoid: 'Sweet potatoes, raw tomatoes, avocado, heavy zucchini, cucumbers in winter' }
  },
  {
    category: 'Fruits',
    vata: { favor: 'Sweet ripe mangoes, soaked dates, stewed apples, bananas, avocados, figs', avoid: 'Dry fruits (unsoaked), unripe astringent persimmons, cranberries, raw green apples' },
    pitta: { favor: 'Sweet melons, sweet apples, ripe pears, sweet berries, pomegranates, soaked raisins', avoid: 'Sour citrus, sour oranges, sour pineapple, unripe berries, sour cherries' },
    kapha: { favor: 'Astringent apples, pears, pomegranates, dried figs, cranberries, berries', avoid: 'Heavy sweet bananas, dates, mangoes, papayas, avocados, melons' }
  },
  {
    category: 'Legumes & Pulses',
    vata: { favor: 'Yellow Mung Dal (split & cooked with cumin and ghee), red lentils', avoid: 'Chickpeas, black beans, raw soy, kidney beans (aggravates gas)' },
    pitta: { favor: 'Mung Dal, black beans, chickpeas, tofu, split peas, adzuki beans', avoid: 'Urad dal (fermented), soy sauce, spicy salted peanuts' },
    kapha: { favor: 'Mung beans, red lentils, black beans, chickpeas, adzuki beans (spiced)', avoid: 'Heavy urad dal, soy cheese, deep fried tofu' }
  },
  {
    category: 'Oils & Fats',
    vata: { favor: 'Pure Desi Cow Ghee, organic warm Sesame Oil, Almond oil', avoid: 'Canola oil, corn oil, deep fried rancid trans fats' },
    pitta: { favor: 'Desi Cow Ghee, Coconut Oil, Sunflower Oil, Olive Oil', avoid: 'Mustard oil, chili oil, heating peanut oil' },
    kapha: { favor: 'Minimal Mustard Oil, Flaxseed Oil, small amounts of Ghee', avoid: 'Deep-fried foods, heavy coconut oil, commercial margarine' }
  },
  {
    category: 'Spices & Seasonings',
    vata: { favor: 'Fresh ginger, cumin, coriander, fennel, cardamom, cinnamon, rock salt', avoid: 'Excess cayenne pepper, dry chili powder, black mustard seeds' },
    pitta: { favor: 'Fennel seeds, coriander, fresh cilantro, mint, cardamom, turmeric (moderate)', avoid: 'Cayenne, red chili, raw garlic, black pepper in excess, cloves' },
    kapha: { favor: 'Dry ginger, black pepper, pippali, mustard seeds, cloves, cinnamon, fenugreek', avoid: 'Excess refined table salt, heavy sweet syrups' }
  }
];

const sixTastes = [
  {
    taste: 'Madhura (Sweet)',
    elements: 'Earth + Water',
    action: 'Nourishes tissues, calms Vata & Pitta, increases Kapha in excess',
    sources: 'Ghee, basmati rice, milk, sweet ripe fruits, dates, wheat'
  },
  {
    taste: 'Amla (Sour)',
    elements: 'Earth + Fire',
    action: 'Stimulates digestive Agni, enlivens mind, increases Pitta & Kapha',
    sources: 'Lemon, amla, buttermilk, fermented yoghurt, tamarind'
  },
  {
    taste: 'Lavana (Salty)',
    elements: 'Water + Fire',
    action: 'Retains cellular moisture, kindles taste buds, increases Pitta & Kapha',
    sources: 'Himalayan Pink Rock Salt (Saindhava Lavana), sea salt, kelp'
  },
  {
    taste: 'Katu (Pungent)',
    elements: 'Fire + Air',
    action: 'Clears sinuses, burns Ama, stimulates metabolism, increases Vata & Pitta',
    sources: 'Dry ginger, black pepper, pippali, garlic, cloves'
  },
  {
    taste: 'Tikta (Bitter)',
    elements: 'Air + Ether',
    action: 'Purifies blood, cleanses liver, reduces fever, increases Vata',
    sources: 'Neem, turmeric, bitter melon, leafy greens, fenugreek'
  },
  {
    taste: 'Kashaya (Astringent)',
    elements: 'Air + Earth',
    action: 'Contracts tissues, heals mucosal wounds, cools Pitta, increases Vata',
    sources: 'Haritaki, green tea, pomegranates, raw bananas, legumes'
  }
];

export default function DietGuide() {
  const [selectedDosha, setSelectedDosha] = useState('ALL');

  return (
    <div className="min-h-screen bg-sand-50 py-10 px-4 sm:px-6 lg:px-8">
      <div className="max-w-7xl mx-auto space-y-10">
        
        {/* Header */}
        <div className="text-center space-y-3">
          <div className="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-emerald-100 text-emerald-800 text-xs font-semibold tracking-wide uppercase">
            <Utensils className="w-3.5 h-3.5" />
            Ayurvedic Nutritional Science (Āhāra Vijñāna)
          </div>
          <h1 className="font-serif text-3xl sm:text-4xl lg:text-5xl font-bold text-earth-950">
            Ayurvedic Diet & Food Chart Guide
          </h1>
          <p className="text-gray-600 max-w-2xl mx-auto text-base">
            "When diet is wrong, medicine is of no use. When diet is correct, medicine is of no need." — Classical Ayurvedic Proverb
          </p>
        </div>

        {/* Dosha Selector Tab */}
        <div className="bg-white rounded-3xl p-4 sm:p-6 border border-gray-200 shadow-sm flex flex-col sm:flex-row items-center justify-between gap-4">
          <div className="flex items-center gap-2 text-earth-950 font-serif font-bold text-base">
            <Filter className="w-5 h-5 text-emerald-700" />
            Filter by Constitutional Imbalance (Dosha):
          </div>

          <div className="flex items-center gap-2 bg-sand-100 p-1.5 rounded-2xl w-full sm:w-auto overflow-x-auto">
            {['ALL', 'VATA', 'PITTA', 'KAPHA'].map((d) => (
              <button
                key={d}
                onClick={() => setSelectedDosha(d)}
                className={`flex-1 sm:flex-none px-5 py-2.5 rounded-xl text-xs font-bold transition-all ${
                  selectedDosha === d
                    ? 'bg-earth-900 text-white shadow-sm'
                    : 'text-earth-800 hover:bg-sand-200'
                }`}
              >
                {d === 'ALL' ? 'Complete View' : `${d} Pacifying`}
              </button>
            ))}
          </div>
        </div>

        {/* Six Tastes (Shad Rasa) Cards */}
        <div className="space-y-4">
          <h2 className="font-serif text-2xl font-bold text-earth-950">
            The Six Sacred Tastes (Ṣaḍ Rasa)
          </h2>
          <p className="text-xs text-gray-500">A wholesome meal should incorporate all six tastes to ensure complete physiological satiety.</p>

          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
            {sixTastes.map((t, idx) => (
              <div key={idx} className="bg-white p-5 rounded-2xl border border-gray-200 shadow-sm space-y-2">
                <div className="flex items-center justify-between">
                  <h3 className="font-serif font-bold text-sm text-earth-950">{t.taste}</h3>
                  <span className="text-[11px] px-2 py-0.5 rounded-full bg-sand-100 text-earth-800 font-semibold">
                    {t.elements}
                  </span>
                </div>
                <p className="text-xs text-gray-600 leading-relaxed">{t.action}</p>
                <div className="text-[11px] text-emerald-800 font-medium pt-1">
                  <strong>Examples:</strong> {t.sources}
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* Master Food Chart by Category */}
        <div className="space-y-6">
          <h2 className="font-serif text-2xl font-bold text-earth-950">
            Dosha Food Classification Matrix
          </h2>

          <div className="grid grid-cols-1 gap-6">
            {foodCategories.map((cat, idx) => (
              <div key={idx} className="bg-white rounded-3xl border border-gray-200 p-6 sm:p-8 shadow-sm space-y-5">
                <h3 className="font-serif text-xl font-bold text-earth-950 border-b border-gray-100 pb-3 flex items-center gap-2">
                  <Apple className="w-5 h-5 text-emerald-700" />
                  {cat.category}
                </h3>

                <div className="grid grid-cols-1 md:grid-cols-3 gap-5">
                  {/* Vata Column */}
                  {(selectedDosha === 'ALL' || selectedDosha === 'VATA') && (
                    <div className="bg-blue-50/40 rounded-2xl p-4 border border-blue-100 space-y-3">
                      <div className="flex items-center gap-2 text-blue-900 font-serif font-bold text-sm">
                        <Wind className="w-4 h-4 text-blue-600" />
                        Vata (Air + Ether)
                      </div>
                      <div className="space-y-1 text-xs">
                        <div className="flex items-start gap-1.5 text-emerald-800">
                          <CheckCircle2 className="w-4 h-4 text-emerald-600 flex-shrink-0 mt-0.5" />
                          <span><strong>Favor:</strong> {cat.vata.favor}</span>
                        </div>
                        <div className="flex items-start gap-1.5 text-rose-800 pt-1">
                          <XCircle className="w-4 h-4 text-rose-500 flex-shrink-0 mt-0.5" />
                          <span><strong>Avoid:</strong> {cat.vata.avoid}</span>
                        </div>
                      </div>
                    </div>
                  )}

                  {/* Pitta Column */}
                  {(selectedDosha === 'ALL' || selectedDosha === 'PITTA') && (
                    <div className="bg-amber-50/40 rounded-2xl p-4 border border-amber-100 space-y-3">
                      <div className="flex items-center gap-2 text-amber-900 font-serif font-bold text-sm">
                        <Flame className="w-4 h-4 text-amber-600" />
                        Pitta (Fire + Water)
                      </div>
                      <div className="space-y-1 text-xs">
                        <div className="flex items-start gap-1.5 text-emerald-800">
                          <CheckCircle2 className="w-4 h-4 text-emerald-600 flex-shrink-0 mt-0.5" />
                          <span><strong>Favor:</strong> {cat.pitta.favor}</span>
                        </div>
                        <div className="flex items-start gap-1.5 text-rose-800 pt-1">
                          <XCircle className="w-4 h-4 text-rose-500 flex-shrink-0 mt-0.5" />
                          <span><strong>Avoid:</strong> {cat.pitta.avoid}</span>
                        </div>
                      </div>
                    </div>
                  )}

                  {/* Kapha Column */}
                  {(selectedDosha === 'ALL' || selectedDosha === 'KAPHA') && (
                    <div className="bg-emerald-50/40 rounded-2xl p-4 border border-emerald-100 space-y-3">
                      <div className="flex items-center gap-2 text-emerald-900 font-serif font-bold text-sm">
                        <Droplets className="w-4 h-4 text-emerald-600" />
                        Kapha (Water + Earth)
                      </div>
                      <div className="space-y-1 text-xs">
                        <div className="flex items-start gap-1.5 text-emerald-800">
                          <CheckCircle2 className="w-4 h-4 text-emerald-600 flex-shrink-0 mt-0.5" />
                          <span><strong>Favor:</strong> {cat.kapha.favor}</span>
                        </div>
                        <div className="flex items-start gap-1.5 text-rose-800 pt-1">
                          <XCircle className="w-4 h-4 text-rose-500 flex-shrink-0 mt-0.5" />
                          <span><strong>Avoid:</strong> {cat.kapha.avoid}</span>
                        </div>
                      </div>
                    </div>
                  )}
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* Viruddha Ahara Warnings */}
        <div className="bg-rose-50/60 rounded-3xl p-6 sm:p-8 border border-rose-200 shadow-sm space-y-4">
          <div className="flex items-center gap-2 text-rose-900 font-serif font-bold text-lg">
            <AlertTriangle className="w-5 h-5 text-rose-600" />
            Classical Incompatible Food Combinations (Viruddha Āhāra)
          </div>
          <p className="text-xs text-rose-800 leading-relaxed max-w-3xl">
            Ayurveda warns that certain foods that are nourishing when eaten alone generate toxic metabolic waste (Ama) when combined due to opposing thermal potencies (Virya) or digestive rates:
          </p>

          <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-3 pt-2">
            <div className="bg-white p-3.5 rounded-xl border border-rose-100 text-xs text-gray-700">
              <strong>Milk + Citrus / Sour Fruits</strong>
              <p className="text-[11px] text-gray-500 mt-0.5">Curdles in stomach, creating heavy undigested toxins.</p>
            </div>
            <div className="bg-white p-3.5 rounded-xl border border-rose-100 text-xs text-gray-700">
              <strong>Milk + Fish</strong>
              <p className="text-[11px] text-gray-500 mt-0.5">Opposing Virya creates deep blood impurities (Rakta).</p>
            </div>
            <div className="bg-white p-3.5 rounded-xl border border-rose-100 text-xs text-gray-700">
              <strong>Heated / Cooked Honey</strong>
              <p className="text-[11px] text-gray-500 mt-0.5">Heating transforms raw honey into sticky toxic glue.</p>
            </div>
            <div className="bg-white p-3.5 rounded-xl border border-rose-100 text-xs text-gray-700">
              <strong>Equal Ratio Ghee + Honey</strong>
              <p className="text-[11px] text-gray-500 mt-0.5">Always consume in unequal weight proportions.</p>
            </div>
            <div className="bg-white p-3.5 rounded-xl border border-rose-100 text-xs text-gray-700">
              <strong>Melons with Any Other Food</strong>
              <p className="text-[11px] text-gray-500 mt-0.5">Eat melons alone; combining ferments other foods.</p>
            </div>
            <div className="bg-white p-3.5 rounded-xl border border-rose-100 text-xs text-gray-700">
              <strong>Yoghurt at Night</strong>
              <p className="text-[11px] text-gray-500 mt-0.5">Blocks microchannels (Srotas) and elevates Kapha.</p>
            </div>
          </div>
        </div>

        {/* Clinical Disclaimer */}
        <Disclaimer text="Ayurvedic dietary recommendations are tailored to individual digestive power (Agni) and current imbalances (Vikriti). For chronic conditions, consult with your registered Ayurvedic doctor." />
      </div>
    </div>
  );
}
