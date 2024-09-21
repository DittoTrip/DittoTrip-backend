package site.dittotrip.ditto_trip.spot.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.spot.domain.SpotApply;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class SpotSaveReq {

    private String name;
    private String address;
    private Double pointX;
    private Double pointY;

    private List<Long> categoryIds = new ArrayList<>();
    private List<String> hashtagNames = new ArrayList<>();

     public Spot toEntity() {
         return new Spot(name, address, pointX, pointY, null);
     }

}
