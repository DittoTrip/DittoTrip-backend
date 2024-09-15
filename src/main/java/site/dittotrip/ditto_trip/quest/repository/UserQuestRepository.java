package site.dittotrip.ditto_trip.quest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import site.dittotrip.ditto_trip.quest.domain.UserQuest;
import site.dittotrip.ditto_trip.quest.domain.enums.UserQuestStatus;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.List;

public interface UserQuestRepository extends JpaRepository<UserQuest, Long> {

    Page<UserQuest> findByUserAndUserQuestStatus(User user, UserQuestStatus userQuestStatus, Pageable pageable);

    List<UserQuest> findByUserAndUserQuestStatus(User user, UserQuestStatus userQuestStatus);

}
