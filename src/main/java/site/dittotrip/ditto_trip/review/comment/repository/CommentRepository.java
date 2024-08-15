package site.dittotrip.ditto_trip.review.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.dittotrip.ditto_trip.review.comment.domain.Comment;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByUser(User user);

    List<Comment> findByReviewAndParentCommentIsNullOrderByCreatedDateTimeAsc(Review review);

    Long countByReview(Review review);

}
