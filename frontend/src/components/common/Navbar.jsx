import React, { useState } from 'react';
import { Link, NavLink, useNavigate } from 'react-router-dom';
import { 
  Leaf, 
  Menu, 
  X, 
  Search, 
  Heart, 
  User, 
  Camera, 
  Stethoscope, 
  BookOpen, 
  SunMedium, 
  Sparkles,
  LogOut,
  Shield,
  ChevronDown,
  Clock,
  Utensils,
  Eye,
  HeartPulse,
  Calendar,
  HelpCircle
} from 'lucide-react';
import { useAuth } from '../../context/AuthContext';

export default function Navbar() {
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);
  const [userDropdownOpen, setUserDropdownOpen] = useState(false);
  const [ayurvedaDropdownOpen, setAyurvedaDropdownOpen] = useState(false);

  const { user, isAuthenticated, logout, hasRole } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    setUserDropdownOpen(false);
    navigate('/');
  };

  const navLinks = [
    { name: 'Herbs & Plants', path: '/herbs', icon: Leaf },
    { name: 'Formulations', path: '/remedies', icon: Sparkles },
    { name: 'Yoga', path: '/yoga', icon: SunMedium },
    { name: 'Meditation', path: '/meditation', icon: SunMedium },
    { name: 'Doctors', path: '/doctors', icon: Stethoscope },
    { name: 'Articles', path: '/articles', icon: BookOpen },
    { name: 'Plant Scanner', path: '/scanner', icon: Camera },
  ];

  return (
    <header className="sticky top-0 z-50 bg-[#fafaf7]/95 backdrop-blur-md border-b border-earth-100 transition-all">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex items-center justify-between h-20">
          {/* Brand Logo */}
          <Link to="/" className="flex items-center gap-3 group flex-shrink-0">
            <div className="w-11 h-11 rounded-full bg-gradient-to-tr from-emerald-800 to-emerald-600 flex items-center justify-center text-white shadow-md shadow-emerald-900/10 group-hover:scale-105 transition-transform">
              <Leaf className="w-6 h-6" />
            </div>
            <div>
              <span className="font-serif text-2xl font-bold tracking-tight text-earth-950">
                Ayur<span className="text-emerald-700 font-normal">Vana</span>
              </span>
              <span className="block text-[10px] tracking-widest uppercase font-semibold text-emerald-800/80 -mt-1">
                Ancient Wisdom • Modern Wellness
              </span>
            </div>
          </Link>

          {/* Desktop Navigation */}
          <nav className="hidden xl:flex items-center gap-1">
            {/* Ayurveda Clinical Dropdown */}
            <div className="relative">
              <button
                onClick={() => setAyurvedaDropdownOpen(!ayurvedaDropdownOpen)}
                onMouseEnter={() => setAyurvedaDropdownOpen(true)}
                className="px-3 py-2 rounded-xl text-xs font-bold text-earth-900 hover:text-emerald-800 hover:bg-earth-100/60 transition-all flex items-center gap-1.5"
              >
                <Sparkles className="w-3.5 h-3.5 text-emerald-700" />
                Ayurveda Hub
                <ChevronDown className="w-3.5 h-3.5 text-gray-500" />
              </button>

              {ayurvedaDropdownOpen && (
                <div
                  onMouseLeave={() => setAyurvedaDropdownOpen(false)}
                  className="absolute left-0 mt-1 w-64 bg-white rounded-2xl shadow-xl border border-gray-100 p-2 z-50 space-y-1"
                >
                  <Link
                    to="/ayurveda"
                    onClick={() => setAyurvedaDropdownOpen(false)}
                    className="flex items-center gap-2.5 px-3 py-2 rounded-xl text-xs font-semibold text-earth-950 hover:bg-sand-50"
                  >
                    <BookOpen className="w-4 h-4 text-emerald-700" />
                    Principles &amp; Samhitas
                  </Link>
                  <Link
                    to="/ayurveda/quiz"
                    onClick={() => setAyurvedaDropdownOpen(false)}
                    className="flex items-center gap-2.5 px-3 py-2 rounded-xl text-xs font-semibold text-earth-950 hover:bg-sand-50"
                  >
                    <Sparkles className="w-4 h-4 text-amber-600" />
                    Prakriti Dosha Assessment
                  </Link>
                  <Link
                    to="/ayurveda/dinacharya"
                    onClick={() => setAyurvedaDropdownOpen(false)}
                    className="flex items-center gap-2.5 px-3 py-2 rounded-xl text-xs font-semibold text-earth-950 hover:bg-sand-50"
                  >
                    <Clock className="w-4 h-4 text-blue-600" />
                    Dinacharya Circadian Clock
                  </Link>
                  <Link
                    to="/ayurveda/ritucharya"
                    onClick={() => setAyurvedaDropdownOpen(false)}
                    className="flex items-center gap-2.5 px-3 py-2 rounded-xl text-xs font-semibold text-earth-950 hover:bg-sand-50"
                  >
                    <Calendar className="w-4 h-4 text-teal-600" />
                    Ritucharya Seasonal Living
                  </Link>
                  <Link
                    to="/ayurveda/diet-guide"
                    onClick={() => setAyurvedaDropdownOpen(false)}
                    className="flex items-center gap-2.5 px-3 py-2 rounded-xl text-xs font-semibold text-earth-950 hover:bg-sand-50"
                  >
                    <Utensils className="w-4 h-4 text-emerald-600" />
                    Diet &amp; 6 Tastes Food Chart
                  </Link>
                  <Link
                    to="/ayurveda/tongue-diagnosis"
                    onClick={() => setAyurvedaDropdownOpen(false)}
                    className="flex items-center gap-2.5 px-3 py-2 rounded-xl text-xs font-semibold text-earth-950 hover:bg-sand-50"
                  >
                    <Eye className="w-4 h-4 text-purple-600" />
                    Tongue Diagnosis (Jihvā)
                  </Link>
                  <Link
                    to="/ayurveda/nadi-pariksha"
                    onClick={() => setAyurvedaDropdownOpen(false)}
                    className="flex items-center gap-2.5 px-3 py-2 rounded-xl text-xs font-semibold text-earth-950 hover:bg-sand-50"
                  >
                    <HeartPulse className="w-4 h-4 text-rose-600" />
                    Pulse Diagnosis (Nāḍī)
                  </Link>
                </div>
              )}
            </div>

            {navLinks.map((link) => (
              <NavLink
                key={link.name}
                to={link.path}
                className={({ isActive }) => `
                  px-2.5 py-2 rounded-xl text-xs font-bold transition-all flex items-center gap-1.5
                  ${isActive 
                    ? 'text-emerald-900 bg-emerald-100/70 font-bold' 
                    : 'text-earth-900 hover:text-emerald-800 hover:bg-earth-100/60'
                  }
                `}
              >
                {link.name}
              </NavLink>
            ))}

            <NavLink
              to="/faq"
              className={({ isActive }) => `
                px-2.5 py-2 rounded-xl text-xs font-bold transition-all flex items-center gap-1.5
                ${isActive 
                  ? 'text-emerald-900 bg-emerald-100/70 font-bold' 
                  : 'text-earth-900 hover:text-emerald-800 hover:bg-earth-100/60'
                }
              `}
            >
              FAQs
            </NavLink>
          </nav>

          {/* Right Action Icons */}
          <div className="hidden sm:flex items-center gap-2">
            <Link
              to="/search"
              aria-label="Search"
              className="p-2 rounded-full text-gray-600 hover:text-emerald-800 hover:bg-earth-100/60 transition-colors"
            >
              <Search className="w-4 h-4" />
            </Link>

            <Link
              to="/favorites"
              aria-label="Saved Favorites"
              className="p-2 rounded-full text-gray-600 hover:text-rose-600 hover:bg-rose-50 transition-colors"
            >
              <Heart className="w-4 h-4" />
            </Link>

            {/* User Dropdown */}
            {isAuthenticated ? (
              <div className="relative">
                <button
                  onClick={() => setUserDropdownOpen(!userDropdownOpen)}
                  className="flex items-center gap-2 pl-2 pr-3 py-1.5 rounded-full bg-emerald-100/60 border border-emerald-200/80 text-emerald-950 text-xs font-bold hover:bg-emerald-100 transition-all"
                >
                  <div className="w-6 h-6 rounded-full bg-emerald-700 text-white flex items-center justify-center font-serif text-xs">
                    {user?.fullName ? user.fullName[0].toUpperCase() : 'U'}
                  </div>
                  <span className="max-w-[100px] truncate">{user?.fullName?.split(' ')[0]}</span>
                  <ChevronDown className="w-3.5 h-3.5 text-emerald-800" />
                </button>

                {userDropdownOpen && (
                  <div className="absolute right-0 mt-2 w-56 bg-white rounded-2xl shadow-xl border border-gray-100 py-2 z-50">
                    <div className="px-4 py-2 border-b border-gray-100">
                      <p className="text-xs font-bold text-gray-900 truncate">{user?.fullName}</p>
                      <p className="text-[11px] text-gray-500 truncate">{user?.email}</p>
                    </div>

                    <Link
                      to="/profile"
                      onClick={() => setUserDropdownOpen(false)}
                      className="flex items-center gap-2 px-4 py-2 text-xs text-gray-700 hover:bg-sand-50"
                    >
                      <User className="w-4 h-4 text-emerald-700" />
                      My Health Profile
                    </Link>

                    <Link
                      to="/appointments"
                      onClick={() => setUserDropdownOpen(false)}
                      className="flex items-center gap-2 px-4 py-2 text-xs text-gray-700 hover:bg-sand-50"
                    >
                      <Stethoscope className="w-4 h-4 text-emerald-700" />
                      My Appointments
                    </Link>

                    {hasRole('ROLE_DOCTOR') && (
                      <Link
                        to="/doctor/dashboard"
                        onClick={() => setUserDropdownOpen(false)}
                        className="flex items-center gap-2 px-4 py-2 text-xs text-emerald-800 font-bold hover:bg-emerald-50"
                      >
                        <Shield className="w-4 h-4 text-emerald-700" />
                        Doctor Console
                      </Link>
                    )}

                    {hasRole('ROLE_ADMIN') && (
                      <Link
                        to="/admin/dashboard"
                        onClick={() => setUserDropdownOpen(false)}
                        className="flex items-center gap-2 px-4 py-2 text-xs text-amber-900 font-bold hover:bg-amber-50"
                      >
                        <Shield className="w-4 h-4 text-amber-700" />
                        Admin Governance
                      </Link>
                    )}

                    <button
                      onClick={handleLogout}
                      className="w-full flex items-center gap-2 px-4 py-2 text-xs text-rose-600 hover:bg-rose-50 border-t border-gray-100 mt-1"
                    >
                      <LogOut className="w-4 h-4" />
                      Sign Out
                    </button>
                  </div>
                )}
              </div>
            ) : (
              <div className="flex items-center gap-2">
                <Link
                  to="/login"
                  className="px-3.5 py-1.5 rounded-full text-xs font-bold text-earth-900 hover:bg-earth-100 transition-all"
                >
                  Log In
                </Link>
                <Link
                  to="/register"
                  className="px-4 py-1.5 rounded-full bg-emerald-800 text-white text-xs font-bold hover:bg-emerald-900 shadow-sm transition-all"
                >
                  Join AyurVana
                </Link>
              </div>
            )}
          </div>

          {/* Mobile Menu Toggle Button */}
          <div className="flex xl:hidden items-center gap-2">
            <Link
              to="/search"
              aria-label="Search"
              className="p-2 rounded-full text-gray-600 hover:bg-earth-100"
            >
              <Search className="w-4 h-4" />
            </Link>
            <button
              onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
              className="p-2 rounded-xl text-earth-950 hover:bg-earth-100"
            >
              {mobileMenuOpen ? <X className="w-6 h-6" /> : <Menu className="w-6 h-6" />}
            </button>
          </div>
        </div>
      </div>

      {/* Mobile Menu */}
      {mobileMenuOpen && (
        <div className="xl:hidden bg-white border-b border-gray-200 px-4 pt-3 pb-6 space-y-3">
          <div className="space-y-1">
            <Link
              to="/ayurveda"
              onClick={() => setMobileMenuOpen(false)}
              className="block px-3 py-2 rounded-xl text-sm font-bold text-earth-950 hover:bg-sand-50"
            >
              🌿 Ayurveda Knowledge Hub
            </Link>
            <Link
              to="/ayurveda/quiz"
              onClick={() => setMobileMenuOpen(false)}
              className="block px-3 py-2 rounded-xl text-sm font-bold text-emerald-800 hover:bg-sand-50"
            >
              ✨ Dosha Prakriti Assessment
            </Link>
            <Link
              to="/ayurveda/dinacharya"
              onClick={() => setMobileMenuOpen(false)}
              className="block px-3 py-2 rounded-xl text-sm font-bold text-blue-800 hover:bg-sand-50"
            >
              ⏰ Dinacharya Circadian Planner
            </Link>
            <Link
              to="/ayurveda/ritucharya"
              onClick={() => setMobileMenuOpen(false)}
              className="block px-3 py-2 rounded-xl text-sm font-bold text-teal-800 hover:bg-sand-50"
            >
              📅 Ritucharya Seasonal Living
            </Link>
            <Link
              to="/ayurveda/diet-guide"
              onClick={() => setMobileMenuOpen(false)}
              className="block px-3 py-2 rounded-xl text-sm font-bold text-earth-900 hover:bg-sand-50"
            >
              🥗 Ayurvedic Diet &amp; Food Chart
            </Link>
            <Link
              to="/ayurveda/tongue-diagnosis"
              onClick={() => setMobileMenuOpen(false)}
              className="block px-3 py-2 rounded-xl text-sm font-bold text-purple-800 hover:bg-sand-50"
            >
              👅 Tongue Diagnosis (Jihva)
            </Link>
            <Link
              to="/ayurveda/nadi-pariksha"
              onClick={() => setMobileMenuOpen(false)}
              className="block px-3 py-2 rounded-xl text-sm font-bold text-rose-800 hover:bg-sand-50"
            >
              💓 Pulse Diagnosis (Nadi)
            </Link>
            <Link
              to="/remedies"
              onClick={() => setMobileMenuOpen(false)}
              className="block px-3 py-2 rounded-xl text-sm font-bold text-amber-900 hover:bg-sand-50"
            >
              🍯 Classical Formulations (Remedies)
            </Link>
            <Link
              to="/herbs"
              onClick={() => setMobileMenuOpen(false)}
              className="block px-3 py-2 rounded-xl text-sm font-bold text-earth-950 hover:bg-sand-50"
            >
              🌱 Herb &amp; Botanical Library
            </Link>
            <Link
              to="/yoga"
              onClick={() => setMobileMenuOpen(false)}
              className="block px-3 py-2 rounded-xl text-sm font-bold text-earth-950 hover:bg-sand-50"
            >
              🧘 Yoga Asana Sanctuary
            </Link>
            <Link
              to="/meditation"
              onClick={() => setMobileMenuOpen(false)}
              className="block px-3 py-2 rounded-xl text-sm font-bold text-earth-950 hover:bg-sand-50"
            >
              🌬️ Guided Meditation &amp; Pranayama
            </Link>
            <Link
              to="/doctors"
              onClick={() => setMobileMenuOpen(false)}
              className="block px-3 py-2 rounded-xl text-sm font-bold text-earth-950 hover:bg-sand-50"
            >
              🩺 Consult Ayurvedic Doctors
            </Link>
            <Link
              to="/scanner"
              onClick={() => setMobileMenuOpen(false)}
              className="block px-3 py-2 rounded-xl text-sm font-bold text-earth-950 hover:bg-sand-50"
            >
              📷 AI Plant Scanner
            </Link>
            <Link
              to="/faq"
              onClick={() => setMobileMenuOpen(false)}
              className="block px-3 py-2 rounded-xl text-sm font-bold text-earth-950 hover:bg-sand-50"
            >
              ❓ Help &amp; FAQs
            </Link>
          </div>

          <div className="border-t border-gray-100 pt-3 flex flex-col gap-2">
            {isAuthenticated ? (
              <>
                <Link
                  to="/profile"
                  onClick={() => setMobileMenuOpen(false)}
                  className="w-full text-center py-2.5 rounded-xl bg-sand-100 text-earth-950 font-bold text-xs"
                >
                  My Profile ({user?.fullName})
                </Link>
                <button
                  onClick={handleLogout}
                  className="w-full text-center py-2.5 rounded-xl bg-rose-50 text-rose-600 font-bold text-xs"
                >
                  Sign Out
                </button>
              </>
            ) : (
              <>
                <Link
                  to="/login"
                  onClick={() => setMobileMenuOpen(false)}
                  className="w-full text-center py-2.5 rounded-xl bg-sand-100 text-earth-950 font-bold text-xs"
                >
                  Log In
                </Link>
                <Link
                  to="/register"
                  onClick={() => setMobileMenuOpen(false)}
                  className="w-full text-center py-2.5 rounded-xl bg-emerald-800 text-white font-bold text-xs"
                >
                  Join AyurVana
                </Link>
              </>
            )}
          </div>
        </div>
      )}
    </header>
  );
}
