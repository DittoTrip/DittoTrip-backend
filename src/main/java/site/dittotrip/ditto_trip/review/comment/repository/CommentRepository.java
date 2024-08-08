package site.dittotrip.ditto_trip.review.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.dittotrip.ditto_trip.review.comment.domain.Comment;
import site.dittotrip.ditto_trip.review.domain.Review;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByReviewAndParentCommentIsNullOrderByCreatedDateTimeAsc(Review review);

}
