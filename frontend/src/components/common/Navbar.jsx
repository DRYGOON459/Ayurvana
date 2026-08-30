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
  ChevronDown
} from 'lucide-react';
import { useAuth } from '../../context/AuthContext';

export default function Navbar() {
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);
  const [userDropdownOpen, setUserDropdownOpen] = useState(false);

  const { user, isAuthenticated, logout, hasRole } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    setUserDropdownOpen(false);
    navigate('/');
  };

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

            {/* Authentication Action / User Menu */}
            {isAuthenticated ? (
              <div className="relative">
                <button
                  onClick={() => setUserDropdownOpen(!userDropdownOpen)}
                  className="flex items-center gap-2 px-3 py-1.5 rounded-full bg-ayur-100/80 border border-ayur-200 text-xs font-semibold text-ayur-900 hover:bg-ayur-200/70 transition-all"
                >
                  <div className="w-6 h-6 rounded-full bg-ayur-700 text-white flex items-center justify-center text-[10px] font-bold">
                    {user.fullName.charAt(0)}
                  </div>
                  <span className="max-w-[100px] truncate">{user.fullName.split(' ')[0]}</span>
                  <ChevronDown className="w-3.5 h-3.5 text-gray-500" />
                </button>

                {/* Dropdown Menu */}
                {userDropdownOpen && (
                  <div className="absolute right-0 mt-2 w-56 bg-white rounded-2xl shadow-xl border border-gray-100 py-2 z-50 animate-in fade-in slide-in-from-top-2 duration-150">
                    <div className="px-4 py-2 border-b border-gray-100 text-xs space-y-0.5">
                      <p className="font-bold text-gray-900">{user.fullName}</p>
                      <p className="text-gray-500 truncate">{user.email}</p>
                      <div className="pt-1 flex gap-1">
                        {user.roles?.map((r) => (
                          <span key={r} className="px-1.5 py-0.5 rounded bg-ayur-50 text-[9px] font-bold text-ayur-800 border border-ayur-100">
                            {r.replace('ROLE_', '')}
                          </span>
                        ))}
                      </div>
                    </div>

                    <Link
                      to="/profile"
                      onClick={() => setUserDropdownOpen(false)}
                      className="block px-4 py-2 text-xs text-gray-700 hover:bg-gray-50"
                    >
                      My Profile
                    </Link>

                    <Link
                      to="/favorites"
                      onClick={() => setUserDropdownOpen(false)}
                      className="block px-4 py-2 text-xs text-gray-700 hover:bg-gray-50"
                    >
                      Saved Favorites
                    </Link>

                    <Link
                      to="/appointments"
                      onClick={() => setUserDropdownOpen(false)}
                      className="block px-4 py-2 text-xs text-gray-700 hover:bg-gray-50 font-medium text-blue-900"
                    >
                      My Appointments
                    </Link>

                    {hasRole('DOCTOR') && (
                      <Link
                        to="/doctor/dashboard"
                        onClick={() => setUserDropdownOpen(false)}
                        className="block px-4 py-2 text-xs text-blue-700 hover:bg-blue-50 font-medium"
                      >
                        Doctor Consultation Dashboard
                      </Link>
                    )}

                    {hasRole('ADMIN') && (
                      <Link
                        to="/admin/dashboard"
                        onClick={() => setUserDropdownOpen(false)}
                        className="block px-4 py-2 text-xs text-purple-700 hover:bg-purple-50 font-medium"
                      >
                        Admin Portal
                      </Link>
                    )}

                    <div className="border-t border-gray-100 mt-1 pt-1">
                      <button
                        onClick={handleLogout}
                        className="w-full text-left px-4 py-2 text-xs text-rose-600 hover:bg-rose-50 flex items-center gap-1.5 font-medium"
                      >
                        <LogOut className="w-3.5 h-3.5" />
                        <span>Sign Out</span>
                      </button>
                    </div>
                  </div>
                )}
              </div>
            ) : (
              <Link
                to="/login"
                className="inline-flex items-center gap-2 px-4 py-2 rounded-full bg-ayur-800 text-white text-sm font-medium hover:bg-ayur-700 shadow-sm hover:shadow transition-all"
              >
                <User className="w-4 h-4" />
                <span>Sign In</span>
              </Link>
            )}
          </div>

          {/* Mobile Menu Button */}
          <div className="flex lg:hidden items-center gap-2">
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
            {isAuthenticated ? (
              <button
                onClick={handleLogout}
                className="flex-1 text-center py-2.5 rounded-lg bg-rose-600 text-white text-sm font-medium"
              >
                Sign Out ({user.fullName.split(' ')[0]})
              </button>
            ) : (
              <>
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
              </>
            )}
          </div>
        </div>
      )}
    </header>
  );
}
