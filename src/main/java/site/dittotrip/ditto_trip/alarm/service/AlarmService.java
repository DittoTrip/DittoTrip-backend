package site.dittotrip.ditto_trip.alarm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.alarm.domain.Alarm;
import site.dittotrip.ditto_trip.alarm.domain.dto.AlarmListRes;
import site.dittotrip.ditto_trip.alarm.repository.AlarmRepository;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;

    @Transactional(readOnly = false)
    public AlarmListRes findAlarmList(User user, Pageable pageable) {
        Page<Alarm> page = alarmRepository.findByUser(user, pageable);
        List<Alarm> alarms = page.getContent();

        AlarmListRes alarmListRes = AlarmListRes.fromEntities(alarms, page.getTotalPages());

        for (Alarm alarm : alarms) {
            alarm.setIsChecked(Boolean.TRUE);
        }

        return alarmListRes;
    }

}
