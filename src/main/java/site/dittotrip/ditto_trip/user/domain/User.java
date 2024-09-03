package site.dittotrip.ditto_trip.user.domain;

import jakarta.persistence.*;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import site.dittotrip.ditto_trip.follow.domain.Follow;
import site.dittotrip.ditto_trip.profile.domain.UserProfile;
import site.dittotrip.ditto_trip.spot.domain.SpotVisit;
import site.dittotrip.ditto_trip.user.domain.enums.UserStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "users_id")
  private Long id;
  
  @Column(name = "user_name")
  private String name;
  
  private String email;
  private String password;
  private String nickname;

  @Enumerated(EnumType.STRING)
  private UserStatus userStatus = UserStatus.NORMAL;
  private LocalDateTime suspendedDateTime = null;

  @ElementCollection(fetch = FetchType.EAGER)
  private Set<GrantedAuthority> authorities = new HashSet<>();

  @OneToMany(mappedBy = "user")
  private List<SpotVisit> spotVisits = new ArrayList<>();

  @OneToOne(mappedBy = "user")
  private UserProfile userProfile;

  @OneToMany(mappedBy = "followingUser")
  private List<Follow> followings = new ArrayList<>();

  @OneToMany(mappedBy = "followedUser")
  private List<Follow> followeds = new ArrayList<>();

}
