package pl.joboffers.infrastructure.security.jwt.error;

import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "auth.token")
@Builder
public record JwtAuthConfigurationProperties(String secret, String issuer, long expirationInDays) {
}
