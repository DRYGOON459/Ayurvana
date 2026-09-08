import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { User, Mail, Phone, Shield, Save, CheckCircle2, Heart, Sparkles, AlertCircle } from 'lucide-react';
import { useAuth } from '../../context/AuthContext';
import authService from '../../services/authService';
import LoadingSpinner from '../../components/common/LoadingSpinner';

export default function UserProfile() {
  const { user } = useAuth();
  const [profile, setProfile] = useState(null);
  const [loading, setLoading] = useState(true);

  // Edit form state
  const [fullName, setFullName] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');
  const [bio, setBio] = useState('');
  const [doshaType, setDoshaType] = useState('TRIDOSHIC');
  const [saving, setSaving] = useState(false);
  const [successMessage, setSuccessMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  useEffect(() => {
    setLoading(true);
    authService.getCurrentUser()
      .then((res) => {
        setProfile(res.data);
        setFullName(res.data.fullName || '');
        setPhoneNumber(res.data.phoneNumber || '');
        setBio(res.data.bio || '');
        setDoshaType(res.data.doshaType || 'TRIDOSHIC');
      })
      .catch((err) => console.error("Error loading user profile", err))
      .finally(() => setLoading(false));
  }, []);

  const handleUpdate = async (e) => {
    e.preventDefault();
    setSaving(true);
    setSuccessMessage('');
    setErrorMessage('');

    try {
      const res = await authService.updateProfile({ fullName, phoneNumber, bio, doshaType });
      setProfile(res.data);
      setSuccessMessage('Profile updated successfully.');
      setTimeout(() => setSuccessMessage(''), 4000);
    } catch (err) {
      console.error("Update failed", err);
      setErrorMessage(err.response?.data?.message || 'Failed to update profile.');
    } finally {
      setSaving(false);
    }
  };

  if (loading) return <LoadingSpinner message="Loading your account profile..." />;

  return (
    <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-10 space-y-8">
      {/* Header */}
      <div className="flex items-center gap-4 border-b border-gray-200 pb-6">
        <div className="w-16 h-16 rounded-full bg-ayur-800 text-white flex items-center justify-center text-2xl font-bold font-serif shadow-md">
          {profile?.fullName?.charAt(0) || 'U'}
        </div>
        <div>
          <div className="flex items-center gap-2">
            <h1 className="font-serif text-2xl sm:text-3xl font-bold text-gray-950">
              {profile?.fullName}
            </h1>
            {profile?.active && (
              <span className="px-2 py-0.5 rounded-full bg-emerald-100 text-emerald-800 text-[11px] font-semibold flex items-center gap-1">
                <CheckCircle2 className="w-3 h-3" />
                Active
              </span>
            )}
          </div>
          <p className="text-xs sm:text-sm text-gray-500">{profile?.email}</p>
        </div>
      </div>

      {/* Roles & Status Banner */}
      <div className="p-4 rounded-2xl bg-white border border-gray-200 flex flex-wrap items-center justify-between gap-3">
        <div className="flex items-center gap-2">
          <Shield className="w-5 h-5 text-ayur-700" />
          <span className="text-xs font-semibold uppercase tracking-wider text-gray-600">
            Account Privileges:
          </span>
          <div className="flex gap-1.5">
            {profile?.roles?.map((r) => (
              <span key={r} className="px-2 py-0.5 rounded-md bg-ayur-100 text-ayur-800 text-xs font-bold">
                {r.replace('ROLE_', '')}
              </span>
            ))}
          </div>
        </div>

        <Link
          to="/favorites"
          className="inline-flex items-center gap-1.5 text-xs font-semibold text-rose-700 hover:text-rose-800"
        >
          <Heart className="w-3.5 h-3.5 fill-rose-600" />
          <span>Go to Saved Favorites</span>
        </Link>
      </div>

      {/* Ayurvedic Prakriti Card */}
      <div className="p-6 rounded-3xl bg-gradient-to-r from-ayur-900 to-teal-950 text-white space-y-3">
        <div className="flex items-center justify-between">
          <div className="flex items-center gap-2">
            <Sparkles className="w-5 h-5 text-amber-300" />
            <h2 className="font-serif text-xl font-bold">My Ayurvedic Constitution (Prakriti)</h2>
          </div>
          <Link
            to="/ayurveda/quiz"
            className="px-3 py-1 rounded-full bg-white/20 hover:bg-white/30 text-xs font-semibold backdrop-blur-sm transition-colors"
          >
            Take / Retake Quiz
          </Link>
        </div>
        <p className="text-xs sm:text-sm text-teal-100/90 leading-relaxed">
          Understanding whether your dominant bio-force is Vata (Ether/Air), Pitta (Fire/Water), or Kapha (Water/Earth) guides optimal nutrition, circadian routines, and herbal combinations.
        </p>
      </div>

      {/* Edit Form */}
      <div className="p-6 sm:p-8 rounded-3xl bg-white border border-gray-200 shadow-sm space-y-6">
        <h2 className="font-serif text-xl font-bold text-gray-900">Personal Information</h2>

        {successMessage && (
          <div className="p-3.5 rounded-xl bg-emerald-50 border border-emerald-200 text-emerald-800 text-xs flex items-center gap-2">
            <CheckCircle2 className="w-4 h-4" />
            <span>{successMessage}</span>
          </div>
        )}

        {errorMessage && (
          <div className="p-3.5 rounded-xl bg-rose-50 border border-rose-200 text-rose-800 text-xs flex items-center gap-2">
            <AlertCircle className="w-4 h-4" />
            <span>{errorMessage}</span>
          </div>
        )}

        <form onSubmit={handleUpdate} className="space-y-4">
          <div>
            <label className="block text-xs font-semibold uppercase tracking-wider text-gray-700 mb-1.5">
              Full Name
            </label>
            <div className="relative">
              <User className="w-4 h-4 text-gray-400 absolute left-3.5 top-1/2 -translate-y-1/2" />
              <input
                type="text"
                required
                value={fullName}
                onChange={(e) => setFullName(e.target.value)}
                className="w-full pl-10 pr-4 py-2.5 rounded-xl border border-gray-300 text-sm focus:outline-none focus:ring-2 focus:ring-ayur-500 focus:border-transparent transition-all"
              />
            </div>
          </div>

          <div>
            <label className="block text-xs font-semibold uppercase tracking-wider text-gray-700 mb-1.5">
              Registered Email (Read-Only)
            </label>
            <div className="relative">
              <Mail className="w-4 h-4 text-gray-400 absolute left-3.5 top-1/2 -translate-y-1/2" />
              <input
                type="email"
                disabled
                value={profile?.email || ''}
                className="w-full pl-10 pr-4 py-2.5 rounded-xl border border-gray-200 bg-gray-50 text-gray-500 text-sm cursor-not-allowed"
              />
            </div>
          </div>

          <div>
            <label className="block text-xs font-semibold uppercase tracking-wider text-gray-700 mb-1.5">
              Phone Number
            </label>
            <div className="relative">
              <Phone className="w-4 h-4 text-gray-400 absolute left-3.5 top-1/2 -translate-y-1/2" />
              <input
                type="tel"
                value={phoneNumber}
                onChange={(e) => setPhoneNumber(e.target.value)}
                placeholder="+91 98765 43210"
                className="w-full pl-10 pr-4 py-2.5 rounded-xl border border-gray-300 text-sm focus:outline-none focus:ring-2 focus:ring-emerald-500 focus:border-transparent transition-all"
              />
            </div>
          </div>

          <div>
            <label className="block text-xs font-semibold uppercase tracking-wider text-gray-700 mb-1.5">
              Dominant Ayurvedic Constitution (Dosha)
            </label>
            <select
              value={doshaType}
              onChange={(e) => setDoshaType(e.target.value)}
              className="w-full px-4 py-2.5 rounded-xl border border-gray-300 text-sm focus:outline-none focus:ring-2 focus:ring-emerald-500 bg-white"
            >
              <option value="VATA">Vata (Air &amp; Ether)</option>
              <option value="PITTA">Pitta (Fire &amp; Water)</option>
              <option value="KAPHA">Kapha (Water &amp; Earth)</option>
              <option value="TRIDOSHIC">Tridoshic (Balanced)</option>
            </select>
          </div>

          <div>
            <label className="block text-xs font-semibold uppercase tracking-wider text-gray-700 mb-1.5">
              Personal Wellness Bio &amp; Health Goals
            </label>
            <textarea
              rows="3"
              value={bio}
              onChange={(e) => setBio(e.target.value)}
              placeholder="Share dietary preferences, chronic concerns, or wellness goals..."
              className="w-full px-4 py-2.5 rounded-xl border border-gray-300 text-sm focus:outline-none focus:ring-2 focus:ring-emerald-500 transition-all"
            />
          </div>

          <div className="pt-2">
            <button
              type="submit"
              disabled={saving}
              className="inline-flex items-center gap-2 px-6 py-2.5 rounded-full bg-emerald-800 text-white text-xs font-semibold hover:bg-emerald-700 transition-all shadow-sm disabled:opacity-50"
            >
              <Save className="w-3.5 h-3.5" />
              <span>{saving ? 'Saving...' : 'Save Profile Changes'}</span>
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
