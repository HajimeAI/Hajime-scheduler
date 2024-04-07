package ai.hajimebot.global;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class LogEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(LogEvent logEvent) {
        applicationEventPublisher.publishEvent(logEvent);
    }
}
