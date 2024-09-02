package site.dittotrip.ditto_trip.auth.service;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import site.dittotrip.ditto_trip.auth.contoroller.dto.SignupReq;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.repository.UserRepository;
import site.dittotrip.ditto_trip.user.service.UserService;
import site.dittotrip.ditto_trip.utils.EmailService;
import site.dittotrip.ditto_trip.utils.RedisService;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {
  @Value("${REDIS_EXPIRE_TIME}")
  private int REDIS_EXPIRE_TIME;

  private final UserRepository userRepository;
  private final UserService userService;
  private final EmailService emailService;
  private final RedisService redisService;
  private final PasswordEncoder passwordEncoder;


  public boolean duplicationCheck(String email, String nickname) {
    List<User> users = userService.findAllByEmailOrNickname(email, nickname);
    return users.isEmpty();
  }

  public String sendCode(String email){
    String subject = "이메일 인증 코드";
    String code = generateRandomNumber();
    System.out.println(code);
    emailService.sendSimpleMessage(email, subject, code);
    redisService.setex("code:" + email, code, REDIS_EXPIRE_TIME);
    return "Email sent successfully";
  }

  public boolean verifyCode(String email, String code){
    String savedCode = redisService.get("code:" + email);
    return savedCode.equals(code);
  }

  public User signup(SignupReq dto) throws BadRequestException {
    if(!duplicationCheck(dto.getEmail(), dto.getNickname())){
      throw new BadRequestException();
    }

    if(!verifyCode(dto.getEmail(), dto.getCode())){
      throw new BadRequestException();
    }

    User user = new User();
    user.setEmail(dto.getEmail());
    user.setNickname(dto.getNickname());
    user.setPassword(passwordEncoder.encode(dto.getPassword()));

    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
    user.getAuthorities().add(authority);

    return userRepository.save(user);
  }

  private static String generateRandomNumber() {
    Random random = new Random();
    int randomNumber = 100000 + random.nextInt(900000); // 100000 ~ 999999 범위의 숫자 생성
    return String.valueOf(randomNumber);
  }
}
