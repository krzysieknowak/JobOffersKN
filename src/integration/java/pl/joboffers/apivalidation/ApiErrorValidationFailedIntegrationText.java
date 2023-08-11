package pl.joboffers.apivalidation;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import pl.joboffers.BaseIntegrationTest;
import pl.joboffers.infrastructure.apivalidation.OfferAPIValidationErrorResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ApiErrorValidationFailedIntegrationText extends BaseIntegrationTest {

    @Test
    @WithMockUser
    public void user_adds_offer_with_missing_parameter_and_gets_400_bad_request() throws Exception {
        //given
        //when
        ResultActions performAddOffer = mockMvc.perform(post("/offers")
                .content("""
                                            {
                                            "offerUrl" : "google.pl",
                                            "jobPosition" : "koparkowy",
                                            "companyName" : "gugl"
                                            }
                                            """.trim()).contentType(MediaType.APPLICATION_JSON));
        //then
        MvcResult mvcResult = performAddOffer.andExpect(status().isBadRequest()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        OfferAPIValidationErrorResponse resultErrors = objectMapper.readValue(json, OfferAPIValidationErrorResponse.class);
        assertThat(resultErrors.errorList()).containsExactlyInAnyOrder(
                "earnings must not be empty",
                "earnings must not be null"
        );

    }
}
