package site.dittotrip.ditto_trip.alarm.domain.dto;

import lombok.Data;
import site.dittotrip.ditto_trip.alarm.domain.Alarm;

import java.util.ArrayList;
import java.util.List;

@Data
public class AlarmListRes {

    private List<AlarmData> alarmDataList = new ArrayList<>();
    private Integer totalPage;

    public static AlarmListRes fromEntities(List<Alarm> alarms, Integer page) {
        AlarmListRes alarmListRes = new AlarmListRes();
        alarmListRes.setTotalPage(page);
        for (Alarm alarm : alarms) {
            alarmListRes.getAlarmDataList().add(AlarmData.fromEntity(alarm));
        }
        return alarmListRes;
    }

}
