package site.dittotrip.ditto_trip.ditto.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.ArrayList;
import java.util.List;

@Data
public class DittoSaveReq {

    private String title;
    private String body;
    private List<String> hashtagNames = new ArrayList<>();

    public Ditto toEntity(User user) {
        return new Ditto(title, body, user);
    }

}
