package site.dittotrip.ditto_trip.userpage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.repository.UserRepository;
import site.dittotrip.ditto_trip.userpage.domain.dto.UserPageRes;

import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserPageService {

    private UserRepository userRepository;

    public UserPageRes findUserPage(User reqUser, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        return null;
    }

}
