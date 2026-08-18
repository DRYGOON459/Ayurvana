import React, { useState } from 'react';
import { Link, NavLink } from 'react-router-dom';
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
  Sparkles 
} from 'lucide-react';

export default function Navbar() {
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);

  const navLinks = [
    { name: 'Ayurveda', path: '/ayurveda', icon: Sparkles },
    { name: 'Herbs & Plants', path: '/herbs', icon: Leaf },
    { name: 'Yoga', path: '/yoga', icon: SunMedium },
    { name: 'Meditation', path: '/meditation', icon: SunMedium },
    { name: 'Doctors', path: '/doctors', icon: Stethoscope },
    { name: 'Articles', path: '/articles', icon: BookOpen },
    { name: 'Plant Scanner', path: '/scanner', icon: Camera },
  ];

  return (
    <header className="sticky top-0 z-50 bg-[#fafaf7]/90 backdrop-blur-md border-b border-ayur-100 transition-all">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex items-center justify-between h-20">
          {/* Brand Logo */}
          <Link to="/" className="flex items-center gap-3 group">
            <div className="w-11 h-11 rounded-full bg-gradient-to-tr from-ayur-700 to-ayur-500 flex items-center justify-center text-white shadow-md shadow-ayur-900/10 group-hover:scale-105 transition-transform">
              <Leaf className="w-6 h-6" />
            </div>
            <div>
              <span className="font-serif text-2xl font-bold tracking-tight text-ayur-900">
                Ayur<span className="text-ayur-600 font-normal">Vana</span>
              </span>
              <span className="block text-[10px] tracking-widest uppercase font-semibold text-earth-600 -mt-1">
                Ancient Wisdom • Modern Wellness
              </span>
            </div>
          </Link>

          {/* Desktop Navigation */}
          <nav className="hidden lg:flex items-center gap-1 xl:gap-2">
            {navLinks.map((link) => (
              <NavLink
                key={link.name}
                to={link.path}
                className={({ isActive }) => `
                  px-3 py-2 rounded-lg text-sm font-medium transition-all flex items-center gap-1.5
                  ${isActive 
                    ? 'text-ayur-800 bg-ayur-100 font-semibold' 
                    : 'text-gray-600 hover:text-ayur-800 hover:bg-ayur-50'
                  }
                `}
              >
                {link.name}
              </NavLink>
            ))}
          </nav>

          {/* Right Action Icons */}
          <div className="hidden sm:flex items-center gap-3">
            <Link
              to="/search"
              aria-label="Search"
              className="p-2.5 rounded-full text-gray-600 hover:text-ayur-800 hover:bg-ayur-50 transition-colors"
            >
              <Search className="w-5 h-5" />
            </Link>

            <Link
              to="/favorites"
              aria-label="Saved Favorites"
              className="p-2.5 rounded-full text-gray-600 hover:text-rose-600 hover:bg-rose-50 transition-colors"
            >
              <Heart className="w-5 h-5" />
            </Link>

            <Link
              to="/login"
              className="inline-flex items-center gap-2 px-4 py-2 rounded-full bg-ayur-800 text-white text-sm font-medium hover:bg-ayur-700 shadow-sm hover:shadow transition-all"
            >
              <User className="w-4 h-4" />
              <span>Sign In</span>
            </Link>
          </div>

          {/* Mobile Menu Button */}
          <div className="flex lg:hidden items-center gap-2">
            <Link
              to="/login"
              className="p-2 rounded-full text-gray-700 hover:bg-gray-100"
            >
              <User className="w-5 h-5" />
            </Link>
            <button
              onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
              className="p-2 rounded-lg text-gray-700 hover:bg-gray-100 focus:outline-none"
              aria-label="Toggle menu"
            >
              {mobileMenuOpen ? <X className="w-6 h-6" /> : <Menu className="w-6 h-6" />}
            </button>
          </div>
        </div>
      </div>

      {/* Mobile Menu Drawer */}
      {mobileMenuOpen && (
        <div className="lg:hidden border-t border-ayur-100 bg-[#fafaf7] px-4 pt-2 pb-6 space-y-1 shadow-lg animate-in slide-in-from-top duration-200">
          {navLinks.map((link) => (
            <Link
              key={link.name}
              to={link.path}
              onClick={() => setMobileMenuOpen(false)}
              className="block px-3 py-2.5 rounded-lg text-base font-medium text-gray-700 hover:text-ayur-900 hover:bg-ayur-50"
            >
              <div className="flex items-center gap-3">
                <link.icon className="w-5 h-5 text-ayur-600" />
                <span>{link.name}</span>
              </div>
            </Link>
          ))}
          <div className="pt-4 border-t border-gray-200 flex gap-2">
            <Link
              to="/favorites"
              onClick={() => setMobileMenuOpen(false)}
              className="flex-1 text-center py-2.5 rounded-lg border border-gray-200 text-sm font-medium text-gray-700"
            >
              Favorites
            </Link>
            <Link
              to="/login"
              onClick={() => setMobileMenuOpen(false)}
              className="flex-1 text-center py-2.5 rounded-lg bg-ayur-800 text-white text-sm font-medium"
            >
              Sign In
            </Link>
          </div>
        </div>
      )}
    </header>
  );
}
