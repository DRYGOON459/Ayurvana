import React, { useState } from 'react';
import { HeartPulse, Activity, Sparkles, BookOpen, User, CheckCircle2, ChevronRight, Info } from 'lucide-react';
import Disclaimer from '../../components/common/Disclaimer';

const pulseLevels = [
  {
    finger: 'Index Finger (Tarjani)',
    location: 'Radial artery immediately below styloid process',
    dosha: 'VATA (Air + Ether)',
    animalMovement: 'Sarpa Gati (Serpentine / Snake-like Movement)',
    qualities: 'Fast, thin, irregular, cold, sliding like a creeping serpent.',
    speed: '80 – 95 beats per minute',
    organsSuperficial: 'Large Intestine & Pelvis (Apana Vayu)',
    organsDeep: 'Lungs, Nervous System (Prana & Vyana Vayu)',
    imbalances: 'Anxiety, insomnia, gas, tremors, joint pain, dryness.'
  },
  {
    finger: 'Middle Finger (Madhyama)',
    location: 'Adjacent to index finger on radial pulse',
    dosha: 'PITTA (Fire + Water)',
    animalMovement: 'Manduka Gati (Frogging / Jumping Movement)',
    qualities: 'Strong, bounding, sharp, hot, rhythmic leaps like a hopping frog.',
    speed: '70 – 80 beats per minute',
    organsSuperficial: 'Gallbladder & Stomach (Pachaka Pitta)',
    organsDeep: 'Liver & Blood (Ranjaka Pitta), Heart',
    imbalances: 'Acidity, anger, skin inflammation, hypertension, ulcers.'
  },
  {
    finger: 'Ring Finger (Anamika)',
    location: 'Adjacent to middle finger towards arm',
    dosha: 'KAPHA (Water + Earth)',
    animalMovement: 'Hamsa Gati (Swan / Graceful Floating Movement)',
    qualities: 'Slow, deep, broad, steady, flowing gracefully like a swan on water.',
    speed: '55 – 65 beats per minute',
    organsSuperficial: 'Stomach & Pancreas (Kledaka Kapha)',
    organsDeep: 'Heart, Brain & Joint Synovial Fluid (Tarpana & Shleshaka Kapha)',
    imbalances: 'Lethargy, weight gain, sinus congestion, fluid retention.'
  }
];

export default function NadiPariksha() {
  const [selectedFinger, setSelectedFinger] = useState(pulseLevels[0]);
  const [pulseDepth, setPulseDepth] = useState('superficial'); // 'superficial' | 'deep'

  return (
    <div className="min-h-screen bg-sand-50 py-10 px-4 sm:px-6 lg:px-8">
      <div className="max-w-6xl mx-auto space-y-10">
        
        {/* Header */}
        <div className="text-center space-y-3">
          <div className="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-emerald-100 text-emerald-800 text-xs font-semibold tracking-wide uppercase">
            <HeartPulse className="w-3.5 h-3.5" />
            Ashtavidha Pariksha • Nāḍī Parīkṣā
          </div>
          <h1 className="font-serif text-3xl sm:text-4xl lg:text-5xl font-bold text-earth-950">
            Ayurvedic Pulse Diagnosis (Nadi Pariksha)
          </h1>
          <p className="text-gray-600 max-w-2xl mx-auto text-base">
            Master the 5,000-year-old art of reading physiological bio-frequencies via the radial artery under three diagnostic fingers.
          </p>
        </div>

        {/* Anatomical Pulse Placement Graphic Box */}
        <div className="bg-white rounded-3xl p-6 sm:p-8 border border-gray-200 shadow-sm space-y-6">
          <div className="flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4 border-b border-gray-100 pb-5">
            <div>
              <h2 className="font-serif text-xl sm:text-2xl font-bold text-earth-950">
                Radial Artery 3-Finger Placement
              </h2>
              <p className="text-xs text-gray-500">
                Right hand for Men / Left hand for Women (classical guideline). Palpate in the early morning before food.
              </p>
            </div>
            
            <div className="flex items-center gap-2 bg-sand-100 p-1.5 rounded-2xl">
              <button
                onClick={() => setPulseDepth('superficial')}
                className={`px-4 py-2 rounded-xl text-xs font-bold transition-all ${
                  pulseDepth === 'superficial'
                    ? 'bg-earth-900 text-white shadow-sm'
                    : 'text-earth-800 hover:bg-sand-200'
                }`}
              >
                Superficial Pulse (Vikriti)
              </button>
              <button
                onClick={() => setPulseDepth('deep')}
                className={`px-4 py-2 rounded-xl text-xs font-bold transition-all ${
                  pulseDepth === 'deep'
                    ? 'bg-earth-900 text-white shadow-sm'
                    : 'text-earth-800 hover:bg-sand-200'
                }`}
              >
                Deep Pulse (Prakriti)
              </button>
            </div>
          </div>

          {/* Three Fingers Interactive Bar */}
          <div className="grid grid-cols-1 md:grid-cols-3 gap-5">
            {pulseLevels.map((p, idx) => {
              const isSelected = selectedFinger.finger === p.finger;

              return (
                <div
                  key={idx}
                  onClick={() => setSelectedFinger(p)}
                  className={`p-6 rounded-3xl border transition-all cursor-pointer space-y-4 ${
                    isSelected
                      ? 'border-emerald-600 bg-emerald-50/50 shadow-md ring-2 ring-emerald-500/20'
                      : 'border-gray-200 bg-white hover:border-emerald-300'
                  }`}
                >
                  <div className="flex items-center justify-between">
                    <span className="text-xs font-bold text-gray-500 uppercase">Finger #{idx + 1}</span>
                    <span className={`text-[11px] font-bold px-2.5 py-0.5 rounded-full ${
                      idx === 0 ? 'bg-blue-100 text-blue-800' :
                      idx === 1 ? 'bg-amber-100 text-amber-800' : 'bg-emerald-100 text-emerald-800'
                    }`}>
                      {p.dosha.split(' ')[0]}
                    </span>
                  </div>

                  <h3 className="font-serif text-lg font-bold text-earth-950">{p.finger}</h3>
                  <p className="text-xs font-semibold text-emerald-800 italic">{p.animalMovement}</p>

                  <div className="pt-2 border-t border-gray-100 space-y-1.5 text-xs text-gray-600">
                    <div><strong>Pulse Rate:</strong> {p.speed}</div>
                    <div><strong>Movement:</strong> {p.qualities}</div>
                  </div>
                </div>
              );
            })}
          </div>
        </div>

        {/* Deep Dive Explorer for Selected Finger */}
        <div className="bg-gradient-to-br from-earth-900 to-earth-950 text-white rounded-3xl p-6 sm:p-8 shadow-xl space-y-6">
          <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 border-b border-white/10 pb-5">
            <div className="space-y-1">
              <div className="flex items-center gap-2 text-amber-400 text-xs font-bold uppercase tracking-wider">
                <Activity className="w-4 h-4" />
                Diagnostic Examination Profile
              </div>
              <h3 className="font-serif text-2xl sm:text-3xl font-bold text-sand-50">
                {selectedFinger.finger} — {selectedFinger.dosha}
              </h3>
            </div>

            <div className="bg-white/10 px-4 py-2 rounded-2xl border border-white/10 text-xs font-bold text-amber-300">
              Examining: {pulseDepth === 'superficial' ? 'Superficial Level (Current Imbalance)' : 'Deep Level (Constitutional Blueprint)'}
            </div>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            <div className="bg-white/10 rounded-2xl p-5 border border-white/10 space-y-2">
              <span className="text-[11px] font-bold text-amber-300 uppercase tracking-wider">Animal Movement (Gati)</span>
              <h4 className="font-serif text-lg font-bold text-white">{selectedFinger.animalMovement}</h4>
              <p className="text-xs text-sand-200">{selectedFinger.qualities}</p>
            </div>

            <div className="bg-white/10 rounded-2xl p-5 border border-white/10 space-y-2">
              <span className="text-[11px] font-bold text-amber-300 uppercase tracking-wider">
                {pulseDepth === 'superficial' ? 'Superficial Organs' : 'Deep Tissue Organs'}
              </span>
              <h4 className="font-serif text-lg font-bold text-white">
                {pulseDepth === 'superficial' ? selectedFinger.organsSuperficial : selectedFinger.organsDeep}
              </h4>
              <p className="text-xs text-sand-200">Palpated with {pulseDepth === 'superficial' ? 'light feather pressure' : 'firm downward occlusion'}.</p>
            </div>

            <div className="bg-white/10 rounded-2xl p-5 border border-white/10 space-y-2">
              <span className="text-[11px] font-bold text-amber-300 uppercase tracking-wider">Pathological Indicators</span>
              <h4 className="font-serif text-lg font-bold text-white">Clinical Symptoms</h4>
              <p className="text-xs text-sand-200">{selectedFinger.imbalances}</p>
            </div>
          </div>
        </div>

        {/* Clinical Disclaimer */}
        <Disclaimer text="Nadi Pariksha requires years of dedicated clinical practice under an accomplished Vaidya. This digital module is intended for educational understanding of classical Ayurvedic diagnostic sciences." />
      </div>
    </div>
  );
}
