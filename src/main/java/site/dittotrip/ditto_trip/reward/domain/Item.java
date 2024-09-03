package site.dittotrip.ditto_trip.reward.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import site.dittotrip.ditto_trip.reward.domain.enums.ItemType;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Item extends Reward {

    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    private String etc;

    public Item(String imagePath, ItemType itemType, String etc) {
        super(imagePath);
        this.itemType = itemType;
        this.etc = etc;
    }

}
