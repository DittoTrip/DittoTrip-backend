package site.dittotrip.ditto_trip.quest.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.dittotrip.ditto_trip.quest.domain.enums.UserQuestStatus;
import site.dittotrip.ditto_trip.user.domain.User;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@Getter
public class UserQuest {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_quest_id")
    private Long id;

    private UserQuestStatus userQuestStatus = UserQuestStatus.NOT_ACHIEVE;
    private Integer nowCount = 0;
    private LocalDateTime achieveDateTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "quest_id")
    private Quest quest;

    public void achieveQuest() {
        this.userQuestStatus = UserQuestStatus.ACHIEVE;
        this.achieveDateTime = LocalDateTime.now();
    }

    public void addNowCount() {
        this.nowCount += 1;
    }

    public void achieveButPendingQuest() {
        this.userQuestStatus = UserQuestStatus.PENDING;
    }

}
