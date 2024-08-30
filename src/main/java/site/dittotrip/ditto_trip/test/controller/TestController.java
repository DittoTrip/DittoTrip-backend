package site.dittotrip.ditto_trip.test.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.repository.UserRepository;
import site.dittotrip.ditto_trip.utils.JwtProvider;

/**
 * 1. 테스트용 액세스 토큰 발급
 */
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TestController {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @GetMapping
    @Operation(summary = "서버 정상 여부 확인",
            description = "")
    public String test() {
        return "server is ok";
    }

    @GetMapping("/access-token")
    @Operation(summary = "테스트를 위한 액세스 토큰 발급",
            description = "")
    public String issueTestToken(@RequestParam(name = "userId") Long userId) {
        User user = userRepository.findById(userId).get();
        return jwtProvider.generateToken(user.getEmail(), user.getAuthorities());
    }

}
