package site.dittotrip.ditto_trip.userpage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.alarm.domain.Alarm;
import site.dittotrip.ditto_trip.alarm.repository.AlarmRepository;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;
import site.dittotrip.ditto_trip.ditto.repository.DittoRepository;
import site.dittotrip.ditto_trip.follow.domain.Follow;
import site.dittotrip.ditto_trip.follow.repository.FollowRepository;
import site.dittotrip.ditto_trip.profile.domain.UserProfile;
import site.dittotrip.ditto_trip.profile.repository.UserProfileRepository;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.repository.UserRepository;
import site.dittotrip.ditto_trip.userpage.domain.dto.UserPageRes;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserPageService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final DittoRepository dittoRepository;
    private final FollowRepository followRepository;
    private final AlarmRepository alarmRepository;

    /** 추가될 데이터
     * 1. 팔로우, 팔로잉 데이터
     * 2. request user 와의 팔로잉 정보
     */
    public UserPageRes findUserPage(Long reqUserId, Long userId) {
        User reqUser = null;
        if (reqUserId != null) {
            reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        }

        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        UserProfile userProfile = userProfileRepository.findByUser(user).orElseThrow(NoSuchElementException::new);
        List<Ditto> dittos = dittoRepository.findTop6ByUserOrderByCreatedDateTimeDesc(user);

        Integer followingCount = followRepository.countByFollowingUser(user);
        Integer followedCount = followRepository.countByFollowedUser(user);

        // 읽지 않은 알림 정보 조회
        Boolean isNotCheckedAlarm = Boolean.FALSE;
        if (reqUser != null) {
            List<Alarm> reqUsersAlarms = alarmRepository.findByUserAndIsChecked(reqUser, Boolean.FALSE);
            isNotCheckedAlarm = !reqUsersAlarms.isEmpty();
        }

        // 팔로잉 정보 조회
        Optional<Follow> followOptional = followRepository.findByFollowingUserAndFollowedUser(reqUser, user);
        Long myFollowingId = null;
        if (followOptional.isPresent()) {
            myFollowingId = followOptional.get().getId();
        }

        return UserPageRes.fromEntities(reqUser, user, userProfile, dittos, followingCount, followedCount, isNotCheckedAlarm, myFollowingId);
    }

}
