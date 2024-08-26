package site.dittotrip.ditto_trip.follow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.follow.repository.FollowRepository;
import site.dittotrip.ditto_trip.user.domain.User;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;

    public void findFollowingList(User reqUser, Long userId) {

    }

    public void findFollowed(User reqUser, Long userId) {

    }

}
