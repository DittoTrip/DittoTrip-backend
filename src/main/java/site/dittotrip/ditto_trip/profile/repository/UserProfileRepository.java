package site.dittotrip.ditto_trip.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.dittotrip.ditto_trip.profile.domain.UserProfile;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findByUser(User user);

}
