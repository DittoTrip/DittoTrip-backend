package site.dittotrip.ditto_trip.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);
  Optional<User> findByNickname(String nickname);

  @Query("select a from User a where (a.email is not null and a.email = :email) or (a.nickname is not null and a.nickname = :nickname)")
  List<User> findAllByEmailOrNickname(String email, String nickname);
  Optional<User> findByProviderAndProviderId(String provider, String providerId);

  Page<User> findByNicknameContaining(String word, Pageable pageable);

}
