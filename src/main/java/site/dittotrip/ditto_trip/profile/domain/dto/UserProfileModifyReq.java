package site.dittotrip.ditto_trip.profile.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.dittotrip.ditto_trip.profile.domain.UserProfile;

@Data
@NoArgsConstructor
public class UserProfileModifyReq {

    private Long itemSkinId;
    private Long itemEyesId;
    private Long itemMouseId;
    private Long itemHairId;
    private Long itemAccessoryId;
    private Long badgeId;

}
