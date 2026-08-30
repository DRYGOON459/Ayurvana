import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { Stethoscope, ShieldCheck, Award, FileText, CheckCircle2, AlertCircle, ArrowRight } from 'lucide-react';
import doctorService from '../../services/doctorService';

export default function DoctorOnboarding() {
  const [qualifications, setQualifications] = useState('');
  const [medicalLicenseNumber, setMedicalLicenseNumber] = useState('');
  const [stateMedicalCouncil, setStateMedicalCouncil] = useState('');
  const [yearsOfExperience, setYearsOfExperience] = useState(5);
  const [primarySpecialization, setPrimarySpecialization] = useState('Kayachikitsa');
  const [languagesSpoken, setLanguagesSpoken] = useState('English, Hindi');
  const [consultationFee, setConsultationFee] = useState(800);
  const [bio, setBio] = useState('');

  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState(false);

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSubmitting(true);

    try {
      await doctorService.onboardDoctor({
        qualifications,
        medicalLicenseNumber,
        stateMedicalCouncil,
        yearsOfExperience: parseInt(yearsOfExperience, 10),
        primarySpecialization,
        languagesSpoken,
        consultationFee: parseFloat(consultationFee),
        bio,
      });

      setSuccess(true);
      setTimeout(() => {
        navigate('/doctors');
      }, 2500);
    } catch (err) {
      console.error("Onboarding failed", err);
      setError(err.response?.data?.message || 'Failed to submit credentials. Please verify your entries.');
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="max-w-3xl mx-auto px-4 sm:px-6 lg:px-8 py-10 space-y-8">
      {/* Header */}
      <div className="text-center space-y-2 max-w-xl mx-auto">
        <div className="inline-flex items-center gap-1.5 px-3 py-1 rounded-full bg-blue-100 text-blue-900 text-xs font-semibold uppercase tracking-wider">
          <Stethoscope className="w-3.5 h-3.5" />
          <span>Practitioner Registration</span>
        </div>
        <h1 className="font-serif text-3xl sm:text-4xl font-bold text-gray-950">
          Ayurvedic Doctor Credential Onboarding
        </h1>
        <p className="text-xs sm:text-sm text-gray-600">
          Join AyurVana's verified network of classical physicians and connect with wellness seekers worldwide.
        </p>
      </div>

      {/* Verification Notice */}
      <div className="p-4 rounded-2xl bg-blue-50/70 border border-blue-200 text-blue-950 flex items-start gap-3 text-xs leading-relaxed">
        <ShieldCheck className="w-5 h-5 text-blue-700 flex-shrink-0 mt-0.5" />
        <div>
          <strong className="block font-semibold">Credential Verification Standard:</strong>
          <span>
            Under AyurVana clinical governance, all medical degrees (BAMS / MD / MS Ayurveda) and State Medical Council registrations are strictly verified before enabling patient consultation appointments.
          </span>
        </div>
      </div>

      {/* Form Box */}
      <div className="p-6 sm:p-10 rounded-3xl bg-white border border-gray-200 shadow-sm space-y-6">
        {success && (
          <div className="p-4 rounded-2xl bg-emerald-50 border border-emerald-200 text-emerald-800 text-xs flex items-center gap-2">
            <CheckCircle2 className="w-5 h-5 text-emerald-600 flex-shrink-0" />
            <div>
              <p className="font-bold">Credentials Submitted Successfully!</p>
              <p>Your doctor profile is active. Redirecting you to the doctor directory...</p>
            </div>
          </div>
        )}

        {error && (
          <div className="p-3.5 rounded-xl bg-rose-50 border border-rose-200 text-rose-800 text-xs flex items-center gap-2">
            <AlertCircle className="w-4 h-4 flex-shrink-0" />
            <span>{error}</span>
          </div>
        )}

        <form onSubmit={handleSubmit} className="space-y-5">
          {/* Degree Qualifications */}
          <div>
            <label className="block text-xs font-semibold uppercase tracking-wider text-gray-700 mb-1.5">
              Medical Degrees &amp; University Qualifications *
            </label>
            <input
              type="text"
              required
              value={qualifications}
              onChange={(e) => setQualifications(e.target.value)}
              placeholder="e.g. BAMS (Univ of Mumbai), MD (Ayurveda - Kayachikitsa)"
              className="w-full px-4 py-2.5 rounded-xl border border-gray-300 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all"
            />
          </div>

          {/* License & Council Row */}
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
            <div>
              <label className="block text-xs font-semibold uppercase tracking-wider text-gray-700 mb-1.5">
                Medical License / Registration No. *
              </label>
              <input
                type="text"
                required
                value={medicalLicenseNumber}
                onChange={(e) => setMedicalLicenseNumber(e.target.value)}
                placeholder="e.g. MCIM-AYUR-2018-9921"
                className="w-full px-4 py-2.5 rounded-xl border border-gray-300 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all"
              />
            </div>

            <div>
              <label className="block text-xs font-semibold uppercase tracking-wider text-gray-700 mb-1.5">
                State Medical Council *
              </label>
              <input
                type="text"
                required
                value={stateMedicalCouncil}
                onChange={(e) => setStateMedicalCouncil(e.target.value)}
                placeholder="e.g. Maharashtra Council of Indian Medicine"
                className="w-full px-4 py-2.5 rounded-xl border border-gray-300 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all"
              />
            </div>
          </div>

          {/* Experience & Specialization */}
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
            <div>
              <label className="block text-xs font-semibold uppercase tracking-wider text-gray-700 mb-1.5">
                Years of Clinical Practice *
              </label>
              <input
                type="number"
                min="0"
                required
                value={yearsOfExperience}
                onChange={(e) => setYearsOfExperience(e.target.value)}
                className="w-full px-4 py-2.5 rounded-xl border border-gray-300 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all"
              />
            </div>

            <div>
              <label className="block text-xs font-semibold uppercase tracking-wider text-gray-700 mb-1.5">
                Primary Clinical Specialization *
              </label>
              <select
                value={primarySpecialization}
                onChange={(e) => setPrimarySpecialization(e.target.value)}
                className="w-full px-4 py-2.5 rounded-xl border border-gray-300 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all bg-white"
              >
                <option value="Kayachikitsa">Kayachikitsa (Internal Medicine)</option>
                <option value="Panchakarma">Panchakarma (Detoxification)</option>
                <option value="Rasayana & Longevity">Rasayana &amp; Longevity</option>
                <option value="Dravyaguna">Dravyaguna (Herbal Pharmacology)</option>
                <option value="Shalya Tantra">Shalya Tantra (Surgical / Marma)</option>
              </select>
            </div>
          </div>

          {/* Languages & Fee */}
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
            <div>
              <label className="block text-xs font-semibold uppercase tracking-wider text-gray-700 mb-1.5">
                Languages Spoken *
              </label>
              <input
                type="text"
                required
                value={languagesSpoken}
                onChange={(e) => setLanguagesSpoken(e.target.value)}
                placeholder="e.g. English, Hindi, Marathi"
                className="w-full px-4 py-2.5 rounded-xl border border-gray-300 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all"
              />
            </div>

            <div>
              <label className="block text-xs font-semibold uppercase tracking-wider text-gray-700 mb-1.5">
                Consultation Fee (INR) *
              </label>
              <input
                type="number"
                min="0"
                step="50"
                required
                value={consultationFee}
                onChange={(e) => setConsultationFee(e.target.value)}
                className="w-full px-4 py-2.5 rounded-xl border border-gray-300 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all"
              />
            </div>
          </div>

          {/* Biography */}
          <div>
            <label className="block text-xs font-semibold uppercase tracking-wider text-gray-700 mb-1.5">
              Clinical Approach &amp; Bio
            </label>
            <textarea
              rows="4"
              value={bio}
              onChange={(e) => setBio(e.target.value)}
              placeholder="Describe your diagnostic approach (Nadi Pariksha, Prakriti assessment), clinical background, and focus areas..."
              className="w-full px-4 py-2.5 rounded-xl border border-gray-300 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all"
            />
          </div>

          <button
            type="submit"
            disabled={submitting}
            className="w-full py-3.5 rounded-full bg-blue-900 hover:bg-blue-800 text-white font-semibold text-sm transition-all shadow-sm hover:shadow disabled:opacity-50 flex items-center justify-center gap-2"
          >
            <span>{submitting ? 'Submitting Credentials...' : 'Submit Verification Application'}</span>
            <ArrowRight className="w-4 h-4" />
          </button>
        </form>
      </div>
    </div>
  );
}
