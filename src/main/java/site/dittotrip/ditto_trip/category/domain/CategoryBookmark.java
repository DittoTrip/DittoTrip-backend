package site.dittotrip.ditto_trip.category.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import site.dittotrip.ditto_trip.user.domain.User;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
public class CategoryBookmark {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_bookmark_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    public CategoryBookmark(Category category, User user) {
        this.category = category;
        this.user = user;
    }

}
