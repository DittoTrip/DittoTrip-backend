package site.dittotrip.ditto_trip.spot.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.dittotrip.ditto_trip.spot.domain.SpotApply;
import site.dittotrip.ditto_trip.user.domain.User;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class SpotApplySaveReq {

    private String name;
    private String address;
    private Double pointX;
    private Double pointY;

    private List<Long> categoryIds = new ArrayList<>();
    private List<String> hashtagNames = new ArrayList<>();

     public SpotApply toEntity(User user) {
         return new SpotApply(name, address, pointX, pointY, user);
     }

}
