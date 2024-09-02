package site.dittotrip.ditto_trip.hashtag.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.dittotrip.ditto_trip.spot.domain.SpotApply;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@Getter
public class HashtagSpotApply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_spot_apply_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtag;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "spot_apply_id")
    private SpotApply spotApply;

    public HashtagSpotApply(Hashtag hashtag, SpotApply spotApply) {
        this.hashtag = hashtag;
        this.spotApply = spotApply;
    }

}
