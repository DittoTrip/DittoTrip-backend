package site.dittotrip.ditto_trip.user.domain;

import jakarta.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
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
@NoArgsConstructor
@Table(name = "users")
@Getter @Setter
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
  private String provider;
  private String providerId;

  @CreationTimestamp
  private LocalDateTime createdDateTime;

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
