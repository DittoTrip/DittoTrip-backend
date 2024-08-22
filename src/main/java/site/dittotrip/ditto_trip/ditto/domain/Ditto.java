package site.dittotrip.ditto_trip.ditto.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import site.dittotrip.ditto_trip.hashtag.domain.Hashtag;
import site.dittotrip.ditto_trip.user.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@Getter
public class Ditto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ditto_id")
    private Long id;

    private String title;
    private String body;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @OneToMany(mappedBy = "ditto")
    private List<DittoImage> dittoImages = new ArrayList<>();

    @ManyToMany(mappedBy = "dittos")
    private List<Hashtag> hashtags = new ArrayList<>();

}
