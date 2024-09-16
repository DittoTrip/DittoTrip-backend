package site.dittotrip.ditto_trip.alarm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import site.dittotrip.ditto_trip.alarm.domain.Alarm;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    Page<Alarm> findByUser(User user, Pageable pageable);

    List<Alarm> findByUserAndIsChecked(User user, Boolean isChecked);

}
