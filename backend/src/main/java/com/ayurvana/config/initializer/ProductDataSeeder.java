package com.ayurvana.config.initializer;

import com.ayurvana.model.entity.Product;
import com.ayurvana.model.enums.DoshaType;
import com.ayurvana.model.enums.ProductCategory;
import com.ayurvana.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductDataSeeder {

    private final ProductRepository productRepository;

    public void seedProducts() {
        if (productRepository.count() >= 25) {
            return;
        }

        List<Product> products = new ArrayList<>();

        // 1. Classical Triphala Churna
        products.add(buildProduct("Classical Triphala Churna", "Triphalā Cūrṇa (त्रिफला चूर्ण)", "classical-triphala-churna",
                ProductCategory.CHURNA, DoshaType.TRIDOSHIC,
                "The premier classical colon cleanser, gentle detoxifier, and digestive harmonizer combining Haritaki, Bibhitaki, and Amalaki.",
                "Classical Ayurvedic polyherbal formulation referenced in Charaka Samhita. Synergistically cleanses the gastrointestinal tract, supports healthy microbial flora, and promotes regular elimination without habit formation.",
                "Charaka Samhita, Chikitsa Sthana 1/1",
                "त्रिफला सर्वदोषघ्नी दीपिनी पाचनी सरा। (Triphala pacifies all three doshas, stimulates digestive fire, and facilitates gentle bowel movement.)",
                "Haritaki (Terminalia chebula) 33.3%, Bibhitaki (Terminalia bellirica) 33.3%, Amalaki (Phyllanthus emblica) 33.3%",
                "Gentle daily colon detox, improves nutrient absorption, antioxidant rich, relieves constipation and bloating",
                "1 teaspoon (3–6g) at bedtime", "Warm water or pure cow ghee", "Acute diarrhea, severe dehydration, pregnancy",
                new BigDecimal("220.00"), new BigDecimal("185.00"), 150, "AYUR-TPH-100G", "100g",
                "https://images.unsplash.com/photo-1615485290382-441e4d049cb5?auto=format&fit=crop&w=800&q=80", true, new BigDecimal("4.95"), 84));

        // 2. Ashwagandharishta Classical Elixir
        products.add(buildProduct("Ashwagandharishta Classical Elixir", "Aśvagandhāriṣṭa (अश्वगन्धारिष्ट)", "ashwagandharishta-elixir",
                ProductCategory.ARISHTA, DoshaType.VATA,
                "Naturally fermented bio-active herbal wine for nervous exhaustion, stress, and neuromuscular vitality.",
                "Classical Ayurvedic fermented formulation prepared through natural self-generated alcohol (Sandhana Kalpana). Rejuvenates the nervous system and fosters deep sleep.",
                "Bhaishajya Ratnavali, Murccha Rogadhikara",
                "अश्वगन्धारिष्टो मूर्च्छाऽपस्मारशोषहृत्। (Ashwagandharishta dispels fainting, nervous exhaustion, tremors, and physical emaciation.)",
                "Ashwagandha, Mushali, Manjistha, Haridra, Yashtimadhu, Rasna, Vidari, Dhataki Pushpa, Natural Jaggery",
                "Soothes adrenal fatigue, calms anxious thoughts, supports restful REM sleep, strengthens muscles",
                "15–20 ml twice daily after meals", "Equal quantity of lukewarm water", "Hyperacidity, gastric ulcers, children under 12",
                new BigDecimal("340.00"), new BigDecimal("290.00"), 95, "AYUR-ASW-450ML", "450ml",
                "https://images.unsplash.com/photo-1598511726623-d2e9996892f0?auto=format&fit=crop&w=800&q=80", true, new BigDecimal("4.92"), 62));

        // 3. Medicated Brahmi Ghrita
        products.add(buildProduct("Medicated Brahmi Ghrita", "Brāhmī Ghṛta (ब्राह्मी घृत)", "medicated-brahmi-ghrita",
                ProductCategory.GHRITA, DoshaType.PITTA,
                "Pure A2 Vedic cow ghee infused with Bacopa monnieri, Shankhpushpi, and Vacha for supreme memory and mental focus.",
                "A supreme Medhya Rasayana prepared in authentic grass-fed A2 Gir cow ghee. Crosses the blood-brain barrier to nourish neural tissues.",
                "Ashtanga Hridaya, Uttarasthana 6/23",
                "ब्राह्मी घृतं स्मृतिकरं मेध्यमायुष्यमुत्तमम्। (Brahmi Ghrita enhances memory, sharpens intellect, and confers longevity.)",
                "Vedic A2 Ghee, Brahmi Swarasa, Shankhpushpi, Vacha, Kushtha, Pippali, Vidanga",
                "Enhances cognitive recall, reduces mental burnout, fosters emotional equilibrium",
                "1 teaspoon (5–10g) in morning on empty stomach", "Warm milk or warm water", "High fever with Ama, acute indigestion",
                new BigDecimal("550.00"), new BigDecimal("475.00"), 75, "AYUR-BRG-200G", "200g",
                "https://images.unsplash.com/photo-1540420773420-3366772f4999?auto=format&fit=crop&w=800&q=80", true, new BigDecimal("4.98"), 47));

        // 4. Classical Yograj Guggulu
        products.add(buildProduct("Classical Yograj Guggulu", "Yogarāja Guggulu (योगराज गुग्गुलु)", "classical-yograj-guggulu",
                ProductCategory.GUGGULU, DoshaType.VATA,
                "Classical Ayurvedic resin tablets for joint comfort, cartilage rejuvenation, and neuromuscular flexibility.",
                "A venerated formulation combining purified Guggulu with 28 classical herbs to clear toxic Ama from joints and ease stiffness.",
                "Bhaishajya Ratnavali, Amavata Rogadhikara",
                "योगराजो महाश्रेष्ठः सर्ववातविकारनुत्। (Yograj Guggulu is the foremost remedy for all severe Vata and joint ailments.)",
                "Shuddha Guggulu, Triphala, Trikatu, Chavya, Pippalimoola, Chitrak, Ajwain, Rasna, Gokshura",
                "Relieves joint pain & stiffness, reduces morning swelling, nourishes synovial fluid",
                "2 tablets twice daily after meals", "Warm water or Dashamoola decoction", "Pregnancy, active bleeding disorders, severe liver dysfunction",
                new BigDecimal("280.00"), new BigDecimal("240.00"), 120, "AYUR-YGJ-80TAB", "80 Tablets",
                "https://images.unsplash.com/photo-1509358271058-acd22cc93898?auto=format&fit=crop&w=800&q=80", true, new BigDecimal("4.89"), 53));

        // 5. Saffron Kumkumadi Tailam
        products.add(buildProduct("Saffron Kumkumadi Tailam", "Kuṅkumādi Tailam (कुङ्कुमादि तैलम्)", "saffron-kumkumadi-tailam",
                ProductCategory.TAILA, DoshaType.TRIDOSHIC,
                "Miraculous classical facial beauty nectar formulated with Kashmiri Saffron, Sandalwood, and Blue Lotus for radiant glow.",
                "Classical Ayurvedic facial elixir referenced in Ashtanga Hridaya. Imparts natural golden luminescence and fades blemishes.",
                "Ashtanga Hridaya, Uttarasthana 13/36",
                "कुङ्कुमाद्यमिदं तैलं मुखकान्तिकरं परम्। (Kumkumadi Tailam is the supreme oil for facial radiance and youthfulness.)",
                "Pure Kashmiri Kesar (Crocus sativus), Chandana, Manjistha, Yashtimadhu, Padmaka, Ushira, Sesame Oil, Goat Milk",
                "Brightens complexion, fades hyperpigmentation, smooths fine lines, balances skin oiliness",
                "3–5 drops gently massaged onto cleansed face at night", "Topical application only", "Extremely oily or cystic acne prone skin during active flare-up",
                new BigDecimal("1200.00"), new BigDecimal("999.00"), 60, "AYUR-KKT-30ML", "30ml",
                "https://images.unsplash.com/photo-1515377905703-c4788e51af15?auto=format&fit=crop&w=800&q=80", true, new BigDecimal("4.96"), 91));

        // 6. Authentic Chyawanprash Rasayana
        products.add(buildProduct("Authentic Chyawanprash Rasayana", "Cyavanaprāśa (च्यवनप्राश)", "authentic-chyawanprash-rasayana",
                ProductCategory.RASAYANA, DoshaType.TRIDOSHIC,
                "Ancient longevity jam prepared with wild forest Amalaki, pure honey, cow ghee, and 48 rare Himalayan botanical roots.",
                "The original rejuvenating superfood of Sage Chyawana. Fosters innate vitality (Ojas), respiratory defense, and cellular longevity.",
                "Charaka Samhita, Chikitsa Sthana 1/1",
                "च्यवनप्राशः परं मेध्यं स्मृतिकरं बलवर्धनम्। (Chyawanprash is the ultimate promoter of intellect, memory, and strength.)",
                "Fresh Wild Amla, A2 Cow Ghee, Sesame Oil, Honey, Dashamoola, Ashwagandha, Shatavari, Cardamom, Pippali",
                "Supercharges immune defense, strengthens lung capacity, rejuvenates cellular tissues",
                "1 tablespoon (12–15g) daily morning on empty stomach", "Warm organic milk or warm water", "Uncontrolled diabetes mellitus (consult physician)",
                new BigDecimal("490.00"), new BigDecimal("420.00"), 110, "AYUR-CYP-500G", "500g",
                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?auto=format&fit=crop&w=800&q=80", true, new BigDecimal("4.94"), 118));

        // 7. Classical Sitopaladi Churna
        products.add(buildProduct("Classical Sitopaladi Churna", "Sitopalādi Cūrṇa (सितोपलादि चूर्ण)", "classical-sitopaladi-churna",
                ProductCategory.CHURNA, DoshaType.KAPHA,
                "Classical sweet cooling Ayurvedic powder for bronchial comfort, cough soothing, and seasonal respiratory defense.",
                "Revered classical formulation for clearing bronchial congestion, soothing throat irritation, and supporting lung resilience.",
                "Sharangadhara Samhita, Madhyama Khanda 6/134",
                "सितोपलादि चूर्णं तु कासश्वासज्वरापहम्। (Sitopaladi Churna eradicates cough, dyspnea, burning sensation, and fever.)",
                "Sitopala (Cane Sugar Candy), Vamshalochana (Bamboo Manna), Pippali (Long Pepper), Elaichi (Cardamom), Twak (Cinnamon)",
                "Relieves dry and productive cough, soothes burning sensations, supports clear breathing",
                "2–3 grams thrice daily", "Warm honey or pure cow ghee", "High blood glucose, diabetes",
                new BigDecimal("190.00"), new BigDecimal("160.00"), 140, "AYUR-STP-60G", "60g",
                "https://images.unsplash.com/photo-1563178406-4cdc2923acbc?auto=format&fit=crop&w=800&q=80", false, new BigDecimal("4.91"), 43));

        // 8. Pure Himalayan Shilajit Resin
        products.add(buildProduct("Pure Himalayan Shilajit Resin (Gold Grade)", "Śuddha Śilājatu (शुद्ध शिलाजीत)", "pure-himalayan-shilajit-resin",
                ProductCategory.RASAYANA, DoshaType.VATA,
                "100% pure fulvic acid rich Himalayan exudate purified through classical Triphala decoction washing for peak vitality.",
                "Authentic mountain pitch harvest from 16,000+ ft altitude in the Himalayas. Purified with Triphala decoction (Shodhana).",
                "Charaka Samhita, Chikitsa Sthana 1/3",
                "न सोऽस्ति रोगो भुवि यन्न शमयेच्छीघ्रं शिलाजतु। (There is no curable disease on earth that cannot be conquered by genuine Shilajit.)",
                "100% Purified Himalayan Shilajit (min 75% Fulvic Acid, 84+ ionic trace minerals)",
                "Boosts mitochondrial ATP energy, elevates testosterone and stamina, improves cognitive clarity",
                "Pea-sized amount (300–500mg) dissolved in warm beverage", "Warm milk, green tea, or warm water", "Uric acid calculi (gout flare-up), active iron overload (hemochromatosis)",
                new BigDecimal("1450.00"), new BigDecimal("1199.00"), 80, "AYUR-SHL-20G", "20g",
                "https://images.unsplash.com/photo-1615485290382-441e4d049cb5?auto=format&fit=crop&w=800&q=80", true, new BigDecimal("4.99"), 156));

        // 9. Classical Mahanarayan Taila
        products.add(buildProduct("Classical Mahanarayan Taila", "Mahānārāyaṇa Taila (महानारायण तैल)", "classical-mahanarayan-taila",
                ProductCategory.TAILA, DoshaType.VATA,
                "Royal herbal massage oil processed with 52 Ayurvedic botanicals and Dashamoola for joint, tendon, and muscular relief.",
                "Supreme classical medicated oil for calming aggravated Vata in muscles, tendons, and joints (Abhyanga).",
                "Bhaishajya Ratnavali, Vata Vyadhi Rogadhikara",
                "महानारायणं तैलं सर्ववातविकारजित्। (Mahanarayan Taila conquers all painful neuromuscular Vata conditions.)",
                "Sesame Oil, Dashamoola, Ashwagandha, Bala, Shatavari, Rasna, Cow Milk, Camphor",
                "Eases lower back stiffness, relieves muscle spasms, nourishes dry connective tissues",
                "Warm oil gently and massage affected joints for 15 minutes", "External body massage (Abhyanga)", "Open wounds, acute inflamed fever",
                new BigDecimal("380.00"), new BigDecimal("320.00"), 105, "AYUR-MNT-200ML", "200ml",
                "https://images.unsplash.com/photo-1540420773420-3366772f4999?auto=format&fit=crop&w=800&q=80", true, new BigDecimal("4.93"), 67));

        // 10. Classical Avipattikar Churna
        products.add(buildProduct("Classical Avipattikar Churna", "Avipattikara Cūrṇa (अविपत्तिकर चूर्ण)", "classical-avipattikar-churna",
                ProductCategory.CHURNA, DoshaType.PITTA,
                "Classical digestive formula for balancing hyperacidity, acid reflux, heartburn, and digestive burning sensations.",
                "Formulated with Trikatu, Triphala, Musta, Vidanga, Elaichi, and Nishoth to balance excessive digestive fire (Tikshnagni).",
                "Bhaishajya Ratnavali, Amlapitta Rogadhikara",
                "अविपत्तिकरं चूर्णं पित्तरोगापहं परम्। (Avipattikar Churna is the preeminent remedy for hyperacidity and Pitta burning.)",
                "Trikatu, Triphala, Musta, Vidanga, Ela, Tvak, Patra, Lavanga, Nishoth, Sharkara",
                "Neutralizes stomach acid, soothes esophageal burning, relieves sour belching",
                "3–5 grams twice daily before or after meals", "Cool or lukewarm water, or coconut water", "Severe diarrhea, chronic ulcerative bleeding",
                new BigDecimal("210.00"), new BigDecimal("175.00"), 130, "AYUR-AVP-100G", "100g",
                "https://images.unsplash.com/photo-1509358271058-acd22cc93898?auto=format&fit=crop&w=800&q=80", false, new BigDecimal("4.88"), 39));

        // 11. Dashamoolarishta Rejuvenation Elixir
        products.add(buildProduct("Dashamoolarishta Classical Elixir", "Daśamūlāriṣṭa (दशमूलारिष्ट)", "dashamoolarishta-elixir",
                ProductCategory.ARISHTA, DoshaType.VATA,
                "Venerable classical restorative tonic brewed with the Ten Sacred Roots for fatigue, post-delivery rejuvenation, and stamina.",
                "Comprehensive classical formulation featuring Bilva, Agnimantha, Shyonaka, Patala, Gambhari, and 5 small roots for deep vitality.",
                "Sharangadhara Samhita, Madhyama Khanda 10",
                "दशमूलारिष्टः श्रेष्ठः सूतिकारोगनाशनः। (Dashamoolarishta is the preeminent restorative for postpartum strength and deep debility.)",
                "Ten Sacred Roots (Dashamoola), Dhataki, Draksha, Ashwagandha, Triphala, Jaggery",
                "Combats physical fatigue, strengthens pelvic floor, rebuilds postpartum vitality",
                "15–25 ml twice daily after meals", "Equal quantity of warm water", "Hyperacidity during acute flare-up",
                new BigDecimal("360.00"), new BigDecimal("310.00"), 90, "AYUR-DMR-450ML", "450ml",
                "https://images.unsplash.com/photo-1598511726623-d2e9996892f0?auto=format&fit=crop&w=800&q=80", true, new BigDecimal("4.96"), 74));

        // 12. Kanchanar Guggulu
        products.add(buildProduct("Classical Kanchanar Guggulu", "Kāñcanāra Guggulu (काञ्चनार गुग्गुलु)", "classical-kanchanar-guggulu",
                ProductCategory.GUGGULU, DoshaType.KAPHA,
                "Classical Ayurvedic botanical tablet for lymphatic drainage, thyroid balance, and glandular wellness.",
                "Combines Mountain Ebony bark (Kanchanar) with purified Guggulu to decongest lymphatic tissue and support healthy thyroid function.",
                "Sharangadhara Samhita, Madhyama Khanda 7/95",
                "काञ्चनारो गलगण्डे गण्डमालासु चोच्यते। (Kanchanar Guggulu is specifically praised for glandular swellings, lymph nodes, and cysts.)",
                "Kanchanara Bark, Shuddha Guggulu, Triphala, Trikatu, Varuna, Elaichi, Twak, Tejpatra",
                "Decongests swollen glands, supports balanced thyroid hormones, clears lipomas and cysts",
                "2 tablets twice daily after food", "Warm water or Varunadi Kwatha", "Pregnancy, active bleeding disorders",
                new BigDecimal("290.00"), new BigDecimal("250.00"), 115, "AYUR-KCG-80TAB", "80 Tablets",
                "https://images.unsplash.com/photo-1515377905703-c4788e51af15?auto=format&fit=crop&w=800&q=80", true, new BigDecimal("4.91"), 58));

        // 13. Trikatu Churna
        products.add(buildProduct("Classical Trikatu Churna (The Three Pungents)", "Trikaṭu Cūrṇa (त्रिकटु चूर्ण)", "classical-trikatu-churna",
                ProductCategory.CHURNA, DoshaType.KAPHA,
                "The premier digestive fire ignite powder combining Black Pepper, Long Pepper, and Dry Ginger to clear sluggish metabolism.",
                "Potent bio-enhancer and metabolism igniter that eliminates deep seated Ama toxins and excess Kapha mucus.",
                "Charaka Samhita, Sutra Sthana 27",
                "दीपनीयः श्लेष्मघ्नः श्वासकासप्रणाशनः। (Trikatu kindles digestive Agni, conquers Kapha mucus, and clears cough and dyspnea.)",
                "Shunthi (Dry Ginger) 33.3%, Maricha (Black Pepper) 33.3%, Pippali (Long Pepper) 33.3%",
                "Ignites low digestive fire (Mandagni), speeds basal metabolism, burns visceral fat toxins",
                "1–2 grams with meals", "Raw honey or warm water", "Hyperacidity, active peptic ulcer, acute gastritis",
                new BigDecimal("180.00"), new BigDecimal("150.00"), 160, "AYUR-TKT-100G", "100g",
                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?auto=format&fit=crop&w=800&q=80", false, new BigDecimal("4.87"), 51));

        // 14. Ksheerabala Taila 101 Avarthi
        products.add(buildProduct("Ksheerabala Taila 101 Avarthi Drops", "Kṣīrabalā Taila (क्षीरबला तैल १०१)", "ksheerabala-101-drops",
                ProductCategory.TAILA, DoshaType.VATA,
                "Ultra-potent 101-times recycled classical neuro-rejuvenative oil for nervous insomnia, neuralgia, and chronic Vata.",
                "Prepared by cycling Country Mallow root (Bala), pure cow milk, and sesame oil 101 continuous times (Avarthanam).",
                "Ashtanga Hridaya, Chikitsa Sthana 22",
                "क्षीरबला तैलं शतं आवर्तितं परम्। (Ksheerabala 101 is the ultimate elixir for all neurological degenerative conditions.)",
                "Bala (Sida cordifolia), Vedic Cow Milk, Pure Cold-Pressed Sesame Oil",
                "Relieves sciatica & peripheral neuropathy, induces restorative sleep, calms central nervous system",
                "5–10 drops in warm milk at bedtime, or 2 drops per nostril as Nasya", "Warm milk or direct sublingual", "Acute phlegm fever",
                new BigDecimal("680.00"), new BigDecimal("590.00"), 50, "AYUR-KSB-25ML", "25ml",
                "https://images.unsplash.com/photo-1615485290382-441e4d049cb5?auto=format&fit=crop&w=800&q=80", true, new BigDecimal("4.98"), 38));

        // 15. Saraswatarishta
        products.add(buildProduct("Saraswatarishta with Gold (Swarna Yukta)", "Sārasvatāriṣṭa (सारस्वतारिष्ट)", "saraswatarishta-gold",
                ProductCategory.ARISHTA, DoshaType.PITTA,
                "Divine elixir for students, researchers, and professionals seeking superhuman intellect, speech clarity, and memory recall.",
                "Classical Ayurvedic fermented Medhya tonic processed with Brahmi, Shatavari, Vidari, Ashwagandha, and purified Gold calx.",
                "Bhaishajya Ratnavali, Rasayana Rogadhikara",
                "आयुष्यं वीर्यं मेधां च वाक्सिद्धिं कुरुते ध्रुवम्। (Saraswatarishta bestows longevity, mental vigor, memory, and eloquence in speech.)",
                "Brahmi, Shatavari, Vidarikanda, Haritaki, Ushira, Ginger, Cardamom, Dhataki, Honey, Purified Gold Foil",
                "Elevates concentration & IQ, enhances verbal eloquence, calms mental stress and exam anxiety",
                "15–20 ml twice daily after meals", "Equal quantity of lukewarm water", "None significant",
                new BigDecimal("480.00"), new BigDecimal("410.00"), 70, "AYUR-SRW-450ML", "450ml",
                "https://images.unsplash.com/photo-1540420773420-3366772f4999?auto=format&fit=crop&w=800&q=80", true, new BigDecimal("4.97"), 89));

        // 16. Chandraprabha Vati
        products.add(buildProduct("Classical Chandraprabha Vati", "Candraprabhā Vaṭī (चन्द्रप्रभा वटी)", "classical-chandraprabha-vati",
                ProductCategory.GUGGULU, DoshaType.TRIDOSHIC,
                "Translates to 'Glow of the Full Moon'; sovereign classical remedy for urinary tract vitality, kidney health, and stamina.",
                "Comprehensive formulation of 37 botanical herbs and minerals including Shilajit, Guggulu, and Camphor.",
                "Sharangadhara Samhita, Madhyama Khanda 7/40",
                "चन्द्रप्रभा सर्वदोषघ्नी प्रमेहविनाशिनी। (Chandraprabha dispels all doshic vitiations and conquers metabolic and urinary imbalances.)",
                "Shilajit, Guggulu, Karpura, Ativisha, Haridra, Vacha, Musta, Triphala, Chavya, Vidanga, Swarna Makshika",
                "Supports urinary tract wellness, reduces burning sensation, strengthens pelvic floor, clears uric acid crystals",
                "2 tablets twice daily with water or milk", "Warm water or Punarnavadi Kwatha", "Active hyperkalemia without doctor supervision",
                new BigDecimal("260.00"), new BigDecimal("225.00"), 135, "AYUR-CPV-80TAB", "80 Tablets",
                "https://images.unsplash.com/photo-1509358271058-acd22cc93898?auto=format&fit=crop&w=800&q=80", false, new BigDecimal("4.90"), 64));

        // 17. Mahatiktaka Ghrita
        products.add(buildProduct("Classical Mahatiktaka Ghrita", "Mahātiktaka Ghṛta (तिक्तक घृत)", "classical-mahatiktaka-ghrita",
                ProductCategory.GHRITA, DoshaType.PITTA,
                "Supreme bitter medicated ghee for inflammatory skin disorders, eczema, psoriasis, and deep liver cooling.",
                "Processed with 32 cooling and bitter herbs in pure cow ghee to detoxify Rakta (blood) and Pitta seated in the liver and skin.",
                "Ashtanga Hridaya, Chikitsa Sthana 19/8",
                "महातिक्तं परं कुष्ठपित्तरक्तज्वरापहम्। (Mahatiktaka Ghrita is the supreme conqueror of skin diseases, bleeding disorders, and Pitta fevers.)",
                "A2 Cow Ghee, Saptaparna, Nimba, Patola, Katuki, Brahmi, Chandana, Manjistha, Haridra, Triphala",
                "Clears stubborn eczema & psoriasis, soothes hot inflammatory skin, purifies blood and liver",
                "1–2 teaspoons on empty stomach in morning", "Warm water or lukewarm milk", "High acute fever, severe congestion",
                new BigDecimal("580.00"), new BigDecimal("499.00"), 65, "AYUR-MTG-200G", "200g",
                "https://images.unsplash.com/photo-1515377905703-c4788e51af15?auto=format&fit=crop&w=800&q=80", true, new BigDecimal("4.94"), 42));

        // 18. Anu Taila
        products.add(buildProduct("Classical Anu Taila (Nasal Drops)", "Aṇu Taila (अणु तैल)", "classical-anu-taila-nasya",
                ProductCategory.TAILA, DoshaType.KAPHA,
                "Classical Ayurvedic micro-penetrating nasal oil for sinus decongestion, headache relief, and sensory acuity.",
                "Formulated with 28 herbs processed in sesame oil and goat milk to cleanse and nourish the head and sense organs (Urdhva Jatrugata).",
                "Charaka Samhita, Sutra Sthana 5/56",
                "न तस्य चक्षुर्न घ्राणं न श्रोत्रमुपहिन्यते। (One who uses Anu Taila Nasya regularly never suffers deterioration of eyes, nose, or hearing.)",
                "Jivanti, Jala, Devadaru, Jalada, Tvak, Sevya, Gopi, Hima, Darvi, Madhuka, Sesame Oil, Goat Milk",
                "Clears chronic sinusitis, eases migraine & tension headaches, prevents premature greying and hair fall",
                "2–3 drops in each nostril every morning after face steam", "Nasal administration (Nasya)", "Immediately after food or during acute cold sneezing fit",
                new BigDecimal("170.00"), new BigDecimal("140.00"), 180, "AYUR-ANU-20ML", "20ml",
                "https://images.unsplash.com/photo-1598511726623-d2e9996892f0?auto=format&fit=crop&w=800&q=80", true, new BigDecimal("4.93"), 97));

        // 19. Draksharishta
        products.add(buildProduct("Classical Draksharishta (Grape Elixir)", "Drākṣāriṣṭa (द्राक्षारिष्ट)", "classical-draksharishta",
                ProductCategory.ARISHTA, DoshaType.PITA,
                "Nutritious fermented black grape tonic for chronic weakness, anemia, appetite stimulation, and gut health.",
                "Naturally fermented tonic prepared with high-grade Raisins (Draksha), Cinnamon, Cardamom, and Dhataki flowers.",
                "Sharangadhara Samhita, Madhyama Khanda 10",
                "द्राक्षारिष्टः परं बल्यो दीपनः श्वासकासनुत्। (Draksharishta bestows physical strength, kindles digestion, and relieves cough.)",
                "Black Raisins (Draksha), Dhataki Pushpa, Cinnamon, Cardamom, Tejpatra, Nagakeshara, Pippali, Jaggery",
                "Restores appetite, supports healthy hemoglobin, relieves chronic fatigue, eases dry cough",
                "15–25 ml twice daily after meals", "Equal quantity of water", "Severe hyperacidity without diluting",
                new BigDecimal("290.00"), new BigDecimal("250.00"), 100, "AYUR-DKR-450ML", "450ml",
                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?auto=format&fit=crop&w=800&q=80", false, new BigDecimal("4.89"), 45));

        // 20. Kaishore Guggulu
        products.add(buildProduct("Classical Kaishore Guggulu", "Kaiśora Guggulu (कैशोर गुग्गुलु)", "classical-kaishore-guggulu",
                ProductCategory.GUGGULU, DoshaType.PITTA,
                "Classical Ayurvedic formulation for uric acid balance, gouty arthritis (Vatarakta), and skin eruptions.",
                "Contains Guduchi, Triphala, Trikatu, and purified Guggulu to balance Pitta in blood and joints.",
                "Bhaishajya Ratnavali, Vatarakta Rogadhikara",
                "कैशोरो नाम गुग्गुलुः वातरक्तविनाशनः। (Kaishore Guggulu is the foremost conqueror of gout, uric acid, and blood toxicity.)",
                "Guduchi, Shuddha Guggulu, Haritaki, Bibhitaki, Amalaki, Shunthi, Maricha, Pippali, Vidanga, Trivrit",
                "Lowers elevated uric acid crystals, relieves toe & ankle pain, clears inflammatory acne",
                "2 tablets twice daily with water", "Warm water or Manjishtadi decoction", "Pregnancy, active diarrhea",
                new BigDecimal("270.00"), new BigDecimal("230.00"), 110, "AYUR-KSG-80TAB", "80 Tablets",
                "https://images.unsplash.com/photo-1563178406-4cdc2923acbc?auto=format&fit=crop&w=800&q=80", false, new BigDecimal("4.92"), 55));

        // 21. Bhringraj Medicated Hair Oil
        products.add(buildProduct("Pure Bhringraj Herbal Hair Oil", "Bhṛṅgarāja Taila (भृङ्गराज तैल)", "bhringraj-herbal-hair-oil",
                ProductCategory.TAILA, DoshaType.PITTA,
                "Vedic cold-pressed hair oil cooked with fresh Bhringraj juice, Amla, and Brahmi to stop hair fall and stimulate roots.",
                "Cooked via classical Sneha Paka with cold-pressed sesame and coconut oil to penetrate scalp follicles.",
                "Bhaishajya Ratnavali, Shirorogadhikara",
                "भृङ्गराजतैलं पलितखालित्यनाशनम्। (Bhringraj oil halts premature greying, baldness, and hair loss.)",
                "Bhringraj Swarasa, Amalaki, Brahmi, Manjistha, Padmaka, Sesame Oil, Coconut Oil, Nagakeshara",
                "Halts excessive hair thinning, stimulates dormant follicles, induces calming sleep",
                "Apply generously to scalp, massage for 10 minutes, leave overnight", "Scalp topical application", "None",
                new BigDecimal("390.00"), new BigDecimal("330.00"), 125, "AYUR-BRT-200ML", "200ml",
                "https://images.unsplash.com/photo-1540420773420-3366772f4999?auto=format&fit=crop&w=800&q=80", true, new BigDecimal("4.95"), 112));

        // 22. Amritarishta
        products.add(buildProduct("Classical Amritarishta (Giloy Elixir)", "Amṛtāriṣṭa (अमृतारिष्ट)", "classical-amritarishta",
                ProductCategory.ARISHTA, DoshaType.TRIDOSHIC,
                "Fermented Guduchi tonic for chronic intermittent pyrexia, post-infection immunity, and liver revitalization.",
                "Classical formulation powered by Heart-Leaved Moonseed (Guduchi) and Dashamoola to clear deep-seated chronic fevers.",
                "Bhaishajya Ratnavali, Jvara Rogadhikara",
                "अमृतारिष्टः सर्वज्वरान् हन्ति। (Amritarishta destroys all forms of chronic and acute fevers, strengthening immunity.)",
                "Guduchi (Tinospora cordifolia), Dashamoola, Ajwain, Parpata, Saptaparna, Dhataki, Jaggery",
                "Builds white blood cell defense, eliminates lingering post-viral fatigue, restores splenomegaly",
                "15–20 ml twice daily after food", "Equal volume of warm water", "None significant",
                new BigDecimal("310.00"), new BigDecimal("265.00"), 90, "AYUR-AMR-450ML", "450ml",
                "https://images.unsplash.com/photo-1615485290382-441e4d049cb5?auto=format&fit=crop&w=800&q=80", false, new BigDecimal("4.93"), 49));

        // 23. Bilvadi Churna
        products.add(buildProduct("Classical Bilvadi Churna", "Bilvādi Cūrṇa (बिल्वादि चूर्ण)", "classical-bilvadi-churna",
                ProductCategory.CHURNA, DoshaType.PITA,
                "Classical Bael fruit powder formulation for irritable bowel syndrome (IBS), chronic loose stools, and intestinal spasms.",
                "Combines unripe Bael fruit with digestive spices to bind loose bowel stools and soothe hyper-motility.",
                "Bhaishajya Ratnavali, Grahani Rogadhikara",
                "बिल्वादि चूर्णं ग्रहणीदोषनाशनम्। (Bilvadi Churna is the preeminent cure for Grahani syndrome and malabsorption.)",
                "Bilva Fruit Pulp, Mochrasa, Shunthi, Dhataki, Dhanyaka, Fennel, Cannabis (purified traces/classical)",
                "Restores normal stool consistency, halts chronic intestinal cramping, heals colon mucosa",
                "3–5 grams twice daily after meals", "Fresh buttermilk or warm water", "Severe dry constipation",
                new BigDecimal("210.00"), new BigDecimal("175.00"), 105, "AYUR-BLV-100G", "100g",
                "https://images.unsplash.com/photo-1509358271058-acd22cc93898?auto=format&fit=crop&w=800&q=80", false, new BigDecimal("4.89"), 37));

        // 24. Shatavari Kalpa
        products.add(buildProduct("Classical Shatavari Kalpa", "Śatāvarī Kalpa (शतावरी कल्प)", "classical-shatavari-kalpa",
                ProductCategory.RASAYANA, DoshaType.PITA,
                "Sweet nourishing granules prepared with Shatavari, Cardamom, and Saffron for female hormonal balance and lactation.",
                "Classical Ayurvedic confection for nurturing Ojas, supporting lactation, and relieving menstrual irritability.",
                "Ayurvedic Formulary of India",
                "शतावरी कल्पः स्तन्यजननो बलवर्धनः। (Shatavari Kalpa fosters copious breastmilk, physical stamina, and reproductive youth.)",
                "Shatavari Extract, Elaichi (Cardamom), Kesar (Saffron), Sharkara (Cane Sugar)",
                "Promotes breastmilk production, balances female hormones, calms hot flashes and PMS",
                "1–2 teaspoons twice daily", "Warm milk or warm water", "Uncontrolled diabetes mellitus",
                new BigDecimal("320.00"), new BigDecimal("270.00"), 120, "AYUR-STK-200G", "200g",
                "https://images.unsplash.com/photo-1598511726623-d2e9996892f0?auto=format&fit=crop&w=800&q=80", true, new BigDecimal("4.94"), 65));

        // 25. Organic Tulsi Infusion
        products.add(buildProduct("Organic Tulsi Holy Basil Herbal Infusion", "Tulasī Cāya (तुलसी पेय)", "organic-tulsi-infusion",
                ProductCategory.HERBAL_TEA, DoshaType.KAPHA,
                "Handpicked whole-leaf Krishna, Rama, and Vana Tulsi infused with ginger and cardamom for daily immune resilience.",
                "Certified organic trio of holy basil species offering deep adaptogenic stress relief and respiratory clarity.",
                "Classical Ayurvedic Herbal Practice",
                "तुलसी सर्वदा सेव्या रोगघ्नी पापनाशिनी। (Tulsi should be enjoyed daily to dispel illnesses and purify mind and body.)",
                "Rama Tulsi (35%), Krishna Tulsi (35%), Vana Tulsi (20%), Ginger (5%), Cardamom (5%)",
                "Daily antioxidant defense, eases seasonal allergies, calms nervous tension",
                "1 tea bag or 2g loose leaves steeped in boiling water for 5 minutes", "Drink warm as herbal tea", "None",
                new BigDecimal("220.00"), new BigDecimal("185.00"), 150, "AYUR-TLS-50TB", "50 Tea Bags",
                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?auto=format&fit=crop&w=800&q=80", false, new BigDecimal("4.91"), 59));

        productRepository.saveAll(products);
        log.info("ProductDataSeeder: Successfully seeded {} classical Ayurvedic pharmacy products.", products.size());
    }

    private Product buildProduct(String name, String sanskritName, String slug, ProductCategory category,
                                 DoshaType targetDosha, String shortDesc, String fullDesc, String classicalRef,
                                 String shloka, String ingredients, String benefits, String dosage,
                                 String anupana, String contra, BigDecimal price, BigDecimal discountPrice,
                                 int stock, String sku, String netWeight, String imageUrl, boolean featured,
                                 BigDecimal rating, int reviews) {
        return Product.builder()
                .name(name)
                .sanskritName(sanskritName)
                .slug(slug)
                .category(category)
                .targetDosha(targetDosha)
                .shortDescription(shortDesc)
                .fullDescription(fullDesc)
                .classicalReference(classicalRef)
                .shlokaText(shloka)
                .ingredients(ingredients)
                .benefits(benefits)
                .dosageInstructions(dosage)
                .anupanaCarrier(anupana)
                .contraindications(contra)
                .price(price)
                .discountPrice(discountPrice)
                .stockQuantity(stock)
                .sku(sku)
                .netWeight(netWeight)
                .imageUrl(imageUrl)
                .isFeatured(featured)
                .isAyushCertified(true)
                .rating(rating)
                .reviewCount(reviews)
                .build();
    }
}
