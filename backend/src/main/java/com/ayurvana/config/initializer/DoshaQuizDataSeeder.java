package com.ayurvana.config.initializer;

import com.ayurvana.model.entity.DoshaQuizOption;
import com.ayurvana.model.entity.DoshaQuizQuestion;
import com.ayurvana.model.enums.DoshaType;
import com.ayurvana.repository.DoshaQuizQuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DoshaQuizDataSeeder {

    private final DoshaQuizQuestionRepository questionRepository;

    public void seedQuizQuestions() {
        if (questionRepository.count() >= 12) {
            return;
        }

        List<DoshaQuizQuestion> questions = new ArrayList<>();

        // 1. Physical Frame & Body Build
        questions.add(buildQuestion("Physical Frame & Structure",
                "How would you describe your natural physical body frame and bone structure?", 1,
                List.of(
                        new OptionData("Slender, thin, light-boned, prominent joints, difficulty gaining weight", DoshaType.VATA, 1, 1),
                        new OptionData("Medium build, athletic, well-proportioned, moderate muscle development", DoshaType.PITTA, 1, 2),
                        new OptionData("Broad, solid, sturdy frame, heavy-boned, gains weight easily, well-padded", DoshaType.KAPHA, 1, 3)
                )));

        // 2. Skin Nature & Complexion
        questions.add(buildQuestion("Skin Nature & Complexion",
                "What is the general texture and tendency of your skin?", 2,
                List.of(
                        new OptionData("Dry, rough, thin, cool to touch, chaps easily in winter", DoshaType.VATA, 1, 1),
                        new OptionData("Warm, reddish or freckled, prone to breakouts, sensitive, sweats easily", DoshaType.PITTA, 1, 2),
                        new OptionData("Thick, smooth, soft, naturally moist/oily, cool, youthful elasticity", DoshaType.KAPHA, 1, 3)
                )));

        // 3. Hair Texture & Volume
        questions.add(buildQuestion("Hair Characteristics",
                "How would you characterize your natural hair?", 3,
                List.of(
                        new OptionData("Dry, brittle, frizzy, split ends, coarse or naturally thin", DoshaType.VATA, 1, 1),
                        new OptionData("Fine, soft, straight, prone to premature greying or early thinning/balding", DoshaType.PITTA, 1, 2),
                        new OptionData("Thick, dense, abundant, wavy, glossy, strong and slow to grey", DoshaType.KAPHA, 1, 3)
                )));

        // 4. Appetite & Digestion (Agni)
        questions.add(buildQuestion("Appetite & Digestion (Agni)",
                "How does your digestive fire (Agni) behave throughout the day?", 4,
                List.of(
                        new OptionData("Irregular (Vishama Agni): sometimes very hungry, sometimes forgetting to eat; prone to gas/bloating", DoshaType.VATA, 1, 1),
                        new OptionData("Intense (Tikshna Agni): strong, sharp appetite; irritable ('hangry') if meals are delayed; fast digestion", DoshaType.PITTA, 1, 2),
                        new OptionData("Slow/Steady (Manda Agni): can skip meals without discomfort, slow digestion, feels heavy after eating", DoshaType.KAPHA, 1, 3)
                )));

        // 5. Bowel Elimination
        questions.add(buildQuestion("Bowel Regularity",
                "What is your typical pattern of bowel elimination?", 5,
                List.of(
                        new OptionData("Tendency towards dryness, irregularity, hard stools, or constipation", DoshaType.VATA, 1, 1),
                        new OptionData("Regular, multiple times daily, soft or loose stools, burning sensation if spicy", DoshaType.PITTA, 1, 2),
                        new OptionData("Regular, once daily, slow, well-formed, heavy or sticky stools", DoshaType.KAPHA, 1, 3)
                )));

        // 6. Sleep Patterns & Dreams
        questions.add(buildQuestion("Sleep Quality & Dreams",
                "What is your natural sleep depth and quality?", 6,
                List.of(
                        new OptionData("Light, interrupted, easily awakened by noise, dreams of flying, moving, or falling", DoshaType.VATA, 1, 1),
                        new OptionData("Moderate (6–7 hours), sound sleep, dreams of adventure, fire, passion, or debate", DoshaType.PITTA, 1, 2),
                        new OptionData("Deep, heavy (8+ hours), hard to wake up in the morning, dreams of water, romance, or serene nature", DoshaType.KAPHA, 1, 3)
                )));

        // 7. Mental Disposition & Thinking
        questions.add(buildQuestion("Mental Processing & Intellect",
                "How does your mind process information and make decisions?", 7,
                List.of(
                        new OptionData("Quick to grasp new ideas, highly creative, imaginative, but quick to forget", DoshaType.VATA, 1, 1),
                        new OptionData("Sharp, analytical, logical, decisive, goal-driven, excellent focus", DoshaType.PITTA, 1, 2),
                        new OptionData("Methodical, deliberate, takes time to learn, but possesses exceptional long-term memory", DoshaType.KAPHA, 1, 3)
                )));

        // 8. Emotional Stress Response
        questions.add(buildQuestion("Stress & Emotional Response",
                "When under high stress or unexpected pressure, what is your primary reaction?", 8,
                List.of(
                        new OptionData("Anxiety, worry, racing thoughts, restlessness, fear, indecisiveness", DoshaType.VATA, 1, 1),
                        new OptionData("Frustration, anger, impatience, critical thinking, irritability", DoshaType.PITTA, 1, 2),
                        new OptionData("Withdrawal, avoidance, stubbornness, procrastination, emotional eating", DoshaType.KAPHA, 1, 3)
                )));

        // 9. Weather & Climate Preference
        questions.add(buildQuestion("Weather & Temperature Sensitivity",
                "Which climate do you find most uncomfortable or aggravating?", 9,
                List.of(
                        new OptionData("Cold, dry, and windy weather; crave warm sun and cozy humid rooms", DoshaType.VATA, 1, 1),
                        new OptionData("Hot, humid, direct sunlight; crave cool breezes, air conditioning, and ice drinks", DoshaType.PITTA, 1, 2),
                        new OptionData("Cold, damp, overcast, rainy weather; crave warm, dry, active conditions", DoshaType.KAPHA, 1, 3)
                )));

        // 10. Physical Stamina & Energy
        questions.add(buildQuestion("Physical Stamina & Activity",
                "How would you describe your daily physical energy levels?", 10,
                List.of(
                        new OptionData("Comes in bursts of high enthusiasm followed by rapid fatigue; easily exhausted", DoshaType.VATA, 1, 1),
                        new OptionData("Strong, focused, moderate-to-high stamina, competitive and pushing through limits", DoshaType.PITTA, 1, 2),
                        new OptionData("Steady, enduring stamina; slow to get started but can sustain long endurance", DoshaType.KAPHA, 1, 3)
                )));

        // 11. Speech & Voice
        questions.add(buildQuestion("Speech & Communication Style",
                "What is your natural tone of voice and conversation pace?", 11,
                List.of(
                        new OptionData("Rapid, talkative, enthusiastic, skips between topics, voice can get hoarse/dry", DoshaType.VATA, 1, 1),
                        new OptionData("Clear, sharp, articulate, persuasive, commanding, sometimes cutting tone", DoshaType.PITTA, 1, 2),
                        new OptionData("Slow, melodious, calm, gentle, pleasant, speaks only when necessary", DoshaType.KAPHA, 1, 3)
                )));

        // 12. Financial & Resource Habits
        questions.add(buildQuestion("Spending & Financial Tendency",
                "How do you typically manage money and personal possessions?", 12,
                List.of(
                        new OptionData("Impulsive spender, spends on immediate joys or novelty, money moves fast", DoshaType.VATA, 1, 1),
                        new OptionData("Purposeful spender on luxury, quality items, investments, or tools for achievement", DoshaType.PITTA, 1, 2),
                        new OptionData("Natural saver, cautious with expenditures, accumulates wealth and security", DoshaType.KAPHA, 1, 3)
                )));

        questionRepository.saveAll(questions);
        log.info("DoshaQuizDataSeeder: Successfully seeded {} clinical Prakriti diagnostic questions.", questions.size());
    }

    private DoshaQuizQuestion buildQuestion(String category, String questionText, int order, List<OptionData> options) {
        DoshaQuizQuestion q = DoshaQuizQuestion.builder()
                .category(category)
                .questionText(questionText)
                .questionOrder(order)
                .build();

        List<DoshaQuizOption> optEntities = new ArrayList<>();
        for (OptionData od : options) {
            optEntities.add(DoshaQuizOption.builder()
                    .question(q)
                    .optionText(od.text)
                    .doshaType(od.dosha)
                    .scoreWeight(od.weight)
                    .optionOrder(od.order)
                    .build());
        }
        q.setOptions(optEntities);
        return q;
    }

    private static class OptionData {
        String text;
        DoshaType dosha;
        int weight;
        int order;

        OptionData(String text, DoshaType dosha, int weight, int order) {
            this.text = text;
            this.dosha = dosha;
            this.weight = weight;
            this.order = order;
        }
    }
}
