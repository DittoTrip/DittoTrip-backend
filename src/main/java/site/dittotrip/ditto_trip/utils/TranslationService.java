package site.dittotrip.ditto_trip.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class TranslationService {
    @Value("${translation.apiKey}")
    private String apiKey;
    private final WebClient webClient;


    public TranslationService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api-free.deepl.com").build();
    }

    public TranslationRes translateText(String[] textList) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        for(String text : textList) {
            formData.add("text", text);
        }
        formData.add("target_lang", "EN");

        return webClient.post()
            .uri("/v2/translate")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", apiKey)
            .body(BodyInserters.fromFormData(formData))
            .retrieve()
            .bodyToMono(TranslationRes.class)
            .block();
    }
}