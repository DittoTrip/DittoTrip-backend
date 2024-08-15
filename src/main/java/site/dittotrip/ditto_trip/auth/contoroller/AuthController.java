package site.dittotrip.ditto_trip.auth.contoroller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import site.dittotrip.ditto_trip.auth.contoroller.dto.LoginReq;
import site.dittotrip.ditto_trip.auth.contoroller.dto.SignupReq;
import site.dittotrip.ditto_trip.auth.service.AuthService;
import site.dittotrip.ditto_trip.auth.contoroller.dto.VerifyCodeReq;
import site.dittotrip.ditto_trip.utils.JwtProvider;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;
  private final JwtProvider jwtProvider;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;

  @EventListener(ApplicationReadyEvent.class)
  public void ready() {
    log.info("controller register");
  }

  @GetMapping("/duplication-check")
  public boolean duplicationCheck(String email, String nickname) {
    return authService.duplicationCheck(email, nickname);
  }

  @GetMapping("/send-code")
  public String sendCode(String email) {
    return authService.sendCode(email);
  }

  @PostMapping("/verify-code")
  public boolean verifyCode(@RequestBody VerifyCodeReq dto) {
    return authService.verifyCode(dto.getEmail(), dto.getCode());
  }

  @PostMapping("/signup")
  public void signup(@RequestBody SignupReq dto, HttpServletResponse response) throws IOException {
    authService.signup(dto);
    String redirectUrl = "/";
    response.sendRedirect(redirectUrl);
  }

  @PostMapping("/login")
  public String login(@RequestBody LoginReq dto)throws Exception{
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
    // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
    // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);


    // 3. 인증 정보를 기반으로 JWT 토큰 생성
    String token = jwtProvider.generateToken(authentication);
    return token;
  }
}
