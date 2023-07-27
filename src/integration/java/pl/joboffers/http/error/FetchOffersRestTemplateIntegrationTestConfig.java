package pl.joboffers.http.error;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;
import pl.joboffers.domain.offer.OfferFetchable;
import pl.joboffers.infrastructure.offer.http.OfferFetcherClientConfig;
import pl.joboffers.infrastructure.offer.http.OfferFetcherRestTemplate;
import pl.joboffers.infrastructure.offer.http.OfferFetcherRestTemplateConfigurationProperties;
import pl.joboffers.infrastructure.offer.http.RestTemplateResponseErrorHandler;

import java.time.Duration;

public class FetchOffersRestTemplateIntegrationTestConfig extends OfferFetcherClientConfig {


    public FetchOffersRestTemplateIntegrationTestConfig(OfferFetcherRestTemplateConfigurationProperties properties) {
        super(properties);
    }

    public OfferFetchable remoteOfferFetcherClient(RestTemplate restTemplate, String uri, int port){
        return new OfferFetcherRestTemplate(restTemplate, uri, port);
    }
    public OfferFetchable remoteOfferFetcherClient(int conTime, int readTime, int port){
        RestTemplate restTemplate = restTemplate(conTime, readTime, restTemplateResponseErrorHandler());
        return remoteOfferFetcherClient(restTemplate,"http://localhost", port);
    }

    public RestTemplate restTemplate(int conTime, int readTime, RestTemplateResponseErrorHandler restTemplateResponseErrorHandler) {
        return new RestTemplateBuilder()
                .errorHandler(restTemplateResponseErrorHandler)
                .setConnectTimeout(Duration.ofMillis(conTime))
                .setReadTimeout(Duration.ofMillis(readTime))
                .build();
    }

}
