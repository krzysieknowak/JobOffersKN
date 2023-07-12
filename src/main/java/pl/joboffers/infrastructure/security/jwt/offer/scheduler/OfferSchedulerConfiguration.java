package pl.joboffers.infrastructure.security.jwt.offer.scheduler;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ConditionalOnProperty(value = "${scheduling.enabled", matchIfMissing = false)
public class OfferSchedulerConfiguration {

}
