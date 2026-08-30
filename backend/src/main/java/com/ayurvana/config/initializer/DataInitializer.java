package com.ayurvana.config.initializer;

import com.ayurvana.model.entity.*;
import com.ayurvana.model.enums.*;
import com.ayurvana.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

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
                log.info("Checking AyurVana database initialization state...");
                seedRoles();
                seedUsers();
                seedDoctors();
                seedHerbs();
                seedYogaPoses();
                seedMeditations();
                seedAyurvedaTopics();
                seedArticles();
                log.info("AyurVana database initialization completed successfully.");
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
                                        .roles(java.util.Set.of(userRole))
                                        .active(true)
                                        .build();
                        userRepository.save(regularUser);

                        // 2. Demo Doctor
                        User doctorUser = User.builder()
                                        .fullName("Dr. Aarav Sharma")
                                        .email("doctor@ayurvana.com")
                                        .passwordHash(passwordEncoder.encode("password123"))
                                        .phoneNumber("+91 98111 22334")
                                        .roles(java.util.Set.of(userRole, doctorRole))
                                        .active(true)
                                        .build();
                        userRepository.save(doctorUser);

                        // 3. Admin
                        User adminUser = User.builder()
                                        .fullName("AyurVana Administrator")
                                        .email("admin@ayurvana.com")
                                        .passwordHash(passwordEncoder.encode("admin123"))
                                        .phoneNumber("+91 99999 00000")
                                        .roles(java.util.Set.of(userRole, adminRole))
                                        .active(true)
                                        .build();
                        userRepository.save(adminUser);

                        log.info("Seeded 3 default accounts: user@ayurvana.com, doctor@ayurvana.com, admin@ayurvana.com");
                }
        }

        private void seedDoctors() {
                if (doctorProfileRepository.count() == 0) {
                        userRepository.findByEmail("doctor@ayurvana.com").ifPresent(doctorUser -> {
                                DoctorProfile doc1 = DoctorProfile.builder()
                                                .user(doctorUser)
                                                .qualifications("BAMS (Univ of Delhi), MD (Ayurveda - Kayachikitsa)")
                                                .medicalLicenseNumber("MCIM-AYUR-2015-8492")
                                                .stateMedicalCouncil("Maharashtra Council of Indian Medicine")
                                                .yearsOfExperience(12)
                                                .primarySpecialization(
                                                                "Kayachikitsa (Internal Medicine & Metabolic Health)")
                                                .languagesSpoken("English, Hindi, Sanskrit")
                                                .consultationFee(new java.math.BigDecimal("800.00"))
                                                .bio("Dr. Aarav Sharma is a senior Ayurvedic physician specializing in chronic lifestyle disorders, diabetes management, autoimmune balance, and classical gut restoration. With over a decade of clinical experience, he integrates traditional pulse diagnosis (Nadi Pariksha) with modern laboratory evaluations.")
                                                .verified(true)
                                                .verificationStatus(VerificationStatus.APPROVED)
                                                .averageRating(new java.math.BigDecimal("4.92"))
                                                .totalReviews(48)
                                                .build();
                                doctorProfileRepository.save(doc1);
                        });

                        Role userRole = roleRepository.findByName(RoleName.ROLE_USER).orElse(null);
                        Role doctorRole = roleRepository.findByName(RoleName.ROLE_DOCTOR).orElse(null);

                        User doctor2User = User.builder()
                                        .fullName("Dr. Priyadarshini Iyer")
                                        .email("priyadarshini@ayurvana.com")
                                        .passwordHash(passwordEncoder.encode("password123"))
                                        .phoneNumber("+91 98222 33445")
                                        .roles(java.util.Set.of(userRole, doctorRole))
                                        .active(true)
                                        .build();
                        doctor2User = userRepository.save(doctor2User);

                        DoctorProfile doc2 = DoctorProfile.builder()
                                        .user(doctor2User)
                                        .qualifications("BAMS, MS (Ayurveda), PGD Yoga Therapy (NIMHANS)")
                                        .medicalLicenseNumber("TNMC-AYUR-2018-3190")
                                        .stateMedicalCouncil("Tamil Nadu Board of Indian Medicine")
                                        .yearsOfExperience(8)
                                        .primarySpecialization("Panchakarma (Detoxification & Gut Health)")
                                        .languagesSpoken("English, Tamil, Hindi")
                                        .consultationFee(new java.math.BigDecimal("750.00"))
                                        .bio("Dr. Priyadarshini Iyer focuses on clinical detoxification, neuro-muscular rejuvenation, and women's hormonal health. She has guided hundreds of patients through authentic seasonal Panchakarma and Dinacharya lifestyle optimization.")
                                        .verified(true)
                                        .verificationStatus(VerificationStatus.APPROVED)
                                        .averageRating(new java.math.BigDecimal("4.88"))
                                        .totalReviews(32)
                                        .build();
                        doctorProfileRepository.save(doc2);

                        log.info("Seeded 2 verified doctors: Dr. Aarav Sharma, Dr. Priyadarshini Iyer");
                }
        }

        private void seedRoles() {
                if (roleRepository.count() == 0) {
                        for (RoleName roleName : RoleName.values()) {
                                roleRepository.save(Role.builder().name(roleName).build());
                        }
                        log.info("Seeded default roles: ROLE_USER, ROLE_DOCTOR, ROLE_ADMIN");
                }
        }

        private void seedHerbs() {
                if (herbRepository.count() == 0) {
                        // 1. Ashwagandha
                        Herb ashwagandha = Herb.builder()
                                        .commonName("Ashwagandha (Indian Ginseng)")
                                        .sanskritName("Aśvagandhā (अश्वगन्धा)")
                                        .scientificName("Withania somnifera")
                                        .botanicalFamily("Solanaceae")
                                        .slug("ashwagandha")
                                        .rasa("Tikta (Bitter), Kashaya (Astringent), Madhura (Sweet)")
                                        .virya("Ushna (Heating)")
                                        .vipaka("Madhura (Sweet)")
                                        .primaryDoshaEffect(
                                                        "Balances Vata and Kapha; may aggravate Pitta in high doses")
                                        .partsUsed("Roots")
                                        .description("Ashwagandha is one of the most revered adaptogenic herbs in Ayurveda, celebrated for Rasayana (rejuvenative) and Medhya (cognitive) properties.")
                                        .traditionalUses(
                                                        "Historically prescribed for physical debility, insomnia, nervous exhaustion, stress, and enhancing vitality (Ojas).")
                                        .modernResearchSummary(
                                                        "Clinical trials indicate Withanolides in Ashwagandha reduce serum cortisol levels, attenuate perceived stress, and support restful sleep architecture.")
                                        .dosageAndPreparations(
                                                        "Powder (Churna): 3–6 grams daily with warm milk or ghee; Extract capsules: 300–600 mg standardized extract.")
                                        .imageUrl("https://images.unsplash.com/photo-1615485290382-441e4d049cb5?auto=format&fit=crop&w=800&q=80")
                                        .published(true)
                                        .build();

                        PlantSafety ashwagandhaSafety = PlantSafety.builder()
                                        .herb(ashwagandha)
                                        .toxicityLevel(ToxicityLevel.SAFE)
                                        .contraindications(
                                                        "Hyperthyroidism, acute severe liver conditions, or concurrent heavy sedative medication without medical supervision.")
                                        .interactionWarnings(
                                                        "May enhance effects of barbiturates, benzodiazepines, and immunosuppressants.")
                                        .pregnancySafety(
                                                        "Contraindicated during pregnancy due to potential uterine stimulant properties.")
                                        .overdoseSymptoms("Gastrointestinal distress, nausea, diarrhea.")
                                        .emergencyAction(
                                                        "Discontinue usage and hydrate. Seek medical attention if severe discomfort persists.")
                                        .build();
                        ashwagandha.setSafety(ashwagandhaSafety);

                        PlantReference ashwagandhaRef1 = PlantReference.builder()
                                        .herb(ashwagandha)
                                        .sourceTitle("Charaka Samhita - Chikitsa Sthana (Rasayana Adhyaya)")
                                        .citation("Charaka Samhita, Vol 2, Chapter 1: Rasayana Therapy")
                                        .evidenceType(EvidenceType.TRADITIONAL_TEXT)
                                        .build();
                        PlantReference ashwagandhaRef2 = PlantReference.builder()
                                        .herb(ashwagandha)
                                        .sourceTitle("A prospective, randomized double-blind trial on Ashwagandha in stress reduction")
                                        .citation("Indian J Psychol Med. 2012 Jul-Sep; 34(3): 255–262. doi: 10.4103/0253-7176.106022")
                                        .url("https://pubmed.ncbi.nlm.nih.gov/23439798/")
                                        .evidenceType(EvidenceType.CLINICAL_TRIAL)
                                        .build();
                        ashwagandha.setReferences(Arrays.asList(ashwagandhaRef1, ashwagandhaRef2));
                        herbRepository.save(ashwagandha);

                        // 2. Tulsi
                        Herb tulsi = Herb.builder()
                                        .commonName("Holy Basil (Tulsi)")
                                        .sanskritName("Tulasī (तुलसी)")
                                        .scientificName("Ocimum tenuiflorum")
                                        .botanicalFamily("Lamiaceae")
                                        .slug("tulsi")
                                        .rasa("Katu (Pungent), Tikta (Bitter)")
                                        .virya("Ushna (Heating)")
                                        .vipaka("Katu (Pungent)")
                                        .primaryDoshaEffect(
                                                        "Balances Kapha and Vata; may elevate Pitta if consumed excessively")
                                        .partsUsed("Leaves, Seeds")
                                        .description("Known as 'The Incomparable One' and 'Queen of Herbs', Tulsi is renowned for supporting respiratory clarity, immune balance, and mental calm.")
                                        .traditionalUses(
                                                        "Traditional relief for bronchial congestion, cough, fever, digestive sluggishness, and environmental stress.")
                                        .modernResearchSummary(
                                                        "Eugenol and rosmarinic acid constituents exhibit significant antimicrobial, antioxidant, and immunomodulatory activities.")
                                        .dosageAndPreparations(
                                                        "Infusion (Tea): 2–3 grams dried leaves steeped in boiling water twice daily; Fresh leaf juice: 5–10 ml.")
                                        .imageUrl("https://images.unsplash.com/photo-1596755094514-f87e34085b2c?auto=format&fit=crop&w=800&q=80")
                                        .published(true)
                                        .build();

                        PlantSafety tulsiSafety = PlantSafety.builder()
                                        .herb(tulsi)
                                        .toxicityLevel(ToxicityLevel.SAFE)
                                        .contraindications(
                                                        "Bleeding disorders or patients scheduled for surgery within 2 weeks due to mild anticoagulant potential.")
                                        .interactionWarnings(
                                                        "May potentiate antiplatelet and anticoagulant medications.")
                                        .pregnancySafety(
                                                        "Caution advised during pregnancy and breastfeeding; avoid high therapeutic doses.")
                                        .overdoseSymptoms("Gastric irritation, excessive perspiration.")
                                        .emergencyAction("Reduce dosage or dilute with room-temperature water.")
                                        .build();
                        tulsi.setSafety(tulsiSafety);

                        PlantReference tulsiRef = PlantReference.builder()
                                        .herb(tulsi)
                                        .sourceTitle("Tulsi - Ocimum sanctum: A herb for all reasons")
                                        .citation("J Ayurveda Integr Med. 2014 Oct-Dec; 5(4): 251–259. doi: 10.4103/0975-9476.146554")
                                        .url("https://pubmed.ncbi.nlm.nih.gov/25624701/")
                                        .evidenceType(EvidenceType.CLINICAL_TRIAL)
                                        .build();
                        tulsi.setReferences(List.of(tulsiRef));
                        herbRepository.save(tulsi);

                        // 3. Neem
                        Herb neem = Herb.builder()
                                        .commonName("Neem (Indian Lilac)")
                                        .sanskritName("Nimba (निम्ब)")
                                        .scientificName("Azadirachta indica")
                                        .botanicalFamily("Meliaceae")
                                        .slug("neem")
                                        .rasa("Tikta (Bitter), Kashaya (Astringent)")
                                        .virya("Sheeta (Cooling)")
                                        .vipaka("Katu (Pungent)")
                                        .primaryDoshaEffect("Balances Pitta and Kapha; increases Vata in excess")
                                        .partsUsed("Leaves, Bark, Seed Oil (Topical only)")
                                        .description("The quintessential Ayurvedic cooling and blood-purifying (Raktashodhaka) botanical.")
                                        .traditionalUses(
                                                        "Skin disorders (Kushtha), inflammatory conditions, metabolic detox, oral hygiene.")
                                        .modernResearchSummary(
                                                        "Azadirachtin and nimbin demonstrate proven broad-spectrum antibacterial and anti-inflammatory properties.")
                                        .dosageAndPreparations(
                                                        "Leaf powder: 1–3 grams daily; Bark decoction: 15–30 ml; Oil strictly for external application.")
                                        .imageUrl("https://images.unsplash.com/photo-1540420773420-3366772f4999?auto=format&fit=crop&w=800&q=80")
                                        .published(true)
                                        .build();

                        PlantSafety neemSafety = PlantSafety.builder()
                                        .herb(neem)
                                        .toxicityLevel(ToxicityLevel.MILD_IRRITANT)
                                        .contraindications(
                                                        "Neem seed oil is TOXIC if ingested orally, especially in children. Contraindicated in weakness or emaciation.")
                                        .interactionWarnings(
                                                        "May lower blood glucose; monitor if taking anti-diabetic medications.")
                                        .pregnancySafety(
                                                        "STRICTLY CONTRAINDICATED in pregnancy; historically used as an abortifacient.")
                                        .overdoseSymptoms(
                                                        "Hepatic/renal irritation from seed oil ingestion; excessive Vata aggravation.")
                                        .emergencyAction("Immediate gastric lavage if seed oil ingested internally.")
                                        .build();
                        neem.setSafety(neemSafety);
                        herbRepository.save(neem);

                        // 4. Turmeric (Haridra)
                        Herb turmeric = Herb.builder()
                                        .commonName("Turmeric")
                                        .sanskritName("Haridrā (हरिद्रा)")
                                        .scientificName("Curcuma longa")
                                        .botanicalFamily("Zingiberaceae")
                                        .slug("turmeric")
                                        .rasa("Tikta (Bitter), Katu (Pungent)")
                                        .virya("Ushna (Heating)")
                                        .vipaka("Katu (Pungent)")
                                        .primaryDoshaEffect(
                                                        "Tridoshic (balances all three doshas, especially Kapha and Pitta)")
                                        .partsUsed("Rhizome")
                                        .description("Golden spice of Ayurveda celebrated for immune modulation, joint comfort, and digestive fire.")
                                        .traditionalUses(
                                                        "Wound healing, skin glow, liver detoxification, respiratory wellness, joint mobility.")
                                        .modernResearchSummary(
                                                        "Curcuminoids provide potent NF-kB inhibition, systemic antioxidant defence, and cellular protection.")
                                        .dosageAndPreparations(
                                                        "Rhizome powder: 1–3 grams with pinch of black pepper (Piperine) and healthy fats.")
                                        .imageUrl("https://images.unsplash.com/photo-1615485290382-441e4d049cb5?auto=format&fit=crop&w=800&q=80")
                                        .published(true)
                                        .build();

                        PlantSafety turmericSafety = PlantSafety.builder()
                                        .herb(turmeric)
                                        .toxicityLevel(ToxicityLevel.SAFE)
                                        .contraindications(
                                                        "Gallbladder obstruction or gallstones; active bleeding disorders.")
                                        .interactionWarnings("May potentiate blood-thinning and anticoagulant drugs.")
                                        .pregnancySafety(
                                                        "Safe in culinary amounts; avoid excessive therapeutic extracts during pregnancy.")
                                        .overdoseSymptoms("Acid reflux, upset stomach.")
                                        .emergencyAction("Hydrate with cooling herbal teas (coriander/fennel).")
                                        .build();
                        turmeric.setSafety(turmericSafety);
                        herbRepository.save(turmeric);

                        // 5. Brahmi
                        Herb brahmi = Herb.builder()
                                        .commonName("Brahmi (Water Hyssop)")
                                        .sanskritName("Brāhmī (ब्राह्मी)")
                                        .scientificName("Bacopa monnieri")
                                        .botanicalFamily("Plantaginaceae")
                                        .slug("brahmi")
                                        .rasa("Tikta (Bitter), Kashaya (Astringent)")
                                        .virya("Sheeta (Cooling)")
                                        .vipaka("Madhura (Sweet)")
                                        .primaryDoshaEffect("Balances Vata and Pitta; clears excess Kapha from mind")
                                        .partsUsed("Whole Plant")
                                        .description("Premier Medhya Rasayana (nootropic brain tonic) in classical Ayurveda.")
                                        .traditionalUses(
                                                        "Cognitive enhancement, memory retention, nervous system calming, speech clarity.")
                                        .modernResearchSummary(
                                                        "Bacosides A and B facilitate synaptic transmission and support neuroplasticity.")
                                        .dosageAndPreparations("Powder: 2–3 grams with ghee; Fresh juice: 10–15 ml.")
                                        .imageUrl("https://images.unsplash.com/photo-1540420773420-3366772f4999?auto=format&fit=crop&w=800&q=80")
                                        .published(true)
                                        .build();

                        PlantSafety brahmiSafety = PlantSafety.builder()
                                        .herb(brahmi)
                                        .toxicityLevel(ToxicityLevel.SAFE)
                                        .contraindications(
                                                        "Severe bradycardia (low heart rate) or thyroid abnormalities without guidance.")
                                        .interactionWarnings(
                                                        "May interact with thyroid hormone therapies and anticholinergic drugs.")
                                        .pregnancySafety(
                                                        "Use only under supervision of a registered Ayurvedic physician.")
                                        .overdoseSymptoms("Nausea, intestinal cramps, dry mouth.")
                                        .emergencyAction(
                                                        "Take with food or warm ghee to mitigate digestive sensitivity.")
                                        .build();
                        brahmi.setSafety(brahmiSafety);
                        herbRepository.save(brahmi);

                        // 6. Giloy (Guduchi)
                        Herb giloy = Herb.builder()
                                        .commonName("Giloy (Heart-leaved Moonseed)")
                                        .sanskritName("Guḍūcī / Amṛtā (अमृता)")
                                        .scientificName("Tinospora cordifolia")
                                        .botanicalFamily("Menispermaceae")
                                        .slug("giloy")
                                        .rasa("Tikta (Bitter), Kashaya (Astringent)")
                                        .virya("Ushna (Mildly Heating)")
                                        .vipaka("Madhura (Sweet)")
                                        .primaryDoshaEffect("Tridosha Shamaka (Balances all three doshas)")
                                        .partsUsed("Stem")
                                        .description("Referred to as 'Amrita' (Nectar of Immortality), Giloy is revered for immunomodulation and fever management.")
                                        .traditionalUses(
                                                        "Recurrent fevers, metabolic imbalances, liver protection, joint inflammation.")
                                        .modernResearchSummary(
                                                        "Cordifolioside and tinocordiside compounds activate macrophage phagocytosis.")
                                        .dosageAndPreparations(
                                                        "Stem Decoction (Kwath): 20–30 ml; Satva (Starch Extract): 500 mg–1 gram.")
                                        .imageUrl("https://images.unsplash.com/photo-1615485290382-441e4d049cb5?auto=format&fit=crop&w=800&q=80")
                                        .published(true)
                                        .build();

                        PlantSafety giloySafety = PlantSafety.builder()
                                        .herb(giloy)
                                        .toxicityLevel(ToxicityLevel.SAFE)
                                        .contraindications(
                                                        "Autoimmune flare-ups without clinical supervision; pre-existing chronic liver disease.")
                                        .interactionWarnings("May enhance anti-diabetic medications.")
                                        .pregnancySafety("Avoid in high doses during pregnancy.")
                                        .overdoseSymptoms("Constipation if taken without adequate fluids.")
                                        .emergencyAction("Increase water intake and reduce dosage.")
                                        .build();
                        giloy.setSafety(giloySafety);
                        herbRepository.save(giloy);

                        log.info("Seeded 6 comprehensive medicinal herbs with safety and evidence citations.");
                }
        }

        private void seedYogaPoses() {
                if (yogaPoseRepository.count() == 0) {
                        List<YogaPose> poses = List.of(
                                        YogaPose.builder()
                                                        .englishName("Mountain Pose")
                                                        .sanskritName("Tādāsana (ताडासन)")
                                                        .slug("tadasana")
                                                        .difficulty(DifficultyLevel.BEGINNER)
                                                        .category("Standing")
                                                        .doshaEffect("Grounds Vata, aligns physical stability")
                                                        .instructions("Stand upright with feet together or hip-width apart. Engage thighs, draw navel gently inward, roll shoulders back, and lengthen crown towards sky. Breathe deeply.")
                                                        .benefits("Improves spinal posture, establishes grounding awareness, strengthens arches of feet and core stability.")
                                                        .precautions("Avoid prolonged standing if experiencing severe dizziness or migraine.")
                                                        .imageUrl("https://images.unsplash.com/photo-1544367567-0f2fcb009e0b?auto=format&fit=crop&w=800&q=80")
                                                        .recommendedDurationSeconds(60)
                                                        .build(),

                                        YogaPose.builder()
                                                        .englishName("Tree Pose")
                                                        .sanskritName("Vṛkṣāsana (वृक्षासन)")
                                                        .slug("vrikshasana")
                                                        .difficulty(DifficultyLevel.BEGINNER)
                                                        .category("Balance")
                                                        .doshaEffect("Balances Vata through neuromuscular equilibrium")
                                                        .instructions("Shift weight onto standing leg. Place sole of other foot against inner calf or inner thigh (never on the knee joint). Join hands at heart center (Anjali Mudra) or extend overhead.")
                                                        .benefits("Develops mental concentration (Dharana), strengthens ankles, tones calves and glutes.")
                                                        .precautions("Avoid placing foot directly against the side of the knee joint.")
                                                        .imageUrl("https://images.unsplash.com/photo-1506126613408-eca07ce68773?auto=format&fit=crop&w=800&q=80")
                                                        .recommendedDurationSeconds(45)
                                                        .build(),

                                        YogaPose.builder()
                                                        .englishName("Cobra Pose")
                                                        .sanskritName("Bhujaṅgāsana (भुजङ्गासन)")
                                                        .slug("bhujangasana")
                                                        .difficulty(DifficultyLevel.BEGINNER)
                                                        .category("Backbend")
                                                        .doshaEffect("Stimulates Agni (digestive fire), pacifies Kapha and Vata")
                                                        .instructions("Lie prone on belly with palms beneath shoulders. Press pubic bone into mat, inhale and gently elevate chest using back strength, keeping elbows lightly bent.")
                                                        .benefits("Opens chest cavity, increases lung capacity, stimulates abdominal organs, invigorates spinal mobility.")
                                                        .precautions("Avoid during pregnancy, active peptic ulcer, or severe lumbar hernia.")
                                                        .imageUrl("https://images.unsplash.com/photo-1575052814086-f385e2e2ad1b?auto=format&fit=crop&w=800&q=80")
                                                        .recommendedDurationSeconds(30)
                                                        .build(),

                                        YogaPose.builder()
                                                        .englishName("Downward-Facing Dog")
                                                        .sanskritName("Adho Mukha Śvānāsana (अधोमुखश्वानासन)")
                                                        .slug("adho-mukha-svanasana")
                                                        .difficulty(DifficultyLevel.INTERMEDIATE)
                                                        .category("Inversion")
                                                        .doshaEffect("Calms nervous system, relieves excess Pitta from head")
                                                        .instructions("From hands and knees, tuck toes and lift hips toward the ceiling to form an inverted 'V'. Press hands evenly into mat, lengthen spine, and draw heels toward floor.")
                                                        .benefits("Lengthens hamstrings and calves, decompresses vertebrae, calms brain, strengthens upper body.")
                                                        .precautions("Carpal tunnel syndrome or severe high blood pressure.")
                                                        .imageUrl("https://images.unsplash.com/photo-1599447421416-3414500d18a5?auto=format&fit=crop&w=800&q=80")
                                                        .recommendedDurationSeconds(60)
                                                        .build(),

                                        YogaPose.builder()
                                                        .englishName("Corpse Pose")
                                                        .sanskritName("Śavāsana (शवासन)")
                                                        .slug("savasana")
                                                        .difficulty(DifficultyLevel.BEGINNER)
                                                        .category("Restorative")
                                                        .doshaEffect("Profoundly pacifies agitated Vata and cooling to Pitta")
                                                        .instructions("Lie flat on back with legs comfortably spaced and arms relaxed by sides, palms facing upward. Close eyes and systematically relax every muscle from toes to crown.")
                                                        .benefits("Activates parasympathetic nervous system, reduces blood pressure, integrates neurological benefits of asana practice.")
                                                        .precautions("Place a bolster beneath knees if experiencing lower back discomfort.")
                                                        .imageUrl("https://images.unsplash.com/photo-1544367567-0f2fcb009e0b?auto=format&fit=crop&w=800&q=80")
                                                        .recommendedDurationSeconds(300)
                                                        .build());

                        yogaPoseRepository.saveAll(poses);
                        log.info("Seeded 5 foundational Yoga Asanas.");
                }
        }

        private void seedMeditations() {
                if (meditationRepository.count() == 0) {
                        List<Meditation> sessions = List.of(
                                        Meditation.builder()
                                                        .title("Nadi Shodhana (Alternate Nostril Breathing)")
                                                        .category("Pranayama")
                                                        .durationMinutes(10)
                                                        .difficulty(DifficultyLevel.BEGINNER)
                                                        .description("Balancing pranayama technique that harmonizes Ida (Lunar/Cooling) and Pingala (Solar/Heating) energy channels.")
                                                        .instructions("Sit in a comfortable upright posture. Use Vishnu Mudra with right hand. Close right nostril with thumb, inhale through left for 4 counts. Close left nostril with ring finger, exhale right for 4 counts. Inhale right, exhale left. Repeat smoothly.")
                                                        .benefits("Balances autonomic nervous system, clears mental fog, reduces acute anxiety.")
                                                        .build(),

                                        Meditation.builder()
                                                        .title("So-Hum Breath Awareness")
                                                        .category("Mindfulness")
                                                        .durationMinutes(15)
                                                        .difficulty(DifficultyLevel.BEGINNER)
                                                        .description("Ancient Vedic mantra meditation aligning mental focus with spontaneous inhalation and exhalation.")
                                                        .instructions("Gently observe the breath without forcing. Silently synchronize the syllable 'So' with every inhalation and 'Hum' with every exhalation.")
                                                        .benefits("Deepens mindfulness, halts runaway thought loops, anchors present moment awareness.")
                                                        .build(),

                                        Meditation.builder()
                                                        .title("Bhramari (Humming Bee Breath)")
                                                        .category("Stress Relief")
                                                        .durationMinutes(8)
                                                        .difficulty(DifficultyLevel.BEGINNER)
                                                        .description("Calming acoustic vibration technique that triggers instantaneous nitric oxide production and parasympathetic resonance.")
                                                        .instructions("Close ears gently with thumbs and place index fingers over eyelids. Inhale deeply through nose. On exhalation, produce a smooth, high-pitched humming sound like a bee in the throat and cranium.")
                                                        .benefits("Instant relief from cerebral tension, anger, hyperactivity, and insomnia.")
                                                        .build());

                        meditationRepository.saveAll(sessions);
                        log.info("Seeded 3 guided meditation and pranayama practices.");
                }
        }

        private void seedAyurvedaTopics() {
                if (topicRepository.count() == 0) {
                        List<AyurvedaTopic> topics = List.of(
                                        AyurvedaTopic.builder()
                                                        .title("The Five Great Elements (Panchamahabhutas)")
                                                        .sanskritTitle("Pañcamahābhūta (पञ्चमहाभूत)")
                                                        .slug("panchamahabhutas")
                                                        .category("Fundamentals")
                                                        .doshaAssociation(DoshaType.TRIDOSHIC)
                                                        .summary("Ayurveda views everything in the universe—including the human body—as a unique manifestation of five cosmic elements.")
                                                        .content("1. Akasha (Ether/Space): Nuclear space and cavities within the body.\n2. Vayu (Air/Movement): Kinetic energy, nerve impulses, inhalation/exhalation.\n3. Tejas/Agni (Fire/Metabolism): Enzymatic transformation, digestion, intellect.\n4. Jala/Apas (Water/Cohesion): Bodily fluids, lymph, cytoplasm.\n5. Prithvi (Earth/Structure): Bones, teeth, tissues, stability.")
                                                        .traditionalReferences("Charaka Samhita, Sutrasthana Chapter 1")
                                                        .published(true)
                                                        .build(),

                                        AyurvedaTopic.builder()
                                                        .title("Understanding Tridosha: Vata, Pitta, and Kapha")
                                                        .sanskritTitle("Tridoṣa Vijñāna (त्रिदोष विज्ञान)")
                                                        .slug("tridosha-principles")
                                                        .category("Dosha")
                                                        .doshaAssociation(DoshaType.TRIDOSHIC)
                                                        .summary("The three fundamental biological humors that govern all physiological and psychological functions.")
                                                        .content("When the Five Elements combine, they form three functional forces (Doshas):\n\n- **Vata (Ether + Air)**: Controls all bodily movements, circulation, breath, and nervous transmission.\n- **Pitta (Fire + Water)**: Controls biochemical transformation, temperature, digestion, and visual acuity.\n- **Kapha (Water + Earth)**: Controls physical structure, lubrication of joints, cellular cohesion, and immune resilience.")
                                                        .traditionalReferences(
                                                                        "Ashtanga Hridaya, Sutrasthana Chapter 1")
                                                        .published(true)
                                                        .build(),

                                        AyurvedaTopic.builder()
                                                        .title("Dinacharya: The Ayurvedic Daily Routine")
                                                        .sanskritTitle("Dinacaryā (दिनचर्या)")
                                                        .slug("dinacharya-daily-routine")
                                                        .category("Dinacharya")
                                                        .doshaAssociation(DoshaType.TRIDOSHIC)
                                                        .summary("Aligning biological circadian rhythms with solar cycles for disease prevention and longevity.")
                                                        .content("Core pillars of Dinacharya include:\n\n1. **Brahma Muhurta**: Waking 45-90 minutes before sunrise.\n2. **Jihwa Nirlekhana**: Tongue scraping with copper/silver to eliminate oral Ama.\n3. **Ushapan**: Drinking warm water to encourage downward peristalsis (Apana Vayu).\n4. **Abhyanga**: Warm sesame oil self-massage before bathing to soothe Vata.\n5. **Pranayama & Asana**: Awakening vitality.\n6. **Midday Meal**: Consuming the primary meal between 12:00-1:30 PM when solar/digestive fire (Agni) peaks.")
                                                        .traditionalReferences(
                                                                        "Sushruta Samhita, Chikitsasthana Chapter 24")
                                                        .published(true)
                                                        .build());

                        topicRepository.saveAll(topics);
                        log.info("Seeded foundational Ayurveda Knowledge Topics.");
                }
        }

        private void seedArticles() {
                if (articleRepository.count() == 0) {
                        List<Article> articles = List.of(
                                        Article.builder()
                                                        .title("Circadian Rhythms & Dinacharya: Modern Chronobiology Meets Ancient Wisdom")
                                                        .slug("circadian-rhythms-and-dinacharya")
                                                        .category("Lifestyle")
                                                        .authorName("Dr. Shreya Kulkarni, BAMS, MD")
                                                        .readTimeMinutes(6)
                                                        .coverImageUrl("https://images.unsplash.com/photo-1506126613408-eca07ce68773?auto=format&fit=crop&w=800&q=80")
                                                        .summary("How the 2017 Nobel Prize discovery in circadian gene clocks mirrors the 3,000-year-old Vedic science of Dinacharya time windows.")
                                                        .content("Modern chronobiology has affirmed that cellular repair, digestive enzymes, and melatonin synthesis operate on strict 24-hour oscillating clocks. In classical Ayurveda, each 4-hour window of the day is governed by a distinct Dosha:\n\n- **6:00 AM - 10:00 AM (Kapha)**: Heavy, grounding. Optimal time for exercise.\n- **10:00 AM - 2:00 PM (Pitta)**: Peak enzymatic capacity. Ideal time for largest meal.\n- **2:00 PM - 6:00 PM (Vata)**: High mental agility and creativity.\n- **6:00 PM - 10:00 PM (Kapha)**: Natural winding down and sleep preparation.")
                                                        .referencesContent(
                                                                        "Panda S. (2016). Circadian physiology of metabolism. Science, 354(6315), 1008-1015.")
                                                        .published(true)
                                                        .build(),

                                        Article.builder()
                                                        .title("The Science of Adaptogens: How Ashwagandha Regulates the HPA Axis")
                                                        .slug("science-of-adaptogens-ashwagandha")
                                                        .category("Research & Evidence")
                                                        .authorName("AyurVana Research Editorial Board")
                                                        .readTimeMinutes(5)
                                                        .coverImageUrl("https://images.unsplash.com/photo-1615485290382-441e4d049cb5?auto=format&fit=crop&w=800&q=80")
                                                        .summary("A deep dive into hypothalamic-pituitary-adrenal modulation and cortisol balancing through Withania somnifera.")
                                                        .content("Chronic psychological stress triggers persistent hyperactivation of the hypothalamic-pituitary-adrenal (HPA) axis. Ashwagandha acts as a systemic adaptogen by stabilizing corticotropin-releasing hormone pathways...")
                                                        .referencesContent(
                                                                        "Chandrasekhar K, et al. (2012). Indian J Psychol Med, 34(3):255-262.")
                                                        .published(true)
                                                        .build());

                        articleRepository.saveAll(articles);
                        log.info("Seeded evidence-grounded wellness articles.");
                }
        }
}
