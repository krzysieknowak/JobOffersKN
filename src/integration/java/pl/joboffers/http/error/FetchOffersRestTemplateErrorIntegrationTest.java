package pl.joboffers.http.error;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.Fault;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.web.server.ResponseStatusException;
import pl.joboffers.domain.offer.OfferFetchable;
import pl.joboffers.infrastructure.offer.http.OfferFetcherRestTemplateConfigurationProperties;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.apache.hc.core5.http.HttpStatus.*;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class FetchOffersRestTemplateErrorIntegrationTest {

    @RegisterExtension
    public static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    OfferFetchable offerFetcherClient = new FetchOffersRestTemplateIntegrationTestConfig
            (new OfferFetcherRestTemplateConfigurationProperties(1000,1000,"http://localhost", 1111))
            .remoteOfferFetcherClient(1000,1500, wireMockServer.getPort());

    @Test
    public void should_throw_exception_500_when_fault_connection_reset_by_peer(){
        //given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(SC_OK)
                        .withHeader("Content-Type", "application/json")
                        .withFault(Fault.CONNECTION_RESET_BY_PEER)));
        //when
        Throwable throwable = catchThrowable(()-> offerFetcherClient.fetchOffers());

        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }
    @Test
    public void should_throw_exception_500_when_fault_empty_response(){
        //given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(SC_OK)
                        .withHeader("Content-Type", "application/json")
                        .withFault(Fault.EMPTY_RESPONSE)));
        //when
        Throwable throwable = catchThrowable(()-> offerFetcherClient.fetchOffers());

        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }
    @Test
    public void should_throw_exception_500_when_fault_malformed_response_chunk(){
        //given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(SC_OK)
                        .withHeader("Content-Type", "application/json")
                        .withFault(Fault.MALFORMED_RESPONSE_CHUNK)));
        //when
        Throwable throwable = catchThrowable(()-> offerFetcherClient.fetchOffers());

        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }
    @Test
    public void should_throw_exception_500_when_fault_random_data_then_close(){
        //given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(SC_OK)
                        .withHeader("Content-Type", "application/json")
                        .withFault(Fault.RANDOM_DATA_THEN_CLOSE)));
        //when
        Throwable throwable = catchThrowable(()-> offerFetcherClient.fetchOffers());

        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }
    @Test
    public void should_throw_exception_500_when_fixed_delay_is_set_to_3000_and_readTimeout_is_set_to_1500(){
        //given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(SC_OK)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                [{
                        "title": "Junior Java Developer",
                        "company": "BlueSoft Sp. z o.o.",
                        "salary": "7 000 – 9 000 PLN",
                        "offerUrl": "https://nofluffjobs.com/pl/job/junior-java-developer-bluesoft-remote-hfuanrre"
                    }]
                """.trim())
                        .withFixedDelay(3000)));
        //when
        Throwable throwable = catchThrowable(()-> offerFetcherClient.fetchOffers());

        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("500 INTERNAL_SERVER_ERROR");
    }

    @Test
    public void should_throw_exception_204_when_fault_no_content(){
        //given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(SC_NO_CONTENT)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                [{
                        "title": "Junior Java Developer",
                        "company": "BlueSoft Sp. z o.o.",
                        "salary": "7 000 – 9 000 PLN",
                        "offerUrl": "https://nofluffjobs.com/pl/job/junior-java-developer-bluesoft-remote-hfuanrre"
                    }]
                """.trim())));
        //when
        Throwable throwable = catchThrowable(()-> offerFetcherClient.fetchOffers());

        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("204 NO_CONTENT");
    }
    @Test
    public void should_throw_exception_401_when_status_unauthorized(){
        //given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(SC_UNAUTHORIZED)
                        .withHeader("Content-Type", "application/json")));

        //when
        Throwable throwable = catchThrowable(()-> offerFetcherClient.fetchOffers());

        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("401 UNAUTHORIZED");
    }
    @Test
    public void should_throw_exception_404_when_status_not_found(){
        //given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(SC_NOT_FOUND)
                        .withHeader("Content-Type", "application/json")));

        //when
        Throwable throwable = catchThrowable(()-> offerFetcherClient.fetchOffers());

        //then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("404 NOT_FOUND");
    }
}
