package site.dittotrip.ditto_trip.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import site.dittotrip.ditto_trip.auth.domain.CustomUserDetails;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.repository.UserRepository;

import java.io.IOException;

import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
  private final JwtProvider jwtProvider;
  private final UserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String token = jwtProvider.resolveToken(request);

    if (jwtProvider.validateToken(token)) {
      User user = userRepository.findById(Long.valueOf(jwtProvider.getUserId(token)))
          .orElseThrow(() -> new UsernameNotFoundException("유효하지 않은 토큰입니다."));
      UserDetails userDetails = new CustomUserDetails(user);
      Authentication authentication =  new UsernamePasswordAuthenticationToken(userDetails, "", createAuthorityList(jwtProvider.getRole(token)));
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    filterChain.doFilter(request, response);
  }
}
