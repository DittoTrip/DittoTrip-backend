package site.dittotrip.ditto_trip.spot.stillcut.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Stillcut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stillcut_id")
    private Long id;

    @Column(name = "stillcut_body")
    private String body;

}
