package site.dittotrip.ditto_trip.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;
import site.dittotrip.ditto_trip.ditto.repository.DittoRepository;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.review.repository.ReviewRepository;
import site.dittotrip.ditto_trip.user.domain.dto.*;
import site.dittotrip.ditto_trip.user.domain.enums.UserStatus;
import site.dittotrip.ditto_trip.user.repository.UserRepository;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
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

  public UserDetailRes findUserDetail(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
    Long reviewCount = reviewRepository.countByUser(user);
    Long dittoCount = dittoRepository.countByUser(user);

    return UserDetailRes.fromEntity(UserDataForAdmin.fromEntity(user, reviewCount.intValue(), dittoCount.intValue()));
  }

  public MyUserInfoRes findMyUserInfo(Long reqUserId) {
    User reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
    return MyUserInfoRes.fromEntity(reqUser);
  }

  public ContentListRes findUsersReviewList(Long userId, Pageable pageable) {
    User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
    Page<Review> page = reviewRepository.findByUser(user, pageable);
    return ContentListRes.fromReviews(page);
  }

  public ContentListRes findUsersDittoList(Long userId, Pageable pageable) {
    User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
    Page<Ditto> page = dittoRepository.findByUser(user, pageable);
    return ContentListRes.fromDittos(page);
  }

  @Transactional(readOnly = false)
  public void pauseUser(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
    user.setUserStatus(UserStatus.PERMANENTLY_BANNED);
  }

}
