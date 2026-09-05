import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import Navbar from './components/common/Navbar';
import Footer from './components/common/Footer';
import Home from './pages/Home';

// Knowledge Hub Pages
import AyurvedaHub from './pages/Ayurveda/AyurvedaHub';
import AyurvedaTopicDetail from './pages/Ayurveda/AyurvedaTopicDetail';
import DoshaQuiz from './pages/Ayurveda/DoshaQuiz';
import DinacharyaPlanner from './pages/Ayurveda/DinacharyaPlanner';
import DietGuide from './pages/Ayurveda/DietGuide';

import ClassicalRemedies from './pages/Remedies/ClassicalRemedies';

import HerbList from './pages/Herbs/HerbList';
import HerbDetail from './pages/Herbs/HerbDetail';

import YogaList from './pages/Yoga/YogaList';
import YogaDetail from './pages/Yoga/YogaDetail';

import MeditationHub from './pages/Meditation/MeditationHub';

import ArticleList from './pages/Articles/ArticleList';
import ArticleDetail from './pages/Articles/ArticleDetail';

import FaqPage from './pages/Help/FaqPage';

import SearchPage from './pages/Search/SearchPage';
import Login from './pages/Login';
import Register from './pages/Register';

import ProtectedRoute from './routes/ProtectedRoute';
import FavoritesDashboard from './pages/Dashboard/FavoritesDashboard';
import UserProfile from './pages/Dashboard/UserProfile';
import MyAppointments from './pages/Dashboard/MyAppointments';
import PlantScanner from './pages/Scanner/PlantScanner';

import DoctorList from './pages/Doctors/DoctorList';
import DoctorDetail from './pages/Doctors/DoctorDetail';
import DoctorOnboarding from './pages/Doctors/DoctorOnboarding';
import ConsultationRoom from './pages/Consultation/ConsultationRoom';

import DoctorDashboard from './pages/Dashboard/DoctorDashboard';
import AdminDashboard from './pages/Dashboard/AdminDashboard';

// 404 Fallback
const PlaceholderPage = ({ title, description }) => (
  <div className="max-w-4xl mx-auto px-4 py-20 text-center space-y-4">
    <div className="w-16 h-16 rounded-full bg-emerald-100 text-emerald-700 mx-auto flex items-center justify-center font-serif text-2xl font-bold">
      🌿
    </div>
    <h1 className="font-serif text-3xl font-bold text-earth-900">{title}</h1>
    <p className="text-gray-600 max-w-md mx-auto text-sm">{description}</p>
  </div>
);

export default function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <div className="flex flex-col min-h-screen">
          <Navbar />
          <main className="flex-grow">
            <Routes>
              {/* Core Home */}
              <Route path="/" element={<Home />} />

              {/* Authentication */}
              <Route path="/login" element={<Login />} />
              <Route path="/register" element={<Register />} />

              {/* Universal Search */}
              <Route path="/search" element={<SearchPage />} />

              {/* Ayurveda Knowledge Center */}
              <Route path="/ayurveda" element={<AyurvedaHub />} />
              <Route path="/ayurveda/topics/:slug" element={<AyurvedaTopicDetail />} />
              <Route path="/ayurveda/quiz" element={<DoshaQuiz />} />
              <Route path="/ayurveda/dinacharya" element={<DinacharyaPlanner />} />
              <Route path="/ayurveda/diet-guide" element={<DietGuide />} />

              {/* Classical Formulations & Remedies */}
              <Route path="/remedies" element={<ClassicalRemedies />} />

              {/* Herb & Botanical Encyclopedia */}
              <Route path="/herbs" element={<HerbList />} />
              <Route path="/herbs/:slug" element={<HerbDetail />} />

              {/* Yoga Sanctuary */}
              <Route path="/yoga" element={<YogaList />} />
              <Route path="/yoga/:slug" element={<YogaDetail />} />

              {/* Meditation & Pranayama */}
              <Route path="/meditation" element={<MeditationHub />} />

              {/* Wellness & Research Articles */}
              <Route path="/articles" element={<ArticleList />} />
              <Route path="/articles/:slug" element={<ArticleDetail />} />

              {/* Patient Support & FAQs */}
              <Route path="/faq" element={<FaqPage />} />

              {/* Plant Scanner */}
              <Route path="/scanner" element={<PlantScanner />} />

              {/* Doctor Directory & Profiles */}
              <Route path="/doctors" element={<DoctorList />} />
              <Route path="/doctors/:id" element={<DoctorDetail />} />
              <Route
                path="/doctors/onboarding"
                element={
                  <ProtectedRoute>
                    <DoctorOnboarding />
                  </ProtectedRoute>
                }
              />

              {/* Protected User Dashboard Routes */}
              <Route
                path="/favorites"
                element={
                  <ProtectedRoute>
                    <FavoritesDashboard />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/profile"
                element={
                  <ProtectedRoute>
                    <UserProfile />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/appointments"
                element={
                  <ProtectedRoute>
                    <MyAppointments />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/consultation/:appointmentId"
                element={
                  <ProtectedRoute>
                    <ConsultationRoom />
                  </ProtectedRoute>
                }
              />

              {/* Doctor Consultation Console */}
              <Route
                path="/doctor/dashboard"
                element={
                  <ProtectedRoute requiredRole="ROLE_DOCTOR">
                    <DoctorDashboard />
                  </ProtectedRoute>
                }
              />

              {/* Admin Governance Portal */}
              <Route
                path="/admin/dashboard"
                element={
                  <ProtectedRoute requiredRole="ROLE_ADMIN">
                    <AdminDashboard />
                  </ProtectedRoute>
                }
              />

              <Route path="*" element={<PlaceholderPage title="404 - Page Not Found" description="The page you are looking for does not exist." />} />
            </Routes>
          </main>
          <Footer />
        </div>
      </BrowserRouter>
    </AuthProvider>
  );
}
