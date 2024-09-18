package site.dittotrip.ditto_trip.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import site.dittotrip.ditto_trip.ditto.repository.DittoRepository;
import site.dittotrip.ditto_trip.review.repository.ReviewRepository;
import site.dittotrip.ditto_trip.user.domain.dto.UserDataForAdmin;
import site.dittotrip.ditto_trip.user.domain.dto.UserListForAdminRes;
import site.dittotrip.ditto_trip.user.domain.dto.UserListRes;
import site.dittotrip.ditto_trip.user.repository.UserRepository;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;
  private final ReviewRepository reviewRepository;
  private final DittoRepository dittoRepository;

  public List<User> findAllByEmailOrNickname(String email, String nickname){
    return userRepository.findAllByEmailOrNickname(email, nickname);
  }

  public UserListRes findUserList(String word, Pageable pageable) {
    Page<User> page = userRepository.findByNicknameContaining(word, pageable);
    return UserListRes.fromEntities(page);
  }

  public UserListForAdminRes findUserListForAdmin(String query, Pageable pageable) {
    Page<User> page;
    if (query != null) {
      page = userRepository.findByNicknameContaining(query, pageable);
    } else {
      page = userRepository.findAll(pageable);
    }

    UserListForAdminRes res = new UserListForAdminRes();
    res.setTotalPages(page.getTotalPages());

    for (User user : page.getContent()) {
      Long reviewCount = reviewRepository.countByUser(user);
      Long dittoCount = dittoRepository.countByUser(user);
      res.getUserDataForAdminList().add(UserDataForAdmin.fromEntity(user, reviewCount.intValue(), dittoCount.intValue()));
    }

    return res;
  }

}
