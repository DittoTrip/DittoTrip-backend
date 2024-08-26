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

@Slf4j
@Component
@RequiredArgsConstructor
public class TestDataConfig {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void testDataInit() {
        log.info("===== TEST DATA INIT START =====");

        User user = new User();
        user.setEmail("won9619v@naver.com");
        user.setNickname("haus");
        user.setPassword(passwordEncoder.encode("1234"));
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        user.getAuthorities().add(authority);
        userRepository.save(user);

        log.info("===== TEST DATA INIT END =====");
    }



}
