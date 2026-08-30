import React from 'react';
import { AlertTriangle, ShieldAlert, ShieldCheck } from 'lucide-react';

export default function SafetyWarning({ toxicityLevel, contraindications, pregnancySafety, interactionWarnings }) {
  if (!toxicityLevel || toxicityLevel === 'SAFE') {
    return (
      <div className="p-4 rounded-xl bg-emerald-50 border border-emerald-200 text-emerald-900 text-xs sm:text-sm space-y-2">
        <div className="flex items-center gap-2 font-semibold text-emerald-800">
          <ShieldCheck className="w-5 h-5 text-emerald-600 flex-shrink-0" />
          <span>Safety Profile: Generally Recognized as Safe in Standard Dosage</span>
        </div>
        {pregnancySafety && <p><strong className="text-emerald-950">Pregnancy &amp; Lactation:</strong> {pregnancySafety}</p>}
        {contraindications && <p><strong className="text-emerald-950">Contraindications:</strong> {contraindications}</p>}
      </div>
    );
  }

  const isDeadly = toxicityLevel === 'DEADLY' || toxicityLevel === 'TOXIC';

  return (
    <div className={`p-5 rounded-2xl border ${
      isDeadly ? 'bg-rose-50 border-rose-300 text-rose-950' : 'bg-amber-50 border-amber-300 text-amber-950'
    } space-y-3 shadow-sm`}>
      <div className="flex items-start gap-3">
        {isDeadly ? (
          <ShieldAlert className="w-6 h-6 text-rose-600 flex-shrink-0 mt-0.5" />
        ) : (
          <AlertTriangle className="w-6 h-6 text-amber-600 flex-shrink-0 mt-0.5" />
        )}
        <div>
          <h4 className="font-bold text-sm sm:text-base uppercase tracking-wide">
            {isDeadly ? '⚠ Strict Toxicity Warning & Handling Precautions' : '⚠ Caution & Clinical Interaction Warning'}
          </h4>
          <p className="text-xs sm:text-sm mt-1 leading-relaxed opacity-90">
            Toxicity Classification: <span className="font-bold">{toxicityLevel}</span>. Use strictly under the guidance of a qualified Ayurvedic physician.
          </p>
        </div>
      </div>

      <div className="text-xs sm:text-sm space-y-1.5 pt-2 border-t border-current/10">
        {contraindications && (
          <p><strong>Contraindications:</strong> {contraindications}</p>
        )}
        {pregnancySafety && (
          <p><strong>Pregnancy:</strong> {pregnancySafety}</p>
        )}
        {interactionWarnings && (
          <p><strong>Known Interactions:</strong> {interactionWarnings}</p>
        )}
      </div>
    </div>
  );
}
