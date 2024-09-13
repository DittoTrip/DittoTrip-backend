package site.dittotrip.ditto_trip.quest.aop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.quest.repository.QuestRepository;

@Component
@RequiredArgsConstructor
@Transactional
public class QuestManager {

    private final QuestRepository questRepository;

    public void handleQuests() {

    }

}
