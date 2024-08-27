package site.dittotrip.ditto_trip.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.repository.UserRepository;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestDataConfig {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void testUserDataInit() {
        log.info("===== TEST DATA INIT START =====");

        String email = "won9619v@naver.com";
        String nickname = "haus";
        String password = "1234";

        Optional<User> findUser = userRepository.findByEmail(email);
        if (findUser.isPresent()) {
            log.info("===== ALREADY EXISTING DATA =====");
            return;
        }

        User user = new User();
        user.setEmail(email);
        user.setNickname(nickname);
        user.setPassword(passwordEncoder.encode(password));
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        user.getAuthorities().add(authority);
        userRepository.save(user);

        log.info("===== TEST DATA INIT END =====");
    }

}
