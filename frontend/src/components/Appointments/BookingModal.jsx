import React, { useState, useEffect } from 'react';
import { Calendar, Clock, CheckCircle2, AlertCircle, X, Loader2 } from 'lucide-react';
import appointmentService from '../../services/appointmentService';
import { useAuth } from '../../context/AuthContext';
import { useNavigate } from 'react-router-dom';

export default function BookingModal({ doctor, isOpen, onClose }) {
  const { user } = useAuth();
  const navigate = useNavigate();

  // Next 14 days generator
  const getDates = () => {
    const dates = [];
    const today = new Date();
    for (let i = 0; i < 14; i++) {
      const d = new Date(today);
      d.setDate(today.getDate() + i);
      dates.push(d.toISOString().split('T')[0]);
    }
    return dates;
  };

  const availableDates = getDates();
  const [selectedDate, setSelectedDate] = useState(availableDates[0]);
  const [slots, setSlots] = useState([]);
  const [selectedSlot, setSelectedSlot] = useState(null);
  const [patientNotes, setPatientNotes] = useState('');
  const [loadingSlots, setLoadingSlots] = useState(false);
  const [booking, setBooking] = useState(false);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState(false);

  useEffect(() => {
    if (!isOpen || !doctor?.id || !selectedDate) return;
    setLoadingSlots(true);
    setError('');
    setSelectedSlot(null);

    appointmentService.getDoctorAvailability(doctor.id, selectedDate)
      .then((res) => {
        setSlots(res.data.slots || []);
      })
      .catch((err) => {
        console.error("Failed to load slots", err);
        setError("Unable to load slots for the selected date.");
      })
      .finally(() => setLoadingSlots(false));
  }, [isOpen, doctor?.id, selectedDate]);

  if (!isOpen) return null;

  const handleBooking = async (e) => {
    e.preventDefault();
    if (!user) {
      navigate('/login');
      return;
    }

    if (!selectedSlot) {
      setError('Please select an available consultation time slot.');
      return;
    }

    setBooking(true);
    setError('');
    try {
      await appointmentService.bookAppointment({
        doctorId: doctor.id,
        appointmentDate: selectedDate,
        startTime: selectedSlot.startTime,
        endTime: selectedSlot.endTime,
        patientNotes: patientNotes.trim() || undefined,
      });

      setSuccess(true);
      setTimeout(() => {
        setSuccess(false);
        onClose();
        navigate('/appointments');
      }, 2000);
    } catch (err) {
      console.error("Booking error", err);
      const msg = err.response?.data?.message || err.response?.data?.data || 'Booking failed. Slot may have been taken.';
      setError(msg);
      // Refresh slots
      appointmentService.getDoctorAvailability(doctor.id, selectedDate).then(res => setSlots(res.data.slots || []));
    } finally {
      setBooking(false);
    }
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/60 backdrop-blur-xs animate-in fade-in duration-200">
      <div className="bg-white w-full max-w-lg rounded-3xl shadow-2xl border border-gray-200 overflow-hidden flex flex-col max-h-[90vh]">
        {/* Modal Header */}
        <div className="p-6 border-b border-gray-100 flex items-center justify-between bg-blue-950 text-white">
          <div>
            <span className="text-[10px] uppercase font-bold text-blue-200 tracking-wider block">
              Consultation Booking
            </span>
            <h3 className="font-serif text-lg font-bold">{doctor.fullName}</h3>
            <p className="text-xs text-blue-100/80">Fee: ₹{doctor.consultationFee} (30 mins)</p>
          </div>
          <button
            onClick={onClose}
            className="p-2 rounded-full hover:bg-white/10 text-white transition-colors"
          >
            <X className="w-5 h-5" />
          </button>
        </div>

        {/* Modal Body */}
        <div className="p-6 overflow-y-auto space-y-6 flex-1">
          {success ? (
            <div className="py-12 text-center space-y-3">
              <div className="w-16 h-16 rounded-full bg-emerald-100 text-emerald-700 flex items-center justify-center mx-auto">
                <CheckCircle2 className="w-10 h-10" />
              </div>
              <h4 className="font-serif text-xl font-bold text-gray-900">Appointment Confirmed!</h4>
              <p className="text-xs text-gray-600 max-w-xs mx-auto">
                Your consultation on <strong>{selectedDate}</strong> at <strong>{selectedSlot?.startTime}</strong> has been secured. Redirecting to your dashboard...
              </p>
            </div>
          ) : (
            <form onSubmit={handleBooking} className="space-y-5">
              {error && (
                <div className="p-3.5 rounded-xl bg-rose-50 border border-rose-200 text-rose-800 text-xs flex items-center gap-2">
                  <AlertCircle className="w-4 h-4 flex-shrink-0" />
                  <span>{error}</span>
                </div>
              )}

              {/* Date Selection */}
              <div>
                <label className="block text-xs font-bold uppercase tracking-wider text-gray-600 mb-2 flex items-center gap-1.5">
                  <Calendar className="w-3.5 h-3.5 text-blue-700" />
                  <span>1. Select Consultation Date</span>
                </label>
                <div className="flex gap-2 overflow-x-auto pb-2 scrollbar-thin">
                  {availableDates.map((dateStr) => {
                    const d = new Date(dateStr + 'T00:00:00');
                    const isSelected = selectedDate === dateStr;
                    return (
                      <button
                        key={dateStr}
                        type="button"
                        onClick={() => setSelectedDate(dateStr)}
                        className={`flex-shrink-0 px-3.5 py-2.5 rounded-2xl border text-center transition-all ${
                          isSelected
                            ? 'bg-blue-900 border-blue-900 text-white shadow-xs'
                            : 'bg-white border-gray-200 text-gray-700 hover:border-blue-300'
                        }`}
                      >
                        <span className="text-[10px] uppercase font-bold block opacity-75">
                          {d.toLocaleDateString('en-US', { weekday: 'short' })}
                        </span>
                        <span className="text-sm font-bold block mt-0.5">
                          {d.getDate()} {d.toLocaleDateString('en-US', { month: 'short' })}
                        </span>
                      </button>
                    );
                  })}
                </div>
              </div>

              {/* Time Slots */}
              <div>
                <label className="block text-xs font-bold uppercase tracking-wider text-gray-600 mb-2 flex items-center gap-1.5">
                  <Clock className="w-3.5 h-3.5 text-blue-700" />
                  <span>2. Select Time Slot (30 Mins)</span>
                </label>

                {loadingSlots ? (
                  <div className="py-8 flex items-center justify-center gap-2 text-xs text-gray-500">
                    <Loader2 className="w-4 h-4 animate-spin text-blue-700" />
                    <span>Loading available doctor slots...</span>
                  </div>
                ) : slots.length === 0 ? (
                  <p className="text-xs text-gray-500 py-4 text-center bg-gray-50 rounded-xl">
                    No consultation slots available for this date.
                  </p>
                ) : (
                  <div className="grid grid-cols-3 sm:grid-cols-4 gap-2 max-h-48 overflow-y-auto p-1">
                    {slots.map((slot) => {
                      const isSelected = selectedSlot?.startTime === slot.startTime;
                      return (
                        <button
                          key={slot.startTime}
                          type="button"
                          disabled={!slot.available}
                          onClick={() => setSelectedSlot(slot)}
                          className={`py-2 px-1 rounded-xl text-xs font-medium border text-center transition-all ${
                            !slot.available
                              ? 'bg-gray-100 border-gray-200 text-gray-400 cursor-not-allowed line-through'
                              : isSelected
                              ? 'bg-emerald-700 border-emerald-700 text-white font-bold shadow-xs'
                              : 'bg-white border-gray-200 text-gray-800 hover:border-emerald-500 hover:bg-emerald-50/50'
                          }`}
                        >
                          {slot.startTime.substring(0, 5)}
                        </button>
                      );
                    })}
                  </div>
                )}
              </div>

              {/* Patient Notes */}
              <div>
                <label className="block text-xs font-bold uppercase tracking-wider text-gray-600 mb-1.5">
                  3. Health Symptoms / Reason for Visit (Optional)
                </label>
                <textarea
                  rows="2"
                  value={patientNotes}
                  onChange={(e) => setPatientNotes(e.target.value)}
                  placeholder="Mention current symptoms, Prakriti inquiries, or ongoing treatments..."
                  className="w-full px-3.5 py-2 rounded-xl border border-gray-300 text-xs focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all"
                />
              </div>

              {/* Submit Button */}
              <button
                type="submit"
                disabled={booking || !selectedSlot}
                className="w-full py-3 rounded-full bg-blue-900 hover:bg-blue-800 text-white font-semibold text-xs transition-all shadow-sm hover:shadow disabled:opacity-50 flex items-center justify-center gap-2"
              >
                {booking ? (
                  <>
                    <Loader2 className="w-4 h-4 animate-spin" />
                    <span>Locking Slot &amp; Confirming...</span>
                  </>
                ) : (
                  <span>Confirm Appointment (₹{doctor.consultationFee})</span>
                )}
              </button>
            </form>
          )}
        </div>
      </div>
    </div>
  );
}
