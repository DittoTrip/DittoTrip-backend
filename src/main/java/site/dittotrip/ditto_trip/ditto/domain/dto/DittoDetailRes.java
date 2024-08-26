package site.dittotrip.ditto_trip.ditto.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;

@Data
@Builder
public class DittoDetailRes {

    private DittoData dittoData;

    public static DittoDetailRes fromEntity(Ditto ditto, Boolean isMine, Long myBookmarkId) {
        return DittoDetailRes.builder()
                .dittoData(DittoData.fromEntity(ditto, isMine, myBookmarkId))
                .build();
    }

}
