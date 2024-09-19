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

import java.security.SecureRandom;
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

  public void logout(String refreshToken) {
    if(jwtProvider.validateToken(refreshToken)){
      User user = userRepository.findById(Long.valueOf(jwtProvider.getUserId(refreshToken))).orElseThrow(NoSuchElementException::new);
      jwtProvider.deleteRefreshToken(user.getId().toString());
    }
  }

  public void resetPassword(String email){
    User user = userRepository.findByEmail(email).orElseThrow(NoSuchElementException::new);
    String newPassword = generatePassword();

    user.setPassword(passwordEncoder.encode(newPassword));
    userRepository.save(user);

    String subject = "비밀번호 초기화";
    emailService.sendSimpleMessage(email, subject, newPassword);
  }

  private static String generatePassword() {
    // 사용할 문자들 정의 (숫자, 대문자, 소문자, 특수문자)
    String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String lowerCase = "abcdefghijklmnopqrstuvwxyz";
    String digits = "0123456789";
    String specialCharacters = "!@#$%^&*()-_+=<>?";
    String allCharacters = upperCase + lowerCase + digits + specialCharacters;

    // SecureRandom 객체 생성
    SecureRandom random = new SecureRandom();
    StringBuilder password = new StringBuilder(12);

    // 각 종류의 문자 하나씩 추가
    password.append(upperCase.charAt(random.nextInt(upperCase.length())));
    password.append(lowerCase.charAt(random.nextInt(lowerCase.length())));
    password.append(digits.charAt(random.nextInt(digits.length())));
    password.append(specialCharacters.charAt(random.nextInt(specialCharacters.length())));

    // 남은 길이만큼 랜덤 문자 추가
    for (int i = 4; i < 12; i++) {
      password.append(allCharacters.charAt(random.nextInt(allCharacters.length())));
    }

    // 비밀번호 섞기
    for (int i = password.length() - 1; i > 0; i--) {
      int j = random.nextInt(i + 1);
      char temp = password.charAt(i);
      password.setCharAt(i, password.charAt(j));
      password.setCharAt(j, temp);
    }

    return password.toString();
  }

  private static String generateRandomNumber() {
    Random random = new Random();
    int randomNumber = 100000 + random.nextInt(900000); // 100000 ~ 999999 범위의 숫자 생성
    return String.valueOf(randomNumber);
  }
}
