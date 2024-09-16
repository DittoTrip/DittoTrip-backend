package site.dittotrip.ditto_trip.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.category.domain.enums.CategoryMajorType;
import site.dittotrip.ditto_trip.category.domain.enums.CategorySubType;
import site.dittotrip.ditto_trip.category.repository.CategoryRepository;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;
import site.dittotrip.ditto_trip.ditto.repository.DittoRepository;
import site.dittotrip.ditto_trip.hashtag.domain.Hashtag;
import site.dittotrip.ditto_trip.hashtag.domain.HashtagSpot;
import site.dittotrip.ditto_trip.hashtag.repository.HashtagRepository;
import site.dittotrip.ditto_trip.profile.domain.UserProfile;
import site.dittotrip.ditto_trip.profile.repository.UserProfileRepository;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.review.repository.ReviewRepository;
import site.dittotrip.ditto_trip.reward.domain.*;
import site.dittotrip.ditto_trip.reward.domain.enums.ItemType;
import site.dittotrip.ditto_trip.reward.domain.enums.RewardType;
import site.dittotrip.ditto_trip.reward.repository.*;
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
    private final HashtagRepository hashtagRepository;
    private final CategorySpotRepository categorySpotRepository;
    private final SpotVisitRepository spotVisitRepository;
    private final ReviewRepository reviewRepository;
    private final DittoRepository dittoRepository;
    private final RewardRepository rewardRepository;
    private final ItemRepository itemRepository;
    private final BadgeRepository badgeRepository;
    private final UserRewardRepository userRewardRepository;
    private final UserItemRepository userItemRepository;
    private final UserBadgeRepository userBadgeRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void testDataInit() {
        log.info("===== TEST DATA INIT START =====");

        User user1 = createUser("순재", "won9619v@naver.com", "1234", "haus");
        User user2 = createUser("인주", "huhhuh@naver.com", "12345", "YH486");
        User user3 = createUser("윤하", "yunha@naver.com", "123456", "realYH");

        Category category101 = createCategory("이상한 변호사 우영우", CategoryMajorType.CONTENT, CategorySubType.CONTENT_DRAMA);
        Category category102 = createCategory("동백꽃 필 무렵", CategoryMajorType.CONTENT, CategorySubType.CONTENT_DRAMA);
        Category category103 = createCategory("눈물의 여왕", CategoryMajorType.CONTENT, CategorySubType.CONTENT_DRAMA);
        Category category104 = createCategory("카지노", CategoryMajorType.CONTENT, CategorySubType.CONTENT_DRAMA);
        Category category105 = createCategory("응답하라 1988", CategoryMajorType.CONTENT, CategorySubType.CONTENT_DRAMA);
        Category category106 = createCategory("마스크걸", CategoryMajorType.CONTENT, CategorySubType.CONTENT_DRAMA);
        Category category107 = createCategory("나의 아저씨", CategoryMajorType.CONTENT, CategorySubType.CONTENT_DRAMA);

        Category category201 = createCategory("서울의 봄", CategoryMajorType.CONTENT, CategorySubType.CONTENT_MOVIE);
        Category category202 = createCategory("기생충", CategoryMajorType.CONTENT, CategorySubType.CONTENT_MOVIE);
        Category category203 = createCategory("올드보이", CategoryMajorType.CONTENT, CategorySubType.CONTENT_MOVIE);
        Category category301 = createCategory("고딩엄빠", CategoryMajorType.CONTENT, CategorySubType.CONTENT_ENTERTAINMENT);
        Category category302 = createCategory("라디오 스타", CategoryMajorType.CONTENT, CategorySubType.CONTENT_ENTERTAINMENT);
        Category category303 = createCategory("하트 시그널", CategoryMajorType.CONTENT, CategorySubType.CONTENT_ENTERTAINMENT);

        Category category401 = createCategory("김수현", CategoryMajorType.PERSON, CategorySubType.PERSON_ACTOR);
        Category category402 = createCategory("김지원", CategoryMajorType.PERSON, CategorySubType.PERSON_ACTOR);
        Category category403 = createCategory("공유", CategoryMajorType.PERSON, CategorySubType.PERSON_ACTOR);
        Category category501 = createCategory("윤지영", CategoryMajorType.PERSON, CategorySubType.PERSON_SINGER);
        Category category502 = createCategory("윤하", CategoryMajorType.PERSON, CategorySubType.PERSON_SINGER);
        Category category503 = createCategory("최유리", CategoryMajorType.PERSON, CategorySubType.PERSON_SINGER);

        Spot spot1 = createSpot("소소주점", "강원도 강릉시 주문진읍", 127.0, 36.9, null, List.of(category102, category101));
        Spot spot2 = createSpot("소덕동 팽나무", "경상남도 창원시 의창구 대산면, 대산북로 899번길 43-5", 126.9, 36.7, null, List.of(category101, category102));
        Spot spot3 = createSpot("순재네 집", "서울 광진구", 126.8, 36.6, null, List.of(category103, category105));
        Spot spot4 = createSpot("인주네 집", "교대역쪽", 126.7, 36.5, null, List.of(category104));
        Spot spot5 = createSpot("spot5", "spot5 주소", 128.0, 36.0, null, List.of(category101));
        Spot spot6 = createSpot("spot6", "spot6 주소", 129.0, 37.0, null, List.of(category101));
        Spot spot7 = createSpot("spot7", "spot7 주소", 130.0, 38.0, null, List.of(category101));
        Spot spot8 = createSpot("spot8", "spot8 주소", 131.0, 39.0, null, List.of(category101));
        Spot spot9 = createSpot("spot9", "spot9 주소", 132.0, 40.0, null, List.of(category101));
        Spot spot10 = createSpot("spot10", "spot10 주소", 133.0, 41.0, null, List.of(category101));
        Spot spot11 = createSpot("spot11", "spot11 주소", 134.0, 42.0, null, List.of(category101));

        Hashtag hashtag1 = createHashtag("우영우");
        Hashtag hashtag2 = createHashtag("동백꽃");

        HashtagSpot hashtagSpot1 = new HashtagSpot(hashtag1, spot1);
        HashtagSpot hashtagSpot2 = new HashtagSpot(hashtag2, spot1);
        spot1.getHashtagSpots().add(hashtagSpot1);
        spot1.getHashtagSpots().add(hashtagSpot2);

        SpotVisit spotVisit1 = createSpotVisit(spot1, user1);
        SpotVisit spotVisit2 = createSpotVisit(spot2, user2);
        SpotVisit spotVisit3 = createSpotVisit(spot1, user2);
        SpotVisit spotVisit4 = createSpotVisit(spot1, user3);
        SpotVisit spotVisit5 = createSpotVisit(spot1, user1);
        SpotVisit spotVisit6 = createSpotVisit(spot1, user2);
        SpotVisit spotVisit7 = createSpotVisit(spot1, user3);
        SpotVisit spotVisit8 = createSpotVisit(spot1, user1);

        Review review1 = createReview("좋았슴", 4f, user2, spotVisit1);
        Review review2 = createReview("좋았습니다.", 3f, user2, spotVisit2);
        Review review3 = createReview("review_body", 4f, user2, spotVisit3);
        Review review4 = createReview("review_body", 4f, user3, spotVisit4);
        Review review5 = createReview("review_body", 4f, user1, spotVisit5);
        Review review6 = createReview("review_body", 4f, user2, spotVisit6);
        Review review7 = createReview("review_body", 4f, user3, spotVisit7);
        Review review8 = createReview("review_body", 4f, user1, spotVisit8);

        Ditto ditto1 = createDitto("제목1", "여기 좋아요1", user1);
        Ditto ditto2 = createDitto("제목2", "여기 좋아요2", user1);
        Ditto ditto3 = createDitto("제목3", "여기 좋아요3", user2);
        Ditto ditto4 = createDitto("제목4", "여기 좋아요4", user2);

        Item item11 = createItem("skin1", "empty imagePath", ItemType.SKIN);
        Item item12 = createItem("skin2", "empty imagePath", ItemType.SKIN);
        Item item13 = createItem("skin3", "empty imagePath", ItemType.SKIN);
        Item item21 = createItem("eyes1", "empty imagePath", ItemType.EYES);
        Item item22 = createItem("eyes2", "empty imagePath", ItemType.EYES);
        Item item23 = createItem("eyes3", "empty imagePath", ItemType.EYES);
        Item item31 = createItem("mouse1", "empty imagePath", ItemType.MOUSE);
        Item item32 = createItem("mouse2", "empty imagePath", ItemType.MOUSE);
        Item item33 = createItem("mouse3", "empty imagePath", ItemType.MOUSE);
        Item item41 = createItem("hair1", "empty imagePath", ItemType.HAIR);
        Item item42 = createItem("hair2", "empty imagePath", ItemType.HAIR);
        Item item43 = createItem("hair3", "empty imagePath", ItemType.HAIR);
        Item item51 = createItem("accessory1", "empty imagePath", ItemType.ACCESSORY);
        Item item52 = createItem("accessory2", "empty imagePath", ItemType.ACCESSORY);
        Item item53 = createItem("accessory3", "empty imagePath", ItemType.ACCESSORY);

        Badge badge1 = createBadge("badge1", "empty imagePath", "body1", "condition1");
        Badge badge2 = createBadge("badge2", "empty imagePath", "body2", "condition2");
        Badge badge3 = createBadge("badge3", "empty imagePath", "body3", "condition3");
        Badge badge4 = createBadge("badge4", "empty imagePath", "body4", "condition4");
        Badge badge5 = createBadge("badge5", "empty imagePath", "body4", "condition4");
        Badge badge6 = createBadge("badge6", "empty imagePath", "body4", "condition4");

        UserItem userItem1 = createUserItem(user1, item11);
        UserItem userItem2 = createUserItem(user1, item12);
        UserItem userItem3 = createUserItem(user1, item21);
        UserItem userItem4 = createUserItem(user1, item22);
        UserItem userItem5 = createUserItem(user1, item31);
        UserItem userItem6 = createUserItem(user1, item32);
        UserItem userItem7 = createUserItem(user1, item41);
        UserItem userItem8 = createUserItem(user1, item42);
        UserItem userItem9 = createUserItem(user1, item51);
        UserItem userItem10 = createUserItem(user1, item52);
        UserBadge userBadge1 = createUserBadge(user1, badge1);
        UserBadge userBadge2 = createUserBadge(user1, badge2);
        UserBadge userBadge3 = createUserBadge(user1, badge3);

        UserProfile userProfile1 = user1.getUserProfile();
        userProfile1.setUserItemSkin(userItem1);
        userProfile1.setUserItemEyes(userItem3);
        userProfile1.setUserItemMouse(userItem5);
        userProfile1.setUserItemHair(userItem7);
        userProfile1.setUserItemAccessory(userItem9);
        userProfile1.setUserBadge(userBadge1);

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

    private Hashtag createHashtag(String name) {
        Hashtag hashtag = new Hashtag(name);
        hashtagRepository.save(hashtag);
        return hashtag;
    }

    private Category createCategory(String name, CategoryMajorType majorType, CategorySubType subType) {
        Category category = new Category(name, majorType, subType);
        categoryRepository.save(category);
        return category;
    }

    private Spot createSpot(String spotName, String address, Double pointX, Double pointY, String imagePath, List<Category> categories) {
        Spot spot = new Spot(spotName, address, pointX, pointY, imagePath);
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
        Review review = new Review(body, rating, user, spotVisit);
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

//    private Reward createReward(String name, String imagePath, RewardType rewardType) {
//        Reward reward = new Reward(name, imagePath, rewardType);
//        rewardRepository.save(reward);
//        return reward;
//    }

    private Item createItem(String name, String imagePath, ItemType itemType) {
        Item item = new Item(name, imagePath, itemType);
        itemRepository.save(item);
        return item;
    }

    private Badge createBadge(String name, String imagePath, String body, String conditionBody) {
        Badge badge = new Badge(name, imagePath, body, conditionBody);
        badgeRepository.save(badge);
        return badge;
    }

    private UserItem createUserItem(User user, Item item) {
        UserItem userItem = new UserItem(user, item);
        userItemRepository.save(userItem);
        return userItem;
    }

    private UserBadge createUserBadge(User user, Badge badge) {
        UserBadge userBadge = new UserBadge(user, badge);
        userBadgeRepository.save(userBadge);
        return userBadge;
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
