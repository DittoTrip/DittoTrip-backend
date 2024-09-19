package site.dittotrip.ditto_trip.user.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import site.dittotrip.ditto_trip.user.domain.User;

@Data
@AllArgsConstructor
public class UserDetailRes {

    private UserDataForAdmin userDataForAdmin;

    public static UserDetailRes fromEntity(UserDataForAdmin userDataForAdmin) {
        return new UserDetailRes(userDataForAdmin);
    }
}
