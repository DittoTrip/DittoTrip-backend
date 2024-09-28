package site.dittotrip.ditto_trip.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class TranslationRes {
    private List<Translation> translations;

    @Getter
    @Setter
    public static class Translation {
        private String detected_source_language;
        private String text;
    }
}
