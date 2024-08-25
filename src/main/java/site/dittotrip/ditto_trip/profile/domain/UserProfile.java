package site.dittotrip.ditto_trip.profile.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.dittotrip.ditto_trip.item.domain.Badge;
import site.dittotrip.ditto_trip.item.domain.Item;
import site.dittotrip.ditto_trip.user.domain.User;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@Getter
public class UserProfile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_profile_id")
    private Long id;

    private Integer progressionBar;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "item_skin_id")
    private Item itemSkin;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "item_eyes_id")
    private Item itemEyes;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "item_mouse_id")
    private Item itemMouse;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "item_hair_id")
    private Item itemHair;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "item_accessory_id")
    private Item itemAccessory;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "user_badge_id")
    private Badge badge;

}
