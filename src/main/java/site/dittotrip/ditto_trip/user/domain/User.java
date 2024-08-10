package site.dittotrip.ditto_trip.user.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String email;
  private String password;
  private String nickname;

  @ElementCollection(fetch = FetchType.EAGER)
  private Set<GrantedAuthority> authorities = new HashSet<>();
}
