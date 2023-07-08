package pl.joboffers.infrastructure.security.jwt.offer.http;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.joboffers.domain.offer.OfferFetchable;
import pl.joboffers.domain.offer.offerdto.JobOfferResponseDto;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Log4j2
public class OfferFetcherRestTemplate implements OfferFetchable {

    private final RestTemplate restTemplate;
    private final String uri;
    private final int port;

    @Override
    public List<JobOfferResponseDto> fetchOffers() {
        log.info("Started fetching offers from remote server");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        try {
            String urlForService = getUrlForService("/offers");
            final String url = UriComponentsBuilder.fromHttpUrl(urlForService).toUriString();
            ResponseEntity<List<JobOfferResponseDto>> response = restTemplate.exchange(
                    urlForService,
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<>() {
                    });
            final List<JobOfferResponseDto> offers = response.getBody();
            if (offers == null) {
                log.info("There are no offers on remote server;empty list is returned");
                return Collections.emptyList();
            } else
                log.info("Offers fetched from remote server: " + offers);
            return offers;
        } catch (ResourceAccessException e) {
            log.error("There was an error caught! Zero offers are returned");
            return Collections.emptyList();
        }
    }

    private String getUrlForService(String service) {
        return uri + ":" + port + service;
    }
}

// http://ec2-3-120-147-150.eu-central-1.compute.amazonaws.com:5057/offers
