import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Stethoscope, Calendar, Clock, DollarSign, Star, CheckCircle2, User, MessageSquare, AlertCircle } from 'lucide-react';
import doctorService from '../../services/doctorService';
import appointmentService from '../../services/appointmentService';
import LoadingSpinner from '../../components/common/LoadingSpinner';

export default function DoctorDashboard() {
  const [profile, setProfile] = useState(null);
  const [stats, setStats] = useState(null);
  const [appointments, setAppointments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  const loadData = () => {
    setLoading(true);
    Promise.all([
      doctorService.getMyDoctorProfile(),
      doctorService.getMyDoctorStats(),
      appointmentService.getDoctorSchedule(0, 20),
    ])
      .then(([profRes, statsRes, appRes]) => {
        setProfile(profRes.data);
        setStats(statsRes.data);
        setAppointments(appRes.data.content || []);
      })
      .catch((err) => {
        console.error("Failed to load doctor dashboard", err);
        setError("Could not load doctor consultation data.");
      })
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    loadData();
  }, []);

  const handleStatusUpdate = async (appointmentId, status) => {
    try {
      await appointmentService.updateStatus(appointmentId, status);
      loadData();
    } catch (err) {
      console.error("Failed to update status", err);
      alert("Status update failed.");
    }
  };

  if (loading) return <LoadingSpinner message="Opening doctor consultation console..." />;

  return (
    <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-10 space-y-8">
      {error && (
        <div className="p-3.5 rounded-xl bg-rose-50 border border-rose-200 text-rose-800 text-xs flex items-center gap-2">
          <AlertCircle className="w-4 h-4 flex-shrink-0" />
          <span>{error}</span>
        </div>
      )}

      {/* Practitioner Identity Card */}
      {profile && (
        <div className="p-6 sm:p-8 rounded-3xl bg-white border border-gray-200 shadow-sm flex flex-col sm:flex-row sm:items-center justify-between gap-6">
          <div className="flex items-center gap-5">
            <div className="w-16 h-16 sm:w-20 sm:h-20 rounded-2xl bg-blue-950 text-white flex items-center justify-center font-serif text-2xl sm:text-3xl font-bold flex-shrink-0">
              {profile.fullName?.charAt(0) || 'D'}
            </div>

            <div className="space-y-1">
              <div className="flex items-center gap-2">
                <h1 className="font-serif text-2xl font-bold text-gray-950">{profile.fullName}</h1>
                <span className="px-2.5 py-0.5 rounded-full bg-blue-100 text-blue-800 text-[10px] font-bold">
                  {profile.verificationStatus}
                </span>
              </div>
              <p className="text-xs font-semibold text-earth-800">{profile.qualifications}</p>
              <p className="text-xs text-gray-500 font-mono">
                {profile.stateMedicalCouncil} • Reg: {profile.medicalLicenseNumber}
              </p>
            </div>
          </div>

          <div className="text-right border-t sm:border-t-0 sm:border-l border-gray-100 pt-4 sm:pt-0 sm:pl-6 space-y-1">
            <span className="text-[10px] uppercase font-bold text-gray-400 block">Consultation Rate</span>
            <span className="font-serif text-2xl font-bold text-gray-900 block">₹{profile.consultationFee}</span>
            <span className="text-xs text-gray-500 block">Per 30-min Session</span>
          </div>
        </div>
      )}

      {/* Stats Summary Grid */}
      {stats && (
        <div className="grid grid-cols-2 sm:grid-cols-4 gap-4">
          <div className="p-5 rounded-2xl bg-white border border-gray-200 shadow-2xs space-y-1">
            <span className="text-[10px] uppercase font-bold text-gray-400 block">Total Consultations</span>
            <span className="font-serif text-2xl font-bold text-gray-900 block">{stats.totalAppointments}</span>
          </div>

          <div className="p-5 rounded-2xl bg-white border border-gray-200 shadow-2xs space-y-1">
            <span className="text-[10px] uppercase font-bold text-blue-600 block">Upcoming / Active</span>
            <span className="font-serif text-2xl font-bold text-blue-900 block">{stats.upcomingAppointments}</span>
          </div>

          <div className="p-5 rounded-2xl bg-white border border-gray-200 shadow-2xs space-y-1">
            <span className="text-[10px] uppercase font-bold text-emerald-600 block">Completed Sessions</span>
            <span className="font-serif text-2xl font-bold text-emerald-800 block">{stats.completedAppointments}</span>
          </div>

          <div className="p-5 rounded-2xl bg-white border border-gray-200 shadow-2xs space-y-1">
            <span className="text-[10px] uppercase font-bold text-amber-600 block">Estimated Revenue</span>
            <span className="font-serif text-2xl font-bold text-amber-800 block">₹{stats.totalEarnings}</span>
          </div>
        </div>
      )}

      {/* Patient Appointments Queue */}
      <div className="p-6 sm:p-8 rounded-3xl bg-white border border-gray-200 shadow-sm space-y-6">
        <div className="flex items-center justify-between border-b border-gray-100 pb-4">
          <div>
            <h2 className="font-serif text-xl font-bold text-gray-950">Patient Consultation Schedule</h2>
            <p className="text-xs text-gray-500">Upcoming booked sessions and clinical telemedicine history.</p>
          </div>
          <span className="text-xs font-bold text-gray-500">{appointments.length} Total Bookings</span>
        </div>

        {appointments.length === 0 ? (
          <div className="py-12 text-center text-gray-400 space-y-2">
            <Calendar className="w-8 h-8 mx-auto text-gray-300" />
            <p className="text-xs">No patient bookings scheduled at this time.</p>
          </div>
        ) : (
          <div className="overflow-x-auto">
            <table className="w-full text-left text-xs">
              <thead className="bg-gray-50 text-gray-500 uppercase font-bold text-[10px] border-y border-gray-200/80">
                <tr>
                  <th className="py-3 px-4">Patient Name</th>
                  <th className="py-3 px-4">Date</th>
                  <th className="py-3 px-4">Slot</th>
                  <th className="py-3 px-4">Symptoms / Inquiry</th>
                  <th className="py-3 px-4">Status</th>
                  <th className="py-3 px-4 text-right">Actions</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-gray-100 text-gray-700">
                {appointments.map((app) => (
                  <tr key={app.id} className="hover:bg-gray-50/60 transition-colors">
                    <td className="py-3.5 px-4 font-semibold text-gray-900">
                      {app.patientName}
                      <span className="block text-[10px] text-gray-400 font-normal">{app.patientEmail}</span>
                    </td>
                    <td className="py-3.5 px-4 font-medium">{app.appointmentDate}</td>
                    <td className="py-3.5 px-4 font-mono font-medium">
                      {app.startTime.substring(0, 5)} - {app.endTime.substring(0, 5)}
                    </td>
                    <td className="py-3.5 px-4 max-w-xs truncate text-gray-600">
                      {app.patientNotes || 'No notes provided'}
                    </td>
                    <td className="py-3.5 px-4">
                      <span
                        className={`px-2.5 py-1 rounded-full text-[10px] font-bold ${
                          app.status === 'CONFIRMED'
                            ? 'bg-emerald-100 text-emerald-800'
                            : app.status === 'COMPLETED'
                            ? 'bg-blue-100 text-blue-800'
                            : app.status === 'CANCELLED'
                            ? 'bg-rose-100 text-rose-800'
                            : 'bg-amber-100 text-amber-800'
                        }`}
                      >
                        {app.status}
                      </span>
                    </td>
                    <td className="py-3.5 px-4 text-right space-x-2">
                      {app.status === 'CONFIRMED' && (
                        <>
                          <Link
                            to={`/consultation/${app.id}`}
                            className="inline-flex items-center gap-1 px-3 py-1.5 rounded-full bg-blue-900 hover:bg-blue-800 text-white font-semibold text-[11px] transition-colors shadow-2xs"
                          >
                            <MessageSquare className="w-3 h-3" />
                            <span>Enter Room</span>
                          </Link>

                          <button
                            onClick={() => handleStatusUpdate(app.id, 'COMPLETED')}
                            className="px-2.5 py-1.5 rounded-full border border-gray-200 text-gray-700 hover:bg-gray-100 font-semibold text-[11px] transition-colors"
                          >
                            Complete
                          </button>
                        </>
                      )}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  );
}
