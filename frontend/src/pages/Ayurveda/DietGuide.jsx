import React, { useState, useEffect } from 'react';
import { Utensils, Sparkles, CheckCircle2, XCircle, AlertTriangle, Filter, ChevronRight, Apple, Flame, Wind, Droplets, BookOpen, Clock, Users, X } from 'lucide-react';
import Disclaimer from '../../components/common/Disclaimer';
import recipeService from '../../services/recipeService';

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
    sources: 'Rock salt (Saindhava Lavana), sea vegetables, black salt'
  },
  {
    taste: 'Katu (Pungent)',
    elements: 'Fire + Air',
    action: 'Clears sinuses, burns Ama toxins, calms Kapha, increases Vata & Pitta',
    sources: 'Ginger, black pepper, pippali, chili, mustard, garlic'
  },
  {
    taste: 'Tikta (Bitter)',
    elements: 'Air + Ether',
    action: 'Detoxifies blood and liver, clears heat, calms Pitta & Kapha, increases Vata',
    sources: 'Neem, turmeric, bitter gourd, leafy greens, fenugreek'
  },
  {
    taste: 'Kashaya (Astringent)',
    elements: 'Air + Earth',
    action: 'Heals mucosa, tightens tissues, calms Pitta & Kapha, increases Vata',
    sources: 'Triphala, pomegranate, green tea, raw bananas, cranberries'
  }
];

export default function DietGuide() {
  const [selectedDosha, setSelectedDosha] = useState('ALL');
  const [recipes, setRecipes] = useState([]);
  const [loadingRecipes, setLoadingRecipes] = useState(true);
  const [selectedRecipe, setSelectedRecipe] = useState(null);
  const [recipeFilter, setRecipeFilter] = useState('ALL');

  useEffect(() => {
    async function loadRecipes() {
      try {
        setLoadingRecipes(true);
        const res = await recipeService.getAllRecipes(0, 20);
        if (res?.data?.data?.content) {
          setRecipes(res.data.data.content);
        }
      } catch (err) {
        console.error("Failed to load recipes from backend", err);
      } finally {
        setLoadingRecipes(false);
      }
    }
    loadRecipes();
  }, []);

  const openRecipeDetail = async (slug) => {
    try {
      const res = await recipeService.getRecipeBySlug(slug);
      if (res?.data?.data) {
        setSelectedRecipe(res.data.data);
      }
    } catch (err) {
      console.error("Failed to fetch recipe detail", err);
    }
  };

  const filteredRecipes = recipes.filter(r => {
    if (recipeFilter === 'ALL') return true;
    return r.doshaSuitability === recipeFilter;
  });

  return (
    <div className="min-h-screen bg-gradient-to-b from-stone-50 via-white to-stone-50 py-10 px-4 sm:px-6 lg:px-8">
      <div className="max-w-6xl mx-auto space-y-12">
        {/* Header */}
        <div className="text-center space-y-3">
          <div className="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-emerald-50 border border-emerald-200 text-emerald-800 text-xs font-medium">
            <Utensils className="w-3.5 h-3.5 text-emerald-600" />
            <span>Ayurvedic Āhāra Vijnāna</span>
          </div>
          <h1 className="text-3xl sm:text-4xl font-serif font-bold text-gray-900 tracking-tight">
            Ayurvedic Diet & Nutrition Guide
          </h1>
          <p className="text-sm sm:text-base text-gray-600 max-w-2xl mx-auto">
            "When diet is wrong, medicine is of no use. When diet is correct, medicine is of no need."
            Explore individual food energetics, incompatible combinations (Viruddha Ahara), and authentic therapeutic recipes.
          </p>
        </div>

        {/* Six Tastes (Shad Rasa) Table */}
        <div className="bg-white rounded-3xl p-6 sm:p-8 border border-stone-200/80 shadow-sm space-y-6">
          <div className="flex items-center gap-2 text-stone-900 font-serif font-bold text-xl">
            <Sparkles className="w-5 h-5 text-amber-600" />
            The Six Classical Tastes (Ṣaḍ Rasa)
          </div>
          <p className="text-xs text-stone-600 leading-relaxed max-w-3xl">
            In Ayurveda, every meal should ideally balance all six tastes to satisfy the palate, stimulate optimal enzymatic secretions, and prevent overeating:
          </p>

          <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-4">
            {sixTastes.map((t, idx) => (
              <div key={idx} className="bg-stone-50/70 p-4 rounded-2xl border border-stone-200/60 space-y-2 hover:border-emerald-300 transition-colors">
                <div className="font-serif font-bold text-sm text-stone-900">{t.taste}</div>
                <div className="text-[11px] font-medium text-emerald-700 bg-emerald-50 inline-block px-2 py-0.5 rounded-md">
                  {t.elements}
                </div>
                <p className="text-xs text-stone-600">{t.action}</p>
                <div className="text-[11px] text-stone-500 pt-1 border-t border-stone-200/60">
                  <strong className="text-stone-700">Sources:</strong> {t.sources}
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* Interactive Food Matrix */}
        <div className="bg-white rounded-3xl p-6 sm:p-8 border border-stone-200/80 shadow-sm space-y-6">
          <div className="flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4">
            <div>
              <h2 className="text-xl font-serif font-bold text-stone-900 flex items-center gap-2">
                <Apple className="w-5 h-5 text-emerald-600" />
                Dosha-Specific Food Guide
              </h2>
              <p className="text-xs text-stone-600 mt-1">
                Filter by constitutional dosha to view which foods nourish and which create imbalance.
              </p>
            </div>

            {/* Dosha Filter Buttons */}
            <div className="flex items-center gap-1.5 p-1 bg-stone-100 rounded-2xl text-xs font-medium">
              {['ALL', 'VATA', 'PITTA', 'KAPHA'].map(dosha => (
                <button
                  key={dosha}
                  onClick={() => setSelectedDosha(dosha)}
                  className={`px-3 py-1.5 rounded-xl transition-all ${
                    selectedDosha === dosha
                      ? 'bg-white text-stone-900 font-semibold shadow-sm'
                      : 'text-stone-600 hover:text-stone-900'
                  }`}
                >
                  {dosha === 'ALL' ? 'All Doshas' : dosha}
                </button>
              ))}
            </div>
          </div>

          <div className="space-y-4 pt-2">
            {foodCategories.map((cat, idx) => (
              <div key={idx} className="border border-stone-200 rounded-2xl p-5 hover:border-stone-300 transition-colors">
                <h3 className="font-serif font-bold text-sm text-stone-900 mb-3 flex items-center gap-2">
                  <span className="w-2 h-2 rounded-full bg-emerald-600"></span>
                  {cat.category}
                </h3>

                <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
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

        {/* Classical Ayurvedic Recipes Section */}
        <div className="bg-white rounded-3xl p-6 sm:p-8 border border-stone-200/80 shadow-sm space-y-6">
          <div className="flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4">
            <div>
              <div className="flex items-center gap-2 text-stone-900 font-serif font-bold text-xl">
                <BookOpen className="w-5 h-5 text-emerald-600" />
                Classical Ayurvedic Recipes & Kitchen Pharmacy
              </div>
              <p className="text-xs text-stone-600 mt-1">
                Authentic classical dishes prepared with specific spices to optimize Agni and balance constitutional doshas.
              </p>
            </div>

            <div className="flex items-center gap-1.5 p-1 bg-stone-100 rounded-2xl text-xs font-medium">
              {['ALL', 'TRIDOSHIC', 'VATA', 'PITTA', 'KAPHA'].map(d => (
                <button
                  key={d}
                  onClick={() => setRecipeFilter(d)}
                  className={`px-3 py-1.5 rounded-xl transition-all ${
                    recipeFilter === d
                      ? 'bg-emerald-700 text-white font-semibold shadow-sm'
                      : 'text-stone-600 hover:text-stone-900'
                  }`}
                >
                  {d}
                </button>
              ))}
            </div>
          </div>

          {loadingRecipes ? (
            <div className="text-center py-12 text-sm text-stone-500 animate-pulse">
              Loading authentic Ayurvedic recipes...
            </div>
          ) : (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 pt-2">
              {filteredRecipes.map((recipe) => (
                <div
                  key={recipe.id}
                  className="bg-stone-50/60 rounded-2xl border border-stone-200/80 overflow-hidden hover:shadow-md transition-all flex flex-col group cursor-pointer"
                  onClick={() => openRecipeDetail(recipe.slug)}
                >
                  <div className="h-44 overflow-hidden relative">
                    <img
                      src={recipe.imageUrl || 'https://images.unsplash.com/photo-1546069901-ba9599a7e63c?auto=format&fit=crop&w=800&q=80'}
                      alt={recipe.title}
                      className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300"
                    />
                    <div className="absolute top-3 left-3 bg-stone-900/75 backdrop-blur-md text-white text-[11px] font-medium px-2.5 py-1 rounded-lg">
                      {recipe.doshaSuitability}
                    </div>
                    <div className="absolute top-3 right-3 bg-emerald-600/90 text-white text-[11px] font-medium px-2.5 py-1 rounded-lg">
                      {recipe.mealType}
                    </div>
                  </div>

                  <div className="p-5 flex-1 flex flex-col justify-between space-y-3">
                    <div>
                      <h4 className="font-serif font-bold text-base text-stone-900 group-hover:text-emerald-700 transition-colors">
                        {recipe.title}
                      </h4>
                      {recipe.sanskritName && (
                        <p className="text-xs text-emerald-800 font-medium mt-0.5">
                          {recipe.sanskritName}
                        </p>
                      )}
                      <p className="text-xs text-stone-600 mt-2 line-clamp-2">
                        {recipe.description}
                      </p>
                    </div>

                    <div className="pt-3 border-t border-stone-200 flex items-center justify-between text-[11px] text-stone-500">
                      <div className="flex items-center gap-1.5">
                        <Clock className="w-3.5 h-3.5 text-stone-400" />
                        <span>{recipe.cookTimeMinutes} mins</span>
                      </div>
                      <div className="flex items-center gap-1.5">
                        <Users className="w-3.5 h-3.5 text-stone-400" />
                        <span>{recipe.servings} Servings</span>
                      </div>
                      <span className="text-emerald-700 font-semibold group-hover:underline flex items-center">
                        View Recipe <ChevronRight className="w-3 h-3 ml-0.5" />
                      </span>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>

        {/* Recipe Detail Modal */}
        {selectedRecipe && (
          <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/60 backdrop-blur-sm animate-fade-in">
            <div className="bg-white rounded-3xl max-w-2xl w-full max-h-[90vh] overflow-y-auto shadow-2xl border border-stone-200 p-6 sm:p-8 space-y-6 relative">
              <button
                onClick={() => setSelectedRecipe(null)}
                className="absolute top-5 right-5 p-2 rounded-full bg-stone-100 hover:bg-stone-200 text-stone-700 transition-colors"
              >
                <X className="w-5 h-5" />
              </button>

              <div className="space-y-2">
                <div className="flex items-center gap-2">
                  <span className="text-xs font-semibold px-2.5 py-0.5 rounded-full bg-emerald-100 text-emerald-800">
                    {selectedRecipe.doshaSuitability} Pacifier
                  </span>
                  <span className="text-xs font-semibold px-2.5 py-0.5 rounded-full bg-stone-100 text-stone-700">
                    {selectedRecipe.mealType}
                  </span>
                </div>
                <h3 className="text-2xl font-serif font-bold text-stone-900">
                  {selectedRecipe.title}
                </h3>
                {selectedRecipe.sanskritName && (
                  <p className="text-sm font-serif text-emerald-800 font-medium italic">
                    {selectedRecipe.sanskritName}
                  </p>
                )}
              </div>

              <div className="grid grid-cols-3 gap-3 p-3 bg-stone-50 rounded-2xl text-xs text-center border border-stone-200">
                <div>
                  <div className="text-stone-400">Prep Time</div>
                  <div className="font-bold text-stone-800 mt-0.5">{selectedRecipe.prepTimeMinutes} mins</div>
                </div>
                <div>
                  <div className="text-stone-400">Cook Time</div>
                  <div className="font-bold text-stone-800 mt-0.5">{selectedRecipe.cookTimeMinutes} mins</div>
                </div>
                <div>
                  <div className="text-stone-400">Servings</div>
                  <div className="font-bold text-stone-800 mt-0.5">{selectedRecipe.servings} People</div>
                </div>
              </div>

              <div className="space-y-2">
                <h4 className="font-serif font-bold text-sm text-stone-900">Therapeutic Action & Agni Dynamics</h4>
                <p className="text-xs text-stone-600 leading-relaxed bg-emerald-50/50 p-3.5 rounded-xl border border-emerald-100">
                  {selectedRecipe.therapeuticBenefits || selectedRecipe.digestiveActionAgni || selectedRecipe.description}
                </p>
              </div>

              <div className="space-y-2">
                <h4 className="font-serif font-bold text-sm text-stone-900">Ayurvedic Ingredients & Spices</h4>
                <ul className="space-y-1.5 text-xs text-stone-700 list-disc list-inside bg-stone-50 p-4 rounded-xl border border-stone-200">
                  {selectedRecipe.ingredients?.map((ing, i) => (
                    <li key={i}>{ing}</li>
                  ))}
                </ul>
              </div>

              <div className="space-y-2">
                <h4 className="font-serif font-bold text-sm text-stone-900">Classical Preparation Steps</h4>
                <ol className="space-y-2 text-xs text-stone-700 list-decimal list-inside">
                  {selectedRecipe.instructions?.map((inst, i) => (
                    <li key={i} className="leading-relaxed pl-1 pb-1">
                      {inst}
                    </li>
                  ))}
                </ol>
              </div>

              {selectedRecipe.classicalReference && (
                <div className="text-[11px] text-stone-500 pt-3 border-t border-stone-200">
                  <strong>Classical Reference:</strong> {selectedRecipe.classicalReference}
                </div>
              )}
            </div>
          </div>
        )}

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
