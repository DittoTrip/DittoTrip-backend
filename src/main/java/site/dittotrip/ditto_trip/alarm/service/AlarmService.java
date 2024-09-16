package site.dittotrip.ditto_trip.alarm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.alarm.domain.Alarm;
import site.dittotrip.ditto_trip.alarm.domain.dto.AlarmListRes;
import site.dittotrip.ditto_trip.alarm.repository.AlarmRepository;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AlarmService {

    private final UserRepository userRepository;
    private final AlarmRepository alarmRepository;

    @Transactional(readOnly = false)
    public AlarmListRes findAlarmList(Long reqUserId, Pageable pageable) {
        User reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);

        Page<Alarm> page = alarmRepository.findByUser(reqUser, pageable);
        List<Alarm> alarms = page.getContent();

        AlarmListRes alarmListRes = AlarmListRes.fromEntities(alarms, page.getTotalPages());

        for (Alarm alarm : alarms) {
            alarm.setIsChecked(Boolean.TRUE);
        }

        return alarmListRes;
    }

}
