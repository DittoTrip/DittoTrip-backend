package site.dittotrip.ditto_trip.follow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.alarm.domain.Alarm;
import site.dittotrip.ditto_trip.alarm.repository.AlarmRepository;
import site.dittotrip.ditto_trip.follow.domain.Follow;
import site.dittotrip.ditto_trip.follow.domain.dto.FollowListRes;
import site.dittotrip.ditto_trip.follow.exception.ExistingFollowException;
import site.dittotrip.ditto_trip.follow.exception.FollowSelfException;
import site.dittotrip.ditto_trip.follow.repository.FollowRepository;
import site.dittotrip.ditto_trip.exception.common.NoAuthorityException;
import site.dittotrip.ditto_trip.quest.aop.QuestHandlingTargetMethod;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final AlarmRepository alarmRepository;

    public FollowListRes findFollowingList(Long reqUserId, Long userId) {
        User reqUser = null;
        if (reqUser != null) {
            reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        }

        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        List<Follow> follows = followRepository.findByFollowingUser(user);

        return FollowListRes.fromEntities(follows, reqUser, Boolean.TRUE);
    }

    public FollowListRes findFollowedList(Long reqUserId, Long userId) {
        User reqUser = null;
        if (reqUser != null) {
            reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        }

        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        List<Follow> follows = followRepository.findByFollowedUser(user);

        return FollowListRes.fromEntities(follows, reqUser, Boolean.FALSE);
    }

    @QuestHandlingTargetMethod
    @Transactional(readOnly = false)
    public void saveFollow(Long reqUserId, Long userId) {
        User reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);

        if (user.getId() == reqUser.getId()) {
            throw new FollowSelfException();
        }

        followRepository.findByFollowingUserAndFollowedUser(reqUser, user).ifPresent(m -> {
            throw new ExistingFollowException();
        });

        Follow follow = new Follow(reqUser, user);
        followRepository.save(follow);

        processAlarmInSaveFollow(follow);
    }

    @Transactional(readOnly = false)
    public void removeFollow(Long reqUserId, Long userId) {
        User reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);

        Follow follow = followRepository.findByFollowingUserAndFollowedUser(reqUser, user).orElseThrow(NoSuchElementException::new);
        followRepository.delete(follow);
    }

    private void processAlarmInSaveFollow(Follow follow) {
        String title = "새로운 팔로워가 생겼어요 !!";
        String body = follow.getFollowingUser().getNickname() + "님이 당신을 팔로우 했습니다.";
        String path = "/user-page/" + follow.getFollowingUser().getId(); // 수정해야 할 수 있음 !!
        List<User> targets = new ArrayList<>(List.of(follow.getFollowedUser()));
        alarmRepository.saveAll(Alarm.createAlarms(title, body, path, targets));
    }

}
