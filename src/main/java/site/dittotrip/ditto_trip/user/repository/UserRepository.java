package site.dittotrip.ditto_trip.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);
  Optional<User> findByNickname(String nickname);
  List<User> findAllByEmailOrNickname(String email, String nickname);
  Optional<User> findByProviderAndProviderId(String provider, String providerId);
}
