package pl.joboffers.infrastructure.security.jwt.offer.http;

import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "offer.http.client.config")
@Builder
public record OfferFetcherRestTemplateConfigurationProperties(int connectTimeout, int readTimeout, String uri, int port) {
}
