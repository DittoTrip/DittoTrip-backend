package site.dittotrip.ditto_trip.hashtag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.dittotrip.ditto_trip.hashtag.domain.Hashtag;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
}
