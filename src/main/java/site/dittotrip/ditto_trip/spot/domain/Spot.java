package site.dittotrip.ditto_trip.spot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.geo.Point;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.hashtag.domain.Hashtag;

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

    private String spotName;
    private String intro;
    private String address;
    private LocalTime startTime;
    private LocalTime endTime;
    private String phoneNumber;
    private String homeUri;
    private Double pointX;
    private Double pointY;
    private String imagePath;
    @Setter
    private Integer reviewCount = 0;
    @Setter
    private Float rating = 0.0f;

    @OneToMany(mappedBy = "spot")
    private List<CategorySpot> categorySpots = new ArrayList<>();

    @OneToMany(mappedBy = "spot")
    private List<SpotImage> spotImages = new ArrayList<>();

    @ManyToMany(mappedBy = "spots")
    private List<Hashtag> hashtags = new ArrayList<>();

    /**
     * for test
     */
    public Spot(String spotName, String intro, String address, LocalTime startTime, LocalTime endTime, String phoneNumber, String homeUri, Double pointX, Double pointY, String imagePath) {
        this.spotName = spotName;
        this.intro = intro;
        this.address = address;
        this.startTime = startTime;
        this.endTime = endTime;
        this.phoneNumber = phoneNumber;
        this.homeUri = homeUri;
        this.pointX = pointX;
        this.pointY = pointY;
        this.imagePath = imagePath;
    }
}
