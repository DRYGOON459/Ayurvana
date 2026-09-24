package com.ayurvana.service.impl;

import com.ayurvana.dto.request.AiChatMessageRequest;
import com.ayurvana.dto.response.AiChatMessageResponse;
import com.ayurvana.dto.response.AiChatSuggestionResponse;
import com.ayurvana.model.entity.AyurvedicRecipe;
import com.ayurvana.model.entity.DoctorProfile;
import com.ayurvana.model.entity.Herb;
import com.ayurvana.model.entity.Product;
import com.ayurvana.model.enums.DoshaType;
import com.ayurvana.repository.DoctorProfileRepository;
import com.ayurvana.repository.HerbRepository;
import com.ayurvana.repository.ProductRepository;
import com.ayurvana.repository.RecipeRepository;
import com.ayurvana.service.AiChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiChatServiceImpl implements AiChatService {

    private final ProductRepository productRepository;
    private final RecipeRepository recipeRepository;
    private final HerbRepository herbRepository;
    private final DoctorProfileRepository doctorProfileRepository;

    @Override
    @Transactional(readOnly = true)
    public AiChatMessageResponse processMessage(AiChatMessageRequest request, String userEmail) {
        String query = request.getMessage() != null ? request.getMessage().trim() : "";
        String lower = query.toLowerCase();

        AiChatMessageResponse.AiChatMessageResponseBuilder builder = AiChatMessageResponse.builder();

        // 1. Emergency or High-Risk Symptoms
        if (matchesAny(lower, "chest pain", "heart attack", "difficulty breathing", "severe bleeding", "unconscious", "stroke", "poisoning", "suicidal", "emergency")) {
            builder.reply("⚠️ **Urgent Medical Advisory**: The symptoms you described indicate a potential medical emergency. Ayurvedic supportive care is not a substitute for acute emergency medicine. Please call your local emergency services (e.g. 112 / 911 / 108) or proceed immediately to the nearest hospital emergency room.")
                    .sanskritShloka("असाध्ये तु भिषक् प्राज्ञः प्रत्याख्यायाचरेत् क्रियाम्।")
                    .shlokaMeaning("In critical or acute emergencies, an astute physician must first secure vital life stability before constitutional therapeutics.")
                    .classicalTreatiseReference("Charaka Samhita, Sutra Sthana 10")
                    .quickReplies(List.of("Find Nearest Emergency Care", "Consult a Specialist Vaidya", "Home Care Guidelines"));
            return builder.build();
        }

        // 2. Sleep & Insomnia (Anidra)
        if (matchesAny(lower, "sleep", "insomnia", "can't sleep", "nightmare", "anidra", "restless", "wake up early", "racing mind", "trouble sleeping")) {
            builder.reply("### 🌙 Ayurvedic Management for Restful Sleep (Anidra)\n\n" +
                    "In classical Ayurveda, insomnia is primarily caused by aggravated **Vata Dosha** in the nervous system (Majjā Dhātu) and excess **Raja Guna** (mental agitation).\n\n" +
                    "**Recommended Daily Regimen:**\n" +
                    "1. **Bedtime Moon Milk:** Drink warm milk cooked with Ashwagandha, crushed Cardamom, and a pinch of Nutmeg (Jatiphala) 30 minutes before bed.\n" +
                    "2. **Pādābhyanga (Foot Massage):** Massage the soles of your feet with warm Sesame or Ksheerabala oil for 5 minutes before sleeping.\n" +
                    "3. **Digital Curfew:** Switch off blue-light screens by 9:30 PM to preserve natural melatonin secretion and settle Prana Vayu.\n" +
                    "4. **Yoga & Pranayama:** Practice 10 minutes of gentle *Bhramari Pranayama* (Humming Bee Breath) and *Shavasana*.")
                    .sanskritShloka("रात्रौ जागरणं रूक्षं स्निग्धं प्रस्वपनं दिवा।")
                    .shlokaMeaning("Staying awake late at night causes dryness (Vata aggravation), whereas adequate natural night sleep imparts deep nourishment and cellular lubrication (Sneha).")
                    .classicalTreatiseReference("Ashtanga Hridaya, Sutra Sthana 7/53")
                    .quickReplies(List.of("How to prepare Moon Milk?", "Best herbs for anxiety", "Book a Sleep Specialist Doctor", "Take Dosha Quiz"));

            // Attach product cards
            List<Product> products = productRepository.findAll().stream()
                    .filter(p -> p.getSlug().contains("ashwagandha") || p.getSlug().contains("ksheerabala") || p.getSlug().contains("brahmi"))
                    .limit(2)
                    .toList();
            builder.recommendedProducts(mapProducts(products));

            // Attach recipe
            recipeRepository.findBySlug("ashwagandha-sleep-moon-milk")
                    .ifPresent(r -> builder.recommendedRecipes(List.of(mapRecipe(r))));

            // Attach herb
            herbRepository.findBySlug("ashwagandha")
                    .ifPresent(h -> builder.recommendedHerbs(List.of(mapHerb(h))));

            return builder.build();
        }

        // 3. Digestion, Acidity, Gas & Bloating (Amlapitta / Agni)
        if (matchesAny(lower, "acidity", "acid reflux", "heartburn", "gas", "bloat", "digestion", "constipation", "indigestion", "gut", "stomach pain", "amlapitta", "gerd", "appetite")) {
            boolean isPitta = matchesAny(lower, "acidity", "heartburn", "acid reflux", "burning", "sour belching", "gerd");
            if (isPitta) {
                builder.reply("### 🔥 Soothing Hyperacidity & Heartburn (Amlapitta)\n\n" +
                        "Hyperacidity stems from an aggravation of **Pāchaka Pitta** where digestive juices become excessively sour, sharp, and liquid (Amla-Tikshna).\n\n" +
                        "**Immediate Relief Protocol:**\n" +
                        "1. **CCF Tea:** Sip warm Cumin-Coriander-Fennel tea throughout the day to neutralize excess gastric acid without suppressing enzymatic power.\n" +
                        "2. **Medicated Ghee / Yashtimadhu:** 1 teaspoon of pure cow ghee or Licorice root powder in lukewarm water forms a soothing mucosal coating.\n" +
                        "3. **Dietary Guidance:** Avoid hot chili, deep-fried snacks, vinegar, tomatoes, and fermented foods. Favor sweet fruits, cucumbers, and basmati rice.\n" +
                        "4. **Classical Formulation:** *Avipattikar Churna* taken before meals brings swift relief.")
                        .sanskritShloka("अविपत्तिकरं चूर्णं पित्तरोगापहं परम्।")
                        .shlokaMeaning("Avipattikar Churna is the foremost classical formulation for pacifying excessive sour bile, burning reflux, and digestive Pitta.")
                        .classicalTreatiseReference("Bhaishajya Ratnavali, Amlapitta Adhyaya")
                        .quickReplies(List.of("View CCF Tea Recipe", "Avipattikar Churna details", "Consult Gut Specialist", "Diet for Pitta Dosha"));

                productRepository.findBySlug("classical-avipattikar-churna")
                        .ifPresent(p -> builder.recommendedProducts(List.of(mapProduct(p))));

                recipeRepository.findBySlug("ccf-digestive-tea")
                        .ifPresent(r -> builder.recommendedRecipes(List.of(mapRecipe(r))));

                herbRepository.findBySlug("yashtimadhu")
                        .ifPresent(h -> builder.recommendedHerbs(List.of(mapHerb(h))));
            } else {
                builder.reply("### 🌿 Restoring Digestive Agni & Relieving Bloating / Constipation\n\n" +
                        "Sluggish digestion and gas represent **Vishama Agni (Irregular Fire)** or **Manda Agni**, driven by cold Vata and heavy Kapha.\n\n" +
                        "**Rebalancing Protocol:**\n" +
                        "1. **Classical Triphala Detox:** Take 1 teaspoon of Triphala Churna in warm water before bedtime for natural colon peristalsis.\n" +
                        "2. **Ginger & Rock Salt:** Chew a thin slice of fresh ginger sprinkled with pink Himalayan rock salt 10 minutes before meals to stoke digestive Agni.\n" +
                        "3. **Medicated Takra:** Enjoy freshly churned spiced buttermilk after lunch with roasted cumin and asafoetida (hing).\n" +
                        "4. **Hydration:** Sip warm boiled water throughout the day; avoid iced drinks during meals.")
                        .sanskritShloka("रोगाः सर्वेऽपि मन्देऽग्नौ सुतरामुदराणि च।")
                        .shlokaMeaning("All physical illnesses and digestive disorders originate from a weak, sluggish, or suppressed digestive fire (Agni).")
                        .classicalTreatiseReference("Ashtanga Hridaya, Nidana Sthana 12/1")
                        .quickReplies(List.of("Triphala Churna dosage", "Medicated Takra Recipe", "Dietary Incompatibilities (Viruddha)", "Book Doctor"));

                productRepository.findBySlug("classical-triphala-churna")
                        .ifPresent(p -> builder.recommendedProducts(List.of(mapProduct(p))));

                recipeRepository.findBySlug("medicated-takra-buttermilk")
                        .ifPresent(r -> builder.recommendedRecipes(List.of(mapRecipe(r))));

                herbRepository.findBySlug("haritaki")
                        .ifPresent(h -> builder.recommendedHerbs(List.of(mapHerb(h))));
            }
            return builder.build();
        }

        // 4. Skin & Beauty (Kushta / Varnya)
        if (matchesAny(lower, "skin", "acne", "glow", "blemish", "pigmentation", "eczema", "psoriasis", "complexion", "kumkumadi", "face", "varnya")) {
            builder.reply("### ✨ Ayurvedic Skin Radiance & Detoxification (Varnya & Rakta Shodhana)\n\n" +
                    "In Ayurveda, the skin (Tvachā) is a direct reflection of your **Rasa** (nutrient plasma) and **Rakta** (blood tissue). Breakouts and dullness indicate accumulated metabolic heat (Pitta) and lymphatic toxins.\n\n" +
                    "**Ayurvedic Skin Protocol:**\n" +
                    "1. **Saffron Kumkumadi Tailam:** Apply 3–4 drops of authentic Kumkumadi Taila at night for a natural golden luminescence and fading of dark spots.\n" +
                    "2. **Blood-Purifying Botanicals:** Herbs like *Neem*, *Manjistha*, and *Turmeric* purify the blood and cool systemic inflammation.\n" +
                    "3. **Cooling Hydration:** Drink Hibiscus & Rose Petal Sharbat in warm weather to soothe internal heat.\n" +
                    "4. **Diet:** Reduce fermented, salty, and deep-fried foods.")
                    .sanskritShloka("कुङ्कुमाद्यमिदं तैलं मुखकान्तिकरं परम्।")
                    .shlokaMeaning("Kumkumadi Tailam is the supreme classical elixir for facial luminosity, clearing blemishes, and youthful radiance.")
                    .classicalTreatiseReference("Ashtanga Hridaya, Uttarasthana 13")
                    .quickReplies(List.of("Buy Kumkumadi Tailam", "Neem & Manjistha benefits", "Diet for Glowing Skin", "Consult Ayurvedic Dermatologist"));

            productRepository.findBySlug("saffron-kumkumadi-tailam")
                    .ifPresent(p -> builder.recommendedProducts(List.of(mapProduct(p))));

            herbRepository.findBySlug("neem")
                    .ifPresent(h -> builder.recommendedHerbs(List.of(mapHerb(h))));

            recipeRepository.findBySlug("cooling-hibiscus-rose-sharbat")
                    .ifPresent(r -> builder.recommendedRecipes(List.of(mapRecipe(r))));

            return builder.build();
        }

        // 5. Joint Pain & Muscular Stiffness (Sandhivata / Amavata / Arthritis)
        if (matchesAny(lower, "joint", "arthritis", "knee pain", "back pain", "stiffness", "amavata", "sandhivata", "muscle pain", "sciatica")) {
            builder.reply("### 🌿 Relieving Joint Mobility & Inflammatory Pain (Sandhivata & Amavata)\n\n" +
                    "Ayurveda classifies joint disorders into two main etiologies:\n" +
                    "- **Sandhivata (Osteoarthritis):** Degenerative dryness from aggravated Vata causing crackling sounds and stiffness.\n" +
                    "- **Amavata (Rheumatoid / Inflammatory):** Toxic metabolic waste (Ama) accumulating in synovial membranes, causing hot, tender swellings.\n\n" +
                    "**Therapeutic Protocol:**\n" +
                    "1. **Internal Resins:** *Yograj Guggulu* and *Shallaki (Boswellia)* scrape toxins and rebuild flexible connective tissue.\n" +
                    "2. **External Abhyanga:** Warm *Mahanarayan Taila* massaged into joints daily for 15 minutes followed by gentle warm fomentation (Swedana).\n" +
                    "3. **Anti-Inflammatory Nutrition:** Golden Turmeric milk with black pepper and a teaspoon of cow ghee.")
                    .sanskritShloka("योगराजो महाश्रेष्ठः सर्ववातविकारनुत्।")
                    .shlokaMeaning("Yograj Guggulu is the foremost classical remedy for overcoming all deep-seated joint, spinal, and neuromuscular Vata afflictions.")
                    .classicalTreatiseReference("Bhaishajya Ratnavali, Amavata Rogadhikara")
                    .quickReplies(List.of("Yograj Guggulu details", "Mahanarayan Oil application", "Book Joint Specialist", "Anti-inflammatory Diet"));

            List<Product> products = productRepository.findAll().stream()
                    .filter(p -> p.getSlug().contains("yograj") || p.getSlug().contains("mahanarayan"))
                    .limit(2)
                    .toList();
            builder.recommendedProducts(mapProducts(products));

            herbRepository.findBySlug("shallaki")
                    .ifPresent(h -> builder.recommendedHerbs(List.of(mapHerb(h))));

            return builder.build();
        }

        // 6. Dosha Principles & Quiz Guidance
        if (matchesAny(lower, "dosha", "prakriti", "vata", "pitta", "kapha", "what is my dosha", "constitution", "tridosha", "vikriti")) {
            builder.reply("### 🧘 The Three Biological Doshas (Tridosha Siddhānta)\n\n" +
                    "According to Ayurveda, your unique mind-body blueprint is determined by three fundamental energies (Prakriti):\n\n" +
                    "1. **Vāta (Air + Ether):** Governs movement, nervous impulses, breathing, and creativity. When imbalanced, causes anxiety, insomnia, constipation, and joint dryness.\n" +
                    "2. **Pitta (Fire + Water):** Governs metabolism, digestion, body temperature, and intellect. When elevated, triggers hyperacidity, skin rashes, inflammation, and irritability.\n" +
                    "3. **Kapha (Water + Earth):** Governs structure, lubrication, stamina, and biological immunity. When aggravated, leads to lethargy, weight gain, sinus congestion, and fluid retention.\n\n" +
                    "💡 **Take our comprehensive 12-question Prakriti Diagnostic Assessment** to determine your precise percentage breakdown and receive a customized dietary & herbal prescription.")
                    .sanskritShloka("वायुः पित्तं कफश्चेति त्रयो दोषाः समासतः।")
                    .shlokaMeaning("Vata, Pitta, and Kapha are the three primary biological forces that, in balance, maintain health, and in vitiation, cause disease.")
                    .classicalTreatiseReference("Ashtanga Hridaya, Sutra Sthana 1/6")
                    .quickReplies(List.of("Start Dosha Quiz Now", "Diet for Vata", "Diet for Pitta", "Diet for Kapha"));

            recipeRepository.findBySlug("classical-tridoshic-kitchari")
                    .ifPresent(r -> builder.recommendedRecipes(List.of(mapRecipe(r))));

            return builder.build();
        }

        // 7. Herbs Query (Ashwagandha, Tulsi, Brahmi, Shilajit, etc.)
        if (matchesAny(lower, "ashwagandha", "tulsi", "brahmi", "shatavari", "neem", "shilajit", "turmeric", "guduchi", "triphala")) {
            String targetSlug = "ashwagandha";
            if (lower.contains("tulsi")) targetSlug = "tulsi";
            else if (lower.contains("brahmi")) targetSlug = "brahmi";
            else if (lower.contains("shatavari")) targetSlug = "shatavari";
            else if (lower.contains("neem")) targetSlug = "neem";
            else if (lower.contains("shilajit")) targetSlug = "pure-himalayan-shilajit-resin";
            else if (lower.contains("guduchi") || lower.contains("giloy")) targetSlug = "guduchi";
            else if (lower.contains("turmeric") || lower.contains("haridra")) targetSlug = "haridra";

            final String fSlug = targetSlug;
            Optional<Herb> herbOpt = herbRepository.findBySlug(fSlug);
            if (herbOpt.isPresent()) {
                Herb h = herbOpt.get();
                builder.reply(String.format("### 🌿 Botanical Profile: %s (%s)\n\n" +
                        "**Botanical Name:** *%s* (%s Family)\n" +
                        "**Ayurvedic Energetics (Dravyaguna):**\n" +
                        "- **Rasa (Taste):** %s\n" +
                        "- **Vīrya (Thermal Potency):** %s\n" +
                        "- **Vipāka (Post-Digestive Effect):** %s\n" +
                        "- **Dosha Action:** %s\n\n" +
                        "**Therapeutic Uses:** %s\n\n" +
                        "**Classical Dosage & Anupāna:** %s\n\n" +
                        "**Precautions / Contraindications:** %s",
                        h.getCommonName(), h.getSanskritName(), h.getScientificName(), h.getBotanicalFamily(),
                        h.getRasa(), h.getVirya(), h.getVipaka(), h.getPrimaryDoshaEffect(),
                        h.getTraditionalUses(),
                        h.getDosageAndPreparations() != null ? h.getDosageAndPreparations() : "Consult physician",
                        h.getSafety() != null && h.getSafety().getContraindications() != null ? h.getSafety().getContraindications() : "Safe under qualified clinical guidance."))
                        .sanskritShloka("यथा विषं यथा शस्त्रं यथाऽग्निरशनिर्यथा। तथौषधमविज्ञातं विज्ञातममृतं यथा॥")
                        .shlokaMeaning("An herb used without proper knowledge acts like poison or fire; used with precise understanding, it acts as the nectar of immortality.")
                        .classicalTreatiseReference("Charaka Samhita, Sutra Sthana 1/124")
                        .quickReplies(List.of("View formulation in Store", "Check Herb-Drug Interactions", "Find Similar Herbs", "Ask Another Question"));

                builder.recommendedHerbs(List.of(mapHerb(h)));
                return builder.build();
            }
        }

        // 8. Doctor Consultation Guidance
        if (matchesAny(lower, "doctor", "consult", "vaidya", "appointment", "pulse diagnosis", "nadi pariksha", "specialist")) {
            builder.reply("### 🩺 Clinical Ayurvedic Telehealth Consultations\n\n" +
                    "AyurVana connects you with certified, licensed **BAMS & MD Ayurvedic Physicians** across specialized classical branches (Ashtanga Ayurveda):\n\n" +
                    "- **Kāyachikitsā:** Internal medicine, diabetes, metabolic & gut health.\n" +
                    "- **Panchakarma:** Clinical biopurification & chronic disease reversal.\n" +
                    "- **Prasūti Tantra:** Women's reproductive health, fertility & PCOS.\n" +
                    "- **Rasāyana:** Geriatric vitality, immunity & anti-aging.\n\n" +
                    "Select a physician below to schedule a secure 1-on-1 video consultation with digital certified prescription delivery.")
                    .sanskritShloka("आयुः कामयमानेन धर्मार्थसुखसाधनम्। आयुर्वेदोपदेशेषु विधेयः परमादरः॥")
                    .shlokaMeaning("One who desires longevity for fulfilling righteous living, wealth, and happiness should hold the teachings of Ayurveda in highest esteem.")
                    .classicalTreatiseReference("Ashtanga Hridaya, Sutra Sthana 1/2")
                    .quickReplies(List.of("View All Available Vaidyas", "Book Dr. Aarav Sharma", "Consult Panchakarma Specialist", "Prescription Refill"));

            List<DoctorProfile> doctors = doctorProfileRepository.findAll().stream().limit(3).toList();
            builder.recommendedDoctors(mapDoctors(doctors));
            return builder.build();
        }

        // 9. Default Intelligent Conversational Response
        builder.reply("Namaste! 🙏 I am your **AyurVana Vaidya AI Health Assistant**, grounded in the classical wisdom of the *Charaka Samhita*, *Sushruta Samhita*, and *Ashtanga Hridaya*.\n\n" +
                "How may I assist your health and wellness journey today?\n\n" +
                "✨ **Popular Topics you can ask me:**\n" +
                "- *\"How do I balance high Pitta acidity and heartburn?\"*\n" +
                "- *\"What is my Dosha constitution?\"*\n" +
                "- *\"What are the benefits of Ashwagandha and Brahmi?\"*\n" +
                "- *\"Natural classical remedies for deep sleep and insomnia\"*\n" +
                "- *\"Ayurvedic recipes for digestive detox (Kitchari, CCF Tea)\"*\n" +
                "- *\"Consult a certified Ayurvedic doctor\"*")
                .sanskritShloka("सर्वे भवन्तु सुखिनः सर्वे सन्तु निरामयाः। सर्वे भद्राणि पश्यन्तु मा कश्चिद्दुःखभाग्भवेत्॥")
                .shlokaMeaning("May all beings be happy. May all beings be free from illness. May all perceive auspiciousness. May none suffer.")
                .classicalTreatiseReference("Brihadaranyaka Upanishad")
                .quickReplies(List.of("What is my Dosha?", "Remedy for Insomnia", "Remedy for Acidity", "Explore Apothecary Store", "Consult a Doctor"));

        return builder.build();
    }

    @Override
    public AiChatSuggestionResponse getSuggestions() {
        return AiChatSuggestionResponse.builder()
                .welcomeMessage("Welcome to AyurVana Intelligent Health Assistant. Grounded in authentic Ayurvedic treatises and clinical herbal medicine.")
                .categories(List.of(
                        AiChatSuggestionResponse.SuggestionCategory.builder()
                                .category("Prakriti & Doshas")
                                .icon("Compass")
                                .prompts(List.of("What is my Dosha constitution?", "How do I calm aggravated Vata?", "Foods to avoid for Pitta heat", "How to overcome Kapha lethargy?"))
                                .build(),
                        AiChatSuggestionResponse.SuggestionCategory.builder()
                                .category("Remedies & Health Goals")
                                .icon("Sparkles")
                                .prompts(List.of("Natural remedy for insomnia & deep sleep", "How to soothe hyperacidity & GERD?", "Classical oils for joint pain & stiffness", "Herbal remedies for glowing skin & acne"))
                                .build(),
                        AiChatSuggestionResponse.SuggestionCategory.builder()
                                .category("Pharmacopoeia & Herbs")
                                .icon("Leaf")
                                .prompts(List.of("Tell me about Ashwagandha benefits", "How to take Triphala Churna?", "Benefits of Pure Himalayan Shilajit", "Is Neem safe during pregnancy?"))
                                .build(),
                        AiChatSuggestionResponse.SuggestionCategory.builder()
                                .category("Kitchen Pharmacy Recipes")
                                .icon("Utensils")
                                .prompts(List.of("How to prepare Classical Kitchari?", "Golden Milk recipe for inflammation", "What is CCF Digestive Tea?", "Medicated Takra recipe for gut health"))
                                .build(),
                        AiChatSuggestionResponse.SuggestionCategory.builder()
                                .category("Clinical Consultations")
                                .icon("UserCheck")
                                .prompts(List.of("How does Ayurvedic Nadi Pariksha work?", "Book consultation with Dr. Aarav Sharma", "Panchakarma detox consultation", "Digital prescription refill"))
                                .build()
                ))
                .build();
    }

    private boolean matchesAny(String input, String... keywords) {
        for (String kw : keywords) {
            if (input.contains(kw.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private List<AiChatMessageResponse.ProductCard> mapProducts(List<Product> products) {
        return products.stream().map(this::mapProduct).collect(Collectors.toList());
    }

    private AiChatMessageResponse.ProductCard mapProduct(Product p) {
        return AiChatMessageResponse.ProductCard.builder()
                .id(p.getId())
                .name(p.getName())
                .sanskritName(p.getSanskritName())
                .slug(p.getSlug())
                .category(p.getCategory() != null ? p.getCategory().name() : "")
                .targetDosha(p.getTargetDosha() != null ? p.getTargetDosha().name() : "")
                .price(p.getPrice())
                .discountPrice(p.getDiscountPrice())
                .imageUrl(p.getImageUrl())
                .shortDescription(p.getShortDescription())
                .build();
    }

    private AiChatMessageResponse.RecipeCard mapRecipe(AyurvedicRecipe r) {
        return AiChatMessageResponse.RecipeCard.builder()
                .id(r.getId())
                .title(r.getTitle())
                .sanskritName(r.getSanskritName())
                .slug(r.getSlug())
                .doshaSuitability(r.getDoshaSuitability() != null ? r.getDoshaSuitability().name() : "")
                .mealType(r.getMealType())
                .cookTimeMinutes(r.getCookTimeMinutes())
                .imageUrl(r.getImageUrl())
                .description(r.getDescription())
                .build();
    }

    private AiChatMessageResponse.HerbCard mapHerb(Herb h) {
        return AiChatMessageResponse.HerbCard.builder()
                .commonName(h.getCommonName())
                .sanskritName(h.getSanskritName())
                .scientificName(h.getScientificName())
                .slug(h.getSlug())
                .primaryDoshaEffect(h.getPrimaryDoshaEffect())
                .rasa(h.getRasa())
                .imageUrl(h.getImageUrl())
                .build();
    }

    private List<AiChatMessageResponse.DoctorCard> mapDoctors(List<DoctorProfile> doctors) {
        return doctors.stream().map(doc -> AiChatMessageResponse.DoctorCard.builder()
                .id(doc.getId())
                .name(doc.getUser() != null ? doc.getUser().getFullName() : "Dr. Ayurvedic Vaidya")
                .primarySpecialization(doc.getPrimarySpecialization())
                .yearsOfExperience(doc.getYearsOfExperience())
                .consultationFee(doc.getConsultationFee())
                .rating(doc.getAverageRating())
                .languagesSpoken(doc.getLanguagesSpoken())
                .build()).collect(Collectors.toList());
    }
}
