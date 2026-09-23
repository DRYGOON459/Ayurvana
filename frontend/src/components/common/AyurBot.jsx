import React, { useState, useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import { 
  Bot, MessageSquare, X, Send, Sparkles, Compass, Leaf, Utensils, 
  UserCheck, RotateCcw, Volume2, VolumeX, Maximize2, Minimize2, 
  ShoppingBag, ChevronRight, BookOpen, Clock, Heart, Award, ArrowUpRight
} from 'lucide-react';
import { aiChatService } from '../../services/aiChatService';
import { useCart } from '../../context/CartContext';
import { useAuth } from '../../context/AuthContext';

export default function AyurBot() {
  const [isOpen, setIsOpen] = useState(false);
  const [isExpanded, setIsExpanded] = useState(false);
  const [inputMessage, setInputMessage] = useState('');
  const [messages, setMessages] = useState([]);
  const [loading, setLoading] = useState(false);
  const [suggestions, setSuggestions] = useState(null);
  const [activeCategory, setActiveCategory] = useState(0);
  const [isMuted, setIsMuted] = useState(true);

  const { addToCart } = useCart();
  const { user } = useAuth();
  const navigate = useNavigate();
  const messagesEndRef = useRef(null);
  const inputRef = useRef(null);

  // Initial welcome message
  useEffect(() => {
    const initialGreeting = {
      id: 'msg-welcome',
      role: 'assistant',
      reply: `Namaste${user?.fullName ? ' ' + user.fullName : ''}! 🙏 I am your **AyurVana Vaidya AI Assistant**, grounded in the classical treatises of the *Charaka Samhita*, *Sushruta Samhita*, and *Ashtanga Hridaya*.\n\nHow may I support your health, Dosha balance, or herbal wellness today?`,
      sanskritShloka: 'सर्वे भवन्तु सुखिनः सर्वे सन्तु निरामयाः।',
      shlokaMeaning: 'May all beings be healthy, happy, and free from disease and suffering.',
      classicalTreatiseReference: 'Brihadaranyaka Upanishad',
      quickReplies: ['What is my Dosha?', 'Remedy for insomnia & sleep', 'How to soothe acid reflux?', 'Explore Apothecary Store', 'Consult a Doctor']
    };
    setMessages([initialGreeting]);

    // Fetch dynamic categories
    aiChatService.getSuggestions()
      .then(res => {
        if (res?.data?.data) {
          setSuggestions(res.data.data);
        }
      })
      .catch(err => console.error("Error fetching suggestions", err));
  }, [user]);

  // Scroll to bottom when messages update
  useEffect(() => {
    if (isOpen) {
      messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
    }
  }, [messages, isOpen]);

  // Focus input on open
  useEffect(() => {
    if (isOpen) {
      setTimeout(() => inputRef.current?.focus(), 200);
    }
  }, [isOpen]);

  const speakText = (text) => {
    if (!('speechSynthesis' in window) || isMuted) return;
    window.speechSynthesis.cancel();
    // Clean markdown symbols for cleaner TTS
    const clean = text.replace(/[*#_`]/g, '').replace(/\[(.*?)\]\(.*?\)/g, '$1');
    const utterance = new SpeechSynthesisUtterance(clean);
    utterance.rate = 0.95;
    utterance.pitch = 1.0;
    window.speechSynthesis.speak(utterance);
  };

  const handleSendMessage = async (userText) => {
    const textToSend = (userText || inputMessage).trim();
    if (!textToSend || loading) return;

    const userMsg = {
      id: 'usr-' + Date.now(),
      role: 'user',
      content: textToSend
    };

    setMessages(prev => [...prev, userMsg]);
    setInputMessage('');
    setLoading(true);

    try {
      // Build history for context
      const history = messages.slice(-4).map(m => ({
        role: m.role,
        content: m.role === 'user' ? m.content : m.reply
      }));

      const res = await aiChatService.sendMessage(
        textToSend,
        user?.doshaType || null,
        history
      );

      if (res?.data?.data) {
        const botMsg = {
          id: 'bot-' + Date.now(),
          role: 'assistant',
          ...res.data.data
        };
        setMessages(prev => [...prev, botMsg]);
        speakText(res.data.data.reply);
      }
    } catch (err) {
      console.error("AI Chat message failed", err);
      const errorMsg = {
        id: 'err-' + Date.now(),
        role: 'assistant',
        reply: "I apologize, my herbal knowledge connection experienced a temporary hiccup. Please ask again or select one of the classical health topics below.",
        quickReplies: ['What is my Dosha?', 'Remedy for acid reflux', 'Herbs for stress', 'Book a Doctor']
      };
      setMessages(prev => [...prev, errorMsg]);
    } finally {
      setLoading(false);
    }
  };

  const clearChat = () => {
    window.speechSynthesis?.cancel();
    setMessages([{
      id: 'msg-welcome-new',
      role: 'assistant',
      reply: "Namaste! Chat history cleared. How may I assist your holistic health journey?",
      quickReplies: ['What is my Dosha?', 'Remedy for insomnia', 'Remedy for acidity', 'Explore Store']
    }]);
  };

  const handleAddToCart = (product) => {
    addToCart({
      id: product.id,
      name: product.name,
      sanskritName: product.sanskritName,
      slug: product.slug,
      price: product.discountPrice || product.price,
      imageUrl: product.imageUrl,
      category: product.category,
      netWeight: '100g'
    }, 1);
  };

  // Helper for rendering simple markdown
  const renderMarkdown = (text) => {
    if (!text) return null;
    const lines = text.split('\n');
    return lines.map((line, idx) => {
      if (line.startsWith('### ')) {
        return <h4 key={idx} className="font-serif font-bold text-sm text-emerald-950 mt-2 mb-1">{line.replace('### ', '')}</h4>;
      }
      if (line.startsWith('**') && line.endsWith('**')) {
        return <p key={idx} className="font-semibold text-xs text-stone-900 my-1">{line.replace(/\*\*/g, '')}</p>;
      }
      if (line.startsWith('- ') || line.startsWith('* ')) {
        return (
          <li key={idx} className="text-xs text-stone-700 ml-4 list-disc my-0.5">
            <span dangerouslySetInnerHTML={{ __html: formatBold(line.substring(2)) }} />
          </li>
        );
      }
      if (line.match(/^\d+\.\s/)) {
        return (
          <li key={idx} className="text-xs text-stone-700 ml-4 list-decimal my-0.5">
            <span dangerouslySetInnerHTML={{ __html: formatBold(line.replace(/^\d+\.\s/, '')) }} />
          </li>
        );
      }
      if (!line.trim()) {
        return <div key={idx} className="h-1" />;
      }
      return <p key={idx} className="text-xs text-stone-800 leading-relaxed my-1" dangerouslySetInnerHTML={{ __html: formatBold(line) }} />;
    });
  };

  const formatBold = (str) => {
    return str.replace(/\*\*(.*?)\*\*/g, '<strong class="font-semibold text-stone-900">$1</strong>');
  };

  return (
    <>
      {/* Floating Trigger Button */}
      {!isOpen && (
        <button
          onClick={() => setIsOpen(true)}
          className="fixed bottom-6 right-6 z-50 flex items-center gap-3 bg-gradient-to-r from-emerald-800 via-emerald-700 to-teal-800 text-white px-4 py-3 rounded-full shadow-2xl hover:shadow-emerald-900/40 hover:scale-105 transition-all duration-300 group border border-emerald-500/30"
          aria-label="Open Vaidya AI Assistant"
        >
          <div className="relative">
            <div className="w-10 h-10 rounded-full bg-white/10 flex items-center justify-center backdrop-blur-md border border-white/20">
              <Bot className="w-5 h-5 text-emerald-200 group-hover:rotate-12 transition-transform" />
            </div>
            <span className="absolute -top-1 -right-1 flex h-3.5 w-3.5">
              <span className="animate-ping absolute inline-flex h-full w-full rounded-full bg-amber-400 opacity-75"></span>
              <span className="relative inline-flex rounded-full h-3.5 w-3.5 bg-amber-500 border-2 border-emerald-900"></span>
            </span>
          </div>

          <div className="text-left pr-1 hidden sm:block">
            <div className="text-xs font-serif font-bold tracking-wide text-white flex items-center gap-1.5">
              <span>Vaidya AI</span>
              <span className="text-[10px] font-sans px-1.5 py-0.2 rounded bg-amber-400/20 text-amber-300 border border-amber-400/30">Ayurveda</span>
            </div>
            <div className="text-[11px] text-emerald-200/90">Instant Holistic Health Advice</div>
          </div>
        </button>
      )}

      {/* Floating Chat Modal */}
      {isOpen && (
        <div
          className={`fixed z-50 transition-all duration-300 flex flex-col bg-white shadow-2xl border border-stone-200/90 overflow-hidden ${
            isExpanded
              ? 'inset-4 sm:inset-10 rounded-3xl'
              : 'bottom-4 right-4 sm:bottom-6 sm:right-6 w-[calc(100vw-2rem)] sm:w-[460px] h-[640px] max-h-[88vh] rounded-3xl'
          }`}
        >
          {/* Header */}
          <div className="bg-gradient-to-r from-emerald-900 via-emerald-800 to-teal-900 text-white p-4 flex items-center justify-between shadow-md select-none border-b border-emerald-700/50">
            <div className="flex items-center gap-3">
              <div className="w-10 h-10 rounded-2xl bg-white/10 backdrop-blur-md flex items-center justify-center border border-white/20 shadow-inner">
                <Bot className="w-5 h-5 text-amber-300" />
              </div>
              <div>
                <div className="flex items-center gap-2">
                  <h3 className="font-serif font-bold text-sm tracking-wide text-white">Vaidya AI</h3>
                  <span className="px-2 py-0.5 rounded-full bg-emerald-700/60 text-emerald-200 text-[10px] font-medium border border-emerald-600/40">
                    Online • Ancient Wisdom
                  </span>
                </div>
                <p className="text-[11px] text-emerald-200/80">Clinical Ayurvedic & Herbal Companion</p>
              </div>
            </div>

            <div className="flex items-center gap-1 text-emerald-200">
              <button
                onClick={() => setIsMuted(!isMuted)}
                title={isMuted ? "Unmute Voice Reading" : "Mute Voice Reading"}
                className="p-1.5 rounded-lg hover:bg-white/10 hover:text-white transition-colors"
              >
                {isMuted ? <VolumeX className="w-4 h-4" /> : <Volume2 className="w-4 h-4 text-amber-300" />}
              </button>

              <button
                onClick={clearChat}
                title="Clear Conversation"
                className="p-1.5 rounded-lg hover:bg-white/10 hover:text-white transition-colors"
              >
                <RotateCcw className="w-4 h-4" />
              </button>

              <button
                onClick={() => setIsExpanded(!isExpanded)}
                title={isExpanded ? "Collapse" : "Expand"}
                className="p-1.5 rounded-lg hover:bg-white/10 hover:text-white transition-colors hidden sm:block"
              >
                {isExpanded ? <Minimize2 className="w-4 h-4" /> : <Maximize2 className="w-4 h-4" />}
              </button>

              <button
                onClick={() => setIsOpen(false)}
                title="Close"
                className="p-1.5 rounded-lg hover:bg-white/10 hover:text-white transition-colors"
              >
                <X className="w-5 h-5" />
              </button>
            </div>
          </div>

          {/* Quick Category Suggestions Banner */}
          {suggestions?.categories && (
            <div className="bg-stone-100/90 border-b border-stone-200/80 px-3 py-2 flex items-center gap-1.5 overflow-x-auto no-scrollbar">
              {suggestions.categories.map((cat, idx) => (
                <button
                  key={idx}
                  onClick={() => {
                    setActiveCategory(idx);
                    handleSendMessage(cat.prompts[0]);
                  }}
                  className={`text-[11px] font-medium whitespace-nowrap px-2.5 py-1 rounded-xl transition-all flex items-center gap-1.5 ${
                    activeCategory === idx
                      ? 'bg-emerald-800 text-white shadow-sm'
                      : 'bg-white text-stone-700 hover:bg-stone-200/80 border border-stone-200'
                  }`}
                >
                  <Sparkles className="w-3 h-3 text-amber-400" />
                  <span>{cat.category}</span>
                </button>
              ))}
            </div>
          )}

          {/* Messages Scroll Area */}
          <div className="flex-1 overflow-y-auto p-4 space-y-4 bg-gradient-to-b from-stone-50/50 via-white to-stone-50/30">
            {messages.map((msg) => (
              <div key={msg.id} className={`flex flex-col ${msg.role === 'user' ? 'items-end' : 'items-start'}`}>
                {/* Message Bubble */}
                <div
                  className={`max-w-[88%] rounded-3xl p-4 shadow-sm text-xs leading-relaxed ${
                    msg.role === 'user'
                      ? 'bg-gradient-to-br from-emerald-800 to-teal-800 text-white rounded-tr-sm'
                      : 'bg-white border border-stone-200/90 text-stone-800 rounded-tl-sm'
                  }`}
                >
                  {msg.role === 'user' ? (
                    <p className="whitespace-pre-wrap">{msg.content}</p>
                  ) : (
                    <div className="space-y-3">
                      {renderMarkdown(msg.reply)}

                      {/* Classical Sanskrit Shloka Box */}
                      {msg.sanskritShloka && (
                        <div className="bg-amber-50/70 border-l-2 border-amber-500 rounded-xl p-3 space-y-1 mt-2">
                          <div className="font-serif font-bold text-[12px] text-amber-950 tracking-wide">
                            {msg.sanskritShloka}
                          </div>
                          {msg.shlokaMeaning && (
                            <p className="text-[11px] text-amber-900/80 italic">
                              "{msg.shlokaMeaning}"
                            </p>
                          )}
                          {msg.classicalTreatiseReference && (
                            <div className="text-[10px] font-mono text-amber-800/60 pt-0.5 flex items-center gap-1">
                              <BookOpen className="w-3 h-3" />
                              <span>{msg.classicalTreatiseReference}</span>
                            </div>
                          )}
                        </div>
                      )}

                      {/* Recommended Products Action Cards */}
                      {msg.recommendedProducts?.length > 0 && (
                        <div className="space-y-2 pt-2 border-t border-stone-100">
                          <div className="text-[11px] font-semibold text-emerald-900 flex items-center gap-1">
                            <ShoppingBag className="w-3.5 h-3.5 text-emerald-600" />
                            <span>Classical Pharmacy Formulations:</span>
                          </div>
                          <div className="grid grid-cols-1 sm:grid-cols-2 gap-2">
                            {msg.recommendedProducts.map((prod) => (
                              <div
                                key={prod.id}
                                className="bg-stone-50 border border-stone-200 rounded-2xl p-2.5 flex items-center gap-3 hover:border-emerald-300 transition-all group"
                              >
                                <img
                                  src={prod.imageUrl || 'https://images.unsplash.com/photo-1615485290382-441e4d049cb5?auto=format&fit=crop&w=400&q=80'}
                                  alt={prod.name}
                                  className="w-12 h-12 rounded-xl object-cover border border-stone-200"
                                />
                                <div className="flex-1 min-w-0">
                                  <h5 className="font-serif font-bold text-xs text-stone-900 truncate">
                                    {prod.name}
                                  </h5>
                                  <div className="flex items-center gap-1.5 text-[11px] mt-0.5">
                                    <span className="font-bold text-emerald-800">₹{prod.discountPrice || prod.price}</span>
                                    {prod.discountPrice && (
                                      <span className="line-through text-stone-400 text-[10px]">₹{prod.price}</span>
                                    )}
                                  </div>
                                  <button
                                    onClick={() => handleAddToCart(prod)}
                                    className="mt-1 text-[10px] font-semibold text-emerald-700 hover:text-emerald-900 flex items-center gap-0.5"
                                  >
                                    Add to Cart <ArrowUpRight className="w-2.5 h-2.5" />
                                  </button>
                                </div>
                              </div>
                            ))}
                          </div>
                        </div>
                      )}

                      {/* Recommended Recipe Cards */}
                      {msg.recommendedRecipes?.length > 0 && (
                        <div className="space-y-2 pt-2 border-t border-stone-100">
                          <div className="text-[11px] font-semibold text-emerald-900 flex items-center gap-1">
                            <Utensils className="w-3.5 h-3.5 text-emerald-600" />
                            <span>Kitchen Pharmacy Remedy Recipe:</span>
                          </div>
                          {msg.recommendedRecipes.map((recipe) => (
                            <div
                              key={recipe.id}
                              onClick={() => {
                                setIsOpen(false);
                                navigate('/diet-nutrition');
                              }}
                              className="bg-emerald-50/50 border border-emerald-200/80 rounded-2xl p-3 flex items-center justify-between cursor-pointer hover:bg-emerald-50 transition-all group"
                            >
                              <div>
                                <h5 className="font-serif font-bold text-xs text-emerald-950 group-hover:text-emerald-700">
                                  {recipe.title}
                                </h5>
                                <p className="text-[11px] text-stone-600 mt-0.5 line-clamp-1">
                                  {recipe.description}
                                </p>
                              </div>
                              <span className="text-emerald-700 font-semibold text-xs flex items-center ml-2 whitespace-nowrap">
                                View <ChevronRight className="w-3.5 h-3.5" />
                              </span>
                            </div>
                          ))}
                        </div>
                      )}

                      {/* Recommended Doctors Card */}
                      {msg.recommendedDoctors?.length > 0 && (
                        <div className="space-y-2 pt-2 border-t border-stone-100">
                          <div className="text-[11px] font-semibold text-emerald-900 flex items-center gap-1">
                            <UserCheck className="w-3.5 h-3.5 text-emerald-600" />
                            <span>Recommended Ayurvedic Vaidyas:</span>
                          </div>
                          {msg.recommendedDoctors.map((doc) => (
                            <div
                              key={doc.id}
                              onClick={() => {
                                setIsOpen(false);
                                navigate(`/doctors/${doc.id}`);
                              }}
                              className="bg-stone-50 border border-stone-200 rounded-2xl p-2.5 flex items-center justify-between cursor-pointer hover:border-emerald-300 transition-all"
                            >
                              <div>
                                <div className="font-serif font-bold text-xs text-stone-900">{doc.name}</div>
                                <div className="text-[10px] text-emerald-800">{doc.primarySpecialization} • {doc.yearsOfExperience} yrs exp</div>
                              </div>
                              <span className="text-[11px] font-bold text-emerald-800 bg-emerald-100/60 px-2 py-0.5 rounded-lg">
                                Book ₹{doc.consultationFee}
                              </span>
                            </div>
                          ))}
                        </div>
                      )}
                    </div>
                  )}
                </div>

                {/* Quick Reply Pills */}
                {msg.role === 'assistant' && msg.quickReplies?.length > 0 && (
                  <div className="flex flex-wrap gap-1.5 mt-2 max-w-[90%]">
                    {msg.quickReplies.map((qr, i) => (
                      <button
                        key={i}
                        onClick={() => handleSendMessage(qr)}
                        className="text-[11px] bg-stone-100 hover:bg-emerald-50 hover:text-emerald-800 hover:border-emerald-300 text-stone-700 px-3 py-1 rounded-full border border-stone-200 transition-all shadow-2xs"
                      >
                        {qr}
                      </button>
                    ))}
                  </div>
                )}
              </div>
            ))}

            {loading && (
              <div className="flex items-center gap-2 text-stone-500 text-xs bg-stone-100/80 px-4 py-2.5 rounded-2xl w-max animate-pulse">
                <Bot className="w-4 h-4 text-emerald-600 animate-spin" />
                <span>Vaidya AI is consulting classical texts...</span>
              </div>
            )}

            <div ref={messagesEndRef} />
          </div>

          {/* Chat Input Bar */}
          <div className="p-3 bg-white border-t border-stone-200 flex items-center gap-2">
            <input
              ref={inputRef}
              type="text"
              value={inputMessage}
              onChange={(e) => setInputMessage(e.target.value)}
              onKeyDown={(e) => {
                if (e.key === 'Enter' && !e.shiftKey) {
                  e.preventDefault();
                  handleSendMessage();
                }
              }}
              placeholder="Ask about Doshas, remedies, herbs, diet..."
              className="flex-1 bg-stone-100 border border-stone-200 rounded-2xl px-4 py-2.5 text-xs text-stone-900 focus:outline-none focus:ring-2 focus:ring-emerald-600/30 focus:border-emerald-600 transition-all"
            />

            <button
              onClick={() => handleSendMessage()}
              disabled={!inputMessage.trim() || loading}
              className="bg-emerald-800 hover:bg-emerald-900 disabled:opacity-50 text-white p-2.5 rounded-2xl transition-all shadow-sm"
              aria-label="Send message"
            >
              <Send className="w-4 h-4" />
            </button>
          </div>
        </div>
      )}
    </>
  );
}
