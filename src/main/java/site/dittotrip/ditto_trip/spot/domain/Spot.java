package site.dittotrip.ditto_trip.spot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import site.dittotrip.ditto_trip.hashtag.domain.Hashtag;
import site.dittotrip.ditto_trip.hashtag.domain.HashtagSpot;
import site.dittotrip.ditto_trip.hashtag.domain.HashtagSpotApply;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Spot {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spot_id")
    private Long id;

    private String name;
    private String address;
    private Double pointX;
    private Double pointY;
    private String imagePath;
    @Setter
    private Integer reviewCount = 0;
    @Setter
    private Float rating = 0.0f;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @OneToMany(mappedBy = "spot", cascade = CascadeType.ALL)
    @Setter
    private List<CategorySpot> categorySpots = new ArrayList<>();

    @OneToMany(mappedBy = "spot", cascade = CascadeType.ALL)
    @Setter
    private List<SpotImage> spotImages = new ArrayList<>();

    @OneToMany(mappedBy = "spot", cascade = CascadeType.ALL)
    @Setter
    private List<HashtagSpot> hashtagSpots = new ArrayList<>();

    public static Spot fromSpotApply(SpotApply spotApply) {
        Spot spot = new Spot(spotApply.getName(), spotApply.getAddress(), spotApply.getPointX(), spotApply.getPointY(), spotApply.getImagePath());

        for (CategorySpotApply categorySpotApply : spotApply.getCategorySpotApplies()) {
            spot.getCategorySpots().add(new CategorySpot(categorySpotApply.getCategory(), spot));
        }

        for (SpotApplyImage spotApplyImage : spotApply.getSpotApplyImages()) {
            spot.getSpotImages().add(new SpotImage(spotApplyImage.getImagePath(), spot));
        }

        for (HashtagSpotApply hashtagSpotApply : spotApply.getHashtagSpotApplies()) {
            spot.getHashtagSpots().add(new HashtagSpot(hashtagSpotApply.getHashtag(), spot));
        }

        return spot;
    }

    /**
     * for test
     */
    public Spot(String name, String address, Double pointX, Double pointY, String imagePath) {
        this.name = name;
        this.address = address;
        this.pointX = pointX;
        this.pointY = pointY;
        this.imagePath = imagePath;
    }
}
