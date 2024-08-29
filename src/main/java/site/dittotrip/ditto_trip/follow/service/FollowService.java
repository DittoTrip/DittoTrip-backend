package site.dittotrip.ditto_trip.follow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.follow.domain.Follow;
import site.dittotrip.ditto_trip.follow.domain.dto.FollowListRes;
import site.dittotrip.ditto_trip.follow.exception.ExistingFollowException;
import site.dittotrip.ditto_trip.follow.exception.FollowSelfException;
import site.dittotrip.ditto_trip.follow.repository.FollowRepository;
import site.dittotrip.ditto_trip.review.exception.NoAuthorityException;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    public FollowListRes findFollowingList(User reqUser, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        List<Follow> follows = followRepository.findByFollowingUser(user);

        return FollowListRes.fromEntities(follows, reqUser, Boolean.TRUE);
    }

    public FollowListRes findFollowedList(User reqUser, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        List<Follow> follows = followRepository.findByFollowedUser(user);

        return FollowListRes.fromEntities(follows, reqUser, Boolean.FALSE);
    }

    @Transactional(readOnly = false)
    public void saveFollow(User reqUser, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);

        if (user.getId() == reqUser.getId()) {
            throw new FollowSelfException();
        }

        followRepository.findByFollowingUserAndFollowedUser(reqUser, user).ifPresent(m -> {
            throw new ExistingFollowException();
        });

        Follow follow = new Follow(reqUser, user);
        followRepository.save(follow);
    }

    @Transactional(readOnly = false)
    public void removeFollow(User reqUser, Long followId) {
        Follow follow = followRepository.findById(followId).orElseThrow(NoSuchElementException::new);

        if (!follow.getFollowingUser().equals(reqUser) && !follow.getFollowedUser().equals(reqUser)) {
            throw new NoAuthorityException();
        }

        followRepository.delete(follow);
    }

}
