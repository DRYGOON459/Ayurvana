import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { 
  Leaf, 
  Sparkles, 
  SunMedium, 
  Stethoscope, 
  ArrowRight, 
  ShieldCheck, 
  CheckCircle2, 
  Server,
  Heart,
  Calendar,
  BookOpen,
  UserCheck
} from 'lucide-react';
import api from '../services/api';
import Disclaimer from '../components/common/Disclaimer';

export default function Home() {
  const [backendHealth, setBackendHealth] = useState({ loading: true, status: null, error: null });

  useEffect(() => {
    // Test communication between React & Spring Boot
    api.get('/health')
      .then((res) => {
        setBackendHealth({ loading: false, status: res.data, error: null });
      })
      .catch((err) => {
        setBackendHealth({ 
          loading: false, 
          status: null, 
          error: err.message || 'Could not connect to Spring Boot backend' 
        });
      });
  }, []);

  return (
    <div className="space-y-16 pb-16">
      {/* 1. HERO SECTION */}
      <section className="relative overflow-hidden pt-12 pb-16 lg:pt-20 lg:pb-24 bg-gradient-to-b from-ayur-50/80 via-[#fafaf7] to-transparent">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 relative z-10">
          <div className="text-center max-w-3xl mx-auto space-y-6">
            {/* Tag Badge */}
            <div className="inline-flex items-center gap-2 px-4 py-1.5 rounded-full bg-ayur-100/90 border border-ayur-200 text-ayur-800 text-xs sm:text-sm font-medium shadow-sm">
              <Sparkles className="w-4 h-4 text-ayur-600" />
              <span>Ancient Vedic Wisdom • Modern Health Technology</span>
            </div>

            {/* Hero Main Headline */}
            <h1 className="font-serif text-4xl sm:text-5xl lg:text-6xl font-bold tracking-tight text-ayur-950 leading-[1.15]">
              Discover Ayurveda. <br className="hidden sm:inline" />
              <span className="text-ayur-700 italic font-normal">Connect with Wellness.</span>
            </h1>

            {/* Subtitle */}
            <p className="text-base sm:text-lg text-gray-600 leading-relaxed max-w-2xl mx-auto">
              Explore traditional Ayurvedic principles, herbal pharmacology, yoga postures, guided meditation, and consult verified Ayurvedic practitioners on a unified digital platform.
            </p>

            {/* Action Buttons */}
            <div className="flex flex-wrap items-center justify-center gap-4 pt-2">
              <Link
                to="/ayurveda"
                className="inline-flex items-center gap-2 px-6 py-3.5 rounded-full bg-ayur-800 text-white font-medium text-sm hover:bg-ayur-700 shadow-md hover:shadow-lg shadow-ayur-900/10 transition-all hover:-translate-y-0.5"
              >
                <span>Explore Ayurveda</span>
                <ArrowRight className="w-4 h-4" />
              </Link>
              <Link
                to="/doctors"
                className="inline-flex items-center gap-2 px-6 py-3.5 rounded-full bg-white text-ayur-900 border border-ayur-200 font-medium text-sm hover:bg-ayur-50 shadow-sm hover:shadow transition-all"
              >
                <Stethoscope className="w-4 h-4 text-ayur-600" />
                <span>Find an Ayurvedic Doctor</span>
              </Link>
              <Link
                to="/herbs"
                className="inline-flex items-center gap-2 px-6 py-3.5 rounded-full bg-earth-100 text-earth-900 border border-earth-200 font-medium text-sm hover:bg-earth-200 shadow-sm transition-all"
              >
                <Leaf className="w-4 h-4 text-earth-700" />
                <span>Herbs &amp; Remedies</span>
              </Link>
            </div>

            {/* Backend Connectivity Status */}
            <div className="pt-6 max-w-sm mx-auto">
              <div className="p-3.5 rounded-2xl bg-white border border-gray-200/80 shadow-sm text-left">
                <div className="flex items-center justify-between mb-1.5">
                  <div className="flex items-center gap-2 text-xs font-semibold uppercase tracking-wider text-gray-500">
                    <Server className="w-3.5 h-3.5 text-ayur-600" />
                    <span>System Status</span>
                  </div>
                  <span className={`inline-flex items-center px-2 py-0.5 rounded-full text-[11px] font-medium ${
                    backendHealth.loading 
                      ? 'bg-yellow-100 text-yellow-800' 
                      : backendHealth.status 
                        ? 'bg-emerald-100 text-emerald-800' 
                        : 'bg-rose-100 text-rose-800'
                  }`}>
                    {backendHealth.loading ? 'Connecting...' : backendHealth.status ? 'ONLINE' : 'OFFLINE'}
                  </span>
                </div>
                {backendHealth.status && (
                  <p className="text-xs text-gray-600">
                    Spring Boot Backend: <strong className="text-emerald-700">{backendHealth.status.service}</strong>
                  </p>
                )}
                {backendHealth.error && (
                  <p className="text-xs text-rose-600">{backendHealth.error}</p>
                )}
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* 2. CORE PILLARS SECTION */}
      <section className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="text-center space-y-3 mb-12">
          <h2 className="font-serif text-3xl font-bold text-ayur-950">
            Four Foundations of Natural Healing
          </h2>
          <p className="text-gray-600 text-sm sm:text-base max-w-xl mx-auto">
            A balanced integration of elemental constitution, herbal medicine, mindful breathwork, and clinical doctor consultations.
          </p>
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
          {/* Card 1: Ayurveda */}
          <Link to="/ayurveda" className="group p-6 rounded-2xl bg-white border border-ayur-100 shadow-sm hover:shadow-md hover:border-ayur-300 transition-all">
            <div className="w-12 h-12 rounded-xl bg-ayur-50 text-ayur-700 flex items-center justify-center mb-4 group-hover:scale-110 transition-transform">
              <Sparkles className="w-6 h-6" />
            </div>
            <h3 className="font-serif text-lg font-bold text-ayur-900 mb-2">Ayurvedic Knowledge</h3>
            <p className="text-xs sm:text-sm text-gray-600 leading-relaxed mb-4">
              Explore Tridosha (Vata, Pitta, Kapha), Prakriti, Dinacharya daily rhythms, and seasonal Ritucharya nutrition.
            </p>
            <span className="text-xs font-semibold text-ayur-700 group-hover:text-ayur-800 flex items-center gap-1">
              Explore Ayurveda <ArrowRight className="w-3.5 h-3.5" />
            </span>
          </Link>

          {/* Card 2: Herbs & Plants */}
          <Link to="/herbs" className="group p-6 rounded-2xl bg-white border border-ayur-100 shadow-sm hover:shadow-md hover:border-ayur-300 transition-all">
            <div className="w-12 h-12 rounded-xl bg-emerald-50 text-emerald-700 flex items-center justify-center mb-4 group-hover:scale-110 transition-transform">
              <Leaf className="w-6 h-6" />
            </div>
            <h3 className="font-serif text-lg font-bold text-ayur-900 mb-2">Herbal Encyclopedia</h3>
            <p className="text-xs sm:text-sm text-gray-600 leading-relaxed mb-4">
              Detailed monographs for Ashwagandha, Tulsi, Neem, Giloy, and Shatavari with therapeutic properties and safety profiles.
            </p>
            <span className="text-xs font-semibold text-emerald-700 group-hover:text-emerald-800 flex items-center gap-1">
              Browse Herbs <ArrowRight className="w-3.5 h-3.5" />
            </span>
          </Link>

          {/* Card 3: Yoga & Meditation */}
          <Link to="/yoga" className="group p-6 rounded-2xl bg-white border border-ayur-100 shadow-sm hover:shadow-md hover:border-ayur-300 transition-all">
            <div className="w-12 h-12 rounded-xl bg-amber-50 text-amber-700 flex items-center justify-center mb-4 group-hover:scale-110 transition-transform">
              <SunMedium className="w-6 h-6" />
            </div>
            <h3 className="font-serif text-lg font-bold text-ayur-900 mb-2">Yoga &amp; Meditation</h3>
            <p className="text-xs sm:text-sm text-gray-600 leading-relaxed mb-4">
              Guided asana postures, pranayama breathing exercises, and interactive mindfulness timers for stress relief.
            </p>
            <span className="text-xs font-semibold text-amber-700 group-hover:text-amber-800 flex items-center gap-1">
              Start Practice <ArrowRight className="w-3.5 h-3.5" />
            </span>
          </Link>

          {/* Card 4: Verified Doctors */}
          <Link to="/doctors" className="group p-6 rounded-2xl bg-white border border-ayur-100 shadow-sm hover:shadow-md hover:border-ayur-300 transition-all">
            <div className="w-12 h-12 rounded-xl bg-blue-50 text-blue-700 flex items-center justify-center mb-4 group-hover:scale-110 transition-transform">
              <Stethoscope className="w-6 h-6" />
            </div>
            <h3 className="font-serif text-lg font-bold text-ayur-900 mb-2">Doctor Consultations</h3>
            <p className="text-xs sm:text-sm text-gray-600 leading-relaxed mb-4">
              Book one-on-one appointments with state council-verified Ayurvedic doctors and receive personalized guidance.
            </p>
            <span className="text-xs font-semibold text-blue-700 group-hover:text-blue-800 flex items-center gap-1">
              Find Doctors <ArrowRight className="w-3.5 h-3.5" />
            </span>
          </Link>
        </div>
      </section>

      {/* 3. DOCTOR CONSULTATION SPOTLIGHT */}
      <section className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="rounded-3xl bg-gradient-to-r from-ayur-900 via-ayur-800 to-ayur-900 text-white p-8 sm:p-12 shadow-xl">
          <div className="grid grid-cols-1 lg:grid-cols-12 gap-8 items-center">
            <div className="lg:col-span-7 space-y-4">
              <div className="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-ayur-700/60 text-ayur-200 text-xs font-medium">
                <UserCheck className="w-3.5 h-3.5" />
                <span>Verified Ayurvedic Practitioners</span>
              </div>
              <h2 className="font-serif text-3xl sm:text-4xl font-bold tracking-tight">
                Consult With Experienced Ayurvedic Doctors
              </h2>
              <p className="text-sm text-ayur-200/90 leading-relaxed">
                Connect with qualified BAMS and MD Ayurvedic practitioners. Every doctor on AyurVana undergoes administrative credential verification before being granted consultation privileges.
              </p>
              <div className="pt-2 flex flex-wrap gap-4">
                <Link
                  to="/doctors"
                  className="px-6 py-3 rounded-full bg-white text-ayur-900 font-semibold text-sm hover:bg-ayur-50 transition-colors shadow"
                >
                  Browse Verified Doctors
                </Link>
                <Link
                  to="/doctors/onboarding"
                  className="px-6 py-3 rounded-full bg-ayur-700/60 text-white border border-ayur-500/40 text-sm font-medium hover:bg-ayur-700 transition-colors"
                >
                  Doctor Registration
                </Link>
              </div>
            </div>

            <div className="lg:col-span-5 flex justify-center">
              <div className="w-full max-w-sm rounded-2xl bg-white/10 backdrop-blur-md border border-white/20 p-6 space-y-4 shadow-2xl">
                <div className="flex items-center gap-4">
                  <div className="w-14 h-14 rounded-full bg-ayur-100 text-ayur-800 flex items-center justify-center font-serif text-xl font-bold">
                    👨‍⚕️
                  </div>
                  <div>
                    <div className="flex items-center gap-1.5">
                      <h4 className="font-serif font-bold text-white text-base">Dr. Aarav Sharma</h4>
                      <CheckCircle2 className="w-4 h-4 text-emerald-400" />
                    </div>
                    <p className="text-xs text-ayur-200">BAMS, MD (Kayachikitsa) • 12 Yrs Exp</p>
                  </div>
                </div>
                <div className="border-t border-white/10 pt-3 text-xs space-y-2 text-ayur-200">
                  <div className="flex justify-between">
                    <span>Specialization:</span>
                    <strong className="text-white">Digestive &amp; Lifestyle Disorders</strong>
                  </div>
                  <div className="flex justify-between">
                    <span>Languages:</span>
                    <strong className="text-white">English, Hindi</strong>
                  </div>
                  <div className="flex justify-between">
                    <span>Next Available Slot:</span>
                    <strong className="text-emerald-300">Today, 4:00 PM</strong>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* 4. MEDICAL DISCLAIMER */}
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <Disclaimer variant="general" />
      </div>
    </div>
  );
}
