package site.dittotrip.ditto_trip.user.domain.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserListRes {

    private List<UserData> userDataList = new ArrayList<>();
    private Integer totalPages;

    public static UserListRes fromEntities(Page<User> page) {
        UserListRes userListRes = new UserListRes();
        userListRes.setTotalPages(page.getTotalPages());

        for (User user : page.getContent()) {
            userListRes.getUserDataList().add(UserData.fromEntity(user));
        }

        return userListRes;
    }

}
