package pl.joboffers.infrastructure.offer.http;

import lombok.AllArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pl.joboffers.domain.offer.OfferFetchable;

import java.time.Duration;
@AllArgsConstructor
@Configuration
public class OfferFetcherClientConfig {

    final private OfferFetcherRestTemplateConfigurationProperties properties;

    @Bean
    public RestTemplateResponseErrorHandler restTemplateResponseErrorHandler(){
        return new RestTemplateResponseErrorHandler();
    }
    @Bean
    public RestTemplate restTemplate(RestTemplateResponseErrorHandler restTemplateResponseErrorHandler){
        return new RestTemplateBuilder()
                .errorHandler(restTemplateResponseErrorHandler)
                .setConnectTimeout(Duration.ofMillis(properties.connectTimeout()))
                .setReadTimeout(Duration.ofMillis(properties.readTimeout()))
                .build();
    }
    @Bean
    public OfferFetchable remoteOfferFetcherClient(RestTemplate restTemplate){
        return new OfferFetcherRestTemplate(restTemplate, properties.uri(), properties.port());
    }
}
