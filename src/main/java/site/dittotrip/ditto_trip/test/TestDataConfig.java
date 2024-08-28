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
import site.dittotrip.ditto_trip.ditto.repository.DittoRepository;
import site.dittotrip.ditto_trip.profile.domain.UserProfile;
import site.dittotrip.ditto_trip.profile.repository.UserProfileRepository;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.review.repository.ReviewRepository;
import site.dittotrip.ditto_trip.spot.domain.CategorySpot;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.spot.domain.SpotVisit;
import site.dittotrip.ditto_trip.spot.repository.CategorySpotRepository;
import site.dittotrip.ditto_trip.spot.repository.SpotRepository;
import site.dittotrip.ditto_trip.spot.repository.SpotVisitRepository;
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

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final CategoryRepository categoryRepository;
    private final SpotRepository spotRepository;
    private final CategorySpotRepository categorySpotRepository;
    private final SpotVisitRepository spotVisitRepository;
    private final ReviewRepository reviewRepository;
    private final DittoRepository dittoRepository;

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
        Spot spot3 = createSpot("순재네 집", "순재가 사는 집", "서울 광진구", null, null,
                null, null, 126.8, 36.6, null, List.of(category13, category15));
        Spot spot4 = createSpot("인주네 집", "인주가 사는 집", "교대역쪽", null, null,
                null, null, 126.7, 36.5, null, List.of(category14));

        SpotVisit spotVisit1 = createSpotVisit(spot1, user1);
        SpotVisit spotVisit2 = createSpotVisit(spot2, user2);

        Review review1 = createReview("좋았슴", 4f, user2, spotVisit1);
        Review review2 = createReview("좋았습니다.", 3f, user2, spotVisit2);

        Ditto ditto1 = createDitto("제목1", "여기 좋아요1", user1);
        Ditto ditto2 = createDitto("제목2", "여기 좋아요2", user1);
        Ditto ditto3 = createDitto("제목3", "여기 좋아요3", user2);
        Ditto ditto4 = createDitto("제목4", "여기 좋아요4", user2);

        log.info("===== TEST DATA INIT END =====");
    }

    private User createUser(String name, String email, String password, String nickname) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setNickname(nickname);
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        user.getAuthorities().add(authority);
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

    private SpotVisit createSpotVisit(Spot spot, User user) {
        SpotVisit spotVisit = new SpotVisit(spot, user);
        spotVisitRepository.save(spotVisit);
        return spotVisit;
    }

    private Review createReview(String body, Float rating, User user, SpotVisit spotVisit) {
        Review review = new Review(body, rating, null, user, spotVisit);
        reviewRepository.save(review);
        Spot spot = spotVisit.getSpot();
        modifySpotRating(spot, 3.0f, null);
        return review;
    }

    private Ditto createDitto(String title, String body, User user) {
        Ditto ditto = new Ditto(title, body, user);
        dittoRepository.save(ditto);
        return ditto;
    }


    private void modifySpotRating(Spot spot, Float add, Float sub) {
        int reviewCount = spot.getReviewCount();
        float ratingSum = spot.getRating() * reviewCount;

        if (add != null) {
            reviewCount++;
            ratingSum += add;
        }

        if (sub != null) {
            reviewCount--;
            ratingSum -= sub;
        }

        Float newRating = ratingSum / reviewCount;

        spot.setReviewCount(reviewCount);
        spot.setRating(newRating);
    }

}
