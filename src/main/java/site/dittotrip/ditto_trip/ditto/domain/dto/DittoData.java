package site.dittotrip.ditto_trip.ditto.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;
import site.dittotrip.ditto_trip.hashtag.domain.HashtagDitto;
import site.dittotrip.ditto_trip.user.domain.dto.UserData;
import site.dittotrip.ditto_trip.utils.Language;
import site.dittotrip.ditto_trip.utils.TranslationService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class DittoData {

    private Long dittoId;
    private String title;
    private String body;
    private String imagePath;
    private LocalDateTime createdDateTime;

    private UserData userData;
    @Builder.Default
    private List<String> hashtags = new ArrayList<>();

    private Boolean isMine;
    private Long myBookmarkId;

    public static DittoData fromEntity(Ditto ditto, Boolean isMine, Long myBookmarkId) {
        DittoData dittoData = DittoData.builder()
                .dittoId(ditto.getId())
                .title(TranslationService.getLanguage() == Language.EN ? ditto.getTitleEN() : ditto.getTitle())
                .body(TranslationService.getLanguage() == Language.EN ? ditto.getBodyEN() : ditto.getBody())
                .imagePath(ditto.getImagePath())
                .createdDateTime(ditto.getCreatedDateTime())
                .userData(UserData.fromEntity(ditto.getUser()))
                .isMine(isMine)
                .myBookmarkId(myBookmarkId)
                .build();

        dittoData.putHashtags(ditto);
        return dittoData;
    }

    private void putHashtags(Ditto ditto) {
        for (HashtagDitto hashtagDitto : ditto.getHashtagDittos()) {
            this.hashtags.add(hashtagDitto.getHashtag().getName());
        }
    }

}
