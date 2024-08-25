package site.dittotrip.ditto_trip.userpage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;
import site.dittotrip.ditto_trip.ditto.repository.DittoRepository;
import site.dittotrip.ditto_trip.profile.domain.UserProfile;
import site.dittotrip.ditto_trip.profile.repository.UserProfileRepository;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.repository.UserRepository;
import site.dittotrip.ditto_trip.userpage.domain.dto.UserPageRes;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserPageService {

    private UserRepository userRepository;
    private UserProfileRepository userProfileRepository;
    private DittoRepository dittoRepository;

    /** 추가될 데이터
     * 1. 팔로우, 팔로잉 데이터
     * 2. request user 와의 팔로잉 정보
     */
    public UserPageRes findUserPage(User reqUser, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        UserProfile userProfile = userProfileRepository.findByUser(user).orElseThrow(NoSuchElementException::new);
        List<Ditto> dittos = dittoRepository.findTop6ByUserOrderByCreatedDateTimeDesc(user);

        return UserPageRes.fromEntities(user, userProfile, dittos);
    }

}
