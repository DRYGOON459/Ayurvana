import React, { useState } from 'react';
import { Search, Sparkles, Filter, ShieldCheck, Heart, Droplet, Flame, Wind, BookOpen, Clock, AlertCircle } from 'lucide-react';
import Disclaimer from '../../components/common/Disclaimer';

const classicalFormulations = [
  {
    id: 'triphala',
    name: 'Triphala Chūrṇa (त्रिफला चूर्ण)',
    englishName: 'Three Fruits Bowel & Rasayana Formulary',
    category: 'Digestion & Detox',
    form: 'Fine Herbal Powder',
    primaryDosha: 'Tridosha Shamaka',
    classicalSource: 'Charaka Samhita, Chikitsasthana Chapter 1',
    description: 'The most renowned polyherbal formulation in Ayurvedic medicine, composed of equal proportions of Amalaki, Haritaki, and Bibhitaki.',
    ingredients: [
      { herb: 'Amalaki (Phyllanthus emblica)', part: 'Fruit pericarp (1 part)', role: 'Pitta pacifier, antioxidant, Vitamin C' },
      { herb: 'Haritaki (Terminalia chebula)', part: 'Fruit rind (1 part)', role: 'Vata pacifier, gentle bowel regulator (Anulomana)' },
      { herb: 'Bibhitaki (Terminalia bellirica)', part: 'Fruit rind (1 part)', role: 'Kapha pacifier, mucus cleanser, lung tonic' }
    ],
    indications: ['Chronic constipation', 'Sluggish digestion (Mandagni)', 'Gut microbiome dysbiosis', 'Ocular strain (Chakshushya)', 'Oral gingivitis'],
    dosage: '3–5 grams at bedtime steeped in warm water.',
    anupana: 'Warm water (general cleansing), Honey (weight balance), Ghee (ocular health).'
  },
  {
    id: 'chyawanprash',
    name: 'Cyavanaprāśa Avaleha (च्यवनप्राश अवलेह)',
    englishName: 'Classical Herbal Rejuvenation Jam',
    category: 'Immunity & Rasayana',
    form: 'Herbal Jam / Electuary (Avaleha)',
    primaryDosha: 'Balances Vata and Pitta',
    classicalSource: 'Charaka Samhita, Chikitsa Sthana (Rasayana Adhyaya)',
    description: 'Formulated by Sage Chyavana to restore youth and immunity, combining fresh wild Amla with over 40 synergistic herbs and honey-ghee lipid base.',
    ingredients: [
      { herb: 'Fresh Amalaki (Amla)', part: 'Pulp (Base)', role: 'High Vitamin C, free-radical scavenger' },
      { herb: 'Ashwagandha & Shatavari', part: 'Roots', role: 'Nervous & reproductive vitality' },
      { herb: 'Pippali & Cardamom', part: 'Spices', role: 'Bioavailability enhancer & respiratory tonic' },
      { herb: 'Raw Honey & Pure Cow Ghee', part: 'Vehicle', role: 'Deep lipid cellular transport into Ojas' }
    ],
    indications: ['Recurrent seasonal colds & bronchitis', 'Chronic physical debility', 'Aging & cellular oxidative stress', 'Childhood immunity', 'Lung capacity deficit'],
    dosage: '1–2 teaspoons (10–15g) twice daily 15 minutes before breakfast and at bedtime.',
    anupana: 'Warm organic cow’s milk or warm almond milk.'
  },
  {
    id: 'trikatu',
    name: 'Trikaṭu Chūrṇa (त्रिकटु चूर्ण)',
    englishName: 'Three Pungents Agni Kindling Formulary',
    category: 'Metabolism & Digestion',
    form: 'Fine Spiced Powder',
    primaryDosha: 'Pacifies Kapha & Vata; increases Pitta',
    classicalSource: 'Ashtanga Hridaya, Sutrasthana Chapter 6',
    description: 'The supreme catalytic herbal combination composed of equal parts Black Pepper, Long Pepper (Pippali), and Dry Ginger (Shunthi).',
    ingredients: [
      { herb: 'Shunthi (Zingiber officinale)', part: 'Dry Rhizome (1 part)', role: 'Agni deepana without aggravating Pitta' },
      { herb: 'Maricha (Piper nigrum)', part: 'Black Peppercorn (1 part)', role: 'Scrapes Ama, stimulates gastric acid' },
      { herb: 'Pippali (Piper longum)', part: 'Long pepper (1 part)', role: 'Bronchial cleanser & bio-enhancer (Yogavahi)' }
    ],
    indications: ['Sluggish metabolism (Mandagni)', 'Chronic sinus congestion', 'Toxic Ama accumulation', 'Nutrient malabsorption', 'High triglycerides'],
    dosage: '500 mg – 1 gram twice daily 10 minutes before meals.',
    anupana: 'Raw unprocessed honey or warm water.'
  },
  {
    id: 'brahmi-ghrita',
    name: 'Brāhmī Ghṛta (ब्राह्मी घृत)',
    englishName: 'Medicated Ghee for Nootropic Cognition',
    category: 'Mind & Memory',
    form: 'Medicated Cow Ghee (Ghrita)',
    primaryDosha: 'Pacifies Vata & Pitta',
    classicalSource: 'Ashtanga Hridaya, Uttarasthana Chapter 6',
    description: 'Pure grass-fed cow ghee infused with fresh Brahmi juice, Shankhpushpi, Vacha, and Kushtha to cross the blood-brain barrier.',
    ingredients: [
      { herb: 'Brahmi (Bacopa monnieri)', part: 'Fresh juice', role: 'Bacosides synaptic enhancement' },
      { herb: 'Shankhpushpi (Convolvulus pluricaulis)', part: 'Whole plant paste', role: 'Cerebral cooling & anxiety relief' },
      { herb: 'Vacha (Acorus calamus)', part: 'Rhizome paste', role: 'Speech clarity & channel opening' },
      { herb: 'Pure Desi Cow Ghee', part: 'Lipid Base', role: 'Crosses blood-brain barrier, nourishes Majja' }
    ],
    indications: ['Memory retention deficits', 'ADHD & scattered focus', 'Chronic anxiety & exam stress', 'Speech impediments', 'Neurological recovery'],
    dosage: '5–10 ml (1–2 teaspoons) on an empty stomach in the morning.',
    anupana: 'Warm water or warm milk.'
  },
  {
    id: 'sitopaladi',
    name: 'Sitopalādi Chūrṇa (सितोपलादि चूर्ण)',
    englishName: 'Classical Respiratory & Cough Relief Powder',
    category: 'Respiratory Health',
    form: 'Fine Powder',
    primaryDosha: 'Balances Pitta & Kapha',
    classicalSource: 'Sharangadhara Samhita, Madhyama Khanda Chapter 6',
    description: 'A soothing traditional formulation designed to relieve chest burning, allergic coughs, and throat irritation without drowsiness.',
    ingredients: [
      { herb: 'Mishri (Rock Sugar Crystals)', part: 'Crystals (16 parts)', role: 'Cooling demulcent base' },
      { herb: 'Vanshlochan (Bamboo Manna)', part: 'Silica exudate (8 parts)', role: 'Lung tissue strengthening' },
      { herb: 'Pippali (Piper longum)', part: 'Spikes (4 parts)', role: 'Bronchodilation & expectoration' },
      { herb: 'Ela (Green Cardamom)', part: 'Seeds (2 parts)', role: 'Aromatic throat soothing' },
      { herb: 'Twak (Cinnamon)', part: 'Bark (1 part)', role: 'Antimicrobial & warming' }
    ],
    indications: ['Allergic bronchitis', 'Dry hacking cough', 'Burning sensation in chest', 'Loss of appetite from fever', 'Sore throat'],
    dosage: '2–3 grams mixed into a paste with honey 2–3 times daily.',
    anupana: 'Raw honey and pure ghee (unequal proportions).'
  },
  {
    id: 'avipattikar',
    name: 'Avipattikara Chūrṇa (अविपत्तिकर चूर्ण)',
    englishName: 'Hyperacidity & Pitta Purgation Formulary',
    category: 'Digestion & Detox',
    form: 'Fine Powder',
    primaryDosha: 'Supreme Pitta Shamaka',
    classicalSource: 'Bhaishajya Ratnavali, Amlapitta Chikitsa',
    description: 'The golden standard Ayurvedic remedy for GERD, acid reflux, heartburn, and excessive stomach heat.',
    ingredients: [
      { herb: 'Trivrit (Operculina turpethum)', part: 'Root bark (44 parts)', role: 'Downwards Pitta elimination (Virechana)' },
      { herb: 'Trikatu (Ginger, Pepper, Pippali)', part: 'Spices (3 parts)', role: 'Kindles sluggish Agni without burning' },
      { herb: 'Triphala & Musta', part: 'Fruits & Tubers (3 parts)', role: 'Soothes inflamed gastric mucosa' },
      { herb: 'Mishri (Rock Sugar)', part: 'Crystals (66 parts)', role: 'Neutralizes excess gastric acid' }
    ],
    indications: ['Acid reflux & GERD', 'Heartburn (Amlapitta)', 'Gastric ulcers & sour burps', 'Constipation associated with high Pitta', 'Morning nausea'],
    dosage: '3–6 grams before meals or at bedtime.',
    anupana: 'Cold water or coconut water.'
  },
  {
    id: 'mahanarayan-taila',
    name: 'Mahānārāyaṇa Taila (महानारायण तैल)',
    englishName: 'Classical Joint & Neuromuscular Massage Oil',
    category: 'Joints & Mobility',
    form: 'Medicated Topical Oil',
    primaryDosha: 'Supreme Vata Pacifier',
    classicalSource: 'Bhaishajya Ratnavali, Vatavyadhi Chikitsa',
    description: 'An elaborate formulation of 50+ herbs boiled in pure sesame oil and milk to lubricate joints and soothe chronic musculoskeletal pain.',
    ingredients: [
      { herb: 'Dashamoola (Ten Sacred Roots)', part: 'Roots decoction', role: 'Deep anti-inflammatory & Vata pacification' },
      { herb: 'Bala & Ashwagandha', part: 'Roots', role: 'Nerve & muscle rejuvenation' },
      { herb: 'Shatavari & Rasna', part: 'Rhizomes', role: 'Joint synovial lubrication' },
      { herb: 'Pure Sesame Oil & Cow Milk', part: 'Base', role: 'Deep transdermal penetration' }
    ],
    indications: ['Osteoarthritis (Sandhigata Vata)', 'Lower back stiffness & sciatica', 'Cervical spondylosis', 'Frozen shoulder', 'Post-workout recovery'],
    dosage: 'Apply warm oil generously over affected joints; massage in circular motions followed by hot water compress.',
    anupana: 'External application strictly.'
  },
  {
    id: 'kumkumadi-taila',
    name: 'Kuṅkumādi Taila (कुङ्कुमादि तैल)',
    englishName: 'Miraculous Saffron Complexion Beauty Elixir',
    category: 'Skin & Beauty',
    form: 'Facial Oil Elixir',
    primaryDosha: 'Balances Pitta & Vata in skin',
    classicalSource: 'Ashtanga Hridaya, Uttarasthana Chapter 32',
    description: 'The legendary Ayurvedic golden skin oil infused with pure Kashmiri Saffron, Red Sandalwood, Lotus stamen, and Manjistha.',
    ingredients: [
      { herb: 'Kumkuma (Kashmiri Saffron Stigmas)', part: 'Stigmas', role: 'Cellular radiance & skin tone brightening' },
      { herb: 'Rakta Chandana (Red Sandalwood)', part: 'Heartwood', role: 'Cools inflammation & clears hyperpigmentation' },
      { herb: 'Manjistha & Ushira', part: 'Roots', role: 'Lymphatic blood purifier & skin detox' },
      { herb: 'Pure Goat Milk & Sesame Oil', part: 'Base', role: 'Deep lipid nourishing carrier' }
    ],
    indications: ['Hyperpigmentation & dark spots', 'Dull skin texture', 'Fine lines & premature aging', 'Acne scars (Mukhadushika)', 'Under-eye dark circles'],
    dosage: 'Apply 3–5 drops to clean, damp face at night; massage gently until absorbed.',
    anupana: 'Topical facial application.'
  }
];

const categories = ['All', 'Digestion & Detox', 'Immunity & Rasayana', 'Mind & Memory', 'Respiratory Health', 'Joints & Mobility', 'Skin & Beauty'];

export default function ClassicalRemedies() {
  const [selectedCat, setSelectedCat] = useState('All');
  const [searchTerm, setSearchTerm] = useState('');

  const filtered = classicalFormulations.filter(item => {
    const matchesCat = selectedCat === 'All' || item.category === selectedCat;
    const matchesSearch = item.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
                          item.englishName.toLowerCase().includes(searchTerm.toLowerCase()) ||
                          item.description.toLowerCase().includes(searchTerm.toLowerCase()) ||
                          item.indications.some(i => i.toLowerCase().includes(searchTerm.toLowerCase()));
    return matchesCat && matchesSearch;
  });

  return (
    <div className="min-h-screen bg-sand-50 py-10 px-4 sm:px-6 lg:px-8">
      <div className="max-w-7xl mx-auto space-y-10">
        
        {/* Header */}
        <div className="text-center space-y-3">
          <div className="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-amber-100 text-amber-900 text-xs font-semibold tracking-wide uppercase">
            <Sparkles className="w-3.5 h-3.5 text-amber-700" />
            Classical Ayurvedic Formulary
          </div>
          <h1 className="font-serif text-3xl sm:text-4xl lg:text-5xl font-bold text-earth-950">
            Classical Formulations & Yogas
          </h1>
          <p className="text-gray-600 max-w-2xl mx-auto text-base">
            Explore time-tested polyherbal Churnas, Avalehas, Ghritas, and Tailas documented in the classical Samhitas with precise ingredients and Anupana carrier guidelines.
          </p>
        </div>

        {/* Search & Category Filter Bar */}
        <div className="bg-white rounded-3xl p-4 sm:p-6 border border-gray-200 shadow-sm space-y-4">
          <div className="relative">
            <Search className="absolute left-4 top-1/2 -translate-y-1/2 w-5 h-5 text-gray-400" />
            <input
              type="text"
              placeholder="Search formulations by name, ailment (e.g. Acid Reflux, Cough, Arthritis, Memory), or ingredient..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="w-full pl-12 pr-4 py-3.5 rounded-2xl bg-sand-50 border border-gray-200 text-earth-950 placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-emerald-600 text-sm"
            />
          </div>

          <div className="flex items-center gap-2 overflow-x-auto pb-2 scrollbar-none">
            {categories.map((cat) => (
              <button
                key={cat}
                onClick={() => setSelectedCat(cat)}
                className={`px-4 py-2 rounded-xl text-xs font-semibold whitespace-nowrap transition-all ${
                  selectedCat === cat
                    ? 'bg-earth-900 text-white shadow-sm'
                    : 'bg-sand-100 text-earth-800 hover:bg-sand-200'
                }`}
              >
                {cat}
              </button>
            ))}
          </div>
        </div>

        {/* Formulations Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          {filtered.map((item) => (
            <div
              key={item.id}
              className="bg-white rounded-3xl border border-gray-200 p-6 sm:p-7 shadow-sm hover:shadow-md transition-all flex flex-col justify-between space-y-6"
            >
              <div className="space-y-4">
                <div className="flex items-start justify-between gap-3">
                  <div>
                    <span className="text-[11px] font-bold uppercase tracking-wider px-2.5 py-1 rounded-full bg-emerald-100 text-emerald-800 inline-block mb-2">
                      {item.category}
                    </span>
                    <h3 className="font-serif text-xl sm:text-2xl font-bold text-earth-950">
                      {item.name}
                    </h3>
                    <p className="text-xs font-medium text-emerald-700">{item.englishName}</p>
                  </div>
                  <span className="text-xs px-2.5 py-1 rounded-xl bg-sand-100 font-semibold text-earth-800 whitespace-nowrap">
                    {item.form}
                  </span>
                </div>

                <p className="text-xs text-gray-600 leading-relaxed">
                  {item.description}
                </p>

                {/* Key Ingredients */}
                <div className="bg-sand-50/80 rounded-2xl p-4 border border-sand-200/60 space-y-2">
                  <h4 className="text-[11px] font-bold text-earth-950 uppercase tracking-wider flex items-center gap-1.5">
                    <BookOpen className="w-3.5 h-3.5 text-emerald-700" />
                    Key Botanical Ingredients & Pharmacology
                  </h4>
                  <ul className="space-y-1.5">
                    {item.ingredients.map((ing, idx) => (
                      <li key={idx} className="text-xs text-gray-700 flex items-start gap-2">
                        <span className="w-1.5 h-1.5 rounded-full bg-emerald-600 mt-1.5 flex-shrink-0" />
                        <span>
                          <strong>{ing.herb}</strong> ({ing.part}) — <span className="text-gray-500">{ing.role}</span>
                        </span>
                      </li>
                    ))}
                  </ul>
                </div>

                {/* Indications Badges */}
                <div className="space-y-1.5">
                  <span className="text-[11px] font-bold text-gray-500 uppercase tracking-wider">Clinical Indications (Roga-Ghnata):</span>
                  <div className="flex flex-wrap gap-1.5">
                    {item.indications.map((ind, idx) => (
                      <span key={idx} className="text-[11px] px-2.5 py-0.5 rounded-lg bg-gray-100 text-gray-700 font-medium">
                        {ind}
                      </span>
                    ))}
                  </div>
                </div>
              </div>

              {/* Dosage & Anupana Footer */}
              <div className="border-t border-gray-100 pt-4 space-y-2 text-xs">
                <div className="flex items-start gap-2 text-gray-700">
                  <Clock className="w-4 h-4 text-amber-600 flex-shrink-0 mt-0.5" />
                  <span><strong>Recommended Dosage:</strong> {item.dosage}</span>
                </div>
                <div className="flex items-start gap-2 text-emerald-800">
                  <Droplet className="w-4 h-4 text-emerald-600 flex-shrink-0 mt-0.5" />
                  <span><strong>Classical Anupāna (Vehicle):</strong> {item.anupana}</span>
                </div>
                <div className="text-[10px] text-gray-400 italic pt-1">
                  Source: {item.classicalSource}
                </div>
              </div>
            </div>
          ))}
        </div>

        {/* Empty State */}
        {filtered.length === 0 && (
          <div className="text-center py-16 bg-white rounded-3xl border border-gray-200 space-y-3">
            <AlertCircle className="w-10 h-10 text-gray-300 mx-auto" />
            <h3 className="font-serif text-lg font-bold text-gray-700">No matching formulations found</h3>
            <p className="text-xs text-gray-500">Try adjusting your search terms or category filter.</p>
          </div>
        )}

        {/* Clinical Disclaimer */}
        <Disclaimer text="Classical formulations must be taken in appropriate dosage according to age, digestive power (Agni), and individual constitution. Consult a registered BAMS/MD Ayurvedic physician before beginning any internal herbal protocol." />
      </div>
    </div>
  );
}
