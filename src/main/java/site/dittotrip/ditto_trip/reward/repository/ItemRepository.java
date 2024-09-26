package site.dittotrip.ditto_trip.reward.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.dittotrip.ditto_trip.reward.domain.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findItemByNameEquals(String name);
}
