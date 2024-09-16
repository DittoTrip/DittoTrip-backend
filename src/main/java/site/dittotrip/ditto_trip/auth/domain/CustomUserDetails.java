package site.dittotrip.ditto_trip.auth.domain;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import site.dittotrip.ditto_trip.auth.exception.NotFoundUserInfoException;
import site.dittotrip.ditto_trip.user.domain.User;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Getter
public class CustomUserDetails implements UserDetails, OAuth2User {

  private final User user;
  private final Map<String, Object> attributes;

  public CustomUserDetails(User user) {
    this.user = user;
    attributes = Map.of();
  }

  public CustomUserDetails(User user, Map<String, Object> attributes) {
    this.user = user;
    this.attributes = attributes;
  }
  
  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<GrantedAuthority> collection = new ArrayList<>();
    for (GrantedAuthority grantedAuthority : user.getAuthorities()){
      collection.add(grantedAuthority);
    }
    return collection;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getId().toString();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public static Long getUserIdFromUserDetails(CustomUserDetails userDetails, boolean isRequired) {
    if (userDetails == null) {
      if (isRequired) {
        throw new NotFoundUserInfoException();
      } else {
        return null;
      }
    } else {
      return userDetails.getUser().getId();
    }
  }

  public static User getUserFromUserDetails(CustomUserDetails userDetails, boolean isRequired) {
    if (userDetails == null) {
      if (isRequired) {
        throw new NotFoundUserInfoException();
      } else {
        return null;
      }
    } else {
      return userDetails.getUser();
    }
  }

  @Override
  public String getName() {
    return user.getId().toString();
  }

  public Long getId() {
    return user.getId();
  }

}
