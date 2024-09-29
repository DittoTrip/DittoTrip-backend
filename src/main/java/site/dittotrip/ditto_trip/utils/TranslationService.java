package site.dittotrip.ditto_trip.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
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

    public static Language getLanguage() {
        Language language = Language.KO;

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            if(request.getHeader("Accept-Language") != null) {
                language = request.getHeader("Accept-Language").equals("en") ? Language.EN : Language.KO;

            }
        }

        return language;
    }
}