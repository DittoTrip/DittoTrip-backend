package site.dittotrip.ditto_trip.category.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.dittotrip.ditto_trip.category.domain.CategoryBookmark;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.category.domain.enums.CategoryMajorType;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface CategoryBookmarkRepository extends JpaRepository<CategoryBookmark, Long> {

    Optional<CategoryBookmark> findByCategoryAndUser(Category category, User user);

    @EntityGraph(attributePaths = "category")
    @Query("select b from CategoryBookmark b where b.user= :user and b.category.categoryMajorType= :majorType")
    Page<CategoryBookmark> findByUserAndMajorType(@Param("user") User user,
                                                  @Param("majorType") CategoryMajorType majorType,
                                                  Pageable pageable);

}
