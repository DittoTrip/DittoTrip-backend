package site.dittotrip.ditto_trip.quest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.quest.domain.UserQuest;
import site.dittotrip.ditto_trip.quest.domain.dto.UserQuestListRes;
import site.dittotrip.ditto_trip.quest.domain.enums.UserQuestStatus;
import site.dittotrip.ditto_trip.quest.repository.QuestRepository;
import site.dittotrip.ditto_trip.quest.repository.UserQuestRepository;
import site.dittotrip.ditto_trip.user.domain.User;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestService {

    private final QuestRepository questRepository;
    private final UserQuestRepository userQuestRepository;

    public UserQuestListRes findQuestList(User user, UserQuestStatus userQuestStatus, Pageable pageable) {
        Page<UserQuest> page = userQuestRepository.findByUserAndUserQuestStatus(user, userQuestStatus, pageable);
        return UserQuestListRes.fromEntities(page);
    }

    public void achieveQuest(User user, Long userQuestId) {

    }


}
