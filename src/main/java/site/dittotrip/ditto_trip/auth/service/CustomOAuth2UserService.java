package site.dittotrip.ditto_trip.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import site.dittotrip.ditto_trip.auth.domain.CustomUserDetails;
import site.dittotrip.ditto_trip.profile.domain.UserProfile;
import site.dittotrip.ditto_trip.profile.repository.UserProfileRepository;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.repository.UserRepository;

import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

  private final UserRepository userRepository;
  private final UserProfileRepository userProfileRepository;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) {
    OAuth2User oAuth2User = super.loadUser(userRequest);

    String provider = userRequest.getClientRegistration().getRegistrationId();
    String providerId = oAuth2User.getName();

    User user = getOrSave(provider, providerId);
    Map<String, Object> attributes = oAuth2User.getAttributes();

    return new CustomUserDetails(user, attributes);
  }

  private User getOrSave(String provider, String providerId) {
    Optional<User> user = userRepository.findByProviderAndProviderId(provider, providerId);
    if (user.isEmpty()) {
      User newUser = new User();
      newUser.setNickname(createRandomNickname());
      newUser.setProvider(provider);
      newUser.setProviderId(providerId);
      newUser.getAuthorities().add(new SimpleGrantedAuthority("ROLE_USER"));
      userRepository.save(newUser);

      UserProfile userProfile = new UserProfile(newUser);
      newUser.setUserProfile(userProfile);
      userProfileRepository.save(userProfile);

      return newUser;
    }

    return user.get();
  }

  private String createRandomNickname() {
    Random random = new Random();
    String newNickname;

    do {
      String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
      StringBuilder nickname = new StringBuilder();

      for (int i = 0; i < 3; i++) {
        nickname.append(alphabet.charAt(random.nextInt(alphabet.length())));
      }

      int number = random.nextInt(9000) + 1000;
      nickname.append(number);

      newNickname = nickname.toString();
    }while(userRepository.findByNickname(newNickname).isPresent());

    return newNickname;
  }
}


