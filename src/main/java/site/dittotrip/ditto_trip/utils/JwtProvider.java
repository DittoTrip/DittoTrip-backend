package site.dittotrip.ditto_trip.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.*;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;

import java.util.Collection;
import java.util.Date;

@Component
public class JwtProvider {

  @Value("${jwt.token.secret-key}")
  private String SECRET_KEY;

  @Value("${jwt.token.expire-time}")
  private long EXPIRE_TIME;

  /**
   * Authentication 기반 토큰 생성 메소드.
   * {@link #generateToken(String, Collection)}
   * @param authentication
   * @return JWT(String)
   */
  public String generateToken(Authentication authentication) {
    return generateToken(authentication.getName(), authentication.getAuthorities());
  }

  /**
   * Username 및 Authorities 기반 토큰 생성 메소드.
   * @param username
   * @param authorities
   * @return JWT(String)
   */
  public String generateToken(String username, Collection<? extends GrantedAuthority> authorities) {
    return Jwts.builder()
        .setSubject(username)
        .claim("role", authorities.stream().findFirst().get().toString())
        .setExpiration(getExpireDate())
        .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
        .compact();
  }

//  /**
//   * 토큰으로부터 받은 정보를 기반으로 Authentication 객체를 반환하는 메소드.
//   * @param accessToken
//   * @return Authentication
//   */
//  public Authentication getAuthentication(String accessToken) {
//    return new UsernamePasswordAuthenticationToken(getUsername(accessToken), "", createAuthorityList(getRole(accessToken)));
//  }

  /**
   * 사용자가 보낸 요청 헤더의 'Authorization' 필드에서 토큰을 추출하는 메소드.
   * @param request
   * @return token(String)
   */
  public String resolveToken(HttpServletRequest request) {
    return request.getHeader("Authorization");
  }

  public boolean validateToken(String accessToken) {
    if (accessToken == null) {
      return false;
    }

    try {
      return Jwts.parser()
          .setSigningKey(SECRET_KEY)
          .parseClaimsJws(accessToken)
          .getBody()
          .getExpiration()
          .after(new Date());
    }
    catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public String getUsername(String accessToken) {
    return Jwts.parser()
        .setSigningKey(SECRET_KEY)
        .parseClaimsJws(accessToken)
        .getBody()
        .getSubject();
  }

  public String getRole(String accessToken) {
    return (String) Jwts.parser()
        .setSigningKey(SECRET_KEY)
        .parseClaimsJws(accessToken)
        .getBody()
        .get("role", String.class);

  }

  private Date getExpireDate() {
    Date now = new Date();
    return new Date(now.getTime() + EXPIRE_TIME);
  }
}