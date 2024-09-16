package site.dittotrip.ditto_trip.reward.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import site.dittotrip.ditto_trip.reward.domain.enums.RewardType;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
public class Reward {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reward_id")
    private Long id;

    private String name;

    @Setter
    private String imagePath;

    @Enumerated(EnumType.STRING)
    private RewardType rewardType;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    public Reward(String name, String imagePath, RewardType rewardType) {
        this.name = name;
        this.imagePath = imagePath;
        this.rewardType = rewardType;
    }

}
