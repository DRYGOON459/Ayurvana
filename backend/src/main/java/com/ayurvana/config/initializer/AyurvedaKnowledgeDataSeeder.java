package com.ayurvana.config.initializer;

import com.ayurvana.model.entity.Article;
import com.ayurvana.model.entity.AyurvedaTopic;
import com.ayurvana.repository.ArticleRepository;
import com.ayurvana.repository.AyurvedaTopicRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AyurvedaKnowledgeDataSeeder {

    private final AyurvedaTopicRepository topicRepository;
    private final ArticleRepository articleRepository;

    public void seedKnowledge() {
        if (topicRepository.count() < 10) {
            seedTopics();
        }
        if (articleRepository.count() < 10) {
            seedArticles();
        }
    }

    private void seedTopics() {
        List<AyurvedaTopic> topics = new ArrayList<>();

        // 1. Tridosha
        topics.add(buildTopic("Tridosha: The Three Biological Energies", "Tridoṣa Siddhānta (त्रिदोष सिद्धान्त)", "tridosha-principles",
                "Fundamental Principles",
                "The core pillar of Ayurvedic physiology and pathology: Vata (Kinetic/Air), Pitta (Transformative/Fire), and Kapha (Cohesive/Water).",
                "Every human physiology is governed by three fundamental biological forces known as Doshas: Vata governs movement, neurotransmission, respiration, and cellular division; Pitta regulates metabolic transformation, digestion, hormonal enzymes, and body temperature; Kapha maintains structural integrity, cellular lubrication, joint cushioning, and biological immunity. Health (Svasthya) is the dynamic equilibrium of these three forces relative to one's unique constitutional baseline (Prakriti).",
                "Essential for diagnosing disease root cause (Samprapti) and tailoring personalized diets, herbs, and therapies.",
                "Charaka Samhita, Sutra Sthana 1/57; Ashtanga Hridaya, Sutra Sthana 1/6",
                "https://images.unsplash.com/photo-1544367567-0f2fcb009e0b?auto=format&fit=crop&w=800&q=80"));

        // 2. Sapta Dhatu
        topics.add(buildTopic("Sapta Dhatu: The Seven Fundamental Tissues", "Sapta Dhātu (सप्त धातु)", "sapta-dhatu-tissues",
                "Anatomy & Physiology",
                "The sequential continuum of seven structural bodily tissues: Rasa, Rakta, Mamsa, Meda, Asthi, Majja, and Shukra.",
                "Ingested nutrition is converted by Agni into nutrient plasma (Ahara Rasa), which feeds the seven structural tissues in sequential transformation: 1. Rasa (Plasma & lymph), 2. Rakta (Red blood cells & oxygenation), 3. Mamsa (Muscle tissue), 4. Meda (Adipose & lipids), 5. Asthi (Bones & teeth), 6. Majja (Bone marrow & nervous system), and 7. Shukra/Artava (Reproductive essence & genetic vitality). The supreme refined essence of all seven tissues is Ojas (supreme immunity).",
                "Helps pinpoint deep-seated tissue pathology and determine depth of herbal penetration.",
                "Charaka Samhita, Chikitsa Sthana 15; Sushruta Samhita, Sutra Sthana 14",
                "https://images.unsplash.com/photo-1506126613408-eca07ce68773?auto=format&fit=crop&w=800&q=80"));

        // 3. Agni: The 4 Digestive States
        topics.add(buildTopic("Agni: The Biological Fire & Metabolism", "Agni Vicāra (अग्नि विचार)", "agni-metabolic-fire",
                "Digestion & Metabolism",
                "The metabolic fire responsible for digestion, absorption, cellular cellular transformation, and mental discernment.",
                "Ayurveda states that all chronic diseases begin with impaired Agni (Rogaah Sarvepi Mande Agnau). There are four clinical states of Agni: 1. Sama Agni (Balanced, optimal digestion), 2. Vishama Agni (Irregular, fluctuating digestion characteristic of Vata), 3. Tikshna Agni (Intense, burning hyperacidity characteristic of Pitta), and 4. Manda Agni (Sluggish, slow metabolism characteristic of Kapha).",
                "Treatment in Ayurveda always begins with rehabilitating Agni (Deepana & Pachana).",
                "Charaka Samhita, Chikitsa Sthana 15/3",
                "https://images.unsplash.com/photo-1575052814086-f385e2e2ad1b?auto=format&fit=crop&w=800&q=80"));

        // 4. Ama & Detoxification
        topics.add(buildTopic("Ama: Metabolic Endotoxins & Cellular Waste", "Āma Doṣa (आम दोष)", "ama-endotoxins",
                "Pathology & Cleansing",
                "Undigested toxic metabolic residue that clogs biological channels (Srotas), dulls immunity, and triggers inflammation.",
                "When Agni is weak, food is not properly metabolized, forming a sticky, foul-smelling, cold, unctuous toxic sludge called Ama. Ama circulates through the bloodstream, adheres to weak organ sites (Khavaigunya), obstructs intercellular communication, and initiates autoimmune, degenerative, and inflammatory diseases.",
                "Fundamental to identifying tongue coating (Jihva Pariksha) and initiating Shodhana (detox) protocols.",
                "Ashtanga Hridaya, Sutra Sthana 13/25",
                "https://images.unsplash.com/photo-1515377905703-c4788e51af15?auto=format&fit=crop&w=800&q=80"));

        // 5. Dinacharya: Classical Daily Routine
        topics.add(buildTopic("Dinacharya: The Ayurvedic Daily Circadian Routine", "Dinacaryā (दिनचर्या)", "dinacharya-daily-routine",
                "Lifestyle & Prevention",
                "The classical daily regimen synchronizing human physiological rhythms with nature's circadian cycle.",
                "Waking before sunrise (Brahma Muhurta), tongue scraping (Jihva Nirlekhana) to remove overnight Ama, oil pulling (Gandusha), nasal oil application (Nasya), warm herbal body oil massage (Abhyanga), mindful exercise (Vyayama), midday main meal when solar fire peaks, and digital curfew by 9:30 PM.",
                "The foundation of preventive medicine (Swasthavritta) and hormonal synchronization.",
                "Ashtanga Hridaya, Sutra Sthana 2; Charaka Samhita, Sutra Sthana 5",
                "https://images.unsplash.com/photo-1540420773420-3366772f4999?auto=format&fit=crop&w=800&q=80"));

        // 6. Ritucharya: Seasonal Living across 6 Ritus
        topics.add(buildTopic("Ritucharya: Seasonal Regimens across Six Indian Ritus", "Ṛtucaryā (ऋतुचर्या)", "ritucharya-seasonal-living",
                "Seasonal Medicine",
                "Preventive adaptation of diet, herbs, and physical activity across the six bi-monthly seasons.",
                "1. Shishira (Late Winter - Heavy nourishing foods), 2. Vasanta (Spring - Bitter herbs, Panchakarma detox to liquefy Kapha), 3. Grishma (Summer - Cooling fluids, sweet fruits, avoiding direct noon sun), 4. Varsha (Monsoon - Gentle warm foods, kindle dampened Agni), 5. Sharad (Autumn - Ghee, sweet cooling herbs to purge accumulated Pitta), 6. Hemanta (Early Winter - Rejuvenative Rasayanas, warm spices).",
                "Prevents seasonal disease outbreaks, respiratory allergies, and seasonal affective imbalances.",
                "Charaka Samhita, Sutra Sthana 6; Ashtanga Hridaya, Sutra Sthana 3",
                "https://images.unsplash.com/photo-1509358271058-acd22cc93898?auto=format&fit=crop&w=800&q=80"));

        // 7. Panchakarma: The 5 Biocleansing Therapies
        topics.add(buildTopic("Panchakarma: The Five Classical Biocleansing Therapies", "Pañcakarma (पञ्चकर्म)", "panchakarma-purification",
                "Therapeutics & Clinical Shodhana",
                "The clinical detoxification protocol that eradicates deep-seated chronic disease at the root level.",
                "Comprises preparatory procedures (Purvakarma: Snehana oleation and Swedana herbal steam) followed by the 5 main therapies: 1. Vamana (Therapeutic emesis for Kapha disorders), 2. Virechana (Therapeutic purgation for Pitta disorders), 3. Basti (Medicated herbal enemas - the master therapy for Vata), 4. Nasya (Nasal administration for cranial/sensory disorders), and 5. Raktamokshana (Bloodletting for chronic skin & vascular toxicities).",
                "The cornerstone of inpatient and specialized clinical Ayurvedic hospital management.",
                "Charaka Samhita, Siddhi Sthana; Sushruta Samhita, Chikitsa Sthana",
                "https://images.unsplash.com/photo-1563178406-4cdc2923acbc?auto=format&fit=crop&w=800&q=80"));

        // 8. Ojas, Tejas & Prana
        topics.add(buildTopic("Ojas, Tejas & Prana: The Three Master Subtle Essences", "Ojas-Tejas-Prāṇa (ओजस्-तेजस्-प्राण)", "subtle-essences-ojas",
                "Subtle Energetics",
                "The refined master subtle counterparts of Kapha, Pitta, and Vata governing biological immunity, mental discernment, and vital life force.",
                "Prana is the subtle breath of life coordinating all cellular intelligence and nervous impulses. Tejas is the inner flame of clarity, courage, and cellular transformation. Ojas is the supreme golden nectar of vitality, resilience, and radiant health synthesized from the complete digestion of all seven tissues.",
                "Nurturing Ojas is the ultimate objective of Ayurvedic anti-aging (Rasayana) and clinical recovery.",
                "Charaka Samhita, Sutra Sthana 17/74",
                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?auto=format&fit=crop&w=800&q=80"));

        topicRepository.saveAll(topics);
        log.info("AyurvedaKnowledgeDataSeeder: Seeded {} fundamental Ayurvedic topics.", topics.size());
    }

    private void seedArticles() {
        List<Article> articles = new ArrayList<>();

        // 1. Ashwagandha Cortisol Study
        articles.add(buildArticle(
                "A Prospective, Randomized Double-Blind, Placebo-Controlled Study of High-Concentration Ashwagandha Root Extract in Reducing Stress and Anxiety",
                "ashwagandha-cortisol-clinical-study",
                "Dr. K. Chandrasekhar, MD, DPM", "Director of Neuropsychiatry, Asha Hospital",
                "Clinical Neuropharmacology",
                "Ashwagandha (Withania somnifera) root extract significantly reduced serum cortisol levels and attenuated perceived stress scores in adults with chronic stress.",
                "This randomized double-blind clinical trial evaluated 64 subjects with a history of chronic stress. Subjects in the treatment group received 300 mg of standardized high-concentration full-spectrum Ashwagandha root extract twice daily for 60 days. The treatment group demonstrated a statistically significant 27.9% reduction in serum cortisol levels (p < 0.0001) compared to the placebo group. Scores on the Perceived Stress Scale (PSS) decreased by 44.0% in the Ashwagandha group compared to 5.5% in placebo.",
                "Indian Journal of Psychological Medicine. 2012; 34(3):255–262", "10.4103/0253-7176.106022",
                "https://images.unsplash.com/photo-1615485290382-441e4d049cb5?auto=format&fit=crop&w=800&q=80", 6));

        // 2. Curcumin in Osteoarthritis
        articles.add(buildArticle(
                "Efficacy and Safety of Curcuminoids in Knee Osteoarthritis: A Systematic Review and Meta-Analysis of Randomized Clinical Trials",
                "curcumin-osteoarthritis-meta-analysis",
                "Dr. Amirhossein Sahebkar, PhD", "Biotechnology Research Center, Mashhad University of Medical Sciences",
                "Rheumatology & Integrative Medicine",
                "Curcumin extracts demonstrate significant pain reduction and functional joint improvement comparable to ibuprofen with superior gastrointestinal tolerability.",
                "A meta-analysis of randomized controlled clinical trials evaluating standardized Curcuma longa extracts in knee osteoarthritis patients. The synthesized findings show marked reductions in WOMAC pain scores, stiffness metrics, and systemic inflammatory markers (hs-CRP, IL-1beta). Adverse event incidence was 68% lower in the curcumin group compared to synthetic NSAID control groups.",
                "Phytotherapy Research. 2019; 33(5):1252–1267", "10.1002/ptr.6324",
                "https://images.unsplash.com/photo-1598511726623-d2e9996892f0?auto=format&fit=crop&w=800&q=80", 7));

        // 3. Brahmi Cognitive Nootropic
        articles.add(buildArticle(
                "Chronic Effects of Brahmi (Bacopa monnieri) on Cognitive Function in Healthy Adults: Double-Blind Clinical Investigation",
                "brahmi-cognitive-function-study",
                "Dr. Con Stough, PhD", "Brain Sciences Institute, Swinburne University",
                "Neuroscience & Nootropics",
                "Standardized Bacopa monnieri extract significantly enhanced working memory, speed of visual information processing, and memory consolidation.",
                "A double-blind, placebo-controlled clinical trial involving 76 healthy adult subjects aged 40 to 65. Participants were administered 300 mg of standardized Bacopa extract daily over a 12-week intervention period. Significant improvements were observed in Rey Auditory Verbal Learning Test (AVLT) recall scores (p < 0.01) and rate of information processing without sedative side effects.",
                "Neuropsychopharmacology. 2002; 27(2):279–281", "10.1016/S0893-133X(01)00419-5",
                "https://images.unsplash.com/photo-1540420773420-3366772f4999?auto=format&fit=crop&w=800&q=80", 5));

        // 4. Triphala Microbiome Modulation
        articles.add(buildArticle(
                "Therapeutic Uses of Triphala in Ayurvedic Medicine and Its Interaction with the Human Gut Microbiome",
                "triphala-gut-microbiome-study",
                "Dr. Christine Tara Peterson, PhD", "Department of Family Medicine & Public Health, UC San Diego",
                "Gastroenterology & Microbiome",
                "Triphala functions as a potent prebiotic, selectively promoting beneficial Bifidobacteria and Lactobacillus while suppressing pathogenic colonic microbes.",
                "High-throughput 16S rRNA gene sequencing revealed that polyphenols and organic acids in Triphala promote gut microbial diversity, stimulate short-chain fatty acid (SCFA) production, and strengthen intestinal epithelial tight junctions, preventing leaky gut syndrome and metabolic endotoxemia.",
                "Journal of Alternative and Complementary Medicine. 2017; 23(8):607–614", "10.1089/acm.2017.0083",
                "https://images.unsplash.com/photo-1509358271058-acd22cc93898?auto=format&fit=crop&w=800&q=80", 8));

        // 5. Nadi Shodhana Autonomic Nervous System
        articles.add(buildArticle(
                "Immediate Effects of Alternate Nostril Breathing (Nadi Shodhana) on Heart Rate Variability and Autonomic Nervous Balance",
                "nadi-shodhana-hrv-study",
                "Dr. Shirley Telles, PhD, MBBS", "Patanjali Research Foundation",
                "Cardiovascular & Yogic Sciences",
                "15 minutes of Nadi Shodhana pranayama induces immediate parasympathetic activation, elevated vagal tone, and lowered systolic blood pressure.",
                "Thirty healthy volunteers practiced Nadi Shodhana breathing with continuous digital ECG monitoring. High-frequency (HF) heart rate variability power increased significantly by 38% while low-frequency to high-frequency (LF/HF) sympathetic ratio dropped, demonstrating swift reduction in physiological stress responses.",
                "International Journal of Yoga. 2013; 6(2):104–110", "10.4103/0973-6131.113405",
                "https://images.unsplash.com/photo-1544367567-0f2fcb009e0b?auto=format&fit=crop&w=800&q=80", 6));

        articleRepository.saveAll(articles);
        log.info("AyurvedaKnowledgeDataSeeder: Seeded {} scientific peer-reviewed research articles.", articles.size());
    }

    private AyurvedaTopic buildTopic(String title, String sktTerm, String slug, String category,
                                     String summary, String content, String significance,
                                     String sources, String imgUrl) {
        return AyurvedaTopic.builder()
                .title(title)
                .sanskritTerm(sktTerm)
                .slug(slug)
                .category(category)
                .summary(summary)
                .detailedContent(content)
                .clinicalSignificance(significance)
                .classicalSources(sources)
                .imageUrl(imgUrl)
                .build();
    }

    private Article buildArticle(String title, String slug, String author, String creds,
                                 String category, String absText, String content,
                                 String citation, String doi, String imgUrl, int readTime) {
        return Article.builder()
                .title(title)
                .slug(slug)
                .authorName(author)
                .authorCredentials(creds)
                .category(category)
                .abstractText(absText)
                .content(content)
                .journalCitation(citation)
                .doi(doi)
                .imageUrl(imgUrl)
                .readTimeMinutes(readTime)
                .build();
    }
}
