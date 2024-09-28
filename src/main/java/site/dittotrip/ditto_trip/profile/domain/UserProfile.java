package site.dittotrip.ditto_trip.profile.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.dittotrip.ditto_trip.reward.domain.*;
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
    @JoinColumn(name = "user_item_skin_id")
    @Setter
    private UserItem userItemSkin;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "user_item_eyes_id")
    @Setter
    private UserItem userItemEyes;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "user_item_mouth_id")
    @Setter
    private UserItem userItemMouth;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "user_item_hair_id")
    @Setter
    private UserItem userItemHair;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "user_item_accessory_id")
    @Setter
    private UserItem userItemAccessory;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "user_badge_id")
    @Setter
    private UserBadge userBadge;

    public UserProfile(User user) {
        this.user = user;
    }

    public void addExp(Integer exp) {
        this.progressionBar += exp;
    }



}
