package site.dittotrip.ditto_trip.mainpage.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;
import site.dittotrip.ditto_trip.utils.Language;
import site.dittotrip.ditto_trip.utils.TranslationService;

@Data
@Builder
public class DittoMainPageData {

    private Long dittoId;
    private String title;
    private String body;
    private String imagePath;

    public static DittoMainPageData fromEntity(Ditto ditto) {
        return DittoMainPageData.builder()
                .dittoId(ditto.getId())
                .title(TranslationService.getLanguage() == Language.EN ? ditto.getTitleEN() : ditto.getTitle())
                .body(TranslationService.getLanguage() == Language.EN ? ditto.getBodyEN() : ditto.getBody())
                .imagePath(ditto.getImagePath())
                .build();
    }

}
