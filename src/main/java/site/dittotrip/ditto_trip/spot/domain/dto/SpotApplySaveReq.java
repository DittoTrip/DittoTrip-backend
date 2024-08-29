package site.dittotrip.ditto_trip.spot.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.spot.domain.SpotApply;
import site.dittotrip.ditto_trip.user.domain.User;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class SpotApplySaveReq {

    private String name;
    private String intro;
    private String address;
    private LocalTime startTime;
    private LocalTime endTime;
    private String phoneNumber;
    private String homeUri;
    private Double pointX;
    private Double pointY;

    private List<Long> categoryIds = new ArrayList<>();
    // 해시태그 리스트

     public SpotApply toEntity(User user, List<Category> categories) {
         return new SpotApply(name, intro, address, startTime, endTime, phoneNumber, homeUri, pointX, pointY, user, categories);
     }

}
