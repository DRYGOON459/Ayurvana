import React, { useState } from 'react';
import { Eye, Sparkles, AlertTriangle, CheckCircle2, RefreshCw, BookOpen, HeartPulse, Info, HelpCircle } from 'lucide-react';
import Disclaimer from '../../components/common/Disclaimer';

const tongueZones = [
  { zone: 'Back (Root of Tongue)', organ: 'Colon, Kidneys, Lower Spine', dosha: 'Vata Zone', desc: 'Reflects the health of Apana Vayu, renal clearance, and large intestine microbiome.' },
  { zone: 'Middle Region', organ: 'Stomach, Spleen, Pancreas', dosha: 'Pitta / Samana Vayu', desc: 'Reflects Jatharagni (central digestive fire) and food enzymatic breakdown.' },
  { zone: 'Sides (Left & Right)', organ: 'Liver (Right) & Spleen (Left)', dosha: 'Pitta (Ranjaka Pitta)', desc: 'Reflects liver detoxification, bile secretion, and emotional stress or anger.' },
  { zone: 'Front / Tip', organ: 'Heart & Lungs (Pranavaha Srotas)', dosha: 'Vata / Kapha', desc: 'Reflects cardiovascular circulation, emotional sadness/grief, and lung ventilation.' }
];

const clinicalObservations = {
  coatings: [
    { id: 'clean', title: 'Thin Translucent White (Healthy)', interpretation: 'Optimal Agni (Digestive Fire)', imbalance: 'Tridoshic Balance', recommendations: 'Maintain current Dinacharya and balanced seasonal nutrition.' },
    { id: 'thick-white', title: 'Thick Heavy White Coating', interpretation: 'Excess Kapha & Metabolic Ama', imbalance: 'Kapha & Low Agni (Mandagni)', recommendations: 'Drink hot ginger water, scrape tongue with copper scraper, avoid cold dairy and heavy sweets. Take Trikatu churna before meals.' },
    { id: 'yellow-green', title: 'Thick Yellow or Greenish Coating', interpretation: 'Excess Pitta Heat & Liver Toxicity', imbalance: 'Pitta Aggravation in GI Tract', recommendations: 'Consume cooling herbal teas (coriander, fennel, mint), avoid deep-fried and spicy foods. Take Triphala or Avipattikar churna.' },
    { id: 'grey-black', title: 'Dark Grey or Brownish Coating', interpretation: 'Severe Vata Imbalance & Dehydration', imbalance: 'Vata Aggravation & Chronic Toxin Stagnation', recommendations: 'Drink warm unctuous broths with ghee, hydrate thoroughly, take gentle warm Triphala with ghee at bedtime.' }
  ],
  shapes: [
    { id: 'normal', title: 'Medium Shape, Smooth Contours', interpretation: 'Balanced Tissue Tone', imbalance: 'Normal Dhatu Nutrition', recommendations: 'Continue regular wholesome routine.' },
    { id: 'scalloped', title: 'Scalloped / Teeth Marks on Edges', interpretation: 'Malabsorption & Spleen Qi / Agni Deficit', imbalance: 'Impaired Nutrient Assimilation (Ama)', recommendations: 'Chew food thoroughly, avoid iced water, sip warm water with roasted cumin and fennel during meals.' },
    { id: 'cracks', title: 'Cracks / Fissures Across Surface', interpretation: 'Chronic Vata Dryness & Nervous Depletion', imbalance: 'High Vata in Rasa & Majja Dhatus', recommendations: 'Incorporate pure Desi Cow Ghee, practice daily Abhyanga oil massage, take Ashwagandha with warm milk.' },
    { id: 'swollen', title: 'Enlarged / Thick / Swollen Body', interpretation: 'Water Retention & Lymphatic Congestion', imbalance: 'Kapha Accumulation in Tissues', recommendations: 'Incorporate light bitter greens, Punarnava tea, and dynamic morning Sun Salutations.' }
  ],
  colors: [
    { id: 'pink', title: 'Healthy Light Coral Pink', interpretation: 'Optimal Blood Oxygenation (Rakta Dhatu)', imbalance: 'Normal Vitality (Ojas)', recommendations: 'Sustain balanced lifestyle.' },
    { id: 'pale', title: 'Pale or Washed Out', interpretation: 'Low Red Blood Cells or Nutrient Deficit', imbalance: 'Anemia / Rasa-Rakta Dhatu Kshaya', recommendations: 'Eat iron-rich foods (pomegranates, soaked black raisins, beetroot), take Amla and Draksharishta.' },
    { id: 'red', title: 'Bright Red / Crimson Tip or Edges', interpretation: 'Internal Heat, Inflammation or High Stress', imbalance: 'Elevated Pitta & Heart Fire', recommendations: 'Practice Sheetali cooling pranayama, drink coconut water, apply Brahmi oil to crown of head.' },
    { id: 'purple', title: 'Purple / Bluish Undertone', interpretation: 'Stagnant Circulation or Oxygen Deprivation', imbalance: 'Vata-Kapha Channel Obstruction (Srotorodha)', recommendations: 'Engage in aerobic brisk walking, take Arjuna bark decoction and ginger.' }
  ]
};

export default function TongueDiagnosis() {
  const [selectedCoating, setSelectedCoating] = useState(clinicalObservations.coatings[0]);
  const [selectedShape, setSelectedShape] = useState(clinicalObservations.shapes[0]);
  const [selectedColor, setSelectedColor] = useState(clinicalObservations.colors[0]);

  return (
    <div className="min-h-screen bg-sand-50 py-10 px-4 sm:px-6 lg:px-8">
      <div className="max-w-6xl mx-auto space-y-10">
        
        {/* Header */}
        <div className="text-center space-y-3">
          <div className="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-emerald-100 text-emerald-800 text-xs font-semibold tracking-wide uppercase">
            <Eye className="w-3.5 h-3.5" />
            Ashtavidha Pariksha • Jihvā Parīkṣā
          </div>
          <h1 className="font-serif text-3xl sm:text-4xl lg:text-5xl font-bold text-earth-950">
            Ayurvedic Tongue Diagnosis Guide
          </h1>
          <p className="text-gray-600 max-w-2xl mx-auto text-base">
            In classical Ayurveda, the tongue is the direct mirror of the gastrointestinal tract and internal organ vitality. Examine your tongue in the morning before brushing.
          </p>
        </div>

        {/* Anatomical Organ Map of the Tongue */}
        <div className="bg-white rounded-3xl p-6 sm:p-8 border border-gray-200 shadow-sm space-y-6">
          <div className="flex items-center justify-between border-b border-gray-100 pb-4">
            <h2 className="font-serif text-xl sm:text-2xl font-bold text-earth-950 flex items-center gap-2.5">
              <BookOpen className="w-6 h-6 text-emerald-700" />
              Classical Organ Topography on the Tongue
            </h2>
            <span className="text-xs font-semibold text-emerald-800 px-3 py-1 rounded-full bg-emerald-50">
              Charaka Samhita Principles
            </span>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
            {tongueZones.map((z, idx) => (
              <div key={idx} className="bg-sand-50/70 p-5 rounded-2xl border border-sand-200/80 space-y-2">
                <span className="text-[10px] font-bold tracking-wider uppercase px-2 py-0.5 rounded-full bg-earth-200 text-earth-900 inline-block">
                  {z.dosha}
                </span>
                <h3 className="font-serif font-bold text-base text-earth-950">{z.zone}</h3>
                <p className="text-xs font-semibold text-emerald-800">Organs: {z.organ}</p>
                <p className="text-xs text-gray-600 leading-relaxed">{z.desc}</p>
              </div>
            ))}
          </div>
        </div>

        {/* Interactive Tongue Diagnostic Selector */}
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
          
          {/* Step 1: Coating */}
          <div className="bg-white rounded-3xl p-6 border border-gray-200 shadow-sm space-y-4">
            <h3 className="font-serif text-lg font-bold text-earth-950 flex items-center gap-2">
              <span className="w-6 h-6 rounded-full bg-emerald-700 text-white text-xs flex items-center justify-center font-sans font-bold">1</span>
              Tongue Coating (Ama)
            </h3>
            <div className="space-y-2.5">
              {clinicalObservations.coatings.map((c) => (
                <button
                  key={c.id}
                  onClick={() => setSelectedCoating(c)}
                  className={`w-full text-left p-3.5 rounded-2xl border text-xs font-medium transition-all ${
                    selectedCoating.id === c.id
                      ? 'border-emerald-600 bg-emerald-50 text-emerald-950 font-bold shadow-sm'
                      : 'border-gray-200 hover:border-emerald-300 text-gray-700'
                  }`}
                >
                  {c.title}
                </button>
              ))}
            </div>
          </div>

          {/* Step 2: Shape & Margins */}
          <div className="bg-white rounded-3xl p-6 border border-gray-200 shadow-sm space-y-4">
            <h3 className="font-serif text-lg font-bold text-earth-950 flex items-center gap-2">
              <span className="w-6 h-6 rounded-full bg-emerald-700 text-white text-xs flex items-center justify-center font-sans font-bold">2</span>
              Tongue Shape & Edges
            </h3>
            <div className="space-y-2.5">
              {clinicalObservations.shapes.map((s) => (
                <button
                  key={s.id}
                  onClick={() => setSelectedShape(s)}
                  className={`w-full text-left p-3.5 rounded-2xl border text-xs font-medium transition-all ${
                    selectedShape.id === s.id
                      ? 'border-emerald-600 bg-emerald-50 text-emerald-950 font-bold shadow-sm'
                      : 'border-gray-200 hover:border-emerald-300 text-gray-700'
                  }`}
                >
                  {s.title}
                </button>
              ))}
            </div>
          </div>

          {/* Step 3: Color & Tone */}
          <div className="bg-white rounded-3xl p-6 border border-gray-200 shadow-sm space-y-4">
            <h3 className="font-serif text-lg font-bold text-earth-950 flex items-center gap-2">
              <span className="w-6 h-6 rounded-full bg-emerald-700 text-white text-xs flex items-center justify-center font-sans font-bold">3</span>
              Tongue Body Color
            </h3>
            <div className="space-y-2.5">
              {clinicalObservations.colors.map((col) => (
                <button
                  key={col.id}
                  onClick={() => setSelectedColor(col)}
                  className={`w-full text-left p-3.5 rounded-2xl border text-xs font-medium transition-all ${
                    selectedColor.id === col.id
                      ? 'border-emerald-600 bg-emerald-50 text-emerald-950 font-bold shadow-sm'
                      : 'border-gray-200 hover:border-emerald-300 text-gray-700'
                  }`}
                >
                  {col.title}
                </button>
              ))}
            </div>
          </div>
        </div>

        {/* Dynamic Clinical Synthesis Result */}
        <div className="bg-gradient-to-br from-earth-900 to-earth-950 text-white rounded-3xl p-6 sm:p-8 shadow-xl space-y-6">
          <div className="flex items-center gap-3 border-b border-white/10 pb-4">
            <Sparkles className="w-6 h-6 text-amber-400" />
            <h3 className="font-serif text-2xl font-bold text-sand-50">
              Synthesized Ayurvedic Assessment
            </h3>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            <div className="bg-white/10 rounded-2xl p-5 border border-white/10 space-y-2">
              <span className="text-[11px] font-bold text-amber-300 uppercase tracking-wider">Metabolic Agni & Ama</span>
              <h4 className="font-serif text-lg font-bold text-white">{selectedCoating.interpretation}</h4>
              <p className="text-xs text-sand-200">{selectedCoating.imbalance}</p>
            </div>

            <div className="bg-white/10 rounded-2xl p-5 border border-white/10 space-y-2">
              <span className="text-[11px] font-bold text-amber-300 uppercase tracking-wider">Tissue Tone & Assimilation</span>
              <h4 className="font-serif text-lg font-bold text-white">{selectedShape.interpretation}</h4>
              <p className="text-xs text-sand-200">{selectedShape.imbalance}</p>
            </div>

            <div className="bg-white/10 rounded-2xl p-5 border border-white/10 space-y-2">
              <span className="text-[11px] font-bold text-amber-300 uppercase tracking-wider">Blood & Cellular Heat</span>
              <h4 className="font-serif text-lg font-bold text-white">{selectedColor.interpretation}</h4>
              <p className="text-xs text-sand-200">{selectedColor.imbalance}</p>
            </div>
          </div>

          <div className="bg-emerald-900/50 border border-emerald-500/30 rounded-2xl p-5 space-y-2">
            <h4 className="font-serif font-bold text-sm text-emerald-300 flex items-center gap-2">
              <CheckCircle2 className="w-4 h-4 text-emerald-400" />
              Tailored Therapeutic Recommendations:
            </h4>
            <ul className="text-xs text-sand-100 space-y-1.5 list-disc pl-5 leading-relaxed">
              <li><strong>Coating Protocol:</strong> {selectedCoating.recommendations}</li>
              <li><strong>Tissue & Shape Support:</strong> {selectedShape.recommendations}</li>
              <li><strong>Color & Thermal Regulation:</strong> {selectedColor.recommendations}</li>
            </ul>
          </div>
        </div>

        {/* Clinical Disclaimer */}
        <Disclaimer text="Jihva Pariksha is a non-invasive educational screening tool. For persistent discoloration, pain, ulcers, or complex systemic illness, consult with our verified Ayurvedic doctors." />
      </div>
    </div>
  );
}
