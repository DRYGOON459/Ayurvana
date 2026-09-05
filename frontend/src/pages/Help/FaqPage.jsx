import React, { useState } from 'react';
import { Search, ChevronDown, HelpCircle, ShieldCheck, Video, HeartPulse, Stethoscope, Sparkles } from 'lucide-react';
import Disclaimer from '../../components/common/Disclaimer';

const faqs = [
  {
    category: 'Consultations & Telemedicine',
    question: 'How do live online Ayurvedic consultations work on AyurVana?',
    answer: 'You can book an appointment with any verified BAMS/MD Ayurvedic physician. At the appointment time, you enter a private, encrypted Consultation Room equipped with WebRTC peer-to-peer video streaming, real-time clinical chat, and Ashtavidha Pariksha (visual tongue and eye examination guidance). The doctor will issue a digital prescription and clinical formulation notes directly to your dashboard.'
  },
  {
    category: 'Consultations & Telemedicine',
    question: 'How does the doctor perform Nadi Pariksha (Pulse Diagnosis) online?',
    answer: 'While direct physical radial pulse diagnosis requires touch, classical Ayurveda incorporates Ashtavidha Pariksha (8-fold examination) and Dashavidha Pariksha (10-fold clinical evaluation). In an online setting, your doctor evaluates Jihva (tongue coat and cracks), Drik (eyes and sclera), Akriti (facial structure and body constitution), voice acoustics, detailed digestive history, sleep architecture, and symptom chronobiology.'
  },
  {
    category: 'Consultations & Telemedicine',
    question: 'Are the prescriptions legal and valid for purchasing Ayurvedic medicines?',
    answer: 'Yes. All doctors on AyurVana are licensed practitioners holding recognized BAMS (Bachelor of Ayurvedic Medicine and Surgery) or MD/MS degrees registered with their respective State Medical Councils (e.g. Maharashtra Council of Indian Medicine, Travancore-Cochin Medical Council). Digital prescriptions issued through the platform contain the doctor’s verified registration number and qualifications.'
  },
  {
    category: 'Prakriti & Doshas',
    question: 'What is the difference between Prakriti and Vikriti?',
    answer: 'Prakriti is your unique, inborn constitutional baseline determined at conception by the genetic and doshic state of your parents (e.g., Vata-Pitta). Vikriti is your current state of imbalance caused by stress, poor diet, weather, or lifestyle. Ayurvedic medicine focuses on pacifying the current Vikriti to guide your physiology back to its natural, harmonious Prakriti.'
  },
  {
    category: 'Prakriti & Doshas',
    question: 'How accurate is the online Dosha Assessment Quiz?',
    answer: 'The AyurVana Dosha Quiz is developed following the classical diagnostic parameters in the Charaka Samhita. It provides an excellent initial baseline assessment of your constitutional tendencies (physical frame, digestive Agni, sleep, mental stress response). For complex chronic conditions, we recommend pairing your quiz results with a 1-on-1 consultation.'
  },
  {
    category: 'Herbs & Botanical Safety',
    question: 'Are Ayurvedic herbs safe to take alongside modern pharmaceutical medications?',
    answer: 'Many classical herbs (such as Ashwagandha, Turmeric, Amla) are gentle and safe. However, certain herbs can interact with pharmaceutical drugs (for example, Guggulu can interact with blood thinners, and Kutki can lower blood sugar). Always review the Safety & Contraindications section on every herb monograph in our Botanical Library, and inform your physician of all prescription medications you take.'
  },
  {
    category: 'Herbs & Botanical Safety',
    question: 'How does the AI Botanical Vision Scanner identify plants?',
    answer: 'The Botanical Vision Scanner analyzes leaf morphology, phyllotaxis, blossom geometry, and fruit capsules using deep botanical classification models. It matches specimens against our verified database to provide classical monographs, pharmacological actions, and instant toxicity hazard alerts (e.g. distinguishing edible leaves from toxic plants like Castor or Dhatura).'
  },
  {
    category: 'Doctor Onboarding',
    question: 'How are doctors verified before they can accept consultations?',
    answer: 'Every prospective doctor must submit government-issued medical license numbers, university degree certificates (BAMS/MD), and State Medical Council registrations. Our administrative board manually audits each submission against State Council registries before granting APPROVED verified status.'
  }
];

const categories = ['All', 'Consultations & Telemedicine', 'Prakriti & Doshas', 'Herbs & Botanical Safety', 'Doctor Onboarding'];

export default function FaqPage() {
  const [selectedCat, setSelectedCat] = useState('All');
  const [searchTerm, setSearchTerm] = useState('');
  const [openIndex, setOpenIndex] = useState(null);

  const toggleFaq = (idx) => {
    setOpenIndex(openIndex === idx ? null : idx);
  };

  const filteredFaqs = faqs.filter((faq) => {
    const matchesCat = selectedCat === 'All' || faq.category === selectedCat;
    const matchesSearch = faq.question.toLowerCase().includes(searchTerm.toLowerCase()) ||
                          faq.answer.toLowerCase().includes(searchTerm.toLowerCase());
    return matchesCat && matchesSearch;
  });

  return (
    <div className="min-h-screen bg-sand-50 py-10 px-4 sm:px-6 lg:px-8">
      <div className="max-w-4xl mx-auto space-y-10">
        
        {/* Header */}
        <div className="text-center space-y-3">
          <div className="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-emerald-100 text-emerald-800 text-xs font-semibold tracking-wide uppercase">
            <HelpCircle className="w-3.5 h-3.5" />
            Knowledge & Patient Support Center
          </div>
          <h1 className="font-serif text-3xl sm:text-4xl lg:text-5xl font-bold text-earth-950">
            Frequently Asked Questions
          </h1>
          <p className="text-gray-600 max-w-xl mx-auto text-base">
            Everything you need to know about Ayurvedic consultations, dosha diagnosis, botanical safety, and platform governance.
          </p>
        </div>

        {/* Search & Filter */}
        <div className="bg-white rounded-3xl p-4 sm:p-6 border border-gray-200 shadow-sm space-y-4">
          <div className="relative">
            <Search className="absolute left-4 top-1/2 -translate-y-1/2 w-5 h-5 text-gray-400" />
            <input
              type="text"
              placeholder="Search questions (e.g. video call, pulse diagnosis, herb safety, prescription)..."
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

        {/* FAQ Accordion List */}
        <div className="space-y-3">
          {filteredFaqs.map((faq, idx) => {
            const isOpen = openIndex === idx;

            return (
              <div
                key={idx}
                className="bg-white rounded-2xl border border-gray-200 shadow-sm overflow-hidden transition-all"
              >
                <button
                  onClick={() => toggleFaq(idx)}
                  className="w-full p-5 text-left flex items-center justify-between gap-4 select-none hover:bg-sand-50/50 transition-colors"
                >
                  <span className="font-serif font-bold text-sm sm:text-base text-earth-950">
                    {faq.question}
                  </span>
                  <ChevronDown
                    className={`w-5 h-5 text-emerald-700 flex-shrink-0 transition-transform duration-200 ${
                      isOpen ? 'rotate-180' : ''
                    }`}
                  />
                </button>

                {isOpen && (
                  <div className="px-5 pb-5 pt-1 text-xs sm:text-sm text-gray-600 leading-relaxed border-t border-gray-100 bg-sand-50/30">
                    {faq.answer}
                  </div>
                )}
              </div>
            );
          })}

          {filteredFaqs.length === 0 && (
            <div className="text-center py-12 bg-white rounded-2xl border border-gray-200 text-sm text-gray-500">
              No matching questions found. Try typing different keywords.
            </div>
          )}
        </div>

        {/* Clinical Disclaimer */}
        <Disclaimer text="AyurVana telemedicine consultations provide integrative guidance based on classical Ayurvedic treatises and registered medical practitioners. In case of acute medical emergencies, immediately contact local emergency healthcare services." />
      </div>
    </div>
  );
}
