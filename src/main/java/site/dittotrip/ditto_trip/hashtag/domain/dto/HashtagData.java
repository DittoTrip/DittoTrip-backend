package site.dittotrip.ditto_trip.hashtag.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.hashtag.domain.Hashtag;

@Data
@Builder
public class HashtagData {

    private String hashtagName;

    public static HashtagData fromEntity(Hashtag hashtag) {
        return HashtagData.builder()
                .hashtagName(hashtag.getHashtagName())
                .build();
    }

}
