package site.dittotrip.ditto_trip.profile.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.dittotrip.ditto_trip.profile.domain.UserProfile;

@Data
@NoArgsConstructor
public class UserProfileModifyReq {

    private Long userItemSkinId;
    private Long userItemEyesId;
    private Long userItemMouseId;
    private Long userItemHairId;
    private Long userItemAccessoryId;

}
