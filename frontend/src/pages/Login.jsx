import React, { useState } from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import { Leaf, Lock, Mail, AlertCircle, ArrowRight, Sparkles, CheckCircle2 } from 'lucide-react';
import { useAuth } from '../context/AuthContext';
import storeService from '../services/storeService';

export default function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [submitting, setSubmitting] = useState(false);

  // Forgot password modal state
  const [showForgotModal, setShowForgotModal] = useState(false);
  const [forgotEmail, setForgotEmail] = useState('');
  const [resetToken, setResetToken] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [forgotStep, setForgotStep] = useState(1);
  const [forgotLoading, setForgotLoading] = useState(false);
  const [forgotError, setForgotError] = useState('');
  const [forgotSuccess, setForgotSuccess] = useState('');

  const { login } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();
  const from = location.state?.from?.pathname || '/';

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSubmitting(true);

    try {
      await login(email, password);
      navigate(from, { replace: true });
    } catch (err) {
      console.error("Login failed", err);
      const msg = err.response?.data?.message || 'Invalid email or password. Please try again.';
      setError(msg);
    } finally {
      setSubmitting(false);
    }
  };

  const handleQuickFill = (demoEmail, demoPassword) => {
    setEmail(demoEmail);
    setPassword(demoPassword);
    setError('');
  };

  const handleSendResetCode = async (e) => {
    e.preventDefault();
    setForgotError('');
    setForgotSuccess('');
    setForgotLoading(true);
    try {
      const res = await storeService.forgotPassword(forgotEmail.trim());
      const token = res.data;
      setResetToken(token);
      setForgotSuccess(`Recovery code generated: ${token}. Use this code to set your new password.`);
      setForgotStep(2);
    } catch (err) {
      console.error("Forgot password request failed", err);
      setForgotError(err.response?.data?.message || 'Could not find account with that email.');
    } finally {
      setForgotLoading(false);
    }
  };

  const handleConfirmResetPassword = async (e) => {
    e.preventDefault();
    setForgotError('');
    setForgotSuccess('');
    setForgotLoading(true);
    try {
      await storeService.resetPassword(resetToken.trim(), newPassword);
      setForgotSuccess('Password has been successfully updated! You can now sign in.');
      setEmail(forgotEmail);
      setPassword(newPassword);
      setTimeout(() => {
        setShowForgotModal(false);
        setForgotStep(1);
      }, 1800);
    } catch (err) {
      console.error("Reset password failed", err);
      setForgotError(err.response?.data?.message || 'Invalid or expired recovery code.');
    } finally {
      setForgotLoading(false);
    }
  };

  return (
    <div className="min-h-[80vh] flex items-center justify-center px-4 py-12">
      <div className="w-full max-w-md space-y-8 bg-white p-8 sm:p-10 rounded-3xl border border-ayur-200 shadow-xl">
        {/* Brand Icon */}
        <div className="text-center space-y-2">
          <div className="w-12 h-12 rounded-full bg-ayur-700 text-white mx-auto flex items-center justify-center shadow-md">
            <Leaf className="w-6 h-6" />
          </div>
          <h1 className="font-serif text-2xl sm:text-3xl font-bold text-gray-950">
            Sign In to AyurVana
          </h1>
          <p className="text-xs sm:text-sm text-gray-500">
            Access your personalized wellness profile and saved favorites
          </p>
        </div>

        {/* Error Alert */}
        {error && (
          <div className="p-3.5 rounded-xl bg-rose-50 border border-rose-200 text-rose-800 text-xs flex items-center gap-2">
            <AlertCircle className="w-4 h-4 flex-shrink-0" />
            <span>{error}</span>
          </div>
        )}

        {/* Form */}
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-xs font-semibold uppercase tracking-wider text-gray-700 mb-1.5">
              Email Address
            </label>
            <div className="relative">
              <Mail className="w-4 h-4 text-gray-400 absolute left-3.5 top-1/2 -translate-y-1/2" />
              <input
                type="email"
                required
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                placeholder="name@example.com"
                className="w-full pl-10 pr-4 py-2.5 rounded-xl border border-gray-300 text-sm focus:outline-none focus:ring-2 focus:ring-ayur-500 focus:border-transparent transition-all"
              />
            </div>
          </div>

          <div>
            <label className="block text-xs font-semibold uppercase tracking-wider text-gray-700 mb-1.5">
              Password
            </label>
            <div className="relative">
              <Lock className="w-4 h-4 text-gray-400 absolute left-3.5 top-1/2 -translate-y-1/2" />
              <input
                type="password"
                required
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder="••••••••"
                className="w-full pl-10 pr-4 py-2.5 rounded-xl border border-gray-300 text-sm focus:outline-none focus:ring-2 focus:ring-ayur-500 focus:border-transparent transition-all"
              />
            </div>
          </div>

          <div className="flex items-center justify-between text-xs">
            <label className="flex items-center space-x-1.5 text-stone-600">
              <input type="checkbox" defaultChecked className="rounded border-stone-300 text-emerald-700" />
              <span>Remember me</span>
            </label>
            <button
              type="button"
              onClick={() => setShowForgotModal(true)}
              className="text-emerald-800 hover:text-emerald-950 font-semibold"
            >
              Forgot password?
            </button>
          </div>

          <button
            type="submit"
            disabled={submitting}
            className="w-full py-3 rounded-full bg-emerald-800 text-white text-sm font-semibold hover:bg-emerald-700 transition-all shadow-sm hover:shadow disabled:opacity-50 flex items-center justify-center gap-2"
          >
            <span>{submitting ? 'Authenticating...' : 'Sign In'}</span>
            <ArrowRight className="w-4 h-4" />
          </button>
        </form>

        {/* Quick Fill Demo Buttons */}
        <div className="pt-4 border-t border-gray-100 space-y-2">
          <span className="text-[11px] font-bold text-gray-400 uppercase tracking-wider block text-center">
            ⚡ Quick Test Demo Accounts
          </span>
          <div className="grid grid-cols-3 gap-2 text-[11px]">
            <button
              type="button"
              onClick={() => handleQuickFill('user@ayurvana.com', 'password123')}
              className="p-2 rounded-lg bg-gray-50 hover:bg-emerald-50 border border-gray-200 text-gray-700 font-medium transition-colors"
            >
              Demo User
            </button>
            <button
              type="button"
              onClick={() => handleQuickFill('doctor@ayurvana.com', 'password123')}
              className="p-2 rounded-lg bg-gray-50 hover:bg-emerald-50 border border-gray-200 text-gray-700 font-medium transition-colors"
            >
              Demo Doctor
            </button>
            <button
              type="button"
              onClick={() => handleQuickFill('admin@ayurvana.com', 'admin123')}
              className="p-2 rounded-lg bg-gray-50 hover:bg-emerald-50 border border-gray-200 text-gray-700 font-medium transition-colors"
            >
              Demo Admin
            </button>
          </div>
        </div>

        {/* Footer Link */}
        <div className="text-center text-xs text-gray-600">
          <span>Don't have an account? </span>
          <Link to="/register" className="text-emerald-800 font-semibold hover:underline">
            Register for free
          </Link>
        </div>
      </div>

      {/* Forgot Password Modal */}
      {showForgotModal && (
        <div className="fixed inset-0 z-50 bg-stone-900/60 backdrop-blur-xs flex items-center justify-center p-4">
          <div className="bg-white rounded-3xl p-6 sm:p-8 max-w-md w-full shadow-2xl border border-stone-200 space-y-5">
            <div className="flex items-center justify-between pb-3 border-b border-stone-100">
              <h3 className="font-serif font-bold text-lg text-stone-900">
                {forgotStep === 1 ? 'Password Recovery' : 'Create New Password'}
              </h3>
              <button
                onClick={() => {
                  setShowForgotModal(false);
                  setForgotStep(1);
                  setForgotError('');
                  setForgotSuccess('');
                }}
                className="text-stone-400 hover:text-stone-700 text-sm font-bold"
              >
                ✕
              </button>
            </div>

            {forgotError && (
              <div className="p-3 bg-rose-50 border border-rose-200 rounded-xl text-xs text-rose-800">
                {forgotError}
              </div>
            )}

            {forgotSuccess && (
              <div className="p-3 bg-emerald-50 border border-emerald-200 rounded-xl text-xs text-emerald-800">
                {forgotSuccess}
              </div>
            )}

            {forgotStep === 1 ? (
              <form onSubmit={handleSendResetCode} className="space-y-4">
                <p className="text-xs text-stone-600">
                  Enter your registered account email. We will generate a secure one-time password reset code.
                </p>
                <div>
                  <label className="block text-xs font-bold text-stone-700 mb-1">Email Address</label>
                  <input
                    type="email"
                    required
                    value={forgotEmail}
                    onChange={(e) => setForgotEmail(e.target.value)}
                    placeholder="name@example.com"
                    className="w-full px-4 py-2.5 rounded-xl border border-stone-300 text-xs focus:outline-none focus:ring-2 focus:ring-emerald-600"
                  />
                </div>
                <button
                  type="submit"
                  disabled={forgotLoading}
                  className="w-full py-3 bg-emerald-800 hover:bg-emerald-700 text-white rounded-xl font-bold text-xs shadow transition disabled:opacity-50"
                >
                  {forgotLoading ? 'Generating Code...' : 'Get Recovery Code'}
                </button>
              </form>
            ) : (
              <form onSubmit={handleConfirmResetPassword} className="space-y-4">
                <p className="text-xs text-stone-600">
                  Enter the recovery token received and choose your new password.
                </p>
                <div>
                  <label className="block text-xs font-bold text-stone-700 mb-1">Reset Code / Token</label>
                  <input
                    type="text"
                    required
                    value={resetToken}
                    onChange={(e) => setResetToken(e.target.value)}
                    className="w-full px-4 py-2.5 rounded-xl border border-stone-300 text-xs font-mono uppercase focus:outline-none focus:ring-2 focus:ring-emerald-600"
                  />
                </div>
                <div>
                  <label className="block text-xs font-bold text-stone-700 mb-1">New Password</label>
                  <input
                    type="password"
                    required
                    minLength="6"
                    value={newPassword}
                    onChange={(e) => setNewPassword(e.target.value)}
                    placeholder="Min 6 characters"
                    className="w-full px-4 py-2.5 rounded-xl border border-stone-300 text-xs focus:outline-none focus:ring-2 focus:ring-emerald-600"
                  />
                </div>
                <button
                  type="submit"
                  disabled={forgotLoading}
                  className="w-full py-3 bg-emerald-800 hover:bg-emerald-700 text-white rounded-xl font-bold text-xs shadow transition disabled:opacity-50"
                >
                  {forgotLoading ? 'Updating Password...' : 'Reset Password & Proceed'}
                </button>
              </form>
            )}
          </div>
        </div>
      )}
    </div>
  );
}
