package com.adp.changeservice.bdd.steps;

import com.adp.changeservice.domain.CoinInventory;
import com.adp.changeservice.domain.repository.CoinRepository;
import com.adp.changeservice.presentation.rest.controller.dto.ChangeResponse;
import com.adp.changeservice.presentation.rest.controller.dto.ConfirmMessageResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ContextConfiguration(classes = {CoinInventory.class, CoinRepository.class})
public class GetChangeForBillSteps {

    @Autowired
    private CoinInventory coinInventory;

    @Autowired
    private CoinRepository coinRepository;

    private TestRestTemplate restTemplate;

    private Map<Double, Integer> availableInventory;

    private Map<Double, Integer> changeResponse;

    private ConfirmMessageResponse confirmMessageResponse;

    private int statusCode;

    @LocalServerPort
    private int port;

    public GetChangeForBillSteps(TestRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Given("the coin inventory is initialized")
    public void theCoinInventoryIsInitialized(DataTable dataTable) {
        coinRepository.clearCoins();
        coinInventory.saveInitialData(dataTable.asMap(Double.class, Integer.class));
        availableInventory = coinInventory.getAvailableCoins();
    }

    @When("the user requests change for the bill with the amount {string}")
    public void theUserRequestsChangeForTheBillWithTheAmount(final String amount) throws JsonProcessingException {
        final double billAmount = Double.parseDouble(amount);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final HttpEntity<String> entity = new HttpEntity<>(null, headers);

        final ResponseEntity<String> response
                = restTemplate.postForEntity("http://localhost:" + port + "/api/change-service/bills/" + billAmount,
                entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            changeResponse = new ObjectMapper().readValue(response.getBody(), ChangeResponse.class)
                    .change();
        } else {
            confirmMessageResponse = new ObjectMapper().readValue(response.getBody(), ConfirmMessageResponse.class);
        }

        statusCode = response.getStatusCode().value();

    }

    @Then("the user should receive the response with {string} coins with count {string}")
    public void theUserShouldReceiveTheResponseWithCoinsWithCount(final String denomination, final String count) {
        double coinDenomination = Double.parseDouble(denomination);
        int expectedCount = Integer.parseInt(count);
        assertEquals(expectedCount, changeResponse.get(coinDenomination));
        assertEquals(HttpStatus.OK.value(), statusCode);
    }

    @And("the inventory should be updated with the following coins:")
    public void theInventoryShouldBeUpdatedWithTheFollowingCoins(DataTable dataTable) {
        availableInventory = coinInventory.getAvailableCoins();
        final Map<Double, Integer> expectedCoins = dataTable.asMap(Double.class, Integer.class);
        expectedCoins.forEach((coinType, count) -> {
            assertThat(availableInventory.get(coinType)).isEqualTo(count);
        });
    }

    @Then("we expect {int} response with message {string}")
    public void weExpectResponseWithMessage(int statusCode, String exceptionMessage) {
        assertEquals(statusCode, this.statusCode);
        assertTrue(confirmMessageResponse.exceptionMessages().contains(exceptionMessage));
    }
}
