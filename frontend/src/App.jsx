import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Navbar from './components/common/Navbar';
import Footer from './components/common/Footer';
import Home from './pages/Home';

// Placeholder Pages for Phase 1 routing
const PlaceholderPage = ({ title, description }) => (
  <div className="max-w-4xl mx-auto px-4 py-20 text-center space-y-4">
    <div className="w-16 h-16 rounded-full bg-ayur-100 text-ayur-700 mx-auto flex items-center justify-center font-serif text-2xl font-bold">
      🌿
    </div>
    <h1 className="font-serif text-3xl font-bold text-ayur-900">{title}</h1>
    <p className="text-gray-600 max-w-md mx-auto text-sm">{description}</p>
  </div>
);

export default function App() {
  return (
    <BrowserRouter>
      <div className="flex flex-col min-h-screen">
        <Navbar />
        <main className="flex-grow">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/ayurveda" element={<PlaceholderPage title="Ayurveda Knowledge Hub" description="Explore Tridosha, Dinacharya, Ritucharya, and ancient principles in upcoming phases." />} />
            <Route path="/herbs" element={<PlaceholderPage title="Herb Encyclopedia" description="Explore botanical monographs, safety profiles, and scientific citations in Phase 3." />} />
            <Route path="/yoga" element={<PlaceholderPage title="Yoga & Asanas" description="Explore yoga postures, difficulty levels, and step-by-step instructions in Phase 3." />} />
            <Route path="/meditation" element={<PlaceholderPage title="Meditation & Pranayama" description="Interactive mindfulness timer and breathwork routines in Phase 3." />} />
            <Route path="/scanner" element={<PlaceholderPage title="AI Plant Scanner" description="Camera capture and botanical computer vision identification in Phase 8." />} />
            <Route path="/doctors" element={<PlaceholderPage title="Verified Ayurvedic Doctors" description="Find and consult certified Ayurvedic practitioners in Phase 10." />} />
            <Route path="/articles" element={<PlaceholderPage title="Wellness Articles" description="Evidence-backed guides and articles in Phase 3." />} />
            <Route path="/login" element={<PlaceholderPage title="Sign In" description="Secure authentication and JWT user accounts in Phase 6." />} />
            <Route path="/favorites" element={<PlaceholderPage title="Saved Favorites" description="Save herbs, yoga poses, and doctors in Phase 7." />} />
            <Route path="*" element={<PlaceholderPage title="404 - Page Not Found" description="The page you are looking for does not exist." />} />
          </Routes>
        </main>
        <Footer />
      </div>
    </BrowserRouter>
  );
}
