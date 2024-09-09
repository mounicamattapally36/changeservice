package com.adp.changeservice.domain;

import com.adp.changeservice.domain.repository.CoinRepository;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CoinInventoryTest {

    private final static Map<Double, Integer> coinMap = Map.of(0.25, 100, 0.10, 100, 0.05, 100, 0.01, 100);

    private final CoinRepository coinRepository = mock(CoinRepository.class);
    private final CoinInventory coinInventory = new CoinInventory(coinRepository);


    @Test
    void testGetAvailableCoins() {
        when(coinRepository.getAvailableCoins()).thenReturn(coinMap);
        final Map<Double, Integer> actual = coinInventory.getAvailableCoins();
        assertThat(actual).isEqualTo(coinMap);
    }

    @Test
    void testSaveInitialData()  {
        coinInventory.saveInitialData(coinMap);
        verify(coinRepository, times(1)).saveCoins(coinMap);
    }

    @Test
    void testUpdateInventory() {
        when(coinRepository.getAvailableCoins()).thenReturn(coinMap);
        coinInventory.updateInventory(0.25, 20);
        verify(coinRepository, times(1)).updateCoins(Map.of(0.25, 80));
    }
}
