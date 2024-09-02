package site.dittotrip.ditto_trip.hashtag.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@Getter
public class HashtagDitto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_ditto_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtag;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ditto_id")
    private Ditto ditto;

    public HashtagDitto(Hashtag hashtag, Ditto ditto) {
        this.hashtag = hashtag;
        this.ditto = ditto;
    }

}
