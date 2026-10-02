package com.ayurvana.service.ai;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.*;

@Component
public class AyurvedicDynamicSynthesizer {

    @Data
    @Builder
    public static class SynthesizedResponse {
        private String reply;
        private String sanskritShloka;
        private String shlokaMeaning;
        private String classicalTreatiseReference;
        private List<String> quickReplies;
        private List<String> herbKeywords;
        private List<String> productKeywords;
        private List<String> recipeKeywords;
    }

    public SynthesizedResponse synthesize(String query, String userDosha) {
        String q = query != null ? query.toLowerCase().trim() : "";

        // 1. Emergency Safety Guardrail
        if (containsAny(q, "chest pain", "heart attack", "difficulty breathing", "severe bleeding", "unconscious", "stroke", "poisoning", "suicide", "emergency")) {
            return SynthesizedResponse.builder()
                    .reply("⚠️ **Urgent Medical Advisory**: The symptoms you described require immediate emergency medical care. Ayurvedic supportive care is not a substitute for acute emergency medicine. Please call your local emergency services (112 / 911 / 108) or proceed immediately to the nearest hospital trauma center.")
                    .sanskritShloka("असाध्ये तु भिषक् प्राज्ञः प्रत्याख्यायाचरेत् क्रियाम्।")
                    .shlokaMeaning("In critical emergencies, an astute physician must first secure vital life preservation before constitutional therapeutics.")
                    .classicalTreatiseReference("Charaka Samhita, Sutra Sthana 10/6")
                    .quickReplies(List.of("Find Nearest Hospital", "Consult Senior Vaidya", "Home Care Guidelines"))
                    .build();
        }

        // 2. Dinacharya / Biological Clock Query
        if (containsAny(q, "dinacharya", "clock", "what should i do right now", "current time", "circadian", "daily routine", "brahma muhurta", "schedule")) {
            return generateDinacharyaNowResponse();
        }

        // 3. Hair Health / Hair Fall / Premature Graying (Khalitya / Palitya)
        if (containsAny(q, "hair", "hairfall", "hair fall", "bald", "alopecia", "dandruff", "gray hair", "grey hair", "khalitya", "palitya", "scalp", "bhringraj")) {
            return SynthesizedResponse.builder()
                    .reply("### 🌿 Ayurvedic Trichology: Reversing Hair Fall & Premature Graying (Khalitya & Palitya)\n\n" +
                            "In classical Ayurveda, hair (*Keshas*) is the metabolic byproduct (**Upadhātu**) of the **Asthi Dhātu** (bone tissue). Excessive internal heat (*Pitta-Rakta Dushti*) and dry Vata burn hair follicles and lead to premature thinning.\n\n" +
                            "#### 🍃 Clinical Hair Restoration Regimen:\n" +
                            "1. **Bhringraj & Neelibhringadi Shiroabhyanga:** Warm authentic *Bhringraj Tailam* and massage your scalp in circular motions 2–3 times a week to cool the cranial nerves and strengthen hair roots.\n" +
                            "2. **Amla & Triphala Internal Tonification:** Take 1 tsp of organic Amla powder with warm water every morning; Amla is the premier classical *Keshya* Rasayana for melanin preservation.\n" +
                            "3. **Nasya Therapy (Nasal Drops):** Administer 2 drops of lukewarm *Anu Tailam* into each nostril every morning (*Nāsā hi shiraso dvāram* — the nose is the gateway to cranial health).\n" +
                            "4. **Dietary Guidance:** Avoid excessively salty, spicy, and fermented foods. Include soaked almonds, black sesame seeds (*Tila*), and fresh curry leaves in your diet.")
                    .sanskritShloka("भृङ्गराजः कटुस्तिक्तः कफवातहरो गुरुः। केश्यस्त्वच्यः कृमिघ्नश्च रसायनवरः स्मृतः॥")
                    .shlokaMeaning("Bhringraj is the supreme herbal rasayana for hair rejuvenation, strengthening roots, preventing premature graying, and cooling the mind.")
                    .classicalTreatiseReference("Bhavaprakasha Nighantu, Guduchyadi Varga")
                    .quickReplies(List.of("Bhringraj Oil application guide", "Nasya Therapy steps", "Diet for Stronger Hair", "Book Hair Specialist Vaidya"))
                    .herbKeywords(List.of("amla", "bhringraj", "neem"))
                    .productKeywords(List.of("kumkumadi", "triphala"))
                    .recipeKeywords(List.of("kitchari", "tea"))
                    .build();
        }

        // 4. Thyroid & Metabolism (Galaganda / Agnimandya)
        if (containsAny(q, "thyroid", "hypothyroid", "hyperthyroid", "galaganda", "tsh", "goiter", "sluggish metabolism")) {
            return SynthesizedResponse.builder()
                    .reply("### ⚖️ Ayurvedic Approach to Thyroid & Metabolic Balance (Galaganda & Medo-Dhatu)\n\n" +
                            "Ayurveda views thyroid irregularities as an imbalance of **Kapha Dosha** and **Medo Dhātu** (fat tissue), accompanied by **Manda Agni** (sluggish cellular metabolism) obstructing the neck channels (**Kantha Srotas**).\n\n" +
                            "#### 🌿 Rebalancing Strategy:\n" +
                            "1. **Kanchanar Guggulu:** The premier classical polyherbal formulation for dissolving metabolic lymphatic congestions and supporting endocrine glandular harmony.\n" +
                            "2. **Ashwagandha & Guggulu Synergy:** Ashwagandha revitalizes HPA axis signaling while purified Guggulu stimulates hepatic conversion of thyroid hormones.\n" +
                            "3. **Coriander Seed Water (Dhanyaka Hima):** Boil 2 tablespoons of crushed coriander seeds in 2 cups of water down to 1 cup; sip lukewarm on an empty stomach.\n" +
                            "4. **Pranayama & Asanas:** Practice *Ujjayi Pranayama* (Ocean Breath) and *Sarvangasana* (Shoulder Stand) for direct thyroid gland stimulation.")
                    .sanskritShloka("गलगण्डे गलगण्डघ्नः काञ्चनारो महागुणः। कफमेदोहरः श्रेष्ठः शोथघ्नः कण्ठशोधनः॥")
                    .shlokaMeaning("Kanchanar is of immense virtue for resolving glandular swellings, clearing Kapha and sluggish adipose tissue, and purifying the cervical channels.")
                    .classicalTreatiseReference("Sharangadhara Samhita, Madhyama Khanda")
                    .quickReplies(List.of("Kanchanar Guggulu dosage", "Coriander Seed Tea Recipe", "Ujjayi Pranayama guide", "Consult Endocrine Vaidya"))
                    .herbKeywords(List.of("ashwagandha", "guggulu", "triphala"))
                    .productKeywords(List.of("ashwagandha", "guggulu"))
                    .recipeKeywords(List.of("tea", "kitchari"))
                    .build();
        }

        // 5. Migraine, Chronic Headaches & Brain Fog (Ardhavabhedaka / Shiroroga)
        if (containsAny(q, "headache", "migraine", "head pain", "shiroroga", "ardhavabhedaka", "temple pain", "brain fog", "dizziness")) {
            return SynthesizedResponse.builder()
                    .reply("### 🧠 Soothing Migraines, Chronic Headaches & Cranial Tension (Ardhavabhedaka)\n\n" +
                            "In Ayurveda, unilateral or pulsating headaches (*Ardhavabhedaka*) stem from aggravated **Vata-Pitta Doshas** constricting the cranial blood vessels and neurological channels (**Shiras Srotas**).\n\n" +
                            "#### 🍃 Classical Relief Protocol:\n" +
                            "1. **Pratimarsha Nasya:** Instill 2 drops of warm *Ksheerabala 101* or pure A2 Cow Ghee into each nostril on an empty stomach to lubricate the cerebral nerves.\n" +
                            "2. **Brahmi & Shankhpushpi Elixir:** These *Medhya* herbs pacify neuro-inflammation, stabilize cranial vascular tone, and sharpen mental clarity.\n" +
                            "3. **Sandalwood & Camphor Lepa:** Apply a thin paste of cooling Sandalwood (*Chandana*) powder with rose water on your forehead and temples.\n" +
                            "4. **Trigger Avoidance:** Avoid skipping meals (which spikes Pitta), irregular sleep schedules, direct harsh sunlight without protection, and excessive digital screen exposure.")
                    .sanskritShloka("नसा हि शिरसो द्वारं तेन तद्व्याप्य हन्ति तान्। सर्वशिरोरोगेषु नस्यं श्रेष्ठं विधीयते॥")
                    .shlokaMeaning("The nose is the primary gateway to the brain and head; therefore, Nasya therapy is supreme for treating all cranial and neurological disorders.")
                    .classicalTreatiseReference("Ashtanga Hridaya, Sutra Sthana 20/1")
                    .quickReplies(List.of("Nasya Therapy Steps", "Brahmi Ghee details", "Diet for Migraine Relief", "Book Neurological Vaidya"))
                    .herbKeywords(List.of("brahmi", "shankhpushpi", "ashwagandha"))
                    .productKeywords(List.of("brahmi", "triphala"))
                    .recipeKeywords(List.of("moon milk", "tea"))
                    .build();
        }

        // 6. Eye Health & Screen Fatigue (Netra Roga / Drishti Prasadana)
        if (containsAny(q, "eye", "vision", "screen fatigue", "dry eyes", "strained eyes", "netra", "sight", "spectacles", "burning eyes")) {
            return SynthesizedResponse.builder()
                    .reply("### 👁️ Classical Eye Rejuvenation & Vision Care (Chakshushya & Netra Tarpana)\n\n" +
                            "The eyes are ruled by **Alochaka Pitta** (the metabolic fire of visual perception). Modern digital blue-light exposure causes dry Vata-Pitta strain, burning sensations, and diminished visual acuity.\n\n" +
                            "#### 🌿 Daily Ophthalmic Protocol:\n" +
                            "1. **Triphala Eye Wash (Netra Prakshalana):** Soak 1/2 tsp Triphala Churna in 1 glass of boiled water overnight. Strain through 4 layers of clean muslin cloth and gently rinse eyes in the morning.\n" +
                            "2. **Ghee at Bedtime (Chakshushya Rasayana):** Taking 1 teaspoon of pure cow ghee with warm milk at night deeply lubricates the optic nerve.\n" +
                            "3. **Palming & Trataka:** Rub your palms together until warm and cup them gently over closed eyes for 60 seconds; practice gentle candle-gazing (*Trataka*) to tone ocular muscles.\n" +
                            "4. **Triphala Ghrita:** A classical medicated ghee used both internally and in clinical Netra Tarpana therapies.")
                    .sanskritShloka("त्रिफला चक्षुष्या वृष्या त्वच्या च दीपनी। कफपित्तप्रशमनी मेध्या च परमा स्मृता॥")
                    .shlokaMeaning("Triphala is the foremost rejuvenator for eyesight, lustrous skin, digestive agni, cognitive clarity, and balancing Pitta and Kapha.")
                    .classicalTreatiseReference("Bhavaprakasha Nighantu, Haritakyadi Varga")
                    .quickReplies(List.of("Triphala Eyewash preparation", "Trataka meditation steps", "Diet for Visual Health", "Consult Ayurvedic Doctor"))
                    .herbKeywords(List.of("triphala", "amla", "yashtimadhu"))
                    .productKeywords(List.of("triphala", "brahmi"))
                    .recipeKeywords(List.of("tea", "kitchari"))
                    .build();
        }

        // 7. Heart Health, Blood Pressure & Cholesterol (Hridroga / Medoroga)
        if (containsAny(q, "heart", "cholesterol", "blood pressure", "hypertension", "bp", "cardiovascular", "hridroga", "arjuna", "triglycerides")) {
            return SynthesizedResponse.builder()
                    .reply("### ❤️ Ayurvedic Cardiovascular Health & Lipid Harmony (Hridroga & Medo-Dhatu)\n\n" +
                            "Ayurveda describes the Heart (**Hridaya**) as the seat of **Ojas**, **Prāṇa**, and **Sādhaka Pitta**. Elevated cholesterol and arterial stiffness represent accumulated metabolic debris (**Āma-Medas**) blocking the vital circulation channels (*Rasa-Vāha Srotas*).\n\n" +
                            "#### 🍃 Cardiovascular Protocol:\n" +
                            "1. **Arjuna Ksheerapaka (Terminalia arjuna Decoction):** Simmer 1 tsp of pure Arjuna bark powder with 1/2 cup milk and 1/2 cup water until only the milk remains. Take once daily for cardiac muscle tonification.\n" +
                            "2. **Garlic & Ginger Agni Tonic:** Consume 1 clove of raw garlic soaked in honey or warm water in the morning to dissolve arterial plaques.\n" +
                            "3. **Guggulu Lipid Detox:** Formulations like *Medohar Guggulu* and *Hridayarnava Rasa* regulate lipid balance and support clear vascular micro-channels.\n" +
                            "4. **Stress Modulation:** Daily practice of *Bhramari Pranayama* directly activates the vagus nerve and lowers sympathetic arterial resistance.")
                    .sanskritShloka("हृद्यः कषायो मधुरः शीतो वातकफापहः। अर्जुनो दीपनो बल्यः क्षतकासास्रजित्परः॥")
                    .shlokaMeaning("Arjuna bark is astringent and cooling; it pacifies Pitta and Kapha, strengthens cardiac muscular contractility, and heals vascular integrity.")
                    .classicalTreatiseReference("Dravyaguna Vijnana, Priyavrat Sharma")
                    .quickReplies(List.of("How to make Arjuna Ksheerapaka?", "Diet for High Cholesterol", "Pranayama for BP", "Consult Cardiac Vaidya"))
                    .herbKeywords(List.of("arjuna", "guggulu", "garlic"))
                    .productKeywords(List.of("guggulu", "triphala"))
                    .recipeKeywords(List.of("tea", "kitchari"))
                    .build();
        }

        // 8. Sleep & Insomnia (Anidra)
        if (containsAny(q, "sleep", "insomnia", "nightmare", "anidra", "restless", "wake up early", "racing mind", "trouble sleeping", "melatonin")) {
            return SynthesizedResponse.builder()
                    .reply("### 🌙 Ayurvedic Management for Restful Sleep (Anidra)\n\n" +
                            "In classical Ayurveda, insomnia is primarily caused by aggravated **Vata Dosha** in the nervous system (Majjā Dhātu) and excess **Raja Guna** (mental agitation).\n\n" +
                            "#### 🌿 Recommended Daily Regimen:\n" +
                            "1. **Bedtime Moon Milk:** Drink warm milk cooked with Ashwagandha, crushed Cardamom, and a pinch of Nutmeg (*Jatiphala*) 30 minutes before bed.\n" +
                            "2. **Pādābhyanga (Foot Massage):** Massage the soles of your feet with warm Sesame or *Ksheerabala* oil for 5 minutes before sleeping.\n" +
                            "3. **Digital Curfew:** Switch off blue-light screens by 9:30 PM to preserve natural melatonin secretion and settle Prana Vayu.\n" +
                            "4. **Yoga & Pranayama:** Practice 10 minutes of gentle *Bhramari Pranayama* (Humming Bee Breath) and *Shavasana*.")
                    .sanskritShloka("रात्रौ जागरणं रूक्षं स्निग्धं प्रस्वपनं दिवा। अरूक्षममनभिष्यन्दि त्वासीनं प्रचलायितम्॥")
                    .shlokaMeaning("Staying awake late at night causes dryness (Vata aggravation), whereas adequate natural night sleep imparts deep nourishment and cellular lubrication (Sneha).")
                    .classicalTreatiseReference("Ashtanga Hridaya, Sutra Sthana 7/53")
                    .quickReplies(List.of("How to prepare Moon Milk?", "Best herbs for anxiety", "Book a Sleep Specialist Doctor", "Take Dosha Quiz"))
                    .herbKeywords(List.of("ashwagandha", "brahmi", "jatiphala"))
                    .productKeywords(List.of("sleep", "ashwagandha", "ksheerabala"))
                    .recipeKeywords(List.of("moon milk", "tea"))
                    .build();
        }

        // 9. Digestion, Acidity, Gas & Bloating (Amlapitta / Agni)
        if (containsAny(q, "acidity", "acid reflux", "heartburn", "gas", "bloat", "digestion", "constipation", "indigestion", "gut", "stomach pain", "amlapitta", "gerd", "appetite")) {
            boolean isPitta = containsAny(q, "acidity", "heartburn", "acid reflux", "burning", "sour belching", "gerd");
            if (isPitta) {
                return SynthesizedResponse.builder()
                        .reply("### 🔥 Soothing Hyperacidity & Heartburn (Amlapitta)\n\n" +
                                "Hyperacidity stems from an aggravation of **Pāchaka Pitta** where digestive juices become excessively sour, sharp, and liquid (*Amla-Tikshna*).\n\n" +
                                "#### 🍃 Immediate Relief Protocol:\n" +
                                "1. **CCF Tea:** Sip warm Cumin-Coriander-Fennel tea throughout the day to neutralize excess gastric acid without suppressing enzymatic power.\n" +
                                "2. **Medicated Ghee / Yashtimadhu:** 1 teaspoon of pure cow ghee or Licorice root powder in lukewarm water forms a soothing mucosal coating.\n" +
                                "3. **Dietary Guidance:** Avoid hot chili, deep-fried snacks, vinegar, tomatoes, and fermented foods. Favor sweet fruits, cucumbers, and basmati rice.\n" +
                                "4. **Classical Formulation:** *Avipattikar Churna* taken before meals brings swift relief.")
                        .sanskritShloka("अविपत्तिकरं चूर्णं पित्तरोगापहं परम्। अम्लपित्ते विशेषेण हितं विण्मूत्रशोधनम्॥")
                        .shlokaMeaning("Avipattikar Churna is the foremost classical formulation for pacifying excessive sour bile, burning reflux, and digestive Pitta.")
                        .classicalTreatiseReference("Bhaishajya Ratnavali, Amlapitta Adhyaya")
                        .quickReplies(List.of("View CCF Tea Recipe", "Avipattikar Churna details", "Consult Gut Specialist", "Diet for Pitta Dosha"))
                        .herbKeywords(List.of("yashtimadhu", "amla", "fennel"))
                        .productKeywords(List.of("avipattikar", "triphala"))
                        .recipeKeywords(List.of("ccf", "tea", "kitchari"))
                        .build();
            } else {
                return SynthesizedResponse.builder()
                        .reply("### 🌿 Restoring Digestive Agni & Relieving Bloating / Constipation\n\n" +
                                "Sluggish digestion and gas represent **Vishama Agni** (Irregular Fire) or **Manda Agni**, driven by cold Vata and heavy Kapha.\n\n" +
                                "#### 🍃 Rebalancing Protocol:\n" +
                                "1. **Classical Triphala Detox:** Take 1 teaspoon of Triphala Churna in warm water before bedtime for natural colon peristalsis.\n" +
                                "2. **Ginger & Rock Salt:** Chew a thin slice of fresh ginger sprinkled with pink Himalayan rock salt 10 minutes before meals to stoke digestive Agni.\n" +
                                "3. **Medicated Takra:** Enjoy freshly churned spiced buttermilk after lunch with roasted cumin and asafoetida (hing).\n" +
                                "4. **Hydration:** Sip warm boiled water throughout the day; avoid iced drinks during meals.")
                        .sanskritShloka("रोगाः सर्वेऽपि मन्देऽग्नौ सुतरामुदराणि च।")
                        .shlokaMeaning("All physical illnesses and digestive disorders originate from a weak, sluggish, or suppressed digestive fire (Agni).")
                        .classicalTreatiseReference("Ashtanga Hridaya, Nidana Sthana 12/1")
                        .quickReplies(List.of("Triphala Churna dosage", "Medicated Takra Recipe", "Dietary Incompatibilities (Viruddha)", "Book Doctor"))
                        .herbKeywords(List.of("triphala", "haritaki", "ginger"))
                        .productKeywords(List.of("triphala", "takra"))
                        .recipeKeywords(List.of("takra", "kitchari"))
                        .build();
            }
        }

        // 10. Skin & Complexion (Kushta / Varnya)
        if (containsAny(q, "skin", "acne", "glow", "blemish", "pigmentation", "eczema", "psoriasis", "complexion", "kumkumadi", "face", "varnya")) {
            return SynthesizedResponse.builder()
                    .reply("### ✨ Ayurvedic Skin Radiance & Detoxification (Varnya & Rakta Shodhana)\n\n" +
                            "In Ayurveda, the skin (*Tvachā*) is a direct reflection of your **Rasa** (nutrient plasma) and **Rakta** (blood tissue). Breakouts and dullness indicate accumulated metabolic heat (Pitta) and lymphatic toxins.\n\n" +
                            "#### 🌸 Ayurvedic Skin Protocol:\n" +
                            "1. **Saffron Kumkumadi Tailam:** Apply 3–4 drops of authentic Kumkumadi Taila at night for a natural golden luminescence and fading of dark spots.\n" +
                            "2. **Blood-Purifying Botanicals:** Herbs like *Neem*, *Manjistha*, and *Turmeric* purify the blood and cool systemic inflammation.\n" +
                            "3. **Cooling Hydration:** Drink Hibiscus & Rose Petal Sharbat in warm weather to soothe internal heat.\n" +
                            "4. **Diet:** Reduce fermented, salty, and deep-fried foods.")
                    .sanskritShloka("कुङ्कुमाद्यमिदं तैलं मुखकान्तिकरं परम्। नीलिकां पिप्लूं व्यङ्गं हन्ति वक्त्रे च यौवनम्॥")
                    .shlokaMeaning("Kumkumadi Tailam is the supreme classical elixir for facial luminosity, clearing blemishes, and youthful radiance.")
                    .classicalTreatiseReference("Ashtanga Hridaya, Uttarasthana 13")
                    .quickReplies(List.of("Buy Kumkumadi Tailam", "Neem & Manjistha benefits", "Diet for Glowing Skin", "Consult Ayurvedic Dermatologist"))
                    .herbKeywords(List.of("neem", "haridra", "manjistha"))
                    .productKeywords(List.of("kumkumadi", "neem"))
                    .recipeKeywords(List.of("tea", "kitchari"))
                    .build();
        }

        // 11. Stress, Anxiety & Memory (Medhya Rasayana)
        if (containsAny(q, "stress", "anxiety", "nervous", "tension", "worry", "panic", "calm", "relax", "brain", "focus", "memory", "adhd", "depression", "burnout")) {
            return SynthesizedResponse.builder()
                    .reply("### 🧠 Calming the Mind & Strengthening Prana (Medhya Rasayana)\n\n" +
                            "Mental fatigue, stress, and overthinking indicate an aggravation of **Prāṇa Vāyu** and **Sādhaka Pitta**, which depletes the brain's stabilizing essence (**Ojas**).\n\n" +
                            "#### 🌿 Mind Rejuvenation Protocol:\n" +
                            "1. **Medhya Botanicals:** *Brahmi (Bacopa monnieri)* and *Shankhpushpi* enhance memory (*Dhi*), cognitive retention (*Dhriti*), and recall (*Smriti*) while reducing cortisol.\n" +
                            "2. **Shiroabhyanga (Warm Head Massage):** Apply warm *Brahmi Tailam* or *Bhringraj Oil* to the crown of the head to nourish the central nervous system.\n" +
                            "3. **Nadi Shodhana Pranayama:** Practice 10 minutes of alternate nostril breathing daily to harmonize the right and left brain hemispheres.\n" +
                            "4. **Ashwagandha Tonification:** Take 1/2 tsp of organic Ashwagandha with warm milk or ghee in the morning for adaptogenic stress resistance.")
                    .sanskritShloka("धीधैर्यस्मृतिविभ्रष्टः कर्म यत् कुरुतेऽशुभम्। प्रज्ञापराधं तं विद्यात् सर्वदोषप्रकोपणम्॥")
                    .shlokaMeaning("When intellect, restraint, and memory are compromised, one engages in unwholesome actions (Prajnaparadha), which is the root cause of all psychological and physical disease.")
                    .classicalTreatiseReference("Charaka Samhita, Sharira Sthana 1/102")
                    .quickReplies(List.of("Brahmi vs Ashwagandha", "How to practice Nadi Shodhana", "Medhya Rasayana Guide", "Book Vaidya"))
                    .herbKeywords(List.of("brahmi", "ashwagandha", "shankhpushpi"))
                    .productKeywords(List.of("brahmi", "ashwagandha"))
                    .recipeKeywords(List.of("moon milk", "tea"))
                    .build();
        }

        // 12. Joints, Arthritis & Back Pain (Sandhivata / Amavata)
        if (containsAny(q, "joint", "arthritis", "knee", "back pain", "spine", "stiff", "inflammation", "sandhi", "swelling", "gout", "sciatica")) {
            return SynthesizedResponse.builder()
                    .reply("### 🦴 Restoring Joint Mobility & Soothing Inflammation (Sandhivata / Amavata)\n\n" +
                            "Joint stiffness and pain arise either from **Sandhivata** (degenerative Vata dryness depleting synovial fluid/Sleshaka Kapha) or **Āmavata** (inflammatory metabolic toxins lodged in joint tissues).\n\n" +
                            "#### 🍃 Classical Joint Therapy:\n" +
                            "1. **Mahanarayan & Gandha Tailam:** Warm Mahanarayan Oil and massage the affected joints with gentle circular strokes, followed by warm fomentation (*Svedana*).\n" +
                            "2. **Shallaki (Boswellia) & Guggulu Formulations:** *Yograj Guggulu* and purified Shallaki reduce joint swelling and enhance ligament flexibility.\n" +
                            "3. **Golden Turmeric Ginger Milk:** Drink warm milk cooked with crushed turmeric root, dry ginger powder (*Shunti*), and black pepper (*Maricha*) for systemic bioavailable curcumin absorption.\n" +
                            "4. **Gentle Asanas:** Practice *Sukshma Vyayama* (micro-joint rotations), *Pawanamuktasana*, and *Vrikshasana*.")
                    .sanskritShloka("वातव्याधिप्रशमनं महाना Debated तैलमुत्तमम्। संकोचे स्तम्भे शूले च सन्धिगे परमौषधम्॥")
                    .shlokaMeaning("Mahanarayan Oil is the foremost classical formulation for pacifying severe Vata disorders, joint contracture, stiffness, and chronic musculoskeletal pain.")
                    .classicalTreatiseReference("Bhaishajya Ratnavali, Vatavyadhi Chikitsa")
                    .quickReplies(List.of("Mahanarayan Oil application", "Yograj Guggulu details", "Golden Milk Recipe", "Consult Orthopedic Vaidya"))
                    .herbKeywords(List.of("shallaki", "guggulu", "haridra"))
                    .productKeywords(List.of("mahanarayan", "turmeric"))
                    .recipeKeywords(List.of("golden milk", "kitchari"))
                    .build();
        }

        // 13. Immunity, Vitality & Longevity (Rasayana & Ojas)
        if (containsAny(q, "immunity", "ojas", "vitality", "energy", "fatigue", "weakness", "rejuvenat", "rasayana", "shilajit", "chyawanprash")) {
            return SynthesizedResponse.builder()
                    .reply("### 🛡️ Building Core Ojas & Cellular Vitality (Rasayana & Vyadhikshamatva)\n\n" +
                            "In Ayurveda, supreme immunity is called **Vyādhikshamatva**—the physiological capacity to resist and neutralize disease—powered by pure **Ojas** (the supreme refined essence of all seven bodily tissues).\n\n" +
                            "#### 🍯 Vitality Protocol:\n" +
                            "1. **Classical Chyawanprash:** Take 1 tablespoon of authentic Amla-based Chyawanprash every morning on an empty stomach with warm cow's milk.\n" +
                            "2. **Pure Himalayan Shilajit:** Pure resin contains 84+ ionic minerals and fulvic acid to catalyze mitochondrial ATP production and cellular longevity.\n" +
                            "3. **Guduchi (Giloy) - The Nectar of Immortality:** Take Guduchi Ghanvati to balance all three Doshas and support macrophage immune activity.\n" +
                            "4. **Sattvic Lifestyle:** Practice regular fasting according to appetite, restful sleep, and positive emotional contemplation (*Achara Rasayana*).")
                    .sanskritShloka("दीर्घमायुः स्मृतिं मेधामारोग्यं तरुणं वयः। प्रभावर्णस्वरौदार्यं देहेन्द्रियबलं परम्॥ लभते ना रसायनात्।")
                    .shlokaMeaning("Through authentic Rasayana therapy, one attains longevity, sharp memory, robust intelligence, freedom from disease, youthful vigor, lustrous complexion, melodious voice, and supreme bodily strength.")
                    .classicalTreatiseReference("Charaka Samhita, Chikitsa Sthana 1/1/7-8")
                    .quickReplies(List.of("How to take Shilajit Resin?", "Chyawanprash benefits", "Guduchi (Giloy) Guide", "Book Consultation"))
                    .herbKeywords(List.of("amla", "guduchi", "shilajit"))
                    .productKeywords(List.of("shilajit", "chyawanprash"))
                    .recipeKeywords(List.of("golden milk", "kitchari"))
                    .build();
        }

        // 14. Universal Dynamic Holistic Assessment
        return SynthesizedResponse.builder()
                .reply(String.format("### 🌿 Ayurvedic Holistic Analysis & Clinical Guidance\n\n" +
                        "Thank you for your question regarding **\"%s\"**.\n\n" +
                        "From an authentic Ayurvedic perspective, health is the dynamic equilibrium of **Doshas** (energetic principles), **Agni** (digestive metabolic fire), **Dhatus** (seven bodily tissues), and **Malas** (proper elimination), alongside a serene mind and senses.\n\n" +
                        "#### 🍃 4-Pillar Therapeutic Strategy:\n" +
                        "1. **Constitutional Balance (Tridosha & Agni):** Harmonize your current routine with your unique body type (Prakriti / Vikriti).\n" +
                        "2. **Ahara (Diet & Pathya):** Favor fresh, warm, easily digestible whole foods cooked with digestive spices (Cumin, Ginger, Coriander, Turmeric).\n" +
                        "3. **Vihara & Dinacharya (Lifestyle & Movement):** Synchronize your circadian rhythm with early morning waking, gentle Yoga asanas, and mindful Pranayama.\n" +
                        "4. **Aushadha (Botanical Tonics):** Specific adaptogenic herbs nourish target tissue channels without systemic side effects.\n\n" +
                        "Explore our pharmacopoeia, try a kitchen recipe, or connect directly with our certified Vaidyas for an individualized clinical prescription.",
                        query != null ? query : "your health query"))
                .sanskritShloka("स्वस्थस्य स्वास्थ्यरक्षणं आतुरस्य विकारप्रशमनं च।")
                .shlokaMeaning("The twin supreme aims of Ayurveda are: to preserve and protect the health of the healthy, and to cure and alleviate disease in the ill.")
                .classicalTreatiseReference("Charaka Samhita, Sutra Sthana 30/26")
                .quickReplies(List.of("What is my Dosha constitution?", "Herbs for vitality & stress", "Kitchen detox recipes", "Consult a certified Vaidya"))
                .herbKeywords(List.of("ashwagandha", "triphala", "tulsi"))
                .productKeywords(List.of("ashwagandha", "triphala"))
                .recipeKeywords(List.of("kitchari", "tea"))
                .build();
    }

    private SynthesizedResponse generateDinacharyaNowResponse() {
        LocalTime now = LocalTime.now();
        int hour = now.getHour();

        String phase;
        String doshaPeriod;
        String recommendations;

        if (hour >= 6 && hour < 10) {
            phase = "Morning Kapha Phase (6:00 AM – 10:00 AM)";
            doshaPeriod = "💧 Heavy, Earthy & Stable Kapha Energy";
            recommendations = "• **Optimal Activities:** Vigorous movement, Surya Namaskar, Kapalabhati Pranayama to shake off morning sluggishness.\n" +
                    "• **Diet:** Warm CCF digestive tea or stewed apples; avoid heavy cold dairy breakfast.\n" +
                    "• **Mindset:** Set clear daily intentions; practice Gandusha (oil pulling) and tongue scraping (*Jihwa Nirlekhana*).";
        } else if (hour >= 10 && hour < 14) {
            phase = "Midday Pitta Phase (10:00 AM – 2:00 PM)";
            doshaPeriod = "🔥 Supreme Agni Peak & High Metabolic Power";
            recommendations = "• **Optimal Activities:** High-focus cognitive tasks, strategic decision making, and your **main hearty meal of the day**.\n" +
                    "• **Diet:** Eat your largest, most nourishing meal now when digestive fire (*Jatharagni*) is at its solar zenith.\n" +
                    "• **Mindset:** Take a 100-step gentle stroll (*Shatapadi*) after lunch; avoid midday sleeping which triggers Ama.";
        } else if (hour >= 14 && hour < 18) {
            phase = "Afternoon Vata Phase (2:00 PM – 6:00 PM)";
            doshaPeriod = "💨 Light, Creative, Mobile Vata Energy";
            recommendations = "• **Optimal Activities:** Creative writing, brainstorming, communicative meetings, light mobility stretches.\n" +
                    "• **Hydration:** Sip warm spiced herbal tea (Cinnamon, Cardamom) to prevent late-afternoon Vata dryness and caffeine crashes.\n" +
                    "• **Mindset:** Take 5 minutes of deep belly breathing to ground restless mental energy.";
        } else if (hour >= 18 && hour < 22) {
            phase = "Evening Kapha Phase (6:00 PM – 10:00 PM)";
            doshaPeriod = "💧 Calming, Grounding & Restful Kapha Energy";
            recommendations = "• **Optimal Activities:** Light dinner before 7:30 PM (Kitchari or vegetable soup); evening stroll; family connection.\n" +
                    "• **Sleep Prep:** Dim bright blue screens by 9:00 PM; enjoy warm Ashwagandha Moon Milk.\n" +
                    "• **Ritual:** 5-minute foot massage (*Padabhyanga*) with warm sesame oil and Bhramari Pranayama.";
        } else {
            phase = "Night Pitta & Vata Phase (10:00 PM – 6:00 AM)";
            doshaPeriod = "✨ Deep Cellular Repair, Liver Detox & Dreaming";
            recommendations = "• **Optimal Activities:** Deep biological sleep. Between 10 PM - 2 AM, the liver (*Yakrit*) conducts enzymatic blood purification.\n" +
                    "• **Brahma Muhurta (4:30 AM – 6:00 AM):** The sacred pre-dawn window where Sattva Guna is purest—ideal for meditation and wakefulness.";
        }

        return SynthesizedResponse.builder()
                .reply(String.format("### ⏰ Biological Ayurvedic Clock: Current Dinacharya Phase\n\n" +
                        "**Current Phase:** %s\n" +
                        "**Dominant Element:** %s\n\n" +
                        "#### 🌿 Recommended Regimen for Right Now:\n%s",
                        phase, doshaPeriod, recommendations))
                .sanskritShloka("ब्राह्मे मुहूर्ते उत्तिष्ठेत् स्वस्थो रक्षार्थमायुषः।")
                .shlokaMeaning("An individual seeking optimal health, mental clarity, and longevity should awaken during the sacred Brahma Muhurta (pre-dawn hour).")
                .classicalTreatiseReference("Ashtanga Hridaya, Sutra Sthana 2/1")
                .quickReplies(List.of("Full Dinacharya Guide", "Best Breakfast for my Dosha", "Evening Moon Milk Recipe", "Book Vaidya"))
                .herbKeywords(List.of("ashwagandha", "triphala", "tulsi"))
                .productKeywords(List.of("ashwagandha", "triphala"))
                .recipeKeywords(List.of("ccf", "moon milk", "kitchari"))
                .build();
    }

    private boolean containsAny(String input, String... terms) {
        if (input == null) return false;
        for (String term : terms) {
            if (input.contains(term.toLowerCase())) return true;
        }
        return false;
    }
}
