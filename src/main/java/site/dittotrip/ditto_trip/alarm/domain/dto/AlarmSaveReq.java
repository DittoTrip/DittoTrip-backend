package site.dittotrip.ditto_trip.alarm.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.dittotrip.ditto_trip.alarm.domain.Alarm;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class AlarmSaveReq {

    private String title;
    private String body;
    private String path;

    public List<Alarm> toEntities(List<User> targets) {
        return Alarm.createAlarms(title, body, path, targets);
    }

}
