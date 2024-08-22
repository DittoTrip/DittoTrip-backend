package site.dittotrip.ditto_trip.ditto.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;
import site.dittotrip.ditto_trip.user.domain.User;

@Data
@NoArgsConstructor
public class DittoSaveReq {

    private String title;
    private String body;

    public Ditto toEntity(User user) {
        return new Ditto(title, body, user);
    }

}
