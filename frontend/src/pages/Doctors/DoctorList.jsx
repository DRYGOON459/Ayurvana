import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Stethoscope, CheckCircle2, Star, Clock, Globe, ArrowRight, Shield, UserCheck } from 'lucide-react';
import doctorService from '../../services/doctorService';
import SearchBar from '../../components/common/SearchBar';
import Pagination from '../../components/common/Pagination';
import LoadingSpinner from '../../components/common/LoadingSpinner';
import Disclaimer from '../../components/common/Disclaimer';

export default function DoctorList() {
  const [doctors, setDoctors] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedSpecialization, setSelectedSpecialization] = useState('ALL');
  const [searchQuery, setSearchQuery] = useState('');
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  const specializations = [
    'ALL',
    'Kayachikitsa',
    'Panchakarma',
    'Rasayana & Longevity',
    'Dravyaguna',
    'Shalya Tantra'
  ];

  const fetchDoctors = (pageNum = 0, spec = selectedSpecialization, q = searchQuery) => {
    setLoading(true);
    doctorService.getVerifiedDoctors(spec, q, pageNum, 9)
      .then((res) => {
        setDoctors(res.data.content || []);
        setTotalPages(res.data.totalPages || 0);
        setPage(res.data.page || 0);
      })
      .catch((err) => console.error("Failed to load doctors", err))
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    fetchDoctors(page, selectedSpecialization, searchQuery);
  }, [page, selectedSpecialization]);

  const handleSearch = (q) => {
    setSearchQuery(q);
    setPage(0);
    fetchDoctors(0, selectedSpecialization, q);
  };

  const handleClearSearch = () => {
    setSearchQuery('');
    setPage(0);
    fetchDoctors(0, selectedSpecialization, '');
  };

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-10 space-y-10">
      {/* Header Banner */}
      <div className="rounded-3xl bg-gradient-to-r from-blue-950 via-teal-950 to-ayur-950 text-white p-8 sm:p-12 shadow-xl relative overflow-hidden">
        <div className="max-w-2xl space-y-4 relative z-10">
          <span className="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-blue-800/60 text-blue-200 text-xs font-semibold uppercase tracking-wider">
            <Shield className="w-3.5 h-3.5" />
            Verified Vaidyas &amp; Clinical Practitioners
          </span>
          <h1 className="font-serif text-3xl sm:text-4xl lg:text-5xl font-bold tracking-tight">
            Consult Certified Ayurvedic Doctors
          </h1>
          <p className="text-sm sm:text-base text-blue-100/90 leading-relaxed">
            Every practitioner on AyurVana undergoes strict credential verification of their BAMS / MD degrees and State Medical Council registrations before providing clinical consultations.
          </p>

          <div className="pt-2">
            <Link
              to="/doctors/onboarding"
              className="inline-flex items-center gap-2 px-5 py-2.5 rounded-full bg-white text-blue-950 text-xs font-bold hover:bg-blue-50 transition-colors shadow-sm"
            >
              <UserCheck className="w-4 h-4 text-blue-700" />
              <span>Are You an Ayurvedic Doctor? Register Here</span>
            </Link>
          </div>
        </div>
      </div>

      {/* Filter & Search Bar */}
      <div className="space-y-4">
        <SearchBar
          value={searchQuery}
          onChange={handleSearch}
          onClear={handleClearSearch}
          placeholder="Search doctor by name, qualification (e.g. BAMS, MD), or health focus..."
        />

        {/* Specialization Tabs */}
        <div className="flex flex-wrap items-center gap-2 border-b border-gray-200 pb-4">
          {specializations.map((spec) => (
            <button
              key={spec}
              onClick={() => { setSelectedSpecialization(spec); setPage(0); }}
              className={`px-4 py-1.5 rounded-full text-xs font-semibold transition-all ${
                selectedSpecialization === spec
                  ? 'bg-blue-900 text-white shadow-sm'
                  : 'bg-white text-gray-600 hover:bg-gray-50 border border-gray-200'
              }`}
            >
              {spec === 'ALL' ? 'All Specializations' : spec}
            </button>
          ))}
        </div>
      </div>

      {/* Doctor Grid */}
      {loading ? (
        <LoadingSpinner message="Locating verified Ayurvedic physicians..." />
      ) : doctors.length === 0 ? (
        <div className="text-center py-20 bg-white rounded-3xl border border-gray-200 space-y-3">
          <Stethoscope className="w-12 h-12 text-gray-300 mx-auto" />
          <h3 className="font-serif text-xl font-bold text-gray-700">No doctors matched your criteria</h3>
          <p className="text-sm text-gray-500">Try adjusting your specialization filter or search terms.</p>
        </div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {doctors.map((doc) => (
            <div
              key={doc.id}
              className="group rounded-3xl bg-white border border-gray-200/80 shadow-sm hover:shadow-md hover:border-blue-300 transition-all flex flex-col justify-between p-6 space-y-5"
            >
              <div className="space-y-4">
                {/* Doctor Avatar & Identity */}
                <div className="flex items-start gap-4">
                  <div className="w-16 h-16 rounded-2xl bg-blue-50 text-blue-900 flex items-center justify-center font-serif text-2xl font-bold flex-shrink-0 border border-blue-100">
                    {doc.avatarUrl ? (
                      <img src={doc.avatarUrl} alt={doc.fullName} className="w-full h-full object-cover rounded-2xl" />
                    ) : (
                      doc.fullName.charAt(0)
                    )}
                  </div>

                  <div className="space-y-1">
                    <div className="flex items-center gap-1.5">
                      <h3 className="font-serif text-lg font-bold text-gray-900 group-hover:text-blue-900 transition-colors">
                        {doc.fullName}
                      </h3>
                      {doc.verified && (
                        <CheckCircle2 className="w-4 h-4 text-blue-600 fill-blue-50 flex-shrink-0" title="Council Verified" />
                      )}
                    </div>
                    <p className="text-xs font-semibold text-earth-800 line-clamp-1">
                      {doc.qualifications}
                    </p>
                    <span className="inline-block px-2.5 py-0.5 rounded-full bg-blue-50 text-blue-800 text-[10px] font-bold">
                      {doc.primarySpecialization}
                    </span>
                  </div>
                </div>

                {/* Key Metrics */}
                <div className="grid grid-cols-3 gap-2 py-3 border-y border-gray-100 text-center text-xs">
                  <div>
                    <span className="text-[10px] uppercase font-bold text-gray-400 block">Experience</span>
                    <span className="font-semibold text-gray-800 mt-0.5 block">{doc.yearsOfExperience}+ Years</span>
                  </div>
                  <div>
                    <span className="text-[10px] uppercase font-bold text-gray-400 block">Rating</span>
                    <span className="font-semibold text-amber-700 mt-0.5 flex items-center justify-center gap-1">
                      <Star className="w-3 h-3 fill-amber-500 text-amber-500" />
                      {doc.averageRating} ({doc.totalReviews})
                    </span>
                  </div>
                  <div>
                    <span className="text-[10px] uppercase font-bold text-gray-400 block">Consult Fee</span>
                    <span className="font-semibold text-emerald-800 mt-0.5 block">₹{doc.consultationFee}</span>
                  </div>
                </div>

                {/* Languages */}
                <div className="flex items-center gap-2 text-xs text-gray-500">
                  <Globe className="w-3.5 h-3.5 text-gray-400" />
                  <span>Speaks: <strong className="text-gray-700">{doc.languagesSpoken}</strong></span>
                </div>
              </div>

              {/* Action Button */}
              <div>
                <Link
                  to={`/doctors/${doc.id}`}
                  className="w-full py-3 rounded-xl bg-blue-900 hover:bg-blue-800 text-white text-xs font-semibold transition-colors flex items-center justify-center gap-2 shadow-sm"
                >
                  <span>View Clinical Profile &amp; Book</span>
                  <ArrowRight className="w-3.5 h-3.5" />
                </Link>
              </div>
            </div>
          ))}
        </div>
      )}

      <Pagination page={page} totalPages={totalPages} onPageChange={setPage} />
      <Disclaimer variant="general" />
    </div>
  );
}
