package site.dittotrip.ditto_trip.category.categorydibs.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.user.domain.User;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
public class CategoryDibs {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_dibs_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    public CategoryDibs(Category category, User user) {
        this.category = category;
        this.user = user;
    }

}
