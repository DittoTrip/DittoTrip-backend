package site.dittotrip.ditto_trip.item.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import site.dittotrip.ditto_trip.item.domain.enums.ItemType;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String imagePath;

    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    private String etc;

}
