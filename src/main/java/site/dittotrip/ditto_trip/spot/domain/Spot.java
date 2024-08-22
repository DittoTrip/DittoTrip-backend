package site.dittotrip.ditto_trip.spot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;
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
    private Point point;
    private String imagePath;

    @OneToMany(mappedBy = "spot")
    private List<CategorySpot> categorySpots;

    @OneToMany(mappedBy = "spot")
    private List<SpotImage> spotImages = new ArrayList<>();

    @ManyToMany(mappedBy = "spots")
    private List<Hashtag> hashtags = new ArrayList<>();

}
