package site.dittotrip.ditto_trip.review.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("select r from Review r where r.spotVisit.spot= :spot order by r.createdDateTime desc limit 3")
    List<Review> findTop3BySpot(Spot spot);

    @Query("select r from Review r where r.spotVisit.spot= :spot order by r.createdDateTime desc")
    Page<Review> findBySpot(Spot spot, Pageable pageable);

    Optional<Review> findByUser(User user);

}
