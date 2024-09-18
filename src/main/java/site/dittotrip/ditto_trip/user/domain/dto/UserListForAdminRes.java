package site.dittotrip.ditto_trip.user.domain.dto;

import lombok.Data;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserListForAdminRes {

    private List<UserDataForAdmin> userDataForAdminList = new ArrayList<>();

    private Integer totalPages;

}
