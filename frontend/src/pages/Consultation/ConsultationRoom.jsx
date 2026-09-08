import React, { useState, useEffect, useRef } from 'react';
import { useParams, Link } from 'react-router-dom';
import { Client } from '@stomp/stompjs';
import { Send, Video, ArrowLeft, CheckCircle2, Shield, FileText, User, Sparkles, Clock, AlertCircle } from 'lucide-react';
import chatService from '../../services/chatService';
import { useAuth } from '../../context/AuthContext';
import LoadingSpinner from '../../components/common/LoadingSpinner';
import VideoConsultationPanel from '../../components/Video/VideoConsultationPanel';

export default function ConsultationRoom() {
  const { appointmentId } = useParams();
  const { user } = useAuth();

  const [conversation, setConversation] = useState(null);
  const [messages, setMessages] = useState([]);
  const [inputText, setInputText] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [prescriptionNotes, setPrescriptionNotes] = useState('');
  const [savingPrescription, setSavingPrescription] = useState(false);
  const [activeTab, setActiveTab] = useState('chat'); // 'chat' or 'prescription'
  const [isVideoActive, setIsVideoActive] = useState(false);

  const stompClientRef = useRef(null);
  const messagesEndRef = useRef(null);

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  };

  useEffect(() => {
    scrollToBottom();
  }, [messages]);

  // Load conversation details
  useEffect(() => {
    setLoading(true);
    chatService.getConversationByAppointment(appointmentId)
      .then((res) => {
        setConversation(res.data);
        setMessages(res.data.messages || []);
        if (res.data.consultationNotes) {
          setPrescriptionNotes(res.data.consultationNotes);
        }
      })
      .catch((err) => {
        console.error("Failed to load consultation", err);
        setError("Could not load consultation room. Please check your booking.");
      })
      .finally(() => setLoading(false));
  }, [appointmentId]);

  // Connect to STOMP WebSocket
  useEffect(() => {
    if (!conversation?.id) return;

    // Detect ws/wss scheme based on window.location
    const protocol = window.location.protocol === 'https:' ? 'wss:' : 'ws:';
    const wsUrl = `${protocol}//${window.location.hostname}:8080/ws-raw`;

    const client = new Client({
      brokerURL: wsUrl,
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
      onConnect: () => {
        console.log("Connected to AyurVana STOMP WebSocket broker");
        // Subscribe to real-time conversation topic
        client.subscribe(`/topic/conversation/${conversation.id}`, (message) => {
          if (message.body) {
            try {
              const payload = JSON.parse(message.body);
              if (payload.content) {
                // Single ChatMessageResponse
                setMessages((prev) => {
                  if (prev.some((m) => m.id === payload.id)) return prev;
                  return [...prev, payload];
                });
              } else if (payload.consultationNotes) {
                // Prescription update
                setPrescriptionNotes(payload.consultationNotes);
              }
            } catch (e) {
              console.error("Error parsing WebSocket frame", e);
            }
          }
        });
      },
      onStompError: (frame) => {
        console.error("STOMP protocol error", frame.headers['message']);
      },
    });

    client.activate();
    stompClientRef.current = client;

    return () => {
      if (stompClientRef.current) {
        stompClientRef.current.deactivate();
      }
    };
  }, [conversation?.id]);

  const handleSendMessage = async (e) => {
    e.preventDefault();
    if (!inputText.trim() || !conversation?.id) return;

    const text = inputText.trim();
    setInputText('');

    try {
      const res = await chatService.sendMessage(conversation.id, text);
      const savedMsg = res.data;
      setMessages((prev) => {
        if (prev.some((m) => m.id === savedMsg.id)) return prev;
        return [...prev, savedMsg];
      });
    } catch (err) {
      console.error("Failed to send message", err);
      alert("Message sending failed. Please try again.");
    }
  };

  const handleSavePrescription = async (e) => {
    e.preventDefault();
    if (!prescriptionNotes.trim()) return;

    setSavingPrescription(true);
    try {
      const res = await chatService.savePrescription(appointmentId, prescriptionNotes);
      setPrescriptionNotes(res.data.consultationNotes);
      alert("Prescription saved and transmitted to patient!");
      // Switch back to chat to view the automatic prescription announcement
      setActiveTab('chat');
    } catch (err) {
      console.error("Failed to save prescription", err);
      alert(err.response?.data?.message || "Could not save prescription.");
    } finally {
      setSavingPrescription(false);
    }
  };

  if (loading) return <LoadingSpinner message="Entering secure consultation room..." />;
  if (error || !conversation) {
    return (
      <div className="max-w-xl mx-auto px-4 py-20 text-center space-y-4">
        <AlertCircle className="w-12 h-12 text-rose-500 mx-auto" />
        <h2 className="font-serif text-xl font-bold text-gray-800">{error || 'Room not accessible'}</h2>
        <Link to="/appointments" className="text-xs font-semibold text-blue-700 underline">
          Return to My Appointments
        </Link>
      </div>
    );
  }

  const isCurrentUserDoctor = user?.id === conversation.doctorId;
  const partnerName = isCurrentUserDoctor ? conversation.patientName : conversation.doctorName;
  const partnerRole = isCurrentUserDoctor ? 'Patient' : 'Ayurvedic Physician';

  return (
    <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-6 space-y-4 h-[calc(100vh-5rem)] flex flex-col">
      {/* Top Header Bar */}
      <div className="bg-white p-4 sm:p-5 rounded-3xl border border-gray-200 shadow-sm flex flex-col sm:flex-row sm:items-center justify-between gap-4 flex-shrink-0">
        <div className="flex items-center gap-3">
          <Link
            to="/appointments"
            className="p-2 rounded-full hover:bg-gray-100 text-gray-600 transition-colors"
            title="Back to Appointments"
          >
            <ArrowLeft className="w-5 h-5" />
          </Link>

          <div>
            <div className="flex items-center gap-2">
              <h1 className="font-serif text-lg font-bold text-gray-900">
                Consultation: {partnerName}
              </h1>
              <span className="px-2 py-0.5 rounded-full bg-emerald-100 text-emerald-800 text-[10px] font-bold flex items-center gap-1">
                <span className="w-2 h-2 rounded-full bg-emerald-500 animate-pulse" />
                Live Session
              </span>
            </div>
            <p className="text-xs text-gray-500">
              {partnerRole} • Session Slot: {conversation.startTime?.substring(0, 5)} - {conversation.endTime?.substring(0, 5)}
            </p>
          </div>
        </div>

        <div className="flex items-center gap-2 self-end sm:self-auto">
          {/* Phase 13 Video Trigger */}
          <button
            onClick={() => setIsVideoActive((prev) => !prev)}
            className={`px-4 py-2 rounded-full text-xs font-semibold flex items-center gap-1.5 transition-colors shadow-xs ${
              isVideoActive
                ? 'bg-rose-600 hover:bg-rose-700 text-white'
                : 'bg-blue-900 hover:bg-blue-800 text-white'
            }`}
          >
            <Video className="w-3.5 h-3.5" />
            <span>{isVideoActive ? 'Close Video Window' : 'Launch Video Call'}</span>
          </button>
        </div>
      </div>

      {/* Main Split Grid */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-4 flex-1 min-h-0">
        {/* If video is active, show video panel in place of or above chat */}
        {isVideoActive ? (
          <div className="lg:col-span-2 flex flex-col overflow-hidden">
            <VideoConsultationPanel
              appointmentId={appointmentId}
              partnerName={partnerName}
              isDoctor={isCurrentUserDoctor}
              onClose={() => setIsVideoActive(false)}
            />
          </div>
        ) : (
          /* Chat Pane (2 cols) */
          <div className="lg:col-span-2 bg-white rounded-3xl border border-gray-200 shadow-sm flex flex-col overflow-hidden">
          {/* Sub-Header */}
          <div className="p-3.5 border-b border-gray-100 bg-gray-50/70 flex items-center justify-between text-xs">
            <span className="text-gray-600 font-medium">
              Encrypted Real-Time STOMP WebSocket Messaging
            </span>
            <span className="text-[11px] text-gray-400">
              {messages.length} Messages Exchanged
            </span>
          </div>

          {/* Messages Feed */}
          <div className="flex-1 p-4 sm:p-6 overflow-y-auto space-y-4">
            {messages.length === 0 ? (
              <div className="h-full flex flex-col items-center justify-center text-center text-gray-400 space-y-2">
                <Sparkles className="w-8 h-8 text-blue-300" />
                <p className="text-xs">
                  This clinical consultation is active. Say hello to begin pulse diagnosis or symptom review.
                </p>
              </div>
            ) : (
              messages.map((msg) => {
                const isMe = msg.senderId === user?.id;
                const isPrescription = msg.content.includes('[OFFICIAL AYURVEDIC PRESCRIPTION');

                return (
                  <div
                    key={msg.id}
                    className={`flex flex-col ${isMe ? 'items-end' : 'items-start'}`}
                  >
                    <div className="flex items-baseline gap-2 mb-1">
                      <span className="text-[10px] font-bold text-gray-500">
                        {isMe ? 'You' : msg.senderName}
                        {msg.isDoctor && ' (Vaidya)'}
                      </span>
                      <span className="text-[9px] text-gray-400">
                        {new Date(msg.sentAt).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}
                      </span>
                    </div>

                    <div
                      className={`max-w-[80%] rounded-2xl px-4 py-2.5 text-xs sm:text-sm whitespace-pre-wrap leading-relaxed shadow-2xs ${
                        isPrescription
                          ? 'bg-earth-900 text-white font-serif border-2 border-amber-300'
                          : isMe
                          ? 'bg-blue-900 text-white'
                          : 'bg-gray-100 text-gray-900'
                      }`}
                    >
                      {msg.content}
                    </div>
                  </div>
                );
              })
            )}
            <div ref={messagesEndRef} />
          </div>

          {/* Input Footer */}
          <form onSubmit={handleSendMessage} className="p-3.5 border-t border-gray-100 bg-white flex items-center gap-2">
            <input
              type="text"
              value={inputText}
              onChange={(e) => setInputText(e.target.value)}
              placeholder={`Message ${partnerName}...`}
              className="flex-1 px-4 py-2.5 rounded-full border border-gray-200 text-xs sm:text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all"
            />
            <button
              type="submit"
              disabled={!inputText.trim()}
              className="p-2.5 rounded-full bg-blue-900 hover:bg-blue-800 text-white transition-colors disabled:opacity-40 shadow-xs"
            >
              <Send className="w-4 h-4" />
            </button>
          </form>
        </div>
        )}

        {/* Clinical Notes & Prescription Panel (1 col) */}
        <div className="bg-white rounded-3xl border border-gray-200 shadow-sm flex flex-col overflow-hidden">
          <div className="p-4 border-b border-gray-100 bg-earth-50/50 flex items-center justify-between">
            <div className="flex items-center gap-2 text-earth-950 font-serif font-bold text-sm">
              <FileText className="w-4 h-4 text-earth-800" />
              <span>Ayurvedic Prescription &amp; Dinacharya</span>
            </div>
          </div>

          <div className="flex-1 p-5 overflow-y-auto space-y-4">
            {isCurrentUserDoctor ? (
              <form onSubmit={handleSavePrescription} className="space-y-4 h-full flex flex-col">
                <p className="text-xs text-gray-500">
                  Formulate clinical herbal formulations, dosage, Anupana (carrier e.g. warm milk/ghee), and lifestyle Dinacharya advice. Saving will transmit this directly to the patient.
                </p>

                <textarea
                  rows="12"
                  value={prescriptionNotes}
                  onChange={(e) => setPrescriptionNotes(e.target.value)}
                  placeholder="e.g.&#10;1. Ashwagandha Churna: 3g with warm cow's milk at bedtime&#10;2. Tulsi Ghanvati: 1 tab twice daily before meals&#10;3. Dinacharya: Nasya with Anu Taila (2 drops in each nostril in morning)&#10;4. Diet: Warm freshly prepared soups, avoid raw cold foods"
                  className="w-full flex-1 p-3.5 rounded-2xl border border-gray-200 text-xs font-mono focus:outline-none focus:ring-2 focus:ring-earth-700 transition-all"
                />

                <button
                  type="submit"
                  disabled={savingPrescription || !prescriptionNotes.trim()}
                  className="w-full py-2.5 rounded-full bg-earth-900 hover:bg-earth-800 text-white font-semibold text-xs transition-colors shadow-xs disabled:opacity-50"
                >
                  {savingPrescription ? 'Transmitting Prescription...' : 'Issue Official Prescription'}
                </button>
              </form>
            ) : (
              <div className="space-y-4">
                {prescriptionNotes ? (
                  <div className="space-y-4">
                    <div className="p-5 rounded-2xl bg-stone-50 border border-emerald-200 shadow-sm space-y-3">
                      <div className="flex items-center justify-between border-b border-stone-200 pb-2.5">
                        <span className="text-[10px] uppercase font-bold text-emerald-900 tracking-wider flex items-center gap-1.5">
                          <CheckCircle2 className="w-4 h-4 text-emerald-600" />
                          <span>Official Certified Digital Rx</span>
                        </span>
                        <button
                          onClick={() => window.print()}
                          className="text-[11px] font-bold text-emerald-800 hover:text-emerald-900 bg-white border border-emerald-300 px-2.5 py-1 rounded-lg shadow-xs hover:bg-emerald-50 transition"
                        >
                          Print / Save PDF Slip
                        </button>
                      </div>

                      <pre className="text-xs font-sans whitespace-pre-wrap text-stone-800 leading-relaxed font-medium">
                        {prescriptionNotes}
                      </pre>

                      <div className="pt-3 border-t border-stone-200 flex items-center justify-between text-[10px] text-stone-500">
                        <span>Digital Stamp: AYUSH-CERTIFIED</span>
                        <span className="font-mono">QR: RX-VERIFIED</span>
                      </div>
                    </div>

                    <Link
                      to="/store"
                      className="w-full inline-flex items-center justify-center space-x-2 bg-emerald-800 hover:bg-emerald-700 text-white font-bold text-xs py-3 px-4 rounded-xl shadow transition"
                    >
                      <Sparkles className="w-4 h-4" />
                      <span>Order Prescribed Herbal Remedies from Store</span>
                    </Link>
                  </div>
                ) : (
                  <div className="text-center py-12 text-gray-400 space-y-2">
                    <Clock className="w-8 h-8 mx-auto text-gray-300" />
                    <p className="text-xs">
                      The attending doctor has not yet finalized a prescription for this session. It will appear here once issued.
                    </p>
                  </div>
                )}
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}
