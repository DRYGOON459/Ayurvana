import React from 'react';
import { Link } from 'react-router-dom';
import { Leaf, Heart, Shield, Sparkles } from 'lucide-react';

export default function Footer() {
  return (
    <footer className="bg-ayur-950 text-ayur-100/80 pt-16 pb-12 border-t border-ayur-900">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-5 gap-10 mb-12">
          {/* Col 1: Brand */}
          <div className="lg:col-span-2 space-y-4">
            <div className="flex items-center gap-3">
              <div className="w-10 h-10 rounded-full bg-ayur-700 flex items-center justify-center text-white">
                <Leaf className="w-5 h-5" />
              </div>
              <span className="font-serif text-2xl font-bold text-white tracking-tight">
                Ayur<span className="text-ayur-400">Vana</span>
              </span>
            </div>
            <p className="text-sm text-ayur-200/70 max-w-sm leading-relaxed">
              Bridging timeless Vedic healing traditions with cutting-edge digital health technology. Discover herbs, master yoga, meditate, and consult verified Ayurvedic doctors.
            </p>
            <div className="pt-2 text-xs text-ayur-300/60 flex items-center gap-2">
              <Shield className="w-4 h-4 text-ayur-400" />
              <span>Evidence-Grounding &amp; Verified Practitioners</span>
            </div>
          </div>

          {/* Col 2: Ayurveda & Nature */}
          <div className="space-y-3">
            <h4 className="text-white text-sm font-semibold tracking-wider uppercase">Ayurveda</h4>
            <ul className="space-y-2 text-sm">
              <li><Link to="/ayurveda" className="hover:text-white transition-colors">Dosha Principles</Link></li>
              <li><Link to="/herbs" className="hover:text-white transition-colors">Herb Encyclopedia</Link></li>
              <li><Link to="/scanner" className="hover:text-white transition-colors">AI Plant Scanner</Link></li>
              <li><Link to="/safety" className="hover:text-white transition-colors">Plant Safety Center</Link></li>
            </ul>
          </div>

          {/* Col 3: Practices */}
          <div className="space-y-3">
            <h4 className="text-white text-sm font-semibold tracking-wider uppercase">Mind &amp; Body</h4>
            <ul className="space-y-2 text-sm">
              <li><Link to="/yoga" className="hover:text-white transition-colors">Yoga Asanas</Link></li>
              <li><Link to="/yoga/pranayama" className="hover:text-white transition-colors">Pranayama Breathing</Link></li>
              <li><Link to="/meditation" className="hover:text-white transition-colors">Guided Meditation</Link></li>
              <li><Link to="/articles" className="hover:text-white transition-colors">Wellness Articles</Link></li>
            </ul>
          </div>

          {/* Col 4: Doctor Consult */}
          <div className="space-y-3">
            <h4 className="text-white text-sm font-semibold tracking-wider uppercase">Consultation</h4>
            <ul className="space-y-2 text-sm">
              <li><Link to="/doctors" className="hover:text-white transition-colors">Find Doctors</Link></li>
              <li><Link to="/doctors/onboarding" className="hover:text-white transition-colors">Doctor Verification</Link></li>
              <li><Link to="/appointments" className="hover:text-white transition-colors">Book Consultation</Link></li>
              <li><Link to="/consultation-safety" className="hover:text-white transition-colors">Safety Standards</Link></li>
            </ul>
          </div>
        </div>

        {/* Bottom Bar */}
        <div className="pt-8 border-t border-ayur-900/60 flex flex-col sm:flex-row items-center justify-between text-xs text-ayur-300/60 gap-4">
          <p>© {new Date().getFullYear()} AyurVana. All rights reserved.</p>
          <div className="flex items-center gap-6">
            <Link to="/privacy" className="hover:text-white transition-colors">Privacy Policy</Link>
            <Link to="/terms" className="hover:text-white transition-colors">Terms of Service</Link>
            <Link to="/disclaimer" className="hover:text-white transition-colors">Medical Disclaimer</Link>
          </div>
        </div>
      </div>
    </footer>
  );
}
