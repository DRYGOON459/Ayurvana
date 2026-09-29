package com.ayurvana.service.ai;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

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

        // 1. Emergency Safety Check
        if (containsAny(q, "chest pain", "heart attack", "difficulty breathing", "severe bleeding", "unconscious", "stroke", "poisoning", "suicide", "emergency")) {
            return SynthesizedResponse.builder()
                    .reply("⚠️ **Urgent Medical Advisory**: The symptoms you described require immediate allopathic emergency assessment. Ayurvedic supportive care is not a substitute for acute emergency medicine. Please contact your local emergency services (112 / 911 / 108) or visit the nearest emergency trauma center immediately.")
                    .sanskritShloka("असाध्ये तु भिषक् प्राज्ञः प्रत्याख्यायाचरेत् क्रियाम्।")
                    .shlokaMeaning("In critical emergencies, an astute physician must first ensure vital life preservation before constitutional therapeutics.")
                    .classicalTreatiseReference("Charaka Samhita, Sutra Sthana 10/6")
                    .quickReplies(List.of("Find Nearest Hospital", "Consult Senior Vaidya", "Home Care Safety Guidelines"))
                    .build();
        }

        // 2. Determine Topic Domain & Energetics
        boolean isSleep = containsAny(q, "sleep", "insomnia", "nightmare", "anidra", "restless", "wake", "bedtime", "melatonin", "tired");
        boolean isDigestion = containsAny(q, "acid", "reflux", "gerd", "bloat", "gas", "constipat", "digestion", "gut", "stomach", "agni", "appetite", "bowel", "diarrhea", "cramp");
        boolean isSkin = containsAny(q, "skin", "acne", "pimple", "glow", "blemish", "eczema", "psoriasis", "complexion", "face", "rash", "dry skin");
        boolean isStress = containsAny(q, "stress", "anxiety", "nervous", "tension", "worry", "panic", "calm", "relax", "brain", "focus", "memory", "adhd", "depression", "burnout");
        boolean isJoints = containsAny(q, "joint", "arthritis", "knee", "back pain", "spine", "stiff", "inflammation", "sandhi", "swelling", "gout");
        boolean isImmunity = containsAny(q, "immunity", "ojas", "vitality", "energy", "fatigue", "weakness", "rejuvenat", "rasayana", "shilajit", "chyawanprash");
        boolean isRespiratory = containsAny(q, "cough", "cold", "sinus", "mucus", "phlegm", "asthma", "throat", "bronchitis", "congestion", "allergy");
        boolean isWeight = containsAny(q, "weight", "fat", "obesity", "metabolism", "slimming", "cholesterol", "diet", "fasting", "meda");
        boolean isWomen = containsAny(q, "period", "pcos", "pcod", "fertility", "cramp", "menopause", "pregnancy", "hormon", "shatavari");
        boolean isDoshaQuery = containsAny(q, "dosha", "vata", "pitta", "kapha", "prakriti", "vikriti", "constitution");
        boolean isHerbQuery = containsAny(q, "ashwagandha", "brahmi", "tulsi", "triphala", "shilajit", "neem", "turmeric", "haridra", "guduchi", "shatavari", "amla", "ghee");

        StringBuilder reply = new StringBuilder();
        String shloka;
        String meaning;
        String treatise;
        List<String> quickReplies = new ArrayList<>();
        List<String> herbKeywords = new ArrayList<>();
        List<String> productKeywords = new ArrayList<>();
        List<String> recipeKeywords = new ArrayList<>();

        if (isSleep) {
            reply.append("### 🌙 Ayurvedic Protocol for Restful Sleep & Nervous Equilibrium (Anidra)\n\n")
                 .append("In classical Ayurveda, disturbed sleep is primarily attributed to aggravated **Vata Dosha** in the **Majjā Dhātu** (nervous system) accompanied by elevated **Raja Guna** (mental restlessness).\n\n")
                 .append("#### 🌿 Recommended Holistic Regimen:\n")
                 .append("1. **Ashwagandha Moon Milk:** Simmer 1 cup of whole organic milk (or almond milk) with 1/2 tsp Ashwagandha powder, 1 crushed green cardamom, and a pinch of grated nutmeg (*Jātīphala*) 30 minutes before rest.\n")
                 .append("2. **Pādābhyanga (Foot Massage):** Gently massage the soles of your feet with warm Sesame or *Ksheerabala Tailam* for 5 minutes to ground upward-moving Prana Vayu.\n")
                 .append("3. **Evening Dinacharya:** Avoid digital blue light screens after 9:00 PM; favor warm golden ambient lighting and 10 minutes of gentle *Bhramari Pranayama* (Humming Bee Breath).\n")
                 .append("4. **Dietary Guidance:** Avoid stimulating caffeinated beverages, heavy raw salads, and cold foods in the evening.");

            shloka = "रात्रौ जागरणं रूक्षं स्निग्धं प्रस्वपनं दिवा। अरूक्षममनभिष्यन्दि त्वासीनं प्रचलायितम्॥";
            meaning = "Remaining awake at night causes dryness and Vata aggravation, whereas timely night sleep provides unctuous nourishment (Sneha) and cellular restoration.";
            treatise = "Ashtanga Hridaya, Sutra Sthana 7/53";
            quickReplies = List.of("How to make Moon Milk?", "Best herbs for anxiety", "Book a Sleep Specialist Doctor", "Take Dosha Quiz");
            herbKeywords = List.of("ashwagandha", "brahmi", "jatiphala");
            productKeywords = List.of("sleep", "ashwagandha", "ksheerabala");
            recipeKeywords = List.of("moon milk", "tea");

        } else if (isDigestion) {
            boolean isAcidic = containsAny(q, "acid", "reflux", "gerd", "burning", "heartburn", "sour");
            if (isAcidic) {
                reply.append("### 🔥 Soothing Hyperacidity & Gastric Heat (Amlapitta)\n\n")
                     .append("According to the *Charaka Samhita*, hyperacidity arises when **Pāchaka Pitta** increases in its liquid (*Drava*) and sour (*Amla*) qualities, impairing the protective gastric mucosal barrier.\n\n")
                     .append("#### 🍃 Therapeutic Protocol:\n")
                     .append("1. **CCF Digestive Tea:** Sip lukewarm Cumin-Coriander-Fennel tea between meals to cool the gastrointestinal tract without extinguishing digestive fire (*Agni*).\n")
                     .append("2. **Yashtimadhu (Licorice) & Cow's Ghee:** 1/2 teaspoon of organic Licorice root powder mixed with 1 teaspoon of pure A2 Gir Cow Ghee acts as an instant mucosal coating.\n")
                     .append("3. **Pathya (Favorable Foods):** Sweet ripe fruits, soaked raisins, cucumbers, coconut water, boiled mung dal, and cooling basmati rice.\n")
                     .append("4. **Apathya (Foods to Avoid):** Fermented foods, vinegar, green chilies, deep-fried snacks, alcohol, and excessive tomatoes.");

                shloka = "अविपत्तिकरं चूर्णं पित्तरोगापहं परम्। अम्लपित्ते विशेषेण हितं विण्मूत्रशोधनम्॥";
                meaning = "Avipattikar Churna is the foremost classical remedy for eliminating excessive sour bile, burning acidity, and purifying metabolic channels.";
                treatise = "Bhaishajya Ratnavali, Amlapitta Chikitsa";
                quickReplies = List.of("Avipattikar Churna dosage", "CCF Tea Recipe", "Consult Gut Vaidya", "Pitta-pacifying diet");
                herbKeywords = List.of("yashtimadhu", "amla", "fennel");
                productKeywords = List.of("avipattikar", "triphala");
                recipeKeywords = List.of("ccf", "tea", "kitchari");
            } else {
                reply.append("### 🌿 Igniting Digestive Agni & Relieving Bloating / Constipation\n\n")
                     .append("Sluggish digestion, gas, and irregular elimination indicate **Manda Agni** or **Vishama Agni**, driven by accumulated metabolic toxins (**Āma**) and aggravated **Apāna Vāyu**.\n\n")
                     .append("#### 🍃 Rebalancing Steps:\n")
                     .append("1. **Fresh Ginger Appetizer:** Chew a thin slice of fresh ginger with a pinch of rock salt (*Saindhava Lavana*) 10 minutes prior to meals to activate enzymatic secretions.\n")
                     .append("2. **Triphala Churna at Bedtime:** 1 teaspoon of Triphala powder with 1 cup of warm water facilitates gentle, non-habit-forming bowel tonification.\n")
                     .append("3. **Digestive Spiced Buttermilk (Takra):** Drink freshly churned buttermilk with roasted cumin powder and rock salt after lunch.\n")
                     .append("4. **Hydration Rule:** Sip warm boiled water throughout the day; avoid cold or iced drinks with meals.");

                shloka = "रोगाः सर्वेऽपि मन्देऽग्नौ सुतरामुदराणि च।";
                meaning = "All chronic illnesses, metabolic imbalances, and abdominal disorders originate from a sluggish or suppressed digestive fire (Agni).";
                treatise = "Ashtanga Hridaya, Nidana Sthana 12/1";
                quickReplies = List.of("Triphala Churna benefits", "Medicated Takra Recipe", "Diet for Sluggish Agni", "Book Doctor");
                herbKeywords = List.of("triphala", "haritaki", "ginger");
                productKeywords = List.of("triphala", "takra");
                recipeKeywords = List.of("takra", "kitchari");
            }

        } else if (isSkin) {
            reply.append("### ✨ Classical Ayurvedic Dermatology: Skin Radiance & Blood Purification (Rakta Shodhana)\n\n")
                 .append("Ayurveda views the skin (*Tvachā*) as an external manifestation of the **Rasa Dhātu** (plasma) and **Rakta Dhātu** (blood). Acne, hyperpigmentation, and inflammation stem from toxic heat (*Pitta-Rakta Dushti*).\n\n")
                 .append("#### 🌸 Clinical Radiance Routine:\n")
                 .append("1. **Kumkumadi Tailam Elixir:** Gently press 3–4 drops of authentic saffron-infused Kumkumadi oil into damp facial skin before bedtime for cellular rejuvenation.\n")
                 .append("2. **Blood-Purifying Botanicals:** *Neem*, *Manjistha*, and *Guduchi* eliminate deep lymphatic toxins and balance sebum production.\n")
                 .append("3. **Cooling Hydration:** Drink rose-infused water or hibiscus petal infusion to dissipate internal liver heat.\n")
                 .append("4. **Herbal Lepa (Face Mask):** Mix sandalwood powder with pure rose water and a pinch of turmeric for localized blemish relief.");

            shloka = "कुङ्कुमाद्यमिदं तैलं मुखकान्तिकरं परम्। नीलिकां पिप्लूं व्यङ्गं हन्ति वक्त्रे च यौवनम्॥";
            meaning = "Kumkumadi Tailam is the supreme herbal oil for imparting facial luminosity, clearing pigmentation, and preserving youthful radiance.";
            treatise = "Bhavaprakasha Nighantu, Taila Varga";
            quickReplies = List.of("Kumkumadi Tailam details", "Neem & Manjistha dosage", "Diet for Glowing Skin", "Consult Ayurvedic Dermatologist");
            herbKeywords = List.of("neem", "haridra", "manjistha");
            productKeywords = List.of("kumkumadi", "neem");
            recipeKeywords = List.of("tea", "kitchari");

        } else if (isStress) {
            reply.append("### 🧠 Calming the Mind & Strengthening Prana (Medhya Rasayana)\n\n")
                 .append("Mental fatigue, stress, and overthinking indicate an aggravation of **Prāṇa Vāyu** and **Sādhaka Pitta**, which depletes the brain's stabilizing essence (**Ojas**).\n\n")
                 .append("#### 🌿 Mind Rejuvenation Protocol:\n")
                 .append("1. **Medhya Botanicals:** *Brahmi (Bacopa monnieri)* and *Shankhpushpi* enhance memory (*Dhi*), cognitive retention (*Dhriti*), and recall (*Smriti*) while reducing cortisol.\n")
                 .append("2. **Shiroabhyanga (Warm Head Massage):** Apply warm *Brahmi Tailam* or *Bhringraj Oil* to the crown of the head to nourish the central nervous system.\n")
                 .append("3. **Nadi Shodhana Pranayama:** Practice 10 minutes of alternate nostril breathing daily to harmonize the right and left brain hemispheres.\n")
                 .append("4. **Ashwagandha Tonification:** Take 1/2 tsp of organic Ashwagandha with warm milk or ghee in the morning for adaptogenic stress resistance.");

            shloka = "धीधैर्यस्मृतिविभ्रष्टः कर्म यत् कुरुतेऽशुभम्। प्रज्ञापराधं तं विद्यात् सर्वदोषप्रकोपणम्॥";
            meaning = "When intellect, restraint, and memory are compromised, one engages in unwholesome actions (Prajnaparadha), which is the root cause of all psychological and physical disease.";
            treatise = "Charaka Samhita, Sharira Sthana 1/102";
            quickReplies = List.of("Brahmi vs Ashwagandha", "How to practice Nadi Shodhana", "Medhya Rasayana Guide", "Book Vaidya");
            herbKeywords = List.of("brahmi", "ashwagandha", "shankhpushpi");
            productKeywords = List.of("brahmi", "ashwagandha");
            recipeKeywords = List.of("moon milk", "tea");

        } else if (isJoints) {
            reply.append("### 🦴 Restoring Joint Mobility & Soothing Inflammation (Sandhivata / Amavata)\n\n")
                 .append("Joint stiffness and pain arise either from **Sandhivata** (degenerative Vata dryness depleting synovial fluid/Sleshaka Kapha) or **Āmavata** (inflammatory metabolic toxins lodged in joint tissues).\n\n")
                 .append("#### 🍃 Classical Joint Therapy:\n")
                 .append("1. **Mahanarayan & Gandha Tailam:** Warm Mahanarayan Oil and massage the affected joints with gentle circular strokes, followed by warm fomentation (*Svedana*).\n")
                 .append("2. **Shallaki (Boswellia) & Guggulu Formulations:** *Yograj Guggulu* and purified Shallaki reduce joint swelling and enhance ligament flexibility.\n")
                 .append("3. **Golden Turmeric Ginger Milk:** Drink warm milk cooked with crushed turmeric root, dry ginger powder (*Shunti*), and black pepper (*Maricha*) for systemic bioavailable curcumin absorption.\n")
                 .append("4. **Gentle Asanas:** Practice *Sukshma Vyayama* (micro-joint rotations), *Pawanamuktasana*, and *Vrikshasana*.");

            shloka = "वातव्याधिप्रशमनं महाना Debated तैलमुत्तमम्। संकोचे स्तम्भे शूले च सन्धिगे परमौषधम्॥";
            meaning = "Mahanarayan Oil is the foremost classical formulation for pacifying severe Vata disorders, joint contracture, stiffness, and chronic musculoskeletal pain.";
            treatise = "Bhaishajya Ratnavali, Vatavyadhi Chikitsa";
            quickReplies = List.of("Mahanarayan Oil application", "Yograj Guggulu details", "Golden Milk Recipe", "Consult Orthopedic Vaidya");
            herbKeywords = List.of("shallaki", "guggulu", "haridra");
            productKeywords = List.of("mahanarayan", "turmeric");
            recipeKeywords = List.of("golden milk", "kitchari");

        } else if (isImmunity) {
            reply.append("### 🛡️ Building Core Ojas & Cellular Vitality (Rasayana & Vyadhikshamatva)\n\n")
                 .append("In Ayurveda, supreme immunity is called **Vyādhikshamatva**—the physiological capacity to resist and neutralize disease—powered by pure **Ojas** (the supreme refined essence of all seven bodily tissues).\n\n")
                 .append("#### 🍯 Vitality Protocol:\n")
                 .append("1. **Classical Chyawanprash:** Take 1 tablespoon of authentic Amla-based Chyawanprash every morning on an empty stomach with warm cow's milk.\n")
                 .append("2. **Pure Himalayan Shilajit:** Pure resin contains 84+ ionic minerals and fulvic acid to catalyze mitochondrial ATP production and cellular longevity.\n")
                 .append("3. **Guduchi (Giloy) - The Nectar of Immortality:** Take Guduchi Ghanvati to balance all three Doshas and support macrophage immune activity.\n")
                 .append("4. **Sattvic Lifestyle:** Practice regular fasting according to appetite, restful sleep, and positive emotional contemplation (*Achara Rasayana*).");

            shloka = "दीर्घमायुः स्मृतिं मेधामारोग्यं तरुणं वयः। प्रभावर्णस्वरौदार्यं देहेन्द्रियबलं परम्॥ लभते ना रसायनात्।";
            meaning = "Through authentic Rasayana therapy, one attains longevity, sharp memory, robust intelligence, freedom from disease, youthful vigor, lustrous complexion, melodious voice, and supreme bodily strength.";
            treatise = "Charaka Samhita, Chikitsa Sthana 1/1/7-8";
            quickReplies = List.of("How to take Shilajit Resin?", "Chyawanprash benefits", "Guduchi (Giloy) Guide", "Book Consultation");
            herbKeywords = List.of("amla", "guduchi", "shilajit");
            productKeywords = List.of("shilajit", "chyawanprash");
            recipeKeywords = List.of("golden milk", "kitchari");

        } else if (isHerbQuery) {
            reply.append(String.format("### 🌿 Ayurvedic Pharmacopoeia (Dravyaguna Vijnana): Holistic Botanical Assessment\n\n" +
                    "Classical Ayurveda classifies medicinal plants not merely by chemical constituents, but through their energetic blueprint: **Rasa** (Taste), **Guna** (Qualities), **Vīrya** (Thermal Potency), **Vipāka** (Post-Digestive Transformation), and **Prabhāva** (Unique Specific Action).\n\n" +
                    "#### 🍃 Key Herbal Principles:\n" +
                    "- **Constitutional Action:** Herbs must be matched to your current Prakriti (inherent constitution) and Vikriti (current imbalance).\n" +
                    "- **Anupāna (Vehicle of Delivery):** Using the correct carrier (warm water, A2 cow's milk, raw honey, or ghee) directs the herb deep into target tissue channels (*Dhātu Srotas*).\n" +
                    "- **Synergy:** Single herbs are often compounded in classical polyherbal formulations to maximize efficacy and buffer side-effects."));

            shloka = "यथा विषं यथा शस्त्रं यथाऽग्निरशनिर्यथा। तथौषधमविज्ञातं विज्ञातममृतं यथा॥";
            meaning = "An herb administered without proper energetic knowledge acts like a weapon or poison; but when understood and prescribed precisely, it acts as the nectar of immortality.";
            treatise = "Charaka Samhita, Sutra Sthana 1/124";
            quickReplies = List.of("Ashwagandha Profile", "Triphala Churna Guide", "Pure Shilajit Resin", "Consult Pharmacopoeia Vaidya");
            herbKeywords = List.of("ashwagandha", "triphala", "tulsi", "brahmi");
            productKeywords = List.of("ashwagandha", "triphala", "shilajit");
            recipeKeywords = List.of("tea", "moon milk");

        } else if (isDoshaQuery) {
            reply.append("### ⚖️ The Three Doshas: Understanding Vata, Pitta & Kapha\n\n" +
                    "In Ayurveda, your health depends on the harmonious equilibrium of the three biological forces (**Tridosha**):\n\n" +
                    "- **💨 Vāta Dosha (Space & Air):** Governs all biological movement, nerve impulses, breath, circulation, and elimination. Balanced: creative & enthusiastic. Imbalanced: anxiety, insomnia, dry skin, constipation.\n" +
                    "- **🔥 Pitta Dosha (Fire & Water):** Governs digestion, enzyme metabolism, body temperature, intellect, and vision. Balanced: sharp intellect, glowing skin. Imbalanced: acidity, anger, skin breakouts, inflammation.\n" +
                    "- **💧 Kapha Dosha (Earth & Water):** Governs biological structure, cellular lubrication, joint stability, and immunity. Balanced: calm, grounded, strong endurance. Imbalanced: sluggishness, weight gain, congestion, lethargy.\n\n" +
                    "Would you like to take our clinical 10-question Prakriti Assessment to determine your unique constitutional balance?");

            shloka = "दोषधातुमलमूलं हि शरीरम्।";
            meaning = "The human body is fundamentally rooted in and sustained by the dynamic balance of the Doshas (regulatory energies), Dhatus (seven bodily tissues), and Malas (excretory products).";
            treatise = "Sushruta Samhita, Sutra Sthana 14/1";
            quickReplies = List.of("Take Free Dosha Quiz", "How to pacify Vata", "How to cool Pitta", "How to energize Kapha");
            herbKeywords = List.of("ashwagandha", "triphala", "ghee");
            productKeywords = List.of("triphala", "ashwagandha");
            recipeKeywords = List.of("kitchari", "tea");

        } else {
            // Universal Dynamic Ayurvedic Synthesis for any open-ended question
            reply.append(String.format("### 🌿 Ayurvedic Holistic Analysis & Guidance\n\n" +
                    "Thank you for your question regarding **\"%s\"**.\n\n" +
                    "From an authentic Ayurvedic perspective, optimal well-being is achieved through a multi-dimensional approach:\n\n" +
                    "1. **Constitutional Balance (Tridosha & Agni):** We evaluate how bodily elements (Vata, Pitta, and Kapha) and digestive fire (*Agni*) interact with your current routine and environment.\n" +
                    "2. **Ahara (Dietary Nutrition):** Nourish your tissues with fresh, seasonally appropriate (*Ritucharya*), warm, home-cooked whole foods while avoiding incompatible combinations (*Viruddha Ahara*).\n" +
                    "3. **Vihara & Dinacharya (Daily Lifestyle & Movement):** Harmonize circadian biology by waking during Brahma Muhurta, practicing gentle Yoga asanas, and cultivating mindful breathing (*Pranayama*).\n" +
                    "4. **Aushadha (Classical Botanicals):** Specific classical herbs and adaptogens help re-establish homeostatic balance without adverse biochemical side-effects.\n\n" +
                    "Feel free to explore our Ayurvedic pharmacopoeia, kitchen pharmacy recipes, or connect directly with our certified Vaidyas for an individualized health plan.",
                    query != null ? query : "your health query"));

            shloka = "स्वस्थस्य स्वास्थ्यरक्षणं आतुरस्य विकारप्रशमनं च।";
            meaning = "The twin supreme aims of Ayurveda are: to preserve and protect the health of the healthy, and to cure and alleviate disease in the ill.";
            treatise = "Charaka Samhita, Sutra Sthana 30/26";
            quickReplies = List.of("What is my Dosha constitution?", "Herbs for vitality & stress", "Kitchen detox recipes", "Consult a certified Vaidya");
            herbKeywords = List.of("ashwagandha", "triphala", "tulsi");
            productKeywords = List.of("ashwagandha", "triphala");
            recipeKeywords = List.of("kitchari", "tea");
        }

        return SynthesizedResponse.builder()
                .reply(reply.toString())
                .sanskritShloka(shloka)
                .shlokaMeaning(meaning)
                .classicalTreatiseReference(treatise)
                .quickReplies(quickReplies)
                .herbKeywords(herbKeywords)
                .productKeywords(productKeywords)
                .recipeKeywords(recipeKeywords)
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
