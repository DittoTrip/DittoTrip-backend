package site.dittotrip.ditto_trip.spot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.user.domain.User;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@Getter
public class SpotApply {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spot_apply_id")
    private Long id;

    private String name;
    private String intro;
    private String address;
    private LocalTime startTime;
    private LocalTime endTime;
    private String phoneNumber;
    private String homeUri;
    private Double pointX;
    private Double pointY;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @ManyToMany(fetch = LAZY)
    @JoinTable(name = "category_spot_apply")
    private List<Category> categories = new ArrayList<>();

    // 해시태그 리스트

    public SpotApply(String name, String intro, String address, LocalTime startTime, LocalTime endTime, String phoneNumber, String homeUri, Double pointX, Double pointY, User user, List<Category> categories) {
        this.name = name;
        this.intro = intro;
        this.address = address;
        this.startTime = startTime;
        this.endTime = endTime;
        this.phoneNumber = phoneNumber;
        this.homeUri = homeUri;
        this.pointX = pointX;
        this.pointY = pointY;
        this.user = user;
        this.categories = categories;
    }

}
