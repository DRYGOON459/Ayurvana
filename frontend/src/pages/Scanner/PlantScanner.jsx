import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { Camera, Upload, AlertTriangle, ShieldCheck, ArrowRight, RefreshCw, Leaf, Sparkles } from 'lucide-react';
import plantScannerService from '../../services/plantScannerService';
import Disclaimer from '../../components/common/Disclaimer';

export default function PlantScanner() {
  const [selectedFile, setSelectedFile] = useState(null);
  const [previewUrl, setPreviewUrl] = useState(null);
  const [loading, setLoading] = useState(false);
  const [scanResult, setScanResult] = useState(null);
  const [error, setError] = useState('');

  const handleFileChange = (e) => {
    const file = e.target.files?.[0];
    if (file) {
      setSelectedFile(file);
      setPreviewUrl(URL.createObjectURL(file));
      setScanResult(null);
      setError('');
    }
  };

  const handleScan = async (fileToScan = selectedFile) => {
    if (!fileToScan) {
      setError('Please select or capture a plant photo first.');
      return;
    }

    setLoading(true);
    setError('');
    try {
      const res = await plantScannerService.scanPlant(fileToScan);
      setScanResult(res.data);
    } catch (err) {
      console.error("Scan failed", err);
      setError(err.response?.data?.message || 'Failed to scan image. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  // Helper to trigger instant sample simulation without needing local camera files
  const handleSampleSelect = (sampleName) => {
    // Create an empty dummy file with specific botanical names for vision engine simulation
    const dummyBlob = new Blob(["sample image content"], { type: "image/jpeg" });
    const dummyFile = new File([dummyBlob], `${sampleName}.jpg`, { type: "image/jpeg" });

    setSelectedFile(dummyFile);
    setPreviewUrl(
      sampleName.includes('castor')
        ? 'https://images.unsplash.com/photo-1596755094514-f87e34085b2c?auto=format&fit=crop&w=600&q=80'
        : sampleName.includes('datura')
        ? 'https://images.unsplash.com/photo-1540420773420-3366772f4999?auto=format&fit=crop&w=600&q=80'
        : 'https://images.unsplash.com/photo-1615485290382-441e4d049cb5?auto=format&fit=crop&w=600&q=80'
    );
    handleScan(dummyFile);
  };

  const handleReset = () => {
    setSelectedFile(null);
    setPreviewUrl(null);
    setScanResult(null);
    setError('');
  };

  return (
    <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-10 space-y-8">
      {/* Header */}
      <div className="text-center space-y-2 max-w-xl mx-auto">
        <div className="inline-flex items-center gap-1.5 px-3 py-1 rounded-full bg-emerald-50 text-emerald-800 text-xs font-semibold uppercase tracking-wider">
          <Camera className="w-3.5 h-3.5" />
          <span>Botanical Identification</span>
        </div>
        <h1 className="font-serif text-3xl sm:text-4xl font-bold text-gray-950">
          Plant &amp; Herb Scanner
        </h1>
        <p className="text-xs sm:text-sm text-gray-600">
          Upload or capture a leaf or flower to identify Ayurvedic medicinal plants and screen for high-hazard toxic species.
        </p>
      </div>

      {/* Quick Test Chips */}
      <div className="p-4 rounded-2xl bg-gray-50 border border-gray-200/80 text-center space-y-2">
        <span className="text-[11px] font-bold text-gray-400 uppercase tracking-wider block">
          ⚡ Instant Botanical Simulation Presets
        </span>
        <div className="flex flex-wrap items-center justify-center gap-2">
          <button
            onClick={() => handleSampleSelect('ashwagandha_leaf')}
            className="px-3 py-1.5 rounded-full bg-white hover:bg-emerald-50 text-xs font-medium text-emerald-800 border border-gray-200 transition-colors shadow-2xs"
          >
            🌿 Ashwagandha (Safe)
          </button>
          <button
            onClick={() => handleSampleSelect('tulsi_holy_basil')}
            className="px-3 py-1.5 rounded-full bg-white hover:bg-emerald-50 text-xs font-medium text-emerald-800 border border-gray-200 transition-colors shadow-2xs"
          >
            🌱 Holy Basil / Tulsi (Safe)
          </button>
          <button
            onClick={() => handleSampleSelect('neem_foliage')}
            className="px-3 py-1.5 rounded-full bg-white hover:bg-emerald-50 text-xs font-medium text-emerald-800 border border-gray-200 transition-colors shadow-2xs"
          >
            🍃 Neem (Bitter)
          </button>
          <button
            onClick={() => handleSampleSelect('castor_ricinus')}
            className="px-3 py-1.5 rounded-full bg-white hover:bg-rose-50 text-xs font-medium text-rose-800 border border-rose-200 transition-colors shadow-2xs"
          >
            ⚠️ Castor Bean (Toxic Warning Test)
          </button>
          <button
            onClick={() => handleSampleSelect('datura_thornapple')}
            className="px-3 py-1.5 rounded-full bg-white hover:bg-rose-50 text-xs font-medium text-rose-800 border border-rose-200 transition-colors shadow-2xs"
          >
            ⚠️ Dhatura (Toxic Warning Test)
          </button>
        </div>
      </div>

      {/* Upload Box */}
      <div className="p-6 sm:p-8 rounded-3xl bg-white border border-gray-200 shadow-sm space-y-6">
        {error && (
          <div className="p-3.5 rounded-xl bg-rose-50 border border-rose-200 text-rose-800 text-xs flex items-center gap-2">
            <AlertTriangle className="w-4 h-4 flex-shrink-0" />
            <span>{error}</span>
          </div>
        )}

        {!previewUrl ? (
          <label className="border-2 border-dashed border-gray-300 hover:border-emerald-500 rounded-3xl p-8 sm:p-12 flex flex-col items-center justify-center cursor-pointer transition-colors group bg-gray-50/50">
            <div className="w-14 h-14 rounded-full bg-emerald-50 text-emerald-700 flex items-center justify-center mb-3 group-hover:scale-105 transition-transform">
              <Upload className="w-6 h-6" />
            </div>
            <span className="font-serif text-lg font-bold text-gray-800">
              Select or Drop Plant Photo
            </span>
            <span className="text-xs text-gray-500 mt-1">
              Supports JPEG, PNG, WEBP (Max 10MB)
            </span>
            <input
              type="file"
              accept="image/*"
              capture="environment"
              onChange={handleFileChange}
              className="hidden"
            />
          </label>
        ) : (
          <div className="space-y-4">
            <div className="relative rounded-2xl overflow-hidden max-h-72 border border-gray-200 bg-black/5 flex items-center justify-center">
              <img
                src={previewUrl}
                alt="Selected plant preview"
                className="max-h-72 w-full object-contain"
              />
              <button
                onClick={handleReset}
                className="absolute top-3 right-3 p-2 rounded-full bg-white/90 hover:bg-white text-gray-700 shadow-sm text-xs font-semibold transition-all"
              >
                Change Photo
              </button>
            </div>

            {!scanResult && (
              <div className="flex justify-center">
                <button
                  onClick={() => handleScan()}
                  disabled={loading}
                  className="px-8 py-3 rounded-full bg-emerald-800 hover:bg-emerald-700 text-white font-semibold text-sm shadow-md transition-all disabled:opacity-50 flex items-center gap-2"
                >
                  <RefreshCw className={`w-4 h-4 ${loading ? 'animate-spin' : ''}`} />
                  <span>{loading ? 'Analyzing Botanical Features...' : 'Identify Plant'}</span>
                </button>
              </div>
            )}
          </div>
        )}

        {/* Scan Results Presentation */}
        {scanResult && (
          <div className="pt-6 border-t border-gray-200 space-y-6 animate-in fade-in duration-300">
            <div className="flex flex-wrap items-center justify-between gap-3">
              <div>
                <span className="text-[10px] uppercase font-bold text-gray-400 tracking-wider">
                  Classification Result
                </span>
                <h2 className="font-serif text-2xl font-bold text-gray-950">
                  {scanResult.identifiedPlantName}
                </h2>
                <p className="text-xs text-emerald-800 font-serif italic font-medium">
                  {scanResult.scientificName}
                </p>
              </div>

              <div className="text-right">
                <span className="px-3 py-1 rounded-full bg-emerald-100 text-emerald-800 text-xs font-bold">
                  {scanResult.confidencePercentage}% Confidence
                </span>
              </div>
            </div>

            {/* Toxicity Alert Banner */}
            {scanResult.potentiallyToxic ? (
              <div className="p-5 rounded-2xl bg-rose-50 border-2 border-rose-400 text-rose-950 space-y-2">
                <div className="flex items-center gap-2 font-bold text-rose-900 text-sm">
                  <AlertTriangle className="w-5 h-5 text-rose-600" />
                  <span>CRITICAL SAFETY &amp; TOXICITY WARNING</span>
                </div>
                <p className="text-xs leading-relaxed text-rose-900 font-medium">
                  {scanResult.toxicityWarning}
                </p>
              </div>
            ) : (
              <div className="p-4 rounded-2xl bg-emerald-50 border border-emerald-200 text-emerald-950 flex items-center gap-3">
                <ShieldCheck className="w-5 h-5 text-emerald-600 flex-shrink-0" />
                <div className="text-xs">
                  <span className="font-bold block">Non-Toxic Medicinal Profile</span>
                  <span>Identified as a widely recognized botanical remedy in classical Ayurveda.</span>
                </div>
              </div>
            )}

            {/* Matched Herb Database Link */}
            {scanResult.matchedHerbSlug && (
              <div className="p-5 rounded-2xl bg-earth-50/80 border border-earth-200 flex flex-col sm:flex-row sm:items-center justify-between gap-4">
                <div>
                  <span className="text-[10px] uppercase font-bold text-earth-800 tracking-wider block">
                    AyurVana Database Match
                  </span>
                  <h3 className="font-serif text-base font-bold text-gray-900">
                    {scanResult.matchedHerbName} Monograph Available
                  </h3>
                  <p className="text-xs text-gray-600">
                    Access pharmacological details, Rasa/Virya/Vipaka, traditional formulation recipes, and PubMed references.
                  </p>
                </div>

                <Link
                  to={`/herbs/${scanResult.matchedHerbSlug}`}
                  className="px-5 py-2.5 rounded-full bg-earth-800 hover:bg-earth-900 text-white text-xs font-semibold inline-flex items-center justify-center gap-1.5 flex-shrink-0 transition-colors shadow-sm"
                >
                  <span>Explore Monograph</span>
                  <ArrowRight className="w-3.5 h-3.5" />
                </Link>
              </div>
            )}
          </div>
        )}
      </div>

      <Disclaimer variant="plant" />
    </div>
  );
}
