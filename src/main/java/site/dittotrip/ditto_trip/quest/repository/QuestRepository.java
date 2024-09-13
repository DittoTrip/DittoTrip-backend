package site.dittotrip.ditto_trip.quest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.dittotrip.ditto_trip.quest.domain.Quest;

public interface QuestRepository extends JpaRepository<Quest, Long> {
}
