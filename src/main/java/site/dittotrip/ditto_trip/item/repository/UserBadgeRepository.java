package site.dittotrip.ditto_trip.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.dittotrip.ditto_trip.item.domain.UserBadge;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.List;

public interface UserBadgeRepository extends JpaRepository<UserBadge, Long> {

    List<UserBadge> findByUser(User user);

}
