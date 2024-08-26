package site.dittotrip.ditto_trip.follow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.dittotrip.ditto_trip.follow.domain.Follow;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByFollowingUserAndFollowedUser(User followingUser, User followedUser);

    List<Follow> findByFollowingUser(User followingUser);

    List<Follow> findByFollowedUser(User followedUser);

}
