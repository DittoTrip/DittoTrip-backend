package site.dittotrip.ditto_trip.ditto.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class DittoModifyReq {

    private String title;
    private String body;
    private List<Long> removedImageIds = new ArrayList<>();

    public void modifyEntity(Ditto ditto) {
        ditto.setTitle(title);
        ditto.setBody(body);
    }

}
