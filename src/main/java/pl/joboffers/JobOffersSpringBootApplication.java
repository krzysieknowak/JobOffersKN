package pl.joboffers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import pl.joboffers.infrastructure.security.jwt.offer.http.OfferFetcherRestTemplateConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(OfferFetcherRestTemplateConfigurationProperties.class)
@EnableMongoRepositories
public class JobOffersSpringBootApplication{

    public static void main(String[] args) {
        SpringApplication.run(JobOffersSpringBootApplication.class, args);
    }
}
