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

    public Badge(String imagePath) {
        super(imagePath, RewardType.BADGE);
    }

}
