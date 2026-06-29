package com.linguanova.idiomago.external.words;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;
import org.json.JSONArray;

import static com.linguanova.idiomago.util.AppRoutes.OPENAI_BASE_URL;

@Component
public class OpenAIService {

    private static String apiKey;

    public OpenAIService(@Value("${openai.api.key}") String key) {
        OpenAIService.apiKey = key;
    }

    private static final String MODEL = "gpt-4.1-nano";

    public static IAResponse getWordExplanation(String word, String language) {
        RestTemplate restTemplate = new RestTemplate();

        String prompt = String.format("""
        Given the following:
        -Word: "%s"
        -Language: "%s"
        Returns only a JSON with the following fields:
        {
          "translation": "...",
          "example": "...",
          "description": "..."
        }
        Be brief, clear, and focused on learning that word.
        """, word, language);

        try {
            JSONObject message = new JSONObject()
                    .put("role", "user")
                    .put("content", prompt);

            JSONArray messages = new JSONArray().put(message);

            JSONObject requestBody = new JSONObject()
                    .put("model", MODEL)
                    .put("messages", messages)
                    .put("temperature", 0.7);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
            ResponseEntity<String> response = restTemplate.exchange(OPENAI_BASE_URL, HttpMethod.POST, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                JSONObject json = new JSONObject(response.getBody());
                String content = json
                        .getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content");

                JSONObject parsed = new JSONObject(content);
                return new IAResponse(
                        parsed.optString("translation"),
                        parsed.optString("example"),
                        parsed.optString("description")
                );
            }

        } catch (Exception e) {
            System.out.println("❌ Error calling OpenAI: " + e.getMessage());
        }

        return null;
    }
}
