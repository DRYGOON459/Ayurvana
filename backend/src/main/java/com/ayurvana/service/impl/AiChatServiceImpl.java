package com.ayurvana.service.impl;

import com.ayurvana.dto.request.AiChatMessageRequest;
import com.ayurvana.dto.response.AiChatMessageResponse;
import com.ayurvana.dto.response.AiChatSuggestionResponse;
import com.ayurvana.model.entity.AyurvedicRecipe;
import com.ayurvana.model.entity.DoctorProfile;
import com.ayurvana.model.entity.Herb;
import com.ayurvana.model.entity.Product;
import com.ayurvana.service.AiChatService;
import com.ayurvana.service.ai.AyurvedicDynamicSynthesizer;
import com.ayurvana.service.ai.AyurvedicKnowledgeRetriever;
import com.ayurvana.service.ai.GeminiApiClient;
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

    private final GeminiApiClient geminiApiClient;
    private final AyurvedicKnowledgeRetriever knowledgeRetriever;
    private final AyurvedicDynamicSynthesizer dynamicSynthesizer;

    @Override
    @Transactional(readOnly = true)
    public AiChatMessageResponse processMessage(AiChatMessageRequest request, String userEmail) {
        String query = request.getMessage() != null ? request.getMessage().trim() : "";
        String userDosha = request.getUserDosha();

        // 1. Initial Knowledge Retrieval (RAG from Database)
        AyurvedicKnowledgeRetriever.RetrievalResult ragResult = knowledgeRetriever.retrieveContext(query, Collections.emptyList());

        AiChatMessageResponse.AiChatMessageResponseBuilder responseBuilder = AiChatMessageResponse.builder();

        // 2. Try Gemini Generative AI API if configured
        Optional<GeminiApiClient.GeminiResponsePayload> geminiResponse = Optional.empty();
        if (geminiApiClient.isConfigured()) {
            List<Map<String, String>> historyList = new ArrayList<>();
            if (request.getHistory() != null) {
                for (AiChatMessageRequest.HistoryItem h : request.getHistory()) {
                    if (h.getContent() != null && !h.getContent().isBlank()) {
                        historyList.add(Map.of(
                                "role", h.getRole() != null ? h.getRole() : "user",
                                "content", h.getContent()
                        ));
                    }
                }
            }

            geminiResponse = geminiApiClient.generateAyurvedicResponse(
                    query,
                    userDosha,
                    historyList,
                    ragResult.getContextSummary()
            );
        }

        List<Herb> matchedHerbs = new ArrayList<>(ragResult.getMatchedHerbs());
        List<Product> matchedProducts = new ArrayList<>(ragResult.getMatchedProducts());
        List<AyurvedicRecipe> matchedRecipes = new ArrayList<>(ragResult.getMatchedRecipes());
        List<DoctorProfile> matchedDoctors = new ArrayList<>(ragResult.getMatchedDoctors());

        if (geminiResponse.isPresent()) {
            GeminiApiClient.GeminiResponsePayload payload = geminiResponse.get();
            log.info("Successfully retrieved AI response from Gemini API for query: {}", query);

            responseBuilder.reply(payload.reply)
                    .sanskritShloka(payload.sanskritShloka != null ? payload.sanskritShloka : "सर्वे भवन्तु सुखिनः सर्वे सन्तु निरामयाः।")
                    .shlokaMeaning(payload.shlokaMeaning != null ? payload.shlokaMeaning : "May all beings be healthy and free from illness.")
                    .classicalTreatiseReference(payload.classicalTreatiseReference != null ? payload.classicalTreatiseReference : "Charaka Samhita")
                    .quickReplies(payload.quickReplies != null && !payload.quickReplies.isEmpty() ? payload.quickReplies : List.of("What is my Dosha?", "Ayurvedic diet guide", "Consult Doctor"));

            // Refine retrieval with AI generated keywords if initial matched set is empty
            List<String> extraKeywords = new ArrayList<>();
            if (payload.recommendedHerbKeywords != null) extraKeywords.addAll(payload.recommendedHerbKeywords);
            if (payload.recommendedProductKeywords != null) extraKeywords.addAll(payload.recommendedProductKeywords);
            if (payload.recommendedRecipeKeywords != null) extraKeywords.addAll(payload.recommendedRecipeKeywords);

            if (!extraKeywords.isEmpty()) {
                AyurvedicKnowledgeRetriever.RetrievalResult extraRag = knowledgeRetriever.retrieveContext(query, extraKeywords);
                if (matchedHerbs.isEmpty()) matchedHerbs.addAll(extraRag.getMatchedHerbs());
                if (matchedProducts.isEmpty()) matchedProducts.addAll(extraRag.getMatchedProducts());
                if (matchedRecipes.isEmpty()) matchedRecipes.addAll(extraRag.getMatchedRecipes());
            }

        } else {
            // 3. Fallback to Dynamic Ayurvedic Synthesizer Engine
            log.info("Invoking Ayurvedic Dynamic Synthesizer for query: {}", query);
            AyurvedicDynamicSynthesizer.SynthesizedResponse syn = dynamicSynthesizer.synthesize(query, userDosha);

            responseBuilder.reply(syn.getReply())
                    .sanskritShloka(syn.getSanskritShloka())
                    .shlokaMeaning(syn.getShlokaMeaning())
                    .classicalTreatiseReference(syn.getClassicalTreatiseReference())
                    .quickReplies(syn.getQuickReplies());

            List<String> combinedKw = new ArrayList<>();
            if (syn.getHerbKeywords() != null) combinedKw.addAll(syn.getHerbKeywords());
            if (syn.getProductKeywords() != null) combinedKw.addAll(syn.getProductKeywords());
            if (syn.getRecipeKeywords() != null) combinedKw.addAll(syn.getRecipeKeywords());

            AyurvedicKnowledgeRetriever.RetrievalResult extraRag = knowledgeRetriever.retrieveContext(query, combinedKw);
            if (matchedHerbs.isEmpty()) matchedHerbs.addAll(extraRag.getMatchedHerbs());
            if (matchedProducts.isEmpty()) matchedProducts.addAll(extraRag.getMatchedProducts());
            if (matchedRecipes.isEmpty()) matchedRecipes.addAll(extraRag.getMatchedRecipes());
            if (matchedDoctors.isEmpty()) matchedDoctors.addAll(extraRag.getMatchedDoctors());
        }

        // Attach action cards
        responseBuilder.recommendedHerbs(mapHerbs(matchedHerbs))
                .recommendedProducts(mapProducts(matchedProducts))
                .recommendedRecipes(mapRecipes(matchedRecipes))
                .recommendedDoctors(mapDoctors(matchedDoctors));

        return responseBuilder.build();
    }

    @Override
    public AiChatSuggestionResponse getSuggestions() {
        return AiChatSuggestionResponse.builder()
                .welcomeMessage("Welcome to AyurVana Intelligent Health Assistant. Grounded in authentic Ayurvedic treatises, clinical herbs, and personalized dosha balancing.")
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

    private List<AiChatMessageResponse.ProductCard> mapProducts(List<Product> products) {
        return products.stream().map(p -> AiChatMessageResponse.ProductCard.builder()
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
                .build()).collect(Collectors.toList());
    }

    private List<AiChatMessageResponse.RecipeCard> mapRecipes(List<AyurvedicRecipe> recipes) {
        return recipes.stream().map(r -> AiChatMessageResponse.RecipeCard.builder()
                .id(r.getId())
                .title(r.getTitle())
                .sanskritName(r.getSanskritName())
                .slug(r.getSlug())
                .doshaSuitability(r.getDoshaSuitability() != null ? r.getDoshaSuitability().name() : "")
                .mealType(r.getMealType())
                .cookTimeMinutes(r.getCookTimeMinutes())
                .imageUrl(r.getImageUrl())
                .description(r.getDescription())
                .build()).collect(Collectors.toList());
    }

    private List<AiChatMessageResponse.HerbCard> mapHerbs(List<Herb> herbs) {
        return herbs.stream().map(h -> AiChatMessageResponse.HerbCard.builder()
                .commonName(h.getCommonName())
                .sanskritName(h.getSanskritName())
                .scientificName(h.getScientificName())
                .slug(h.getSlug())
                .primaryDoshaEffect(h.getPrimaryDoshaEffect())
                .rasa(h.getRasa())
                .imageUrl(h.getImageUrl())
                .build()).collect(Collectors.toList());
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
