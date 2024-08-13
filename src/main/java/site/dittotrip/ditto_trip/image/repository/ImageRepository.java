package site.dittotrip.ditto_trip.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.dittotrip.ditto_trip.image.domain.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
