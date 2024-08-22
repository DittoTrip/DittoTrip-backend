package site.dittotrip.ditto_trip.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import site.dittotrip.ditto_trip.review.domain.ReviewComment;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface ReviewCommentRepository extends JpaRepository<ReviewComment, Long> {

    Optional<ReviewComment> findByUser(User user);

    @Query("select c from ReviewComment c where c.review= :review and c.parentReviewComment= null")
    List<ReviewComment> findParentCommentsByReview(Review review);

//    List<ReviewComment> findByReviewAndParentCommentIsNullOrderByCreatedDateTimeAsc(Review review);

    Long countByReview(Review review);

}
