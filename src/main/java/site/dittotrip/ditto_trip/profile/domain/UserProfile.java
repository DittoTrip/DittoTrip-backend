package site.dittotrip.ditto_trip.profile.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.dittotrip.ditto_trip.reward.domain.Badge;
import site.dittotrip.ditto_trip.reward.domain.Item;
import site.dittotrip.ditto_trip.user.domain.User;

import static jakarta.persistence.FetchType.LAZY;

/**
 * 초기화 작업 필요
 */
@Entity
@NoArgsConstructor
@Getter
public class UserProfile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_profile_id")
    private Long id;

    private Integer progressionBar = 0;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "item_skin_id")
    @Setter
    private Item itemSkin;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "item_eyes_id")
    @Setter
    private Item itemEyes;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "item_mouse_id")
    @Setter
    private Item itemMouse;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "item_hair_id")
    @Setter
    private Item itemHair;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "item_accessory_id")
    @Setter
    private Item itemAccessory;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "user_badge_id")
    @Setter
    private Badge badge;

    public UserProfile(User user) {
        this.user = user;
    }
}
