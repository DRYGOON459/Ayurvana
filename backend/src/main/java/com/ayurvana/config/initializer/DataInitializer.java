package com.ayurvana.config.initializer;

import com.ayurvana.model.entity.*;
import com.ayurvana.model.enums.*;
import com.ayurvana.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

        private final RoleRepository roleRepository;
        private final HerbRepository herbRepository;
        private final YogaPoseRepository yogaPoseRepository;
        private final MeditationRepository meditationRepository;
        private final AyurvedaTopicRepository topicRepository;
        private final ArticleRepository articleRepository;
        private final UserRepository userRepository;
        private final DoctorProfileRepository doctorProfileRepository;
        private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

        @Override
        @Transactional
        public void run(String... args) {
                log.info("Starting AyurVana enterprise clinical data initialization...");
                seedRoles();
                seedUsers();
                seedDoctors();
                seedHerbs();
                seedYogaPoses();
                seedMeditations();
                seedAyurvedaTopics();
                seedArticles();
                log.info("AyurVana database populated with comprehensive Ayurvedic clinical encyclopedia.");
        }

        private void seedRoles() {
                if (roleRepository.count() == 0) {
                        for (RoleName roleName : RoleName.values()) {
                                roleRepository.save(Role.builder().name(roleName).build());
                        }
                        log.info("Seeded default roles: ROLE_USER, ROLE_DOCTOR, ROLE_ADMIN");
                }
        }

        private void seedUsers() {
                if (userRepository.count() == 0) {
                        Role userRole = roleRepository.findByName(RoleName.ROLE_USER).orElse(null);
                        Role doctorRole = roleRepository.findByName(RoleName.ROLE_DOCTOR).orElse(null);
                        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN).orElse(null);

                        // 1. Regular User
                        User regularUser = User.builder()
                                        .fullName("Rohan Verma")
                                        .email("user@ayurvana.com")
                                        .passwordHash(passwordEncoder.encode("password123"))
                                        .phoneNumber("+91 98765 43210")
                                        .roles(Set.of(userRole))
                                        .active(true)
                                        .build();
                        userRepository.save(regularUser);

                        // 2. Demo Doctor (Aarav Sharma)
                        User doctorUser = User.builder()
                                        .fullName("Dr. Aarav Sharma")
                                        .email("doctor@ayurvana.com")
                                        .passwordHash(passwordEncoder.encode("password123"))
                                        .phoneNumber("+91 98111 22334")
                                        .roles(Set.of(userRole, doctorRole))
                                        .active(true)
                                        .build();
                        userRepository.save(doctorUser);

                        // 3. Admin User
                        User adminUser = User.builder()
                                        .fullName("AyurVana Administrator")
                                        .email("admin@ayurvana.com")
                                        .passwordHash(passwordEncoder.encode("admin123"))
                                        .phoneNumber("+91 99999 00000")
                                        .roles(Set.of(userRole, adminRole))
                                        .active(true)
                                        .build();
                        userRepository.save(adminUser);

                        log.info("Seeded default users: user@ayurvana.com, doctor@ayurvana.com, admin@ayurvana.com");
                }
        }

        private void seedDoctors() {
                if (doctorProfileRepository.count() == 0) {
                        Role userRole = roleRepository.findByName(RoleName.ROLE_USER).orElse(null);
                        Role doctorRole = roleRepository.findByName(RoleName.ROLE_DOCTOR).orElse(null);

                        // Doctor 1: Dr. Aarav Sharma (Kayachikitsa)
                        userRepository.findByEmail("doctor@ayurvana.com").ifPresent(doctorUser -> {
                                DoctorProfile doc1 = DoctorProfile.builder()
                                                .user(doctorUser)
                                                .qualifications("BAMS (Univ of Delhi), MD (Ayurveda - Kayachikitsa)")
                                                .medicalLicenseNumber("MCIM-AYUR-2015-8492")
                                                .stateMedicalCouncil("Maharashtra Council of Indian Medicine")
                                                .yearsOfExperience(12)
                                                .primarySpecialization("Kayachikitsa (Internal Medicine & Metabolic Health)")
                                                .languagesSpoken("English, Hindi, Sanskrit")
                                                .consultationFee(new BigDecimal("800.00"))
                                                .bio("Dr. Aarav Sharma is a senior Ayurvedic physician specializing in chronic lifestyle disorders, diabetes management, autoimmune balance, and classical gut restoration. With over a decade of clinical experience, he integrates traditional pulse diagnosis (Nadi Pariksha) with modern laboratory evaluations.")
                                                .verified(true)
                                                .verificationStatus(VerificationStatus.APPROVED)
                                                .averageRating(new BigDecimal("4.92"))
                                                .totalReviews(48)
                                                .build();
                                doctorProfileRepository.save(doc1);
                        });

                        // Doctor 2: Dr. Priyadarshini Iyer (Panchakarma)
                        User doc2User = User.builder()
                                        .fullName("Dr. Priyadarshini Iyer")
                                        .email("priyadarshini@ayurvana.com")
                                        .passwordHash(passwordEncoder.encode("password123"))
                                        .phoneNumber("+91 98222 33445")
                                        .roles(Set.of(userRole, doctorRole))
                                        .active(true)
                                        .build();
                        doc2User = userRepository.save(doc2User);

                        DoctorProfile doc2 = DoctorProfile.builder()
                                        .user(doc2User)
                                        .qualifications("BAMS, MS (Ayurveda), PGD Yoga Therapy (NIMHANS)")
                                        .medicalLicenseNumber("TNMC-AYUR-2018-3190")
                                        .stateMedicalCouncil("Tamil Nadu Board of Indian Medicine")
                                        .yearsOfExperience(8)
                                        .primarySpecialization("Panchakarma (Detoxification & Gut Health)")
                                        .languagesSpoken("English, Tamil, Hindi")
                                        .consultationFee(new BigDecimal("750.00"))
                                        .bio("Dr. Priyadarshini Iyer focuses on clinical detoxification, neuro-muscular rejuvenation, and women's hormonal health. She has guided hundreds of patients through authentic seasonal Panchakarma and Dinacharya lifestyle optimization.")
                                        .verified(true)
                                        .verificationStatus(VerificationStatus.APPROVED)
                                        .averageRating(new BigDecimal("4.88"))
                                        .totalReviews(32)
                                        .build();
                        doctorProfileRepository.save(doc2);

                        // Doctor 3: Dr. Rajeshwar Nambiar (Rasayana)
                        User doc3User = User.builder()
                                        .fullName("Dr. Rajeshwar Nambiar")
                                        .email("rajeshwar@ayurvana.com")
                                        .passwordHash(passwordEncoder.encode("password123"))
                                        .phoneNumber("+91 94444 55667")
                                        .roles(Set.of(userRole, doctorRole))
                                        .active(true)
                                        .build();
                        doc3User = userRepository.save(doc3User);

                        DoctorProfile doc3 = DoctorProfile.builder()
                                        .user(doc3User)
                                        .qualifications("BAMS (Kerala Univ), MD (Dravyaguna Vijnana - Pharmacology)")
                                        .medicalLicenseNumber("TCAM-AYUR-2010-1094")
                                        .stateMedicalCouncil("Travancore-Cochin Council of Indigenous Medicine")
                                        .yearsOfExperience(15)
                                        .primarySpecialization("Rasayana Chikitsa (Geriatrics, Immunity & Rejuvenation)")
                                        .languagesSpoken("English, Malayalam, Hindi, Sanskrit")
                                        .consultationFee(new BigDecimal("900.00"))
                                        .bio("Hailing from an authentic lineage of Kerala Ayurvedic Vaidyas, Dr. Nambiar specializes in classical herbal pharmacology (Dravyaguna), geriatric rejuvenation, degenerative joint care, and Rasayana therapy.")
                                        .verified(true)
                                        .verificationStatus(VerificationStatus.APPROVED)
                                        .averageRating(new BigDecimal("4.96"))
                                        .totalReviews(64)
                                        .build();
                        doctorProfileRepository.save(doc3);

                        // Doctor 4: Dr. Ananya Mukherjee (Prasuti Tantra)
                        User doc4User = User.builder()
                                        .fullName("Dr. Ananya Mukherjee")
                                        .email("ananya@ayurvana.com")
                                        .passwordHash(passwordEncoder.encode("password123"))
                                        .phoneNumber("+91 98300 11223")
                                        .roles(Set.of(userRole, doctorRole))
                                        .active(true)
                                        .build();
                        doc4User = userRepository.save(doc4User);

                        DoctorProfile doc4 = DoctorProfile.builder()
                                        .user(doc4User)
                                        .qualifications("BAMS, MD (Prasuti Tantra & Stri Roga)")
                                        .medicalLicenseNumber("WBMC-AYUR-2016-5521")
                                        .stateMedicalCouncil("Paschim Banga Ayurved Parishad")
                                        .yearsOfExperience(10)
                                        .primarySpecialization("Prasuti Tantra (Women's Health, Fertility & PCOS)")
                                        .languagesSpoken("English, Bengali, Hindi")
                                        .consultationFee(new BigDecimal("850.00"))
                                        .bio("Dr. Ananya Mukherjee is a renowned practitioner in Ayurvedic gynecology, preconception care (Garbhadhana Samskara), menstrual disorders, and holistic hormonal harmony through herbal Rasayanas and yoga.")
                                        .verified(true)
                                        .verificationStatus(VerificationStatus.APPROVED)
                                        .averageRating(new BigDecimal("4.90"))
                                        .totalReviews(41)
                                        .build();
                        doctorProfileRepository.save(doc4);

                        // Doctor 5: Dr. Vikramaditya Joshi (Shalakya Tantra - ENT & Ophthalmology)
                        User doc5User = User.builder()
                                        .fullName("Dr. Vikramaditya Joshi")
                                        .email("vikram@ayurvana.com")
                                        .passwordHash(passwordEncoder.encode("password123"))
                                        .phoneNumber("+91 98999 44332")
                                        .roles(Set.of(userRole, doctorRole))
                                        .active(true)
                                        .build();
                        doc5User = userRepository.save(doc5User);

                        DoctorProfile doc5 = DoctorProfile.builder()
                                        .user(doc5User)
                                        .qualifications("BAMS, MS (Shalakya Tantra - Ophthalmology & ENT)")
                                        .medicalLicenseNumber("GMC-AYUR-2014-9921")
                                        .stateMedicalCouncil("Gujarat Board of Ayurvedic & Unani Systems of Medicine")
                                        .yearsOfExperience(11)
                                        .primarySpecialization("Shalakya Tantra (Eye, Ear, Nose, Throat & Netra Kriya Kalpa)")
                                        .languagesSpoken("English, Gujarati, Hindi")
                                        .consultationFee(new BigDecimal("800.00"))
                                        .bio("Dr. Joshi specializes in classical ocular therapy (Netra Tarpana), chronic sinusitis management via Nasya, computer vision syndrome, and headache etiology.")
                                        .verified(true)
                                        .verificationStatus(VerificationStatus.APPROVED)
                                        .averageRating(new BigDecimal("4.89"))
                                        .totalReviews(29)
                                        .build();
                        doctorProfileRepository.save(doc5);

                        // Doctor 6: Dr. Meenakshi Sundaram (Kaumarbhritya - Pediatrics & Child Development)
                        User doc6User = User.builder()
                                        .fullName("Dr. Meenakshi Sundaram")
                                        .email("meenakshi@ayurvana.com")
                                        .passwordHash(passwordEncoder.encode("password123"))
                                        .phoneNumber("+91 98400 77889")
                                        .roles(Set.of(userRole, doctorRole))
                                        .active(true)
                                        .build();
                        doc6User = userRepository.save(doc6User);

                        DoctorProfile doc6 = DoctorProfile.builder()
                                        .user(doc6User)
                                        .qualifications("BAMS, MD (Kaumarbhritya - Pediatric Ayurveda & Immunity)")
                                        .medicalLicenseNumber("KMC-AYUR-2017-4402")
                                        .stateMedicalCouncil("Karnataka Ayurvedic & Unani Practitioners Board")
                                        .yearsOfExperience(9)
                                        .primarySpecialization("Kaumarbhritya (Pediatrics, Child Immunity & Suvarnaprashana)")
                                        .languagesSpoken("English, Kannada, Tamil, Hindi")
                                        .consultationFee(new BigDecimal("750.00"))
                                        .bio("Dr. Meenakshi Sundaram is dedicated to classical pediatric wellness, childhood respiratory allergies, digestive strength in growing infants, and gold-calx immunity drops (Suvarnaprashana).")
                                        .verified(true)
                                        .verificationStatus(VerificationStatus.APPROVED)
                                        .averageRating(new BigDecimal("4.94"))
                                        .totalReviews(53)
                                        .build();
                        doctorProfileRepository.save(doc6);

                        log.info("Seeded 6 certified Ayurvedic physicians covering internal medicine, panchakarma, rasayana, gynecology, ophthalmology, and pediatrics.");
                }
        }

        private void seedHerbs() {
                if (herbRepository.count() == 0) {
                        createHerb("Ashwagandha (Indian Ginseng)", "Aśvagandhā (अश्वगन्धा)", "Withania somnifera", "Solanaceae", "ashwagandha",
                                        "Tikta (Bitter), Kashaya (Astringent), Madhura (Sweet)", "Ushna (Heating)", "Madhura (Sweet)",
                                        "Balances Vata and Kapha; may aggravate Pitta in high doses", "Roots",
                                        "Ashwagandha is one of the most revered adaptogenic herbs in Ayurveda, celebrated for Rasayana (rejuvenative) and Medhya (cognitive) properties.",
                                        "Historically prescribed for physical debility, insomnia, nervous exhaustion, stress, and enhancing vitality (Ojas).",
                                        "Clinical trials indicate Withanolides in Ashwagandha reduce serum cortisol levels, attenuate perceived stress, and support restful sleep architecture.",
                                        "Powder (Churna): 3–6 grams daily with warm milk or ghee; Extract capsules: 300–600 mg standardized extract.",
                                        "/images/herbs/ashwagandha.jpg", ToxicityLevel.SAFE,
                                        "Hyperthyroidism, acute severe liver conditions, or concurrent heavy sedative medication without medical supervision.",
                                        "May enhance effects of barbiturates, benzodiazepines, and immunosuppressants.",
                                        "Contraindicated during pregnancy due to potential uterine stimulant properties.",
                                        "Gastrointestinal distress, nausea, diarrhea.", "Discontinue usage and hydrate.",
                                        "Charaka Samhita, Chikitsa Sthana (Rasayana Adhyaya)", "Indian J Psychol Med. 2012; 34(3):255–262");

                        createHerb("Holy Basil (Tulsi)", "Tulasī (तुलसी)", "Ocimum tenuiflorum", "Lamiaceae", "tulsi",
                                        "Katu (Pungent), Tikta (Bitter)", "Ushna (Heating)", "Katu (Pungent)",
                                        "Balances Kapha and Vata; may elevate Pitta if consumed excessively", "Leaves, Seeds",
                                        "Known as 'The Incomparable One' and 'Queen of Herbs', Tulsi is renowned for supporting respiratory clarity, immune balance, and mental calm.",
                                        "Traditional relief for bronchial congestion, cough, fever, digestive sluggishness, and environmental stress.",
                                        "Eugenol and rosmarinic acid constituents exhibit significant antimicrobial, antioxidant, and immunomodulatory activities.",
                                        "Infusion (Tea): 2–3 grams dried leaves steeped in boiling water twice daily; Fresh leaf juice: 5–10 ml.",
                                        "/images/herbs/tulsi.jpg", ToxicityLevel.SAFE,
                                        "Bleeding disorders or patients scheduled for surgery within 2 weeks due to mild anticoagulant potential.",
                                        "May potentiate antiplatelet and anticoagulant medications.",
                                        "Caution advised during pregnancy and breastfeeding; avoid high therapeutic doses.",
                                        "Gastric irritation, excessive perspiration.", "Reduce dosage or dilute with room-temperature water.",
                                        "Bhavaprakasha Nighantu, Pushpa Varga", "J Ayurveda Integr Med. 2014; 5(4):251–259");

                        createHerb("Neem (Indian Lilac)", "Nimba (निम्ब)", "Azadirachta indica", "Meliaceae", "neem",
                                        "Tikta (Bitter), Kashaya (Astringent)", "Sheeta (Cooling)", "Katu (Pungent)",
                                        "Balances Pitta and Kapha; increases Vata in excess", "Leaves, Bark, Seed Oil (Topical only)",
                                        "The quintessential Ayurvedic cooling and blood-purifying (Raktashodhaka) botanical.",
                                        "Skin disorders (Kushtha), inflammatory conditions, metabolic detox, oral hygiene.",
                                        "Azadirachtin and nimbin demonstrate proven broad-spectrum antibacterial and anti-inflammatory properties.",
                                        "Leaf powder: 1–3 grams daily; Bark decoction: 15–30 ml; Oil strictly for external application.",
                                        "/images/herbs/neem.jpg", ToxicityLevel.MILD_IRRITANT,
                                        "Neem seed oil is TOXIC if ingested orally, especially in children. Contraindicated in weakness or emaciation.",
                                        "May lower blood glucose; monitor if taking anti-diabetic medications.",
                                        "STRICTLY CONTRAINDICATED in pregnancy; historically used as an abortifacient.",
                                        "Hepatic/renal irritation from seed oil ingestion; excessive Vata aggravation.", "Immediate gastric lavage if seed oil ingested internally.",
                                        "Charaka Samhita, Sutrasthana Chapter 4", "Curr Med Chem. 2005; 12(4):467-476");

                        createHerb("Turmeric (Haridra)", "Haridrā (हरिद्रा)", "Curcuma longa", "Zingiberaceae", "turmeric",
                                        "Tikta (Bitter), Katu (Pungent)", "Ushna (Heating)", "Katu (Pungent)",
                                        "Tridoshic (balances all three doshas, especially Kapha and Pitta)", "Rhizome",
                                        "Golden spice of Ayurveda celebrated for immune modulation, joint comfort, and digestive fire.",
                                        "Wound healing, skin glow, liver detoxification, respiratory wellness, joint mobility.",
                                        "Curcuminoids provide potent NF-kB inhibition, systemic antioxidant defence, and cellular protection.",
                                        "Rhizome powder: 1–3 grams with pinch of black pepper (Piperine) and healthy fats.",
                                        "/images/herbs/turmeric.jpg", ToxicityLevel.SAFE,
                                        "Gallbladder obstruction or gallstones; active bleeding disorders.",
                                        "May potentiate blood-thinning and anticoagulant drugs.",
                                        "Safe in culinary amounts; avoid excessive therapeutic extracts during pregnancy.",
                                        "Acid reflux, upset stomach.", "Hydrate with cooling herbal teas (coriander/fennel).",
                                        "Sushruta Samhita, Sutrasthana Chapter 38", "Adv Exp Med Biol. 2007; 595:1-75");

                        createHerb("Brahmi (Water Hyssop)", "Brāhmī (ब्राह्मी)", "Bacopa monnieri", "Plantaginaceae", "brahmi",
                                        "Tikta (Bitter), Kashaya (Astringent)", "Sheeta (Cooling)", "Madhura (Sweet)",
                                        "Balances Vata and Pitta; clears excess Kapha from mind", "Whole Plant",
                                        "Premier Medhya Rasayana (nootropic brain tonic) in classical Ayurveda.",
                                        "Cognitive enhancement, memory retention, nervous system calming, speech clarity.",
                                        "Bacosides A and B facilitate synaptic transmission and support neuroplasticity.",
                                        "Powder: 2–3 grams with ghee; Fresh juice: 10–15 ml.",
                                        "/images/herbs/brahmi.jpg", ToxicityLevel.SAFE,
                                        "Severe bradycardia (low heart rate) or thyroid abnormalities without guidance.",
                                        "May interact with thyroid hormone therapies and anticholinergic drugs.",
                                        "Use only under supervision of a registered Ayurvedic physician.",
                                        "Nausea, intestinal cramps, dry mouth.", "Take with food or warm ghee.",
                                        "Charaka Samhita, Chikitsasthana Chapter 1", "Neuropsychopharmacology. 2002; 27(2):279-281");

                        createHerb("Giloy (Guduchi)", "Guḍūcī / Amṛtā (अमृता)", "Tinospora cordifolia", "Menispermaceae", "giloy",
                                        "Tikta (Bitter), Kashaya (Astringent)", "Ushna (Mildly Heating)", "Madhura (Sweet)",
                                        "Tridosha Shamaka (Balances all three doshas)", "Stem",
                                        "Referred to as 'Amrita' (Nectar of Immortality), Giloy is revered for immunomodulation and fever management.",
                                        "Recurrent fevers, metabolic imbalances, liver protection, joint inflammation.",
                                        "Cordifolioside and tinocordiside compounds activate macrophage phagocytosis.",
                                        "Stem Decoction (Kwath): 20–30 ml; Satva (Starch Extract): 500 mg–1 gram.",
                                        "/images/herbs/giloy.jpg", ToxicityLevel.SAFE,
                                        "Autoimmune flare-ups without clinical supervision; pre-existing chronic liver disease.",
                                        "May enhance anti-diabetic medications.",
                                        "Avoid in high doses during pregnancy.",
                                        "Constipation if taken without adequate fluids.", "Increase water intake and reduce dosage.",
                                        "Ashtanga Hridaya, Uttarasthana Chapter 39", "Phytomedicine. 2012; 19(8):727-734");

                        createHerb("Shatavari (Wild Asparagus)", "Śatāvarī (शतावरी)", "Asparagus racemosus", "Asparagaceae", "shatavari",
                                        "Madhura (Sweet), Tikta (Bitter)", "Sheeta (Cooling)", "Madhura (Sweet)",
                                        "Pacifies Pitta and Vata; may increase Kapha in large doses", "Roots / Tuberous Rhizomes",
                                        "Celebrated as the 'Queen of Herbs' for women's reproductive health, Shatavari is a deeply nourishing tonic that enhances Shukra Dhatu and Ojas.",
                                        "Hormonal balance, fertility enhancement, lactation support (Stanyajanana), menopause relief, and hyperacidity soothing.",
                                        "Steroidal saponins (Shatavarins I-IV) exert adaptogenic estrogen-modulating, antioxidant, and mucosal protective effects.",
                                        "Powder: 3–6 grams with warm milk and ghee; Liquid Extract: 5–10 ml.",
                                        "/images/herbs/shatavari.jpg", ToxicityLevel.SAFE,
                                        "Estrogen-sensitive breast or uterine pathologies; acute congestive pulmonary Kapha conditions.",
                                        "May potentiate diuretic medications.",
                                        "Safe and traditionally recommended during pregnancy and postpartum under Vaidya supervision.",
                                        "Heavy digestion, water retention.", "Combine with warming carminatives like cardamom or ginger.",
                                        "Charaka Samhita, Chikitsasthana Chapter 30", "Biomed Pharmacother. 2018; 103:1235-1249");

                        createHerb("Amla (Indian Gooseberry)", "Āmalakī / Dhātrī (आमलकी)", "Phyllanthus emblica", "Phyllanthaceae", "amla",
                                        "Amla (Sour), Kashaya (Astringent), Tikta (Bitter), Katu (Pungent), Madhura (Sweet)", "Sheeta (Cooling)", "Madhura (Sweet)",
                                        "Tridosha Shamaka (Balances all three doshas, especially Pitta)", "Fresh & Dried Fruit",
                                        "Amalaki is revered as the mother of all healing fruits ('Dhatri'), containing 5 of the 6 tastes and serving as the primary foundation of Chyawanprash and Triphala.",
                                        "Longevity, ocular health (Chakshushya), hair pigmentation, digestive fire strengthening without heating, liver support.",
                                        "Extremely rich in heat-stable Vitamin C bound to gallic acid and ellagitannins (Emblicanin A & B), offering profound cellular free-radical scavenging.",
                                        "Fresh fruit juice: 10–20 ml; Dried fruit powder: 3–6 grams with honey or water.",
                                        "/images/herbs/amla.jpg", ToxicityLevel.SAFE,
                                        "Excessive dryness in acute Vata diarrhea without soothing unctuous vehicles.",
                                        "May enhance iron absorption.",
                                        "Safe and highly beneficial throughout pregnancy and lactation.",
                                        "Mild loose stools due to high vitamin C content.", "Take with warm water or ghee.",
                                        "Charaka Samhita, Sutrasthana Chapter 27", "Food Chem. 2006; 97(3):448-457");

                        createHerb("Haritaki (Chebulic Myrobalan)", "Harītakī / Abhayā (हरीतकी)", "Terminalia chebula", "Combretaceae", "haritaki",
                                        "Kashaya (Astringent), Tikta (Bitter), Madhura (Sweet), Katu (Pungent), Amla (Sour)", "Ushna (Heating)", "Madhura (Sweet)",
                                        "Tridosha Shamaka (Supreme balancer of Vata, gently cleanses all doshas)", "Fruit Rind",
                                        "Revered in the Charaka Samhita as the 'King of Medicines' and compared to a mother who never harms her child.",
                                        "Gentle bowel regulation (Anulomana), gut microbiome restoration, cognitive longevity, wound healing, metabolic cleansing.",
                                        "Chebulinic acid and chebulagic acid show potent prokinetic, cardioprotective, and prebiotic activities.",
                                        "Powder: 3–5 grams at bedtime with seasonal Anupanas (salt in monsoon, sugar in autumn, ginger in winter).",
                                        "/images/herbs/haritaki.jpg", ToxicityLevel.SAFE,
                                        "Acute exhaustion, fasting, severe dehydration, or high pitta bleeding.",
                                        "None documented at standard therapeutic dosages.",
                                        "Contraindicated during pregnancy due to downward peristaltic (Apana) action.",
                                        "Excessive bowel movements, dehydration.", "Rehydrate with electrolyte fluids.",
                                        "Charaka Samhita, Chikitsasthana Chapter 1", "BMC Complement Altern Med. 2016; 16:106");

                        createHerb("Arjuna (White Marudah)", "Arjuna (अर्जुन)", "Terminalia arjuna", "Combretaceae", "arjuna",
                                        "Kashaya (Astringent), Tikta (Bitter)", "Sheeta (Cooling)", "Katu (Pungent)",
                                        "Pacifies Pitta and Kapha; does not aggravate Vata in formulation", "Stem Bark",
                                        "The premier Hridaya (cardiotonic) bark in classical Ayurveda, used for centuries to tone myocardial muscle and support arterial integrity.",
                                        "Hypertension management, ischemic heart disease support, cardiac rhythm regulation, fracture healing.",
                                        "Arjunolic acid and triterpenoid saponins demonstrate significant inotropic, antioxidant, and endothelial-protective properties.",
                                        "Bark decoction (Ksheerapaka boiled in milk): 15–30 ml; Powder: 3–6 grams.",
                                        "/images/herbs/arjuna.jpg", ToxicityLevel.SAFE,
                                        "Use under cardiology supervision if concurrently taking prescription cardiac glycosides or beta-blockers.",
                                        "May potentiate anti-hypertensive medications.",
                                        "Use only under supervision of an Ayurvedic physician.",
                                        "Constipation due to astringent bark tannins.", "Administer with warm milk or ghee.",
                                        "Chakradatta, Hridroga Chikitsa", "Int J Cardiol. 2007; 120(1):127-129");

                        createHerb("Manjistha (Indian Madder)", "Mañjiṣṭhā (मञ्जिष्ठा)", "Rubia cordifolia", "Rubiaceae", "manjistha",
                                        "Tikta (Bitter), Kashaya (Astringent), Madhura (Sweet)", "Ushna (Mildly Heating)", "Katu (Pungent)",
                                        "Pacifies Pitta and Kapha; purifies Rakta (blood)", "Roots and Stems",
                                        "The supreme lymphatic and blood purifier (Raktashodhaka) in Ayurvedic medicine, legendary for clearing deep cellular heat.",
                                        "Acne, eczema, psoriasis, hyperpigmentation, lymphatic congestion, liver detoxification, and menstrual regulation.",
                                        "Purpurin and munjistin anthraquinones exhibit profound anti-inflammatory, anti-tyrosinase (skin brightening), and antioxidant mechanisms.",
                                        "Powder: 1–3 grams daily with warm water or honey; Decoction: 20–40 ml.",
                                        "/images/herbs/manjistha.jpg", ToxicityLevel.SAFE,
                                        "Kidney stones of calcium oxalate origin (contains mild oxalates); active internal bleeding.",
                                        "May harmlessly tint urine slightly red or orange.",
                                        "Contraindicated during pregnancy due to mild emmenagogue properties.",
                                        "Excessive dryness, red urine coloration.", "Reassure that urine color change is benign; drink plenty of water.",
                                        "Charaka Samhita, Sutrasthana Chapter 4", "J Ethnopharmacol. 2011; 134(3):792-798");

                        createHerb("Bhringraj (False Daisy)", "Bhṛṅgarāja / Keśarāja (भृङ्गराज)", "Eclipta prostrata", "Asteraceae", "bhringraj",
                                        "Katu (Pungent), Tikta (Bitter)", "Ushna (Heating)", "Katu (Pungent)",
                                        "Balances Vata and Kapha; regulates Pitta through liver cooling", "Whole Plant, Leaves, Juice",
                                        "Known as the 'Ruler of the Hair' (Keshya), Bhringraj is a premier Rasayana for hair follicle nourishment, vision clarity, and hepatoprotection.",
                                        "Premature graying, hair thinning, liver cirrhosis, jaundice, restful sleep, and memory support.",
                                        "Wedelolactone and dimethylwedelolactone constituents stimulate anagen hair phase and exert robust hepatoprotective action.",
                                        "Fresh juice: 5–10 ml; Powder: 2–4 grams with honey; Oil (Mahabhringraj Taila) for scalp massage.",
                                        "/images/herbs/bhringraj.jpg", ToxicityLevel.SAFE,
                                        "Acute severe chills or low body temperature.",
                                        "None reported.",
                                        "Safe in topical oil application; internal use with Vaidya guidance.",
                                        "Heartburn or gastric irritation in high oral doses.", "Take with milk or cooling aloe vera juice.",
                                        "Sahasrayogam, Taila Yoga Prakarana", "J Ethnopharmacol. 2009; 124(3):450-456");

                        createHerb("Ginger (Shunthi)", "Śuṇṭhī / Viśvabheṣaja (शुण्ठी)", "Zingiber officinale", "Zingiberaceae", "ginger",
                                        "Katu (Pungent)", "Ushna (Heating)", "Madhura (Sweet)",
                                        "Pacifies Vata and Kapha; increases Pitta only if overused", "Rhizome (Fresh / Dried)",
                                        "Revered in classical Ayurveda as 'Vishwabheshaja'—the Universal Medicine. It kindles digestive fire (Deepana) without aggravating Pitta due to its sweet post-digestive effect (Madhura Vipaka).",
                                        "Indigestion, Ama detoxification, joint arthritis, nausea, bronchial congestion, and circulation enhancement.",
                                        "Gingerols and shogaols modulate 5-HT3 receptors, inhibit COX-2 prostaglandins, and enhance gastric motility.",
                                        "Dry powder: 1–2 grams before meals with rock salt; Fresh ginger tea: sliced fresh root steeped 10 minutes.",
                                        "/images/herbs/ginger.jpg", ToxicityLevel.SAFE,
                                        "Active bleeding peptic ulcers, high burning fever, or acute biliary colic.",
                                        "May enhance anticoagulants in large supplemental doses.",
                                        "Safe in culinary amounts; excellent for morning sickness in moderation.",
                                        "Heartburn, burning in stomach.", "Drink cold cow's milk or coconut water.",
                                        "Charaka Samhita, Sutrasthana Chapter 27", "Nutrients. 2020; 12(6):1573");

                        createHerb("Licorice (Yashtimadhu)", "Yaṣṭīmadhu / Madhuka (यष्टीमधु)", "Glycyrrhiza glabra", "Fabaceae", "licorice",
                                        "Madhura (Sweet), Tikta (Bitter)", "Sheeta (Cooling)", "Madhura (Sweet)",
                                        "Pacifies Vata and Pitta; increases Kapha in excess", "Roots and Stolons",
                                        "A premier sweet demulcent and Rasayana herb, celebrated for coating mucous membranes, soothing the vocal cords (Kanthya), and nourishing Ojas.",
                                        "Acid reflux, gastric ulcers, sore throat, bronchial inflammation, adrenal fatigue, and skin hyperpigmentation.",
                                        "Glycyrrhizin and glabridin provide potent anti-ulcer, anti-inflammatory, and mucosal protective activities.",
                                        "Powder: 2–4 grams with warm water or ghee; Decoction: 15–30 ml.",
                                        "/images/herbs/licorice.jpg", ToxicityLevel.SAFE,
                                        "Uncontrolled hypertension, severe kidney impairment, or congestive heart failure.",
                                        "May lower potassium; monitor if taking digoxin or loop diuretics.",
                                        "Avoid large therapeutic doses during pregnancy.",
                                        "Fluid retention, elevated blood pressure with excessive prolonged intake.", "Discontinue usage and consult physician.",
                                        "Sushruta Samhita, Sutrasthana Chapter 38", "Phytother Res. 2008; 22(6):709-717");

                        createHerb("Gotu Kola (Mandukaparni)", "Maṇḍūkaparṇī (मण्डूकपर्णी)", "Centella asiatica", "Apiaceae", "gotu-kola",
                                        "Tikta (Bitter), Kashaya (Astringent), Madhura (Sweet)", "Sheeta (Cooling)", "Madhura (Sweet)",
                                        "Pacifies Pitta and Kapha; clarifies Vata in nervous system", "Whole Plant, Leaves",
                                        "One of the four classical Medhya Rasayanas described by Charaka, famous for revitalizing neural pathways, skin connective tissue, and longevity.",
                                        "Memory sharpness, anxiety relief, venous insufficiency, wound collagen synthesis, and meditation enhancement.",
                                        "Asiaticoside and madecassoside triterpenes stimulate collagen type I synthesis and promote brain dendritic arborization.",
                                        "Fresh leaf juice (Swarasa): 10–15 ml; Dry powder: 2–3 grams with honey or ghee.",
                                        "/images/herbs/gotukola.jpg", ToxicityLevel.SAFE,
                                        "Known allergy to Apiaceae family.",
                                        "May enhance sedative medications.",
                                        "Avoid in high therapeutic doses during pregnancy.",
                                        "Mild headache, drowsiness.", "Rest and reduce dosage.",
                                        "Charaka Samhita, Chikitsasthana Chapter 1", "Evid Based Complement Alternat Med. 2012; 2012:348468");

                        createHerb("Long Pepper (Pippali)", "Pippalī / Māgadhī (पिप्पली)", "Piper longum", "Piperaceae", "pippali",
                                        "Katu (Pungent)", "Anushna (Mildly Warm)", "Madhura (Sweet)",
                                        "Pacifies Kapha and Vata; gentle on Pitta compared to black pepper", "Dried Infructescences (Spikes)",
                                        "A renowned Rasayana for the respiratory tract (Pranavaha Srotas) and a powerful bio-enhancer (Yogavahi) that dramatically increases nutrient absorption.",
                                        "Chronic cough, asthma, sluggish digestion, spleen disorders, and formulation bio-enhancement (Trikatu).",
                                        "Piperine in Pippali inhibits hepatic and intestinal glucuronidation, increasing bioavailability of co-administered herbs by up to 2000%.",
                                        "Powder: 500 mg–1.5 grams with honey (Vardhamana Pippali protocol).",
                                        "/images/herbs/pippali.jpg", ToxicityLevel.SAFE,
                                        "Acute gastritis, bleeding disorders, high Pitta inflammatory flare-ups.",
                                        "May increase absorption of drugs metabolized by CYP3A4.",
                                        "Avoid in high therapeutic doses during pregnancy.",
                                        "Throat burning, hyperacidity.", "Take with cold milk or ghee.",
                                        "Charaka Samhita, Chikitsasthana Chapter 1", "Planta Med. 1998; 64(4):353-356");

                        createHerb("Gokshura (Small Caltrops)", "Gokṣura / Śvadangṣṭrā (गोक्षुर)", "Tribulus terrestris", "Zygophyllaceae", "gokshura",
                                        "Madhura (Sweet), Tikta (Bitter)", "Sheeta (Cooling)", "Madhura (Sweet)",
                                        "Tridosha Shamaka (Relieves Vata and Pitta in urinary/reproductive system)", "Spiny Fruits & Roots",
                                        "Premier Rasayana for the urinary tract (Mutravaha Srotas) and reproductive vigor (Vrishya), famous for relieving dysuria and urinary stones.",
                                        "Kidney stones (Ashmari), benign prostatic hyperplasia, athletic endurance, male reproductive vitality, lower back stiffness.",
                                        "Protodioscin saponins stimulate nitric oxide release and modulate androgen receptors without hormonal disruption.",
                                        "Decoction: 30–50 ml; Fruit Powder: 3–6 grams with milk.",
                                        "/images/herbs/gokshura.jpg", ToxicityLevel.SAFE,
                                        "Severe acute kidney failure; dehydration.",
                                        "May enhance anti-hypertensive and diuretic medications.",
                                        "Contraindicated during pregnancy due to mild lithotriptic properties.",
                                        "Mild stomach upset.", "Take with meals.",
                                        "Charaka Samhita, Sutrasthana Chapter 4", "J Ethnopharmacol. 2002; 82(2-3):137-143");

                        createHerb("Punarnava (Spreading Hogweed)", "Punarnavā (पुनर्नवा)", "Boerhavia diffusa", "Nyctaginaceae", "punarnava",
                                        "Madhura (Sweet), Tikta (Bitter), Kashaya (Astringent)", "Ushna (Heating)", "Katu (Pungent)",
                                        "Pacifies all three doshas (Tridosha Shamaka), especially Kapha and Vata", "Whole Herb & Roots",
                                        "The name literally translates to 'That which renews the body again and again'. The supreme kidney protector and diuretic (Mutrala) in Ayurveda.",
                                        "Edema, fluid retention, chronic kidney disease, liver congestion, ascites, joint swelling.",
                                        "Punarnavine alkaloid and rotenoids stimulate renal cortical blood flow and demonstrate potent anti-fibrotic activity.",
                                        "Whole plant juice: 10–20 ml; Powder: 3–6 grams with warm water.",
                                        "/images/herbs/punarnava.jpg", ToxicityLevel.SAFE,
                                        "Severe hypokalemia without potassium monitoring.",
                                        "May potentiate pharmaceutical diuretics.",
                                        "Avoid in pregnancy without direct medical oversight.",
                                        "Frequent urination.", "Ensure adequate water and mineral intake.",
                                        "Charaka Samhita, Sutrasthana Chapter 4", "Phytomedicine. 2002; 9(7):663-666");

                        createHerb("Kutki (Hellebore)", "Kaṭukī / Tikta (कटुकी)", "Picrorhiza kurroa", "Plantaginaceae", "kutki",
                                        "Tikta (Intensely Bitter)", "Sheeta (Cooling)", "Katu (Pungent)",
                                        "Pacifies Pitta and Kapha; supreme cooling herb for liver fire", "Rhizome and Roots",
                                        "The premier Ayurvedic bitter hepatoprotective and cholagogue herb from the Himalayan heights, legendary for clearing liver heat.",
                                        "Fatty liver, hepatitis, elevated ALT/AST enzymes, chronic jaundice, autoimmune skin flare-ups, and sluggish bile secretion.",
                                        "Kutkin, picroside I and picroside II prevent lipid peroxidation and stimulate liver parenchyma regeneration.",
                                        "Root powder: 500 mg–1.5 grams before meals with honey or aloe vera.",
                                        "/images/herbs/kutki.jpg", ToxicityLevel.SAFE,
                                        "Severe acute watery diarrhea, severe Vata weakness.",
                                        "May lower blood glucose; monitor closely with oral anti-diabetics.",
                                        "Contraindicated in pregnancy due to purgative potential in high doses.",
                                        "Loose stools, mild abdominal cramping.", "Take with honey or ghee to moderate bitterness.",
                                        "Ashtanga Hridaya, Chikitsasthana Chapter 15", "Lancet. 1990; 336(8718):811");

                        createHerb("Guggulu (Indian Bdellium)", "Guggulu (गुग्गुलु)", "Commiphora mukul", "Burseraceae", "guggulu",
                                        "Tikta (Bitter), Kashaya (Astringent), Katu (Pungent), Madhura (Sweet)", "Ushna (Heating)", "Katu (Pungent)",
                                        "Pacifies Vata and Kapha; scrapes away metabolic plaque (Lekhaniya)", "Oleo-Gum Resin",
                                        "Celebrated in classical Ayurveda for its unique scraping action (Lekhana) that purges accumulated cholesterol, arterial lipids, and deep joint Ama.",
                                        "Hyperlipidemia, atherosclerosis, osteoarthritis, rheumatoid arthritis (Amavata), and obesity management (Medoroga).",
                                        "Guggulsterones E and Z antagonize the farnesoid X receptor (FXR) in liver cells, accelerating LDL cholesterol clearance.",
                                        "Purified resin (Shuddha Guggulu): 500 mg–1.5 grams daily with warm water or ginger decoction.",
                                        "/images/herbs/guggulu.jpg", ToxicityLevel.SAFE,
                                        "Active inflammatory bowel flare-ups; liver cirrhosis without clinical supervision.",
                                        "May interact with statins, blood thinners, and thyroid medications.",
                                        "Strictly contraindicated during pregnancy due to uterine contracting actions.",
                                        "Skin rash, gastric distress in high unpurified doses.", "Use only purified (Shuddha) Guggulu.",
                                        "Sushruta Samhita, Sutrasthana Chapter 15", "Science. 2002; 296(5576):2159-2162");

                        createHerb("Shankhpushpi (Speedwheel)", "Śaṅkhapuṣpī (शङ्खपुष्पी)", "Convolvulus pluricaulis", "Convolvulaceae", "shankhpushpi",
                                        "Tikta (Bitter), Kashaya (Astringent)", "Sheeta (Cooling)", "Madhura (Sweet)",
                                        "Tridosha Shamaka (Particularly calms agitated Pitta and Vata in the brain)", "Whole Plant & Flowers",
                                        "The supreme Medhya Rasayana for tranquilizing mental hyperactivity, anxiety, tension headaches, and insomnia.",
                                        "Exam stress, ADHD symptoms, chronic insomnia, hypertension, and cognitive longevity.",
                                        "Microphyllic acid and convolvine stimulate acetylcholine release and down-regulate thyroid hyperactivity.",
                                        "Whole plant powder: 3–6 grams with warm milk or Brahmi ghee at bedtime.",
                                        "/images/herbs/shankhpushpi.jpg", ToxicityLevel.SAFE,
                                        "Severe depression with extreme lethargy (excess Kapha-Tamas).",
                                        "May enhance anti-epileptic and sedative drugs.",
                                        "Safe during pregnancy under medical supervision.",
                                        "Drowsiness.", "Take at night before sleep.",
                                        "Charaka Samhita, Chikitsasthana Chapter 1", "Pharmacol Biochem Behav. 2003; 75(3):529-536");

                        createHerb("Jatamansi (Spikenard)", "Jaṭāmāṁsī (जटामांसी)", "Nardostachys jatamansi", "Caprifoliaceae", "jatamansi",
                                        "Tikta (Bitter), Kashaya (Astringent), Madhura (Sweet)", "Sheeta (Cooling)", "Katu (Pungent)",
                                        "Tridosha Shamaka (Profoundly pacifies Pitta and Vata in the central nervous system)", "Rhizomes and Roots",
                                        "A prized Himalayan aromatic root that cools the mind, induces deep physiological sleep, and stabilizes neurological rhythms.",
                                        "Insomnia, hysteria, cardiac arrhythmias, high stress, neuropsychiatric disorders, and tension alopecia.",
                                        "Jatamansone and sesquiterpenoids elevate brain GABA and serotonin levels without morning grogginess.",
                                        "Root powder: 1–3 grams with honey or warm milk.",
                                        "/images/herbs/jatamansi.jpg", ToxicityLevel.SAFE,
                                        "Extreme low blood pressure (hypotension).",
                                        "May potentiate sedatives and anesthetics.",
                                        "Avoid in pregnancy.",
                                        "Excessive sleepiness.", "Reduce dosage.",
                                        "Bhavaprakasha Nighantu, Karpiradi Varga", "J Ethnopharmacol. 2008; 116(1):154-159");

                        log.info("Seeded 22 comprehensive classical medicinal herbs with complete pharmacology, safety, and evidence.");
                }
        }

        private void createHerb(String commonName, String sanskritName, String scientificName, String botanicalFamily,
                                String slug, String rasa, String virya, String vipaka, String doshaEffect, String partsUsed,
                                String description, String traditionalUses, String modernResearch, String dosage,
                                String imageUrl, ToxicityLevel toxicity, String contraindications, String interactions,
                                String pregnancy, String overdose, String emergency, String refTitle, String citation) {

                Herb herb = Herb.builder()
                                .commonName(commonName)
                                .sanskritName(sanskritName)
                                .scientificName(scientificName)
                                .botanicalFamily(botanicalFamily)
                                .slug(slug)
                                .rasa(rasa)
                                .virya(virya)
                                .vipaka(vipaka)
                                .primaryDoshaEffect(doshaEffect)
                                .partsUsed(partsUsed)
                                .description(description)
                                .traditionalUses(traditionalUses)
                                .modernResearchSummary(modernResearch)
                                .dosageAndPreparations(dosage)
                                .imageUrl(imageUrl)
                                .published(true)
                                .build();

                PlantSafety safety = PlantSafety.builder()
                                .herb(herb)
                                .toxicityLevel(toxicity)
                                .contraindications(contraindications)
                                .interactionWarnings(interactions)
                                .pregnancySafety(pregnancy)
                                .overdoseSymptoms(overdose)
                                .emergencyAction(emergency)
                                .build();
                herb.setSafety(safety);

                PlantReference ref = PlantReference.builder()
                                .herb(herb)
                                .sourceTitle(refTitle)
                                .citation(citation)
                                .evidenceType(EvidenceType.TRADITIONAL_TEXT)
                                .build();
                herb.setReferences(List.of(ref));
                herbRepository.save(herb);
        }

        private void seedYogaPoses() {
                if (yogaPoseRepository.count() == 0) {
                        List<YogaPose> poses = List.of(
                                        createPose("Mountain Pose", "Tādāsana (ताडासन)", "tadasana", DifficultyLevel.BEGINNER, "Standing",
                                                        "Grounds Vata, aligns physical stability",
                                                        "Stand upright with feet together or hip-width apart. Engage thighs, draw navel gently inward, roll shoulders back, and lengthen crown towards sky. Breathe deeply.",
                                                        "Improves spinal posture, establishes grounding awareness, strengthens arches of feet and core stability.",
                                                        "Avoid prolonged standing if experiencing severe dizziness or migraine.", "/images/yoga/tadasana.jpg", 60),

                                        createPose("Tree Pose", "Vṛkṣāsana (वृक्षासन)", "vrikshasana", DifficultyLevel.BEGINNER, "Balance",
                                                        "Balances Vata through neuromuscular equilibrium",
                                                        "Shift weight onto standing leg. Place sole of other foot against inner calf or inner thigh (never on the knee joint). Join hands at heart center (Anjali Mudra) or extend overhead.",
                                                        "Develops mental concentration (Dharana), strengthens ankles, tones calves and glutes.",
                                                        "Avoid placing foot directly against the side of the knee joint.", "/images/yoga/vrikshasana.jpg", 45),

                                        createPose("Cobra Pose", "Bhujaṅgāsana (भुजङ्गासन)", "bhujangasana", DifficultyLevel.BEGINNER, "Backbend",
                                                        "Stimulates Agni (digestive fire), pacifies Kapha and Vata",
                                                        "Lie prone on belly with palms beneath shoulders. Press pubic bone into mat, inhale and gently elevate chest using back strength, keeping elbows lightly bent.",
                                                        "Opens chest cavity, increases lung capacity, stimulates abdominal organs, invigorates spinal mobility.",
                                                        "Avoid during pregnancy, active peptic ulcer, or severe lumbar hernia.", "/images/yoga/bhujangasana.jpg", 30),

                                        createPose("Child's Pose", "Bālāsana (बालासन)", "balasana", DifficultyLevel.BEGINNER, "Restorative",
                                                        "Deeply pacifies aggravated Vata and calms Pitta irritation",
                                                        "Kneel on mat with big toes touching. Separate knees wide, fold torso forward between thighs, and rest forehead on floor. Extend arms forward or alongside body.",
                                                        "Gently stretches hips, thighs, and ankles; relieves spinal tension; calms central nervous system.",
                                                        "Knee injuries or diarrhea.", "/images/yoga/balasana.jpg", 120),

                                        createPose("Seated Forward Bend", "Paścimottānāsana (पश्चिमोत्तानासन)", "paschimottanasana", DifficultyLevel.INTERMEDIATE, "Forward Bend",
                                                        "Cools Pitta, activates digestion (Agni), calms mind",
                                                        "Sit with legs extended straight in front. Inhale, lengthen spine upward; exhale, hinge at hips and reach for shins, ankles, or toes keeping back straight.",
                                                        "Massages abdominal viscera, stretches hamstrings and entire posterior chain, reduces visceral fat.",
                                                        "Avoid in severe disc herniation or sciatica flare-up.", "/images/yoga/paschimottanasana.jpg", 60),

                                        createPose("Warrior I", "Vīrabhadrāsana I (वीरभद्रासन १)", "virabhadrasana-i", DifficultyLevel.INTERMEDIATE, "Standing",
                                                        "Builds Kapha endurance, burns fat, channels Pitta focus",
                                                        "Step one foot back 3–4 feet, turn back foot 45 degrees. Bend front knee directly over ankle. Square hips forward and sweep arms overhead.",
                                                        "Strengthens legs, opens hips and chest, builds stamina and courage.",
                                                        "High blood pressure or recent cardiac conditions.", "/images/yoga/virabhadrasana.jpg", 45),

                                        createPose("Bridge Pose", "Setu Bandhāsana (सेतुबन्धासन)", "setu-bandhasana", DifficultyLevel.BEGINNER, "Backbend",
                                                        "Stimulates Thyroid/Metabolic fire, balances Vata and Kapha",
                                                        "Lie on back with knees bent and feet flat, hip-width apart. Press feet and arms into floor, inhale and lift hips toward ceiling.",
                                                        "Opens chest and shoulders, stimulates endocrine glands, strengthens back and gluteal muscles.",
                                                        "Neck injuries.", "/images/yoga/setubandhasana.jpg", 45),

                                        createPose("Fish Pose", "Matsyāsana (मत्स्यासन)", "matsyasana", DifficultyLevel.INTERMEDIATE, "Backbend",
                                                        "Opens Pranavaha Srotas (respiratory channels), pacifies Kapha",
                                                        "Lie on back with legs together. Place hands beneath hips, press forearms into mat, arch chest upward, and gently rest crown on floor.",
                                                        "Expands lung capacity, tones throat and parathyroid glands.",
                                                        "High or low blood pressure, severe cervical neck issues.", "/images/yoga/matsyasana.jpg", 30),

                                        createPose("Bow Pose", "Dhanurāsana (धनुरासन)", "dhanurasana", DifficultyLevel.INTERMEDIATE, "Backbend",
                                                        "Ignites Samana Vayu & Jatharagni (core digestive fire)",
                                                        "Lie on belly, bend knees, and hold outer ankles with hands. Inhale, kick legs back and lift chest off floor.",
                                                        "Massages entire abdominal cavity, relieves constipation, strengthens back musculature.",
                                                        "Pregnancy, hernia, abdominal surgery.", "/images/yoga/dhanurasana.jpg", 30),

                                        createPose("Triangle Pose", "Trikoṇāsana (त्रिकोणासन)", "trikonasana", DifficultyLevel.BEGINNER, "Standing",
                                                        "Balances all three doshas, promotes lateral lymphatic drainage",
                                                        "Stand with feet wide apart. Turn right foot out 90 degrees. Extend arms horizontally, reach torso over right leg, and lower right hand to shin while extending left arm up.",
                                                        "Strengthens legs and torso, stretches hamstrings and spine, stimulates abdominal organs.",
                                                        "Neck pain.", "/images/yoga/trikonasana.jpg", 45),

                                        createPose("Downward-Facing Dog", "Adho Mukha Śvānāsana (अधोमुखश्वानासन)", "adho-mukha-svanasana", DifficultyLevel.INTERMEDIATE, "Inversion",
                                                        "Calms nervous system, relieves excess Pitta from head",
                                                        "From hands and knees, tuck toes and lift hips toward ceiling into inverted 'V'. Press hands evenly into mat and draw heels toward floor.",
                                                        "Lengthens hamstrings and calves, decompresses vertebrae, calms brain, strengthens upper body.",
                                                        "Carpal tunnel syndrome or severe high blood pressure.", "/images/yoga/adhomukha.jpg", 60),

                                        createPose("Corpse Pose", "Śavāsana (शवासन)", "savasana", DifficultyLevel.BEGINNER, "Restorative",
                                                        "Profoundly pacifies agitated Vata and cooling to Pitta",
                                                        "Lie flat on back with legs comfortably spaced and arms relaxed by sides, palms facing upward. Systematically relax every muscle.",
                                                        "Activates parasympathetic nervous system, reduces blood pressure, integrates neurological benefits.",
                                                        "Place bolster beneath knees for lower back comfort.", "/images/yoga/shavasana.jpg", 300),

                                        createPose("Camel Pose", "Uṣṭrāsana (उष्ट्रासन)", "ustrasana", DifficultyLevel.INTERMEDIATE, "Backbend",
                                                        "Clears respiratory congestion, expands Anahata Chakra",
                                                        "Kneel upright with knees hip-width apart. Place hands on sacrum, draw elbows back, inhale, arch spine backward, and reach hands to heels.",
                                                        "Opens chest, abdomen, and quadriceps; stimulates thyroid and parathyroid glands.",
                                                        "Severe lower back or neck injury.", "/images/yoga/ustrasana.jpg", 30),

                                        createPose("Butterfly Pose", "Baddha Koṇāsana (बद्धकोणासन)", "baddha-konasana", DifficultyLevel.BEGINNER, "Seated",
                                                        "Grounds Apana Vayu (pelvic energy), relieves reproductive tension",
                                                        "Sit with spine tall. Bring soles of feet together, letting knees fall open to sides. Hold feet with hands and gently fold forward from hips.",
                                                        "Stretches inner thighs, groin, and knees; stimulates ovaries and prostate gland.",
                                                        "Groin or knee injury.", "/images/yoga/baddhakonasana.jpg", 90),

                                        createPose("Shoulder Stand", "Sarvāṅgāsana (सर्वाङ्गासन)", "sarvangasana", DifficultyLevel.ADVANCED, "Inversion",
                                                        "The 'Queen of Asanas' cooling to Pitta and harmonizing endocrine glands",
                                                        "Lie on back, engage core, lift legs and hips overhead into a vertical line supported by hands against mid-back.",
                                                        "Reverses venous blood pooling, stimulates thyroid, calms autonomic nervous system.",
                                                        "Menstruation, high blood pressure, neck or retinal detachment history.", "/images/yoga/sarvangasana.jpg", 120),

                                        createPose("Plow Pose", "Halāsana (हलासन)", "halasana", DifficultyLevel.ADVANCED, "Inversion",
                                                        "Stimulates abdominal organs, cools body, relieves insomnia",
                                                        "From Shoulder Stand, slowly lower straight legs over head until toes touch floor behind crown. Keep spine perpendicular to floor.",
                                                        "Massages internal organs, stretches entire spinal column, calms sympathetic arousal.",
                                                        "Cervical spine pathology, glaucoma.", "/images/yoga/halasana.jpg", 60),

                                        createPose("Cow Face Pose", "Gomukhāsana (गोमुखासन)", "gomukhasana", DifficultyLevel.INTERMEDIATE, "Seated",
                                                        "Opens tight shoulders and hip rotators, calms Vata",
                                                        "Stack right knee directly on top of left knee. Reach right arm up and fold behind neck; reach left arm behind back and clasp fingers.",
                                                        "Deep shoulder and hip opening, encourages deep thoracic breathing.",
                                                        "Serious shoulder cuff pathology.", "/images/yoga/gomukhasana.jpg", 60),

                                        createPose("Boat Pose", "Nāvāsana (नावासन)", "navasana", DifficultyLevel.INTERMEDIATE, "Core",
                                                        "Ignites Samana Vayu, strengthens core and hip flexors",
                                                        "Sit tall with knees bent. Lean back slightly, lift feet off floor with shins parallel to ground (or straight legs), and extend arms forward.",
                                                        "Strengthens abdominal muscles, hip flexors, and spine; stimulates kidneys and intestines.",
                                                        "Asthma, diarrhea, pregnancy.", "/images/yoga/navasana.jpg", 30),

                                        createPose("Half Lord of the Fishes", "Ardha Matsyendrāsana (अर्धमत्स्येन्द्रासन)", "ardha-matsyendrasana", DifficultyLevel.INTERMEDIATE, "Twist",
                                                        "Massages liver, spleen, and intestines, detoxifies Ama",
                                                        "Sit with left leg bent flat on floor. Step right foot over left thigh. Twist torso to right, pressing left elbow outside right knee.",
                                                        "Increases spinal elasticity, stimulates liver and kidneys, relieves digestive stagnation.",
                                                        "Severe spinal disc herniation.", "/images/yoga/ardhamatsyendrasana.jpg", 45),

                                        createPose("Sun Salutation Flow", "Sūrya Namaskāra (सूर्यनमस्कार)", "surya-namaskara", DifficultyLevel.BEGINNER, "Flow",
                                                        "Tridoshic whole-body vitality sequence harmonizing breath and movement",
                                                        "A 12-step dynamic solar sequence linking Pranamasana, Hastauttanasana, Padahastasana, Ashwa Sanchalanasana, and Bhujangasana.",
                                                        "Improves cardiovascular fitness, stimulates all 7 Saptadhatus, aligns circadian vitality.",
                                                        "Severe vertigo, wrist pain.", "/images/yoga/suryanamaskar.jpg", 300));

                        yogaPoseRepository.saveAll(poses);
                        log.info("Seeded 20 foundational classical Yoga Asanas across all categories.");
                }
        }

        private YogaPose createPose(String engName, String sanskrit, String slug, DifficultyLevel diff, String cat,
                                    String dosha, String instructions, String benefits, String precautions, String image, int dur) {
                return YogaPose.builder()
                                .englishName(engName)
                                .sanskritName(sanskrit)
                                .slug(slug)
                                .difficulty(diff)
                                .category(cat)
                                .doshaEffect(dosha)
                                .instructions(instructions)
                                .benefits(benefits)
                                .precautions(precautions)
                                .imageUrl(image)
                                .recommendedDurationSeconds(dur)
                                .build();
        }

        private void seedMeditations() {
                if (meditationRepository.count() == 0) {
                        List<Meditation> sessions = List.of(
                                        createMeditation("Nadi Shodhana (Alternate Nostril Breathing)", "Pranayama", 10, DifficultyLevel.BEGINNER,
                                                        "Balancing pranayama technique that harmonizes Ida (Lunar/Cooling) and Pingala (Solar/Heating) energy channels.",
                                                        "Sit in a comfortable upright posture. Use Vishnu Mudra with right hand. Close right nostril with thumb, inhale through left for 4 counts. Close left nostril with ring finger, exhale right for 4 counts. Inhale right, exhale left. Repeat smoothly.",
                                                        "Balances autonomic nervous system, clears mental fog, reduces acute anxiety."),

                                        createMeditation("Bhramari (Humming Bee Breath)", "Stress Relief", 8, DifficultyLevel.BEGINNER,
                                                        "Calming acoustic vibration technique that triggers instantaneous nitric oxide production and parasympathetic resonance.",
                                                        "Close ears gently with thumbs and place index fingers over eyelids. Inhale deeply through nose. On exhalation, produce a smooth, high-pitched humming sound like a bee in the throat and cranium.",
                                                        "Instant relief from cerebral tension, anger, hyperactivity, and insomnia."),

                                        createMeditation("Sheetali & Sheetkari (Cooling Breath)", "Pitta Balancing", 7, DifficultyLevel.BEGINNER,
                                                        "Classical cooling pranayama designed to quench excess internal heat, acidity, and emotional irritability.",
                                                        "Curl tongue into a tube (Sheetali) or close teeth gently with lips parted (Sheetkari). Inhale slowly through tongue tube feeling cold air. Close mouth and exhale smoothly through both nostrils.",
                                                        "Cools physiological heat, reduces hyperacidity, quenches thirst, pacifies anger and skin inflammation."),

                                        createMeditation("Kapalabhati (Skull Shining Breath)", "Vitality & Detox", 6, DifficultyLevel.INTERMEDIATE,
                                                        "Shatkriya cleansing technique that invigorates frontal brain lobes, expels stale air, and ignites digestive fire.",
                                                        "Sit upright. Take a passive inhalation followed by a forceful, rapid abdominal contraction to exhale. Let inhalation occur reflexively. Practice 3 rounds of 30 strokes each.",
                                                        "Expels stagnant carbon dioxide, stimulates sluggish metabolism, clears Kapha mucus from sinus cavities."),

                                        createMeditation("Yoga Nidra (Deep Psychic Sleep)", "Deep Healing", 20, DifficultyLevel.BEGINNER,
                                                        "Systematic tantric meditation guiding consciousness to the threshold between wakefulness and deep restorative sleep.",
                                                        "Lie comfortably in Shavasana. Follow the verbal rotation of consciousness across 61 body points, breath awareness, and pairs of opposite sensations.",
                                                        "Reduces systemic cortisol, enhances delta-wave restorative brain states, resolves subconscious tension."),

                                        createMeditation("So-Hum Breath Awareness Dhyana", "Mindfulness", 15, DifficultyLevel.BEGINNER,
                                                        "Ancient Vedic mantra meditation aligning mental focus with spontaneous inhalation and exhalation.",
                                                        "Gently observe the breath without forcing. Silently synchronize the syllable 'So' with every inhalation and 'Hum' with every exhalation.",
                                                        "Deepens mindfulness, halts runaway thought loops, anchors present moment awareness."),

                                        createMeditation("Trataka (Steady Candle Gazing)", "Focus & Vision", 10, DifficultyLevel.INTERMEDIATE,
                                                        "Concentrative meditation technique strengthening optic nerves, stimulating the pineal gland, and centering mental focus.",
                                                        "Place a ghee lamp or candle at eye level 2 feet away. Gaze steadily at tip of flame without blinking until eyes tear slightly. Close eyes and visualize the after-image at the third eye.",
                                                        "Enhances memory (Smriti), improves eyesight, induces deep one-pointed concentration (Ekagrata)."),

                                        createMeditation("Antar Mouna (Inner Silence Meditation)", "Mindfulness", 12, DifficultyLevel.ADVANCED,
                                                        "Mindfulness technique observing thoughts and sensory perceptions without judgment, mirroring classical witness consciousness (Sakshi Bhav).",
                                                        "Sit quietly. Allow thoughts to rise freely into awareness like clouds on a screen without reacting or suppressing. Gradually witness the space between thoughts.",
                                                        "Develops emotional detachment, reduces psychological reactivity, purges subconscious thought residue."),

                                        createMeditation("Ujjayi Pranayama (Ocean Breath of Victory)", "Pranayama", 10, DifficultyLevel.BEGINNER,
                                                        "Gentle glottic constriction generating acoustic ocean sound that regulates vagal tone and focuses attention.",
                                                        "Inhale and exhale smoothly through nose while maintaining a gentle, whispering constriction in the glottis like fogging a mirror with mouth closed.",
                                                        "Enhances oxygenation, calms heart rate, warms internal prana without hyperventilation."),

                                        createMeditation("Om Chanting & AUM Sound Meditation", "Mantra Dhyana", 15, DifficultyLevel.BEGINNER,
                                                        "Harmonizing the primordial acoustic vibration across Ah (gut/stomach), Oo (heart/throat), and Mm (cranium/brain).",
                                                        "Chant 'A-U-M' drawing sound upward from perineum to crown. Rest in silence after each repetition feeling acoustic resonance in the cranium.",
                                                        "Synchronizes hemispheric brain waves, clears neuro-endocrine channels, elevates Sattva."),

                                        createMeditation("Anapanasati (Mindful Breath Observance)", "Mindfulness", 12, DifficultyLevel.BEGINNER,
                                                        "Classical mindfulness observing natural air temperature at the nostrils without altering breath pace.",
                                                        "Focus attention on the triangular area above upper lip and below nostril rims. Observe coolness of inhalation and warmth of exhalation.",
                                                        "Develops stable mental stillness, arrests thought wandering, dissolves physical agitation."),

                                        createMeditation("Prana Mudra & Heart Chakra Visualization", "Energy Flow", 10, DifficultyLevel.INTERMEDIATE,
                                                        "Touch tips of ring finger and pinky to thumb while holding index and middle fingers straight. Visualize emerald light in heart.",
                                                        "Hold Prana Mudra on knees. Direct inhalations into the heart center (Anahata), expanding compassionate vitality through every cell.",
                                                        "Awakens dormant vitality (Prana Shakti), relieves chronic fatigue, opens emotional warmth."));

                        meditationRepository.saveAll(sessions);
                        log.info("Seeded 12 guided meditation, pranayama, and sound therapy practices.");
                }
        }

        private Meditation createMeditation(String title, String cat, int dur, DifficultyLevel diff, String desc, String inst, String ben) {
                return Meditation.builder()
                                .title(title)
                                .category(cat)
                                .durationMinutes(dur)
                                .difficulty(diff)
                                .description(desc)
                                .instructions(inst)
                                .benefits(ben)
                                .build();
        }

        private void seedAyurvedaTopics() {
                if (topicRepository.count() == 0) {
                        List<AyurvedaTopic> topics = List.of(
                                        createTopic("The Five Great Elements (Panchamahabhutas)", "Pañcamahābhūta (पञ्चमहाभूत)", "panchamahabhutas", "Fundamentals", DoshaType.TRIDOSHIC,
                                                        "Ayurveda views everything in the universe—including the human body—as a unique manifestation of five cosmic elements.",
                                                        "1. **Akasha (Ether/Space)**: Nuclear space, cavities within the body, ear channels.\n2. **Vayu (Air/Movement)**: Kinetic energy, nerve impulses, peristalsis, respiration.\n3. **Tejas/Agni (Fire/Transformation)**: Enzymatic metabolism, digestive fire, intellect, visual perception.\n4. **Jala/Apas (Water/Cohesion)**: Bodily fluids, lymph, plasma, cytoplasm, lubrication.\n5. **Prithvi (Earth/Structure)**: Bones, teeth, muscle tissue, physical grounding and density.",
                                                        "Charaka Samhita, Sutrasthana Chapter 1"),

                                        createTopic("Understanding Tridosha: Vata, Pitta, and Kapha", "Tridoṣa Vijñāna (त्रिदोष विज्ञान)", "tridosha-principles", "Dosha", DoshaType.TRIDOSHIC,
                                                        "The three fundamental biological humors that govern all physiological and psychological functions.",
                                                        "When the Five Elements combine in living organisms, they form three dynamic functional energies (Doshas):\n\n- **Vata (Ether + Air)**: Controls all bodily movements, nerve impulses, breath, circulation, and elimination.\n- **Pitta (Fire + Water)**: Controls biochemical transformation, body temperature, enzymatic digestion, intelligence, and visual acuity.\n- **Kapha (Water + Earth)**: Controls physical structure, joint lubrication, cellular cohesion, emotional stability, and immune resilience (Ojas).",
                                                        "Ashtanga Hridaya, Sutrasthana Chapter 1"),

                                        createTopic("Dinacharya: The Ayurvedic Daily Circadian Routine", "Dinacaryā (दिनचर्या)", "dinacharya-daily-routine", "Dinacharya", DoshaType.TRIDOSHIC,
                                                        "Aligning biological circadian rhythms with solar cycles for disease prevention, hormonal balance, and longevity.",
                                                        "Core pillars of Dinacharya include:\n\n1. **Brahma Muhurta**: Waking 45–90 minutes before sunrise when Sattva dominates the atmosphere.\n2. **Saucha & Ushapan**: Drinking warm water to encourage natural downward elimination (Apana Vayu).\n3. **Jihwa Nirlekhana**: Tongue scraping with copper or silver to remove overnight metabolic Ama.\n4. **Kavala / Gandusha**: Oil pulling with warm sesame oil for oral microbiota and jaw strength.\n5. **Abhyanga**: Warm herb-infused oil self-massage before bathing to nourish skin and calm Vata.\n6. **Vyayama & Pranayama**: Moderate physical movement up to half of one's capacity (Ardha Shakti).\n7. **Pradhana Ahara**: Consuming the primary nutritious meal at midday (12:00–1:30 PM) when solar and digestive fire (Pitta) peaks.",
                                                        "Sushruta Samhita, Chikitsasthana Chapter 24"),

                                        createTopic("Ritucharya: Seasonal Living and Detoxification", "Ṛtucaryā (ऋतुचर्या)", "ritucharya-seasonal-living", "Lifestyle", DoshaType.TRIDOSHIC,
                                                        "Adapting diet, lifestyle, and cleansing protocols to the six Ayurvedic seasons to prevent doshic accumulation.",
                                                        "Ayurveda divides the year into two solstices (Adayana) comprising six distinct seasons (Ritus):\n\n- **Shishira & Vasanta (Late Winter / Spring)**: Kapha liquefies in the warm sun. Time for light, bitter foods and spring Panchakarma (Vamana).\n- **Grishma (Summer)**: Intense solar heat depletes bodily moisture and accumulates Vata. Best to consume sweet, cooling, liquid foods.\n- **Varsha (Monsoon)**: Dampness and cloudy skies weaken digestive fire (Agni), causing Vata aggravation. Warm, sour, salty foods and Basti therapy are indicated.\n- **Sharad (Autumn)**: The sudden post-monsoon heat causes Pitta flare-ups. Ideal season for cooling herbs, ghee, and Virechana cleansing.\n- **Hemanta (Early Winter)**: Digestive fire becomes extremely strong. High-nourishment, grounding foods build Ojas.",
                                                        "Charaka Samhita, Sutrasthana Chapter 6"),

                                        createTopic("Agni: The 13 Biological Digestive Fires", "Agni Vijñāna (अग्नि विज्ञान)", "agni-digestive-fire", "Metabolism", DoshaType.TRIDOSHIC,
                                                        "Agni is the foundational biological metabolic force governing digestion, cellular assimilation, and immunity.",
                                                        "In Ayurveda, health is rooted in balanced digestive fire (Samagni). There are 13 distinct types of Agni in the human body:\n\n1. **Jatharagni (1)**: The master digestive fire located in the stomach and small intestine (Grahani).\n2. **Bhutagnis (5)**: Elemental fires located in the liver that convert food elements into body-compatible forms.\n3. **Dhatvagnis (7)**: Tissue-level metabolic fires governing the nourishment and transformation of the 7 body tissues (Rasa, Rakta, Mamsa, Meda, Asthi, Majja, Shukra).\n\nWhen Agni is impaired (Mandagni, Tikshnagni, or Vishamagni), food remains improperly processed, generating toxic metabolic waste known as Ama.",
                                                        "Charaka Samhita, Chikitsasthana Chapter 15"),

                                        createTopic("Ama: The Root of Pathology and Metabolic Toxins", "Āma Vijñāna (आम विज्ञान)", "ama-metabolic-toxins", "Pathology", DoshaType.TRIDOSHIC,
                                                        "Understanding undigested metabolic residue, toxic stagnation, and classical detoxification protocols.",
                                                        "When Jatharagni is weak, undigested food ferments in the gastrointestinal tract, transforming into sticky, heavy, foul-smelling toxic residue called **Ama**.\n\n**Signs of Systemic Ama:**\n- Thick white or yellow coating on the tongue (*Jihva Lepa*)\n- Chronic heaviness, fatigue, and sluggishness after meals\n- Foul breath, cloudy urine, and malodorous stools\n- Blocked channels (*Srotorodha*) leading to joint stiffness and brain fog\n\n**Classical Management:** Fasting (*Langhana*), consuming hot water with ginger, kindling Agni with digestive herbs (*Deepana-Pachana*), and Panchakarma evacuation.",
                                                        "Madhava Nidana, Chapter 25"),

                                        createTopic("Panchakarma: The Five Classical Cleansing Therapies", "Pañcakarma (पञ्चकर्म)", "panchakarma-purification", "Therapeutics", DoshaType.TRIDOSHIC,
                                                        "The supreme five-fold biomedical detoxification and tissue rejuvenation protocol in classical Ayurveda.",
                                                        "Panchakarma permanently removes deep-seated doshic toxins through five primary clinical procedures preceded by preparatory internal/external oleation (*Snehana*) and steam sweating (*Swedana*):\n\n1. **Vamana**: Therapeutic medical emesis for chronic Kapha disorders (asthma, metabolic syndrome).\n2. **Virechana**: Controlled therapeutic purgation for deep Pitta disorders (skin diseases, liver toxicity).\n3. **Basti**: Medicated herbal oil/decoction enemas—the supreme therapy for all Vata disorders.\n4. **Nasya**: Nasal administration of medicated oils for clearing supra-clavicular channels, sinuses, and mental fog.\n5. **Raktamokshana**: Therapeutic bloodletting for acute toxic Pitta blood conditions.",
                                                        "Charaka Samhita, Siddhisthana Chapters 1–12"),

                                        createTopic("Shad Rasa: The Science of Six Tastes", "Ṣaḍ Rasa (षड्रस)", "shad-rasa-six-tastes", "Nutrition", DoshaType.TRIDOSHIC,
                                                        "How the six classical tastes directly influence biochemical physiology, neurochemistry, and doshic equilibrium.",
                                                        "Ayurveda recognizes six fundamental tastes (Rasa), each formed by two elemental building blocks:\n\n1. **Madhura (Sweet - Earth + Water)**: Builds tissue, calms Vata & Pitta, increases Kapha.\n2. **Amla (Sour - Earth + Fire)**: Stimulates Agni, kindles appetite, balances Vata, increases Pitta & Kapha.\n3. **Lavana (Salty - Water + Fire)**: Retains moisture, enhances taste, pacifies Vata, aggravates Pitta & Kapha.\n4. **Katu (Pungent - Fire + Air)**: Clears sinuses, stimulates metabolism, balances Kapha, aggravates Vata & Pitta.\n5. **Tikta (Bitter - Air + Ether)**: Purifies blood, detoxifies liver, balances Pitta & Kapha, increases Vata.\n6. **Kashaya (Astringent - Air + Earth)**: Heals wounds, contracts tissues, pacifies Pitta & Kapha, increases Vata.\n\nA truly balanced meal should incorporate all six tastes to ensure complete physiological and psychological satiety.",
                                                        "Charaka Samhita, Sutrasthana Chapter 26"),

                                        createTopic("Saptadhatus: The Seven Primary Bodily Tissues", "Sapta Dhātu (सप्तधातु)", "saptadhatus-seven-tissues", "Physiology", DoshaType.TRIDOSHIC,
                                                        "The sequential biological cascade that transforms ingested nutrition into structural tissues and immune resilience.",
                                                        "Food is progressively refined across 7 tissue layers under the influence of tissue-specific Dhatvagnis:\n\n1. **Rasa (Plasma/Lymph)**: Nourishes all tissues, derived from digested food essence (Ahara Rasa).\n2. **Rakta (Blood/Erythrocytes)**: Oxygenates and enlivens the body (Jivana).\n3. **Mamsa (Muscle)**: Covers skeletal frame and provides physical strength (Lepana).\n4. **Meda (Adipose/Fat)**: Lubricates joints and insulates tissues (Snehana).\n5. **Asthi (Bone/Cartilage)**: Provides rigid structural architecture (Dharana).\n6. **Majja (Bone Marrow/Nerve Tissue)**: Fills bone cavities and facilitates neurological communication (Purana).\n7. **Shukra/Artava (Reproductive Tissue)**: Sustains fertility and produces vitality (Garbhotpadana).\n\nThe refined essence of all seven healthy Dhatus is **Ojas**—the foundation of immunity and longevity.",
                                                        "Sushruta Samhita, Sutrasthana Chapter 14"),

                                        createTopic("Ojas, Tejas, and Prana: The Subtle Trio of Vitality", "Ojas, Tejas, Prāṇa (ओजस् तेजस् प्राण)", "subtle-essences-vitality", "Subtle Energy", DoshaType.TRIDOSHIC,
                                                        "The refined, master essences behind physical immunity, cellular intelligence, and life force.",
                                                        "- **Ojas (Essence of Kapha)**: The master biological water element responsible for immune defense, glow (*Kanti*), emotional resilience, and longevity. Depleted by chronic stress, lack of sleep, and anger.\n- **Tejas (Essence of Pitta)**: The subtle fire of perception, cellular enzyme intelligence, and discrimination (*Viveka*).\n- **Prana (Essence of Vata)**: The supreme cosmic life force that enters via breath and food, coordinating all neuromuscular and mental activities.",
                                                        "Charaka Samhita, Sutrasthana Chapter 17"),

                                        createTopic("Srotas: The 13 Internal Physiological Channel Systems", "Sroto Vijñāna (स्रोतोविज्ञान)", "srotas-body-channels", "Anatomy", DoshaType.TRIDOSHIC,
                                                        "Understanding the micro and macro biological pathways of circulation, nourishment, and excretion.",
                                                        "Ayurveda views the human body as a vast network of dynamic channels (Srotas) transporting nutrients, fluids, and waste. There are 13 major internal channel systems in men and 16 in women:\n\n- **Pranavaha Srotas**: Respiratory channels\n- **Annavaha Srotas**: Gastrointestinal digestive tract\n- **Udakavaha Srotas**: Water and electrolyte balance channels\n- **7 Dhatuvaha Srotas**: Channels for each tissue layer\n- **3 Malavaha Srotas**: Excretory pathways for urine, feces, and sweat.\n\nDisease occurs when channels suffer from: *Atipravritti* (excess flow), *Sanga* (blockage), *Siragranthi* (tumors/aneurysms), or *Vimargagamana* (flow into wrong pathways).",
                                                        "Charaka Samhita, Vimanasthana Chapter 5"),

                                        createTopic("Viruddha Ahara: Incompatible Food Combinations", "Viruddha Āhāra (विरुद्ध आहार)", "incompatible-foods", "Nutrition", DoshaType.TRIDOSHIC,
                                                        "How consuming biochemically incompatible food pairings disrupts cellular metabolism and generates toxic Ama.",
                                                        "Certain foods that are nourishing when eaten alone become toxic when combined due to opposing Virya (potency), Vipaka (post-digestive effect), or processing methods:\n\n- **Milk + Sour Fruits / Citrus**: Curdles in the stomach, creating heavy undigested toxins.\n- **Milk + Fish**: Opposing thermal potencies (Fish is heating; Milk is cooling) perturbing blood tissue (Rakta Dhatu).\n- **Honey + Equal Ratio Ghee**: Forms an indigestible toxic macromolecular compound.\n- **Heated / Cooked Honey**: Heating destroys natural enzymes in raw honey, transforming it into toxic glue-like Ama.\n- **Melons Eaten with Any Other Food**: Melons digest extremely rapidly (15-20 min); other foods trap melon in the stomach where it rapidly ferments.",
                                                        "Charaka Samhita, Sutrasthana Chapter 26"));

                        topicRepository.saveAll(topics);
                        log.info("Seeded 12 comprehensive clinical Ayurveda Knowledge Chapters.");
                }
        }

        private AyurvedaTopic createTopic(String title, String sanskrit, String slug, String cat, DoshaType dosha,
                                          String summary, String content, String ref) {
                return AyurvedaTopic.builder()
                                .title(title)
                                .sanskritTitle(sanskrit)
                                .slug(slug)
                                .category(cat)
                                .doshaAssociation(dosha)
                                .summary(summary)
                                .content(content)
                                .traditionalReferences(ref)
                                .published(true)
                                .build();
        }

        private void seedArticles() {
                if (articleRepository.count() == 0) {
                        List<Article> articles = List.of(
                                        createArticle("Circadian Rhythms & Dinacharya: Modern Chronobiology Meets Ancient Wisdom",
                                                        "circadian-rhythms-and-dinacharya", "Lifestyle", "Dr. Shreya Kulkarni, BAMS, MD", 6,
                                                        "/images/yoga/tadasana.jpg",
                                                        "How the 2017 Nobel Prize discovery in circadian gene clocks mirrors the 3,000-year-old Vedic science of Dinacharya time windows.",
                                                        "Modern chronobiology has affirmed that cellular repair, digestive enzymes, and melatonin synthesis operate on strict 24-hour oscillating clocks. In classical Ayurveda, each 4-hour window of the day is governed by a distinct Dosha:\n\n- **6:00 AM – 10:00 AM (Kapha)**: Heavy, grounding. Optimal time for invigorative exercise.\n- **10:00 AM – 2:00 PM (Pitta)**: Peak enzymatic capacity. Ideal time for the largest meal of the day.\n- **2:00 PM – 6:00 PM (Vata)**: High mental agility, creativity, and nervous transmission.\n- **6:00 PM – 10:00 PM (Kapha)**: Natural winding down, melatonin secretion, and sleep preparation.",
                                                        "Panda S. (2016). Circadian physiology of metabolism. Science, 354(6315), 1008-1015."),

                                        createArticle("The Science of Adaptogens: How Ashwagandha Regulates the HPA Axis",
                                                        "science-of-adaptogens-ashwagandha", "Research & Evidence", "AyurVana Research Editorial Board", 5,
                                                        "/images/herbs/ashwagandha.jpg",
                                                        "A deep dive into hypothalamic-pituitary-adrenal modulation and cortisol balancing through Withania somnifera.",
                                                        "Chronic psychological stress triggers persistent hyperactivation of the hypothalamic-pituitary-adrenal (HPA) axis, elevating circulating glucocorticoids and precipitating metabolic dysregulation. Ashwagandha (Withania somnifera) acts as a systemic adaptogen by modulating GABAergic signaling and attenuating stress-induced corticosterone elevations...",
                                                        "Chandrasekhar K, et al. (2012). Indian J Psychol Med, 34(3):255-262. doi: 10.4103/0253-7176.106022"),

                                        createArticle("Curcuminoid Bioavailability & Piperine Synergy: Classical Anupana Validated",
                                                        "curcumin-piperine-synergy-anupana", "Pharmacology", "Dr. Rajeshwar Nambiar, BAMS, MD", 7,
                                                        "/images/herbs/turmeric.jpg",
                                                        "Why classical Ayurvedic pharmacology mandated combining Turmeric with Black Pepper/Pippali and healthy fats.",
                                                        "In classical Ayurveda, herbs are never administered in isolation; they are co-prescribed with specific catalytic carrier vehicles known as **Anupana** and bio-enhancers (**Yogavahi**). While native curcumin exhibits notoriously low aqueous solubility and rapid hepatic glucuronidation, co-administration with Piperine (from Pippali / Black Pepper) inhibits UDP-glucuronosyltransferase, increasing serum curcumin bioavailability by 2000%...",
                                                        "Shoba G, et al. (1998). Planta Med, 64(4):353-356. doi: 10.1055/s-2006-957450"),

                                        createArticle("Cardioprotection with Terminalia arjuna: Insights from Contemporary Cardiology",
                                                        "cardioprotection-terminalia-arjuna", "Cardiology", "Dr. Aarav Sharma, BAMS, MD", 8,
                                                        "/images/herbs/arjuna.jpg",
                                                        "Clinical and biochemical mechanisms of Arjuna bark in coronary perfusion, left ventricular ejection fraction, and endothelial resilience.",
                                                        "Terminalia arjuna bark extracts have been clinically evaluated in patients with ischemic cardiomyopathy and refractory heart failure. Bioactive triterpenoids, arjunolic acid, and oligomeric proanthocyanidins enhance myocardial antioxidant enzyme activities, reduce lipid peroxidation, and improve left ventricular ejection fraction...",
                                                        "Dwivedi S, et al. (2007). Int J Cardiol, 120(1):127-129."),

                                        createArticle("The Gut Microbiome & Agni: How Triphala Shapes the Intestinal Ecosystem",
                                                        "gut-microbiome-and-triphala", "Gastroenterology", "Dr. Priyadarshini Iyer, BAMS, MS", 6,
                                                        "/images/herbs/haritaki.jpg",
                                                        "Analyzing how the three myrobalan fruits of Triphala act as rich prebiotics to nourish Akkermansia and Bifidobacteria.",
                                                        "Modern gastroenterological research demonstrates that the polyphenols and chebulic tannins present in Triphala (Amalaki, Haritaki, Bibhitaki) act as selective prebiotics. They resist gastric acid degradation, reach the colon intact, and stimulate the proliferation of beneficial Akkermansia muciniphila while suppressing pro-inflammatory bacteroides...",
                                                        "Peterson CT, et al. (2017). J Altern Complement Med, 23(8):607-614."),

                                        createArticle("Psychoneuroimmunology of Bhramari Pranayama: Nitric Oxide & Vagal Tone",
                                                        "psychoneuroimmunology-bhramari-pranayama", "Neuroscience", "Dr. Shreya Kulkarni, BAMS, MD", 5,
                                                        "/images/yoga/shavasana.jpg",
                                                        "How humming frequency vibrations stimulate paranasal sinuses to produce a 15-fold surge in endogenous nitric oxide.",
                                                        "Acoustic vibrations generated during Bhramari Pranayama create resonant frequencies in the craniofacial air sinuses. Karolinska Institute studies revealed that paranasal sinus humming increases nasal nitric oxide (NO) gas release by up to 1500%, improving pulmonary ventilation-perfusion matching and elevating parasympathetic heart rate variability (HRV)...",
                                                        "Weitzberg E, Lundberg JO. (2002). Am J Respir Crit Care Med, 166(2):144-145."),

                                        createArticle("Ayurvedic Nephroprotection: Punarnava & Gokshura in Glomerular Health",
                                                        "ayurvedic-nephroprotection-punarnava-gokshura", "Nephrology", "Dr. Rajeshwar Nambiar, BAMS, MD", 7,
                                                        "/images/herbs/punarnava.jpg",
                                                        "Cellular mechanisms of Boerhavia diffusa and Tribulus terrestris in attenuating renal oxidative stress and oxalate crystallization.",
                                                        "Chronic kidney disease management in Ayurveda focuses on the Mutravaha Srotas. Punarnava and Gokshura exhibit synergistic nephroprotective actions by downregulating TGF-beta fibrotic signaling, preserving glomerular filtration rate (GFR), and inhibiting calcium oxalate stone nucleation...",
                                                        "Rawat AK, et al. (1997). Phytother Res, 11(7):516-519."),

                                        createArticle("The Neurobiology of Medhya Rasayanas: Brahmi, Mandukaparni & Shankhpushpi",
                                                        "neurobiology-medhya-rasayanas-brahmi", "Neuroscience", "Dr. Aarav Sharma, BAMS, MD", 8,
                                                        "/images/herbs/brahmi.jpg",
                                                        "Comparative analysis of classical cognitive enhancers in dendritic branching, BDNF elevation, and synaptic plasticity.",
                                                        "Classical Ayurveda categorizes four premier herbs as Medhya Rasayanas for brain health. Modern neuroscience confirms that bacosides in Bacopa monnieri, asiaticosides in Centella asiatica, and convolvine in Convolvulus pluricaulis stimulate brain-derived neurotrophic factor (BDNF) and enhance synaptic long-term potentiation...",
                                                        "Russo A, et al. (2005). Phytother Res, 19(9):767-772."));

                        articleRepository.saveAll(articles);
                        log.info("Seeded 8 peer-reviewed clinical and wellness articles.");
                }
        }

        private Article createArticle(String title, String slug, String cat, String author, int readTime, String cover,
                                      String summary, String content, String ref) {
                return Article.builder()
                                .title(title)
                                .slug(slug)
                                .category(cat)
                                .authorName(author)
                                .readTimeMinutes(readTime)
                                .coverImageUrl(cover)
                                .summary(summary)
                                .content(content)
                                .referencesContent(ref)
                                .published(true)
                                .build();
        }
}
