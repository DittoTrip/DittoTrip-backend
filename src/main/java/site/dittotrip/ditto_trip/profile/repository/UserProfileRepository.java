package site.dittotrip.ditto_trip.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.dittotrip.ditto_trip.profile.domain.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}
