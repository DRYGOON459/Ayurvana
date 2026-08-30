import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import { ArrowLeft, CheckCircle2, Shield, Star, Clock, Globe, Award, Calendar, Stethoscope } from 'lucide-react';
import doctorService from '../../services/doctorService';
import LoadingSpinner from '../../components/common/LoadingSpinner';
import Disclaimer from '../../components/common/Disclaimer';
import BookingModal from '../../components/Appointments/BookingModal';

export default function DoctorDetail() {
  const { id } = useParams();
  const [doctor, setDoctor] = useState(null);
  const [loading, setLoading] = useState(true);
  const [isBookingOpen, setIsBookingOpen] = useState(false);

  useEffect(() => {
    setLoading(true);
    doctorService.getDoctorById(id)
      .then((res) => setDoctor(res.data))
      .catch((err) => console.error("Error loading doctor profile", err))
      .finally(() => setLoading(false));
  }, [id]);

  if (loading) return <LoadingSpinner message="Retrieving certified practitioner credentials..." />;
  if (!doctor) {
    return (
      <div className="max-w-3xl mx-auto px-4 py-20 text-center space-y-4">
        <h2 className="font-serif text-2xl font-bold text-gray-800">Doctor Profile Not Found</h2>
        <Link to="/doctors" className="text-blue-700 font-semibold underline">Back to Doctor Directory</Link>
      </div>
    );
  }

  return (
    <article className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-10 space-y-8">
      <Link
        to="/doctors"
        className="inline-flex items-center gap-2 text-xs font-semibold uppercase tracking-wider text-blue-900 hover:text-blue-950 transition-colors"
      >
        <ArrowLeft className="w-4 h-4" />
        <span>Back to Doctors Directory</span>
      </Link>

      {/* Profile Header Box */}
      <div className="p-6 sm:p-8 rounded-3xl bg-white border border-gray-200 shadow-sm space-y-6">
        <div className="flex flex-col sm:flex-row sm:items-start justify-between gap-6">
          <div className="flex items-start gap-5">
            <div className="w-20 h-20 sm:w-24 sm:h-24 rounded-3xl bg-blue-50 text-blue-950 flex items-center justify-center font-serif text-3xl font-bold flex-shrink-0 border-2 border-blue-100 shadow-sm">
              {doctor.avatarUrl ? (
                <img src={doctor.avatarUrl} alt={doctor.fullName} className="w-full h-full object-cover rounded-3xl" />
              ) : (
                doctor.fullName.charAt(0)
              )}
            </div>

            <div className="space-y-1.5">
              <div className="flex items-center gap-2">
                <h1 className="font-serif text-2xl sm:text-3xl font-bold text-gray-950">
                  {doctor.fullName}
                </h1>
                {doctor.verified && (
                  <span className="px-2.5 py-0.5 rounded-full bg-blue-100 text-blue-800 text-[11px] font-bold flex items-center gap-1">
                    <CheckCircle2 className="w-3.5 h-3.5" />
                    Verified Vaidya
                  </span>
                )}
              </div>

              <p className="text-sm font-semibold text-earth-800">
                {doctor.qualifications}
              </p>

              <span className="inline-block px-3 py-1 rounded-full bg-blue-50 text-blue-900 text-xs font-bold">
                {doctor.primarySpecialization}
              </span>
            </div>
          </div>

          {/* Pricing & Booking Callout */}
          <div className="bg-blue-50/70 p-5 rounded-2xl border border-blue-100 text-center sm:text-right space-y-2 flex-shrink-0">
            <span className="text-[10px] uppercase font-bold text-gray-500 block">Consultation Fee</span>
            <span className="font-serif text-2xl sm:text-3xl font-bold text-gray-950 block">₹{doctor.consultationFee}</span>
            <span className="text-[11px] text-gray-500 block">Per 30-min Clinical Session</span>
          </div>
        </div>

        {/* Credentials Grid */}
        <div className="grid grid-cols-2 sm:grid-cols-4 gap-3 pt-4 border-t border-gray-100 text-xs">
          <div className="p-3.5 rounded-2xl bg-gray-50 border border-gray-200/80">
            <span className="text-[10px] uppercase font-bold text-gray-400 block">Medical License</span>
            <span className="font-mono font-bold text-gray-900 mt-1 block">{doctor.medicalLicenseNumber}</span>
          </div>

          <div className="p-3.5 rounded-2xl bg-gray-50 border border-gray-200/80">
            <span className="text-[10px] uppercase font-bold text-gray-400 block">Medical Council</span>
            <span className="font-medium text-gray-900 mt-1 block line-clamp-1">{doctor.stateMedicalCouncil}</span>
          </div>

          <div className="p-3.5 rounded-2xl bg-gray-50 border border-gray-200/80">
            <span className="text-[10px] uppercase font-bold text-gray-400 block">Clinical Experience</span>
            <span className="font-bold text-gray-900 mt-1 block">{doctor.yearsOfExperience}+ Years</span>
          </div>

          <div className="p-3.5 rounded-2xl bg-gray-50 border border-gray-200/80">
            <span className="text-[10px] uppercase font-bold text-gray-400 block">Languages</span>
            <span className="font-medium text-gray-900 mt-1 block line-clamp-1">{doctor.languagesSpoken}</span>
          </div>
        </div>
      </div>

      {/* Clinical Biography */}
      <section className="p-6 sm:p-8 rounded-3xl bg-white border border-gray-200 space-y-3">
        <h2 className="font-serif text-xl font-bold text-gray-950">
          About the Practitioner &amp; Clinical Approach
        </h2>
        <p className="text-sm sm:text-base text-gray-700 leading-relaxed">
          {doctor.bio}
        </p>
      </section>

      {/* Appointment Booking Trigger Box */}
      <section className="p-8 rounded-3xl bg-gradient-to-r from-blue-900 via-blue-950 to-teal-950 text-white flex flex-col sm:flex-row sm:items-center justify-between gap-6 shadow-lg">
        <div className="space-y-1.5">
          <div className="flex items-center gap-2">
            <Calendar className="w-5 h-5 text-blue-300" />
            <h3 className="font-serif text-xl font-bold">Schedule an Appointment</h3>
          </div>
          <p className="text-xs sm:text-sm text-blue-100/90 max-w-md">
            Select a verified consultation slot for 1-on-1 pulse evaluation, herbal prescription formulation, and holistic diet charting.
          </p>
        </div>

        <button
          onClick={() => setIsBookingOpen(true)}
          className="px-8 py-3.5 rounded-full bg-white text-blue-950 font-bold text-xs hover:bg-blue-50 transition-colors shadow-md flex-shrink-0"
        >
          Book Consultation Slot
        </button>
      </section>

      <BookingModal
        doctor={doctor}
        isOpen={isBookingOpen}
        onClose={() => setIsBookingOpen(false)}
      />

      <Disclaimer variant="general" />
    </article>
  );
}
