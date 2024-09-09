package site.dittotrip.ditto_trip.auth.service;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import site.dittotrip.ditto_trip.auth.domain.dto.LoginReq;
import site.dittotrip.ditto_trip.auth.domain.dto.SignupReq;
import site.dittotrip.ditto_trip.auth.domain.dto.TokenRes;
import site.dittotrip.ditto_trip.auth.domain.enums.TokenType;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.repository.UserRepository;
import site.dittotrip.ditto_trip.user.service.UserService;
import site.dittotrip.ditto_trip.utils.EmailService;
import site.dittotrip.ditto_trip.utils.JwtProvider;
import site.dittotrip.ditto_trip.utils.RedisService;

import java.util.List;
import java.util.NoSuchElementException;
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
  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final JwtProvider jwtProvider;

  public boolean duplicationCheck(String email, String nickname) {
    List<User> users = userService.findAllByEmailOrNickname(email, nickname);
    return users.isEmpty();
  }

  public String sendCode(String email) throws BadRequestException {
    if(!duplicationCheck(email, null)){
      throw new BadRequestException("이미 존재하는 이메일입니다.");
    }

    String subject = "이메일 인증 코드";
    String code = generateRandomNumber();
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

  public TokenRes login(LoginReq dto) {
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

    String accessToken = jwtProvider.generateToken(authentication, TokenType.ACCESS_TOKEN);
    String refreshToken = jwtProvider.generateToken(authentication, TokenType.REFRESH_TOKEN);
    jwtProvider.saveRefreshToken(authentication.getName(), refreshToken);

    return new TokenRes(accessToken,refreshToken);
  }

  public TokenRes refresh(String refreshToken) {
    if(!jwtProvider.validateToken(refreshToken)){
      throw new RuntimeException("Invalid refresh token");
    }

    User user = userRepository.findById(Long.valueOf(jwtProvider.getUserId(refreshToken))).orElseThrow(NoSuchElementException::new);

    if(!jwtProvider.getRefreshToken(user.getId().toString()).equals(refreshToken)) {
      throw new RuntimeException("Invalid refresh token");
    }

    String newAccessToken = jwtProvider.generateToken(user.getId().toString(),user.getAuthorities(), TokenType.ACCESS_TOKEN);
    String newRefreshToken = jwtProvider.generateToken(user.getId().toString(),user.getAuthorities(), TokenType.REFRESH_TOKEN);
    jwtProvider.saveRefreshToken(user.getId().toString(), newRefreshToken);

    return new TokenRes(newAccessToken, newRefreshToken);
  }

  private static String generateRandomNumber() {
    Random random = new Random();
    int randomNumber = 100000 + random.nextInt(900000); // 100000 ~ 999999 범위의 숫자 생성
    return String.valueOf(randomNumber);
  }
}
