package site.dittotrip.ditto_trip.ditto.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.domain.dto.UserData;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class DittoMiniData {

    private Long dittoId;
    private String title;
    private String imagePath;
    private UserData userData;

    public static DittoMiniData fromEntity(Ditto ditto, User user) {
        return DittoMiniData.builder()
                .dittoId(ditto.getId())
                .title(ditto.getTitle())
                .imagePath(ditto.getDittoImages().get(0).getImagePath())
                .userData(UserData.fromEntity(user))
                .build();
    }


    public static List<DittoMiniData> listFromEntities(List<Ditto> dittos, User user) {
        List<DittoMiniData> dittoMiniDataList = new ArrayList<>();
        for (Ditto ditto : dittos) {
            dittoMiniDataList.add(fromEntity(ditto, user));
        }
        return dittoMiniDataList;
    }
}
