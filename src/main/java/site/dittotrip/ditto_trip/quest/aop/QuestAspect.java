package site.dittotrip.ditto_trip.quest.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import site.dittotrip.ditto_trip.auth.domain.CustomUserDetails;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class QuestAspect {

    private final QuestManager questManager;

    @Pointcut("@annotation(QuestHandlingTargetMethod)")
    private void questHandlingTarget() {}

    @AfterReturning(pointcut = "questHandlingTarget()")
    public void handleQuest(JoinPoint joinPoint) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String methodName = joinPoint.getSignature().getName();

        questManager.handleQuests(userDetails, methodName);
    }

}
