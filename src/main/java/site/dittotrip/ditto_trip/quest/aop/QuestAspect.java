package site.dittotrip.ditto_trip.quest.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class QuestAspect {

    private final QuestManager questManager;

    @Pointcut("@annotation(QuestHandlingTargetMethod)")
    private void questHandlingTarget() {}

    @AfterReturning(pointcut = "questHandlingTarget()")
    public void handleQuest(JoinPoint joinPoint) {
    }

}
