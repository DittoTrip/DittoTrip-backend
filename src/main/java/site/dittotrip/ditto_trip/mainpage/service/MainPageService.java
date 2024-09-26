package site.dittotrip.ditto_trip.mainpage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.alarm.domain.Alarm;
import site.dittotrip.ditto_trip.alarm.repository.AlarmRepository;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.category.domain.enums.CategoryMajorType;
import site.dittotrip.ditto_trip.category.domain.enums.CategorySubType;
import site.dittotrip.ditto_trip.category.repository.CategoryRepository;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;
import site.dittotrip.ditto_trip.ditto.repository.DittoRepository;
import site.dittotrip.ditto_trip.mainpage.domain.dto.MainPageRes;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.spot.repository.SpotRepository;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.repository.UserRepository;

import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MainPageService {

    private final UserRepository userRepository;
    private final DittoRepository dittoRepository;
    private final CategoryRepository categoryRepository;
    private final SpotRepository spotRepository;
    private final AlarmRepository alarmRepository;

    public MainPageRes findMainPage(Long reqUserId) {
        User reqUser = null;
        if (reqUserId != null) {
            reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        }

        // Ditto Data
        Ditto ditto = dittoRepository.findOneByRandom().orElseThrow(NoSuchElementException::new);

        // Category Data
        CategorySubType randomSubType = getRandomSubType();
        List<Category> categories = categoryRepository.findByRandom(randomSubType);

        // Spot Data (아직 처리 x)
        List<Spot> spots = new ArrayList<>();

        // 읽지 않은 알림 정보 조회
        Boolean isNotCheckedAlarm = Boolean.FALSE;
        if (reqUser != null) {
            List<Alarm> reqUsersAlarms = alarmRepository.findByUserAndIsChecked(reqUser, Boolean.FALSE);
            isNotCheckedAlarm = !reqUsersAlarms.isEmpty();
        }

        return MainPageRes.fromEntities(ditto, categories, spots, isNotCheckedAlarm);
    }

    private CategorySubType getRandomSubType() {
        CategorySubType[] values = CategorySubType.values();
        List<CategorySubType> subTypes = Arrays.asList(values);

        int randomIdx = new Random().nextInt(subTypes.size());
        return subTypes.get(randomIdx);
    }

}
