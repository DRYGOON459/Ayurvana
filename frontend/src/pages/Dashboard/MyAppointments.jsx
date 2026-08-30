import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Calendar, Clock, Stethoscope, CheckCircle2, AlertCircle, XCircle, ChevronRight, MessageSquare } from 'lucide-react';
import appointmentService from '../../services/appointmentService';
import LoadingSpinner from '../../components/common/LoadingSpinner';
import Pagination from '../../components/common/Pagination';

export default function MyAppointments() {
  const [appointments, setAppointments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [actionLoading, setActionLoading] = useState(null);
  const [error, setError] = useState('');
  const [activeTab, setActiveTab] = useState('ALL');

  const fetchAppointments = (pageNum = 0) => {
    setLoading(true);
    appointmentService.getMyAppointments(pageNum, 10)
      .then((res) => {
        setAppointments(res.data.content || []);
        setTotalPages(res.data.totalPages || 0);
        setPage(res.data.page || 0);
      })
      .catch((err) => {
        console.error("Failed to load appointments", err);
        setError("Could not retrieve your appointments.");
      })
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    fetchAppointments(page);
  }, [page]);

  const handleCancel = async (appointmentId) => {
    const reason = window.prompt("Please provide a reason for cancellation (optional):");
    if (reason === null) return; // User clicked Cancel in prompt

    setActionLoading(appointmentId);
    try {
      await appointmentService.updateStatus(appointmentId, 'CANCELLED', reason || 'Cancelled by patient');
      fetchAppointments(page);
    } catch (err) {
      console.error("Cancellation failed", err);
      alert(err.response?.data?.message || 'Could not cancel appointment.');
    } finally {
      setActionLoading(null);
    }
  };

  const filteredAppointments = appointments.filter((app) => {
    if (activeTab === 'UPCOMING') {
      return app.status === 'CONFIRMED' || app.status === 'PENDING';
    }
    if (activeTab === 'PAST') {
      return app.status === 'COMPLETED' || app.status === 'CANCELLED';
    }
    return true;
  });

  const getStatusBadge = (status) => {
    switch (status) {
      case 'CONFIRMED':
        return (
          <span className="px-2.5 py-1 rounded-full bg-emerald-100 text-emerald-800 text-[11px] font-bold inline-flex items-center gap-1">
            <CheckCircle2 className="w-3 h-3" /> Confirmed
          </span>
        );
      case 'PENDING':
        return (
          <span className="px-2.5 py-1 rounded-full bg-amber-100 text-amber-800 text-[11px] font-bold inline-flex items-center gap-1">
            <Clock className="w-3 h-3" /> Pending Confirmation
          </span>
        );
      case 'CANCELLED':
        return (
          <span className="px-2.5 py-1 rounded-full bg-rose-100 text-rose-800 text-[11px] font-bold inline-flex items-center gap-1">
            <XCircle className="w-3 h-3" /> Cancelled
          </span>
        );
      case 'COMPLETED':
        return (
          <span className="px-2.5 py-1 rounded-full bg-blue-100 text-blue-800 text-[11px] font-bold inline-flex items-center gap-1">
            <CheckCircle2 className="w-3 h-3" /> Completed
          </span>
        );
      default:
        return (
          <span className="px-2.5 py-1 rounded-full bg-gray-100 text-gray-800 text-[11px] font-bold">
            {status}
          </span>
        );
    }
  };

  return (
    <div className="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8 py-10 space-y-8">
      {/* Header */}
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4">
        <div>
          <span className="text-[10px] uppercase font-bold text-blue-800 tracking-wider block">
            Clinical Consultations
          </span>
          <h1 className="font-serif text-3xl font-bold text-gray-950">
            My Appointments
          </h1>
          <p className="text-xs sm:text-sm text-gray-600">
            Manage scheduled consultations with your verified Ayurvedic physicians.
          </p>
        </div>

        <Link
          to="/doctors"
          className="px-5 py-2.5 rounded-full bg-blue-900 hover:bg-blue-800 text-white text-xs font-semibold self-start sm:self-auto transition-colors shadow-sm"
        >
          Book New Consultation
        </Link>
      </div>

      {/* Filter Tabs */}
      <div className="flex gap-2 border-b border-gray-200 pb-3 text-xs font-medium">
        <button
          onClick={() => setActiveTab('ALL')}
          className={`px-4 py-1.5 rounded-full transition-colors ${
            activeTab === 'ALL'
              ? 'bg-blue-900 text-white font-bold'
              : 'bg-white text-gray-600 hover:bg-gray-50 border border-gray-200'
          }`}
        >
          All ({appointments.length})
        </button>
        <button
          onClick={() => setActiveTab('UPCOMING')}
          className={`px-4 py-1.5 rounded-full transition-colors ${
            activeTab === 'UPCOMING'
              ? 'bg-blue-900 text-white font-bold'
              : 'bg-white text-gray-600 hover:bg-gray-50 border border-gray-200'
          }`}
        >
          Upcoming
        </button>
        <button
          onClick={() => setActiveTab('PAST')}
          className={`px-4 py-1.5 rounded-full transition-colors ${
            activeTab === 'PAST'
              ? 'bg-blue-900 text-white font-bold'
              : 'bg-white text-gray-600 hover:bg-gray-50 border border-gray-200'
          }`}
        >
          Past &amp; Cancelled
        </button>
      </div>

      {error && (
        <div className="p-3.5 rounded-xl bg-rose-50 border border-rose-200 text-rose-800 text-xs flex items-center gap-2">
          <AlertCircle className="w-4 h-4 flex-shrink-0" />
          <span>{error}</span>
        </div>
      )}

      {loading ? (
        <LoadingSpinner message="Retrieving your consultation bookings..." />
      ) : filteredAppointments.length === 0 ? (
        <div className="text-center py-20 bg-white rounded-3xl border border-gray-200 space-y-4">
          <Calendar className="w-12 h-12 text-gray-300 mx-auto" />
          <h3 className="font-serif text-lg font-bold text-gray-700">No appointments found</h3>
          <p className="text-xs text-gray-500 max-w-sm mx-auto">
            You do not have any appointments in this view. Browse certified Ayurvedic physicians to schedule a session.
          </p>
          <Link
            to="/doctors"
            className="inline-block px-6 py-2.5 rounded-full bg-blue-900 text-white text-xs font-semibold hover:bg-blue-800 transition-colors shadow-sm"
          >
            Find a Doctor
          </Link>
        </div>
      ) : (
        <div className="space-y-4">
          {filteredAppointments.map((app) => (
            <div
              key={app.id}
              className="p-6 rounded-3xl bg-white border border-gray-200/80 shadow-sm hover:shadow transition-all space-y-4"
            >
              <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 pb-4 border-b border-gray-100">
                <div className="flex items-center gap-4">
                  <div className="w-12 h-12 rounded-2xl bg-blue-50 text-blue-900 flex items-center justify-center font-serif text-lg font-bold flex-shrink-0">
                    {app.doctorAvatarUrl ? (
                      <img src={app.doctorAvatarUrl} alt={app.doctorName} className="w-full h-full object-cover rounded-2xl" />
                    ) : (
                      app.doctorName.charAt(0)
                    )}
                  </div>

                  <div>
                    <h3 className="font-serif text-base font-bold text-gray-900">
                      {app.doctorName}
                    </h3>
                    <p className="text-xs text-gray-500">{app.doctorQualifications}</p>
                    <span className="text-[10px] font-semibold text-blue-800 uppercase tracking-wide">
                      {app.doctorSpecialization}
                    </span>
                  </div>
                </div>

                <div className="flex items-center gap-3">
                  {getStatusBadge(app.status)}
                  <span className="text-xs font-bold text-gray-700">₹{app.consultationFee}</span>
                </div>
              </div>

              {/* Date & Time Row */}
              <div className="grid grid-cols-1 sm:grid-cols-2 gap-3 text-xs">
                <div className="flex items-center gap-2 text-gray-700 bg-gray-50 p-2.5 rounded-xl">
                  <Calendar className="w-4 h-4 text-blue-700" />
                  <span>Date: <strong>{app.appointmentDate}</strong></span>
                </div>

                <div className="flex items-center gap-2 text-gray-700 bg-gray-50 p-2.5 rounded-xl">
                  <Clock className="w-4 h-4 text-blue-700" />
                  <span>Time: <strong>{app.startTime.substring(0, 5)} - {app.endTime.substring(0, 5)}</strong> (30 min session)</span>
                </div>
              </div>

              {/* Patient Notes */}
              {app.patientNotes && (
                <div className="text-xs text-gray-600 bg-blue-50/40 p-3 rounded-xl border border-blue-100">
                  <strong className="text-blue-950 block">Your Notes:</strong>
                  <span>{app.patientNotes}</span>
                </div>
              )}

              {/* Cancellation Reason */}
              {app.status === 'CANCELLED' && app.cancellationReason && (
                <div className="text-xs text-rose-700 bg-rose-50 p-3 rounded-xl border border-rose-200">
                  <strong className="block">Cancellation Reason:</strong>
                  <span>{app.cancellationReason}</span>
                </div>
              )}

              {/* Actions */}
              <div className="flex items-center justify-end gap-3 pt-2">
                {(app.status === 'CONFIRMED' || app.status === 'PENDING') && (
                  <button
                    onClick={() => handleCancel(app.id)}
                    disabled={actionLoading === app.id}
                    className="px-4 py-2 rounded-full border border-rose-200 text-rose-700 hover:bg-rose-50 text-xs font-semibold transition-colors disabled:opacity-50"
                  >
                    {actionLoading === app.id ? 'Cancelling...' : 'Cancel Appointment'}
                  </button>
                )}

                {app.status === 'CONFIRMED' && (
                  <Link
                    to={`/consultation/${app.id}`}
                    className="px-5 py-2 rounded-full bg-emerald-800 hover:bg-emerald-700 text-white text-xs font-semibold transition-colors inline-flex items-center gap-1.5 shadow-xs"
                  >
                    <MessageSquare className="w-3.5 h-3.5" />
                    <span>Enter Consultation Room</span>
                  </Link>
                )}

                <Link
                  to={`/doctors/${app.doctorId}`}
                  className="px-4 py-2 rounded-full bg-gray-100 hover:bg-gray-200 text-gray-800 text-xs font-semibold transition-colors inline-flex items-center gap-1"
                >
                  <span>Doctor Profile</span>
                  <ChevronRight className="w-3 h-3" />
                </Link>
              </div>
            </div>
          ))}
        </div>
      )}

      <Pagination page={page} totalPages={totalPages} onPageChange={setPage} />
    </div>
  );
}
