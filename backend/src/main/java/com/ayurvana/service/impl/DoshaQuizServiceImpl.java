package com.ayurvana.service.impl;

import com.ayurvana.dto.request.DoshaQuizEvaluationRequest;
import com.ayurvana.dto.response.DoshaQuizEvaluationResponse;
import com.ayurvana.dto.response.DoshaQuizOptionResponse;
import com.ayurvana.dto.response.DoshaQuizQuestionResponse;
import com.ayurvana.model.entity.DoshaAssessmentResult;
import com.ayurvana.model.entity.DoshaQuizOption;
import com.ayurvana.model.entity.DoshaQuizQuestion;
import com.ayurvana.model.entity.User;
import com.ayurvana.model.enums.DoshaType;
import com.ayurvana.repository.DoshaAssessmentResultRepository;
import com.ayurvana.repository.DoshaQuizQuestionRepository;
import com.ayurvana.repository.UserRepository;
import com.ayurvana.service.DoshaQuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoshaQuizServiceImpl implements DoshaQuizService {

    private final DoshaQuizQuestionRepository questionRepository;
    private final DoshaAssessmentResultRepository resultRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<DoshaQuizQuestionResponse> getAllQuestions() {
        return questionRepository.findAllByOrderByQuestionOrderAsc().stream()
                .map(this::mapToQuestionResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DoshaQuizEvaluationResponse evaluateQuiz(DoshaQuizEvaluationRequest request, String userEmail) {
        List<DoshaQuizQuestion> allQuestions = questionRepository.findAll();
        Map<Long, DoshaQuizOption> optionMap = new HashMap<>();
        for (DoshaQuizQuestion q : allQuestions) {
            for (DoshaQuizOption opt : q.getOptions()) {
                optionMap.put(opt.getId(), opt);
            }
        }

        int vataScore = 0;
        int pittaScore = 0;
        int kaphaScore = 0;

        for (Long optId : request.getSelectedOptionIds()) {
            DoshaQuizOption opt = optionMap.get(optId);
            if (opt != null) {
                int weight = opt.getScoreWeight() != null ? opt.getScoreWeight() : 1;
                if (opt.getDoshaType() == DoshaType.VATA) vataScore += weight;
                else if (opt.getDoshaType() == DoshaType.PITTA) pittaScore += weight;
                else if (opt.getDoshaType() == DoshaType.KAPHA) kaphaScore += weight;
            }
        }

        int total = Math.max(1, vataScore + pittaScore + kaphaScore);
        BigDecimal vataPct = BigDecimal.valueOf((vataScore * 100.0) / total).setScale(1, RoundingMode.HALF_UP);
        BigDecimal pittaPct = BigDecimal.valueOf((pittaScore * 100.0) / total).setScale(1, RoundingMode.HALF_UP);
        BigDecimal kaphaPct = BigDecimal.valueOf((kaphaScore * 100.0) / total).setScale(1, RoundingMode.HALF_UP);

        // Determine primary and secondary dosha
        List<Map.Entry<DoshaType, Integer>> scores = new ArrayList<>();
        scores.add(new AbstractMap.SimpleEntry<>(DoshaType.VATA, vataScore));
        scores.add(new AbstractMap.SimpleEntry<>(DoshaType.PITTA, pittaScore));
        scores.add(new AbstractMap.SimpleEntry<>(DoshaType.KAPHA, kaphaScore));
        scores.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        DoshaType primary = scores.get(0).getKey();
        DoshaType secondary = scores.get(1).getKey();

        String constitution;
        if (Math.abs(scores.get(0).getValue() - scores.get(1).getValue()) <= 1 &&
            Math.abs(scores.get(1).getValue() - scores.get(2).getValue()) <= 1) {
            constitution = "Tridoshic (Sama Prakriti)";
            primary = DoshaType.TRIDOSHIC;
            secondary = null;
        } else if (Math.abs(scores.get(0).getValue() - scores.get(1).getValue()) <= 2) {
            constitution = primary.name() + "-" + secondary.name() + " (Dual Constitution / Dvidohaja)";
        } else {
            constitution = primary.name() + " Dominant (Eka-Doshaja)";
        }

        List<String> dietary = getDietaryAdvice(primary);
        List<String> lifestyle = getLifestyleAdvice(primary);
        List<String> herbs = getBalancingHerbs(primary);
        List<String> yoga = getYogaPoses(primary);
        List<String> foodsFavour = getFoodsToFavour(primary);
        List<String> foodsAvoid = getFoodsToAvoid(primary);
        String summary = getSummary(primary, constitution);

        if (userEmail != null) {
            userRepository.findByEmail(userEmail).ifPresent(user -> {
                DoshaAssessmentResult result = DoshaAssessmentResult.builder()
                        .user(user)
                        .primaryDosha(primary)
                        .secondaryDosha(secondary)
                        .prakritiConstitution(constitution)
                        .vataPercentage(vataPct)
                        .pittaPercentage(pittaPct)
                        .kaphaPercentage(kaphaPct)
                        .dietaryRecommendations(String.join("; ", dietary))
                        .lifestyleRecommendations(String.join("; ", lifestyle))
                        .recommendedHerbs(String.join("; ", herbs))
                        .recommendedYoga(String.join("; ", yoga))
                        .build();
                resultRepository.save(result);

                user.setDoshaType(primary);
                userRepository.save(user);
            });
        }

        return DoshaQuizEvaluationResponse.builder()
                .primaryDosha(primary)
                .secondaryDosha(secondary)
                .prakritiConstitution(constitution)
                .vataPercentage(vataPct)
                .pittaPercentage(pittaPct)
                .kaphaPercentage(kaphaPct)
                .vataScore(vataScore)
                .pittaScore(pittaScore)
                .kaphaScore(kaphaScore)
                .constitutionalSummary(summary)
                .dietaryRecommendations(dietary)
                .lifestylePractices(lifestyle)
                .balancingHerbs(herbs)
                .recommendedYogaPoses(yoga)
                .foodsToFavour(foodsFavour)
                .foodsToAvoid(foodsAvoid)
                .build();
    }

    private DoshaQuizQuestionResponse mapToQuestionResponse(DoshaQuizQuestion question) {
        List<DoshaQuizOptionResponse> options = question.getOptions().stream()
                .map(opt -> DoshaQuizOptionResponse.builder()
                        .id(opt.getId())
                        .optionText(opt.getOptionText())
                        .doshaType(opt.getDoshaType())
                        .scoreWeight(opt.getScoreWeight())
                        .optionOrder(opt.getOptionOrder())
                        .build())
                .collect(Collectors.toList());

        return DoshaQuizQuestionResponse.builder()
                .id(question.getId())
                .category(question.getCategory())
                .questionText(question.getQuestionText())
                .questionOrder(question.getQuestionOrder())
                .options(options)
                .build();
    }

    private String getSummary(DoshaType dosha, String constitution) {
        if (dosha == DoshaType.VATA) {
            return "Your primary constitution is governed by the elements of Air and Ether (Vata). You possess quick intellect, creativity, and enthusiasm. When out of balance, Vata can manifest as dry skin, irregular digestion, anxiety, and light sleep. Grounding foods, warm oils, and structured routines will maintain your optimal balance.";
        } else if (dosha == DoshaType.PITTA) {
            return "Your primary constitution is governed by Fire and Water (Pitta). You possess strong metabolism, sharp intellect, natural leadership, and high stamina. When aggravated, Pitta manifests as inflammation, hyperacidity, irritability, and skin redness. Cooling foods, moderation, and peaceful environments keep you centered.";
        } else if (dosha == DoshaType.KAPHA) {
            return "Your primary constitution is governed by Earth and Water (Kapha). You possess calm composure, loyalty, strong immunity, and robust stamina. When imbalanced, Kapha can lead to lethargy, sluggish digestion, weight gain, and sinus congestion. Pungent spices, invigorating exercise, and warming foods restore your vitality.";
        } else {
            return "You have a rare and harmonious Tridoshic constitution where Vata, Pitta, and Kapha exist in balanced proportion. Adjust your diet and routines with seasonal shifts (Ritucharya) to sustain this natural harmony.";
        }
    }

    private List<String> getDietaryAdvice(DoshaType dosha) {
        if (dosha == DoshaType.VATA) {
            return List.of("Eat warm, freshly cooked, moist foods with healthy fats (ghee, sesame oil)", "Favour sweet, sour, and salty tastes", "Avoid dry, raw, cold salads and iced drinks", "Maintain regular eating times");
        } else if (dosha == DoshaType.PITTA) {
            return List.of("Eat cooling, moderately heavy foods with sweet, bitter, and astringent tastes", "Use coconut oil, ghee, and cooling herbs like cilantro and fennel", "Avoid excessively spicy, pungent, fermented, and sour foods", "Do not skip meals or let yourself get overly hungry");
        } else if (dosha == DoshaType.KAPHA) {
            return List.of("Eat light, dry, warm, and freshly spiced foods with pungent, bitter, and astringent tastes", "Use warming spices like ginger, black pepper, and cinnamon liberally", "Avoid dairy, heavy fried foods, sweets, and cold drinks", "Fast periodically or consume lighter evening meals");
        } else {
            return List.of("Eat seasonal, fresh, whole foods", "Balance all six tastes (Shad Rasa) in every main meal", "Adjust diet to hot summers (cooling foods) and cold winters (warming foods)");
        }
    }

    private List<String> getLifestyleAdvice(DoshaType dosha) {
        if (dosha == DoshaType.VATA) {
            return List.of("Daily warm oil self-massage (Abhyanga) with sesame or Mahanarayan oil", "Consistent bedtime by 10:00 PM and slow, gentle morning routines", "Keep warm and protected from cold, dry winds");
        } else if (dosha == DoshaType.PITTA) {
            return List.of("Coconut oil self-massage, especially on the scalp and soles of feet", "Avoid direct midday sun and overheating activities", "Engage in non-competitive recreational activities and nature walks");
        } else if (dosha == DoshaType.KAPHA) {
            return List.of("Daily dry brushing (Garshana) or vigorous mustard oil massage", "Early rising before 6:00 AM; avoid daytime naps", "Engage in vigorous daily aerobic exercise and dynamic yoga");
        } else {
            return List.of("Follow classical Dinacharya (daily routine)", "Align lifestyle with natural seasonal rhythms (Ritucharya)");
        }
    }

    private List<String> getBalancingHerbs(DoshaType dosha) {
        if (dosha == DoshaType.VATA) {
            return List.of("Ashwagandha", "Brahmi", "Shatavari", "Dashamoola", "Bala");
        } else if (dosha == DoshaType.PITTA) {
            return List.of("Guduchi (Giloy)", "Amalaki (Amla)", "Shatavari", "Neem", "Brahmi");
        } else if (dosha == DoshaType.KAPHA) {
            return List.of("Tulsi (Holy Basil)", "Trikatu (Pippali, Maricha, Shunthi)", "Punarnava", "Guggulu", "Bibhitaki");
        } else {
            return List.of("Triphala", "Chyawanprash", "Tulsi", "Ashwagandha");
        }
    }

    private List<String> getYogaPoses(DoshaType dosha) {
        if (dosha == DoshaType.VATA) {
            return List.of("Vrikshasana (Tree Pose)", "Paschimottanasana (Seated Forward Bend)", "Bhujangasana (Cobra)", "Nadi Shodhana Pranayama", "Extended Shavasana");
        } else if (dosha == DoshaType.PITTA) {
            return List.of("Chandra Namaskar (Moon Salutations)", "Sheetali / Sheetkari Pranayama", "Matsyasana (Fish Pose)", "Bhujangasana", "Shavasana");
        } else if (dosha == DoshaType.KAPHA) {
            return List.of("Surya Namaskar (Sun Salutations - 12 rounds)", "Virabhadrasana (Warrior I & II)", "Kapalabhati Pranayama", "Bhastrika", "Dhanurasana (Bow Pose)");
        } else {
            return List.of("Surya Namaskar", "Padmasana", "Sarvangasana", "Nadi Shodhana Pranayama");
        }
    }

    private List<String> getFoodsToFavour(DoshaType dosha) {
        if (dosha == DoshaType.VATA) {
            return List.of("Basmati rice, oats, quinoa", "Cooked root vegetables (sweet potatoes, carrots, beets)", "Ghee, sesame oil, olive oil", "Almonds, walnuts, soaked seeds", "Ripe sweet fruits (bananas, mangoes, dates, figs)");
        } else if (dosha == DoshaType.PITTA) {
            return List.of("Basmati rice, barley, oats", "Sweet apples, melons, grapes, sweet pears", "Leafy greens, cucumbers, zucchini, broccoli", "Coconut oil, ghee", "Fennel, coriander, cardamom, mint");
        } else if (dosha == DoshaType.KAPHA) {
            return List.of("Quinoa, buckwheat, millet, barley", "Apples, pomegranates, cranberries", "Broccoli, cabbage, kale, spinach, radishes", "Ginger, black pepper, turmeric, cumin, fenugreek", "Light mung dhal soup");
        } else {
            return List.of("Fresh organic fruits & vegetables", "Whole grains, mung beans, pure cow ghee");
        }
    }

    private List<String> getFoodsToAvoid(DoshaType dosha) {
        if (dosha == DoshaType.VATA) {
            return List.of("Raw salads, iced drinks, carbonated sodas", "Dry crackers, chips, popcorn", "Excessive bitter, pungent, or astringent foods", "Caffeine and white refined sugar");
        } else if (dosha == DoshaType.PITTA) {
            return List.of("Deep fried foods, hot chili peppers, raw garlic", "Fermented foods, vinegar, pickles", "Tomatoes, sour citrus fruits, red meat", "Excessive alcohol and caffeine");
        } else if (dosha == DoshaType.KAPHA) {
            return List.of("Ice cream, heavy cheeses, cold milk", "Fried greasy snacks, fast food", "Excessive salt, refined sweets, pastries", "Cold drinks and heavy red meats");
        } else {
            return List.of("Processed junk food, stale/leftover food (Paryushita)");
        }
    }
}
