package site.dittotrip.ditto_trip.quest.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import site.dittotrip.ditto_trip.quest.domain.enums.QuestActionType;
import site.dittotrip.ditto_trip.reward.domain.Reward;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@Getter
public class Quest {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quest_id")
    private Long id;

    private String title;
    private String body;
    private Integer conditionCount;
    @Enumerated(EnumType.STRING)
    private QuestActionType questActionType;
    @CreationTimestamp
    private LocalDateTime createdDateTime;

    private Integer rewardExp;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "reward_id")
    private Reward reward;

    public Quest(String title, String body, Integer conditionCount, QuestActionType questActionType, Integer rewardExp, Reward reward) {
        this.title = title;
        this.body = body;
        this.conditionCount = conditionCount;
        this.questActionType = questActionType;
        this.rewardExp = rewardExp;
        this.reward = reward;
    }

}
