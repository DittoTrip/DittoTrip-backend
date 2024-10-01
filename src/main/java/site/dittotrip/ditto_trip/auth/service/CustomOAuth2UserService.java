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
import site.dittotrip.ditto_trip.reward.domain.UserBadge;
import site.dittotrip.ditto_trip.reward.domain.UserItem;
import site.dittotrip.ditto_trip.reward.repository.BadgeRepository;
import site.dittotrip.ditto_trip.reward.repository.ItemRepository;
import site.dittotrip.ditto_trip.reward.repository.UserBadgeRepository;
import site.dittotrip.ditto_trip.reward.repository.UserItemRepository;
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
  private final UserBadgeRepository userBadgeRepository;
  private final UserItemRepository userItemRepository;
  private final ItemRepository itemRepository;
  private final BadgeRepository badgeRepository;

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

        UserItem userItem1 = userItemRepository.save(new UserItem(newUser, itemRepository.findItemByNameEquals("skin2")));
        UserItem userItem2 = userItemRepository.save(new UserItem(newUser, itemRepository.findItemByNameEquals("eyes1")));
        UserItem userItem3 = userItemRepository.save(new UserItem(newUser, itemRepository.findItemByNameEquals("mouth1")));
        UserItem userItem4 = userItemRepository.save(new UserItem(newUser, itemRepository.findItemByNameEquals("hair1")));
        UserItem userItem5 = userItemRepository.save(new UserItem(newUser, itemRepository.findItemByNameEquals("accessory0")));
        UserBadge userBadge1 = userBadgeRepository.save(new UserBadge(newUser, badgeRepository.findBadgeByNameEquals("여행의 새싹")));

      UserProfile userProfile = newUser.getUserProfile();
        userProfile.setUserItemSkin(userItem1);
        userProfile.setUserItemEyes(userItem2);
        userProfile.setUserItemMouth(userItem3);
        userProfile.setUserItemHair(userItem4);
        userProfile.setUserItemAccessory(userItem5);
        userProfile.setUserBadge(userBadge1);
        userProfileRepository.save(userProfile);

      return newUser;
    }

    return user.get();
  }

  private String createRandomNickname() {
    Random random = new Random();
    String newNickname;

    do {
        newNickname = "익명" + (random.nextInt(900000) + 100000);
    }while(userRepository.findByNickname(newNickname).isPresent());

    return newNickname;
  }
}


