package pl.joboffers.infrastructure.security.jwt.offer.http;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pl.joboffers.domain.offer.OfferFetchable;

import java.time.Duration;

@Configuration
public class OfferFetcherClientConfig {

    @Bean
    public RestTemplateResponseErrorHandler restTemplateResponseErrorHandler(){
        return new RestTemplateResponseErrorHandler();
    }
    @Bean
    public RestTemplate restTemplate(RestTemplateResponseErrorHandler restTemplateResponseErrorHandler){
        return new RestTemplateBuilder()
                .errorHandler(restTemplateResponseErrorHandler)
                .setConnectTimeout(Duration.ofMillis(5000))
                .setReadTimeout(Duration.ofMillis(5000))
                .build();
    }
    @Bean
    public OfferFetchable remoteOfferFetcherClient(RestTemplate restTemplate,
                                                   @Value("${offer.http.client.config.uri}") String uri,
                                                   @Value("${offer.http.client.config.port}") int port){
        return new OfferFetcherRestTemplate(restTemplate, uri, port);
    }
}
