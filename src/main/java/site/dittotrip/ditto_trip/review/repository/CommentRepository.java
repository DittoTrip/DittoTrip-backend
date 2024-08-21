package site.dittotrip.ditto_trip.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.dittotrip.ditto_trip.review.domain.ReviewComment;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<ReviewComment, Long> {

    Optional<ReviewComment> findByUser(User user);

//    List<ReviewComment> findByReviewAndParentCommentIsNullOrderByCreatedDateTimeAsc(Review review);

    Long countByReview(Review review);

}
