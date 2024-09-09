package com.adp.changeservice.presentation.rest.controller;

import com.adp.changeservice.domain.CoinService;
import com.adp.changeservice.presentation.rest.controller.dto.ChangeResponse;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BillControllerTest {

    private final CoinService coinService = mock(CoinService.class);
    private final BillController billController = new BillController(coinService);

    @Test
    void getChange() {
        when(coinService.getChangeForBill(5.0)).thenReturn(Map.of(0.25, 20));
        final ChangeResponse actual = billController.getChange(5.0);
        assertThat(actual).isEqualTo(ChangeResponse.of(Map.of(0.25, 20)));
    }

}
