package site.dittotrip.ditto_trip.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.dittotrip.ditto_trip.user.repository.UserRepository;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
  private final UserRepository userRepository;

  public List<User> findAllByEmailOrNickname(String email, String nickname){
    return userRepository.findAllByEmailOrNickname(email, nickname);
  }
}
