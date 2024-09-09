package com.adp.changeservice.domain;

import com.adp.changeservice.domain.exception.NoSufficientCoinsException;
import com.adp.changeservice.domain.exception.NotValidBillException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CoinServiceTest {

    private final CoinInventory coinInventory = mock(CoinInventory.class);
    private final CoinService coinService = new CoinService(List.of(1.0, 2.0, 5.0, 10.0, 20.0, 100.0), coinInventory);

    @Test
    void getChangeForBill() {
        when(coinInventory.getAvailableCoins())
                .thenReturn(Map.of(0.25, 100, 0.10, 20));
        final Map<Double, Integer> actual = coinService.getChangeForBill(5.0);
        assertThat(actual).isEqualTo(Map.of(0.25, 20));
    }

    @Test
    void getChangeForBill_notValidBill() {
        assertThrows(NotValidBillException.class, () -> coinService.getChangeForBill(15.0));
    }

    @Test
    void getChangeForBill_noSufficientCoins() {
        when(coinInventory.getAvailableCoins())
                .thenReturn(Map.of(0.25, 100, 0.10, 20));
        assertThrows(NoSufficientCoinsException.class, () -> coinService.getChangeForBill(100.0));
    }
}
