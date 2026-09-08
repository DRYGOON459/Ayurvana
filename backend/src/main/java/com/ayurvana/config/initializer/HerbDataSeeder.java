package com.ayurvana.config.initializer;

import com.ayurvana.model.entity.Herb;
import com.ayurvana.model.entity.PlantSafety;
import com.ayurvana.model.enums.ToxicityLevel;
import com.ayurvana.repository.HerbRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class HerbDataSeeder {

    private final HerbRepository herbRepository;

    public void seedHerbs() {
        if (herbRepository.count() >= 30) {
            return;
        }

        List<Herb> herbs = new ArrayList<>();

        // 1. Ashwagandha
        herbs.add(buildHerb("Ashwagandha (Indian Ginseng)", "Aśvagandhā (अश्वगन्धा)", "Withania somnifera",
                "Solanaceae", "ashwagandha",
                "Tikta (Bitter), Kashaya (Astringent), Madhura (Sweet)", "Ushna (Heating)", "Madhura (Sweet)",
                "Balances Vata and Kapha; may aggravate Pitta in high doses", "Roots",
                "Ashwagandha is one of the most revered adaptogenic herbs in Ayurveda, celebrated for Rasayana (rejuvenative) and Medhya (cognitive) properties.",
                "Historically prescribed for physical debility, insomnia, nervous exhaustion, stress, and enhancing vitality (Ojas).",
                "Clinical trials indicate Withanolides in Ashwagandha reduce serum cortisol levels, attenuate perceived stress, and support restful sleep architecture.",
                "Powder (Churna): 3–6 grams daily with warm milk or ghee; Extract capsules: 300–600 mg standardized extract.",
                "https://images.unsplash.com/photo-1615485290382-441e4d049cb5?auto=format&fit=crop&w=800&q=80", ToxicityLevel.SAFE,
                "Hyperthyroidism, acute severe liver conditions, or concurrent heavy sedative medication without medical supervision.",
                "May enhance effects of barbiturates, benzodiazepines, and immunosuppressants.",
                "Contraindicated during pregnancy due to potential uterine stimulant properties.",
                "Gastrointestinal distress, nausea, diarrhea in excess.", "Discontinue usage and hydrate.",
                "Charaka Samhita, Chikitsa Sthana (Rasayana Adhyaya)", "Indian J Psychol Med. 2012; 34(3):255–262"));

        // 2. Tulsi
        herbs.add(buildHerb("Holy Basil (Tulsi)", "Tulasī (तुलसी)", "Ocimum tenuiflorum", "Lamiaceae", "tulsi",
                "Katu (Pungent), Tikta (Bitter)", "Ushna (Heating)", "Katu (Pungent)",
                "Balances Kapha and Vata; may elevate Pitta if consumed excessively", "Leaves, Seeds",
                "Known as 'The Incomparable One' and 'Queen of Herbs', Tulsi is renowned for supporting respiratory clarity, immune balance, and mental calm.",
                "Sacred Rasayana used in pulmonary infections, bronchial asthma, fever (Jvara), and emotional clarity.",
                "Eugenol, rosmarinic acid, and caryophyllene demonstrate broad-spectrum antimicrobial, adaptogenic, and antioxidant actions.",
                "Fresh leaf infusion / tea: 2–5 grams leaves steeped; Churna: 1–3 grams twice daily with honey.",
                "https://images.unsplash.com/photo-1598511726623-d2e9996892f0?auto=format&fit=crop&w=800&q=80", ToxicityLevel.SAFE,
                "Bleeding disorders, male infertility concerns with prolonged mega-doses.",
                "May potentiate anticoagulant and antiplatelet pharmaceuticals.",
                "Avoid high pharmacological doses during pregnancy.",
                "Rarely mild heartburn in high Pitta constitutions.", "Drink cool water or coconut water.",
                "Sushruta Samhita, Sutra Sthana 38/18", "J Ayurveda Integr Med. 2014; 5(4):251–259"));

        // 3. Brahmi
        herbs.add(buildHerb("Brahmi (Water Hyssop)", "Brāhmī (ब्राह्मी)", "Bacopa monnieri", "Plantaginaceae", "brahmi",
                "Tikta (Bitter), Kashaya (Astringent), Madhura (Sweet)", "Sheeta (Cooling)", "Madhura (Sweet)",
                "Balances all three Doshas (Tridoshahara), especially pacifies Pitta and Vata", "Whole Plant",
                "Premier Medhya Rasayana herb for enhancing memory (Dhi), cognition (Dhriti), recall (Smriti), and emotional equanimity.",
                "Prescribed for neurological disorders, epilepsy (Apasmara), anxiety (Chittodvega), and mental fatigue.",
                "Bacosides A and B cross the blood-brain barrier, stimulating synaptic neurotransmission and neuronal dendritic arborization.",
                "Fresh juice (Swarasa): 10–20 ml; Medicated Ghee (Brahmi Ghrita): 5–10 grams on empty stomach.",
                "https://images.unsplash.com/photo-1540420773420-3366772f4999?auto=format&fit=crop&w=800&q=80", ToxicityLevel.SAFE,
                "Bradycardia, gastrointestinal tract obstruction.",
                "May interact with anticholinergic drugs and thyroid hormones.",
                "Generally safe under professional clinical guidance.",
                "Mild nausea, abdominal cramps on empty stomach in cold water.", "Take with warm ghee or milk.",
                "Charaka Samhita, Chikitsa Sthana 1/3", "Neuropsychopharmacology. 2002; 27(2):279–281"));

        // 4. Shatavari
        herbs.add(buildHerb("Shatavari (Wild Asparagus)", "Śatāvarī (शतावरी)", "Asparagus racemosus", "Asparagaceae", "shatavari",
                "Madhura (Sweet), Tikta (Bitter)", "Sheeta (Cooling)", "Madhura (Sweet)",
                "Balances Vata and Pitta; may increase Kapha and Ama if digestion is weak", "Tuberous Roots",
                "Revered as 'She who possesses a hundred husbands', Shatavari is the foremost female reproductive and hormonal tonic in Ayurveda.",
                "Prescribed for menstrual irregularities, lactation promotion (Stanyajanana), fertility, menopause, and gastric hyperacidity.",
                "Steroidal saponins (Shatavarins I–IV) modulate estrogen receptors and exert gastroprotective, immunomodulatory actions.",
                "Powder: 3–6 grams with warm milk, ghee, or honey; Shatavari Ghritha: 5–10 grams.",
                "https://images.unsplash.com/photo-1509358271058-acd22cc93898?auto=format&fit=crop&w=800&q=80", ToxicityLevel.SAFE,
                "Estrogen-sensitive breast or ovarian conditions, congestive heart failure, acute Kapha accumulation.",
                "May potentiate diuretic medications.",
                "Safe and traditionally recommended during pregnancy and lactation.",
                "Mild weight gain or heaviness if taken by high Kapha individuals.", "Take with pinch of ginger or pippali.",
                "Charaka Samhita, Sutra Sthana 4/18", "Biomed Pharmacother. 2018; 103:46–54"));

        // 5. Neem
        herbs.add(buildHerb("Neem (Indian Lilac)", "Nimba (निम्ब)", "Azadirachta indica", "Meliaceae", "neem",
                "Tikta (Bitter), Kashaya (Astringent)", "Sheeta (Cooling)", "Katu (Pungent)",
                "Strongly pacifies Pitta and Kapha; aggravates Vata in long-term or high dosages", "Leaves, Bark, Seed Oil",
                "Celebrated as 'Sarva Roga Nivarini' (curer of all ailments) and the ultimate blood cleanser (Rakta Shodhaka) and skin purifier (Kushtaghna).",
                "Prescribed for inflammatory skin disorders (eczema, psoriasis, acne), diabetes mellitus (Prameha), and fever.",
                "Azadirachtin, nimbin, and nimbidol exhibit potent antibacterial, antifungal, anti-inflammatory, and hepatoprotective actions.",
                "Powder: 1–3 grams; Leaf decoction: 15–30 ml; Topical seed oil: External application only.",
                "https://images.unsplash.com/photo-1515377905703-c4788e51af15?auto=format&fit=crop&w=800&q=80", ToxicityLevel.MODERATE,
                "Infants, pregnancy, male fertility planning, extreme debility or Vata exhaustion.",
                "May interact with antidiabetic medications, leading to hypoglycemia.",
                "Strictly contraindicated internally during pregnancy and lactation.",
                "Excessive dryness, weight loss, digestive weakness if consumed continuously.", "Limit internal courses to 2–4 weeks max.",
                "Sushruta Samhita, Sutra Sthana 46/262", "Curr Med Chem. 2005; 12(4):467–476"));

        // 6. Guduchi / Giloy
        herbs.add(buildHerb("Guduchi (Giloy / Heart-Leaved Moonseed)", "Guḍūcī (गुडूची)", "Tinospora cordifolia", "Menispermaceae", "guduchi",
                "Tikta (Bitter), Kashaya (Astringent)", "Ushna (Heating)", "Madhura (Sweet)",
                "Tridosha Shamaka (Balances all 3 Doshas - unique heating potency with sweet post-digestive effect)", "Stem",
                "Known as 'Amrita' (Nectar of Immortality), Guduchi is the preeminent Rasayana for immune restoration, chronic fever, and liver protection.",
                "Prescribed in chronic intermittent fevers (Jvara), rheumatoid arthritis (Amavata), gout (Vatarakta), and jaundice.",
                "Tinosporine, cordifolioside A, and arabinogalactan stimulate macrophage activity and downregulate pro-inflammatory cytokines (IL-6, TNF-alpha).",
                "Guduchi Satva (pure water extract): 500 mg - 1 g; Decoction (Kwatha): 20–40 ml daily.",
                "https://images.unsplash.com/photo-1563178406-4cdc2923acbc?auto=format&fit=crop&w=800&q=80", ToxicityLevel.SAFE,
                "Autoimmune conditions without medical supervision (due to potent immunostimulation).",
                "Enhances hypoglycemic effects of antidiabetic drugs.",
                "Use with caution and Vaidya guidance during pregnancy.",
                "Occasional constipation if taken without sufficient fluids.", "Hydrate with warm water.",
                "Charaka Samhita, Chikitsa Sthana 1/3", "Front Pharmacol. 2017; 8:851"));

        // 7. Haridra / Turmeric
        herbs.add(buildHerb("Turmeric (Haridra)", "Haridrā (हरिद्रा)", "Curcuma longa", "Zingiberaceae", "haridra",
                "Tikta (Bitter), Katu (Pungent)", "Ushna (Heating)", "Katu (Pungent)",
                "Pacifies Kapha and Vata; balances Pitta in moderate culinary amounts", "Rhizome",
                "The golden spice of Ayurveda, revered for anti-inflammatory (Shothahara), wound healing (Vranaropana), and complexion enhancement (Varnya).",
                "Prescribed for metabolic disorders, skin afflictions, liver congestion, joint arthritis, and throat infections.",
                "Curcuminoids and volatile oils exhibit robust NF-kB inhibition, cyclooxygenase-2 inhibition, and free-radical scavenging.",
                "Powder: 1–3 grams with warm milk and pinch of black pepper (piperine); Fresh juice: 5–10 ml.",
                "https://images.unsplash.com/photo-1615485290382-441e4d049cb5?auto=format&fit=crop&w=800&q=80", ToxicityLevel.SAFE,
                "Bile duct obstruction, active gallstones, bleeding diathesis.",
                "Potentiates antiplatelet agents (aspirin, clopidogrel) and anticoagulants (warfarin).",
                "Culinary amounts safe; avoid therapeutic high-dose supplements during pregnancy.",
                "Mild gastric irritation if taken dry without lipids.", "Always take with healthy fats (ghee, milk).",
                "Charaka Samhita, Sutra Sthana 4/16", "AAPS J. 2009; 11(3):495–510"));

        // 8. Haritaki
        herbs.add(buildHerb("Haritaki (Chebulic Myrobalan)", "Harītakī (हरीतकी)", "Terminalia chebula", "Combretaceae", "haritaki",
                "Pancha Rasa (Contains 5 of 6 tastes: Astringent, Bitter, Sweet, Pungent, Sour; lacks Salty)", "Ushna (Heating)", "Madhura (Sweet)",
                "Tridoshahara (Balances Vata, Pitta, and Kapha)", "Fruit Pericarp",
                "Called the 'Mother of Ayurvedic Herbs' (Kayakalpa), Haritaki promotes longevity, clears toxins (Ama), and rejuvenates the gastrointestinal tract.",
                "Prescribed for constipation (Anaha), sluggish metabolism, hemorrhoids (Arshas), and longevity regimens.",
                "Chebulinic acid, ellagic acid, and tannins stimulate intestinal peristalsis and possess prebiotic and hepatoprotective properties.",
                "Churna: 3–6 grams at bedtime with warm water or castor oil.",
                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?auto=format&fit=crop&w=800&q=80", ToxicityLevel.SAFE,
                "Severe dehydration, exhaustion, acute fatigue, fasting, pregnancy.",
                "No known major pharmaceutical interactions at normal doses.",
                "Contraindicated during pregnancy due to laxative down-bearing action.",
                "Loose stools if overdosed.", "Reduce dosage.",
                "Charaka Samhita, Chikitsa Sthana 1/1 (Abhayadi Rasayana)", "Asian Pac J Trop Biomed. 2012; 2(8):617–622"));

        // 9. Amalaki / Indian Gooseberry
        herbs.add(buildHerb("Amalaki (Amla / Indian Gooseberry)", "Āmalakī (आमलकी)", "Phyllanthus emblica", "Phyllanthaceae", "amalaki",
                "Pancha Rasa (Sour, Sweet, Bitter, Pungent, Astringent; lacks Salty)", "Sheeta (Cooling)", "Madhura (Sweet)",
                "Tridosha Shamaka, particularly potent in soothing Pitta", "Fresh / Dried Fruit",
                "The cornerstone Rasayana fruit of Ayurveda, richest natural source of stable vitamin C, bioflavonoids, and antioxidant tannins.",
                "Prescribed for hyperacidity (Amlapitta), premature greying (Palitya), eye health (Chakshushya), and cellular longevity.",
                "Emblicanin A and B provide formidable free-radical quenching and preserve pancreatic beta-cell function.",
                "Fresh juice: 10–20 ml; Churna: 3–6 grams with water or honey.",
                "https://images.unsplash.com/photo-1598511726623-d2e9996892f0?auto=format&fit=crop&w=800&q=80", ToxicityLevel.SAFE,
                "Extreme diarrhea, acute cold/shivering.",
                "May enhance insulin sensitivity; monitor diabetic glucose levels.",
                "Completely safe and nourishing during pregnancy and lactation.",
                "Mild loose stools in sensitive individuals.", "Take with ghee.",
                "Charaka Samhita, Chikitsa Sthana 1/1", "Phytother Res. 2019; 33(5):1252–1267"));

        // 10. Bibhitaki
        herbs.add(buildHerb("Bibhitaki (Belleric Myrobalan)", "Bibhītakī (बिभीतक)", "Terminalia bellirica", "Combretaceae", "bibhitaki",
                "Kashaya (Astringent)", "Ushna (Heating)", "Madhura (Sweet)",
                "Pacifies Kapha and Pitta; one of the 3 pillars of Triphala", "Fruit",
                "The third component of Triphala, revered for clearing excess mucus from the respiratory system and lungs (Kaphahara).",
                "Prescribed for chronic cough (Kasa), bronchitis, voice hoarseness, and intestinal parasites (Krimi).",
                "Belleric acid and sitosterol provide bronchodilatory and anti-asthmatic properties.",
                "Churna: 2–5 grams with honey or warm water.",
                "https://images.unsplash.com/photo-1540420773420-3366772f4999?auto=format&fit=crop&w=800&q=80", ToxicityLevel.SAFE,
                "Severe dry cough with tissue emaciation.",
                "None reported.", "Safe in normal herbal doses.",
                "Dryness in mouth if taken without carrier vehicle (Anupana).", "Take with honey or ghee.",
                "Sushruta Samhita, Sutra Sthana 38", "J Ethnopharmacol. 2016; 189:285–293"));

        // 11. Shunthi / Dry Ginger
        herbs.add(buildHerb("Dry Ginger (Shunthi)", "Śuṇṭhī (शुण्ठी)", "Zingiber officinale", "Zingiberaceae", "shunthi",
                "Katu (Pungent)", "Ushna (Heating)", "Madhura (Sweet)",
                "Balances Kapha and Vata; mild on Pitta due to sweet Vipaka", "Dried Rhizome",
                "Known as 'Mahaushadhi' (The Universal Medicine), Shunthi rekindles digestive fire (Deepana) and burns toxic metabolic waste (Pachana).",
                "Prescribed for indigestion (Ajeerna), joint swelling (Amavata), nausea, and respiratory congestion.",
                "Gingerols and shogaols enhance gastric emptying, reduce intestinal spasms, and inhibit inflammatory leukotrienes.",
                "Churna: 1–2 grams before meals with pinch of rock salt (Saindhava Lavana); Decoction: 20–30 ml.",
                "https://images.unsplash.com/photo-1509358271058-acd22cc93898?auto=format&fit=crop&w=800&q=80", ToxicityLevel.SAFE,
                "Bleeding peptic ulcers, high inflammatory fever.",
                "May potentiate anticoagulant drugs.",
                "Safe in culinary and moderate therapeutic doses for morning sickness.",
                "Heartburn if taken in excess on empty stomach.", "Combine with ghee or milk.",
                "Bhavaprakasha Nighantu, Haritakyadi Varga", "Food Funct. 2013; 4(6):845–855"));

        // 12. Yashtimadhu / Licorice
        herbs.add(buildHerb("Licorice (Yashtimadhu / Mulethi)", "Yaṣṭīmadhu (यष्टीमधु)", "Glycyrrhiza glabra", "Fabaceae", "yashtimadhu",
                "Madhura (Sweet), Tikta (Bitter)", "Sheeta (Cooling)", "Madhura (Sweet)",
                "Balances Vata and Pitta; soothing to mucous membranes", "Roots and Stolons",
                "Celebrated for its demulcent, voice-clearing (Kanthya), anti-ulcer, and Medhya Rasayana properties.",
                "Prescribed for hyperacidity, peptic ulcers, chronic dry cough, sore throat, and adrenal rejuvenation.",
                "Glycyrrhizin and glabridin stimulate mucosal prostaglandin synthesis and exert soothing anti-inflammatory actions.",
                "Powder: 2–4 grams with warm milk or water; Decoction: 20–30 ml.",
                "https://images.unsplash.com/photo-1515377905703-c4788e51af15?auto=format&fit=crop&w=800&q=80", ToxicityLevel.SAFE,
                "Hypertension, congestive heart failure, severe hypokalemia, kidney failure.",
                "May interact with potassium-depleting diuretics and cardiac glycosides.",
                "Avoid large chronic doses during pregnancy.",
                "Water retention or elevated blood pressure with prolonged excessive dosage.", "Limit high-dose regimens to 4–6 weeks.",
                "Charaka Samhita, Sutra Sthana 4/1", "Phytother Res. 2012; 26(8):1176–1187"));

        // 13. Mandukaparni / Gotu Kola
        herbs.add(buildHerb("Gotu Kola (Mandukaparni)", "Maṇḍūkaparṇī (मण्डूकपर्णी)", "Centella asiatica", "Apiaceae", "mandukaparni",
                "Tikta (Bitter), Kashaya (Astringent)", "Sheeta (Cooling)", "Madhura (Sweet)",
                "Balances Pitta and Kapha; rejuvenates brain and nervous system", "Whole Plant / Leaves",
                "Premier Medhya Rasayana herb promoting longevity, memory, and skin collagen synthesis.",
                "Prescribed for anxiety, mental fatigue, varicose veins, microcirculation disorders, and wound healing.",
                "Asiaticoside and madecassoside promote cerebral circulation, acetylcholine synthesis, and fibroblast collagen proliferation.",
                "Fresh juice: 10–20 ml; Churna: 2–4 grams with milk or honey.",
                "https://images.unsplash.com/photo-1540420773420-3366772f4999?auto=format&fit=crop&w=800&q=80", ToxicityLevel.SAFE,
                "Severe liver disease.", "May enhance sedative effects.",
                "Avoid high pharmacological doses during early pregnancy.",
                "Mild headache or dizziness if taken in high dose by cold constitutions.", "Take with warm water.",
                "Charaka Samhita, Chikitsa Sthana 1/3", "Evid Based Complement Alternat Med. 2012; 2012:381982"));

        // 14. Guggulu
        herbs.add(buildHerb("Guggulu (Indian Bdellium)", "Guggulu (गुग्गुलु)", "Commiphora mukul", "Burseraceae", "guggulu",
                "Tikta (Bitter), Katu (Pungent), Kashaya (Astringent)", "Ushna (Heating)", "Katu (Pungent)",
                "Balances Vata and Kapha; highly penetrating (Sukshma) and scraping (Lekhana)", "Purified Gum Resin (Shodhita)",
                "Renowned for its deep tissue penetrating ability, lipid scraping, and clearing toxic obstruction (Medoroga & Amavata).",
                "Prescribed for osteoarthritis, rheumatoid arthritis, hyperlipidemia, obesity, and inflammatory skin conditions.",
                "Guggulsterones E and Z antagonize farnesoid X receptors (FXR) to regulate cholesterol metabolism and downregulate inflammatory cytokines.",
                "Purified resin tablets / vatis: 500 mg - 1000 mg twice daily after food with warm water.",
                "https://images.unsplash.com/photo-1615485290382-441e4d049cb5?auto=format&fit=crop&w=800&q=80", ToxicityLevel.SAFE,
                "Acute kidney disease, acute liver failure, bleeding disorders.",
                "May interact with antiplatelet, anticoagulant, and thyroid medications.",
                "Strictly contraindicated during pregnancy due to uterine stimulating action.",
                "Skin rash, gastrointestinal distress if unpurified resin is used.", "Always use classical Shodhita (purified) Guggulu.",
                "Sushruta Samhita, Sutra Sthana 15", "Cardiovasc Drugs Ther. 2007; 21(5):365–372"));

        // 15. Shankhpushpi
        herbs.add(buildHerb("Shankhpushpi", "Śaṅkhapuṣpī (शङ्खपुष्पी)", "Convolvulus pluricaulis", "Convolvulaceae", "shankhpushpi",
                "Tikta (Bitter), Kashaya (Astringent)", "Sheeta (Cooling)", "Madhura (Sweet)",
                "Tridoshahara, supreme brain tonic and nervous system soother", "Whole Herb",
                "Classical Medhya Rasayana herb famous for promoting deep sleep, calming nervous excitability, and enhancing retention.",
                "Prescribed for insomnia (Anidra), tension headaches, ADHD, exam stress, and hypertension.",
                "Microphylline and scopoletin modulate GABAergic neurotransmission, inducing anxiolytic and neuroprotective responses.",
                "Powder: 3–6 grams with warm milk; Syrup / Decoction: 15–30 ml.",
                "https://images.unsplash.com/photo-1598511726623-d2e9996892f0?auto=format&fit=crop&w=800&q=80", ToxicityLevel.SAFE,
                "Severe hypotension.", "May potentiate antiepileptic medications like phenytoin.",
                "Safe under qualified Ayurvedic supervision.",
                "Slight drowsiness if taken in large quantities during daytime.", "Take primarily in the evening.",
                "Charaka Samhita, Chikitsa Sthana 1/3", "J Ayurveda Integr Med. 2012; 3(4):171–175"));

        // 16. Pippali / Long Pepper
        herbs.add(buildHerb("Long Pepper (Pippali)", "Pippalī (पिप्पली)", "Piper longum", "Piperaceae", "pippali",
                "Katu (Pungent)", "Anushna-Sheeta (Mildly heating initially, cooling after)", "Madhura (Sweet)",
                "Pacifies Vata and Kapha; rejuvenates Pranavaha Srotas (Respiratory Channels)", "Dried Fruit Spike",
                "Powerful bio-enhancer (Yogavahi) and respiratory Rasayana that kindles digestive fire without heavily provoking Pitta.",
                "Prescribed in chronic bronchitis, asthma (Tamaka Shwasa), indigestion, spleen enlargement, and malabsorption.",
                "Piperine increases intestinal permeability, dramatically enhancing bioavailability of co-administered herbal alkaloids.",
                "Churna: 500 mg - 1 gram with honey or warm milk; Vardhamana Pippali Rasayana under clinical supervision.",
                "https://images.unsplash.com/photo-1509358271058-acd22cc93898?auto=format&fit=crop&w=800&q=80", ToxicityLevel.SAFE,
                "Active peptic ulcer, acute inflammatory gastritis.",
                "Significantly enhances plasma levels of pharmaceutical drugs due to CYP450 inhibition.",
                "Avoid high therapeutic doses during pregnancy.",
                "Burning sensation if taken in mega-doses.", "Take with milk or honey.",
                "Charaka Samhita, Chikitsa Sthana 1/3 (Pippali Rasayana)", "Fitoterapia. 2011; 82(8):1103–1111"));

        // 17. Elaichi / True Cardamom
        herbs.add(buildHerb("True Cardamom (Elaichi)", "Elā (एला)", "Elettaria cardamomum", "Zingiberaceae", "elaichi",
                "Madhura (Sweet), Katu (Pungent)", "Sheeta (Cooling)", "Madhura (Sweet)",
                "Tridosha balancing; soothes Vata and Pitta, reduces Kapha mucus", "Seeds",
                "The 'Queen of Spices', highly valued for its pleasant aromatic breath freshening (Mukhadoshahara) and digestive carminative properties.",
                "Prescribed for nausea, vomiting (Chhardi), indigestion, bad breath, and respiratory congestion.",
                "Cineole, terpinyl acetate, and borneol relax gastrointestinal smooth muscle and combat oral pathogens.",
                "Churna: 500 mg - 1 gram; crushed pods steeped in tea or warm milk.",
                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?auto=format&fit=crop&w=800&q=80", ToxicityLevel.SAFE,
                "Large gallstones without medical monitoring.", "None reported.",
                "Completely safe during pregnancy and lactation.",
                "None under normal culinary or therapeutic usage.", "N/A",
                "Bhavaprakasha Nighantu, Karpuradi Varga", "J Ethnopharmacol. 2008; 115(3):463–472"));

        // 18. Twak / Ceylon Cinnamon
        herbs.add(buildHerb("Ceylon Cinnamon (Twak)", "Tvak (त्वक्)", "Cinnamomum verum", "Lauraceae", "twak",
                "Katu (Pungent), Tikta (Bitter), Madhura (Sweet)", "Ushna (Heating)", "Katu (Pungent)",
                "Pacifies Vata and Kapha; stimulates blood circulation and digestive Agni", "Inner Stem Bark",
                "Ancient warming spice that supports insulin sensitivity, peripheral circulation, and metabolic digestion.",
                "Prescribed for diabetes (Prameha), cold extremities, chronic cough, and abdominal cramps.",
                "Cinnamaldehyde and polyphenols improve cellular glucose uptake by mimicking insulin signaling pathways.",
                "Churna: 1–2 grams daily with honey or warm water.",
                "https://images.unsplash.com/photo-1515377905703-c4788e51af15?auto=format&fit=crop&w=800&q=80", ToxicityLevel.SAFE,
                "Active internal bleeding, high fever.",
                "May enhance oral hypoglycemics and blood thinners.",
                "Avoid high concentrated essential oils during pregnancy.",
                "Mouth tingling in sensitive individuals.", "Use authentic Ceylon cinnamon (low coumarin).",
                "Sushruta Samhita, Sutra Sthana 38", "Diabetes Care. 2003; 26(12):3215–3218"));

        // 19. Kutki
        herbs.add(buildHerb("Kutki (Hellebore)", "Kaṭukī (कटुकी)", "Picrorhiza kurroa", "Plantaginaceae", "kutki",
                "Tikta (Bitter)", "Sheeta (Cooling)", "Katu (Pungent)",
                "Strongly pacifies Pitta and Kapha; premier liver and spleen cleanser (Yakrit-Pleeha Shodhaka)", "Rhizome & Roots",
                "One of the bitterest and most potent hepatoprotective herbs in Ayurvedic medicine.",
                "Prescribed for hepatitis, fatty liver, jaundice, biliary stasis, chronic fever, and bronchial asthma.",
                "Picrosides I and II protect hepatocytes from carbon tetrachloride and toxin-induced necrosis, restoring normal liver enzymes.",
                "Churna: 500 mg - 1 gram with honey or warm water; High doses (3g) act as a purgative (Bhedana).",
                "https://images.unsplash.com/photo-1563178406-4cdc2923acbc?auto=format&fit=crop&w=800&q=80", ToxicityLevel.SAFE,
                "Severe diarrhea, extreme weakness.", "May enhance immunosuppressive therapy.",
                "Avoid during pregnancy without specialist oversight.",
                "Loose stools or abdominal cramping in higher dosages.", "Reduce dosage.",
                "Charaka Samhita, Sutra Sthana 4/1", "Phytother Res. 2010; 24(7):1004–1011"));

        // 20. Bhringraj
        herbs.add(buildHerb("Bhringraj (False Daisy)", "Bhṛṅgarāja (भृङ्गराज)", "Eclipta alba", "Asteraceae", "bhringraj",
                "Katu (Pungent), Tikta (Bitter)", "Ushna (Heating)", "Katu (Pungent)",
                "Balances Vata and Kapha; soothes Pitta when formulated in coconut/sesame oil", "Whole Plant",
                "Known as 'The Ruler of Hair' (Keshya Rasayana), celebrated for hair restoration, liver health, and ocular vitality.",
                "Prescribed for premature greying, alopecia, hair thinning, liver cirrhosis, and insomnia.",
                "Wedelolactone and luteolin stimulate hair follicle anagen phase transition and exhibit strong hepatoprotective actions.",
                "Oil: External scalp massage; Powder: 1–3 grams with honey, ghee, or milk.",
                "https://images.unsplash.com/photo-1540420773420-3366772f4999?auto=format&fit=crop&w=800&q=80", ToxicityLevel.SAFE,
                "Acute cold / sinusitis when used in cold water internally.", "None significant.",
                "Safe for topical scalp use; consult Vaidya for internal pregnancy usage.",
                "Cooling sensation on scalp.", "Warm the oil slightly before massage.",
                "Bhavaprakasha Nighantu, Guduchyadi Varga", "J Ethnopharmacol. 2009; 124(3):450–456"));

        // 21. Manjistha
        herbs.add(buildHerb("Manjistha (Indian Madder)", "Mañjiṣṭhā (मञ्जिष्ठा)", "Rubia cordifolia", "Rubiaceae", "manjistha",
                "Tikta (Bitter), Kashaya (Astringent), Madhura (Sweet)", "Ushna (Heating)", "Katu (Pungent)",
                "Balances Pitta and Kapha; premier blood and lymphatic purifier (Rakta Prasadana)", "Roots & Stems",
                "Supreme Ayurvedic herb for detoxifying the lymphatic system, brightening skin tone, and clearing inflammatory skin lesions.",
                "Prescribed for eczema, acne, psoriasis, vitiligo, hyperpigmentation, and sluggish lymphatic flow.",
                "Purpurin, munjistin, and alizarin act as potent antioxidants, skin depigmenting agents, and lymphatic decongestants.",
                "Powder: 1–3 grams with warm water or honey; Topical paste with rose water for glowing complexion.",
                "https://images.unsplash.com/photo-1509358271058-acd22cc93898?auto=format&fit=crop&w=800&q=80", ToxicityLevel.SAFE,
                "Severe kidney stones.", "May temporarily color urine orange/red (benign).",
                "Avoid internal use during pregnancy.",
                "Harmless reddish tint in urine.", "Reassure patient.",
                "Charaka Samhita, Sutra Sthana 4/11", "Food Chem Toxicol. 2008; 46(6):2100–2107"));

        // 22. Punarnava
        herbs.add(buildHerb("Punarnava (Spreading Hogweed)", "Punarnavā (पुनर्नवा)", "Boerhavia diffusa", "Nyctaginaceae", "punarnava",
                "Madhura (Sweet), Tikta (Bitter), Kashaya (Astringent)", "Ushna (Heating)", "Madhura (Sweet)",
                "Tridosha Shamaka, especially pacifies Kapha and Vata; rejuvenates kidneys", "Roots & Whole Plant",
                "Translates to 'That which renews the body'; foremost diuretic (Mutrala) and kidney rejuvenator in Ayurveda.",
                "Prescribed for edema (Shotha), urinary tract disorders, chronic kidney impairment, heart failure fluid retention, and ascites.",
                "Punarnavine alkaloid stimulates glomerular filtration, clears water retention, and reduces proteinuria.",
                "Churna: 3–6 grams; Decoction (Kwatha): 20–40 ml daily before meals.",
                "https://images.unsplash.com/photo-1515377905703-c4788e51af15?auto=format&fit=crop&w=800&q=80", ToxicityLevel.SAFE,
                "Severe dehydration.", "May enhance synthetic diuretics like furosemide.",
                "Avoid high pharmacological doses during pregnancy.",
                "Frequent urination (intended therapeutic effect).", "Maintain adequate hydration.",
                "Charaka Samhita, Sutra Sthana 4/16", "J Ethnopharmacol. 2002; 81(3):357–360"));

        // 23. Vidari Kanda
        herbs.add(buildHerb("Vidari Kanda (Indian Kudzu)", "Vidārīkanda (विदारीकन्द)", "Pueraria tuberosa", "Fabaceae", "vidari-kanda",
                "Madhura (Sweet)", "Sheeta (Cooling)", "Madhura (Sweet)",
                "Balances Vata and Pitta; deep tissue builder (Brimhana & Vrishya)", "Tuber",
                "Renowned nourishing Rasayana that promotes physical strength, lactation, and healthy weight gain in emaciated conditions.",
                "Prescribed for general debility, low sperm count, postpartum lactation support, and cardiovascular toning.",
                "Puerarin and isoflavones support cardioprotection, estrogenic balance, and tissue nourishment.",
                "Churna: 3–6 grams boiled in milk with ghee.",
                "https://images.unsplash.com/photo-1615485290382-441e4d049cb5?auto=format&fit=crop&w=800&q=80", ToxicityLevel.SAFE,
                "Excessive obesity or severe Kapha congestion.", "None significant.",
                "Safe and traditionally recommended during lactation.",
                "Weight gain if taken without exercise in Kapha types.", "Engage in moderate yoga.",
                "Charaka Samhita, Sutra Sthana 4/1", "Phytomedicine. 2013; 20(8-9):730–736"));

        // 24. Gokshura
        herbs.add(buildHerb("Gokshura (Small Caltrops)", "Gokṣura (गोक्षुर)", "Tribulus terrestris", "Zygophyllaceae", "gokshura",
                "Madhura (Sweet), Tikta (Bitter)", "Sheeta (Cooling)", "Madhura (Sweet)",
                "Balances Vata and Pitta; rejuvenates urinary tract and male vitality", "Fruit & Roots",
                "Key herb in Dashamoola, revered as a cooling tonic for kidneys, prostate, bladder, and physical stamina.",
                "Prescribed for kidney stones (Ashmari), dysuria, urinary tract infections, erectile dysfunction, and low vitality.",
                "Protodioscin enhances nitric oxide release, improving urogenital microcirculation and renal filtration.",
                "Churna: 3–6 grams with milk or water; Decoction: 20–40 ml.",
                "https://images.unsplash.com/photo-1598511726623-d2e9996892f0?auto=format&fit=crop&w=800&q=80", ToxicityLevel.SAFE,
                "Severe dehydration.", "May enhance antihypertensive and diuretic drugs.",
                "Avoid during pregnancy without medical advice.",
                "Mild diuresis.", "Ensure adequate water intake.",
                "Charaka Samhita, Chikitsa Sthana 2/1", "J Tradit Complement Med. 2016; 6(3):288–294"));

        // 25. Arjuna
        herbs.add(buildHerb("Arjuna (Terminalia Bark)", "Arjuna (अर्जुन)", "Terminalia arjuna", "Combretaceae", "arjuna",
                "Kashaya (Astringent), Tikta (Bitter)", "Sheeta (Cooling)", "Katu (Pungent)",
                "Balances Pitta and Kapha; supreme cardiac tonic (Hridya Rasayana)", "Stem Bark",
                "The legendary heart tonic of Ayurveda, strengthening myocardium, coronary circulation, and blood vessel elasticity.",
                "Prescribed for angina, hypertension, cardiomyopathy, hypercholesterolemia, and heart recovery post-infarct.",
                "Arjunolic acid, arjungenin, and coenzyme Q10 provide potent inotropic, antioxidant, and anti-ischemic protection.",
                "Bark powder boiled in milk (Arjuna Ksheera Paka): 3–6 grams twice daily.",
                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?auto=format&fit=crop&w=800&q=80", ToxicityLevel.SAFE,
                "Severe bradycardia without cardiology guidance.",
                "May potentiate cardiovascular drugs (beta-blockers, digitalis).",
                "Use with medical consultation during pregnancy.",
                "Constipation in high Vata individuals due to astringent taste.", "Prepare as Ksheerapaka with warm milk and pinch of ghee.",
                "Charaka Samhita, Chikitsa Sthana 26/51", "J Cardiovasc Transl Res. 2010; 3(3):214–222"));

        // 26. Shallaki / Boswellia
        herbs.add(buildHerb("Shallaki (Indian Frankincense)", "Śallakī (शल्लकी)", "Boswellia serrata", "Burseraceae", "shallaki",
                "Tikta (Bitter), Kashaya (Astringent), Madhura (Sweet)", "Ushna (Heating)", "Katu (Pungent)",
                "Balances Vata and Kapha; sovereign remedy for joint cartilage preservation (Sandhivata)", "Purified Gum Resin",
                "Classical Ayurvedic resin renowned for halting synovial joint inflammation and rebuilding mobility without stomach ulcers.",
                "Prescribed for osteoarthritis, rheumatoid arthritis, ankylosing spondylitis, bursitis, and inflammatory bowel disease (Crohn's/Colitis).",
                "Acetyl-11-keto-beta-boswellic acid (AKBA) selectively inhibits 5-lipoxygenase (5-LOX), preventing leukotriene synthesis.",
                "Standardized extract: 300–500 mg 2–3 times daily after meals.",
                "https://images.unsplash.com/photo-1615485290382-441e4d049cb5?auto=format&fit=crop&w=800&q=80", ToxicityLevel.SAFE,
                "Severe gastritis.", "May interact with NSAIDs (beneficial additive anti-inflammatory effect).",
                "Avoid high pharmacological doses during pregnancy.",
                "Mild acid reflux in very high doses.", "Always take with food.",
                "Bhavaprakasha Nighantu, Vatadi Varga", "Phytomedicine. 2003; 10(1):3–7"));

        // 27. Bala
        herbs.add(buildHerb("Bala (Country Mallow)", "Balā (बला)", "Sida cordifolia", "Malvaceae", "bala",
                "Madhura (Sweet)", "Sheeta (Cooling)", "Madhura (Sweet)",
                "Balances Vata and Pitta; supreme strength and nervous system builder (Balya & Vatahara)", "Roots & Whole Plant",
                "Translates to 'Strength'; prescribed for neurological weakness, facial palsy, sciatica, emaciation, and muscular dystrophy.",
                "Prescribed in hemiplegia (Pakshaghata), joint degeneration, post-viral fatigue, and sports recovery.",
                "Contains ephedrine-free phytosterols and amino acids that nourish neuromuscular junctions.",
                "Decoction: 20–40 ml; Bala Taila for full-body neuro-muscular massage.",
                "https://images.unsplash.com/photo-1509358271058-acd22cc93898?auto=format&fit=crop&w=800&q=80", ToxicityLevel.SAFE,
                "Severe Kapha congestion.", "None reported at classical root decoction dosages.",
                "Safe under professional Ayurvedic supervision.",
                "Heaviness if taken by sluggish digestive fire.", "Take with warm water.",
                "Charaka Samhita, Sutra Sthana 4/1", "J Ethnopharmacol. 2006; 105(1-2):113–118"));

        // 28. Kalmegh
        herbs.add(buildHerb("Kalmegh (King of Bitters)", "Kālamegha (कालमेघ)", "Andrographis paniculata", "Acanthaceae", "kalmegh",
                "Tikta (Intensely Bitter)", "Sheeta (Cooling)", "Katu (Pungent)",
                "Strongly pacifies Pitta and Kapha; sovereign febrifuge and liver purifier", "Whole Plant",
                "Celebrated across South Asia as the supreme bitter tonic for fighting acute viral upper respiratory infections and restoring liver enzymes.",
                "Prescribed for common cold, influenza, viral hepatitis, chronic fever, and intestinal worm infestations.",
                "Andrographolide exerts formidable antiviral, hepatoprotective, and immunostimulatory properties.",
                "Churna: 1–3 grams; Standardized extract: 200–400 mg.",
                "https://images.unsplash.com/photo-1563178406-4cdc2923acbc?auto=format&fit=crop&w=800&q=80", ToxicityLevel.SAFE,
                "Hypotension, male fertility planning (high prolonged doses).",
                "May interact with anticoagulant and antihypertensive drugs.",
                "Contraindicated during pregnancy.",
                "Intense bitter taste lingering on palate.", "Take in capsule form or with honey.",
                "Indian Pharmacopoeia & Classical Nighantus", "Phytomedicine. 2010; 17(3-4):178–185"));

        // 29. Vacha
        herbs.add(buildHerb("Vacha (Sweet Flag)", "Vacā (वचा)", "Acorus calamus", "Acoraceae", "vacha",
                "Katu (Pungent), Tikta (Bitter)", "Ushna (Heating)", "Katu (Pungent)",
                "Balances Vata and Kapha; stimulates speech, intellect, and mental clarity (Medhya & Smritiprada)", "Purified Rhizome",
                "Translates to 'Speech'; renowned for clearing throat obstructions, enhancing articulation, memory, and clearing brain fog.",
                "Prescribed for stammering, speech delay in children, epilepsy, lethargy, and sinus congestion (as Nasya).",
                "Acorin and alpha-asarone (in purified samples) enhance central nervous system arousal and synaptic transmission.",
                "Purified root powder: 125 mg - 500 mg with honey or ghee (strictly controlled micro-doses).",
                "https://images.unsplash.com/photo-1540420773420-3366772f4999?auto=format&fit=crop&w=800&q=80", ToxicityLevel.MODERATE,
                "Hyperacidity, bleeding disorders, pregnancy.",
                "May enhance MAO inhibitors and CNS depressants.",
                "Strictly contraindicated during pregnancy.",
                "Nausea or emesis if taken in high doses (used clinically for Vamana emesis).", "Adhere to strict micro-dosages (under 500mg).",
                "Charaka Samhita, Sutra Sthana 4/1", "J Ethnopharmacol. 2011; 136(3):428–434"));

        // 30. Sariva
        herbs.add(buildHerb("Sariva (Indian Sarsaparilla)", "Sārivā (सारिवा / अनन्तमूल)", "Hemidesmus indicus", "Apocynaceae", "sariva",
                "Madhura (Sweet), Tikta (Bitter)", "Sheeta (Cooling)", "Madhura (Sweet)",
                "Tridosha balancing, especially soothing to aggravated Pitta; fragrant blood cleanser", "Roots",
                "Also called Anantamoola; sweet-fragrant cooling root used for purifying blood, quenching burning sensations (Daha), and radiant skin.",
                "Prescribed for burning micturition, eczema, urticaria, auto-immune rashes, body odor, and summer heat exhaustion.",
                "2-hydroxy-4-methoxybenzaldehyde and hemidesmin confer fragrant aroma, antioxidant, and anti-inflammatory properties.",
                "Powder: 3–6 grams with milk or water; Sariva syrup (Nannari Sharbat): 20–30 ml in cool water.",
                "https://images.unsplash.com/photo-1515377905703-c4788e51af15?auto=format&fit=crop&w=800&q=80", ToxicityLevel.SAFE,
                "None noted at normal dosages.", "None reported.",
                "Completely safe and nourishing during pregnancy (relieves morning nausea).",
                "None.", "N/A",
                "Charaka Samhita, Sutra Sthana 4/1", "Phytother Res. 2006; 20(9):715–721"));

        // 31. Eranda / Castor
        herbs.add(buildHerb("Castor (Eranda)", "Eraṇḍa (एरण्ड)", "Ricinus communis", "Euphorbiaceae", "eranda",
                "Madhura (Sweet), Katu (Pungent), Kashaya (Astringent)", "Ushna (Heating)", "Madhura (Sweet)",
                "Vatahara Supreme (The greatest herb for destroying Vata dosha disorders)", "Roots, Seed Oil (Eranda Taila)",
                "Celebrated as 'The Lion among Herbs' for conquering deep-seated Vata and rheumatoid joint pain (Amavata).",
                "Prescribed for severe constipation, sciatica (Gridhrasi), low back pain, arthritis, and colon cleansing (Virechana).",
                "Ricinoleic acid activates EP3 prostanoid receptors on intestinal and uterine smooth-muscle cells, prompting rapid peristalsis.",
                "Purified Castor Oil: 5–20 ml with warm milk/ginger tea at bedtime; Root powder: 3–6 grams in decoction.",
                "https://images.unsplash.com/photo-1598511726623-d2e9996892f0?auto=format&fit=crop&w=800&q=80", ToxicityLevel.MODERATE,
                "Intestinal obstruction, appendicitis, acute inflammatory pelvic disease.",
                "May decrease absorption of other oral medications if taken simultaneously.",
                "Contraindicated internally during pregnancy (can induce labor).",
                "Abdominal cramping or loose bowel movements.", "Hydrate with warm fluids and electolytes.",
                "Charaka Samhita, Sutra Sthana 25/40", "Proc Natl Acad Sci USA. 2012; 109(23):9179–9184"));

        // 32. Methi / Fenugreek
        herbs.add(buildHerb("Fenugreek (Methi)", "Methikā (मेथिका)", "Trigonella foenum-graecum", "Fabaceae", "methi",
                "Tikta (Bitter), Katu (Pungent)", "Ushna (Heating)", "Katu (Pungent)",
                "Balances Vata and Kapha; stimulates digestive Agni and regulates blood glucose", "Seeds & Leaves",
                "Ancient culinary and therapeutic seed renowned for balancing metabolism, cholesterol, and enhancing breastmilk production.",
                "Prescribed for diabetes (Madhumeha), postpartum recovery, joint stiffness, indigestion, and bloating.",
                "Galactomannan soluble fiber and 4-hydroxyisoleucine stimulate glucose-dependent insulin secretion and delay carbohydrate absorption.",
                "Seed powder: 3–5 grams with warm water before meals; soaked seeds consumed in morning.",
                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?auto=format&fit=crop&w=800&q=80", ToxicityLevel.SAFE,
                "Hypoglycemia, severe diarrhea.",
                "Enhances action of antidiabetic drugs (monitor glucose).",
                "Avoid high pharmacological doses during early pregnancy; excellent during lactation.",
                "Mild maple syrup scent in sweat/urine (harmless).", "Reassure patient.",
                "Bhavaprakasha Nighantu, Dhanyavarga", "Nutr J. 2014; 13:7"));

        // 33. Jeera / Cumin
        herbs.add(buildHerb("Cumin (Jeera)", "Jīraka (जीरक)", "Cuminum cyminum", "Apiaceae", "jeera",
                "Katu (Pungent), Tikta (Bitter)", "Ushna (Heating)", "Katu (Pungent)",
                "Balances Vata and Kapha; kindle digestive Agni without aggravating Pitta", "Dried Seeds",
                "Derives from 'Jirna' (digestion); celebrated digestive carminative, toxin destroyer (Deepana-Pachana), and uterine cleanser.",
                "Prescribed for bloating, colic, diarrhea, sluggish metabolism, and postpartum maternal recovery.",
                "Cuminaldehyde and thymol stimulate salivary and pancreatic digestive enzyme secretion.",
                "Seeds: 2–4 grams boiled in water (Jeera water); Churna: 1–3 grams with buttermilk.",
                "https://images.unsplash.com/photo-1509358271058-acd22cc93898?auto=format&fit=crop&w=800&q=80", ToxicityLevel.SAFE,
                "None noted at culinary or therapeutic dosages.", "None reported.",
                "Completely safe during pregnancy and postpartum.",
                "None.", "N/A",
                "Charaka Samhita, Sutra Sthana 27", "Food Chem. 2005; 92(4):641–647"));

        // 34. Dhanya / Coriander
        herbs.add(buildHerb("Coriander (Dhanyaka)", "Dhānyaka (धान्यक)", "Coriandrum sativum", "Apiaceae", "dhanya",
                "Kashaya (Astringent), Tikta (Bitter), Madhura (Sweet)", "Sheeta (Cooling)", "Madhura (Sweet)",
                "Tridosha balancing, premier cooling herb for Pitta digestive inflammation and burning urine", "Seeds & Fresh Leaves",
                "Cooling digestive herb that soothes gastric burning, relieves urinary heat, and clears systemic inflammation.",
                "Prescribed for hyperacidity, cystitis, urinary burning, skin rashes, and summer heat.",
                "Linalool and pinene exert smooth-muscle antispasmodic and gentle diuretic activities.",
                "Seed infusion (Hima / Cold Infusion): 20–40 ml; Powder: 2–5 grams.",
                "https://images.unsplash.com/photo-1540420773420-3366772f4999?auto=format&fit=crop&w=800&q=80", ToxicityLevel.SAFE,
                "None.", "None reported.",
                "Completely safe during pregnancy and lactation.",
                "None.", "N/A",
                "Sushruta Samhita, Sutra Sthana 46", "Phytother Res. 2009; 23(12):1650–1654"));

        // 35. Saunf / Fennel
        herbs.add(buildHerb("Fennel (Mishreya / Saunf)", "Miśreyā (मिश्रेया)", "Foeniculum vulgare", "Apiaceae", "saunf",
                "Madhura (Sweet), Tikta (Bitter), Katu (Pungent)", "Sheeta (Cooling)", "Madhura (Sweet)",
                "Tridosha balancing; especially soothing to Pitta and Vata in GI tract", "Dried Seeds",
                "Classical post-meal digestive and carminative herb that strengthens Agni without overheating Pitta.",
                "Prescribed for infant colic, acid reflux, menstrual cramps, indigestion, and gas.",
                "Anethole and fenchone relax intestinal smooth muscles and inhibit bacterial gas formation.",
                "Seeds: 2–5 grams chewed after meals or brewed as warm tea.",
                "https://images.unsplash.com/photo-1515377905703-c4788e51af15?auto=format&fit=crop&w=800&q=80", ToxicityLevel.SAFE,
                "Estrogen-sensitive conditions (in mega-extract doses).", "None significant.",
                "Safe in culinary and standard infusion doses.",
                "None.", "N/A",
                "Bhavaprakasha Nighantu, Haritakyadi Varga", "Biomed Res Int. 2014; 2014:842674"));

        // 36. Ajwain / Carom
        herbs.add(buildHerb("Carom Seeds (Ajwain / Yavani)", "Yavānī (यवानी)", "Trachyspermum ammi", "Apiaceae", "ajwain",
                "Katu (Pungent), Tikta (Bitter)", "Ushna (Very Heating)", "Katu (Pungent)",
                "Pacifies Vata and Kapha; instant destroyer of gas, spasms, and intestinal colic", "Seeds",
                "Fastest-acting carminative in Ayurveda for spasmodic abdominal pain, bloating, and cold congestion.",
                "Prescribed for acute flatulence, stomach cramps, cough, and digestive sluggishness.",
                "Thymol provides powerful antispasmodic, antibacterial, and anti-flatulent relief.",
                "Powder: 1–2 grams with warm water and rock salt.",
                "https://images.unsplash.com/photo-1598511726623-d2e9996892f0?auto=format&fit=crop&w=800&q=80", ToxicityLevel.SAFE,
                "Hyperacidity, bleeding disorders.", "None reported.",
                "Use in moderate culinary doses during pregnancy.",
                "Heating sensation if taken in excess.", "Take with cool water or buttermilk.",
                "Charaka Samhita, Sutra Sthana 27", "Pharmacognosy Res. 2012; 4(4):215–218"));

        herbRepository.saveAll(herbs);
        log.info("HerbDataSeeder: Successfully seeded {} classical Ayurvedic herbs.", herbs.size());
    }

    private Herb buildHerb(String commonName, String sanskritName, String botanicalName, String family,
                           String slug, String rasa, String virya, String vipaka, String doshaEffect,
                           String plantPart, String shortDesc, String tradUses, String modernResearch,
                           String dosage, String imageUrl, ToxicityLevel toxicity, String contra,
                           String drugInteractions, String pregSafety, String sideEffects, String firstAid,
                           String classicalRef, String researchRef) {

        Herb herb = Herb.builder()
                .commonName(commonName)
                .sanskritName(sanskritName)
                .botanicalName(botanicalName)
                .family(family)
                .slug(slug)
                .rasaTaste(rasa)
                .viryaPotency(virya)
                .vipakaPostDigestive(vipaka)
                .doshaEffect(doshaEffect)
                .plantPartUsed(plantPart)
                .shortDescription(shortDesc)
                .traditionalUses(tradUses)
                .modernResearch(modernResearch)
                .dosageInstructions(dosage)
                .imageUrl(imageUrl)
                .toxicityLevel(toxicity)
                .contraindications(contra)
                .drugInteractions(drugInteractions)
                .pregnancyLactationSafety(pregSafety)
                .sideEffects(sideEffects)
                .firstAidMeasures(firstAid)
                .classicalTreatiseReference(classicalRef)
                .build();

        PlantSafety safety = PlantSafety.builder()
                .herb(herb)
                .toxicityLevel(toxicity)
                .generalPrecautions(contra)
                .drugInteractions(drugInteractions)
                .pregnancySafety(pregSafety)
                .sideEffects(sideEffects)
                .firstAidSteps(firstAid)
                .build();

        herb.setSafetyProfile(safety);
        return herb;
    }
}
