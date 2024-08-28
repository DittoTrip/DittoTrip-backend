package site.dittotrip.ditto_trip.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.category.domain.enums.CategoryMajorType;
import site.dittotrip.ditto_trip.category.domain.enums.CategorySubType;
import site.dittotrip.ditto_trip.category.repository.CategoryRepository;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;
import site.dittotrip.ditto_trip.profile.domain.UserProfile;
import site.dittotrip.ditto_trip.profile.repository.UserProfileRepository;
import site.dittotrip.ditto_trip.spot.domain.CategorySpot;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.spot.repository.CategorySpotRepository;
import site.dittotrip.ditto_trip.spot.repository.SpotRepository;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestDataConfig {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserProfileRepository userProfileRepository;
    private final CategoryRepository categoryRepository;
    private final SpotRepository spotRepository;
    private final CategorySpotRepository categorySpotRepository;


    @EventListener(ApplicationReadyEvent.class)
    public void testDataInit() {
        log.info("===== TEST DATA INIT START =====");
        User user1 = createUser("순재", "won9619v@naver.com", "1234", "haus");
        User user2 = createUser("인주", "huhhuh@naver.com", "12345", "YH486");
        User user3 = createUser("윤하", "yunha@naver.com", "123456", "realYH");

        Category category1 = createCategory("이상한 변호사 우영우", CategoryMajorType.CONTENT, CategorySubType.CONTENT_DRAMA);
        Category category2 = createCategory("동백꽃 필 무렵", CategoryMajorType.CONTENT, CategorySubType.CONTENT_DRAMA);
        Category category3 = createCategory("눈물의 여왕", CategoryMajorType.CONTENT, CategorySubType.CONTENT_DRAMA);
        Category category4 = createCategory("서울의 봄", CategoryMajorType.CONTENT, CategorySubType.CONTENT_MOVIE);
        Category category5 = createCategory("기생충", CategoryMajorType.CONTENT, CategorySubType.CONTENT_MOVIE);
        Category category6 = createCategory("올드보이", CategoryMajorType.CONTENT, CategorySubType.CONTENT_MOVIE);
        Category category7 = createCategory("고딩엄빠", CategoryMajorType.CONTENT, CategorySubType.CONTENT_ENTERTAINMENT);
        Category category8 = createCategory("라디오 스타", CategoryMajorType.CONTENT, CategorySubType.CONTENT_ENTERTAINMENT);
        Category category9 = createCategory("하트 시그널", CategoryMajorType.CONTENT, CategorySubType.CONTENT_ENTERTAINMENT);

        Category category10 = createCategory("김수현", CategoryMajorType.PERSON, CategorySubType.PERSON_ACTOR);
        Category category11 = createCategory("김지원", CategoryMajorType.PERSON, CategorySubType.PERSON_ACTOR);
        Category category12 = createCategory("공유", CategoryMajorType.PERSON, CategorySubType.PERSON_ACTOR);
        Category category13 = createCategory("윤지영", CategoryMajorType.PERSON, CategorySubType.PERSON_SINGER);
        Category category14 = createCategory("윤하", CategoryMajorType.PERSON, CategorySubType.PERSON_SINGER);
        Category category15 = createCategory("최유리", CategoryMajorType.PERSON, CategorySubType.PERSON_SINGER);

        Spot spot1 = createSpot("소소주점", "소소한 주점입니다", "강원도 강릉시 주문진읍", LocalTime.of(14, 0), LocalTime.of(23, 0),
                "031-121-2322", null, 127.0, 36.9, null, List.of(category2, category12));

        Spot spot2 = createSpot("소덕동 팽나무", "커다란 나무", "경상남도 창원시 의창구 대산면, 대산북로 899번길 43-5", null, null,
                null, null, 126.9, 36.7, null, List.of(category1, category12));


        log.info("===== TEST DATA INIT END =====");
    }

    private User createUser(String name, String email, String password, String nickname) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setNickname(nickname);
        userRepository.save(user);

        UserProfile userProfile = new UserProfile(user);
        user.setUserProfile(userProfile);
        userProfileRepository.save(userProfile);

        return user;
    }
    private Category createCategory(String name, CategoryMajorType majorType, CategorySubType subType) {
        Category category = new Category(name, majorType, subType, null);
        categoryRepository.save(category);
        return category;
    }

    private Spot createSpot(String spotName, String intro, String address, LocalTime startDateTime, LocalTime endDateTime,
                            String phoneNumber, String homeUri, Double pointX, Double pointY, String imagePath, List<Category> categories) {
        Spot spot = new Spot(spotName, intro, address, startDateTime, endDateTime, phoneNumber, homeUri, pointX, pointY, imagePath);
        spotRepository.save(spot);

        for (Category category : categories) {
            CategorySpot categorySpot = new CategorySpot(category, spot);
            categorySpotRepository.save(categorySpot);
        }

        return spot;
    }

    @EventListener(ContextClosedEvent.class)
    public void testDataRestore() {
        User user1 = userRepository.findByEmail("won9619v@naver.com").get();
        User user2 = userRepository.findByEmail("huhhuh@naver.com").get();
        User user3 = userRepository.findByEmail("yunha@naver.com").get();

        userRepository.delete(user1);
        userRepository.delete(user2);
        userRepository.delete(user3);
        
    }

}
