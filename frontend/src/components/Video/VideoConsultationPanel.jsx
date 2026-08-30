import React, { useState, useEffect, useRef } from 'react';
import { Mic, MicOff, Video, VideoOff, PhoneOff, Maximize2, Minimize2, Shield, Sparkles, HelpCircle, Activity } from 'lucide-react';
import videoService from '../../services/videoService';

export default function VideoConsultationPanel({ appointmentId, partnerName, isDoctor, onClose }) {
  const [tokenData, setTokenData] = useState(null);
  const [isMicOn, setIsMicOn] = useState(true);
  const [isVideoOn, setIsVideoOn] = useState(true);
  const [isFullscreen, setIsFullscreen] = useState(false);
  const [showExamGuide, setShowExamGuide] = useState(false);
  const [mediaError, setMediaError] = useState('');
  const [callDuration, setCallDuration] = useState(0);

  const localVideoRef = useRef(null);
  const localStreamRef = useRef(null);
  const containerRef = useRef(null);

  // Initialize room token
  useEffect(() => {
    videoService.getRoomToken(appointmentId)
      .then((res) => setTokenData(res.data))
      .catch((err) => console.error("Error fetching video token", err));
  }, [appointmentId]);

  // Start local camera stream
  useEffect(() => {
    let stream = null;

    async function startCamera() {
      try {
        if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
          stream = await navigator.mediaDevices.getUserMedia({
            video: { width: { ideal: 640 }, height: { ideal: 480 } },
            audio: true,
          });
          localStreamRef.current = stream;
          if (localVideoRef.current) {
            localVideoRef.current.srcObject = stream;
          }
        }
      } catch (err) {
        console.warn("Camera/Mic access not granted or unavailable", err);
        setMediaError("Camera/Microphone access was not permitted. Video simulation active.");
      }
    }

    startCamera();

    // Call duration timer
    const interval = setInterval(() => {
      setCallDuration((prev) => prev + 1);
    }, 1000);

    return () => {
      clearInterval(interval);
      if (localStreamRef.current) {
        localStreamRef.current.getTracks().forEach((t) => t.stop());
      }
    };
  }, []);

  const toggleMic = () => {
    if (localStreamRef.current) {
      localStreamRef.current.getAudioTracks().forEach((t) => {
        t.enabled = !t.enabled;
      });
      setIsMicOn((prev) => !prev);
    } else {
      setIsMicOn((prev) => !prev);
    }
  };

  const toggleVideo = () => {
    if (localStreamRef.current) {
      localStreamRef.current.getVideoTracks().forEach((t) => {
        t.enabled = !t.enabled;
      });
      setIsVideoOn((prev) => !prev);
    } else {
      setIsVideoOn((prev) => !prev);
    }
  };

  const toggleFullscreen = () => {
    if (!containerRef.current) return;
    if (!document.fullscreenElement) {
      containerRef.current.requestFullscreen?.().catch(() => {});
      setIsFullscreen(true);
    } else {
      document.exitFullscreen?.().catch(() => {});
      setIsFullscreen(false);
    }
  };

  const handleEndCall = async () => {
    if (localStreamRef.current) {
      localStreamRef.current.getTracks().forEach((t) => t.stop());
    }
    try {
      await videoService.endVideoCall(appointmentId);
    } catch (e) {
      console.error("Error concluding video call", e);
    }
    onClose();
  };

  const formatTime = (secs) => {
    const m = Math.floor(secs / 60);
    const s = secs % 60;
    return `${m.toString().padStart(2, '0')}:${s.toString().padStart(2, '0')}`;
  };

  return (
    <div
      ref={containerRef}
      className={`relative bg-gray-950 rounded-3xl overflow-hidden border border-gray-800 shadow-2xl flex flex-col ${
        isFullscreen ? 'fixed inset-0 z-50 rounded-none' : 'w-full h-full min-h-[420px]'
      }`}
    >
      {/* Top Status Overlay */}
      <div className="absolute top-4 left-4 right-4 z-20 flex items-center justify-between pointer-events-none">
        <div className="flex items-center gap-2 pointer-events-auto">
          <span className="px-3 py-1 rounded-full bg-black/60 backdrop-blur-xs text-white text-xs font-semibold flex items-center gap-1.5 border border-white/10">
            <span className="w-2 h-2 rounded-full bg-rose-500 animate-ping" />
            <span className="font-mono">{formatTime(callDuration)}</span>
          </span>

          <span className="px-3 py-1 rounded-full bg-blue-900/80 backdrop-blur-xs text-blue-200 text-xs font-bold border border-blue-400/20">
            {partnerName} ({isDoctor ? 'Patient' : 'Attending Vaidya'})
          </span>
        </div>

        <button
          onClick={() => setShowExamGuide((prev) => !prev)}
          className="pointer-events-auto px-3 py-1 rounded-full bg-amber-500/20 hover:bg-amber-500/30 text-amber-300 text-xs font-semibold flex items-center gap-1.5 border border-amber-400/30 backdrop-blur-xs transition-colors"
        >
          <HelpCircle className="w-3.5 h-3.5" />
          <span>Ayurvedic Pariksha Guide</span>
        </button>
      </div>

      {/* Ayurvedic Examination Guide Overlay Drawer */}
      {showExamGuide && (
        <div className="absolute top-16 right-4 z-30 w-80 p-4 rounded-2xl bg-gray-900/95 border border-amber-400/30 text-white text-xs space-y-2.5 backdrop-blur-md animate-in fade-in slide-in-from-top-2 duration-200 shadow-xl">
          <div className="flex items-center justify-between border-b border-gray-800 pb-2">
            <strong className="font-serif text-amber-400 flex items-center gap-1">
              <Sparkles className="w-3.5 h-3.5" />
              <span>Ashtavidha Pariksha (8-Fold Clinical Exam)</span>
            </strong>
          </div>
          <p className="text-gray-300 leading-relaxed text-[11px]">
            Please position your camera under clear, natural lighting for the following visual diagnostics:
          </p>
          <ul className="space-y-1.5 text-[11px] text-gray-300 list-disc list-inside">
            <li><strong className="text-white">Jihva Pariksha:</strong> Extend tongue forward to evaluate color and white/yellow <em>Ama</em> coating.</li>
            <li><strong className="text-white">Netra Pariksha:</strong> Look upward to inspect sclera for redness (<em>Pitta</em>) or dryness.</li>
            <li><strong className="text-white">Akriti &amp; Varna:</strong> Observe skin hydration, facial symmetry, and vitality.</li>
          </ul>
        </div>
      )}

      {/* Main Video Presentation Grid */}
      <div className="relative flex-1 bg-gradient-to-br from-gray-950 via-slate-900 to-gray-950 flex items-center justify-center overflow-hidden">
        {/* Remote Partner Video Canvas (Mock Clinical Telemedicine Feed) */}
        <div className="w-full h-full flex flex-col items-center justify-center p-6 text-center space-y-3">
          <div className="w-24 h-24 sm:w-32 sm:h-32 rounded-full bg-blue-950/80 border-2 border-blue-400/40 text-blue-300 flex items-center justify-center font-serif text-4xl font-bold shadow-inner">
            {partnerName?.charAt(0) || 'V'}
          </div>
          <div className="space-y-1">
            <h3 className="font-serif text-lg font-bold text-white">{partnerName}</h3>
            <p className="text-xs text-blue-300 flex items-center justify-center gap-1.5">
              <Activity className="w-3.5 h-3.5 animate-pulse text-emerald-400" />
              <span>Encrypted Telemedicine WebRTC Stream Active</span>
            </p>
          </div>
        </div>

        {/* Local Camera Picture-in-Picture */}
        <div className="absolute bottom-20 right-4 w-36 sm:w-48 h-28 sm:h-36 rounded-2xl bg-gray-900 border-2 border-white/20 overflow-hidden shadow-2xl">
          {isVideoOn ? (
            <video
              ref={localVideoRef}
              autoPlay
              playsInline
              muted
              className="w-full h-full object-cover mirror"
            />
          ) : (
            <div className="w-full h-full flex flex-col items-center justify-center text-gray-400 text-xs">
              <VideoOff className="w-6 h-6 mb-1 text-gray-500" />
              <span>Camera Off</span>
            </div>
          )}
          <span className="absolute bottom-1.5 left-2 px-1.5 py-0.5 rounded bg-black/60 text-[9px] font-bold text-white">
            You {!isMicOn && '(Muted)'}
          </span>
        </div>
      </div>

      {/* In-Call Controls Bar */}
      <div className="p-4 bg-gray-900/90 border-t border-gray-800 flex items-center justify-center gap-4 z-20">
        <button
          onClick={toggleMic}
          className={`p-3 rounded-full transition-all ${
            isMicOn
              ? 'bg-gray-800 hover:bg-gray-700 text-white'
              : 'bg-rose-600 hover:bg-rose-700 text-white shadow-lg'
          }`}
          title={isMicOn ? 'Mute Microphone' : 'Unmute Microphone'}
        >
          {isMicOn ? <Mic className="w-5 h-5" /> : <MicOff className="w-5 h-5" />}
        </button>

        <button
          onClick={toggleVideo}
          className={`p-3 rounded-full transition-all ${
            isVideoOn
              ? 'bg-gray-800 hover:bg-gray-700 text-white'
              : 'bg-rose-600 hover:bg-rose-700 text-white shadow-lg'
          }`}
          title={isVideoOn ? 'Turn Camera Off' : 'Turn Camera On'}
        >
          {isVideoOn ? <Video className="w-5 h-5" /> : <VideoOff className="w-5 h-5" />}
        </button>

        <button
          onClick={toggleFullscreen}
          className="p-3 rounded-full bg-gray-800 hover:bg-gray-700 text-white transition-colors"
          title={isFullscreen ? 'Exit Fullscreen' : 'Fullscreen'}
        >
          {isFullscreen ? <Minimize2 className="w-5 h-5" /> : <Maximize2 className="w-5 h-5" />}
        </button>

        <button
          onClick={handleEndCall}
          className="px-6 py-3 rounded-full bg-rose-600 hover:bg-rose-700 text-white font-bold text-xs transition-colors flex items-center gap-2 shadow-lg"
          title="End Consultation"
        >
          <PhoneOff className="w-4 h-4" />
          <span>End Call</span>
        </button>
      </div>
    </div>
  );
}
