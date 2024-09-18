package site.dittotrip.ditto_trip.user.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.profile.domain.dto.UserProfileData;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.domain.enums.UserStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class UserDataForAdmin {

    private Long userId;
    private UserStatus userStatus;
    private String nickname;
    private String email;
    private LocalDateTime createdDateTime;
    private Integer progressionBar;
    private Integer reviewCount;
    private Integer dittoCount;

    private UserProfileData userProfileData;

    public static UserDataForAdmin fromEntity(User user, Integer reviewCount, Integer dittoCount) {
        return UserDataForAdmin.builder()
                .userId(user.getId())
                .userStatus(user.getUserStatus())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .createdDateTime(user.getCreatedDateTime())
                .progressionBar(user.getUserProfile().getProgressionBar())
                .reviewCount(reviewCount)
                .dittoCount(dittoCount)
                .userProfileData(UserProfileData.fromEntity(user.getUserProfile()))
                .build();
    }

}
