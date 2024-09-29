package site.dittotrip.ditto_trip.ditto.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.domain.dto.UserData;
import site.dittotrip.ditto_trip.utils.Language;
import site.dittotrip.ditto_trip.utils.TranslationService;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class DittoMiniData {

    private Long dittoId;
    private String title;
    private String imagePath;
    private UserData userData;

    public static DittoMiniData fromEntity(Ditto ditto) {
        return DittoMiniData.builder()
                .dittoId(ditto.getId())
                .title(TranslationService.getLanguage() == Language.EN ? ditto.getTitleEN() : ditto.getTitle())
                .imagePath(ditto.getImagePath())
                .userData(UserData.fromEntity(ditto.getUser()))
                .build();

    }


    public static List<DittoMiniData> listFromEntities(List<Ditto> dittos) {
        List<DittoMiniData> dittoMiniDataList = new ArrayList<>();
        for (Ditto ditto : dittos) {
            dittoMiniDataList.add(fromEntity(ditto));
        }
        return dittoMiniDataList;
    }

}
