package site.dittotrip.ditto_trip.reward.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import site.dittotrip.ditto_trip.reward.domain.enums.RewardType;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Badge extends Reward {

    private String body;
    private String conditionBody;

    public Badge(String name, String imagePath, String body, String conditionBody) {
        super(name, imagePath, RewardType.BADGE);
        this.body = body;
        this.conditionBody = conditionBody;
    }

}
