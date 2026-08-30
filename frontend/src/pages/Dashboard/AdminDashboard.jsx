import React, { useState, useEffect } from 'react';
import { ShieldCheck, Users, Stethoscope, Calendar, Leaf, CheckCircle2, XCircle, AlertCircle, Clock } from 'lucide-react';
import adminService from '../../services/adminService';
import LoadingSpinner from '../../components/common/LoadingSpinner';

export default function AdminDashboard() {
  const [stats, setStats] = useState(null);
  const [pendingDoctors, setPendingDoctors] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [actionLoading, setActionLoading] = useState(null);

  const loadData = () => {
    setLoading(true);
    Promise.all([
      adminService.getPlatformStats(),
      adminService.getPendingDoctors(),
    ])
      .then(([statsRes, pendingRes]) => {
        setStats(statsRes.data);
        setPendingDoctors(pendingRes.data.content || []);
      })
      .catch((err) => {
        console.error("Failed to load admin data", err);
        setError("Could not load administrative governance console.");
      })
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    loadData();
  }, []);

  const handleDecision = async (doctorId, status) => {
    setActionLoading(doctorId);
    try {
      await adminService.verifyDoctor(doctorId, status);
      loadData();
    } catch (err) {
      console.error("Decision failed", err);
      alert("Failed to record verification decision.");
    } finally {
      setActionLoading(null);
    }
  };

  if (loading) return <LoadingSpinner message="Loading AyurVana administrative governance data..." />;

  return (
    <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-10 space-y-8">
      {/* Header */}
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4">
        <div>
          <div className="inline-flex items-center gap-1.5 px-3 py-1 rounded-full bg-purple-100 text-purple-900 text-xs font-bold uppercase tracking-wider mb-2">
            <ShieldCheck className="w-3.5 h-3.5" />
            <span>Platform Governance &amp; Administration</span>
          </div>
          <h1 className="font-serif text-3xl font-bold text-gray-950">Admin Console</h1>
          <p className="text-xs sm:text-sm text-gray-600">
            Monitor clinical metrics, verify medical credentials, and oversee Ayurvedic telemedicine compliance.
          </p>
        </div>
      </div>

      {error && (
        <div className="p-3.5 rounded-xl bg-rose-50 border border-rose-200 text-rose-800 text-xs flex items-center gap-2">
          <AlertCircle className="w-4 h-4 flex-shrink-0" />
          <span>{error}</span>
        </div>
      )}

      {/* Platform Statistics Grid */}
      {stats && (
        <div className="grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-6 gap-3">
          <div className="p-4 rounded-2xl bg-white border border-gray-200 shadow-2xs space-y-1">
            <span className="text-[10px] uppercase font-bold text-gray-400 block">Total Patients</span>
            <span className="font-serif text-2xl font-bold text-gray-900 block">{stats.totalUsers}</span>
          </div>

          <div className="p-4 rounded-2xl bg-white border border-gray-200 shadow-2xs space-y-1">
            <span className="text-[10px] uppercase font-bold text-blue-600 block">Vaidyas / Doctors</span>
            <span className="font-serif text-2xl font-bold text-blue-900 block">{stats.totalDoctors}</span>
          </div>

          <div className="p-4 rounded-2xl bg-white border border-amber-200 shadow-2xs space-y-1 bg-amber-50/40">
            <span className="text-[10px] uppercase font-bold text-amber-700 block">Pending Review</span>
            <span className="font-serif text-2xl font-bold text-amber-900 block">{stats.pendingDoctorVerifications}</span>
          </div>

          <div className="p-4 rounded-2xl bg-white border border-gray-200 shadow-2xs space-y-1">
            <span className="text-[10px] uppercase font-bold text-gray-400 block">Total Bookings</span>
            <span className="font-serif text-2xl font-bold text-gray-900 block">{stats.totalAppointments}</span>
          </div>

          <div className="p-4 rounded-2xl bg-white border border-gray-200 shadow-2xs space-y-1">
            <span className="text-[10px] uppercase font-bold text-emerald-600 block">Completed Calls</span>
            <span className="font-serif text-2xl font-bold text-emerald-800 block">{stats.completedConsultations}</span>
          </div>

          <div className="p-4 rounded-2xl bg-white border border-gray-200 shadow-2xs space-y-1">
            <span className="text-[10px] uppercase font-bold text-teal-600 block">Herbs Cataloged</span>
            <span className="font-serif text-2xl font-bold text-teal-900 block">{stats.totalHerbs}</span>
          </div>
        </div>
      )}

      {/* Pending Credential Verification Queue */}
      <div className="p-6 sm:p-8 rounded-3xl bg-white border border-gray-200 shadow-sm space-y-6">
        <div className="flex items-center justify-between border-b border-gray-100 pb-4">
          <div>
            <h2 className="font-serif text-xl font-bold text-gray-950">
              Doctor Credential Verification Queue
            </h2>
            <p className="text-xs text-gray-500">
              Review qualifications, State Medical Council records, and registration numbers before certifying practitioners.
            </p>
          </div>
          <span className="px-3 py-1 rounded-full bg-purple-50 text-purple-800 text-xs font-bold">
            {pendingDoctors.length} Awaiting Audit
          </span>
        </div>

        {pendingDoctors.length === 0 ? (
          <div className="py-16 text-center text-gray-400 space-y-2">
            <CheckCircle2 className="w-10 h-10 mx-auto text-emerald-500" />
            <h3 className="font-serif text-base font-bold text-gray-700">Verification Queue is Clear</h3>
            <p className="text-xs text-gray-500">All registered Ayurvedic practitioners have been audited and verified.</p>
          </div>
        ) : (
          <div className="space-y-4">
            {pendingDoctors.map((doc) => (
              <div
                key={doc.id}
                className="p-5 rounded-2xl border border-gray-200 bg-gray-50/50 flex flex-col md:flex-row md:items-center justify-between gap-4"
              >
                <div className="space-y-1.5">
                  <div className="flex items-center gap-2">
                    <h3 className="font-serif text-base font-bold text-gray-900">{doc.fullName}</h3>
                    <span className="text-xs text-gray-500 font-mono">({doc.email})</span>
                  </div>

                  <p className="text-xs font-semibold text-earth-800">{doc.qualifications}</p>

                  <div className="flex flex-wrap items-center gap-3 text-xs text-gray-600 pt-1">
                    <span>Council: <strong>{doc.stateMedicalCouncil}</strong></span>
                    <span>•</span>
                    <span>License: <strong className="font-mono">{doc.medicalLicenseNumber}</strong></span>
                    <span>•</span>
                    <span>Experience: <strong>{doc.yearsOfExperience} Years</strong></span>
                    <span>•</span>
                    <span>Specialization: <strong>{doc.primarySpecialization}</strong></span>
                  </div>
                </div>

                <div className="flex items-center gap-2 self-end md:self-auto">
                  <button
                    onClick={() => handleDecision(doc.id, 'APPROVED')}
                    disabled={actionLoading === doc.id}
                    className="px-4 py-2 rounded-full bg-emerald-800 hover:bg-emerald-700 text-white font-semibold text-xs transition-colors shadow-xs disabled:opacity-50 flex items-center gap-1"
                  >
                    <CheckCircle2 className="w-3.5 h-3.5" />
                    <span>Approve Credentials</span>
                  </button>

                  <button
                    onClick={() => handleDecision(doc.id, 'REJECTED')}
                    disabled={actionLoading === doc.id}
                    className="px-4 py-2 rounded-full border border-rose-200 text-rose-700 hover:bg-rose-50 font-semibold text-xs transition-colors disabled:opacity-50 flex items-center gap-1"
                  >
                    <XCircle className="w-3.5 h-3.5" />
                    <span>Reject</span>
                  </button>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}
