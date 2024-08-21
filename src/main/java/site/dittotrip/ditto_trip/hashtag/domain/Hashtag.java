package site.dittotrip.ditto_trip.hashtag.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.spot.domain.Spot;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Hashtag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private Long id;

    private String hashtagName;

    @ManyToMany
    @JoinTable(name = "hashtag_category")
    private List<Category> categories = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "hashtag_spot")
    private List<Spot> spots = new ArrayList<>();

}
