package site.dittotrip.ditto_trip.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.dittotrip.ditto_trip.review.domain.ReviewImage;

public interface ReviewImageRepository extends JpaRepository<Long, ReviewImage> {
}
