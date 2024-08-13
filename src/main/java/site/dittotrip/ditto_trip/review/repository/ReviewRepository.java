package site.dittotrip.ditto_trip.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    /** 리스트 조회 정렬
     * 등록순
     * 최신순
     * 인기순 (ReviewLikes)
     * 별점 높은 순
     * 별점 낮은 순
     */
    List<Review> findTop3BySpotOrderByCreatedDateTimeDesc(Spot spot);

    List<Review> findBySpotOrderByCreatedDateTimeDesc(Spot spot);

    Long countBySpot(Spot spot);

    Optional<Review> findByUser(User user);

}
