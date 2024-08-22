package site.dittotrip.ditto_trip.ditto.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import site.dittotrip.ditto_trip.user.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@Getter
public class DittoComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ditto_id")
    private Long id;

    @Setter
    private String body;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ditto_id")
    private Ditto ditto;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_ditto_comment_id")
    private DittoComment parentDittoComment;

    @OneToMany(mappedBy = "parentDittoComment")
    private List<DittoComment> childDittoComments = new ArrayList<>();

}
