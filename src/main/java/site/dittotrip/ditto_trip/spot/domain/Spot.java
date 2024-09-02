package site.dittotrip.ditto_trip.spot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import site.dittotrip.ditto_trip.hashtag.domain.Hashtag;
import site.dittotrip.ditto_trip.hashtag.domain.HashtagSpot;

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

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @OneToMany(mappedBy = "spot", cascade = CascadeType.REMOVE)
    private List<CategorySpot> categorySpots = new ArrayList<>();

    @OneToMany(mappedBy = "spot", cascade = CascadeType.REMOVE)
    private List<SpotImage> spotImages = new ArrayList<>();

    @OneToMany(mappedBy = "spot", cascade = CascadeType.REMOVE)
    private List<HashtagSpot> hashtagSpots = new ArrayList<>();

    /**
     * for test
     */
    public Spot(String name, String intro, String address, LocalTime startTime, LocalTime endTime, String phoneNumber, String homeUri, Double pointX, Double pointY, String imagePath) {
        this.name = name;
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
