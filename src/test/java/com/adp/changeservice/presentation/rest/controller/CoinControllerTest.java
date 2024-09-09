package com.adp.changeservice.presentation.rest.controller;

import com.adp.changeservice.domain.CoinService;
import com.adp.changeservice.presentation.rest.controller.dto.InventoryResponse;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CoinControllerTest {

    private final CoinService coinService = mock(CoinService.class);
    private final CoinController coinController = new CoinController(coinService);

    @Test
    void getChangeInventory() {
        when(coinService.getAvailableCoins()).thenReturn(Map.of(0.25, 20, 0.10, 20));
        final InventoryResponse actual = coinController.getCoinInventory();
        assertThat(actual).isEqualTo(InventoryResponse.of(Map.of(0.25, 20, 0.10, 20)));
    }

}