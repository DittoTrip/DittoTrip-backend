package site.dittotrip.ditto_trip.alarm.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.alarm.domain.Alarm;

import java.time.LocalDateTime;

@Data
@Builder
public class AlarmData {

    private Long alarmId;
    private String title;
    private String body;
    private String path;
    private Boolean isChecked;
    private LocalDateTime createdDateTime;

    public static AlarmData fromEntity(Alarm alarm) {
        return AlarmData.builder()
                .alarmId(alarm.getId())
                .title(alarm.getTitle())
                .body(alarm.getBody())
                .path(alarm.getPath())
                .isChecked(alarm.getIsChecked())
                .createdDateTime(alarm.getCreatedDateTime())
                .build();
    }

}
