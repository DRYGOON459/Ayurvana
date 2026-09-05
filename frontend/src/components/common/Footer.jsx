import React from 'react';
import { Link } from 'react-router-dom';
import { Leaf, Shield, Sparkles, Heart } from 'lucide-react';

export default function Footer() {
  return (
    <footer className="bg-earth-950 text-sand-200/80 pt-16 pb-12 border-t border-earth-900">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-5 gap-10 mb-12">
          {/* Col 1: Brand */}
          <div className="lg:col-span-2 space-y-4">
            <div className="flex items-center gap-3">
              <div className="w-10 h-10 rounded-full bg-emerald-700 flex items-center justify-center text-white">
                <Leaf className="w-5 h-5" />
              </div>
              <span className="font-serif text-2xl font-bold text-white tracking-tight">
                Ayur<span className="text-emerald-400">Vana</span>
              </span>
            </div>
            <p className="text-sm text-sand-300/70 max-w-sm leading-relaxed">
              Bridging timeless Vedic healing traditions with cutting-edge digital health technology. Discover herbs, master yoga, explore formulations, track your daily circadian rhythm, and consult verified Ayurvedic doctors.
            </p>
            <div className="pt-2 text-xs text-sand-400/60 flex items-center gap-2">
              <Shield className="w-4 h-4 text-emerald-400" />
              <span>Evidence-Grounded &amp; State Council Verified Practitioners</span>
            </div>
          </div>

          {/* Col 2: Ayurveda & Formulations */}
          <div className="space-y-3">
            <h4 className="text-white text-sm font-semibold tracking-wider uppercase">Ayurveda Knowledge</h4>
            <ul className="space-y-2 text-xs">
              <li><Link to="/ayurveda" className="hover:text-white transition-colors">Philosophy &amp; Chapters</Link></li>
              <li><Link to="/ayurveda/quiz" className="hover:text-white transition-colors">Prakriti Dosha Quiz</Link></li>
              <li><Link to="/ayurveda/dinacharya" className="hover:text-white transition-colors">Dinacharya Daily Planner</Link></li>
              <li><Link to="/ayurveda/diet-guide" className="hover:text-white transition-colors">Ayurvedic Diet &amp; Food Chart</Link></li>
              <li><Link to="/remedies" className="hover:text-white transition-colors">Classical Remedies (Formulary)</Link></li>
            </ul>
          </div>

          {/* Col 3: Botanical & Practices */}
          <div className="space-y-3">
            <h4 className="text-white text-sm font-semibold tracking-wider uppercase">Botanicals &amp; Mind</h4>
            <ul className="space-y-2 text-xs">
              <li><Link to="/herbs" className="hover:text-white transition-colors">Herb &amp; Botanical Library</Link></li>
              <li><Link to="/scanner" className="hover:text-white transition-colors">AI Plant Scanner &amp; Safety</Link></li>
              <li><Link to="/yoga" className="hover:text-white transition-colors">Yoga Asanas Studio</Link></li>
              <li><Link to="/meditation" className="hover:text-white transition-colors">Guided Pranayama &amp; Dhyana</Link></li>
              <li><Link to="/articles" className="hover:text-white transition-colors">Peer-Reviewed Research</Link></li>
            </ul>
          </div>

          {/* Col 4: Telemedicine & Help */}
          <div className="space-y-3">
            <h4 className="text-white text-sm font-semibold tracking-wider uppercase">Consultation &amp; Help</h4>
            <ul className="space-y-2 text-xs">
              <li><Link to="/doctors" className="hover:text-white transition-colors">Find Verified Doctors</Link></li>
              <li><Link to="/doctors/onboarding" className="hover:text-white transition-colors">Doctor Credential Verification</Link></li>
              <li><Link to="/appointments" className="hover:text-white transition-colors">My Appointments</Link></li>
              <li><Link to="/faq" className="hover:text-white transition-colors">Patient FAQs &amp; Help</Link></li>
              <li><Link to="/search" className="hover:text-white transition-colors">Universal Search</Link></li>
            </ul>
          </div>
        </div>

        {/* Bottom Bar */}
        <div className="pt-8 border-t border-earth-900 flex flex-col sm:flex-row items-center justify-between text-xs text-sand-400/60 gap-4">
          <p>© {new Date().getFullYear()} AyurVana Platform. Classical Samhita Wisdom &amp; Modern Telemedicine.</p>
          <div className="flex items-center gap-6">
            <Link to="/faq" className="hover:text-white transition-colors">Help Center</Link>
            <Link to="/ayurveda/quiz" className="hover:text-white transition-colors">Take Dosha Quiz</Link>
            <Link to="/doctors" className="hover:text-white transition-colors">Consult a Vaidya</Link>
          </div>
        </div>
      </div>
    </footer>
  );
}
