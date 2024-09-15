package site.dittotrip.ditto_trip.reward.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import site.dittotrip.ditto_trip.user.domain.User;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@Getter
public class UserReward {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_reward_id")
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "reward_id")
    private Reward reward;

    public UserReward(User user, Reward reward) {
        this.user = user;
        this.reward = reward;
    }

}