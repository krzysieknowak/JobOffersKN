package pl.joboffers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pl.joboffers.infrastructure.security.jwt.offer.http.OfferFetcherRestTemplateConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(OfferFetcherRestTemplateConfigurationProperties.class)
public class JobOffersSpringBootApplication{

    public static void main(String[] args) {
        SpringApplication.run(JobOffersSpringBootApplication.class, args);
    }
}
