package com.ayurvana.config.initializer;

import com.ayurvana.model.entity.Meditation;
import com.ayurvana.model.entity.YogaPose;
import com.ayurvana.model.enums.DifficultyLevel;
import com.ayurvana.model.enums.DoshaType;
import com.ayurvana.repository.MeditationRepository;
import com.ayurvana.repository.YogaPoseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class YogaMeditationDataSeeder {

    private final YogaPoseRepository yogaPoseRepository;
    private final MeditationRepository meditationRepository;

    public void seedYogaAndMeditation() {
        if (yogaPoseRepository.count() < 15) {
            seedYoga();
        }
        if (meditationRepository.count() < 10) {
            seedMeditations();
        }
    }

    private void seedYoga() {
        List<YogaPose> poses = new ArrayList<>();

        // 1. Surya Namaskar
        poses.add(buildPose("Sun Salutations (12 Asana Cycle)", "Sūrya Namaskāra (सूर्य नमस्कार)", "surya-namaskar",
                DifficultyLevel.BEGINNER, DoshaType.TRIDOSHIC, "Vinyasa Flow / Full Body Awakening",
                "Perform the 12 classical fluid postures synchronized with inhalation and exhalation: Pranamasana, Hastauttanasana, Hastapadasana, Ashwa Sanchalanasana, Dandasana, Ashtanga Namaskara, Bhujangasana, Parvatasana, Ashwa Sanchalanasana, Hastapadasana, Hastauttanasana, Pranamasana.",
                "Ignites digestive Agni, improves cardiovascular circulation, lubricates all major joints, activates solar plexus (Manipura Chakra).",
                "Severe spinal disc prolapse, advanced hypertension, active wrist injuries.",
                "Hatha Yoga Pradipika & Yajurveda", 360,
                "https://images.unsplash.com/photo-1544367567-0f2fcb009e0b?auto=format&fit=crop&w=800&q=80"));

        // 2. Padmasana
        poses.add(buildPose("Lotus Pose", "Padmāsana (पद्मासन)", "padmasana",
                DifficultyLevel.INTERMEDIATE, DoshaType.TRIDOSHIC, "Seated Meditation Asana",
                "Sit on the floor with legs extended. Bend right knee and place right foot high on left thigh with sole turned upward. Bend left knee and place left foot on right thigh. Spine erect, hands in Jnana Mudra.",
                "Stabilizes the mind for deep Dharana (concentration), channels Apana Vayu upward into Sushumna Nadi, creates spinal stability.",
                "Knee ligament tears, sciatica, acute ankle sprains.",
                "Hatha Yoga Pradipika 1/44", 300,
                "https://images.unsplash.com/photo-1506126613408-eca07ce68773?auto=format&fit=crop&w=800&q=80"));

        // 3. Tadasana
        poses.add(buildPose("Mountain Pose", "Tāḍāsana (ताडासन)", "tadasana",
                DifficultyLevel.BEGINNER, DoshaType.VATA, "Standing Foundation",
                "Stand with big toes touching, heels slightly apart. Ground through all four corners of both feet. Engage quadriceps, lengthen spine, broaden collarbones, arms relaxed alongside body.",
                "Improves neuro-muscular postural alignment, grounds erratic Vata energy, strengthens core and calves.",
                "Severe dizziness or low blood pressure vertigo.",
                "Gheranda Samhita 2/2", 180,
                "https://images.unsplash.com/photo-1575052814086-f385e2e2ad1b?auto=format&fit=crop&w=800&q=80"));

        // 4. Vrikshasana
        poses.add(buildPose("Tree Pose", "Vṛkṣāsana (वृक्षासन)", "vrikshasana",
                DifficultyLevel.BEGINNER, DoshaType.VATA, "Standing Balance",
                "From Tadasana, shift weight onto left foot. Bend right knee, placing the sole of the right foot high on the inner left thigh (avoid knee joint). Bring palms together at Anjali Mudra before chest or overhead.",
                "Enhances single-pointed concentration (Ekagrata), calms nervous anxiety, strengthens ankles and pelvic stability.",
                "Active vertigo, acute inner ear infections.",
                "Gheranda Samhita 2/36", 120,
                "https://images.unsplash.com/photo-1506126613408-eca07ce68773?auto=format&fit=crop&w=800&q=80"));

        // 5. Bhujangasana
        poses.add(buildPose("Cobra Pose", "Bhujaṅgāsana (भुजङ्गासन)", "bhujangasana",
                DifficultyLevel.BEGINNER, DoshaType.KAPHA, "Backbend & Chest Opener",
                "Lie prone on abdomen, feet hip-width apart. Place palms under shoulders, elbows hugged close to ribs. Inhale and gently elevate chest using back strength, keeping pubic bone grounded.",
                "Opens bronchial chest channels (Pranavaha Srotas), stimulates adrenal glands, reduces Kapha lethargy and asthma.",
                "Abdominal surgery within 6 months, pregnancy, acute lumbar disc hernia.",
                "Hatha Yoga Pradipika 1/65", 60,
                "https://images.unsplash.com/photo-1544367567-0f2fcb009e0b?auto=format&fit=crop&w=800&q=80"));

        // 6. Paschimottanasana
        poses.add(buildPose("Seated Forward Bend", "Paścimottānāsana (पश्चिमोत्तानासन)", "paschimottanasana",
                DifficultyLevel.INTERMEDIATE, DoshaType.PITTA, "Seated Forward Fold",
                "Sit with legs straight ahead in Dandasana. Inhale, lengthen spine upward; exhale, hinge from hip creases reaching towards toes. Keep chest open and spine long.",
                "Kindles gastric fire (Jatharagni), massages liver and kidneys, cools internal Pitta heat, soothes high blood pressure.",
                "Severe hamstring tears, acute sciatica, lumbar disc prolapse.",
                "Hatha Yoga Pradipika 1/28", 180,
                "https://images.unsplash.com/photo-1575052814086-f385e2e2ad1b?auto=format&fit=crop&w=800&q=80"));

        // 7. Trikonasana
        poses.add(buildPose("Extended Triangle Pose", "Utthita Trikoṇāsana (उत्थित त्रिकोणासन)", "trikonasana",
                DifficultyLevel.BEGINNER, DoshaType.TRIDOSHIC, "Standing Lateral Stretch",
                "Step feet 3.5 to 4 feet apart. Turn right foot 90 degrees outward, left foot 15 degrees inward. Extend arms parallel to floor; reach right hand down to right shin or floor while extending left arm toward ceiling.",
                "Stimulates peristalsis, relieves backache, improves thoracic lung capacity, trims waistline fat.",
                "Severe neck spondylosis (keep gaze straight ahead rather than looking up).",
                "Light on Yoga by BKS Iyengar", 90,
                "https://images.unsplash.com/photo-1544367567-0f2fcb009e0b?auto=format&fit=crop&w=800&q=80"));

        // 8. Virabhadrasana I & II
        poses.add(buildPose("Warrior Pose I & II", "Vīrabhadrāsana (वीरभद्रासन)", "virabhadrasana",
                DifficultyLevel.BEGINNER, DoshaType.KAPHA, "Standing Power & Stamina",
                "Step feet 4 feet apart. Turn front knee to 90-degree flexion over ankle. Square hips forward with arms reaching skyward for Warrior I; open hips and extend arms horizontally for Warrior II.",
                "Builds physical courage, expands lung capacity, destroys Kapha inertia, tones quadriceps and core.",
                "Severe cardiac conditions, high blood pressure (keep hands on hips).",
                "Classical Yoga Tradition", 120,
                "https://images.unsplash.com/photo-1506126613408-eca07ce68773?auto=format&fit=crop&w=800&q=80"));

        // 9. Sarvangasana
        poses.add(buildPose("Shoulder Stand (Queen of Asanas)", "Sarvāṅgāsana (सर्वाङ्गासन)", "sarvangasana",
                DifficultyLevel.ADVANCED, DoshaType.TRIDOSHIC, "Inversion & Thyroid Tonic",
                "Lie supine, lift legs and hips vertically, supporting mid-back with palms. Chin presses gently against jugular notch (Jalandhara Bandha). Breathe smoothly.",
                "Regulates thyroid and parathyroid glands, increases venous return to heart, soothes nervous exhaustion.",
                "Cervical spondylosis, high blood pressure, glaucoma, menstruation, retinal detachment.",
                "Hatha Yoga Pradipika 3/78", 180,
                "https://images.unsplash.com/photo-1575052814086-f385e2e2ad1b?auto=format&fit=crop&w=800&q=80"));

        // 10. Matsyasana
        poses.add(buildPose("Fish Pose", "Matsyāsana (मत्स्यासन)", "matsyasana",
                DifficultyLevel.INTERMEDIATE, DoshaType.KAPHA, "Supine Backbend",
                "Lie supine, place hands under buttocks. Press forearms down, lift chest skyward, and gently rest the crown of the head on the mat, expanding throat and chest.",
                "Counter-pose to Sarvangasana, relieves asthma and bronchial spasms, stimulates thymus and thyroid.",
                "Severe cervical spine injury, severe migraine.",
                "Gheranda Samhita 2/21", 90,
                "https://images.unsplash.com/photo-1544367567-0f2fcb009e0b?auto=format&fit=crop&w=800&q=80"));

        // 11. Dhanurasana
        poses.add(buildPose("Bow Pose", "Dhanurāsana (धनुरासन)", "dhanurasana",
                DifficultyLevel.INTERMEDIATE, DoshaType.KAPHA, "Prone Backbend",
                "Lie prone, bend knees and reach back to grasp ankles. Inhale, lift chest and thighs off floor simultaneously, creating the shape of a taut archer's bow.",
                "Massages abdominal organs, cures sluggish constipation, strengthens dorsal spine and shoulder girdle.",
                "Hernia, peptic ulcers, high blood pressure, pregnancy.",
                "Hatha Yoga Pradipika 1/25", 60,
                "https://images.unsplash.com/photo-1506126613408-eca07ce68773?auto=format&fit=crop&w=800&q=80"));

        // 12. Shavasana
        poses.add(buildPose("Corpse Pose (Conscious Relaxation)", "Śavāsana (शवासन)", "shavasana",
                DifficultyLevel.BEGINNER, DoshaType.VATA, "Restorative & Nervous Reset",
                "Lie flat on back, legs hip-distance apart, feet splayed naturally. Arms 45 degrees from torso with palms facing up. Close eyes, release all muscular tension.",
                "Eliminates mental and physical fatigue, lowers blood pressure, integrates energy from yoga practice, calms Vata.",
                "None; essential for all practitioners.",
                "Hatha Yoga Pradipika 1/32", 600,
                "https://images.unsplash.com/photo-1575052814086-f385e2e2ad1b?auto=format&fit=crop&w=800&q=80"));

        // 13. Nadi Shodhana Pranayama
        poses.add(buildPose("Alternate Nostril Breathing (Nadi Shodhana)", "Nāḍī Śodhana (नाडी शोधन)", "nadi-shodhana",
                DifficultyLevel.BEGINNER, DoshaType.TRIDOSHIC, "Pranayama / Channel Purification",
                "Sit in Padmasana. Use Vishnu Mudra on right hand. Close right nostril with thumb, inhale left for 4 counts. Close both, hold for 4 counts. Exhale right for 4 counts. Inhale right, hold, exhale left.",
                "Purifies 72,000 psychic nadis (energy channels), harmonizes left and right brain hemispheres, reduces heart rate and autonomic anxiety.",
                "Active nasal obstruction, acute respiratory distress (practice without breath retention).",
                "Gheranda Samhita 5/38", 600,
                "https://images.unsplash.com/photo-1506126613408-eca07ce68773?auto=format&fit=crop&w=800&q=80"));

        // 14. Kapalabhati Pranayama
        poses.add(buildPose("Skull Shining Breath (Kapalabhati)", "Kapālabhāti (कपालभाति)", "kapalabhati",
                DifficultyLevel.INTERMEDIATE, DoshaType.KAPHA, "Shatkriya / Detox Cleansing Breath",
                "Sit with erect spine. Inhale passively; exhale forcefully by rapidly contracting abdominal muscles towards spine. Repeat at 1 to 2 strokes per second for 30–60 strokes per round.",
                "Clears mucus from sinuses and lungs, cleanses frontal lobe of brain, stimulates digestion, destroys sluggish Kapha.",
                "Pregnancy, hernia, cardiac conditions, epilepsy, active hyperacidity.",
                "Hatha Yoga Pradipika 2/35", 300,
                "https://images.unsplash.com/photo-1544367567-0f2fcb009e0b?auto=format&fit=crop&w=800&q=80"));

        // 15. Sheetali & Sheetkari
        poses.add(buildPose("Cooling Breath (Sheetali & Sheetkari)", "Śītalī Prāṇāyāma (शीतली प्राणायाम)", "sheetali-pranayama",
                DifficultyLevel.BEGINNER, DoshaType.PITTA, "Cooling Pranayama",
                "Roll tongue into a tube (or clench teeth gently for Sheetkari). Inhale slowly through curled tongue creating a cool hiss. Close mouth and exhale smoothly through both nostrils.",
                "Instantly cools core body temperature, quenches thirst and hunger, reduces anger, skin inflammation, and fever.",
                "Severe cold, cough, chronic bronchitis, low blood pressure.",
                "Hatha Yoga Pradipika 2/57", 300,
                "https://images.unsplash.com/photo-1575052814086-f385e2e2ad1b?auto=format&fit=crop&w=800&q=80"));

        // 16. Bhramari Pranayama
        poses.add(buildPose("Humming Bee Breath (Bhramari)", "Bhrāmarī (भ्रामरी)", "bhramari-pranayama",
                DifficultyLevel.BEGINNER, DoshaType.TRIDOSHIC, "Calming Resonance",
                "Close eyes and cover ears with thumbs (Shanmukhi Mudra). Inhale deeply through nose; exhale while producing a steady, smooth, low-pitched humming sound like a female bee.",
                "Increases nasal nitric oxide production 15-fold, induces deep parasympathetic relaxation, dissolves tension and anger.",
                "Severe active ear infections.",
                "Hatha Yoga Pradipika 2/68", 300,
                "https://images.unsplash.com/photo-1506126613408-eca07ce68773?auto=format&fit=crop&w=800&q=80"));

        yogaPoseRepository.saveAll(poses);
        log.info("YogaMeditationDataSeeder: Seeded {} classical Yoga Asanas & Pranayamas.", poses.size());
    }

    private void seedMeditations() {
        List<Meditation> meditations = new ArrayList<>();

        // 1. Om Chanting
        meditations.add(buildMeditation("Om (AUM) Primordial Sound Meditation", "Oṃkāra Dhyāna (ओंकार ध्यान)", "om-primordial-meditation",
                "Vibrational Sound & Mantra", DoshaType.TRIDOSHIC, 15,
                "Experience the primordial cosmic sound vibration (Pranava) as it resonates through the physical, astral, and causal bodies.",
                "Calms fluctuating mental chatter (Vrittis), balances heart rhythm, aligns energetic chakras.",
                "Sit comfortably with spine erect. Take a deep diaphragmatic breath. On exhalation, chant 'A-U-M' allowing 'A' to resonate in the lower abdomen, 'U' in the chest, and 'M' in the skull. Conclude with silent awareness.",
                "https://assets.mixkit.co/music/preview/mixkit-meditation-flute-and-nature-sounds-122.mp3",
                "https://images.unsplash.com/photo-1506126613408-eca07ce68773?auto=format&fit=crop&w=800&q=80"));

        // 2. So-Hum Breath
        meditations.add(buildMeditation("So-Hum Breath Awareness Meditation", "So'ham Dhyāna (सोऽहं ध्यान)", "so-hum-breath-meditation",
                "Ajapa Japa Breath Sync", DoshaType.VATA, 20,
                "Natural spontaneous mantra synchronization with inhalation ('So' - I am That) and exhalation ('Hum' - That I am).",
                "Eliminates existential anxiety, roots scattered Vata energy, unites individual consciousness with universal awareness.",
                "Close your eyes and breathe naturally. Silently hear the internal sound 'So' on the inhale and 'Hum' on the exhale without forcing breath.",
                "https://assets.mixkit.co/music/preview/mixkit-serene-view-443.mp3",
                "https://images.unsplash.com/photo-1544367567-0f2fcb009e0b?auto=format&fit=crop&w=800&q=80"));

        // 3. Yoga Nidra
        meditations.add(buildMeditation("Yoga Nidra (Deep Psychosomatic Sleep)", "Yoga Nidrā (योग निद्रा)", "yoga-nidra-deep-relaxation",
                "Rotational Somatic Awareness", DoshaType.VATA, 30,
                "The sleep of the yogis; a state of conscious deep relaxation between waking and dreaming that releases deep-rooted physical and emotional tension.",
                "Reduces chronic insomnia, alleviates PTSD and nervous burnout, boosts neuroplasticity and emotional resilience.",
                "Lie supine in Shavasana. Systematically rotate awareness through 61 points on the body, sensations of heaviness and lightness, and guided visualization.",
                "https://assets.mixkit.co/music/preview/mixkit-valley-sunset-127.mp3",
                "https://images.unsplash.com/photo-1575052814086-f385e2e2ad1b?auto=format&fit=crop&w=800&q=80"));

        // 4. Trataka
        meditations.add(buildMeditation("Trataka (Candle Flame Gaze)", "Trāṭaka (त्राटक)", "trataka-candle-gaze",
                "Visual Dharana / Third Eye Focus", DoshaType.KAPHA, 15,
                "Classical yogic gazing at a steady ghee lamp or candle flame to build laser-like mental concentration and awaken Ajna Chakra.",
                "Improves eyesight, dispels mental lethargy and brain fog, awakens intuitive insight and memory.",
                "Place a ghee lamp at eye level 3 feet away. Gaze steadily at the tip of the wick without blinking until eyes water slightly. Close eyes and visualize the after-image at the eyebrow center.",
                "https://assets.mixkit.co/music/preview/mixkit-spirit-of-the-east-114.mp3",
                "https://images.unsplash.com/photo-1515377905703-c4788e51af15?auto=format&fit=crop&w=800&q=80"));

        // 5. Chakra Bija Mantra
        meditations.add(buildMeditation("Chakra Bija Mantra Harmonization", "Cakra Bīja Dhyāna (चक्र बीज ध्यान)", "chakra-bija-meditation",
                "Vocal Vibrational Alignment", DoshaType.TRIDOSHIC, 25,
                "Tune the 7 primary subtle nerve plexuses along the spine using the sacred root sounds (LAM, VAM, RAM, YAM, HAM, OM).",
                "Releases emotional blockages stored in vital organs, balances endocrine glands, invigorates Kundalini energy.",
                "Focus on Muladhara (LAM), Svadhisthana (VAM), Manipura (RAM), Anahata (YAM), Vishuddha (HAM), Ajna (OM). Vibrate each sound 3 to 7 times.",
                "https://assets.mixkit.co/music/preview/mixkit-relaxing-in-nature-522.mp3",
                "https://images.unsplash.com/photo-1598511726623-d2e9996892f0?auto=format&fit=crop&w=800&q=80"));

        // 6. Metta Loving Kindness
        meditations.add(buildMeditation("Maitri (Loving Kindness & Compassion)", "Maitrī Bhāvanā (मैत्री भावना)", "metta-loving-kindness",
                "Heart-Centered Contemplation", DoshaType.PITTA, 20,
                "Cultivate unconditional goodwill, compassion, and forgiveness toward yourself, loved ones, neutral acquaintances, and difficult people.",
                "Melts resentment and anger, lowers blood pressure, enhances social connection and vagal tone.",
                "Place hands over spiritual heart space (Hridayakasha). Repeat silent affirmations of peace, freedom from suffering, and joy radiating in all directions.",
                "https://assets.mixkit.co/music/preview/mixkit-serene-view-443.mp3",
                "https://images.unsplash.com/photo-1506126613408-eca07ce68773?auto=format&fit=crop&w=800&q=80"));

        meditationRepository.saveAll(meditations);
        log.info("YogaMeditationDataSeeder: Seeded {} classical guided meditations.", meditations.size());
    }

    private YogaPose buildPose(String engName, String sktName, String slug, DifficultyLevel diff,
                               DoshaType dosha, String cat, String instructions, String benefits,
                               String contra, String ref, int duration, String img) {
        return YogaPose.builder()
                .englishName(engName)
                .sanskritName(sktName)
                .slug(slug)
                .difficultyLevel(diff)
                .targetDosha(dosha)
                .category(cat)
                .instructions(instructions)
                .benefits(benefits)
                .contraindications(contra)
                .shlokaReference(ref)
                .durationSeconds(duration)
                .imageUrl(img)
                .build();
    }

    private Meditation buildMeditation(String title, String sktTitle, String slug, String technique,
                                       DoshaType dosha, int duration, String desc, String benefits,
                                       String instructions, String audioUrl, String imgUrl) {
        return Meditation.builder()
                .title(title)
                .sanskritTitle(sktTitle)
                .slug(slug)
                .technique(technique)
                .targetDosha(dosha)
                .durationMinutes(duration)
                .description(desc)
                .benefits(benefits)
                .instructions(instructions)
                .audioUrl(audioUrl)
                .imageUrl(imgUrl)
                .build();
    }
}
