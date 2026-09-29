package com.ayurvana.service.ai;

import com.ayurvana.model.entity.AyurvedicRecipe;
import com.ayurvana.model.entity.DoctorProfile;
import com.ayurvana.model.entity.Herb;
import com.ayurvana.model.entity.Product;
import com.ayurvana.repository.DoctorProfileRepository;
import com.ayurvana.repository.HerbRepository;
import com.ayurvana.repository.ProductRepository;
import com.ayurvana.repository.RecipeRepository;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class AyurvedicKnowledgeRetriever {

    private final HerbRepository herbRepository;
    private final ProductRepository productRepository;
    private final RecipeRepository recipeRepository;
    private final DoctorProfileRepository doctorProfileRepository;

    @Data
    @Builder
    public static class RetrievalResult {
        private String contextSummary;
        private List<Herb> matchedHerbs;
        private List<Product> matchedProducts;
        private List<AyurvedicRecipe> matchedRecipes;
        private List<DoctorProfile> matchedDoctors;
    }

    public RetrievalResult retrieveContext(String query, List<String> extraKeywords) {
        Set<String> searchTokens = new HashSet<>();
        if (query != null) {
            for (String token : query.toLowerCase().split("[^a-zA-Z0-9]+")) {
                if (token.length() > 2) searchTokens.add(token);
            }
        }
        if (extraKeywords != null) {
            for (String kw : extraKeywords) {
                if (kw != null) {
                    for (String token : kw.toLowerCase().split("[^a-zA-Z0-9]+")) {
                        if (token.length() > 2) searchTokens.add(token);
                    }
                }
            }
        }

        // 1. Match Herbs
        List<Herb> allHerbs = herbRepository.findAll();
        List<Herb> matchedHerbs = allHerbs.stream()
                .filter(h -> matchesScore(h.getCommonName(), h.getSanskritName(), h.getTraditionalUses(), h.getSlug(), searchTokens) > 0)
                .sorted((a, b) -> Integer.compare(
                        matchesScore(b.getCommonName(), b.getSanskritName(), b.getTraditionalUses(), b.getSlug(), searchTokens),
                        matchesScore(a.getCommonName(), a.getSanskritName(), a.getTraditionalUses(), a.getSlug(), searchTokens)
                ))
                .limit(3)
                .collect(Collectors.toList());

        // 2. Match Products
        List<Product> allProducts = productRepository.findAll();
        List<Product> matchedProducts = allProducts.stream()
                .filter(p -> matchesScore(p.getName(), p.getSanskritName(), p.getShortDescription(), p.getSlug(), searchTokens) > 0)
                .sorted((a, b) -> Integer.compare(
                        matchesScore(b.getName(), b.getSanskritName(), b.getShortDescription(), b.getSlug(), searchTokens),
                        matchesScore(a.getName(), a.getSanskritName(), a.getShortDescription(), a.getSlug(), searchTokens)
                ))
                .limit(3)
                .collect(Collectors.toList());

        // 3. Match Recipes
        List<AyurvedicRecipe> allRecipes = recipeRepository.findAll();
        List<AyurvedicRecipe> matchedRecipes = allRecipes.stream()
                .filter(r -> matchesScore(r.getTitle(), r.getSanskritName(), r.getDescription(), r.getSlug(), searchTokens) > 0)
                .sorted((a, b) -> Integer.compare(
                        matchesScore(b.getTitle(), b.getSanskritName(), b.getDescription(), b.getSlug(), searchTokens),
                        matchesScore(a.getTitle(), a.getSanskritName(), a.getDescription(), a.getSlug(), searchTokens)
                ))
                .limit(2)
                .collect(Collectors.toList());

        // 4. Match Doctors
        List<DoctorProfile> allDoctors = doctorProfileRepository.findAll();
        List<DoctorProfile> matchedDoctors = new ArrayList<>();
        boolean wantsDoctor = query != null && (
                query.toLowerCase().contains("doctor") || query.toLowerCase().contains("consult") ||
                query.toLowerCase().contains("vaidya") || query.toLowerCase().contains("appointment") ||
                query.toLowerCase().contains("pulse") || query.toLowerCase().contains("nadi")
        );
        if (wantsDoctor) {
            matchedDoctors = allDoctors.stream().limit(3).collect(Collectors.toList());
        }

        // Build concise context text for LLM prompt
        StringBuilder ctx = new StringBuilder();
        if (!matchedHerbs.isEmpty()) {
            ctx.append("Relevant Herbs in Catalog: ");
            ctx.append(matchedHerbs.stream().map(h -> h.getCommonName() + " (" + h.getSanskritName() + " - " + h.getPrimaryDoshaEffect() + ")").collect(Collectors.joining(", ")));
            ctx.append("\n");
        }
        if (!matchedProducts.isEmpty()) {
            ctx.append("Relevant Store Formulations: ");
            ctx.append(matchedProducts.stream().map(p -> p.getName() + " ($" + p.getPrice() + ")").collect(Collectors.joining(", ")));
            ctx.append("\n");
        }
        if (!matchedRecipes.isEmpty()) {
            ctx.append("Relevant Kitchen Recipes: ");
            ctx.append(matchedRecipes.stream().map(r -> r.getTitle() + " (" + r.getDoshaSuitability() + ")").collect(Collectors.joining(", ")));
            ctx.append("\n");
        }
        if (!matchedDoctors.isEmpty()) {
            ctx.append("Certified Vaidyas available for live consultation: ");
            ctx.append(matchedDoctors.stream().map(d -> (d.getUser() != null ? d.getUser().getFullName() : "Vaidya") + " - " + d.getPrimarySpecialization()).collect(Collectors.joining(", ")));
            ctx.append("\n");
        }

        return RetrievalResult.builder()
                .contextSummary(ctx.toString())
                .matchedHerbs(matchedHerbs)
                .matchedProducts(matchedProducts)
                .matchedRecipes(matchedRecipes)
                .matchedDoctors(matchedDoctors)
                .build();
    }

    private int matchesScore(String field1, String field2, String field3, String field4, Set<String> tokens) {
        int score = 0;
        String combined = (
                (field1 != null ? field1 : "") + " " +
                (field2 != null ? field2 : "") + " " +
                (field3 != null ? field3 : "") + " " +
                (field4 != null ? field4 : "")
        ).toLowerCase();

        for (String token : tokens) {
            if (combined.contains(token)) {
                score += 2;
            }
        }
        return score;
    }
}
