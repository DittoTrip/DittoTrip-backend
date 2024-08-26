package site.dittotrip.ditto_trip.auth.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import site.dittotrip.ditto_trip.auth.exception.NotFoundUserInfoException;
import site.dittotrip.ditto_trip.user.domain.User;


import java.util.ArrayList;
import java.util.Collection;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

  private final User user;

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
    return user.getEmail();
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

}
