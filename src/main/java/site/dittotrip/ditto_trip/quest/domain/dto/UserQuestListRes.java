package site.dittotrip.ditto_trip.quest.domain.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;
import site.dittotrip.ditto_trip.quest.domain.UserQuest;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserQuestListRes {

    private List<UserQuestData> userQuestDataList = new ArrayList<>();

    private Integer totalPages;

    public static UserQuestListRes fromEntities(Page<UserQuest> page) {
        UserQuestListRes userQuestListRes = new UserQuestListRes();
        userQuestListRes.setUserQuestDataList(UserQuestData.fromEntities(page.getContent()));
        userQuestListRes.setTotalPages(page.getTotalPages());
        return userQuestListRes;
    }

}
