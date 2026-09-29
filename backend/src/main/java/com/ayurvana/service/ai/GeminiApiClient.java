package com.ayurvana.service.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.*;

@Component
@Slf4j
public class GeminiApiClient {

    @Value("${app.ai.gemini.api-key:}")
    private String apiKey;

    @Value("${app.ai.gemini.model:gemini-2.5-flash}")
    private String model;

    @Value("${app.ai.gemini.enabled:true}")
    private boolean enabled;

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public GeminiApiClient(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    public boolean isConfigured() {
        return enabled && apiKey != null && !apiKey.trim().isEmpty();
    }

    public Optional<GeminiResponsePayload> generateAyurvedicResponse(
            String userQuery,
            String userDosha,
            List<Map<String, String>> chatHistory,
            String ragKnowledgeContext) {

        if (!isConfigured()) {
            return Optional.empty();
        }

        try {
            String systemInstruction = """
                You are the AyurVana Master Vaidya (वरिष्ठ आयुर्वेदिक चिकित्सक), an enlightened, compassionate, and deeply knowledgeable classical Ayurvedic Doctor.
                You are grounded in classical treatises: Charaka Samhita, Sushruta Samhita, Ashtanga Hridaya, Sharangadhara Samhita, and Bhavaprakasha.
                
                YOUR RESPONSIBILITIES:
                1. Answer EACH and EVERY user question thoroughly with authentic Ayurvedic wisdom, constitutional dosha analysis (Vata, Pitta, Kapha), Samprapti (pathogenesis), Ahara (dietary rules / Pathya-Apathya), Vihara (lifestyle / Yoga / Pranayama), and Aushadha (herbal formulations & Anupana).
                2. Explain the root physiological cause from an Ayurvedic perspective (Agni, Ama, Dosha, Dhatu, Mala, Srotas).
                3. Ground your answer using the provided Database Catalog Context (herbs, recipes, products, doctors) when relevant.
                4. Provide an authentic, relevant classical Sanskrit Shloka with accurate English translation and classical treatise reference.
                5. Provide 3 to 4 helpful follow-up quick reply suggestions.
                
                OUTPUT FORMAT:
                You MUST return ONLY a valid, parseable JSON object with NO markdown backticks around it or standard JSON format with the following exact keys:
                {
                  "reply": "Detailed markdown formatted response with clear headings, bullet points, and clinical depth",
                  "sanskritShloka": "Sanskrit verse in Devanagari script",
                  "shlokaMeaning": "English translation and clinical significance of the Shloka",
                  "classicalTreatiseReference": "e.g. Charaka Samhita, Sutra Sthana 1/41 or Ashtanga Hridaya, Sutra Sthana 1/2",
                  "quickReplies": ["Follow-up question 1", "Follow-up question 2", "Follow-up question 3"],
                  "recommendedHerbKeywords": ["ashwagandha", "triphala", "brahmi"],
                  "recommendedProductKeywords": ["sleep", "chyawanprash", "kumkumadi"],
                  "recommendedRecipeKeywords": ["moon milk", "kitchari", "tea"]
                }
                """;

            StringBuilder promptBuilder = new StringBuilder();
            if (userDosha != null && !userDosha.isBlank()) {
                promptBuilder.append("User's Primary Dosha Constitution: ").append(userDosha).append("\n\n");
            }
            if (ragKnowledgeContext != null && !ragKnowledgeContext.isBlank()) {
                promptBuilder.append("Database Grounding Context (Available catalog in AyurVana):\n")
                        .append(ragKnowledgeContext).append("\n\n");
            }

            if (chatHistory != null && !chatHistory.isEmpty()) {
                promptBuilder.append("Previous Conversation:\n");
                for (Map<String, String> item : chatHistory) {
                    promptBuilder.append(item.getOrDefault("role", "user")).append(": ")
                            .append(item.getOrDefault("content", "")).append("\n");
                }
                promptBuilder.append("\n");
            }

            promptBuilder.append("User's Current Question: ").append(userQuery).append("\n\n");
            promptBuilder.append("Please provide the complete structured Ayurvedic guidance in JSON format.");

            Map<String, Object> requestBody = new HashMap<>();
            
            // Contents
            List<Map<String, Object>> contents = new ArrayList<>();
            Map<String, Object> contentMap = new HashMap<>();
            contentMap.put("role", "user");
            
            List<Map<String, String>> parts = new ArrayList<>();
            Map<String, String> partMap = new HashMap<>();
            partMap.put("text", promptBuilder.toString());
            parts.add(partMap);
            contentMap.put("parts", parts);
            contents.add(contentMap);
            requestBody.put("contents", contents);

            // System Instruction
            Map<String, Object> sysInstructionMap = new HashMap<>();
            List<Map<String, String>> sysParts = new ArrayList<>();
            Map<String, String> sysPart = new HashMap<>();
            sysPart.put("text", systemInstruction);
            sysParts.add(sysPart);
            sysInstructionMap.put("parts", sysParts);
            requestBody.put("system_instruction", sysInstructionMap);

            // Generation Config
            Map<String, Object> genConfig = new HashMap<>();
            genConfig.put("temperature", 0.3);
            genConfig.put("response_mime_type", "application/json");
            requestBody.put("generationConfig", genConfig);

            String requestJson = objectMapper.writeValueAsString(requestBody);

            String endpoint = String.format(
                    "https://generativelanguage.googleapis.com/v1beta/models/%s:generateContent?key=%s",
                    model, apiKey.trim()
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint))
                    .header("Content-Type", "application/json")
                    .timeout(Duration.ofSeconds(15))
                    .POST(HttpRequest.BodyPublishers.ofString(requestJson))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonNode root = objectMapper.readTree(response.body());
                JsonNode candidates = root.path("candidates");
                if (candidates.isArray() && !candidates.isEmpty()) {
                    JsonNode textNode = candidates.get(0).path("content").path("parts").get(0).path("text");
                    String rawText = textNode.asText();

                    // Clean any markdown wrapper if present
                    String cleanJson = rawText.trim();
                    if (cleanJson.startsWith("```json")) {
                        cleanJson = cleanJson.substring(7);
                    }
                    if (cleanJson.startsWith("```")) {
                        cleanJson = cleanJson.substring(3);
                    }
                    if (cleanJson.endsWith("```")) {
                        cleanJson = cleanJson.substring(0, cleanJson.length() - 3);
                    }
                    cleanJson = cleanJson.trim();

                    GeminiResponsePayload payload = objectMapper.readValue(cleanJson, GeminiResponsePayload.class);
                    return Optional.ofNullable(payload);
                }
            } else {
                log.warn("Gemini API returned status code {}: {}", response.statusCode(), response.body());
            }

        } catch (Exception e) {
            log.error("Error communicating with Gemini API: {}", e.getMessage());
        }

        return Optional.empty();
    }

    public static class GeminiResponsePayload {
        public String reply;
        public String sanskritShloka;
        public String shlokaMeaning;
        public String classicalTreatiseReference;
        public List<String> quickReplies;
        public List<String> recommendedHerbKeywords;
        public List<String> recommendedProductKeywords;
        public List<String> recommendedRecipeKeywords;
    }
}
