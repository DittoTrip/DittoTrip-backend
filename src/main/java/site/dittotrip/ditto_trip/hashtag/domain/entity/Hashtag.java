package site.dittotrip.ditto_trip.hashtag.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @OneToMany(mappedBy = "hashtag")
    private List<CategoryHashtag> categoryHashtags = new ArrayList<>();

    @OneToMany(mappedBy = "hashtag")
    private List<SpotHashtag> spotHashtags = new ArrayList<>();

}
