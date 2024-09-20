package site.dittotrip.ditto_trip.spot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.hashtag.domain.HashtagSpotApply;
import site.dittotrip.ditto_trip.spot.domain.enums.SpotApplyStatus;
import site.dittotrip.ditto_trip.user.domain.User;

import java.time.LocalDateTime;
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
    private String address;
    private Double pointX;
    private Double pointY;
    @Setter
    private String imagePath;

    @Setter
    @Enumerated(EnumType.STRING)
    private SpotApplyStatus spotApplyStatus = SpotApplyStatus.PENDING;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @OneToMany(mappedBy = "spotApply", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter
    private List<CategorySpotApply> categorySpotApplies = new ArrayList<>();

    @OneToMany(mappedBy = "spotApply", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter
    private List<SpotApplyImage> spotApplyImages = new ArrayList<>();

    @OneToMany(mappedBy = "spotApply", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter
    private List<HashtagSpotApply> hashtagSpotApplies = new ArrayList<>();

    public SpotApply(String name, String address, Double pointX, Double pointY, User user) {
        this.name = name;
        this.address = address;
        this.pointX = pointX;
        this.pointY = pointY;
        this.user = user;
    }

}
