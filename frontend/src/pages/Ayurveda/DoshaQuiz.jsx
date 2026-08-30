import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { Compass, RotateCcw, CheckCircle2, ArrowRight, ShieldCheck, Sparkles } from 'lucide-react';
import Disclaimer from '../../components/common/Disclaimer';

const questions = [
  {
    id: 1,
    category: "Physical Frame",
    question: "Which best describes your natural body frame and bone structure?",
    options: [
      { text: "Slender, light bones, difficulty gaining weight", dosha: "VATA" },
      { text: "Medium athletic build, good muscle definition, moderate weight", dosha: "PITTA" },
      { text: "Sturdy, broad frame, heavy bones, easily gains weight", dosha: "KAPHA" }
    ]
  },
  {
    id: 2,
    category: "Skin & Temperature",
    question: "How does your skin feel and how do you react to weather?",
    options: [
      { text: "Skin tends toward dryness or coolness; easily feel cold", dosha: "VATA" },
      { text: "Skin is warm, slightly sensitive/red; easily overheat and sweat", dosha: "PITTA" },
      { text: "Skin is smooth, soft, moist or oily; tolerate cold well but dislike dampness", dosha: "KAPHA" }
    ]
  },
  {
    id: 3,
    category: "Digestion & Appetite",
    question: "How would you characterize your natural appetite and digestion (Agni)?",
    options: [
      { text: "Irregular: sometimes very hungry, sometimes forget to eat; prone to gas/bloating", dosha: "VATA" },
      { text: "Sharp & intense: become irritable if meals are delayed; fast digestion", dosha: "PITTA" },
      { text: "Steady & slow: can easily skip meals without irritability; slow digestion", dosha: "KAPHA" }
    ]
  },
  {
    id: 4,
    category: "Sleep Patterns",
    question: "What is your typical sleep experience?",
    options: [
      { text: "Light, interrupted, active dreams; wake up early or feeling ungrounded", dosha: "VATA" },
      { text: "Moderate (6–7 hours); fall asleep easily but may wake up feeling warm", dosha: "PITTA" },
      { text: "Deep, heavy, hard to wake in the morning; love 8+ hours of sleep", dosha: "KAPHA" }
    ]
  },
  {
    id: 5,
    category: "Mental Disposition & Stress",
    question: "How do you naturally respond under acute stress or mental pressure?",
    options: [
      { text: "Anxiety, worry, rapid scattered thoughts, restlessness", dosha: "VATA" },
      { text: "Impatience, frustration, intensity, perfectionism", dosha: "PITTA" },
      { text: "Withdrawal, stubbornness, procrastination, seeking comfort food", dosha: "KAPHA" }
    ]
  },
  {
    id: 6,
    category: "Pace of Action & Speech",
    question: "What is your typical speed of walking, talking, and learning new concepts?",
    options: [
      { text: "Quick and energetic; learn quickly and forget quickly", dosha: "VATA" },
      { text: "Purposeful and articulate; precise and convincing", dosha: "PITTA" },
      { text: "Deliberate and methodical; slow to absorb but retain information forever", dosha: "KAPHA" }
    ]
  }
];

export default function DoshaQuiz() {
  const [currentStep, setCurrentStep] = useState(0);
  const [answers, setAnswers] = useState({});
  const [result, setResult] = useState(null);

  const handleSelect = (dosha) => {
    const nextAnswers = { ...answers, [currentStep]: dosha };
    setAnswers(nextAnswers);

    if (currentStep < questions.length - 1) {
      setCurrentStep(currentStep + 1);
    } else {
      calculateResult(nextAnswers);
    }
  };

  const calculateResult = (finalAnswers) => {
    const scores = { VATA: 0, PITTA: 0, KAPHA: 0 };
    Object.values(finalAnswers).forEach((dosha) => {
      scores[dosha] = (scores[dosha] || 0) + 1;
    });

    const total = questions.length;
    const percentages = {
      VATA: Math.round((scores.VATA / total) * 100),
      PITTA: Math.round((scores.PITTA / total) * 100),
      KAPHA: Math.round((scores.KAPHA / total) * 100)
    };

    let dominant = "VATA";
    if (scores.PITTA > scores.VATA && scores.PITTA >= scores.KAPHA) dominant = "PITTA";
    if (scores.KAPHA > scores.VATA && scores.KAPHA > scores.PITTA) dominant = "KAPHA";

    setResult({ scores, percentages, dominant });
  };

  const resetQuiz = () => {
    setAnswers({});
    setCurrentStep(0);
    setResult(null);
  };

  const currentQ = questions[currentStep];

  return (
    <div className="max-w-3xl mx-auto px-4 sm:px-6 lg:px-8 py-10 space-y-8">
      <header className="text-center space-y-3">
        <div className="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-ayur-100 text-ayur-800 text-xs font-semibold uppercase tracking-wider">
          <Compass className="w-3.5 h-3.5" />
          <span>Self-Reflection Tool</span>
        </div>
        <h1 className="font-serif text-3xl sm:text-4xl font-bold text-ayur-950">
          Discover Your Ayurvedic Prakriti
        </h1>
        <p className="text-sm text-gray-600 max-w-xl mx-auto leading-relaxed">
          Reflect on your lifelong physical, physiological, and emotional predispositions to identify your primary Dosha constitution.
        </p>
      </header>

      {/* Mandatory Clinical Disclaimer Alert */}
      <div className="p-4 rounded-xl bg-amber-50 border border-amber-200 text-amber-900 text-xs leading-relaxed flex items-start gap-3">
        <ShieldCheck className="w-5 h-5 text-amber-700 flex-shrink-0 mt-0.5" />
        <p>
          <strong>Educational Notice:</strong> This Prakriti self-assessment is designed solely for self-reflection and educational awareness. It is <strong>not a medical diagnosis</strong>. Clinical assessment of Prakriti and Vikriti requires physical pulse analysis (Nadi Pariksha) by a licensed BAMS/MD Ayurvedic physician.
        </p>
      </div>

      {!result ? (
        <div className="p-6 sm:p-8 rounded-3xl bg-white border border-ayur-200/80 shadow-md space-y-6">
          {/* Progress Bar */}
          <div className="space-y-2">
            <div className="flex justify-between text-xs font-semibold text-gray-500 uppercase tracking-wider">
              <span>Question {currentStep + 1} of {questions.length}</span>
              <span>{currentQ.category}</span>
            </div>
            <div className="w-full h-2 rounded-full bg-gray-100 overflow-hidden">
              <div
                className="h-full bg-ayur-600 transition-all duration-300 rounded-full"
                style={{ width: `${((currentStep + 1) / questions.length) * 100}%` }}
              />
            </div>
          </div>

          {/* Question Text */}
          <h2 className="font-serif text-xl sm:text-2xl font-bold text-gray-900 leading-snug">
            {currentQ.question}
          </h2>

          {/* Options */}
          <div className="space-y-3 pt-2">
            {currentQ.options.map((opt, idx) => (
              <button
                key={idx}
                onClick={() => handleSelect(opt.dosha)}
                className="w-full text-left p-4 rounded-2xl border border-gray-200 hover:border-ayur-400 hover:bg-ayur-50/60 transition-all flex items-center justify-between group text-sm sm:text-base font-medium text-gray-800"
              >
                <span>{opt.text}</span>
                <ArrowRight className="w-4 h-4 text-gray-400 group-hover:text-ayur-700 group-hover:translate-x-1 transition-all" />
              </button>
            ))}
          </div>
        </div>
      ) : (
        /* Results View */
        <div className="p-6 sm:p-10 rounded-3xl bg-white border border-ayur-200 shadow-lg space-y-8 animate-in fade-in duration-300">
          <div className="text-center space-y-3 border-b border-gray-100 pb-6">
            <Sparkles className="w-8 h-8 text-amber-500 mx-auto" />
            <h2 className="font-serif text-3xl font-bold text-ayur-950">
              Primary Constitution: <span className="text-ayur-700">{result.dominant}</span>
            </h2>
            <p className="text-sm text-gray-600">
              Based on your answers, your biological profile shows prominent {result.dominant} tendencies.
            </p>
          </div>

          {/* Distribution Bars */}
          <div className="space-y-4">
            <h3 className="text-xs uppercase font-bold tracking-wider text-gray-500">
              Constitutional Harmony Profile
            </h3>
            <div className="space-y-3">
              <div>
                <div className="flex justify-between text-xs font-semibold mb-1 text-sky-900">
                  <span>Vata (Ether + Air)</span>
                  <span>{result.percentages.VATA}%</span>
                </div>
                <div className="w-full h-2.5 rounded-full bg-sky-100">
                  <div className="h-full bg-sky-500 rounded-full" style={{ width: `${result.percentages.VATA}%` }} />
                </div>
              </div>

              <div>
                <div className="flex justify-between text-xs font-semibold mb-1 text-amber-900">
                  <span>Pitta (Fire + Water)</span>
                  <span>{result.percentages.PITTA}%</span>
                </div>
                <div className="w-full h-2.5 rounded-full bg-amber-100">
                  <div className="h-full bg-amber-500 rounded-full" style={{ width: `${result.percentages.PITTA}%` }} />
                </div>
              </div>

              <div>
                <div className="flex justify-between text-xs font-semibold mb-1 text-emerald-900">
                  <span>Kapha (Water + Earth)</span>
                  <span>{result.percentages.KAPHA}%</span>
                </div>
                <div className="w-full h-2.5 rounded-full bg-emerald-100">
                  <div className="h-full bg-emerald-500 rounded-full" style={{ width: `${result.percentages.KAPHA}%` }} />
                </div>
              </div>
            </div>
          </div>

          {/* Recommendations Card */}
          <div className="p-6 rounded-2xl bg-ayur-50/80 border border-ayur-200 space-y-3 text-sm text-gray-800">
            <h4 className="font-serif font-bold text-base text-ayur-900">
              {result.dominant === 'VATA' && 'Balancing Guidelines for Vata'}
              {result.dominant === 'PITTA' && 'Balancing Guidelines for Pitta'}
              {result.dominant === 'KAPHA' && 'Balancing Guidelines for Kapha'}
            </h4>
            <ul className="list-disc pl-5 space-y-1.5 text-xs sm:text-sm text-gray-700">
              {result.dominant === 'VATA' && (
                <>
                  <li>Favor warm, nourishing, grounding meals with healthy oils (ghee, sesame).</li>
                  <li>Maintain a consistent daily sleep schedule to stabilize the nervous system.</li>
                  <li>Engage in slow, restorative yoga (e.g. Tadasana, Savasana) and warming herbal infusions.</li>
                </>
              )}
              {result.dominant === 'PITTA' && (
                <>
                  <li>Favor cooling foods with sweet, bitter, and astringent tastes; minimize excess spice and alcohol.</li>
                  <li>Practice moderate, non-competitive exercise and cool evening strolls.</li>
                  <li>Incorporate Sheetali cooling pranayama and relaxing walks in nature.</li>
                </>
              )}
              {result.dominant === 'KAPHA' && (
                <>
                  <li>Favor light, warm, stimulating meals with warming spices like ginger and black pepper.</li>
                  <li>Incorporate vigorous daily movement (Surya Namaskar, active brisk walks).</li>
                  <li>Avoid heavy daytime naps and excessively sweet or oily dairy products.</li>
                </>
              )}
            </ul>
          </div>

          {/* Actions */}
          <div className="flex flex-wrap items-center justify-between gap-4 pt-4 border-t border-gray-100">
            <button
              onClick={resetQuiz}
              className="inline-flex items-center gap-2 px-5 py-2.5 rounded-full border border-gray-200 text-gray-700 text-xs font-semibold hover:bg-gray-50 transition-colors"
            >
              <RotateCcw className="w-3.5 h-3.5" />
              <span>Retake Quiz</span>
            </button>

            <Link
              to="/doctors"
              className="inline-flex items-center gap-2 px-6 py-2.5 rounded-full bg-ayur-800 text-white text-xs font-semibold hover:bg-ayur-700 shadow-sm transition-all"
            >
              <span>Consult an Ayurvedic Doctor</span>
              <ArrowRight className="w-3.5 h-3.5" />
            </Link>
          </div>
        </div>
      )}
    </div>
  );
}
