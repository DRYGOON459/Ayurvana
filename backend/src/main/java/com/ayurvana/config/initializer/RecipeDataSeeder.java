package com.ayurvana.config.initializer;

import com.ayurvana.model.entity.AyurvedicRecipe;
import com.ayurvana.model.enums.DoshaType;
import com.ayurvana.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class RecipeDataSeeder {

    private final RecipeRepository recipeRepository;

    public void seedRecipes() {
        if (recipeRepository.count() >= 12) {
            return;
        }

        List<AyurvedicRecipe> recipes = new ArrayList<>();

        // 1. Classical Tridoshic Mung & Rice Kitchari
        recipes.add(buildRecipe(
                "Classical Tridoshic Mung & Basmati Kitchari",
                "Kṛśarā (कृशरा / खिचड़ी)",
                "classical-tridoshic-kitchari",
                DoshaType.TRIDOSHIC,
                "Lunch & Dinner",
                "All Seasons (Especially during Detox & Panchakarma)",
                15, 30, 4, "Easy",
                "The gold standard cleansing and nourishing meal in Ayurveda. Combines yellow split mung dal and aged Basmati rice with digestive spices and pure cow ghee.",
                "Eases digestive burden, detoxifies cellular Ama, balances all three doshas, provides complete amino acid profile.",
                "Ignites gentle digestive Agni without creating excess heat (Tikshna) or sluggishness (Manda).",
                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?auto=format&fit=crop&w=800&q=80",
                List.of(
                        "1/2 cup yellow split mung dal (soaked for 2 hours)",
                        "1/2 cup aged Basmati rice (rinsed)",
                        "4 cups pure filtered water",
                        "2 tablespoons pure cow ghee",
                        "1 teaspoon cumin seeds (Jeera)",
                        "1 teaspoon mustard seeds",
                        "1/2 teaspoon turmeric powder (Haridra)",
                        "1 inch fresh ginger root (finely grated)",
                        "1 pinch hing (asafoetida)",
                        "1 teaspoon rock salt (Saindhava Lavana)",
                        "Fresh cilantro leaves and lime juice for garnish"
                ),
                List.of(
                        "Heat pure cow ghee in a heavy-bottomed pot over medium heat.",
                        "Add mustard seeds and cumin seeds; sizzle until they pop and release fragrant aroma.",
                        "Add grated ginger, turmeric powder, and asafoetida. Sauté gently for 30 seconds.",
                        "Add rinsed Basmati rice and soaked yellow mung dal, stirring to coat the grains in spiced ghee.",
                        "Pour in 4 cups of water and bring to a gentle boil.",
                        "Reduce heat to low, cover with lid, and simmer for 25–30 minutes until soft and porridge-like.",
                        "Stir in rock salt. Garnish with fresh chopped cilantro and a squeeze of fresh lime before serving warm."
                ),
                "Complete protein, rich in easily digestible soluble fiber and prebiotic starch",
                "Charaka Samhita, Sutra Sthana 27"
        ));

        // 2. Golden Haldi Turmeric Elixir (Golden Milk)
        recipes.add(buildRecipe(
                "Ayurvedic Golden Turmeric Elixir (Golden Moon Milk)",
                "Haridrā Dugdha (हरिद्रा दुग्ध)",
                "ayurvedic-golden-turmeric-milk",
                DoshaType.VATA,
                "Bedtime Beverage",
                "Autumn & Winter (Sharad & Hemanta)",
                5, 10, 2, "Easy",
                "A soothing bedtime elixir formulated to reduce systemic inflammation, calm racing thoughts, and induce deep regenerative sleep.",
                "Curcumin combined with healthy lipids enhances cellular absorption, soothes stiff joints, and bolsters respiratory defense.",
                "Nourishes Ojas, grounds Vata in the nervous system, and soothes Pitta inflammation.",
                "https://images.unsplash.com/photo-1615485290382-441e4d049cb5?auto=format&fit=crop&w=800&q=80",
                List.of(
                        "2 cups organic whole cow milk or almond/oat milk",
                        "1 teaspoon high-curcumin organic turmeric powder",
                        "1/4 teaspoon freshly ground black pepper (enhances bioavailability)",
                        "1/2 teaspoon Ceylon cinnamon powder",
                        "2 green cardamom pods (crushed)",
                        "1/4 teaspoon grated nutmeg (Jatiphala - for deep sleep)",
                        "1 teaspoon pure cow ghee or virgin coconut oil",
                        "1 teaspoon raw unheated honey (added when lukewarm)"
                ),
                List.of(
                        "Add milk, turmeric, black pepper, cinnamon, crushed cardamom, and nutmeg into a small saucepan.",
                        "Whisk over medium-low heat and bring to a gentle simmer for 5–7 minutes.",
                        "Turn off heat and stir in 1 teaspoon of pure cow ghee.",
                        "Allow to cool to warm sipping temperature (never heat honey above 40°C).",
                        "Stir in raw honey and sip slowly 30 minutes before sleep."
                ),
                "High antioxidant ORAC value, natural sleep-inducing magnesium and tryptophan",
                "Bhavaprakasha Nighantu, Dugdhavarga"
        ));

        // 3. CCF Digestive Tea (Cumin, Coriander, Fennel)
        recipes.add(buildRecipe(
                "Classical CCF Digestive Detox Tea (Cumin, Coriander, Fennel)",
                "Dīpana Pānīya (दीपन पानीय)",
                "ccf-digestive-tea",
                DoshaType.TRIDOSHIC,
                "All-Day Digestive Beverage",
                "All Seasons",
                2, 8, 4, "Easy",
                "The quintessential classical tri-seed brew that stokes digestive Agni, flushes urinary toxins, and relieves bloating without heating the body.",
                "Relieves gas, stimulates lymphatic drainage, harmonizes digestion, and soothes hyperacidity.",
                "Balances Samana Vayu, Pachaka Pitta, and Kledaka Kapha simultaneously.",
                "https://images.unsplash.com/photo-1598511726623-d2e9996892f0?auto=format&fit=crop&w=800&q=80",
                List.of(
                        "1/2 teaspoon whole cumin seeds (Jeera)",
                        "1/2 teaspoon whole coriander seeds (Dhanyaka)",
                        "1/2 teaspoon whole fennel seeds (Saunf)",
                        "4 cups pure filtered water"
                ),
                List.of(
                        "Combine whole cumin, coriander, and fennel seeds with 4 cups of water in a pot.",
                        "Bring to a rolling boil, then reduce heat and simmer uncovered for 8–10 minutes.",
                        "Strain seeds and pour tea into a thermal flask.",
                        "Sip warm throughout the day between meals."
                ),
                "Zero calorie, mineral rich, natural diuretic and carminative",
                "Sharangadhara Samhita, Madhyama Khanda 2"
        ));

        // 4. Medicated Takra (Ayurvedic Spiced Buttermilk)
        recipes.add(buildRecipe(
                "Medicated Takra (Ayurvedic Spiced Buttermilk)",
                "Samskṛta Takra (संस्कृत तक्र)",
                "medicated-takra-buttermilk",
                DoshaType.TRIDOSHIC,
                "Post-Lunch Digestive Drink",
                "Summer & Monsoon (Grishma & Varsha)",
                5, 5, 2, "Easy",
                "Ancient probiotic nectar referenced in classical texts as superior even to celestial Amrita for curing IBS, hemorrhoids, and malabsorption.",
                "Restores optimal gut microbiome, relieves post-meal heaviness, cures chronic diarrhea and Grahani.",
                "Laghu (light), Deepana (fire-igniting), and Grahi (absorptive/binding).",
                "https://images.unsplash.com/photo-1540420773420-3366772f4999?auto=format&fit=crop&w=800&q=80",
                List.of(
                        "1/2 cup fresh homemade whole milk yogurt",
                        "1.5 cups cool water",
                        "1/4 teaspoon roasted cumin powder",
                        "1/4 teaspoon rock salt (Saindhava Lavana)",
                        "1 pinch asafoetida (hing)",
                        "4–5 fresh curry leaves",
                        "1 teaspoon finely minced fresh ginger",
                        "Fresh cilantro"
                ),
                List.of(
                        "Place fresh yogurt and water in a blender or traditional churner.",
                        "Blend for 2 minutes until frothy; skim off the top butter fat (transforming it into authentic Laghu Takra).",
                        "In a tiny pan, dry toast roasted cumin, hing, and curry leaves for 15 seconds.",
                        "Stir toasted spices, ginger, rock salt, and minced cilantro into the churned buttermilk.",
                        "Serve immediately at room temperature right after lunch."
                ),
                "Rich in live probiotic Lactobacillus strains and easily digestible lactic acid",
                "Charaka Samhita, Chikitsa Sthana 15 (Grahani Chikitsa)"
        ));

        // 5. Ashwagandha & Nutmeg Moon Milk
        recipes.add(buildRecipe(
                "Ashwagandha & Nutmeg Deep Sleep Moon Nectar",
                "Aśvagandhā Nidrā Pānaka (अश्वगन्धा निद्रा पानक)",
                "ashwagandha-sleep-moon-milk",
                DoshaType.VATA,
                "Night-Time Tonic",
                "Autumn & Winter",
                5, 10, 1, "Easy",
                "A deeply grounding botanical milk infusion designed to soothe hyperactive thoughts, quiet Vata in the nervous system, and restore deep REM sleep.",
                "Lowers nighttime cortisol spikes, supports muscle recovery, and restores nervous equilibrium.",
                "Pacifies Prana Vayu and stimulates Tarpaka Kapha in the cerebral cortex.",
                "https://images.unsplash.com/photo-1509358271058-acd22cc93898?auto=format&fit=crop&w=800&q=80",
                List.of(
                        "1 cup grass-fed organic milk or cashew milk",
                        "1 teaspoon organic Ashwagandha root powder",
                        "1/4 teaspoon freshly grated nutmeg (Jatiphala)",
                        "1/4 teaspoon ground cardamom",
                        "1/2 teaspoon pure cow ghee",
                        "1 teaspoon date syrup or jaggery"
                ),
                List.of(
                        "Simmer milk, Ashwagandha powder, cardamom, and nutmeg in a small pot for 6 minutes.",
                        "Turn off heat, whisk in ghee, and sweeten with date syrup.",
                        "Drink warm 45 minutes before sleep in a screen-free environment."
                ),
                "Rich in adaptogenic Withanolides, Myristicin, and soothing peptides",
                "Bhaishajya Ratnavali, Vatavyadhi Adhyaya"
        ));

        // 6. Sattvic Green Moong Detox Soup
        recipes.add(buildRecipe(
                "Sattvic Whole Green Moong Detox Broth",
                "Mudga Yūṣa (मुद्ग यूष)",
                "sattvic-green-moong-soup",
                DoshaType.TRIDOSHIC,
                "Dinner / Light Cleansing Meal",
                "Spring (Vasanta) & Monsoon",
                10, 25, 4, "Easy",
                "Classical Ayurvedic medicinal broth (Yusha) celebrated for lightening the body, breaking fever toxins, and purifying blood.",
                "Easy assimilation, supports natural liver detox pathways, clears fluid retention.",
                "Enhances digestive capacity without creating metabolic residue (Ama).",
                "https://images.unsplash.com/photo-1515377905703-c4788e51af15?auto=format&fit=crop&w=800&q=80",
                List.of(
                        "1 cup whole green moong beans (soaked overnight)",
                        "5 cups water",
                        "1 tablespoon pure cow ghee",
                        "1 teaspoon cumin seeds",
                        "1/2 teaspoon black pepper",
                        "1/2 teaspoon turmeric",
                        "1 teaspoon rock salt",
                        "Fresh coriander and lemon juice"
                ),
                List.of(
                        "Pressure cook or boil soaked green moong beans in 5 cups water with turmeric and salt until very soft (20 mins).",
                        "Mash lightly with the back of a ladle to create a silky, soupy broth.",
                        "In a small pan, temper ghee with cumin seeds and cracked black pepper until fragrant.",
                        "Pour the spiced ghee (Tarka) into the simmering moong soup.",
                        "Garnish with lemon juice and fresh coriander; serve hot."
                ),
                "High in bioavailable zinc, folate, magnesium, and plant-based protein",
                "Sushruta Samhita, Sutra Sthana 46"
        ));

        // 7. Brahmi Medicated Herbal Ghee Elixir
        recipes.add(buildRecipe(
                "Brahmi Medicated Herbal Ghee Elixir",
                "Brāhmī Siddha Ghṛta (ब्राह्मी सिद्ध घृत)",
                "brahmi-ghee-elixir",
                DoshaType.PITTA,
                "Morning Tonic",
                "All Seasons",
                10, 20, 15, "Moderate",
                "A classical lipid-based memory tonic cooked at home to stimulate neurotransmitter synthesis and sharpen focus.",
                "Enhances synaptic memory recall, protects neurons from oxidative stress, calms ADHD.",
                "Nourishes Majja Dhatu (nervous tissue) and pacifies Sadhaka Pitta.",
                "https://images.unsplash.com/photo-1540420773420-3366772f4999?auto=format&fit=crop&w=800&q=80",
                List.of(
                        "1 cup organic grass-fed unsalted butter (melted into ghee)",
                        "2 tablespoons fresh Brahmi leaf juice or organic Brahmi powder paste",
                        "1/2 teaspoon Shankhpushpi powder",
                        "1/4 teaspoon cardamom powder"
                ),
                List.of(
                        "Gently simmer pure ghee with Brahmi herbal paste over lowest flame until all water moisture evaporates and crackling stops.",
                        "Strain through unbleached cheesecloth into a clean glass jar.",
                        "Take 1 teaspoon melted in warm water every morning upon waking."
                ),
                "Rich in butyric acid, omega-3 fatty acids, and memory-enhancing Bacosides",
                "Charaka Samhita, Chikitsa Sthana 1/3"
        ));

        // 8. Tulsi, Ginger & Black Pepper Kadha
        recipes.add(buildRecipe(
                "Tulsi, Ginger & Black Pepper Kadha (Ayush Kwath)",
                "Tulasī Śuṇṭhī Kaṣāya (तुलसी शुण्ठी कषाय)",
                "tulsi-ginger-black-pepper-kadha",
                DoshaType.KAPHA,
                "Morning / Immunity Beverage",
                "Monsoon & Winter",
                5, 12, 3, "Easy",
                "Ministry of AYUSH endorsed classical immune-defense decoction for flu prevention, chest decongestion, and sore throat.",
                "Broad-spectrum antimicrobial, clears sinus mucus, soothes throat scratchiness.",
                "Aggressively destroys Kapha congestion and rekindles dampened digestive fire.",
                "https://images.unsplash.com/photo-1563178406-4cdc2923acbc?auto=format&fit=crop&w=800&q=80",
                List.of(
                        "10–12 fresh Holy Basil (Tulsi) leaves",
                        "1 inch fresh ginger root (crushed)",
                        "1/2 teaspoon coarsely cracked black peppercorns (Maricha)",
                        "1 stick Ceylon cinnamon (Twak)",
                        "3 cups water",
                        "1 teaspoon jaggery or raw honey"
                ),
                List.of(
                        "Coarsely crush Tulsi leaves, ginger, black peppercorns, and cinnamon in a mortar.",
                        "Add crushed herbs to 3 cups of water in a saucepan.",
                        "Boil vigorously until the liquid reduces to half (1.5 cups).",
                        "Strain into mugs, stir in jaggery or warm honey, and drink hot."
                ),
                "Packed with volatile eugenol, gingerols, piperine, and cinnamaldehyde",
                "Ministry of AYUSH Clinical Guidelines & Bhavaprakasha Nighantu"
        ));

        // 9. Spiced Stewed Apples with Cloves & Cinnamon
        recipes.add(buildRecipe(
                "Spiced Stewed Apples with Cloves & Cinnamon (Morning Agni Booster)",
                "Siddha Seva Phala (सिद्ध सेव फल)",
                "spiced-stewed-apples",
                DoshaType.VATA,
                "Breakfast",
                "Autumn & Winter",
                5, 10, 2, "Easy",
                "The ideal first food of the morning in classical Ayurveda. Gentle on digestion, cleanses the gut wall, and primes enzymatic secretions.",
                "Provides soluble pectin fiber, gently awakens bowel motility, balances morning Vata.",
                "Kindles gentle digestive fire without overwhelming gastric mucosa.",
                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?auto=format&fit=crop&w=800&q=80",
                List.of(
                        "2 sweet organic apples (peeled, cored, and sliced)",
                        "4 whole cloves (Lavanga)",
                        "1/2 teaspoon Ceylon cinnamon",
                        "1/4 teaspoon cardamom powder",
                        "1/2 cup pure water",
                        "1 teaspoon pure cow ghee"
                ),
                List.of(
                        "Heat ghee in a saucepan; add cloves and sliced apples.",
                        "Pour in 1/2 cup water, cover with lid, and simmer on low for 8–10 minutes until fork tender.",
                        "Sprinkle cinnamon and cardamom.",
                        "Eat warm as the first food of your morning."
                ),
                "Rich in prebiotic apple pectin, bioflavonoids, and gut-soothing tannins",
                "Classical Ayurvedic Dinacharya Practice"
        ));

        // 10. Cooling Hibiscus & Rose Petal Sharbat
        recipes.add(buildRecipe(
                "Cooling Hibiscus & Rose Petal Elixir (Pitta Pacifier)",
                "Gulāb Japā Puṣpa Śarbat (गुलाब जपा शरबत)",
                "cooling-hibiscus-rose-sharbat",
                DoshaType.PITTA,
                "Afternoon Summer Beverage",
                "Summer (Grishma)",
                10, 5, 4, "Easy",
                "A vibrant ruby-red cooling nectar designed to quench internal burning sensations, soothe irritability, and protect skin from sun damage.",
                "Cools internal body heat, purifies blood (Rakta Shodhana), relieves summer fatigue.",
                "Strongly pacifies aggravated Bhrajaka and Pachaka Pitta.",
                "https://images.unsplash.com/photo-1515377905703-c4788e51af15?auto=format&fit=crop&w=800&q=80",
                List.of(
                        "2 tablespoons dried organic Hibiscus petals (Japa Pushpa)",
                        "2 tablespoons dried organic edible Rose petals (Taruni)",
                        "1/2 teaspoon crushed cardamom",
                        "4 cups cold water",
                        "1 teaspoon soaked sweet basil seeds (Sabja)",
                        "2 tablespoons raw rock sugar candy (Mishri)"
                ),
                List.of(
                        "Steep hibiscus and rose petals in 4 cups hot water for 15 minutes to extract vibrant essence.",
                        "Strain liquid and allow to chill to room temperature or cellar-cool.",
                        "Sweeten with dissolved Mishri and stir in soaked sabja basil seeds and crushed cardamom.",
                        "Serve in glass cups on hot afternoons."
                ),
                "High in anthocyanin antioxidants, vitamin C, and natural cooling mucilage",
                "Bhavaprakasha Nighantu, Pushpavarga"
        ));

        recipeRepository.saveAll(recipes);
        log.info("RecipeDataSeeder: Successfully seeded {} authentic Ayurvedic recipes.", recipes.size());
    }

    private AyurvedicRecipe buildRecipe(String title, String sanskritName, String slug, DoshaType dosha,
                                        String mealType, String season, int prepTime, int cookTime,
                                        int servings, String difficulty, String desc, String benefits,
                                        String agniAction, String imageUrl, List<String> ingredients,
                                        List<String> instructions, String highlight, String reference) {
        return AyurvedicRecipe.builder()
                .title(title)
                .sanskritName(sanskritName)
                .slug(slug)
                .doshaSuitability(dosha)
                .mealType(mealType)
                .seasonSuitability(season)
                .prepTimeMinutes(prepTime)
                .cookTimeMinutes(cookTime)
                .servings(servings)
                .difficulty(difficulty)
                .description(desc)
                .therapeuticBenefits(benefits)
                .digestiveActionAgni(agniAction)
                .imageUrl(imageUrl)
                .ingredients(ingredients)
                .instructions(instructions)
                .nutritionalHighlight(highlight)
                .classicalReference(reference)
                .build();
    }
}
