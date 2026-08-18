import React from 'react';
import { AlertCircle } from 'lucide-react';

export default function Disclaimer({ variant = "general", customText }) {
  const disclaimers = {
    general: "AyurVana provides traditional Ayurvedic knowledge, yoga guides, and wellness educational content. This platform is not a substitute for clinical medical diagnosis, prescription, or emergency care. Always consult a certified healthcare professional before starting any herbal regimen or physical therapy.",
    scanner: "Plant Identification Notice: Automated image recognition is probabilistic. Do not consume, touch, or handle unknown wild plants based solely on image scan results.",
    doctor: "Consultation Notice: All practitioners on AyurVana are verified independently. For acute medical emergencies or immediate life-threatening conditions, please contact your local emergency services immediately."
  };

  const text = customText || disclaimers[variant] || disclaimers.general;

  return (
    <aside aria-label="Medical and safety disclaimer" className="my-6 p-4 rounded-xl bg-earth-50 border border-earth-200/80 text-earth-900 text-xs sm:text-sm leading-relaxed shadow-sm">
      <div className="flex items-start gap-3">
        <AlertCircle className="w-5 h-5 text-earth-700 flex-shrink-0 mt-0.5" />
        <div>
          <strong className="font-semibold block text-earth-900 mb-0.5">
            Important Medical &amp; Educational Disclaimer
          </strong>
          <p className="text-earth-800">{text}</p>
        </div>
      </div>
    </aside>
  );
}
