package site.dittotrip.ditto_trip.quest.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.dittotrip.ditto_trip.quest.domain.enums.UserQuestStatus;
import site.dittotrip.ditto_trip.user.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@Getter
public class UserQuest {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_quest_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private UserQuestStatus userQuestStatus = UserQuestStatus.NOT_ACHIEVE;
    private Integer nowCount = 0;
    private LocalDateTime achieveDateTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "quest_id")
    private Quest quest;

    public UserQuest(User user, Quest quest) {
        this.user = user;
        this.quest = quest;
    }

    public static List<UserQuest> createUserQuests(List<User> users, Quest quest) {
        List<UserQuest> userQuests = new ArrayList<>();
        for (User user : users) {
            userQuests.add(new UserQuest(user, quest));
        }
        return userQuests;
    }

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
